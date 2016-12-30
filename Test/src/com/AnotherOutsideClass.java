package com;

public class AnotherOutsideClass {


    void defaultDisplay() {
        System.out.println("Called default method.");
    }
    
    protected void protectedDisplay() {
        System.out.println("Called protected method.");
    }
    
    public static void main(String[] args) {

        AnotherOutsideClass testClass = new AnotherOutsideClass();
        testClass.defaultDisplay();
        testClass.protectedDisplay();
    }

}

class MyClass {
    public static void main(String[] args) {
        AnotherOutsideClass anotherOutsideClass = new AnotherOutsideClass();
        anotherOutsideClass.defaultDisplay();
        anotherOutsideClass.protectedDisplay();
    }
}
