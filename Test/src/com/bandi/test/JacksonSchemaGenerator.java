package com.bandi.test;

import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.swiggy.dp.Event;

public class JacksonSchemaGenerator {

	public static void main(String[] args) throws IOException {
		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
		classLoadersList.add(ClasspathHelper.contextClassLoader());
		classLoadersList.add(ClasspathHelper.staticClassLoader());

		String packageName = "com.swiggy.dp";
		Reflections reflections = new Reflections(new ConfigurationBuilder()
				.setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
				.setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
				.filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(packageName))));

		Set<Class<? extends Object>> allClasses = reflections.getSubTypesOf(Object.class);

		if (CollectionUtils.isNotEmpty(allClasses)) {
			Integer totalClasses = 0;
			for (Class<?> clazz : allClasses) {
				Event annotation = clazz.getAnnotation(Event.class);
				if (annotation != null) {
					String jsonSchema = getJsonSchema(clazz);
					String fileName = MessageFormat.format("/Users/bandi.kishore/test/dp_domain_schemas/{0}:{1}.schema",
							annotation.AppName(), annotation.TableName());
					try (FileWriter fw = new FileWriter(fileName)) {
						fw.write(jsonSchema);
						totalClasses++;
					}
				}
			}
			System.out.println("Completed Schema creation for " + totalClasses);
		} else {
			System.out.println("No Classes loaded");
		}
	}

	private static String getJsonSchema(Class clazz) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		// configure mapper, if necessary, then create schema generator
		JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
		JsonSchema schema = schemaGen.generateSchema(clazz);

		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema);
	}

	private static String getJsonSchemaForNonJacksonClasses(Class clazz) throws IOException {
		org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
		// There are other configuration options you can set. This is the one I needed.
		// mapper.configure(SerializationConfig.Feature.WRITE_ENUMS_USING_TO_STRING,
		// true);

		org.codehaus.jackson.schema.JsonSchema schema = mapper.generateJsonSchema(clazz);

		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema);
	}
}
