package com.sometest;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;

public class RXReactJavaTest {

    public static void main(String[] args) throws InterruptedException {
        testObservable1("testObservable");
        // testObservable("testObservable");
        System.out.println("\n\n");
        /*  constantObservable("ConstantObservable");
        System.out.println("\n\n");
        continuousSingleObservable("continuousSingleObservable");
        System.out.println("\n\n");
        continuousSingleCachedObservable("continuousSingleCachedObservable");
        System.out.println("\n\n");
        Thread.sleep(1000);
        continuousParallelObservable("continuousParallelObservable");
        System.out.println("\n\n");
        Thread.sleep(1000);*/
    }

    private static void testObservable1(String string) throws InterruptedException {
        OnSubscribe f = new OnSubscribe<Long>() {
            long value = 1;

            @Override
            public void call(Subscriber<? super Long> subscriber) {
                while (true) {
                    System.out.println(subscriber);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    subscriber.onNext(value++);
                    subscriber.onCompleted();
                }
            }
        };
        Observable<Long> obs = Observable.create(f).publish();

        obs.subscribe(v -> System.out.println("1st subscriber:" + v));

        Thread.sleep(1000);

        obs.subscribe(v -> System.out.println("2nd subscriber:" + v));

        Thread.sleep(10000);
    }

    private static void testObservable(String functionName) {
        Observable<Integer> connectableObservable = Observable.create(new OnSubscribe<Integer>() {
            int value = 1;

            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                System.out.println(subscriber);
                for (int j = 0; j < 3; j++) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    subscriber.onNext(value++);
                }
                // subscriber.onCompleted();
            }
        }).share();// subscribeOn(Schedulers.computation());

        Subscriber<Integer> subscriber1 = new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println(functionName + " - Before Connect Completed");
            }

            @Override
            public void onError(Throwable arg0) {
                System.out.println(functionName + " - Before Connect Error ");
            }

            @Override
            public void onNext(Integer arg0) {
                System.out.println(functionName + " - Before Connect Subscriber : " + arg0);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        connectableObservable.subscribe(subscriber1);
        System.out.println("Subscriber1 : " + subscriber1);

        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Subscriber<Integer> subscriber2 = new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println(functionName + " - After Connect Subscriber Completed ");
            }

            @Override
            public void onError(Throwable arg0) {
                System.out.println(functionName + " - After Connect  Error ");
            }

            @Override
            public void onNext(Integer arg0) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(functionName + " - After Connect Subscriber : " + arg0);
            }
        };
        connectableObservable.subscribe(subscriber2);
        System.out.println("Subscriber1 : " + subscriber2);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private static void continuousParallelObservable(String functionName) {
        Observable<Integer> observable = getEmittingObservable();
        observable = observable.cache().observeOn(Schedulers.computation());
        // flatMap(integer -> Observable.just(integer).subscribeOn(Schedulers.computation()));

        Subscription subscribe = observable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println(functionName + " - First Continuous Subscriber Completed ");
            }

            @Override
            public void onError(Throwable arg0) {
                System.out.println(functionName + " - First Continuous Subscriber Error ");
            }

            @Override
            public void onNext(Integer arg0) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(functionName + " - First Continuous Subscriber : " + arg0);
            }
        });

        Subscription subscribe1 = observable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println(functionName + " - Second Continuous Subscriber Completed ");
            }

            @Override
            public void onError(Throwable arg0) {
                System.out.println(functionName + " - Second Continuous Subscriber Error ");
            }

            @Override
            public void onNext(Integer arg0) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(functionName + " - Second Continuous Subscriber : " + arg0);
            }
        });

    }

    private static Observable<Integer> getEmittingObservable() {
        Observable<Integer> observable = Observable.create(new OnSubscribe<Integer>() {
            int value = 1;

            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int j = 0; j < 3; j++) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    subscriber.onNext(value++);
                }
            }
        });
        return observable;
    }

    private static void continuousSingleCachedObservable(String functionName) {
        Observable<Integer> actualObservable = getEmittingObservable();

        final Observable<Integer> observable = actualObservable.cache();

        new Thread(() -> {
            Subscription subscribe = observable.subscribe(new Subscriber<Integer>() {
                @Override
                public void onCompleted() {
                    System.out.println(functionName + " - First Continuous Subscriber Completed ");
                }

                @Override
                public void onError(Throwable arg0) {
                    System.out.println(functionName + " - First Continuous Subscriber Error ");
                }

                @Override
                public void onNext(Integer arg0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println(functionName + " - First Continuous Subscriber : " + arg0);
                }
            });
        }).start();

        new Thread(() -> {
            Subscription subscribe1 = observable.subscribe(new Subscriber<Integer>() {
                @Override
                public void onCompleted() {
                    System.out.println(functionName + " - Second Continuous Subscriber Completed ");
                }

                @Override
                public void onError(Throwable arg0) {
                    System.out.println(functionName + " - Second Continuous Subscriber Error ");
                }

                @Override
                public void onNext(Integer arg0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println(functionName + " - Second Continuous Subscriber : " + arg0);
                }
            });
        }).start();

    }

    private static void continuousSingleObservable(String functionName) {
        Observable<Integer> observable = getEmittingObservable();

        Subscription subscribe = observable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println(functionName + " - First Continuous Subscriber Completed ");
            }

            @Override
            public void onError(Throwable arg0) {
                System.out.println(functionName + " - First Continuous Subscriber Error ");
            }

            @Override
            public void onNext(Integer arg0) {
                System.out.println(functionName + " - First Continuous Subscriber : " + arg0);
            }
        });

        Subscription subscribe1 = observable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println(functionName + " - Second Continuous Subscriber Completed ");
            }

            @Override
            public void onError(Throwable arg0) {
                System.out.println(functionName + " - Second Continuous Subscriber Error ");
            }

            @Override
            public void onNext(Integer arg0) {
                System.out.println(functionName + " - Second Continuous Subscriber : " + arg0);
            }
        });

    }

    private static void constantObservable(String functionName) {
        Observable<Integer> observable = Observable.just(1);
        observable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer arg0) {
                System.out.println(functionName + " - First Action : " + arg0);
            }
        });
        observable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer arg0) {
                System.out.println(functionName + " - Second Action : " + arg0);
            }
        });
    }

}
