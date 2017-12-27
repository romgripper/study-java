package com.abc;

import java.lang.reflect.Method;
import java.util.Calendar;

public class B extends A {
	protected void run() {
		System.out.println("B.run()");
	}
	
	public static void main(String[] args) {
		A a = new B();
		a.runn();
		//System.out.println(a.a);
		for ( Method method : Calendar.class.getMethods() )
			System.out.println( method );
	}
}
