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

import com.sohospace.dictionary.Dictionary;
import com.sohospace.dictionary.Hit;
import com.sohospace.paoding.CharSet;
import com.sohospace.paoding.Collector;
import com.sohospace.paoding.Knife;

/**
 * 
 * @author Zhiliang Wang [qieqie.wang@gmail.com]
 * 
 * @since 1.0
 * 
 */
public class CJKKnife implements Knife {

	// -------------------------------------------------


	private CJKDictionaryFactory factory;

	// -------------------------------------------------


	public CJKKnife() {
	}

	public CJKKnife(CJKDictionaryFactory factory) {
		this.factory = factory;
	}

	// -------------------------------------------------


	public CJKDictionaryFactory getFactory() {
		return factory;
	}

	public void setFactory(CJKDictionaryFactory factory) {
		this.factory = factory;
	}


	// -------------------------------------------------

	public boolean assignable(CharSequence beaf, int index) {
		return CharSet.isCjkUnifiedIdeographs(beaf.charAt(index));
	}
	
	public int dissect(Collector collector, CharSequence beaf, int offset) {
		if (CharSet.isCjkUnifiedIdeographs(beaf.charAt(beaf.length() - 1))
				 && offset > 0 && beaf.length() - offset < 50){
			return -offset;
		}
		Dictionary vocabulary = factory.getVocabulary();
		/* ����:�����ס�ڱ�����ˮ̶�Ÿ��� */
		// setup��end���ڹ涨��֮��������Ƿ�Ϊ�ʵ����
		int setup, end;
		// ΪunidentifiedIndex����Ϊ���ҳ��Ĵ������λ�õ�����ߣ�e.g '��','��','��','��'
		int identifiedEnd = offset;
		// ���ڶ�λδ�ִܷʵĿ�Ŀ�ʼλ�ã�e.g '��'
		int unidentifiedIndex = -1;
		//���ڸ����ж��Ƿ����shouldAWord()����
		int maxWordLength = 0;
		Hit word = null;
		for (setup = offset, end = offset; setup < beaf.length()
				&& CharSet.isCjkUnifiedIdeographs(beaf.charAt(setup)); end = ++setup) {
			for (int count = 1; end < beaf.length()
					&& CharSet.isCjkUnifiedIdeographs(beaf.charAt(end++)); count++) {
				//��һ��forѭ��ʱ��end=setup+1
				word = vocabulary.search(beaf, setup, count);
				if (word.isUndefined()) {
					if (unidentifiedIndex < 0 && setup >= identifiedEnd) {
						unidentifiedIndex = setup;
					}
					break;
				} else if (word.isHit()) {
					if (identifiedEnd < end) {
						identifiedEnd = end;
					}
					if (unidentifiedIndex >= 0) {
						dissectUnidentified(collector, beaf, unidentifiedIndex,
								setup - unidentifiedIndex);
						unidentifiedIndex = -1;
					}
					collector.collect(word.getWord(), setup, end);
					if (setup == offset && maxWordLength < count) {
						maxWordLength = count;
					}
					if (!(word.isUnclosed() && end < beaf.length()// ����ж���Ϊ�����жϷ���
					&& beaf.charAt(end) >= word.getNext().charAt(count))) {
						break;
					}
				}
			}
		}
		if (identifiedEnd != end) {
			dissectUnidentified(collector, beaf, identifiedEnd,
					end - identifiedEnd);
		}
		int len = end - offset;
		if (len > 2 && len != maxWordLength && shouldAWord(beaf, offset, end)) {
			collect(collector, beaf, offset, end);
		}
		return setup;//��ʱend=start
	}

	// -------------------------------------------------


	/**
	 * �ԷǴʻ���е��ִʷִ�
	 * 
	 * @param cellector
	 * @param beaf
	 * @param offset
	 * @param count
	 */
	protected void dissectUnidentified(Collector collector, CharSequence beaf,
			int offset, int count) {
		int end = offset + count;
		Hit word = null;
		int nearEnd = end - 1;
		for (int i = offset, j=i; i < end;) {
			j = skipXword(beaf, i, end);
			if (j >= 0 && i != j) {
				i = j;
				continue;
			}
			j = collectNumber(collector, beaf, i, end);
			if (j >= 0 && i != j) {
				i = j;
				continue;
			}
			word = factory.getXchars().search(beaf, i, 1);
			if (word.isHit()) {
				i++;
				continue;
			}
			// ͷ��
			if (i == offset) {
				// �ٶ����¼�=�ٶ�+��+...!=�ٶ�+����+...
				collect(collector, beaf, offset, offset + 1);
			}
			// β��
			if (i == nearEnd) {
				if (nearEnd != offset) {
					collect(collector, beaf, nearEnd, end);
				}
			}
			// ���Ԫ�ִ�
			else {
				collect(collector, beaf, i, i + 2);
			}
			i++;
		}
	}

	protected boolean shouldAWord(CharSequence beaf, int offset, int end) {
		if (offset > 0 && end < beaf.length()) {//ȷ��ǰ���ַ�����Ҳ���ַ�
			int prev = offset - 1;
			if (beaf.charAt(prev) == '��' && beaf.charAt(end) == '��') {
				return true;
			} else if (beaf.charAt(prev) == '��' && beaf.charAt(end) == '��') {
				return true;
			} else if (beaf.charAt(prev) == '\'' && beaf.charAt(end) == '\'') {
				return true;
			} else if (beaf.charAt(prev) == '\"' && beaf.charAt(end) == '\"') {
				return true;
			}
		}
		return false;
	}

	private final void collect(Collector collector, CharSequence beaf,
			int offset, int end) {
		collector.collect(beaf.subSequence(offset, end).toString(), offset, end);
	}

	private final int skipXword(CharSequence beaf, int offset, int end) {
		Hit word;
		for (int k = offset + 2; k <= end; k++) {
			word = factory.getXwords().search(beaf, offset, k - offset);
			if (word.isHit()) {
				offset = k;
			}
			if (word.isUndefined() || !word.isUnclosed()) {
				break;
			}
		}
		return offset;
	}

	private final int collectNumber(Collector collector, CharSequence beaf,
			int offset, int end) {
		int number1 = -1;
		int number2 = -1;
		int cur = offset;
		int bitValue = 0;
		int maxUnit = 0;
		boolean hasDigit = false;// ���ã�ȥ��û������ֻ�е�λ�ĺ��֣��硰�򡱣���ǧ��
		for (; cur <= end && (bitValue = toNumber(beaf.charAt(cur))) >= 0; cur++) {
			if (bitValue == 2
					&& (beaf.charAt(cur) == '��' || beaf.charAt(cur) == '��' || beaf
							.charAt(cur) == '�z')) {
				if (cur != offset)
					break;
			}
			if (bitValue >= 0 && bitValue < 10) {
				hasDigit = true;
				if (number2 < 0)
					number2 = bitValue;
				else {
					number2 *= 10;
					number2 += bitValue;
				}
			} else {
				if (number2 < 0) {
					if (number1 < 0) {
						number1 = 1;
					}
					number1 *= bitValue;
				} else {
					if (number1 < 0) {
						number1 = 0;
					}
					if (bitValue >= maxUnit) {
						number1 += number2;
						number1 *= bitValue;
						maxUnit = bitValue;
					} else {
						number1 += number2 * bitValue;
					}
				}
				number2 = -1;
			}
		}
		if (!hasDigit && cur < beaf.length()
				&& !factory.getUnits().search(beaf, cur, 1).isHit()) {
			return offset;
		}
		if (number2 > 0) {
			if (number1 < 0) {
				number1 = number2;
			} else {
				number1 += number2;
			}
		}
		if (number1 >= 0) {
			collector.collect(String.valueOf(number1), offset, cur);

			// ������ܸ��˼�����λ
			Hit wd;
			int i = cur + 1;
			while (i <= beaf.length()
					&& (wd = factory.getUnits().search(beaf, cur, i - cur))
							.isHit()) {
				collector.collect(String.valueOf(number1)
						+ beaf.subSequence(cur, i), offset, i);
				cur++;
				if (!wd.isUnclosed()) {
					break;
				}
				i++;
			}
		}
		return cur;
	}

	private final int toNumber(char c) {
		switch (c) {
		case '��':
		case '��':
			return 0;
		case 'һ':
		case 'Ҽ':
			return 1;
		case '��':
		case '��':
		case '��':
		case '�E':
			return 2;
		case '��':
		case '��':
			return 3;
		case '��':
		case '��':
			return 4;
		case '��':
		case '��':
			return 5;
		case '��':
		case '�':
			return 6;
		case '��':
		case '��':
			return 7;
		case '��':
		case '��':
			return 8;
		case '��':
		case '��':
			return 9;
		case 'ʮ':
		case 'ʲ':
			return 10;
		case '��':
		case '��':
			return 100;
		case 'ǧ':
		case 'Ǫ':
			return 1000;
		case '��':
		case '�f':
			return 10000;
		case '��':
		case '�|':
			return 100000000;
		default:
			return -1;
		}
	}


}
