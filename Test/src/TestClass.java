import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import lombok.Data;
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
        if(value == 17723) {
            return;
        }
        recursion(value+1);
    }


    public static void main(String[] args) throws InterruptedException {
        
        recursion(0);
        System.out.println("done");
        /*ArrayList merge = merge(new ArrayList(
                Arrays.asList(new Interval(1, 3), new Interval(2, 6), new Interval(8, 10), new Interval(15, 18))));
        merge.stream().forEach(System.out::println);
        System.out.println(solve(2, 3));*/

    }

    public static int solve(int A, int B) {
        Map<Integer, Integer> totalValues = new HashMap<>();
        totalValues.put(1, B);
        int totalNumbers = B;
        int lastNumberProcessed = 1;
        while (totalNumbers != A && lastNumberProcessed < B ) {
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
                  if (!newNumberFound) {
                     totalNumbers++;
                     totalNumbers--;
                  }*/
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

