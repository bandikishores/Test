package com.bandi.test;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class CuratorTest {

	public static void main(String[] args) throws Exception {
		String zookeeperConnectionString = "localhost:2181";
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework curator = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);
		curator.start();

		String lockPath = "/test/executors";
		// curator.create().forPath(lockPath);

		InterProcessMutex lock = new InterProcessMutex(curator, lockPath);
		if (lock.acquire(5, TimeUnit.SECONDS)) {
			try {
				Integer currentNodeId = 1;
				List<String> pathStrList = curator.getChildren().forPath(lockPath);
				if (pathStrList != null) {
					List<Integer> paths = pathStrList.stream().filter(NumberUtils::isNumber).map(Integer::parseInt)
							.sorted().collect(Collectors.toList());
					for (Integer nodeFound : paths) {
						if (nodeFound > currentNodeId) {
							break;
						} else {
							currentNodeId++;
						}
					}
				}
				curator.create().withMode(CreateMode.EPHEMERAL).forPath(lockPath + "/" + currentNodeId);
			} finally {
				lock.release();
			}
		}

		Thread.sleep(100000);
	}

}
