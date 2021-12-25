import static staticpack.TupleCollector.toMap;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.json.simple.JSONObject;
import org.slf4j.helpers.MessageFormatter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import scala.Tuple2;

@Data
class Interval {
	int start;
	int end;

	Interval() {
		start = 0;
		end = 0;
	}

	Interval(int s, int e) {
		start = s;
		end = e;
	}
}
 
class Dog extends Animal {
	public void myPrint() {
		print();
	}
}

class Animal {
	protected void print() {
		System.out.println("Animal");
	}
}

@Slf4j
public class TestClass {

	public static Animal fun(Dog asn) {
		return null;
	}

	static interface IE<T extends Exception> {

	}

	static class Manager {

		Map<Class<? extends Exception>, IE<? extends Exception>> map = new HashMap<>();

		public <T extends Exception> void register(Class<T> klass, IE<T> obj) {
			map.put(klass, obj);
		}

	}

	public static void recursion(int value) {
		if (value == 17723) {
			return;
		}
		recursion(value + 1);
	}

	static Queue<String> taskQueue = new LinkedList<>();
	static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);

	@Builder
	@ToString
	public static class MyTestClass {
		@Default
		private Optional<String> str = Optional.of("myClass");
	}

	interface my1 {
		default public void print() {
			System.out.println("my1");
		}
	}

	interface my2 {
		default public void print() {
			System.out.println("my2");
		}
	}

	public static class MyTestClassExtend implements my1, my2 {

		@Override
		public void print() {
			my1.super.print();
		}

	}

	@Data
	@JsonIgnoreProperties(value = { "foo" }, allowGetters = true)
	static class Parent {
		// @Getter(onMethod=@__(@JsonIgnore))
		String foo;
		String bar;
	}

	@Data
	@ToString(callSuper = true)
	// @JsonIgnoreProperties(value = { "alice" }, allowGetters = true)
	static class Child extends Parent {
		@Getter(onMethod = @__(@JsonIgnore))
		String alice;
		String bob;
	}
	
	static class SxtArrayList03<E> {
	    private Object[] elementData;
	    private int size;

	    private static final int DEFAULT_CAPACITY = 10;

	    public SxtArrayList03() {
	        elementData = new Object[DEFAULT_CAPACITY];
	    }

	    public SxtArrayList03(int capacity) {
	        elementData = new Object[capacity];
	    }

	    public void add(E element) {
	        if(size == elementData.length) {
	        Object[] newArray = new Object[elementData.length + (elementData.length>>1)]; //10 + 10/2
	        System.arraycopy(elementData, 0, newArray, 0, elementData.length);   
	        elementData = newArray;
	      }
	        elementData[size++] = element;
	    }

	    @Override
	    public String toString() {

	        StringBuilder sb = new StringBuilder();
	        sb.append("[");
	        for(int i = 0; i < size; i++) {
	            sb.append(elementData[i] + ",");
	        }

	        sb.setCharAt(sb.length() - 1, ']'); 
	        return sb.toString();
	    }


	}
	

	@interface PravaliAnnotation {
		public boolean printKishore() default false;
	}

	
	@PravaliAnnotation(printKishore = true)
	static class Employee {
		private String name = "Pravali";
	}
	@PravaliAnnotation(printKishore = false)
	static class Employee1 {
		private String name = "Pravali";
	}

// Mix in - 
// TestClass.Child(super=TestClass.Parent(foo=null, bar=a), alice=a, bob=a)
// TestClass.Child(super=TestClass.Parent(foo=a, bar=a), alice=null, bob=a)
	public static void main(String[] args) throws InterruptedException, JsonProcessingException, IOException {
		ImmutableList<String> possibleTexts = ImmutableList.of("aa", "bbbbbb"); 
		final List<String> mutableList = new ArrayList<>(possibleTexts);
		Collections.sort(mutableList, (s1, s2) -> Math.abs(s1.length() - s2.length()));
	System.out.println(mutableList);
		
		for (String value : possibleTexts) {
			System.out.println(value);
		}
		
		if (true) return;
		
		double sum = 0;
		int n = 0, y = 0, count = 0, x = 1;
		double[] a = new double[] { 1, 0, 1 };
		double[] b = new double[a.length];
		List<double[]> mylist = new ArrayList<>();
		double[][] c = new double[][] { { 0, 0.66, 0.66 }, { 0.66, 0, 0.66 }, { 0, 0.66, 0 } };
		mylist.add(0, a);

		while (n >= 0 && n < 4) {
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < a.length; j++) {
					sum = sum + (c[i][j] * a[j]);
					if (j == a.length - 1) {
						if (sum > 0)
							sum = 1;
						else
							sum = -1;
						b[y] = sum;
						y++;
						count++;
						sum = 0;
					}

					if (count == a.length) {
						mylist.add(x, b);
						x++;
						y = 0;
						count = 0;
						for (int k = 0; k < a.length; k++) {
							a[k] = b[k];
						}
					}
				}
			n++;
		}

		for (int i = 0; i < mylist.size(); i++)
			for (int j = 0; j < a.length; j++) {
				System.out.print(mylist.get(i)[j] + ",");
			}
		if (true) {
			return;
		}
		System.out.println(MessageFormatter.arrayFormat("My string with a {} and another one {}", new Object[] {"s", "d"}).getMessage());
		if(true) return;
		Employee em = new Employee();
		Employee1 em1 = new Employee1();
		
		
		if(true) return;
		
		String a1 = new String("abd");
		a1 = a1.intern();
		String b1 = "abd";
		System.out.println(a1 == b1);

		
		if(true) return;
		
		InputStream inputStream = null;
	    inputStream = new FileInputStream("D:\\enlistments\\Griffin\\QLogs\\QLocalCache.1.log");
	    byte[] buffer = new byte[8192];
	    int bytesRead;
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    Base64OutputStream output64 = new Base64OutputStream(output, true);
	    
	    try {
	        while ((bytesRead = inputStream.read(buffer)) != -1) {
	            output64.write(buffer, 0, bytesRead);
	            //Here I am getting OutOfMemory exception
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    output64.close();

	    String attachedFile = output.toString();
	    
		if(true) return;
		
		int valueLength = 1000;
		List<Integer> myList = new Vector<>();
		for (int i = 0; i < valueLength; i++) {
			myList.add(i);
		} 
		new Thread(() -> {
			try {
				for(int value : myList) {
					if (value % 3 == 0) {
						Thread.sleep(10);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}).start();

		new Thread(() -> {
			try {
				for (int i = valueLength; i > 0; i--) {
					if (i % 3 == 0) {
						Thread.sleep(5);
						myList.remove(i);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
		;

		if (true)
			return;
		long num = 1;
		LocalDate.now().plusDays(num);

		if (true)
			return;

		JSONObject obj = new JSONObject();
		((Map<String, String>) obj).put("foo", "bar");
		System.out.println(obj.toJSONString());

		if (true)
			return;
		ObjectMapper om = new ObjectMapper();// .addMixIn(Child.class, Parent.class);
		;
		Child ch = new Child();
		ch.alice = "a";
		ch.bob = "a";
		ch.foo = "a";
		ch.bar = "a";
		System.out.println(om.writeValueAsString(ch));

		String hson = "{\"foo\":\"a\",\"bar\":\"a\",\"alice\":\"a\",\"bob\":\"a\"}";
		System.out.println(om.readValue(hson, Child.class));

		if (true)
			return;

		List<Tuple2<String, String>> asList = new ArrayList<>();
		asList.add(new Tuple2<String, String>("a", "1"));
		asList.add(new Tuple2<String, String>("b", "2"));
		asList.add(new Tuple2<String, String>("c", "3"));

		Stream<Tuple2<String, String>> myStream = asList.stream();
		Map<String, String> collect = myStream.collect(toMap());

		for (Entry<String, String> entry : collect.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}

		if (true)
			return;

		// byte[] bytes = Files.toByteArray(new
		// File("C:\\Users\\kibandi\\Downloads\\users.txt.tef"));
		// String content = new String(bytes, StandardCharsets.UTF_8);
		// System.out.println(content);
		String content = "{\"CipherBlockSize\":65632,\"EncryptedPrivateInfo\":\"MIIKYQYJKoZIhvcNAQcDoIIKUjCCCk4CAQAxgcYwgcMCAQAwLDAYMRYwFAYDVQQDEw1NYXJzLVJvb3QtREVWAhArUJZjW8+bpUHmb9buePewMA0GCSqGSIb3DQEBBzAABIGAfLMQdiL81tq2c60ASLBGBpmStEkDUrfF/IwbiqSyqhni72y0AyYMmktC1F/f1rGsci9CUs8rYzR0wNz2OYRF36aafC7vtGmo5YHEbLXimf7XC4J5WN5ytfNAt2ID51b4nempkqEUirOvIYfWe+n4s+29XitNaBZOHSw49i3EK0gwggl+BgkqhkiG9w0BBwEwHQYJYIZIAWUDBAEqBBD9BXAJV/G3ur5hj2sjjPEtgIIJUAoURPB42jOx4WM/tTPeyoccpvNSdh6MGp4BH66wxW9wC73rao9T3vxw7mpIKDu542lKGFARGbpLeXdWg1qKkkcy+psFfTkFMVInD2cRekZgwUhqmFGQNEuYWYG95VkzT0DpC/431qy+kx95oPUNJaSentSH5xgGVjf4K14C94VjnqoNEgUt0P4MuEGpIJlzopVvU47utMYgwlOArkKWNivfPZxLzGWv4iHqZg3tFMAvapJNic+dIlTa+9Likx0QnUZOdNPcNAYLzAk4+XdY8P2RaBPc1CSFVPW4x0tHKMHnCqMn4YuGL5Jqz0HLrB/yBD84mT+G0CQ/Uv+pA5IXpSask8EJWpNbk2rdGF5yeLf9F4yRAkd+9JBCDlL7YbeD0VsGB3erabct+l3ezqgo0Zx137VmCK4t7QwR4c80DDFZdhwVewtnnLuyUjCbL8aDjG+xO4SGv1kvEUpg/BETrk4JBpE/rO3YpWDDo7zyMMxuFv8PTQOS65HvOUq1OS7Qe1g2AQw3PX2E10Worje0Sd5qxLxC3KzoEg8mJ0RAWvxK+NQa8n0o43qzg0WLyNaSiaoxP/UDgVq0N253o8Oa6Aklf99juO9teNEJSrF7jhdK57Rajv4rAIu9e+D9dT1URDXDj0PEWnB5gx0XOp7mVWqdU6D2ew4DuqLLXIRZ24hIlBaVflN52q/lbW4jDb6YGBb3pRkIew6o+hpsfgYQl/236Z6u1Qp8CTY0UVmq8YRRc4q/nfuIvyPu4P77jPMUcHp26P56awzXI+Vqnhs2NWJ9UYajPJGuQ9QXRHvlB6NbgNz7tLhocgQjwwTw/v+dTV7C0/7qsJMaPxLe1YtTs0iLC+kBnCeCbjOxnnP8oj7f4G6FHowDZCmtcrrYSiAaWi/ZL06WzokF6k54Ow7Us3hv2P0ybsLMoGu6u2t553Ubaty5xiKZzhcoZWyx+Zlnen1k6mqQSUdBwYsrryi/+5Umu4mduKxlKOHuz06X5ZyC0tTEIZQYJD0FgXW90USzZ59DunviPFVDlMoQ6kdWEhNxJwm9ikNpFQt0K+f9gcEjrLkiUpStM3PfS8r5kBumvDWdjFLOdWjgiysYw1dOCMdU179HslhsekvG7OuTa6HMrTwfbTODAw5rIl03VjtWMW9MgAfH4K9kXpU4Jd/gFloZw8e6RQ967bPImwxzIFMUfGzGpiKLDGKSfXUK0T9r2GOyFBLFRiB9aEMKGB+OSllZljC84G5HcHWvMHpO3Cfhsv/QtOOtvw0ZzgTSy8/k8V5gWmGhq3X+I2woqM2svUnCkBzZiRhtIoFPR3R0c4BNUZfqG9xVzM3PODJMCVHC6u13rfghs+MDJVKnUq1wB4ooKqKsMusiIgoF5691QAucvyisKl0ug/anuzhPleIkg0Wf7Hr0R1QIeqcdUa0coeT/x26z9PKCVKWUkex7kmf0cLj767tEsfZ1ddKyGNjAXFioVbsb7Ox+3D3FnIf4qFK52Hs/6HsjDSfs4d9c1xnLrrm6l5jjiWOhzFAatDHvWjDNCpCUHr0SsDj4VpCmXFIWa9CJHm0IiTZ6lbjCAJIe5P/chlUd1TJndwBIJ5vQGCcR4Wd3NBqaqfXWuNwsRzHoXqmRVXppEwCbe2GybZQhaiZbzm7gmYxW15Kl0jZdjac9wTz3Zah/FLP+PMDNzexmS66umK3sxY4PxWUDHvE0nrX41vaxKXywUVnuXXmEon2NNwpsNaxgzBSpuk5fRCrhIGzcriqaYhTN5Z72RtNlE+z1ilJ9YUpR4KIJosXSUThMrW8GFo+UXRN2IG/XQtXY7YuPriQSmAqTjlQHzY4sdk78NPBZAdGFzK2NcJL/nukRMm1JxHWE/O5BmgjdxmlJxsStygqUuUBXDpHjWQkcKMAI+rKxHi6JQGgQrT2SB0+lFUZU2/dzauDzZVQWweIyYhw6meLSFo7ODnGHokV1VZ2p5nhHlEPyEXmEqr+zKbDQZPmtPwdqvUnPaSipYVXjJFLNdpfD+ERcwgEtzl7YvE78SLG6rphnmg3vB/jXPJG6ixS9Gcx55s+maQIeRQoo/NBQkSzu8kgOeaVrxJW1KkZaDBZ2LpvgqalcrCUxic2U8kpuxSnx4teRZyOMMiJeV3HsnF/8tgiEV3UyW89PyI56bmzDXy1Yo+2pqIN6j/QPD4I+IJsj95hPtAmAKo+OTmt7s/zTB8T5y3LgAq57qxe43CsTjp/MVaHhK51dpw2rzCek2NgXaLZiS7/XQFmZL5uN9fllxWw3cO6dYVs+eT543Ipm1ommZXXfYIlpH6jHKSuB85me5FJCmbwNkyLEp9r9OeQxaRlbLXQp0jUi4wAmQ6WxJ5JTfTPx/3RntscuuE6iYO9rdin6CB9vdfINycnXuzjuXqhi6E9VJqTrLBLn18GrtokyfiZjtF9cV6SrwhftOfm0bqQD2NJeOnli6W+lgfzR4gcJ7xQXBQfHo8cpw37xflsBN6KHddy+cbHl1VAJ77CkvutZsPxEdf4bDkDWhRV7WUlbxhfOHtd7djN15L/RrNxnY5eTedeXyS1ZYQK4aXAWnzHQrNBbPJ/2LrNhJTQ+C1Ir2CpIGnFhjSY549CBohpL4B2pwn/thbHDc7Efwb4ID4x7WJyLLONvTqNmPkZEH2PkRZC6eWzFiJjkP7gCXJaV+XEtmbNMh104m6MainsKGtH4pucan60YQd5afjIqFtj3EgZE0Z7Xp7ZZiCagnVhPXaWwDu32HQTLcaDCq40GKptZatrRoIfFwA+pv0ooWguNEp8rXi/UBfV4CjYjkB4T6HkW6E8vWbwz33u6LEkZrSVuAMIAuKvEqL8W7UzbizuwtVk/2IB30DxjRwlXGvxu+YpAI5pTwLYC/hujyDMDfHE9AvTawKXzDb74v6nKBn6/jSPO3bey7m39y53GOgZbTOF5iS3e+BiuCFJhAWyl7xsCbC0Fou/zqJXUn9kf8jexL05cM0jb9R0eVN2LTQxmAjF0Quhj7s/xpsS6Y64aXcWUZedgK4vIPVoRcMxHIK71IhWMXfFTtlKk73jYap1xiz/HZcBnW7+KoYtfm+PyNx0Eh1jr4fC66u3e+WHrLhNuFV3lv0cF+VygFEgaDLkq4PkeNu4eMTNJIwYyyfJ+NblvIRIdo3erAXD0z1z2JQGkwYECBR8z\",\"PlainBlockSize\":65536,\"SingleKeyBlocks\":1024,\"TimeStamp\":\"2018-10-03T09:40:05.0590274Z\"}";
		Map<String, Object> map = new ObjectMapper().readValue(content, new TypeReference<Map<String, Object>>() {
		});
		String decodedString = new String(Base64.decodeBase64((String) map.get("EncryptedPrivateInfo")),
				StandardCharsets.UTF_8);
		System.out.println(decodedString);

		Map<String, Object> map2 = new ObjectMapper().readValue(decodedString,
				new TypeReference<Map<String, Object>>() {
				});

		System.out.println(map2);

		if (true)
			return;

		List<? extends Number> myIntList = new ArrayList<Integer>() {
			{
				add(45);
			}
		};
		List genericList = myIntList;
		genericList.add(3.5d);
		myIntList.stream().forEach(System.out::println);

		if (true)
			return;

		Animal an = fun(new Dog());

		LocalDateTime timeMilli = LocalDateTime.parse("2013-11-01T18:00:04.574");
		long epochMilliUTC = timeMilli.toInstant(ZoneOffset.UTC).toEpochMilli();
		long epochMilliIST = timeMilli.atZone(ZoneId.of("Asia/Calcutta")).toInstant().toEpochMilli();
		long epochMilliUTCConverted = timeMilli.atZone(ZoneId.of("Asia/Calcutta"))
				.withZoneSameInstant(ZoneOffset.UTC.normalized()).toLocalDateTime().toInstant(ZoneOffset.UTC)
				.toEpochMilli();
		System.out.println(epochMilliUTC);
		System.out.println(epochMilliIST);
		System.out.println(epochMilliUTCConverted);
		if (true)
			return;

		System.out.println(Double.parseDouble("+3"));
		System.out.println((int) 'A');
		System.out.println(ZoneId.of("Asia/Calcutta").getRules().getOffset(Instant.now()));
		System.out.println("abcdef".substring(3, 4));

		if (true)
			return;

		System.out.println("".compareTo(null));
		Optional.ofNullable(null).orElseThrow(() -> {
			return new RuntimeException("asd");
		});
		System.out.println("Executed");
		if (true) {
			return;
		}
		// int i;
		String da = "2018-05-10T18:22:50+05:30";
		// System.out.println(i);
		Long l = 23234l;
		System.out.println(l.toString());
		System.out.println(new Date(da));
		assert 1 != 2;
		if (true)
			return;
		System.out.println(int.class.getCanonicalName());
		System.out.println(MyTestClass.builder().build());

		List<String> sdf = null;
		List ms = Arrays.asList();
		myfun(ms);

		taskQueue.add("a");
		taskQueue.add("b");
		taskQueue.add("c");
		taskQueue.add("d");
		taskQueue.add("e");

		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {

		}, 0, 1, TimeUnit.MINUTES);

		for (int i = 0; i < 10; i++) {
			fixedThreadPool.execute(() -> {
				try {
					Thread.sleep(valueLength);
					System.out.println("Called : " + taskQueue.poll());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}

		/*
		 * ArrayList merge = merge(new ArrayList( Arrays.asList(new Interval(1, 3), new
		 * Interval(2, 6), new Interval(8, 10), new Interval(15, 18))));
		 * merge.stream().forEach(System.out::println); System.out.println(solve(2, 3));
		 */

	}

	private static void myfun(List<Integer> ms) {
		// TODO Auto-generated method stub

	}

	public static int solve(int A, int B) {
		Map<Integer, Integer> totalValues = new HashMap<>();
		totalValues.put(1, B);
		int totalNumbers = B;
		int lastNumberProcessed = 1;
		while (totalNumbers != A && lastNumberProcessed < B) {
			if (totalValues.containsKey(lastNumberProcessed) && totalValues.get(lastNumberProcessed).intValue() == 1) {
				lastNumberProcessed++;
				continue;
			} else {
				int incrementNumber = lastNumberProcessed + 1;
				int lastNumberCount = totalValues.get(lastNumberProcessed);
				boolean newNumberFound = totalValues.containsKey(incrementNumber);
				int nextNumberCount = newNumberFound ? totalValues.get(incrementNumber) : 0;
				totalValues.put(lastNumberProcessed, --lastNumberCount);
				totalValues.put(incrementNumber, ++nextNumberCount);
				if (lastNumberProcessed == 1) {
					totalNumbers--;
					totalValues.put(lastNumberProcessed, --lastNumberCount);
				} /*
					 * if (!newNumberFound) { totalNumbers++; totalNumbers--; }
					 */
			}
		}

		int totalOperations = 0;
		List<Integer> sortedKey = totalValues.keySet().stream().sorted().collect(Collectors.toList());
		for (Integer key : sortedKey) {
			if (totalValues.get(key).intValue() == 1) {
				continue;
			} else {
				int numberOfShifts = totalValues.get(key).intValue() - 1;
				totalOperations += numberOfShifts;
				int nextKey = key + 1;
				if (totalValues.containsKey(nextKey)) {
					totalValues.put(key + 1, totalValues.get(key + 1) + numberOfShifts);
				} else {
					totalValues.put(key + 1, numberOfShifts);
				}
			}
		}

		return totalOperations;

	}

	public static ArrayList<Interval> merge(ArrayList<Interval> intervals) {
		ArrayList<Interval> mergedIntervals = new ArrayList<>();
		if (intervals != null && intervals.size() > 0) {
			for (Interval interval : intervals) {
				Optional<Interval> optInterval = mergedIntervals.stream().filter(mInt -> {
					if ((interval.start >= mInt.start && interval.start <= mInt.end)
							|| (interval.end >= mInt.start && interval.end <= mInt.end)) {
						return true;
					} else {
						return false;
					}
				}).findFirst();
				if (optInterval.isPresent()) {
					Interval intervalToMerge = optInterval.get();
					intervalToMerge.start = Math.min(intervalToMerge.start, interval.start);
					intervalToMerge.end = Math.max(intervalToMerge.end, interval.end);
				} else {
					mergedIntervals.add(interval);
				}
			}
		}
		return mergedIntervals;
	}

	public void curefit() {

	}
}
