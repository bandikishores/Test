package com.sometest;

import static spark.Spark.*;

public class SampleHttpServer {

    public static void main(String[] args) {
        get("/hello", (req, res) -> {
            Thread.sleep(5000);
            return "Hello World";
        });
    }

}
