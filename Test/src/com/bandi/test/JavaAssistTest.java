package com.bandi.test;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.Bytecode;
import javassist.bytecode.ClassFile;
import javassist.bytecode.DuplicateMemberException;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;

public class JavaAssistTest {

	public static void main(String[] args)
			throws CannotCompileException, RuntimeException, InstantiationException, IllegalAccessException {
		Class<?> runtimeClass = CreateClassInRuntime();
		Object obj = runtimeClass.newInstance();
		System.out.println(obj);
	}

	private static Class<?> CreateClassInRuntime() throws CannotCompileException, RuntimeException {
		ClassFile cf = new ClassFile(false, "com.bandi.test.JavassistGeneratedClass", null);
		cf.setInterfaces(new String[] { "java.lang.AutoCloseable" });

		FieldInfo f = new FieldInfo(cf.getConstPool(), "id", "I");
		f.setAccessFlags(AccessFlag.PUBLIC);
		cf.addField(f);

		AddDefaultConstructor(cf);

		ClassPool classPool = ClassPool.getDefault();
		return classPool.makeClass(cf).toClass();
	}

	private static void AddDefaultConstructor(ClassFile cf) throws DuplicateMemberException {
		Bytecode code = new Bytecode(cf.getConstPool());
		code.addAload(0);
		code.addInvokespecial("java/lang/Object", MethodInfo.nameInit, "()V");
		code.addReturn(null);

		MethodInfo minfo = new MethodInfo(cf.getConstPool(), MethodInfo.nameInit, "()V");
		minfo.setCodeAttribute(code.toCodeAttribute());
		cf.addMethod(minfo);
	}

}
