package com.bandi.test;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.function.Function;
import java.util.function.Supplier;

public class FutureTaskTest {
	public static ThreadLocal<Integer> threadlocal = new ThreadLocal<>();

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// FutureTask is a concrete class that
		// implements both Runnable and Future
		threadlocal.set(235435);
	    ExecutorService exec = Executors.newFixedThreadPool(10);
	    CompletableFuture<Integer> f = CompletableFuture.supplyAsync(new MySupplier(), exec);
	    System.out.println(f.isDone() + " current Thread " + Thread.currentThread().getId() + " ThreadLocal " + FutureTaskTest.threadlocal.get()); // False
	    CompletableFuture<Integer> f2 = f.thenApply(new PlusOne());
	    System.out.println(f2.get() + " current Thread " + Thread.currentThread().getId() + " ThreadLocal " + FutureTaskTest.threadlocal.get()); // Waits until the "calculation" is done, then prints 2
	    
	    f2.thenApply(i -> {
	    	System.out.println("thenApplyAsync received is " + i + " current Thread " + Thread.currentThread().getId() + " ThreadLocal " + FutureTaskTest.threadlocal.get());
	    	return i;
	    }).thenAcceptAsync(i -> {
	    	System.out.println("thenAcceptAsync received is " + i + " current Thread " + Thread.currentThread().getId() + " ThreadLocal " + FutureTaskTest.threadlocal.get());
	    	try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    });
	    exec.shutdown();
	    Thread.sleep(1000);
	}

}

/**
* A supplier that sleeps for a second, and then returns one
**/
class MySupplier implements Supplier<Integer> {

    @Override
    public Integer get() {
        try {
            Thread.sleep(200);
    		System.out.println("Thread in MySupplier execution " + Thread.currentThread().getId() + " ThreadLocal " + FutureTaskTest.threadlocal.get());
        } catch (InterruptedException e) {
            //Do nothing
        }
        return 1;
    }
}

class PlusOne implements Function<Integer, Integer> {

    @Override
    public Integer apply(Integer x) {
		System.out.println("Thread in PlusOne execution " + Thread.currentThread().getId() + " ThreadLocal " + FutureTaskTest.threadlocal.get());
        return x + 1;
    }
}

