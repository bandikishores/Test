package com.sometest;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@Fork(0)
@BenchmarkMode(Mode.AverageTime)
@Measurement(iterations = 0, timeUnit = TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Threads(1)
@Warmup(iterations = 0, timeUnit = TimeUnit.NANOSECONDS)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class JMHTest {

	private int totalSize = 32_768;
	private int filterValue = 1_280;
	private int loopCount = 10_000;
	private Random rnd = new Random(0);

	// private int[] array = IntStream.range(0, totalSize).toArray();
	private int[] array = rnd.ints(totalSize).map(i -> i % 2560).toArray();

	@Benchmark
	public long conditionalOperatorTime() {
		long sum = 0;
		for (int j = 0; j < loopCount; j++) {
			for (int c = 0; c < totalSize; ++c) {
				sum += array[c] >= filterValue ? array[c] : 0;
			}
		}
		return sum;
	}

	@Benchmark
	public long branchStatementTime() {
		long sum = 0;
		for (int j = 0; j < loopCount; j++) {
			for (int c = 0; c < totalSize; ++c) {
				if (array[c] >= filterValue) {
					sum += array[c];
				}
			}
		}
		return sum;
	}

	@Benchmark
	public long streamsTime() {
		long sum = 0;
		for (int j = 0; j < loopCount; j++) {
			sum += IntStream.of(array).filter(value -> value >= filterValue).sum();
		}
		return sum;
	}

	@Benchmark
	public long parallelStreamsTime() {
		long sum = 0;
		for (int j = 0; j < loopCount; j++) {
			sum += IntStream.of(array).parallel().filter(value -> value >= filterValue).sum();
		}
		return sum;
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(".*" + JMHTest.class.getSimpleName() + ".*")
				.forks(1)
				.build();

		new Runner(opt).run();
	}
}
