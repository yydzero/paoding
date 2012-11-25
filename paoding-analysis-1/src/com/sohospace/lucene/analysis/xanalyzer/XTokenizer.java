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
package com.sohospace.lucene.analysis.xanalyzer;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;

import com.sohospace.lucene.analysis.xanalyzer.collector.QueryTokenCollector;
import com.sohospace.lucene.analysis.xanalyzer.collector.WriterTokenCollector;
import com.sohospace.paoding.Beef;
import com.sohospace.paoding.Collector;
import com.sohospace.paoding.Knife;
import com.sohospace.paoding.Paoding;

/**
 * XTokenizer�ǻ��ڡ��Ҷ���ţ����ܵ�TokenStreamʵ�֣�ΪXAnalyzerʹ�á�
 * <p>
 * 
 * @author Zhiliang Wang [qieqie.wang@gmail.com]
 * 
 * @see Beef
 * @see Knife
 * @see Paoding
 * @see Tokenizer
 * @see XAnalyzer
 * 
 * @see Collector
 * @see TokenCollector
 * @see QueryTokenCollector
 * @see WriterTokenCollector
 * 
 * @since 1.0
 */
public final class XTokenizer extends TokenStream implements Collector {

	// -------------------------------------------------

	/**
	 * �ı��ַ�Դ
	 * 
	 * @see #next()
	 */
	private final Reader input;
	
	/**
	 * 
	 */
	private static final int bufferLength = 128;

	/**
	 * ��������{@link #input}���ı��ַ�
	 * 
	 * @see #next()
	 */
	private final char[] buffer = new char[bufferLength];

	/**
	 * {@link buffer}[0]��{@link #input}�е�ƫ��
	 * 
	 * @see #collect(String, int, int)
	 * @see #next()
	 */
	private int offset;
	
	/**
	 * 
	 */
	private final Beef beef = new Beef(buffer, 0, 0);
	
	/**
	 * 
	 */
	private int dissected;

	/**
	 * ���ڷֽ�beef�е��ı��ַ�����XAnalyzer�ṩ
	 * 
	 * @see #next()
	 */
	private Knife knife;

	/**
	 * 
	 */
	private TokenCollector tokenCollector;

	/**
	 * tokens������������next()����˳���ȡtokens�е�Token����
	 * 
	 * @see #tokens
	 * @see #next()
	 */
	private Iterator<Token> tokenIteractor;

	// -------------------------------------------------

	/**
	 * 
	 * @param input
	 * @param knife
	 * @param tokenCollector
	 */
	public XTokenizer(Reader input, Knife knife, TokenCollector tokenCollector) {
		this.input = input;
		this.knife = knife;
		this.tokenCollector = tokenCollector;
	}

	// -------------------------------------------------

	public TokenCollector getTokenCollector() {
		return tokenCollector;
	}

	public void setTokenCollector(TokenCollector tokenCollector) {
		this.tokenCollector = tokenCollector;
	}

	// -------------------------------------------------

	
	public void collect(String word, int offset, int end) {
		tokenCollector.collect(word, this.offset + offset, this.offset + end);
	}

	// -------------------------------------------------
	@Override
	public Token next() throws IOException {
		// �Ѿ��tokensIteractor��Token�������������reader��������
		while (tokenIteractor == null || !tokenIteractor.hasNext()) {
			System.out.println(dissected);
			int read = 0;
			int remainning = -1;//���´�reader�����ַ�ǰ��buffer�л�ʣ�µ��ַ�����������ʾ��ǰ�ݲ���Ҫ��reader�ж����ַ�
			if (dissected >= beef.length()) {
				remainning = 0;
			}
			else if (dissected < 0){
				remainning = bufferLength + dissected;
			}
			if (remainning >= 0) {
				if (remainning > 0) {
					System.arraycopy(buffer, -dissected, buffer, 0, remainning);
				}
				read = input.read(buffer, remainning, bufferLength - remainning);
				int charCount = remainning + read;
				if (charCount < 0) {
					// reader�Ѿ������ӿ�next()Ҫ�󷵻�null.
					return null;
				}
				if (charCount < bufferLength) {
					buffer[charCount ++] = 0;
				}
				// ���조ţ������ʹ��knife���⡱֮
				beef.set(0, charCount);
				offset += Math.abs(dissected);
				//offset -= remainning;
				dissected = 0;
			}
			dissected = knife.dissect((Collector)this, beef, dissected);
//			offset += read;// !!!
			tokenIteractor = tokenCollector.iterator();
		}
		// ����tokensIteractor��һ��Token����
		return tokenIteractor.next();
	}

	// -------------------------------------------------

	@Override
	public void close() throws IOException {
		super.close();
		input.close();
	}

}
