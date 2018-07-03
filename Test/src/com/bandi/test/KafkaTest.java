package com.bandi.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KafkaTest {
	private final static String TOPIC = "cdc_cdc_perf_table";
	private final static String BOOTSTRAP_SERVERS = "localhost:9092";

	static Consumer<Long, String> consumer;

	public static void main(String[] args) throws InterruptedException {
		consumer = createConsumer();
		printLastNotUpdatedTime();
	}

	static void printLastNotUpdatedTime() throws InterruptedException {
		List<TopicPartition> topicList = Arrays.asList(new TopicPartition(TOPIC, 0));
		Map<TopicPartition, Long> beginningOffsets = consumer.beginningOffsets(topicList);
		Map<TopicPartition, Long> endOffsets = consumer.endOffsets(topicList);
		long lastCount = endOffsets.values().stream().findFirst().get()
				- beginningOffsets.values().stream().findFirst().get();
		long startTime = System.currentTimeMillis();
		long totalSameCount = 0;
		boolean isReset = true;
		long totalRecordsForRun = lastCount;
		for (;;) {
			long currentCount = consumer.endOffsets(topicList).values().stream().findFirst().get()
					- consumer.beginningOffsets(topicList).values().stream().findFirst().get();
			if (totalSameCount >= 10) {
				System.out.println("Total Time Taken with last same number " + (System.currentTimeMillis() - startTime)
						+ " For " + (currentCount - totalRecordsForRun + 1) + " Records");
				Thread.sleep(100);
				startTime = System.currentTimeMillis();
				totalSameCount = 0;
				isReset = false;
				totalRecordsForRun = currentCount;
			} else if (currentCount == lastCount) {
				if (isReset) {
					totalSameCount++;
				} else {
					Thread.sleep(100);
				}
			} else {
				lastCount = currentCount;
				totalSameCount = 0;
				if (!isReset) {
					totalRecordsForRun = lastCount;
					isReset = true;
					startTime = System.currentTimeMillis();
				}
			}
			Thread.sleep(1);
		}
	}

	static void runConsumer() throws InterruptedException {
		final int giveUp = 100;
		int noRecordsCount = 0;
		while (true) {
			final ConsumerRecords<Long, String> consumerRecords = consumer.poll(1000);
			if (consumerRecords.count() == 0) {
				noRecordsCount++;
				if (noRecordsCount > giveUp)
					break;
				else
					continue;
			}
			consumerRecords.forEach(record -> {
				System.out.printf("Consumer Record:(%d, %s, %d, %d)\n", record.key(), record.value(),
						record.partition(), record.offset());
			});
			consumer.commitAsync();
		}
		consumer.close();
		System.out.println("DONE");
	}

	private static Consumer<Long, String> createConsumer() {
		final Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaExampleConsumer");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		// Create the consumer using props.
		final Consumer<Long, String> consumer = new KafkaConsumer<>(props);
		// Subscribe to the topic.
		consumer.subscribe(Collections.singletonList(TOPIC));
		return consumer;
	}
}
