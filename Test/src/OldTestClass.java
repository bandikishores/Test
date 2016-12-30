import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

abstract class Aabc implements Iabc {

}

interface Iabc {
	public int add(int a, int b);

	default int getValue() {
		return 5;
	}
}

class ATest {
	@Override 
	public int hashCode()
	{
		return 1;//return new Random().nextInt();
	}
}

public class OldTestClass {
	int a;

	static String value;

	class inner {
		void fun() {
			System.out.println(a);
		}
	}

	public static void consumeInterface(Iabc a) {
		System.out.println(a.add(1, 2));
	}

	public static void consumeAbstract(Aabc a) {
		System.out.println(a.add(3, 6));
	}

	public static void main(String[] args) throws Exception {
		
		ATest atest = new ATest();
		ATest btest = new ATest();
		
		if(atest == btest) System.out.println("equals");
		System.out.println(atest);
		System.out.println(btest);
		if(true) return;
		
		ExpressionParser parser = new SpelExpressionParser();
	    Map<String,String> itemTypes = (Map) parser.parseExpression("{NormalValue:'INGREDIENT','Apostro''Value':'INGREDIENT'}").getValue();
	    for (Map.Entry<String, String> e : itemTypes.entrySet())
	        System.out.println(e.getKey() + ": " + e.getValue());
	    
	    
	    if(true) return;
		Arrays.asList("b", "a").stream().sorted((a, b) -> a.compareTo(b)).forEach(System.out::println);
		System.out.println(5 << 2);

		Stream<String> supplierStream = Stream.of("d2", "a2", "b1", "b3", "c2");

		Map<Integer, List<String>> map = supplierStream
				.collect(Collectors.groupingBy(a -> Integer.valueOf(a.toString().charAt(1) + "")));

		System.out.println(map);

		if (true)
			return;

		Stream.of("d2", "a2", "b1", "a3", "b3", "a1", "c").filter(s -> {
			System.out.println("filter: " + s);
			return s.startsWith("a");
		}).sorted((s1, s2) -> {
			System.out.printf("sort: %s; %s\n", s1, s2);
			return s1.compareTo(s2);
		}).map(s -> {
			System.out.println("map: " + s);
			return s.toUpperCase();
		}).forEach(s -> System.out.println("forEach: " + s));

		if (true)
			return;
		int i = 2333;
		consumeInterface((a, b) -> a * b);
		consumeInterface((a, b) -> {
			return a * b + i;
		});

		Function<String, Integer> toInteger = (s) -> Integer.valueOf(s);
		Function<String, String> backToString = toInteger.andThen(String::valueOf);

		Function<Integer, String> toString = (s) -> value = s.toString();

		Optional<Integer> opt = Optional.of(1);
		opt.ifPresent(toString::apply);

		Consumer<Integer> consumer = new Consumer<Integer>() {

			@Override
			public void accept(Integer t) {
				System.out.println(t);
				t += 1;
			}
		};

		List<Integer> list = new ArrayList(Arrays.asList(3, 42, 23, 2, 65, 7));
		list.stream().sorted().forEach((q) -> System.out.println(q));

		System.out.println(value);

		backToString.apply("123");
		// i = 3;
		if (true)
			return;

		String uri = "<html><img src=\"abc&amp;def\"/>&amp;";
		uri = URLDecoder.decode(uri, "UTF-8");
		uri = uri.replace("&amp;", "&");
		System.out.println(uri);

		String abc = null;
		System.out.println(abc + "des");

		if (1 == 1)
			return;
		// Scanner sf = new Scanner(new FileInputStream("prog415h.dat"));
		int[] number = new int[100];
		int[] numbers = { 12, 12, 30, 12, 45, 66, 78, 30, 82, 19, 99, 11, 11, 15, 31, 18, 51, 17, 12, 17 };
		int count = 0;
		Runner runner = new Runner();
		for (int num : numbers) {
			number[count] = num;

			System.out.println("The orginal set of numbers are: " + number[count] + " ");
			count++;
		}
		runner.setNumbers(number);
		runner.Repeat();
	}
}

class Runner {
	public static int[] numbers;

	public static void setNumbers(int[] numbers) {
		Runner.numbers = numbers;
	}

	public static void Repeat() {
		Set<Integer> uniqueElements = new HashSet<Integer>();
		System.out.println("List of Unique elements");
		for (int i = 0; i < numbers.length; i++) {
			if (!uniqueElements.contains(numbers[i])) {
				uniqueElements.add(numbers[i]);
				System.out.println(numbers[i]);
			}
		}
		System.out.println();
	}
}