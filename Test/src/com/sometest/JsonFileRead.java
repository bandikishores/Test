package com.sometest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import lombok.Data;

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


@Data
class JsonTestClass {
    Boolean      name = new Boolean(true);
    String       test = "str";
    List<String> list = Arrays.asList("some");
}


public class JsonFileRead {

    // Temp Json - TempJsonJackson
    public static void TempJsonJackson(String[] args) throws FileNotFoundException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        String str = mapper.writeValueAsString(new JsonTestClass());
        System.out.println(str);
        System.out.println(mapper.readValue(str, JsonTestClass.class));
    }


    // Temp Json - TempJsonGson
    public static void TempJsonGson(String[] args) throws FileNotFoundException, IOException {


        Gson gson = new GsonBuilder().create();

        HashMap<String, JsonTestClass> map = new HashMap<>();
        map.put("a", new JsonTestClass());
        map.put("b", new JsonTestClass());

        List<JsonTestClass> list = new ArrayList<>();
        list.add(new JsonTestClass());
        String jsonString = gson.toJson(list);
        List<JsonTestClass> fromJsonList = gson.fromJson(jsonString, List.class);

        List<JsonTestClass> anotherJsonListUsingTypeToken =
                gson.fromJson(jsonString, new TypeToken<List<JsonTestClass>>() {}.getType());

        System.out.println(fromJsonList.get(0));
        System.out.println(anotherJsonListUsingTypeToken.get(0));

        // System.out.println(mapper.convertValue(new JsonTestClass(), Map.class));
    }

    // File Read Json Gson - FileReadJsonGson
    public static void FileReadJson(String[] args) throws FileNotFoundException, IOException {
        // String json = org.apache.commons.io.IOUtils.toString(new
        // FileReader("/home/kishore/testJson.txt"));

        String json = IOUtils.toString(new InputStreamReader(JsonFileRead.class.getResourceAsStream("testJson.txt")));

        JSONObject jsonObject = new JSONObject(json);
        String name = jsonObject.getJSONObject("result").getString("name");

        System.out.println(name);
        return;
    }

    // File Read Json Object Mapper - FileReadJsonJackson
    public static void main(String[] args) throws FileNotFoundException, IOException {

        // String json = IOUtils.toString(new
        // InputStreamReader(JsonFileRead.class.getResourceAsStream("testJson.txt")));
        String envPropertiesFilePath = ClassLoader.getSystemResource("testJson.yaml").getPath();
        System.out.println(envPropertiesFilePath);
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

        JsonTestClass jsonTestClass = objectMapper.readValue(new File(envPropertiesFilePath), JsonTestClass.class);


        System.out.println(jsonTestClass);
        return;
    }
}
