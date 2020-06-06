package com.bandi.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.vector.BytesColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.LongColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.orc.OrcFile;
import org.apache.orc.Reader;
import org.apache.orc.RecordReader;
import org.apache.orc.TypeDescription;
import org.apache.orc.Writer;

public class ORCTest {

	public static void main(String[] args) throws IOException {
		String path = "/tmp/myORCdata.txt";
		String normalPath = "/tmp/myNormalORCdata.txt";
		WriteInNormal(normalPath);
		WriteInOC(path);
		ReadInOC(path);
	}

	public static void WriteInNormal(String path) throws IllegalArgumentException, IOException {
		Files.deleteIfExists(Paths.get(path));
		try (OutputStream writer = new FileOutputStream(new File(path))) {
			TypeDescription schema = TypeDescription.fromString("struct<x:int,y:string>");
			VectorizedRowBatch batch = schema.createRowBatch();
			LongColumnVector x = (LongColumnVector) batch.cols[0];
			for (int r = 0; r < 10000; ++r) {
				byte[] buffer = ("Last-" + (r * 3)).getBytes(StandardCharsets.UTF_8);
				writer.write(buffer);
			}
		}
	}

	public static void WriteInOC(String path) throws IllegalArgumentException, IOException {
		// Use WriterOptions to Power this
		Path newPath = new Path(path);
		Files.deleteIfExists(Paths.get(path));
		TypeDescription schema = TypeDescription.fromString("struct<x:int,y:string>");
		try (Writer writer = OrcFile.createWriter(newPath,
				OrcFile.writerOptions(new Configuration()).setSchema(schema))) {
			int maxSize = 500;
			VectorizedRowBatch batch = schema.createRowBatch(maxSize);
			LongColumnVector x = (LongColumnVector) batch.cols[0];
			BytesColumnVector y = (BytesColumnVector) batch.cols[1];
			for (int r = 0; r < 10000; ++r) {
				int row = batch.size++;
				x.vector[row] = r;
				byte[] buffer = ("Last-" + (r * 3)).getBytes(StandardCharsets.UTF_8);
				y.setRef(row, buffer, 0, buffer.length);
				// If the batch is full, write it out and start over.
				if (batch.size == batch.getMaxSize()) {
					writer.addRowBatch(batch);
					batch.reset();
				}
			}
			if (batch.size != 0) {
				writer.addRowBatch(batch);
			}
		}
	}

	public static void ReadInOC(String path) throws IOException {
		// Get the information from the file footer
		Reader reader = OrcFile.createReader(new Path(path), OrcFile.readerOptions(new Configuration()));
		System.out.println("File schema: " + reader.getSchema());
		System.out.println("Row count: " + reader.getNumberOfRows());

		// Pick the schema we want to read using schema evolution
		TypeDescription readSchema = TypeDescription.fromString("struct<z:int,y:string,x:bigint>");
		// Read the row data
		int maxSize = 500;
		VectorizedRowBatch batch = readSchema.createRowBatch(maxSize);
		RecordReader rowIterator = reader.rows(reader.options().schema(readSchema));
		LongColumnVector z = (LongColumnVector) batch.cols[0];
		BytesColumnVector y = (BytesColumnVector) batch.cols[1];
		LongColumnVector x = (LongColumnVector) batch.cols[2];
		while (rowIterator.nextBatch(batch)) {
			for (int row = 0; row < batch.size; ++row) {
				int zRow = z.isRepeating ? 0 : row;
				int xRow = x.isRepeating ? 0 : row;
				//System.out.println("z: " + (z.noNulls || !z.isNull[zRow] ? z.vector[zRow] : null));
				//System.out.println("y: " + y.toString(row));
				//System.out.println("x: " + (x.noNulls || !x.isNull[xRow] ? x.vector[xRow] : null));
			}
		}
		System.out.println("Data read completed");
		rowIterator.close();
	}

}
