package com.sometest;

import static spark.Spark.get;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;

/**
 * 
 * http://localhost:4567/hello
 * 
 * @author kishore.bandi
 *
 */
public class SampleHttpServer {

    public static void main(String[] args) {
        get("/", (req, res) -> {
            try {
                String json = IOUtils.toString(
                        new FileInputStream(new File(ClassLoader.getSystemResource("response.json").getPath())));
                return json;
            } catch (Exception ex) {
                ex.printStackTrace();
                throw ex;
            }
        });
    }

}
