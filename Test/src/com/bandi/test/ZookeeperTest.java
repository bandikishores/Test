package com.bandi.test;

import static org.apache.zookeeper.CreateMode.EPHEMERAL_SEQUENTIAL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.AsyncCallback.ChildrenCallback;
import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.Watcher.Event.EventType;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZookeeperTest {

    private ZooKeeper                      zk;

    private static String                  PATH                       = "/test/kish/ephi";

    private List<Watcher>                  watchers                   = new ArrayList<>(10);

    private final Map<String, LockRequest> awaitingRequests           = new ConcurrentHashMap<>();

    private volatile String                currentLockHoldingNodeName = null;

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZookeeperTest zookeeperTest = new ZookeeperTest();
        zookeeperTest.createConnection();
    }

    public void createConnection() throws IOException, KeeperException, InterruptedException {

        Watcher sessionWatcher = event -> {
            System.out.println("Event occured " + event);
        };
        zk = new ZooKeeper("localhost:2181", 400, sessionWatcher);
        Stat stat = zk.exists(PATH, true);
        System.out.println(stat);
        
        Thread.sleep(2000);
        stat = zk.exists(PATH, true);
        System.out.println(stat);

        String created = zk.create(PATH + "/entity", "jayant".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Created output "  + created);
        
        zk.getChildren(created, false);
        
    //    zk.create(PATH + "/entity/sessions", "inside".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
     //   System.out.println(created);
    }

    public void createEphemeralNode() {
        // zk.

    }

    @RequiredArgsConstructor
    private final class LockNodeChildrenWatcher implements Watcher {

        @Override
        public void process(WatchedEvent event) {
            if (event.getType() == EventType.NodeChildrenChanged) {
                log.trace("children node added or deleted");
                zk.getChildren(PATH, this, new LockNodeChildrenQueryCallBack(), null);
                log.trace("fired getChildren() with lock-awarding callback, and watcher this");
            }
        }
    }

    private static class LockNodeChildrenQueryCallBack implements ChildrenCallback {

        @NonNull
        private ZookeeperTest lock;

        @Override
        public void processResult(int rc, String path, Object ctx, List<String> children) {
            log.trace("received getChildren result ");
            if (children.isEmpty()) {
                log.trace("no child, only remaining lock released");
                return;
            }
            Collections.sort(children);
            String winner = children.get(0);
            if (winner.equals(lock.currentLockHoldingNodeName)) {
                log.trace("earliest node is still holding the lock");
                return;
            }
            LockRequest awaitingRequest = lock.awaitingRequests.get(winner);
            if (null != awaitingRequest) {
                synchronized (lock) {
                    lock.awaitingRequests.remove(winner);
                    log.trace("LockNode {} has been removed from awaitingRequests", winner);
                    lock.currentLockHoldingNodeName = winner;
                }
                awaitingRequest.lockCallback.call(true, awaitingRequest.lockNodeName);
                log.trace(awaitingRequest.lockNodeFqn + " acquired the lock and callback called");
            } else {
                log.warn(winner + " is not in awaiting queue, possible code bug");
            }
        }

    }

    private static class LockNodeCreateCallBack implements StringCallback {

        @NonNull
        private ZookeeperTest lock;

        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            LockRequest lockRequest = (LockRequest) ctx;
            if (rc != Code.OK.intValue()) {
                log.warn("node creation failed @ node " + lockRequest.lockBasePath + " error " + Code.get(rc));
                lockRequest.lockCallback.call(false, null);
                return;
            }
            log.trace("lockNode {} created", name);
            lockRequest.lockNodeFqn = name;
            lockRequest.lockNodeName = name.substring(lockRequest.lockBasePath.length() + 1);
            lock.awaitingRequests.put(lockRequest.lockNodeName, lockRequest);
            log.trace("LockNode {} has been put in awaitingRequests", lockRequest.lockNodeName);
        }

    }

    /******************************************
     * Inner classes
     *********************************************/
    @FunctionalInterface
    public interface LockCallback {
        void call(boolean acquired, String lockName);
    }


    @RequiredArgsConstructor
    private static final class LockRequest {

        @NonNull
        String               lockBasePath;

        String               lockNodeFqn;

        String               lockNodeName;

        @NonNull
        private LockCallback lockCallback;
    }

}
