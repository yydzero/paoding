/**
 * Copyright 2007 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sohospace.dictionary.support;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import com.sohospace.dictionary.support.filewords.FileWordsReader;

/**
 * 
 * @author Zhiliang Wang [qieqie.wang@gmail.com]
 * 
 * @since 1.0
 * 
 */
public class Utils {


	public static int compare(CharSequence one, int begin, int count, CharSequence theOther) {
		for (int i = begin, j = 0; i < one.length() && j < Math.min(theOther.length(), count); i++, j++) {
			if (one.charAt(i) > theOther.charAt(j)){
				return 1;
			}
			else if (one.charAt(i) < theOther.charAt(j)){
				return -1;
			}
		}
		return count - theOther.length();
	}
	

	public static void main(String[] args) throws IOException {
		String dir = "CJK/locale/";
//		String name = "base";
//		String name = "����";
		String name = "����";
//		String name = "����-���";
//		String name = "x������λ";
//		String name = "����-�й�";
//		String name = "xcharacter";
//		String name = "���ս���";
		
		LinkedList<String> words = FileWordsReader.readWords(
				"dic/" + dir + name + ".dic").get(name);
		Set<String> set = new HashSet<String>(words);
		String[] array = set.toArray(new String[]{});
		Arrays.sort(array);
		//String last = "";
		for (int i = 0; i < array.length; i++) {
//			if (array[i].compareTo(last) <= 0) {
//				System.out.println(array[i] + "----" + last);
//			}
//			last = array[i];
			System.out.println(array[i]);
		}
		System.out.println("-" + array.length);
	}
	public static void main0(String[] args) throws IOException {
		String dir = "CJK";
		String name = "base";
//		String name = "����";
//		String name = "����-���";
//		String name = "x������λ";
//		String name = "����-�й�";
//		String name = "xcharacter";
//		String name = "���ս���";
		
		LinkedList<String> words = FileWordsReader.readWords(
				"dic/" + dir + name + ".dic").get(name);
		Set<String> set = new HashSet<String>(words);
		String[] array = set.toArray(new String[]{});
		Arrays.sort(array);
		for (int i = 0; i < array.length; i++) {
			System.out.println(array[i]);
		}
		System.out.println("-" + array.length);
	}
	

	public static void main5(String[] args) throws IOException {
		String dir = "CJK/";
		String name = "base";
		
		HashSet<Integer> �ַ��� = new HashSet<Integer>();
		�ַ���.add((int)'��');
		�ַ���.add((int)'һ');
		�ַ���.add((int)'��');
		�ַ���.add((int)'��');
		�ַ���.add((int)'��');
		�ַ���.add((int)'��');
		�ַ���.add((int)'��');
		�ַ���.add((int)'��');
		�ַ���.add((int)'��');
		�ַ���.add((int)'��');
		�ַ���.add((int)'��');
		�ַ���.add((int)'��');
		�ַ���.add((int)'ʮ');
		�ַ���.add((int)'��');
		�ַ���.add((int)'ǧ');
		�ַ���.add((int)'��');
		�ַ���.add((int)'��');
		
		LinkedList<String> words = FileWordsReader.readWords(
				"dic/" + dir + name + ".dic").get(name);
		System.out.println(words.size());
		Iterator<String> iter = words.iterator();
		while (iter.hasNext()) {
			String Ԫ�� = (String) iter.next();
			
			if (Ԫ��.equals("��ʮ��")) {
				System.out.println("--" + Ԫ��);
			}
			int i = 0;
			for (; i < Ԫ��.length(); i++) {
				if (!�ַ���.contains((int)Ԫ��.charAt(i))) {
					break;
				}
			}
			if (Ԫ��.equals("��ʮ��")) {
				System.out.println(i);
			}
			if (i == Ԫ��.length()) {
				System.out.println(Ԫ��);
				iter.remove();
			}
		}
		System.out.println(words.size());
	}
	
	public static boolean outb(char c) {
		return true;
	}
	
	   /**
     * �ִ�ȫ��ת��ǵĺ���(DBC case)
     * ȫ�ǿո�Ϊ12288����ǿո�Ϊ32
     * �����ַ����(33-126)��ȫ��(65281-65374)�Ķ�Ӧ��ϵ�ǣ������65248
     * @param input
     * @return
     */
    public static char toDbcCase(char src) {
		if (src == 12288) {
			src = (char) 32;
		}
		else if (src > 65280 && src < 65375) {
			src = (char) (src - 65248);
		}
		return src;
	}

}
