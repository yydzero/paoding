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

/**
 * Hit�Ǽ����ֵ�ʱ���صĽ���������ֵ�ʱ�����Ƿ���һ���ǿյ�Hit�����ʾ���ܵĸ��������
 * <p>
 * 
 * Hit�������2���ж���Ϣ��
 * <li>Ҫ�����Ĵ����Ƿ�����ڴʵ���: {@link #isHit()}</li>
 * <li>�ʵ��Ƿ����Ը����ַ�����ͷ����������: {@link #isUnclosed()}</li>
 * <br>
 * �������2����Ϣ���Ƿ񶨵ģ��� {@link #isUndefined()}����true�����򷵻�false. <br>
 * <br>
 * 
 * ���{@link #isHit()}����true����{@link #getWord()}���ز��ҽ����{@link #getNext()}������һ�����<br>
 * ���{@link #isHit()}����false����{@link #isUnclosed()}����true��{@link #getNext()}����������ѯ���￪ͷ��λ���ǰ�Ĵ��
 * <p>
 * 
 * @author Zhiliang Wang [qieqie.wang@gmail.com]
 * 
 * @see Dictionary
 * @see BinaryDictionary
 * @see HashBinaryDictionary
 * 
 * @since 1.0
 * 
 */
public class Hit {

	// -------------------------------------------------

	public final static int UNCLOSED_INDEX = -1;

	public final static int UNDEFINED_INDEX = -2;

	public final static Hit UNDEFINED = new Hit(UNDEFINED_INDEX, null, null);

	// -------------------------------------------------

	/**
	 * Ŀ������ڴʵ��е�λ�ã��������ֵ�û�иô����Ǳ�ʾ������˼(�μ����Ͼ�̬������������)
	 */
	private int index;

	/**
	 * ��������ʱ���ʵ�����Ӧ�Ĵ�
	 */
	private String word;

	/**
	 * �ʵ������дʵ���һ�����ʣ���{@link #isUnclosed()}Ϊtrueʱ��ӽ�����һ����(�μ������ע��)
	 */
	private String next;

	// -------------------------------------------------

	/**
	 * 
	 * @param index
	 *            Ŀ������ڴʵ��е�λ�ã��������ֵ�û�иô����Ǳ�ʾ������˼(�μ����Ͼ�̬������������)
	 * @param word
	 *            ��������ʱ���ʵ�����Ӧ�Ĵ�
	 * @param next
	 *            �ʵ������дʵ���һ�����ʣ���{@link #isUnclosed()}Ϊtrueʱ��ӽ�����һ����(�μ������ע��)
	 */
	public Hit(int index, String word, String next) {
		this.index = index;
		this.word = word;
		this.next = next;
	}

	// -------------------------------------------------

	/**
	 * ��������ʱ���ʵ�����Ӧ�Ĵ�
	 */
	public String getWord() {
		return word;
	}

	/**
	 * Ŀ������ڴʵ��е�λ�ã��������ֵ�û�иô����Ǳ�ʾ������˼(�μ����Ͼ�̬������������)
	 * @return
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * �ʵ������дʵ���һ�����ʣ���{@link #isUnclosed()}Ϊtrueʱ��ӽ�����һ����(�μ������ע��)
	 * @return
	 */
	public String getNext() {
		return next;
	}
	
	/**
	 * �Ƿ����ֵ��м�����Ҫ�����Ĵ���
	 * @return
	 */
	public boolean isHit() {
		return this.index >= 0;
	}

	/**
	 * �Ƿ����Ե�ǰ�������￪ͷ�Ĵ���
	 * @return
	 */
	public boolean isUnclosed() {
		return UNCLOSED_INDEX == this.index
				|| (this.next != null
						&& this.next.length() >= this.word.length() && this.next
						.startsWith(word));
	}

	/**
	 * �ֵ���û�е�ǰ�����Ĵ�������俪ͷ�Ĵ���
	 * @return
	 */
	public boolean isUndefined() {
		return UNDEFINED.index == this.index;
	}

	// -------------------------------------------------

	void setIndex(int index) {
		this.index = index;
	}

	void setWord(String key) {
		this.word = key;
	}

	void setNext(String next) {
		this.next = next;
	}

	// -------------------------------------------------

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((word == null) ? 0 : word.hashCode());
		result = PRIME * result + index;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Hit other = (Hit) obj;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		if (index != other.index)
			return false;
		return true;
	}

	public String toString() {
		if (isUnclosed()) {
			return "[UNCLOSED]";
		} else if (isUndefined()) {
			return "[UNDEFINED]";
		}
		return "[" + index + ']' + word;
	}

}
