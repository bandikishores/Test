import java.io.IOException;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.Builder.Default;
import lombok.extern.slf4j.Slf4j;

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
		@Default private Optional<String> str = Optional.of("myClass");
	}

	public static void main(String[] args) throws InterruptedException, JsonProcessingException, IOException {
		System.out.println(ZoneId.of("Asia/Calcutta").getRules().getOffset(Instant.now()));
		System.out.println(MessageFormat.format("Value {0} {1}", "sd","sdsf"));
		Arrays.asList("1.0","1.1","1.2","1.11").stream().sorted().forEach(System.out::println);
		
		System.out.println("abc".length());
		System.out.println("a".substring(0, "a".length() - 1).length());
		
		if(true) return;
		
		System.out.println("".compareTo(null));
		Optional.ofNullable(null).orElseThrow(() -> {
			return new RuntimeException("asd");
		});
		System.out.println("Executed");
		if(true) {
			return;
		}
		// int i;
		String da = "2018-05-10T18:22:50+05:30";
		//System.out.println(i);
		Long l = 23234l;
		System.out.println(l.toString());
		System.out.println(new Date(da));
		assert 1 != 2;
		if(true)return;
		System.out.println(int.class.getCanonicalName());
		System.out.println(MyTestClass.builder().build());
		System.out.println(MyTestClass.builder().str);
		
		List<String> sdf = null;
		List ms = Arrays.asList();
		myfun(ms);

		taskQueue.add("a");
		taskQueue.add("b");
		taskQueue.add("c");
		taskQueue.add("d");
		taskQueue.add("e");
		
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(()-> {
			
		}, 0, 1, TimeUnit.MINUTES);

		for (int i = 0; i < 10; i++) {
			fixedThreadPool.execute(() -> {
				try {
					Thread.sleep(1000);
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
