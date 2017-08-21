package com.sometest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.sometest.JsonFileReadTest.MyTestClass;

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
class JsonTestClass<T> {
    Boolean active = new Boolean(true);
    Integer id     = 1;
    // String test = "str";
    List<T> list;
}


public class JsonFileReadTest {

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

        String date = "2017-01-18T07:20:00Z";
        Date readDate = mapper.readValue(date, Date.class);
        System.out.println(date);
    }

    static class BooleanTypeAdapter implements JsonDeserializer<Boolean> {
        @Override
        public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();
            if (jsonPrimitive.isBoolean()) {
                return jsonPrimitive.getAsBoolean();
            } else {
                return jsonPrimitive.getAsInt() == 0 ? false : true;
            }
        }
    }

    @Data
    static class MyTestClass {

        private String  abc    = "kishore";
        private String  def    = "bandi";
        private Integer myInt  = 1;
        private Integer myInt2 = 2;
    }

    static @interface MyExclude {

    }

    static class CustomExclusionStrategy implements ExclusionStrategy {

        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            return f.getAnnotations().contains(MyExclude.class) ? true : false;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }

    }

    // mainTemp
    public static void mainTemp(String[] args) throws FileNotFoundException, IOException {

        MyTestClass myTestClass = new MyTestClass();
        Gson gsonExclude = new GsonBuilder().setExclusionStrategies(new CustomExclusionStrategy()).create();
        System.out.println(gsonExclude.toJson(myTestClass));

        Map<String, String> map = new HashMap<>();
        map.put("a", "b");
        map.put("a2", "b4");
        map.put("1", "b3");
        map.put("23", "b5");
        System.out.println(new GsonBuilder().create().toJson(map));

        Gson gson = new GsonBuilder().registerTypeAdapter(Boolean.class, new BooleanTypeAdapter()).create();



        System.out.println(gson.fromJson("{id:12345,active:1}", JsonTestClass.class).getActive());
        System.out.println(gson.fromJson("{id:12345,active:0}", JsonTestClass.class).getActive());
        System.out.println(gson.fromJson("{id:12345,active:false}", JsonTestClass.class).getActive());
        System.out.println(gson.fromJson("{id:12345,active:true}", JsonTestClass.class).getActive());
    }

    public void bla(JsonTestClass<MyTestClass> adsfasfsad) {

    }


    static String NAME_PREFIX = "class ";

    private static String getClassName(Type type) {
        String fullName = type.toString();
        if (fullName.startsWith(NAME_PREFIX)) return fullName.substring(NAME_PREFIX.length());
        return fullName;
    }

    // Temp Json - TempJsonGson
    public static void TempJsonGson(String[] args) throws FileNotFoundException, IOException, NoSuchMethodException,
            SecurityException, ClassNotFoundException {
        Gson gson = new GsonBuilder().create();

        Method method = JsonFileReadTest.class.getMethod("bla", JsonTestClass.class);

        Type type = method.getGenericParameterTypes()[0];
        /*
        
        System.out.println(Class.forName(getClassName(type)));
        System.out.println(Class.forName(getClassName(innerType)));*/

        Type innerType = ((ParameterizedType) type).getActualTypeArguments()[0];
        JsonTestClass jsonTestClass = new JsonTestClass();
        jsonTestClass.setList(Arrays.asList(new MyTestClass(), new MyTestClass()));
        String jsonString = gson.toJson(jsonTestClass);
        
        ObjectMapper mapper = new ObjectMapper();
        JsonTestClass<MyTestClass> jsonTestClassConverted = mapper.readValue(jsonString, JsonTestClass.class);
        
        // JsonTestClass<MyTestClass> jsonTestClassConverted = gson.fromJson(jsonString, type);
        /* JsonTestClass<MyTestClass> jsonTestClassConverted =
                gson.fromJson(jsonString, new TypeToken<JsonTestClass<MyTestClass>>() {}.getType());
        */

        /*JsonTestClass<MyTestClass> jsonTestClassConverted =
                gson.fromJson(jsonString, TypeToken.getParameterized(type, innerType).getType());*/
        System.out.println("Conversion successful ");

        for (MyTestClass myTestClass : jsonTestClassConverted.getList()) {
            System.out.println(myTestClass);
        }

        /*List<JsonTestClass> anotherJsonListUsingTypeToken =
                gson.fromJson(jsonString, new TypeToken<List<JsonTestClass>>() {}.getType());
        
        System.out.println(fromJsonList.get(0));
        System.out.println(anotherJsonListUsingTypeToken.get(0));*/

        // System.out.println(mapper.convertValue(new JsonTestClass(), Map.class));
    }

    // File Read Json Gson - FileReadJsonGson
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // String json = org.apache.commons.io.IOUtils.toString(new
        // FileReader("/home/kishore/testJson.txt"));
        File file = new File(ClassLoader.getSystemResource("testJson.txt").getPath());
        
        String json =
                IOUtils.toString(new FileInputStream(file));

        JSONObject jsonObject = new JSONObject(json);
        String name = jsonObject.getJSONObject("result").getString("name");

        System.out.println(name);
        return;
    }

    // File Read Json Object Mapper - FileReadJsonJackson
    public static void FileReadJsonJackson(String[] args) throws FileNotFoundException, IOException {

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
