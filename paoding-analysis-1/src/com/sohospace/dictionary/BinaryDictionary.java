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

import static com.sohospace.dictionary.Hit.UNDEFINED;

import com.sohospace.dictionary.support.Utils;

/**
 * Dictionary�Ķ������ʵ�֡�
 * <p>
 * 
 * @author Zhiliang Wang [qieqie.wang@gmail.com]
 * 
 * @since 1.0
 * 
 */
public class BinaryDictionary implements Dictionary {

	// -------------------------------------------------

	private String[] ascWords;
	
	private final int start;
	private final int end;
	private final int count; 

	// -------------------------------------------------

	/**
	 * ��һ���������еĴ��ﹹ���������ֵ�
	 * <p>
	 * 
	 * @param ascWords
	 *            �������д���
	 */
	public BinaryDictionary(String[] ascWords) {
		this(ascWords, 0, ascWords.length);
	}
	
	public BinaryDictionary(String[] ascWords, int start, int end) {
		this.ascWords = ascWords;
		this.start = start;
		this.end = end;
		this.count = end - start;
	}

	// -------------------------------------------------
	
	public String get(int index) {
		return ascWords[start + index];
	}

	public int size() {
		return count;
	}

	public Hit search(CharSequence input, int begin, int count) {
		int left = this.start;
		int right = this.end - 1;
		int pointer = 0;
		String word = null;
		int relation;
		//
		while (left <= right) {
			pointer = (left + right) >> 1;
			word = ascWords[pointer];
			relation = Utils.compare(input, begin, count, word);
			if (relation == 0) {
				// System.out.println(new String(input,begin, count)+"***" +
				// word);
				int nextWordIndex = pointer + 1;
				if (nextWordIndex >= ascWords.length) {
					return new Hit(pointer, word, null);
				} else {
					return new Hit(pointer, word, ascWords[nextWordIndex]);
				}
			}
			if (relation < 0)
				right = pointer - 1;
			else
				left = pointer + 1;
		}
		//
		if (left >= ascWords.length) {
			return UNDEFINED;
		}
		//
		boolean asPrex = true;
		String nextWord = ascWords[left];
		// System.out.println(text);
		if (nextWord.length() < count) {
			asPrex = false;
		}
		for (int i = begin, j = 0; asPrex && j < count; i++, j++) {
			if (input.charAt(i) != nextWord.charAt(j)) {
				asPrex = false;
			}
		}
		return asPrex ? new Hit(Hit.UNCLOSED_INDEX, null, nextWord) : UNDEFINED;
	}

}
