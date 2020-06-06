package com.bandi.test;

import java.io.File;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.hadoop.metadata.*;

public class AVROTest {

	public static void main(String[] args) throws IOException {
		WriteToAVROSimple();
	//	WriteToAVROParquetSnappyCompression();
	}

	private static void WriteToAVROSimple() throws IOException {
		Schema schema = new Schema.Parser().parse(new File(ClassLoader.getSystemResource("emp.asvc").getPath()));
		GenericRecord e1 = new GenericData.Record(schema);
		e1.put("name", "Pravali");
		e1.put("last_name", "Kishore");
		e1.put("id", 001);
		e1.put("salary", 10000);
		e1.put("age", 25);
		e1.put("address", "Bangalore");

		DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);

		DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(datumWriter);
		String tempFile = "/tmp/myAVROdata.txt";
		dataFileWriter.create(schema, new File(tempFile));

		for (int i = 0; i < 10000; i++) {
			dataFileWriter.append(e1);
		}
		dataFileWriter.close();

		System.out.println("data successfully serialized");

		DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>(schema);
		DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(new File(tempFile),
				datumReader);
		GenericRecord emp = null;

		while (dataFileReader.hasNext()) {
			emp = dataFileReader.next(emp);
			System.out.println(emp);
		}
		System.out.println("data successfully de-serialized");
		dataFileReader.close();

		// Read it in different Format which doesn't have last_name
		datumReader = new GenericDatumReader<GenericRecord>(
				new Schema.Parser().parse(new File(ClassLoader.getSystemResource("emp2.asvc").getPath())));
		dataFileReader = new DataFileReader<GenericRecord>(new File(tempFile), datumReader);
		emp = null;

		while (dataFileReader.hasNext()) {
			emp = dataFileReader.next(emp);
			System.out.println(emp);
		}
		System.out.println("data successfully de-serialized In New Format With Field Removal");
		dataFileReader.close();

		// Read it in different Format where last_name was changed to first_name
		datumReader = new GenericDatumReader<GenericRecord>(
				new Schema.Parser().parse(new File(ClassLoader.getSystemResource("emp3.asvc").getPath())));
		dataFileReader = new DataFileReader<GenericRecord>(new File(tempFile), datumReader);
		emp = null;

		while (dataFileReader.hasNext()) {
			emp = dataFileReader.next(emp);
			System.out.println(emp);
		}
		System.out.println("data successfully de-serialized In New Format With Change of Field Name");
		dataFileReader.close();
	}

}
