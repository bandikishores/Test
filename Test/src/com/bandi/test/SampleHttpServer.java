package com.bandi.test;

import static spark.Spark.get;
import static spark.Spark.post;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;

import spark.Route;

/**
 * 
 * http://localhost:4567/hello
 * http://localhost:4567/sleep?sleep=10000
 * 
 * @author kishore.bandi
 *
 */
public class SampleHttpServer {

	public static void main(String[] args) {
		Route route = (req, res) -> {
			try {
				if (req.pathInfo().contains("sleep")) {
					Thread.sleep(2);
					res.type("application/json");
					return "{\"statusCode\":1,\"message\":[{\"eventName\":\"TestEvent\",\"appName\":\"delivery-lucifer\",\"version\":\"1.0.0\",\"schema\":\"{\\\"$schema\\\":\\\"http://json-schema.org/draft-04/schema#\\\",\\\"properties\\\":{\\\"destinations\\\":{\\\"items\\\":{\\\"type\\\":\\\"string\\\"},\\\"type\\\":\\\"array\\\"},\\\"method\\\":{\\\"type\\\":\\\"string\\\"},\\\"origin\\\":{\\\"type\\\":\\\"string\\\"},\\\"requestId\\\":{\\\"type\\\":\\\"string\\\"}},\\\"title\\\":\\\"TestEvent\\\",\\\"type\\\":\\\"object\\\"}\"}],\"error\":\"\"}";
				}
				res.type("application/json");
				if (req.pathInfo().contains("schema")) {
					return "{\"statusCode\":1,\"message\":[{\"eventName\":\"TestEvent\",\"appName\":\"delivery-lucifer\",\"version\":\"1.0.0\",\"schema\":\"{\\\"$schema\\\":\\\"http://json-schema.org/draft-04/schema#\\\",\\\"properties\\\":{\\\"destinations\\\":{\\\"items\\\":{\\\"type\\\":\\\"string\\\"},\\\"type\\\":\\\"array\\\"},\\\"method\\\":{\\\"type\\\":\\\"string\\\"},\\\"origin\\\":{\\\"type\\\":\\\"string\\\"},\\\"requestId\\\":{\\\"type\\\":\\\"string\\\"}},\\\"title\\\":\\\"TestEvent\\\",\\\"type\\\":\\\"object\\\"}\"}],\"error\":\"\"}";
				} else if (req.pathInfo().contains("event")) {
					return "{\"statusCode\":1,\"message\":[{\"topic\":\"testevent\",\"group\":\"Example\",\"event\":\"testevent\",\"updatedBy\":\"\"},{\"topic\":\"GetCollectionsV4Response\",\"group\":\"gandalf_pipeline\",\"event\":\"GetCollectionsV4Response\",\"updatedBy\":\"\"},{\"topic\":\"GetCollectionsV4Request\",\"group\":\"gandalf_pipeline\",\"event\":\"GetCollectionsV4Request\",\"updatedBy\":\"\"},{\"topic\":\"GetRestaurantsV4Request\",\"group\":\"gandalf_pipeline\",\"event\":\"GetRestaurantsV4Request\",\"updatedBy\":\"\"},{\"topic\":\"GetRestaurantsV4Response\",\"group\":\"gandalf_pipeline\",\"event\":\"GetRestaurantsV4Response\",\"updatedBy\":\"\"},{\"topic\":\"ListingV3Event\",\"group\":\"data-services-api\",\"event\":\"ListingV3Event\",\"updatedBy\":\"\"}],\"error\":\"\"}";
				}
				res.type("application/json");
				String json = IOUtils.toString(
						new FileInputStream(new File(ClassLoader.getSystemResource("response.json").getPath())));
				return json;
			} catch (Exception ex) {
				ex.printStackTrace();
				throw ex;
			}
		};
		get("/*", route);
		post("/*", route);
	}

}
