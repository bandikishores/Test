package com.bandi.test;

import java.lang.reflect.Method;
import java.util.Observable;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CGLibInterceptorTest {

    public static void main(String[] args) {
        MyTestAPI createObservableBean = new ObservableBeanFactory()
        		.createObservableBean(MyTestAPI.class, new MyInterceptor());
		createObservableBean.printTest();
    }

}


class MyTestAPI {
    public void printTest() {
        System.out.println("My PrintTest");
    }
}

class CustomTestAPI extends MyTestAPI {
    @Override
	public void printTest() {
        System.out.println("custom PrintTest begin");
        super.printTest();
        System.out.println("custom PrintTest end");
    }
}

final class ObservableBeanFactory {

    public static <T> T createObservableBean(Class<T> beanClass, MethodInterceptor interceptor) {

        Enhancer e = new Enhancer();

        e.setSuperclass(beanClass);

        e.setCallbacks(new MethodInterceptor[] {interceptor});
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
