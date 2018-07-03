package com.bandi.test;

import java.lang.reflect.Method;
import java.util.Observable;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CGLibInterceptorTest {

    public static void main(String[] args) {
        new ObservableBeanFactory().createObservableBean(MyTestAPI.class).printTest();;
    }

}


class MyTestAPI {
    public void printTest() {
        System.out.println("My PrintTest");
    }
}


final class ObservableBeanFactory {

    public static <T> T createObservableBean(Class<T> beanClass) {

        MyInterceptor interceptor = new MyInterceptor();

        Enhancer e = new Enhancer();

        e.setSuperclass(beanClass);

        e.setCallback(interceptor);
/*
        e.setInterfaces(new Class[] { Observable.class});*/

        @SuppressWarnings("unchecked")

        T bean = (T) e.create();

        // interceptor.setTarget(bean);

        return bean;

    }

}


class MyInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object currentObj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("My Interceptor called");
        Object returnObject = methodProxy.invokeSuper(currentObj, args);
        System.out.println("My Interceptor return");
        return returnObject;
    }

}
