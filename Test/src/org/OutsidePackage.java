package org;

import com.AnotherOutsideClass;

public class OutsidePackage extends AnotherOutsideClass {

    public static void main(String[] args) {
        OutsidePackage outside = new OutsidePackage();
        outside.protectedDisplay();
    }

  @Override
  protected void protectedDisplay() {
        // TODO Auto-generated method stub
        super.protectedDisplay();
    }  
}

class AnotherOutsidePackage {
    public static void main(String[] args) {
        OutsidePackage outside = new OutsidePackage();
        outside.protectedDisplay();
    }
}
