/*
 *   @Company: China Merchants Bank
 *   @Copyright: Copyright 2015 China Merchants Bank. All rights reserved.
 */

package com.cmbchina.microray.cli.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static boolean isEmpty(String str){
		if(null == str || "".equals(str)) {
			return true;
		}
		
		return false;
	}
	
	public static <T> String flat(Collection<T> list, String delimit, String head, String tail) {
        if (list == null)
            return null;
        if (list.size() == 0)
            return head + tail;
        StringBuilder buff = new StringBuilder(head);
        for (T token: list)
            buff.append(token.toString()).append(delimit);
        buff.replace(buff.length()-delimit.length(), buff.length(), tail);
        return buff.toString();
    }
	
	/**
	 * 将list中的每个元素以‘，’分隔，并去除了其中的null值。最后在头尾加上head和tail。
	 * */
	public static <T> String flat(Collection<T> list, String head, String tail) {
	    if (list == null)
	        return null;
	    if (list.size() == 0)
	        return head + tail;
	    StringBuilder buff = new StringBuilder(head);
        for (T token: list) {
            if (token == null)
                continue;
            buff.append(token.toString()).append(',');
        }
        buff.replace(buff.length()-1, buff.length(), tail);
        return buff.toString();
	}
	
	public static String inputStreamToString(InputStream in, String encoding) throws Exception {
        final int BUFFER_SIZE = 4069;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while((count = in.read(data,0,BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);
        data = null;
        return new String(outStream.toByteArray(), Charset.forName(encoding));
    }
	
	public static boolean equalsIgnoreCase(String s1, String s2) {
	    return s1.toUpperCase().equals(s2.toUpperCase());
	}

    public static String bitSetToString(BitSet bs) {
        return  String.format("%05d", Long.parseLong(Long.toString(bs.toLongArray()[0], 2)));
    }
    public static BitSet bitSetFromString(final String s) {

        return BitSet.valueOf(new long[]{Long.parseLong(s, 2)});
    }

    public static String reverseSort(String str) {
        String result = "";
        for (int i = str.length() - 1; i > -1; i--) {
            result += String.valueOf(str.charAt(i));
        }
        return result;
    }

    public static int lastDigital(String str) throws NumberFormatException {
	    StringBuilder result = new StringBuilder();
	    for(char c:str.toCharArray()){
	        if(Character.isDigit(c)){
	            result.append(c);
            }else {
	            result.setLength(0);
            }
        }
        return Integer.parseInt(result.toString());
    }
    public static Boolean isIpAddress(String s){
        String regex = "(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);
        return m.matches();
    }

	public static void main(String[] args) {
	    List<String> list = Arrays.asList("11", "22");
	    System.out.println(flat(list, "[", ")"));
            System.out.println(flat(list, "\",\"", "\"", "\""));
            System.out.println(flat(list, ",","",""));

        String hostname="scc22Mknode01";
        System.out.println(lastDigital(hostname));
        System.out.println(isIpAddress("99.48.232.159"));
        System.out.println(isIpAddress("99.48.232.256"));
    }


}
