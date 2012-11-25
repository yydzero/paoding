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
 * Collector����Knife�и��ı��õ��Ĵ��
 * <p>
 * 
 * @author Zhiliang Wang [qieqie.wang@gmail.com]
 * 
 * @see Knife
 * 
 * @since 1.0
 * 
 */
public interface Collector {

	/**
	 * ��Knife���ı����л�ȡһ������ʱ�������������á� <br>
	 * ���õ�˳����������ı����е�˳���Ƿ�һ���Ӳ�ͬʵ�ֿ����в�ͬ�Ĳ��ԡ�
	 * <p>
	 * 
	 * �統Knife�յ����й�������������ı����еġ���ᡱʱ������Ĳ����ֱ��ǣ�(����ᡱ, 4, 6)
	 * 
	 * @param word
	 *            ���յ��Ĵ���
	 * @param offset
	 *            �ô������ı����е�ƫ��λ��
	 * @param end
	 *            �ô������ı����еĽ���λ��(���ﲻ�����ı���endλ�õ��ַ�)��end-offset��Ϊword�ĳ���
	 * 
	 *         
	 */
	public void collect(String word, int offset, int end);
}
