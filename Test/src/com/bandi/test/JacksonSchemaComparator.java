package com.bandi.test;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import com.bandi.test.data.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;

public class JacksonSchemaComparator {

	public static void main(String[] args) throws IOException {
		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
		classLoadersList.add(ClasspathHelper.contextClassLoader());
		classLoadersList.add(ClasspathHelper.staticClassLoader());

		String packageName = "com.swiggy.dp.checkout";
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
					JsonSchema jsonSchema = getJsonSchema(clazz);
					for (Class<?> innerClazz : allClasses) {
						if (innerClazz.getCanonicalName() == clazz.getCanonicalName()) {
							continue;
						}

						Event iannotation = innerClazz.getAnnotation(Event.class);
						if (iannotation != null) {
							JsonSchema ijsonSchema = getJsonSchema(innerClazz);
							verifyAndPrintMatches(annotation, iannotation, jsonSchema, ijsonSchema);

							String fileName = MessageFormat.format(
									"/Users/bandi.kishore/test/dp_domain_schemas/{0}:{1}.schema", annotation.AppName(),
									annotation.TableName());
						}

					}
				}
			}
			System.out.println("Completed Schema creation for " + totalClasses);
		} else {
			System.out.println("No Classes loaded");
		}
	}

	private static boolean verifyAndPrintMatches(Event annotation, Event iannotation, JsonSchema jsonSchema,
			JsonSchema ijsonSchema) {
		if (jsonSchema.getId() == ijsonSchema.getId()) {
			printMatch(annotation, iannotation, ijsonSchema);
			return true;
		}
		if (ijsonSchema.isObjectSchema()) {
			ObjectSchema objectSchema = ijsonSchema.asObjectSchema();
			Map<String, JsonSchema> properties = objectSchema.getProperties();
			if (MapUtils.isNotEmpty(properties)) {
				for (Entry<String, JsonSchema> entry : properties.entrySet()) {
					if (entry.getValue().isObjectSchema()) {
						return verifyAndPrintMatches(annotation, iannotation, jsonSchema, entry.getValue());
					} else if (entry.getKey().equals(jsonSchema.getId())) {

					}
				}
			}
			return verifyAndPrintMatches(annotation, iannotation, objectSchema, ijsonSchema);
		} else {
			return false;
		}
	}

	private static void printMatch(Event annotation, Event iannotation, JsonSchema ijsonSchema) {
		System.out.println(MessageFormat.format("Duplicate Found {0} between App:table {1}:{2} and App:table{3}:{4}",
				ijsonSchema.getId(), annotation.AppName(), annotation.TableName(), iannotation.AppName(),
				iannotation.TableName()));
	}

	private static JsonSchema getJsonSchema(Class clazz) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		// configure mapper, if necessary, then create schema generator
		JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
		return schemaGen.generateSchema(clazz);
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
