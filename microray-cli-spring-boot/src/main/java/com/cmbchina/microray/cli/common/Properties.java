package com.cmbchina.microray.cli.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Properties {

	public Properties() {
	}

	private static java.util.Properties props = new java.util.Properties();
	static {
		try {

			String path = Thread.currentThread().getContextClassLoader().getResource("META-INF/").getPath();
			File files=new File(path);
			for(int i=0;i<files.list().length;i++){
//				System.out.println(files.list()[i]);

				props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(""+files.list()[i]));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getValue(String key) {
		String value = "";
		if(key != null && !"".equals(key)){
			value = props.getProperty(key);
		}
		return value;
	}

	public static void updateProperties(String key, String value) {
		props.setProperty(key, value);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println(Properties.getValue("uv"));
	}

}