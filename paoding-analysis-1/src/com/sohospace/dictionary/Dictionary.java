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
 * Dictionary��һ��ֻ���ֵ䣬���ڲ����Ƿ����ĳ������Լ������Ϣ��
 * <p>
 * 
 * @author Zhiliang Wang [qieqie.wang@gmail.com]
 * 
 * @see BinaryDictionary
 * @see HashBinaryDictionary
 * 
 * @since 1.0
 * 
 */
public interface Dictionary {

	/**
	 * �����ֵ��д�����>=0
	 * 
	 * @return
	 */
	public int size();

	/**
	 * ���ظ���λ�õĴ���
	 * 
	 * @param index
	 *            0,1,2,...,size-1
	 * @return
	 */
	public String get(int index);

	/**
	 * �����ʵ��Ƿ��ռ�input[offset]��input[offset+count-1]֮���ַ���(�����߽�)�Ĵʡ�<br>
	 * ��������Էǿ�Hit���������
	 * <p>
	 * @param input Ҫ�������ַ���������������һ����
	 * @param offset Ҫ�������ַ�����ʼλ�����input��ƫ��
	 * @param count Ҫ�������ַ����ַ�����
	 * @return ���ص�Hit����ǿգ�����ͨ��hit�����ṩ�ķ����ж��������
	 * 
	 * @see Hit
	 */
	public Hit search(CharSequence input, int offset, int count);
}
