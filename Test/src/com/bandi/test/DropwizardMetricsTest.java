package com.bandi.test;

import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricAttribute;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

import avro.shaded.com.google.common.collect.Sets;
import lombok.Getter;

public class DropwizardMetricsTest {

	@Getter
	private static final MetricRegistry metrics = new MetricRegistry();
	private static ConsoleReporter reporter;

	static {
		reporter = ConsoleReporter.forRegistry(metrics).convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS)
				.disabledMetricAttributes(Sets.newHashSet(MetricAttribute.M1_RATE, MetricAttribute.M15_RATE,
						MetricAttribute.M5_RATE, MetricAttribute.STDDEV, MetricAttribute.P98, MetricAttribute.P999))
				.build();
	}

	public static void startReporter(int periodInSec) {
		reporter.start(periodInSec, TimeUnit.SECONDS);
	}

	public static void startReporter() {
		startReporter(1);
	}

	public static void main(String[] args) throws InterruptedException {
		startReporter();
		Meter requests = metrics.meter("requests");
		requests.mark();
		Timer timer = metrics.timer("timers");
		long startTime = System.currentTimeMillis();
		wait5Seconds();
		timer.update(System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS);

		Thread.sleep(1000);
	}

	static void wait5Seconds() {
		try {
			Thread.sleep(5 * 1000);
		} catch (InterruptedException e) {
		}
	}

}
