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
package com.sohospace.paoding.cjk;

import com.sohospace.dictionary.BinaryDictionary;
import com.sohospace.dictionary.Dictionary;
import com.sohospace.dictionary.HashBinaryDictionary;

/**
 * �����ֵ仺����ݵ�,Ϊ{@link CJKKnife}���á�<br>
 * �ӱ�������Ի�ȡ������Ҫ������ֵ䡣�����ʻ�����ϱ�������λ�����ԵĴʻ��ֵȡ�
 * <p>
 * ʹ��{@link CJKDictionaryFactory}��Ҫ����һ���ǿյ�{@link #wordsLoader}��
 * <p>
 * 
 * @author Zhiliang Wang [qieqie.wang@gmail.com]
 * 
 * @see CJKKnife
 * 
 * @since 1.0
 */
public class CJKDictionaryFactory {

	// -------------------------------------------------

	/**
	 * ���ڴ�Ŀ¼�����ݿ��л�ȡ����
	 */
	private WordsLoader wordsLoader;

	// -------------------------------------------------

	/**
	 * �ʻ���ֵ�
	 */
	private Dictionary vocabulary;

	/**
	 * �����ֵ�
	 * 
	 */
	private Dictionary confucianFamilyNames;

	/**
	 * ���Եĵ���
	 */
	private Dictionary xchars;

	/**
	 * ���ԵĴ���
	 * 
	 */
	private Dictionary xwords;

	/**
	 * ������λ
	 */
	private Dictionary units;

	// -------------------------------------------------

	public CJKDictionaryFactory() {
	}

	public CJKDictionaryFactory(WordsLoader wordsLoader) {
		this.wordsLoader = wordsLoader;
	}

	// -------------------------------------------------

	public WordsLoader getWordsLoader() {
		return wordsLoader;
	}

	public void setWordsLoader(WordsLoader wordsLoader) {
		this.wordsLoader = wordsLoader;
	}

	// -------------------------------------------------
	/**
	 * �ʻ���ֵ�
	 * 
	 * @return
	 */
	public Dictionary getVocabulary() {
		if (vocabulary == null) {
			synchronized (this) {
				if (vocabulary == null) {
					//�����5639�����д����ȡ0x2fff=x^13>8000>8000*0.75=6000>5639
					vocabulary = new HashBinaryDictionary(wordsLoader
							.loadCJKVocabulary().toArray(new String[0]), 0x2fff, 0.75f);
				}
			}
		}
		return vocabulary;
	}

	/**
	 * �����ֵ�
	 * 
	 * @return
	 */
	public Dictionary getConfucianFamilyNames() {
		if (confucianFamilyNames == null) {
			synchronized (this) {
				if (confucianFamilyNames == null) {
					confucianFamilyNames = new BinaryDictionary(wordsLoader
							.loadCJKConfucianFamilyNames().toArray(
									new String[0]));
				}
			}
		}
		return confucianFamilyNames;
	}

	/**
	 * ���ԵĴ���
	 * 
	 * @return
	 */
	public Dictionary getXchars() {
		if (xchars == null) {
			synchronized (this) {
				if (xchars == null) {
					xchars = new HashBinaryDictionary(wordsLoader.loadCJKXchars()
							.toArray(new String[0]), 256, 0.75f);
				}
			}
		}
		return xchars;
	}

	/**
	 * ���Եĵ���
	 * 
	 * @return
	 */
	public Dictionary getXwords() {
		if (xwords == null) {
			synchronized (this) {
				if (xwords == null) {
					xwords = new BinaryDictionary(wordsLoader.loadCJKXwords()
							.toArray(new String[0]));
				}
			}
		}
		return xwords;
	}

	/**
	 * ������λ
	 * 
	 * @return
	 */
	public Dictionary getUnits() {
		if (units == null) {
			synchronized (this) {
				if (units == null) {
					units = new HashBinaryDictionary(wordsLoader.loadCJKUnit()
							.toArray(new String[0]), 1024, 0.75f);
				}
			}
		}
		return units;
	}

}
