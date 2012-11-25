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
package com.sohospace.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * Dictionary��ɢ��+�������ʵ�֡�
 * <p>
 * ���ڶԴ������ģ���ͷ�ַ���ͬ���ַ����϶�������e.g���ִ����ֵ䡣����������£������ٶȽ��ȶ����ֵ���졣
 * <p>
 * 
 * HashBinaryDictionary��һ���Ѿ�����Ĵ���Ϊ���룬����<b>ͷ�ַ�</b>��ͬ�Ĵ��ﻮΪһ��������Ϊ���ֵ�(ʹ��BinaryDictionaryʵ��)��
 * ���Ҵ���ʱ���ȸ��ݵ�һ���ַ��ҵ÷ִʵ�(BinaryDictionaryʵ��)���ٴӸ÷ִʵ��ж�λ�ô��
 * <p>
 * 
 * @author Zhiliang Wang [qieqie.wang@gmail.com]
 * 
 * @see BinaryDictionary
 * 
 * @since 1.0
 * 
 */
public class HashBinaryDictionary implements Dictionary {

	// -------------------------------------------------

	/**
	 * �ֵ������д�����ڷ���{@link #get(int)}����
	 */
	private String[] ascWords;

	/**
	 * ���ַ����ִʵ��ӳ��
	 */
	private Map<Integer, SubDictionaryWrap> subs;
	
	/**
	 * 
	 */
	private final int hashIndex;
	
	private final int start;
	private final int end;
	private final int count;

	// -------------------------------------------------

	/**
	 * 
	 * @param ascWords
	 *            �������д���
	 * @param initialCapacity
	 * @param loadFactor
	 */
	public HashBinaryDictionary(String[] ascWords, int initialCapacity,
			float loadFactor) {
		this(ascWords, 0, 0, ascWords.length, initialCapacity, loadFactor);
	}
	
	public HashBinaryDictionary(String[] ascWords, int hashIndex, int start, int end, int initialCapacity,
			float loadFactor) {
		this.ascWords = ascWords;
		this.start = start;
		this.end = end;
		this.count = end - start;
		this.hashIndex = hashIndex;
		subs = new HashMap<Integer, SubDictionaryWrap>(initialCapacity, loadFactor);
		createSubDictionaries();
	}

	// -------------------------------------------------

	/**
	 * �����ִʵ�ӳ�䣬Ϊ���캯������
	 */
	protected void createSubDictionaries() {
		// ��λ��ͬͷ�ַ�����Ŀ�ͷ�ͽ���λ����ȷ�Ϸ��ֵ�
		int beginIndex = this.start;
		int endIndex = this.start + 1;

		char beginHashChar = getChar(ascWords[start], hashIndex);
		char endHashChar;
		for (; endIndex < this.end; endIndex++) {
			endHashChar = getChar(ascWords[endIndex], hashIndex);
			if (endHashChar != beginHashChar) {
				addSubDictionary(beginHashChar, beginIndex, endIndex);
				beginIndex = endIndex;
				beginHashChar = endHashChar;
			}
		}
		addSubDictionary(beginHashChar, beginIndex, this.end);
	}
	
	protected char getChar(String s, int index) {
		if (index >= s.length()) {
			return (char)0;
		}
		return s.charAt(index);
	}

	/**
	 * ��λ����beginIndex��endIndex֮��(������endIndex)�Ĵ�����Ϊһ���ִʵ�
	 * 
	 * @param hashChar
	 * @param beginIndex
	 * @param endIndex
	 */
	protected void addSubDictionary(char hashChar, int beginIndex, int endIndex) {
		SubDictionaryWrap subDic = new SubDictionaryWrap(
				hashChar,
				createSubDictionary(ascWords, beginIndex, endIndex), 
				beginIndex);
		Integer key = keyOf(hashChar);
		if (subs.containsKey(key)) {
			System.out.println("����������֣���ʾ����Ĵ������������ȷ���ʵ�������ȷ>>>>>>>>>"
					+ hashChar);
		}
		subs.put(key, subDic);
	}


	
	protected Dictionary createSubDictionary(String[] ascWords, int beginIndex, int endIndex) {
		int count = endIndex - beginIndex; 
		if (count < 16 ) {
			return new BinaryDictionary(ascWords, beginIndex, endIndex);
		}
		else {
			return new HashBinaryDictionary(
					ascWords, 
					hashIndex + 1,
					beginIndex, 
					endIndex, 
					getCapacity(count), 
					0.75f);
		}
	}
	
	protected static final int [] capacityCandiate = {16, 32, 64, 128,256,512,1024,2048,4096,10192};
	
	protected int getCapacity(int count) {
		int capacity = -1;
		count <<= 2;
		count /= 3;
		count += 1;
		for (int i = 0; i < capacityCandiate.length; i++) {
			if (count <= capacityCandiate[i]) {
				capacity = capacityCandiate[i];
				break;
			}
		}
		if (capacity < 0) {
			capacity = capacityCandiate[capacityCandiate.length - 1];
		}
		return capacity;
	}

	// -------------------------------------------------

	public String get(int index) {
		return ascWords[start + index];
	}

	public Hit search(CharSequence input, int begin, int count) {
		SubDictionaryWrap subDic = subs.get(keyOf(input.charAt(hashIndex + begin)));
		if (subDic == null) {
			return Hit.UNDEFINED;
		}
		Dictionary dic = subDic.dic;
		//��count==hashIndex + 1�Ĵ���
		if (count == hashIndex + 1) {
			String header = dic.get(0);
			if (header.length() == hashIndex + 1) {
				if (subDic.wordIndexOffset + 1 < this.ascWords.length) {
					return new Hit(subDic.wordIndexOffset, header, this.ascWords[subDic.wordIndexOffset + 1]);
				}
				else {
					return new Hit(subDic.wordIndexOffset, header, null);
				}
			}
			else {
				return new Hit(Hit.UNCLOSED_INDEX, null, header);
			}
		}
		//count > hashIndex + 1
		Hit word = dic.search(input, begin, count);
		if (word.isHit()) {
			int index = subDic.wordIndexOffset + word.getIndex();
			word.setIndex(index);
			if (word.getNext() == null && index < size()) {
				word.setNext(get(index + 1));
			}
		}
		return word;
	}

	public int size() {
		return count;
	}

	// -------------------------------------------------

	/**
	 * �ַ�����{@link #subs}��keyֵ��
	 * 
	 * @param theChar
	 * @return
	 * 
	 * @see #subs
	 */
	protected int keyOf(char theChar) {
		// return theChar - 0x4E00;// 'һ'==0x4E00
		return theChar;
	}

	/**
	 * �ִʵ����
	 */
	static class SubDictionaryWrap {
		/**
		 * �ִʵ�����ͷ�ַ�
		 */
		char hashChar;

		/**
		 * �ִʵ�
		 */
		Dictionary dic;

		/**
		 * �ִʵ��һ�����������д����е�ƫ��λ��
		 */
		int wordIndexOffset;

		public SubDictionaryWrap(char hashChar, Dictionary dic, int wordIndexOffset) {
			this.hashChar = hashChar;
			this.dic = dic;
			this.wordIndexOffset = wordIndexOffset;
		}
	}

}
