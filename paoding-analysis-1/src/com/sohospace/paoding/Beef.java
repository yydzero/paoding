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
package com.sohospace.paoding;

/**
 * {@link Beef}��Ҫ���Ҷ����⡱�ġ�ţ���⡱���Ƕ��ı��ַ����ĸ�Ч��װ�����Դ��ж�ȡָ��λ�õ��ַ���
 * <p>
 * {@link Beef}��{@link String}����Ĳ�֮ͬ�����ڣ�<br>
 * {@link Beef}���������char���飬{@link String}�Ĳ����ǶԹ���������п�¡����¡��������ܡ�<br>
 * ͬʱ��{@link Beef}�� {@link #charAt(int)}���������ж��ַ���Ԥ����ʹ����ʱ���Ϲ���:1)toLowerCase
 * 2)ȫ��ת��ǵ�
 * <p>
 * 
 * @author Zhiliang Wang [qieqie.wang@gmail.com]
 * 
 * @since 1.0
 * 
 */
public class Beef implements CharSequence {

	// -------------------------------------------------

	/**
	 * �ı��ַ�����
	 */
	private final char[] value;

	/**
	 * �ַ���ʼλ�ã���charAt(i)����value[offset+i]�ַ�
	 */
	private int offset;

	/**
	 * ��offsetλ�ÿ�ʼ���ַ���
	 */
	private int count;

	/** Cache the hash code for the beef */
	private int hash; // Default to 0

	// -------------------------------------------------

	/**
	 * ���캯��
	 * 
	 * @param body
	 *            ����������ֱ��ӵ�е��ı��ַ�����
	 * @param offset
	 *            �ַ���ʼλ�ã���get(i)����body[offset+i]�ַ�
	 * @param count
	 *            ��offsetλ�ÿ�ʼ���ַ���
	 */
	public Beef(char[] value, int offset, int count) {
		this.value = value;
		set(offset, count);
	}

	// -------------------------------------------------

	public void set(int offset, int count) {
        if (offset < 0) {
            throw new StringIndexOutOfBoundsException(offset);
        }
        if (count < 0) {
            throw new StringIndexOutOfBoundsException(count);
        }
        if (offset > value.length - count) {
            throw new StringIndexOutOfBoundsException(offset + count);
        }
		this.offset = offset;
		this.count = count;
	}

	public char[] getValue() {
		return value;
	}


	public int getCount() {
		return count;
	}

	public int getOffset() {
		return offset;
	}

	// -------------------------------------------------
	
	/**
	 * ��ȡָ��λ�õ��ַ�������֮ǰ����Ԥ����1)toLowerCase��2)ȫ��ת��ǵ�
	 */
	public char charAt(int index) {
		if (index >= 0 && index < count) {
			char src = value[offset + index];
			if (src > 65280 && src < 65375) {
				src = (char) (src - 65248);
				value[offset + index] = src;
			}
			if (src >= 'A' && src <= 'Z') {
				src += 32;
				value[offset + index] = src;
			} else if (src == 12288) {
				src = 32;
				value[offset + index] = 32;
			}
			return src;
		}
		return (char) -1;
	}

	public int length() {
		return count;
	}

	public CharSequence subSequence(int start, int end) {
		return new Beef(value, offset + start, end - start);
	}

	// -------------------------------------------------
	
	@Override
	public String toString() {
		return new String(value, offset, count);
	}

	@Override
	public int hashCode() {
		int h = hash;
		if (h == 0) {
			int off = offset;
			char val[] = value;
			int len = count;

			for (int i = 0; i < len; i++) {
				h = 31 * h + val[off++];
			}
			hash = h;
		}
		return h;
	}

}
