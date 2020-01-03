package test;

import java.util.ArrayList;

public class Outer {

	private static ThreadLocal<String> tl=new ThreadLocal<String>();

	public static void main(String[] args) {
		ArrayList<Thread> list=new ArrayList<Thread>();
		while(true) {
			list.add(new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(true) {System.out.println("abc");}
				}
			}));
		}
	}
}