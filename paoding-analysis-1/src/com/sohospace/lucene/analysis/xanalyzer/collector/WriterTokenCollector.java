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
package com.sohospace.lucene.analysis.xanalyzer.collector;

import java.util.Iterator;
import java.util.LinkedList;

import org.apache.lucene.analysis.Token;

import com.sohospace.lucene.analysis.xanalyzer.TokenCollector;
/**
 * 
 * @author Zhiliang Wang [qieqie.wang@gmail.com]
 *
 * @since 1.1
 */
public class WriterTokenCollector implements TokenCollector {

	/**
	 * �洢��ǰ��knife�ֽ���ɵ�Token����
	 * 
	 */
	private LinkedList<Token> tokens;

	/**
	 * Collector�ӿ�ʵ�֡�<br>
	 * �������Token���󣬲�������tokens��
	 * 
	 */
	public void collect(String word, int begin, int end) {
		if (tokens == null) {
			this.tokens = new LinkedList<Token>();
		}
		this.tokens.add(new Token(word, begin, end));
	}

	public Iterator<Token> iterator() {
		if (this.tokens == null) {
			this.tokens = new LinkedList<Token>();
		}
		Iterator<Token> iter = this.tokens.iterator();
		this.tokens = null;
		return iter;
	}

}
