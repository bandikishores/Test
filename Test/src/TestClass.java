import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.velocity.app.event.ReferenceInsertionEventHandler;
import org.json.JSONObject;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
class SiteEntry {
    String url;

    public String url() {
        return url;
    }
}


class MyList extends ArrayList<SiteEntry> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    Map<String, Integer>      urlMap           = new HashMap<>();

    @Override
    public boolean add(SiteEntry object) {
        if (object == null) return false;

        int index;
        // Removes previous entry
        if (urlMap.containsKey(object.url())) {
            index = urlMap.get(object.url());
            super.remove(index);
        }

        // Adds New Entry at the bottom of the list
        super.add(object);
        urlMap.put(object.url(), indexOf(object));

        return true;
    }

    @Override
    public void add(int index, SiteEntry object) {
        if (object == null || index < 0 || index >= size()) return;

        // Adds entry at the specified index
        super.add(index, object);

        int size = size();
        SiteEntry temp;
        // Updates URL Map
        for (int i = index; i < size; i++) {
            temp = get(i);
            urlMap.put(temp.url(), i);
        }
    }

    public boolean addAll(SiteEntry[] entries) {
        if (entries == null) return false;

        int length = entries.length;
        for (int i = 0; i < length; i++)
            add(entries[i]);

        return true;
    }
}


class VelocityTypeHandler implements ReferenceInsertionEventHandler {

    private static final String delimiter = ",";

    @Override
    public Object referenceInsert(String reference, Object value) {
        if (value instanceof Collection<?>) {
            Iterator<?> itr = ((Collection<?>) value).iterator();
            StringBuilder buffer = new StringBuilder();
            while (itr.hasNext()) {
                buffer.append(String.valueOf(handleSpecial(itr.next()).toString()));
                if (itr.hasNext()) buffer.append(delimiter);
            }
            return buffer;
        }
        return handleSpecial(value);
    }

    private Object handleSpecial(Object in) {
        if (in instanceof String) {
            return "'" + in + "'";
        }
        return in;
    }
}


class AuditVelocityTools {

    private final JexlEngine jEngine = new JexlEngine();

    @SuppressWarnings({ "rawtypes", "unchecked"})
    public Collection map(Collection src, String objPath) {
        Collection output = new ArrayList();
        JexlContext jCtx = new MapContext();
        for (Object obj : src) {
            String evalExpr = "arg." + objPath;
            org.apache.commons.jexl2.Expression el =
                    (org.apache.commons.jexl2.Expression) jEngine.createExpression(evalExpr);
            jCtx.set("arg", obj);
            output.add(((org.apache.commons.jexl2.Expression) el).evaluate(jCtx));
        }
        return output;
    }

}


@Data
class VtaDTO {
    private String    url;
    private Integer[] adFormatGroups;
    private Timestamp transactionStartTime;
    private Timestamp transactionEndTime;
}


class Parent {
    public String abc = "1";

    public Parent() {
        System.out.println(abc);
        System.out.println("Parent");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Parent Post Construct");
    }

    public static void staticMethod() {
        System.out.println("Parent Static");
    }
}


class Child extends Parent {
    public String abc = "2";

    public void print() {
        System.out.println(abc);
        System.out.println(super.abc);
    }

    public Child() {
        System.out.println("Child");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Child Construct");
    }

    public static void staticMethod() {
        System.out.println("Child Static");
    }
}


class RecognizedActivity {

    public String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}


class RecognizedActivityList {

    private List<RecognizedActivity> list;

    public List<RecognizedActivity> getList() {
        return list;
    }

    public void setList(List<RecognizedActivity> list) {
        this.list = list;
    }
}


@Getter
enum MyEnum {
    ONE(1), TWO(2);

    private final int value;

    private MyEnum(int value) {
        this.value = value;
    }
}


class SuperSuperClass {

    double b = 1;
}


class SuperClass extends SuperSuperClass {
    int    a = 3;
    double b = 2;

    public void abc() {
        System.out.println("value: " + b);
    }

    public final void clearLocal() {

    }
}


@AllArgsConstructor
@NoArgsConstructor
class SomeCache {
    int cache = 0;
}


class MyClass extends SuperClass implements Cloneable {
    double                 b          = 3;
    SuperClass             superClass;

    ThreadLocal<SomeCache> localCache = new ThreadLocal<>();

    @Override
    public void abc() {
        System.out.println("value: " + b);
    }

    @Override
    public MyClass clone() throws CloneNotSupportedException {
        return (MyClass) super.clone();
    }

    public MyClass testClone() throws CloneNotSupportedException {
        return (MyClass) clone();
    }

    public void sum(List<? super Number> list) {
        // list.add(new Integer(3));
        // Object next = list.iterator().next();
        System.out.println("super");
    }

    public void sumExtends(List<? extends Number> list) {
        System.out.println("extends");
    }
}


interface First {
    public default void display() {
        System.out.println("Hi First");
    }

}


interface Second {
    public default void display() {
        System.out.println("Hi Second");
    }
}


class Third implements First, Second {
    public void display() {
        System.out.println("Hi Third");
    }
}


class InterfaceClass extends Third implements First, Second {
    @Override
    public void display() {
        // First.super.display();
        // Second.super.display();
        super.display();
    }
}


@AllArgsConstructor
class Order {
    Item item;

    public Item getItem() {
        return item;
    }
}


@AllArgsConstructor
class Item {
    Category category;

    public Category getCategory() {
        return category;
    }
}


@AllArgsConstructor
class Category {
    String categoryName;

    public String getCategoryName() {
        return categoryName;
    }
}


@Data
@NoArgsConstructor
@JsonRootName(value = "userValue")
class OutValue {
    public TempValue temp = new TempValue();
    // int value = 3;
}


class TempValue {
    int value = 3;

    @Override
    public int hashCode() {
        return 1;
    }
}


@Slf4j
public class TestClass {

    public void sumP() {
        System.out.println("sum");
    }

    class InnerClass {
        String abs;

        public void sum() {
            sumP();
            System.out.println("sum");
        }
    }

    @Data
    @JsonRootName("DistributionOrderActionParametersList")
    class DistributionOrderActionParametersList {
        private List<DistributionOrderActionParameters> elements;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class DistributionOrderActionParameters {
        private int    distributionOrderId;
        private int    companyId;
        private String tcDistributionOrderId;
        private String lastUpdatedDTTMHid = null;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Test {
        private String name;
        private int    value;
        private String emailId;
    }

    static int getCapacity(ArrayList<?> l) throws Exception {
        Field dataField = ArrayList.class.getDeclaredField("elementData");
        dataField.setAccessible(true);
        return ((Object[]) dataField.get(l)).length;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Team {
        List<Player> players; // with getter & setter
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Player {
        String name;   // with getter & setter
        int    number; // with getter & setter
    }

    @AllArgsConstructor
    @NoArgsConstructor
    static class TreeNode {
        int      val;
        TreeNode left;
        TreeNode right;
    }

    public static int pathSum(TreeNode root, int sum, int sumOfPaths, Stack<Integer> Paths) {
        int path = 0;
        if ((sumOfPaths + root.val) == sum) {
            System.out.println(Paths + " " + root.val);
            return 1;
        } else if (root.left == null && root.right == null) return 0;
        if (root.left != null) {
            Paths.push(root.val);
            path += pathSum(root.left, sum, sumOfPaths + root.val, Paths);
            Paths.pop();
            Stack<Integer> st = new Stack();
            st.push(root.val);
            path += pathSum(root.left, sum, root.val, st);
        }
        if (root.right != null) {
            Paths.push(root.val);
            path += pathSum(root.right, sum, sumOfPaths + root.val, Paths);
            Paths.pop();
            Stack<Integer> st = new Stack();
            st.push(root.val);
            path += pathSum(root.right, sum, root.val, st);
        }
        return path;
    }

    static class Data1 {
        public Data1(List<Float> list) {
            array = list;
        }

        @SerializedName("array")
        @Expose
        private List<Float> array = null;
    }

    public static ResponseEntity someMethod() {
        List<Integer> list = new ArrayList<>();
        return new ResponseEntity(list.stream().peek(System.out::println).collect(Collectors.toList()), HttpStatus.OK);
    }

    public static String[] arraySplit(String s, char separatorChar, boolean trim) {
        if (s == null) {
            return null;
        }
        int length = s.length();
        if (length == 0) {
            return new String[0];
        }
        ArrayList<String> list = new ArrayList<>();
        StringBuilder buff = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (c == separatorChar) {
                String e = buff.toString();
                list.add(trim ? e.trim() : e);
                buff.setLength(0);
            } else if (c == '\\' && i < length - 1) {
                buff.append(s.charAt(++i));
            } else {
                buff.append(c);
            }
        }
        String e = buff.toString();
        list.add(trim ? e.trim() : e);
        String[] array = new String[list.size()];
        list.toArray(array);
        return array;
    }

    public static int[] rotation(int[] a, int k) {

        int[] newArray = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            int newPosition = (i + k) % a.length;
            newArray[newPosition] = a[i];
        }
        return newArray;
    }

    public static class Sample {
        private String name = "Kishore";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static void handleException(Exception e, List<Class<? extends Exception>> acceptableExceptionNames) {
        boolean isRetryable = false;

        for (Class<?> acceptableExceptionName : acceptableExceptionNames) {
            if (acceptableExceptionName.isInstance(e)) {
                isRetryable = true;
                break;
            }
        }

        if (isRetryable) {
            // log retryable
        } else {
            // log error
        }
    }

    static String        value   = TestClass.value1;
    static String        value1  = "abc";
    static String        value2  = TestClass.value1;

    static AtomicInteger counter = new AtomicInteger();

    static class MyExceptionHandler implements UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println("caught");
            if (counter.get() == 3) {
                System.out.println("Reached Max. retries, exiting");
            } else {
                counter.incrementAndGet();
                new Thread(new MyTask()).start();
            }

        }
    }
    static class MyTask implements Runnable {
        @Override
        public void run() {
            try {
                Thread.currentThread().setUncaughtExceptionHandler(new MyExceptionHandler());
                System.out.println("slept");
                Thread.sleep(500);
                double d = 0 / 0;
            } catch (InterruptedException e) {}
        }
    }

    @Data
    @AllArgsConstructor
    static class SuccessfulAttempts {
        String name;
        int    successCount;
    }

    @Data
    @AllArgsConstructor
    static class TotalAttempts {
        String name;
        int    totalCount;
    }

    @Data
    @AllArgsConstructor
    static class PercentageSuccess {
        String name;
        float  percentage;
    }


    static class ResultsDTO implements Comparable<ResultsDTO> {

        private String sNumber;
        private String lpNumber;
        private String startDate;
        private String endDate;
        private String reason;

        @Override
        public int compareTo(ResultsDTO o) {
            if (o != null) {
                if (sNumber != null && o.sNumber != null && sNumber.compareTo(o.sNumber) != 0) {
                    return sNumber.compareTo(o.sNumber);
                } else if (lpNumber != null && o.lpNumber != null && lpNumber.compareTo(o.lpNumber) != 0) {
                    return lpNumber.compareTo(o.lpNumber);
                }
            }
            // This is Invalid, so you can decide what should the scenario be.
            return 0;
        }
    }

    @SuppressWarnings("unused")
    public static void main(String args[]) throws Exception {
        if (true) {
            List<Integer> list = new ArrayList<>();
            IntStream.range(0, 3).forEach(i -> list.add(i));
            list.parallelStream().forEach(System.out::println);
            return;
        }
        if (true) {
            MyList list = new MyList();
            list.add(new SiteEntry("abc4"));

            SiteEntry[] site = { new SiteEntry("abc"), new SiteEntry("abc1"), new SiteEntry("abc2")};

            list.addAll(site);

            list.add(0, new SiteEntry("abc5"));
            list.add(3, new SiteEntry("abc6"));
            list.add(5, new SiteEntry("abc7"));

            list.stream().forEach(System.out::println);

            return;
        }
        if (false) {
            Map<String, String> map1 = new HashMap<>();
            Map<String, String> map2 = new HashMap<>();
            map1.put("abc", "some");
            map1.put("def", "some1");
            map2.put("abc", "some2");
            map2.put("xyz", "some3");

            // map1.entrySet().forEach(entry ->
            // resultMap.compute(entry.getKey(), new HashSet(),
            // HashSet::add));

            Map<String, Set<String>> resultMap = new HashMap<>();

            Stream.of(map1, map2).flatMap(map -> map.entrySet().stream()).forEach(entry -> {
                resultMap.merge(entry.getKey(), Collections.singleton(entry.getValue()),
                        (v1, v2) -> Stream.concat(v1.stream(), v2.stream()).collect(Collectors.toSet()));
            });

            Stream.of(map1, map2).flatMap(map -> map.entrySet().stream()).collect(
                    Collectors.groupingBy(Entry::getKey, Collectors.mapping(Entry::getValue, Collectors.toSet())));

            resultMap.entrySet().forEach(entry -> {
                System.out.println(entry.getKey());
                entry.getValue().stream().forEach(value -> System.out.println("\t" + value));
            });

            return;
        }

        if (true) {
            String json = org.apache.commons.io.IOUtils.toString(new FileReader("/home/kishore/testJson.txt"));

            JSONObject jsonObject = new JSONObject(json);
            String name = jsonObject.getJSONObject("result").getString("name");

            System.out.println(name);
            return;
        }
        if (false) {
            // int[] arr = { 10, 11, 1, 2, 12, 13, 14};
            int[] arr = { 1, 3, 2, 6, 5, 7, 9, 8, 10, 8, 11};
            Integer firstMax = null;
            Integer overallMax = null;

            for (int i = 1; i < arr.length - 1; i++) {
                int currentElement = arr[i];
                if (firstMax == null) {
                    if (overallMax == null) {
                        firstMax = currentElement;
                    } else if (overallMax != null && currentElement > overallMax) {
                        firstMax = currentElement;
                    }
                }
                if (overallMax == null || currentElement > overallMax) {
                    overallMax = currentElement;
                }
                if (firstMax != null && currentElement < firstMax) {
                    // We found a smaller element, so all max found so far
                    // is
                    // useless. Start fresh.
                    firstMax = null;
                }
            }

            System.out.println(firstMax);

            return;
        }
        if (false) {
            TreeNode root = new TreeNode();
            root.val = 10;
            root.left = new TreeNode(5, new TreeNode(3, new TreeNode(3, null, null), new TreeNode(-2, null, null)),
                    new TreeNode(2, null, new TreeNode(1, null, null)));
            root.right = new TreeNode(-3, null, new TreeNode(11, null, null));

            System.out.println(pathSum(root, 8, 0, new Stack<>()));
            return;
        }

        if (true) {
            Map<String, List<Integer>> map = new HashMap<>();
            map.put("a", Arrays.asList(5, 3, 7));
            map.put("b", Arrays.asList(1, 5));
            map.put("c", Arrays.asList(45, 2));
            map.put("d", Arrays.asList(4, 6, 2, 34));

            map.entrySet().stream().collect(Collectors.toMap(Entry::getKey, entry -> {
                return Collections.max(entry.getValue());
            }));

            Integer intmax = map.values().stream().flatMap(list -> list.stream()).max(Integer::compareTo).orElse(0);

            return;
        }
        if (true) {
            int totalSize = 32768;
            int filterValue = 1280;
            int[] array = new int[totalSize];
            Random rnd = new Random(0);
            int loopCount = 10000;
            int value;

            for (int i = 0; i < totalSize; i++) {
                array[i] = rnd.nextInt() % 2560;
                // array[i] = i;
            }

            long start = System.nanoTime();
            long sum = 0;

            for (int j = 0; j < loopCount; j++) {
                for (int c = 0; c < totalSize; ++c) {
                    sum = sum + array[c] >= filterValue ? array[c] : 0;
                }
            }

            long total = System.nanoTime() - start;
            System.out.printf("Conditional Operator Time : %d ns, (%f sec) %n", total, total / Math.pow(10, 9));

            start = System.nanoTime();
            sum = 0;

            for (int j = 0; j < loopCount; j++) {
                for (int c = 0; c < totalSize; ++c) {
                    if (array[c] >= filterValue) {
                        sum = sum + array[c];
                    } else {
                        sum = sum + 0;
                    }
                }
            }

            total = System.nanoTime() - start;
            System.out.printf("Branch Statement Time : %d ns, (%f sec) %n", total, total / Math.pow(10, 9));
            return;
        }

        if (false) {
            Map<TempValue, TempValue> myMap = new HashMap<>();
            List<Thread> listOfThreads = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Thread thread = new Thread(() -> {
                    for (int j = 0; j < 3000; j++) {
                        TempValue key = new TempValue();
                        myMap.put(key, key);
                    }
                });
                thread.start();
                listOfThreads.add(thread);
            }
            for (Thread thread : listOfThreads) {
                thread.join();
            }
            System.out.println("Count should be 30000, actual is : " + myMap.size());

        }
        if (false) {
            TestClass testClass = new TestClass();
            OutValue value = new OutValue();
            ObjectMapper mapper = new ObjectMapper();

            mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
            mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
            mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);

            // Object to JSON in String
            DistributionOrderActionParametersList distributionOrderActionParametersList =
                    testClass.new DistributionOrderActionParametersList();
            distributionOrderActionParametersList.setElements(Arrays.asList(
                    testClass.new DistributionOrderActionParameters(1000703, 1403, "F99WAVTSTLTLNORN0022", null)));
            String jsonInString = mapper.writeValueAsString(distributionOrderActionParametersList);
            System.out.println(jsonInString);

            if (1 == 1) {
                System.out.println("Exiting Main thread");
                return;
            }

            Integer v1 = null;
            Integer v2 = new Integer(1);
            if (v1 != v2) {
                System.out.println("true " + String.valueOf(v1) + " " + String.valueOf(v2));
            } else
                System.out.println("false " + String.valueOf(v1) + " " + String.valueOf(v2));
            return;
        }

        if (false) {
            ExpressionParser parser = new SpelExpressionParser();
            StandardEvaluationContext SEContext = new StandardEvaluationContext();
            Set<RegionShopMap> regionShopMapSet = new HashSet<>();
            regionShopMapSet.add(new RegionShopMap(1, "2"));
            regionShopMapSet.add(new RegionShopMap(3, "4"));

            SEContext.setVariable("regionShopMapSet", regionShopMapSet);
            Expression exp = parser.parseExpression("#regionShopMapSet[1].regionId");
            System.out.println(exp.getValue(SEContext));
            return;
        }

        if (false) {

            Runtime rt = Runtime.getRuntime();
            long totalMem = rt.totalMemory();
            long maxMem = rt.maxMemory();
            long freeMem = rt.freeMemory();
            double megs = 1048576.0;

            System.out.println("Total Memory: " + totalMem + " (" + (totalMem / megs) + " MiB)");
            System.out.println("Max Memory:   " + maxMem + " (" + (maxMem / megs) + " MiB)");
            System.out.println("Free Memory:  " + freeMem + " (" + (freeMem / megs) + " MiB)");

            if (1 == 1) return;
            // al.add(3);
            // System.out.println(al);

            // MyClass myClass = new MyClass();
            // myClass.sum(Arrays.asList(new Object()));
            // myClass.sumExtends(Arrays.asList(3));
            /*
             * SomeCache someCache = new SomeCache(23);
             * myClass.localCache.set(someCache);
             * System.out.println(myClass.localCache.get());
             * myClass.clearLocal();
             * System.out.println(myClass.localCache.get());
             */
        }
        if (true) {

        }
        if (false) {
            URIBuilder uriB =
                    new URIBuilder("http://i.epetbar.com/upload/app/epettg/app-epetmalltuiguang1-release.apk?abx=1");
            uriB.setScheme(null);
            String url = "http://i.epetbar.com/upload/app/epettg/app-epetmalltuiguang1-release.apk?abx=1";
            if (StringUtils.isNotBlank(url)) {
                String urlWithNoProtocol = new URIBuilder(url).setScheme(null).toString();
                if (urlWithNoProtocol.startsWith("//"))
                    System.out.println(urlWithNoProtocol.substring(2));
                else
                    System.out.println(urlWithNoProtocol);
            }
            // System.out.println(new URI(uri).toString());

            MyClass myClass = new MyClass();
            myClass.a = 5;
            myClass.b = 3.0;
            SuperClass superClass = new SuperClass();
            superClass.a = 56;
            myClass.superClass = superClass;
            MyClass deepCopy = TestClass.deepCopy(myClass, MyClass.class);
            deepCopy.superClass.a = 1000;
            // MyClass clone = myClass.testClone();
            // clone.a = 6;
            System.out.println(myClass.a);
            System.out.println(myClass.superClass.a);
            System.out.println(deepCopy.a);
            System.out.println(deepCopy.superClass.a);
            // System.out.println(clone.a + " " + clone.b);
            // System.out.println(clone.superClass.a);
        }
        if (false) {
            long start = System.currentTimeMillis();
            PoolingHttpClientConnectionManager poolingHttpClientConnectionManager =
                    new PoolingHttpClientConnectionManager();
            poolingHttpClientConnectionManager.setMaxTotal(20);
            poolingHttpClientConnectionManager.setDefaultMaxPerRoute(10);

            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000)
                    .setConnectionRequestTimeout(3000).setSocketTimeout(10000).build();
            CloseableHttpClient httpClient =
                    HttpClientBuilder.create().disableAutomaticRetries().setDefaultRequestConfig(requestConfig)
                            .setConnectionManager(poolingHttpClientConnectionManager).disableRedirectHandling().build();

            String url = "http://lnk8.cn/00s4Yt?idfa=&impId=$IMP_ID&ip=$USER_IP&custom=";
            HttpGet httpGet = new HttpGet(url);
            try {

                String android =
                        "Mozilla/5.0 (Linux; Android 4.4.2; Nexus 5 Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.99 Mobile Safari/537.36";
                String ios =
                        "Mozilla/5.0 (iPhone; CPU iPhone OS 9_2_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13D15 Safari/601.1";
                httpGet.setHeader(HttpHeaders.USER_AGENT, ios);
                CloseableHttpResponse response = httpClient.execute(httpGet);

                System.out.println("response " + response);
                System.out.println("status " + response.getStatusLine().getStatusCode());
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                httpGet.releaseConnection();
            }
            System.out.println("Total Time taken : " + (System.currentTimeMillis() - start));
        }

        String url = "http://itunes.apple.com/gb/app/spottd/id469818594?mt=8";

        if (true) {
            return;
        }

        URI uri1 = new URI(
                "https://168894.measurementapi.com/serve?action=click&publisher_id=168894&site_id=102712&agency_id=608&ref_id=$IMP_ID&mac_address_sha1=$O1&odin=$SO1&ios_ifa=$IDA&url=https%3A%2F%2Fitunes.apple.com%2Fus%2Fapp%2Fexpedia-hotels-flights-cars%2Fid427916203%3Fpt%3D247920%26ct%3D%7Btruncate(%7Bmy_campaign%7D%2C40)%7D%26mt%3D8&my_placement=GENERIC-DISPLAY&my_partner=Inmobi-CA&my_adgroup=MAPP&my_site=GENERIC&my_ad=OLA.CA.MAPP.Inmobi.iPhone.Non-Incent.GENERIC.A070_A064_A516307&my_campaign=Inmobi.CA&sub_publisher=$BLINDED_SITE_ID&attr_core=1");
        // URI uri2 = new
        // URI("https://168894.measurementapi.com/serve?action=click&publisher_id=168894&site_id=102712&agency_id=608&ref_id=$IMP_ID&mac_address_sha1=$O1&odin=$SO1&ios_ifa=$IDA&url=https://itunes.apple.com/us/app/expedia-hotels-flights-cars/id427916203?pt=247920&ct={truncate({my_campaign},40)}&mt=8&my_placement=GENERIC-DISPLAY&my_partner=Inmobi-CA&my_adgroup=MAPP&my_site=GENERIC&my_ad=OLA.CA.MAPP.Inmobi.iPhone.Non-Incent.GENERIC.A070_A064_A516307&my_campaign=Inmobi.CA&sub_publisher=$BLINDED_SITE_ID&attr_core=1&response_format=json");

        String s1 =
                "https://168894.measurementapi.com/serve?action=click&publisher_id=168894&site_id=102712&agency_id=608&ref_id=$IMP_ID&mac_address_sha1=$O1&odin=$SO1&ios_ifa=$IDA&url=https%3A%2F%2Fitunes.apple.com%2Fus%2Fapp%2Fexpedia-hotels-flights-cars%2Fid427916203%3Fpt%3D247920%26ct%3D%7Btruncate(%7Bmy_campaign%7D%2C40)%7D%26mt%3D8&my_placement=GENERIC-DISPLAY&my_partner=Inmobi-CA&my_adgroup=MAPP&my_site=GENERIC&my_ad=OLA.CA.MAPP.Inmobi.iPhone.Non-Incent.GENERIC.A070_A064_A516307&my_campaign=Inmobi.CA&sub_publisher=$BLINDED_SITE_ID&attr_core=1";
        String s2 =
                "https://168894.measurementapi.com/serve?action=click&publisher_id=168894&site_id=102712&agency_id=608&ref_id=$IMP_ID&mac_address_sha1=$O1&odin=$SO1&ios_ifa=$IDA&url=https://itunes.apple.com/us/app/expedia-hotels-flights-cars/id427916203?pt=247920&ct={truncate({my_campaign},40)}&mt=8&my_placement=GENERIC-DISPLAY&my_partner=Inmobi-CA&my_adgroup=MAPP&my_site=GENERIC&my_ad=OLA.CA.MAPP.Inmobi.iPhone.Non-Incent.GENERIC.A070_A064_A516307&my_campaign=Inmobi.CA&sub_publisher=$BLINDED_SITE_ID&attr_core=1&response_format=json";

        System.out.println(new URI(addOrReplaceUrlParameter(s1, "")));

        System.out.println(new URI(addOrReplaceUrlParameter(s2, "")));
        if (1 == 1) return;
        int[] intArray = { 4, 2, 1, 6, 2, 5, 1, 5, 5}; // 3

        if (1 == 1) return;
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        sc.close();

        if (1 == 1) return;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate = sdf.parse("2016-07-24 13:04:34");// DateUtils.addSeconds(DateUtils.truncate(DateUtils.addDays(new
                                                         // Date(), -1),
                                                         // Calendar.DAY_OF_MONTH),
                                                         // -1);
        Date currentDate = new Date();

        URI uri = new URI("/test/path/random?l8u=3d&sfd=d&sdf=32");
        String path = uri.getPath().replaceAll("/", ".").trim();
        if (path.startsWith(".")) {
            path = path.substring(1, path.length());
        }
        System.out.println(path);
        if (1 == 1) return;
        List<String> strList = new ArrayList<>(Arrays.asList("zzzz", "ab sdg", "abcd de", "abcd ded jsdj"));
        Collections.sort(strList);
        System.out.println(strList);

    }

    protected static void finiteRecursion(int finiteCount) {
        if (finiteCount > 0) {
            finiteRecursion(--finiteCount);
        }
    }

    protected static void infiniteRecursion() {
        infiniteRecursion();
        return;
    }

    static int   TOTAL_SIZE = 5;
    static int   CHUNK_SIZE = 3;
    static int[] wordArray  = new int[TOTAL_SIZE];

    private static void printValues() {
        for (int i = 0; i < TOTAL_SIZE; i++) {
            wordArray[i] = i;
        }

        List<Integer> newList = new ArrayList<>();
        int[] indexArray = new int[TOTAL_SIZE];

        printAllValues(newList, indexArray);
    }

    private static void printAllValues(List<Integer> newList, int[] indexArray) {
        if (newList.size() == CHUNK_SIZE) {
            newList.stream().forEach(System.out::print);
            System.out.println(""); // new line
            return;
        }

        for (int i = 0; i < TOTAL_SIZE; i++) {
            if (indexArray[i] != 1) {
                indexArray[i] = 1;
                newList.add(wordArray[i]);
                printAllValues(newList, indexArray);
                newList.remove((Integer) wordArray[i]);
                indexArray[i] = 0;
            }
        }

    }

    private static void change(int[] intarr) {
        intarr[0] = 10;
        // System.out.println(intarr[0]);
    }

    public static <T extends Object> T deepCopy(T object, Class<T> klass) {
        if (object != null && object.getClass().equals(klass)) {
            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(object, klass);
            return gson.fromJson(json, klass);
        }
        return null;
    }

    public <T> void sum(T number) {
        System.out.println("number");

    }

    public static class IntWrapper {
        String value;

        public IntWrapper(String value) {
            this.value = value;
            new TestClass().sum(1);
        }

        public String getValue() {
            return this.value;
        }
    }

    /**
     * @param landingUrl
     * @param urlParams : & Seperated params to be added or replaced ex: key1=val1&key2=val2&key3=val3
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String addOrReplaceUrlParameter(String landingUrl, String urlParams) {

        if (StringUtils.isEmpty(landingUrl) || StringUtils.isEmpty(urlParams)) {
            return landingUrl;
        }

        try {
            if (StringUtils.equals(URLEncoder.encode(landingUrl, "UTF-8"), landingUrl)) {
                return landingUrl;
            }

            Map<String, String> initialKeyValueMap = new LinkedHashMap<String, String>(); // HashMap<String,
                                                                                          // String>();
            List<NameValuePair> initialKeyValPairList = URLEncodedUtils.parse(new URI(landingUrl), "UTF-8");
            for (NameValuePair nameValuePair : initialKeyValPairList) {
                if (StringUtils.isNotEmpty(nameValuePair.getName())) {
                    initialKeyValueMap.put(nameValuePair.getName(), nameValuePair.getValue());
                }
            }

            String[] ampSeperatedKeyValuesArr = urlParams.split("&");
            for (String keyValue : ampSeperatedKeyValuesArr) {
                if (StringUtils.isNotEmpty(keyValue)) {
                    String[] keyValPair = keyValue.split("=");
                    if (keyValPair != null && keyValPair.length == 2) {
                        String key = keyValPair[0];
                        String value = keyValPair[1];
                        initialKeyValueMap.put(key, value);
                    }
                }
            }

            int index = landingUrl.indexOf("?");
            if (index == -1) {
                return landingUrl;
            }
            String baseUrl = landingUrl.substring(0, index);

            StringBuilder newLandingUrl = new StringBuilder();
            newLandingUrl.append(baseUrl);
            newLandingUrl.append("?");
            int count = 0;
            for (Entry<String, String> entrySet : initialKeyValueMap.entrySet()) {
                count++;
                if (count > 1) {
                    newLandingUrl.append("&");
                }
                newLandingUrl.append(entrySet.getKey()).append("=").append(entrySet.getValue());
            }
            return newLandingUrl.toString();

        } catch (URISyntaxException e) {
            // Do Nothing since this will never happen as the url is already
            // validated
            return landingUrl;
        } catch (UnsupportedEncodingException e) {
            // Do Nothing since this will never happen as the url is already
            // validated
            return landingUrl;
        }
    }

    @Data
    @AllArgsConstructor
    static class RegionShopMap {

        private Integer regionId;
        private String  shopId;
    }

    private static String convert(int number, int base) {

        return Integer.toString(number, base);
    }

    private static void parseAndPrint(String str, int[] indexArr, int i) {
        if (i == -1) return;
        indexArr[i] = 0;
        printString(str, indexArr);
        parseAndPrint(str, indexArr, i - 1);

        indexArr[i] = 1;
        printString(str, indexArr);
        parseAndPrint(str, indexArr, i - 1);

    }

    private static void printString(String str, int[] indexArr) {
        for (int i = 0; i < indexArr.length; i++) {
            if (indexArr[i] == 1) System.out.print(str.charAt(i));
        }
        System.out.println();
    }

    private static void finalTest(String[] args) {
        // TODO Auto-generated method stub

    }

    private static boolean isVTAChanged(String vta, JsonObject vtaJsonObject) {
        if (StringUtils.isNotBlank(vta)) {
            if ((vtaJsonObject == null || !vtaJsonObject.isJsonObject())) {
                return true;
            } else {
                if (!StringUtils.equals(vta, vtaJsonObject.toString())) {
                    return true;
                }
                if (!StringUtils.equals(vta, vtaJsonObject.toString())) {
                    return true;
                }
            }
        } else if (vtaJsonObject != null && vtaJsonObject.isJsonObject()) {
            return true;
        }
        return false;
    }

    private static JsonElement getAdFormatJsonArray(List<Integer> adGroupFormats) {
        Type listType = new TypeToken<List<Integer>>() {}.getType();
        String jsonArrayStr = new Gson().toJson(adGroupFormats, listType);
        JsonArray jsonObject = new JsonParser().parse(jsonArrayStr).getAsJsonArray();
        return jsonObject;
    }

    // performance
    public static void performance(String args[]) throws IOException, InterruptedException {
        List<StringBuffer> strList = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            String s = "sampleTest";
            for (int j = 0; j < 25; j++) {
                System.out.println(j);
                s += s;
            }
            StringBuffer str = new StringBuffer(s);
        }
        Thread.sleep(10000);
    }
}
