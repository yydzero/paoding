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

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

import com.sohospace.lucene.analysis.xanalyzer.collector.QueryTokenCollector;
import com.sohospace.lucene.analysis.xanalyzer.collector.WriterTokenCollector;
import com.sohospace.paoding.Knife;
import com.sohospace.paoding.Paoding;
import com.sohospace.paoding.cjk.CJKKnife;

/**
 * XAnalyzer�ǻ��ڡ��Ҷ���ţ����ܵ�Lucene������������ǡ��Ҷ���ţ����ܶ�Lucene����������
 * <p>
 * 
 * XAnalyzer���̰߳�ȫ�ģ����������ʹ��ͬһ��XAnalyzerʵ���ǿ��еġ�<br>
 * XAnalyzer�ǿɸ��õģ��Ƽ����ͬһ��XAnalyzerʵ����
 * <p>
 * 
 * ������Ҫ�ر������Ӧͨ�����캯����knife������(setter)�����Զ��Ƶ�Knifeʵ����
 * <p>
 * 
 * @author Zhiliang Wang [qieqie.wang@gmail.com]
 * 
 * @see XWriterAnalyzer
 * @see XQueryAnalyzer
 * 
 * @see XTokenizer
 * @see Knife
 * @see Paoding
 * @see CJKKnife
 * @see TokenCollector
 * 
 * @since 1.0
 * 
 */
public class XAnalyzer extends Analyzer {

	// -------------------------------------------------

	/**
	 * ��ģʽ�ڽ�������ʱʹ�ã��ܹ�ʹ��������ÿ�����ܵĴ��ｨ������
	 */
	public static final int WRITER_MODE = 1;

	/**
	 * ��ģʽ���û�����ʱʹ�ã�ʹ�û������Ľ��ƥ������
	 */
	public static final int QUERY_MODE = 2;

	// -------------------------------------------------
	/**
	 * ������XTokenizer�ṩ���ֽ��ı��ַ�
	 * 
	 * @see XTokenizer#next()
	 * 
	 */
	private Knife knife;

	/**
	 * @see #WRITER_MODE
	 * @see #QUERY_MODE
	 */
	private int mode = WRITER_MODE;

	// -------------------------------------------------

	public XAnalyzer() {
	}

	public XAnalyzer(Knife knife) {
		this.knife = knife;
	}

	// -------------------------------------------------

	public Knife getKnife() {
		return knife;
	}

	public void setKnife(Knife knife) {
		this.knife = knife;
	}

	public int getMode() {
		return mode;
	}

	/**
	 * ���÷�����ģʽ��дģʽ(WRITER_MODE)�����ģʽ(QUERY_MODE)����һ�֡�Ĭ��Ϊдģʽ��
	 * <p>
	 * WRITER_MODE�ڽ�������ʱʹ�ã��ܹ�ʹ��������ÿ�����ܵĴ��ｨ������<br>
	 * QUERY_MODE���û�����ʱʹ�ã�ʹ�û������Ľ��ƥ������
	 * 
	 * @param mode
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}

	// -------------------------------------------------

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
		if (knife == null) {
			throw new NullPointerException("knife should be set before token");
		}
		// XTokenizer��TokenStreamʵ�֣�ʹ��knife����reader������ı�
		return new XTokenizer(reader, knife, createTokenCollector());
	}

	protected TokenCollector createTokenCollector() {
		switch (mode) {
		case WRITER_MODE:
			return new WriterTokenCollector();
		case QUERY_MODE:
			return new QueryTokenCollector();
		default:
			throw new IllegalArgumentException("wrong mode");
		}
	}

}
