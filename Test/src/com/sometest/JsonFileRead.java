package com.sometest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

class BooleanTypeAdapter extends TypeAdapter<Boolean> {

	@Override
	public void write(JsonWriter out, Boolean value) throws IOException {
		out.value(value);

	}

	@Override
	public Boolean read(JsonReader in) throws IOException {
		return in.nextBoolean();
		// return Boolean.valueOf(in.nextString());
	}

}

class JsonTestClass {
	Boolean name;
}

public class JsonFileRead {

	// Temp Json - TempJson
	public static void main(String[] args) throws FileNotFoundException, IOException {

		java.lang.reflect.Type listType = new TypeToken<List<String>>() {
		}.getType();
		List target = new LinkedList();
		target.add("blah");
		target.add(3);
		target.add(new JsonTestClass());

		Gson gson = new Gson();
		
//		System.out.println(gson.toJson(target, listType));
		System.out.println(gson.toJson(target));
		
		List target2 = gson.fromJson(gson.toJson(target), listType);
		
		System.out.println(target2);
	}

	// File Read Json - FileReadJson
	public static void FileReadJson(String[] args) throws FileNotFoundException, IOException {
		// String json = org.apache.commons.io.IOUtils.toString(new
		// FileReader("/home/kishore/testJson.txt"));

		String json = IOUtils.toString(new InputStreamReader(JsonFileRead.class.getResourceAsStream("testJson.txt")));

		JSONObject jsonObject = new JSONObject(json);
		String name = jsonObject.getJSONObject("result").getString("name");

		System.out.println(name);
		return;
	}
}
