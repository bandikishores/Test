package com.bandi.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;

public class CSVReader {

	private static final String METADATA_CSV = "/Users/bandi.kishore/test/metadata.csv";
	private static final String ADDITIONAL_PARSE_NEEDED = "/Users/bandi.kishore/test/extraction.csv";
	private static final String PARSED_OUTPUT = "/Users/bandi.kishore/test/requestedData.csv";
	private static final String EVENTS_LIST = "/Users/bandi.kishore/test/eventsList.csv";
	static Map<String, List<ObjectDefinition>> userDefinedTypes = new HashMap<>();

	@Data
	@AllArgsConstructor
	public static class ObjectDefinition {
		public String name;
		public String type;
		public String description;
	}

	public static int count = 0;
	public static int totalLines = 0;

	public static void main(String[] args) throws IOException {
		File file = new File(PARSED_OUTPUT);
		if (file.exists()) {
			file.delete();
		}
		file = new File(ADDITIONAL_PARSE_NEEDED);
		if (file.exists()) {
			file.delete();
		}
		file = new File(METADATA_CSV);
		if (file.exists()) {
			file.delete();
		}
		file = new File(EVENTS_LIST);
		if (file.exists()) {
			file.delete();
		}
		try (Scanner sc = new Scanner(new File("/Users/bandi.kishore/test/exported.csv"));) {
			String line;
			while ((line = sc.nextLine()) != null) {
				parseLine(line);
				totalLines++;
			}
		} catch (NoSuchElementException e) {

		}
		writeMetadataToCSV();

		// userDefinedTypes.keySet().stream().sorted().forEach(System.out::println);

		System.out.println(count);
		System.out.println(totalLines);
	}

	private static void parseLine(String line) throws IOException {
		String[] column = line.split("\t");
		final String eventName = column[0];
		final String appName = column[1];
		String version = "";
		String schema = "";

		List<ObjectDefinition> objects = new ArrayList<>();
		version = column[2];
		schema = column[3];

		try (FileWriter fw = new FileWriter(EVENTS_LIST, true)) {
			fw.append(StringUtils.join(Arrays.asList(appName, eventName), ",") + "\n");
		}
		if (schema.equals(
				"{\"$schema\":\"http://json-schema.org/draft-04/schema#\",\"definitions\":{},\"id\":\"http://example.com/example.json\",\"properties\":{},\"type\":\"object\"}")) {
			writeToManualParseFile(line);
		} else {
			try (FileWriter fw = new FileWriter(PARSED_OUTPUT, true)) {
				objects = parseSchema(schema);
				if (objects.size() == 0) {
					writeToManualParseFile(line);
				} else {
					objects.stream().forEach(o -> {
						try {
							fw.append(StringUtils.join(Arrays.asList(eventName, o.name, o.type, o.description, appName),
									",") + "\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
				}
			}
		}
	}

	private static void writeMetadataToCSV() throws IOException {
		try (FileWriter fw = new FileWriter(METADATA_CSV, true)) {
			userDefinedTypes.entrySet().stream().forEach(m -> {
				m.getValue().stream().forEach(o -> {
					try {
						fw.append(
								StringUtils.join(Arrays.asList(m.getKey(), o.name, o.type, o.description), ",") + "\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			});
			/*-System.out.println(userDefinedTypes.keySet().size());
			System.out.println(userDefinedTypes.values().stream().flatMap(m -> m.stream()).count());*/
		}
	}

	private static void writeToManualParseFile(String line) throws IOException {
		try (FileWriter fw = new FileWriter(ADDITIONAL_PARSE_NEEDED, true)) {
			fw.write(line + "\n");
			count++;
		}
	}

	private static List<ObjectDefinition> parseSchema(String schema) {
		JSONObject jsonObj = new JSONObject(schema);
		JSONObject jo = jsonObj.getJSONObject("properties");
		List<ObjectDefinition> objects = new ArrayList<>();

		Iterator<String> keys = jo.keys();
		keys = new OrderedIterator(keys);

		while (keys.hasNext()) {
			String k = keys.next();
			List<ObjectDefinition> extractObjects = extractObjects(k.toString(), jo.opt(k), schema, k.toString());
			objects.addAll(extractObjects);
			if (extractObjects.size() == 0) {
				return extractObjects;
			}
		}
		return objects;
	}

	private static List<ObjectDefinition> extractObjects(String hierarchyName, Object o, String schema,
			String currentObjectName) {
		JSONObject jsonObj = (JSONObject) o;
		if (jsonObj.has("type")) {
			String type = jsonObj.getString("type");

			if (type.equals("object")) {
				if (jsonObj.has("properties")) {
					if (userDefinedTypes.containsKey(currentObjectName)) {
						// System.out.println("Already found occurence "+currentObjectName);
					} else {
						List<ObjectDefinition> objectDefinitions = extractFromObject("",
								jsonObj.getJSONObject("properties"), schema);
						userDefinedTypes.put(currentObjectName, objectDefinitions);
					}
					return Arrays
							.asList(new ObjectDefinition(hierarchyName, currentObjectName, getDescription(jsonObj)));
				} else {
					return Arrays.asList(new ObjectDefinition(hierarchyName, "STRING", getDescription(jsonObj)));
				}
			} else if (type.equals("array"))
				return extractObjects(hierarchyName + "[]", jsonObj.getJSONObject("items"), schema, currentObjectName);
			else
				return Arrays.asList(scalarType(hierarchyName, type, getDescription(jsonObj)));
		} else if (jsonObj.has("$ref")) {
			String[] path = jsonObj.getString("$ref").split("/");
			JSONObject valueObj = new JSONObject(schema);

			// ignore first element as that is #
			for (int i = 1; i < path.length; ++i) {
				valueObj = valueObj.getJSONObject(path[i]);
			}
			return extractObjects(hierarchyName, valueObj, schema, currentObjectName);
		} else {
			return Arrays.asList(new ObjectDefinition(hierarchyName, "NoTypeDefined", getDescription(jsonObj)));
		}
	}

	private static List<ObjectDefinition> extractFromObject(String hierarchyName, JSONObject o, String schema)
			throws JSONException {
		Iterator<String> keys = o.keys();
		keys = new OrderedIterator(keys);
		List<ObjectDefinition> objects = new ArrayList<>();

		// return string when properties/items is empty
		if (!keys.hasNext())
			return Arrays.asList(new ObjectDefinition(hierarchyName, "STRING", getDescription(o)));

		while (keys.hasNext()) {
			String k = keys.next();
			objects.addAll(
					extractObjects((StringUtils.isBlank(hierarchyName) ? "" : (hierarchyName + ".")) + k.toString(),
							o.opt(k), schema, k.toString()));
		}

		return objects;
	}

	private static String getDescription(JSONObject o) {
		if (o.has("description")) {
			String desc = o.getString("description");
			if (desc != null && !desc.equals("An explanation about the purpose of this instance.")) {
				return desc;
			}
		}
		return null;
	}

	private static ObjectDefinition scalarType(String hierarchyName, String type, String description) {
		if (type.equals("number"))
			type = "DOUBLE";
		else if (type.equals("integer"))
			type = "BIGINT";
		else if (type.equals("string"))
			type = "STRING";
		else
			type = type.toUpperCase();
		return new ObjectDefinition(hierarchyName, type, description);
	}

	static class OrderedIterator implements Iterator<String> {

		Iterator<String> it;

		public OrderedIterator(Iterator<String> iter) {
			SortedSet<String> keys = new TreeSet<String>();
			while (iter.hasNext()) {
				keys.add(iter.next());
			}
			it = keys.iterator();
		}

		public boolean hasNext() {
			return it.hasNext();
		}

		public String next() {
			return it.next();
		}

		public void remove() {
			it.remove();
		}
	}

}
