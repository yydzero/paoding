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
 * Paoding��һ�����š����䡱(�ڲظ��֡�����)������������ţ�����ˣ������Ҷ�����
 * <p>
 * ����Ϊ��ӵ�и��ֲ�ͬ�ġ������������ܹ�ʶ��ʲô����(�ַ�)��Ӧ����ʲô�������ָ����������������ذ���ͷţ�и��Ϊ���ʵġ���Ƭ(����)���� <br>
 * ����ġ�������Knife���ݣ����֡������ɡ����䡱KnifeBox����(Paoding���������һ��KnifeBox)������KnifeBox����ʲôʱ���ʲô��������
 * 
 * @author Zhiliang Wang [qieqie.wang@gmail.com]
 * 
 * @see Knife
 * @see KnifeBox
 * @see KnifeBoxBean
 * 
 * @since 1.0
 */
public final class Paoding extends KnifeBox implements Knife {

	// -------------------------------------------------
	
	public int dissect(Collector collector, CharSequence beaf, int offset) {
		while (offset >=0 && offset < beaf.length()) {
			offset = super.dissect(collector, beaf, offset);
		}
		return offset;
	}

}
