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

import com.sohospace.paoding.LetterKnife;
import com.sohospace.paoding.NumberKnife;
import com.sohospace.paoding.Paoding;
import com.sohospace.paoding.cjk.CJKDictionaryFactory;
import com.sohospace.paoding.cjk.CJKKnife;
import com.sohospace.paoding.cjk.FileWordsLoader;

public class XFactory {

	private static Paoding paoding;

	private static XAnalyzer queryAnalyzer;

	private static XAnalyzer writerAnalyzer;

	public static XAnalyzer getQueryAnalyzer() {
		if (queryAnalyzer == null) {
			synchronized (XFactory.class) {
				if (queryAnalyzer == null) {
					queryAnalyzer = new XQueryAnalyzer(getPaoding());
				}
			}
		}
		return queryAnalyzer;
	}

	public static XAnalyzer getWriterAnalyzer() {
		if (writerAnalyzer == null) {
			synchronized (XFactory.class) {
				if (writerAnalyzer == null) {
					writerAnalyzer = new XWriterAnalyzer(getPaoding());
				}
			}
		}
		return writerAnalyzer;
	}

	public static Paoding getPaoding() {
		if (paoding == null) {
			synchronized (XFactory.class) {
				if (paoding == null) {
					CJKDictionaryFactory cjkDicFactory = createDicFactory();
					paoding = new Paoding();
					paoding.addKnife(new CJKKnife(cjkDicFactory));
					paoding.addKnife(new LetterKnife());
					paoding.addKnife(new NumberKnife(cjkDicFactory.getUnits()));
				}
			}
		}
		return paoding;
	}

	private static CJKDictionaryFactory createDicFactory() {
		CJKDictionaryFactory cjkDicFactory = new CJKDictionaryFactory();
		cjkDicFactory.setWordsLoader(new FileWordsLoader("dic/CJK"));
		return cjkDicFactory;
	}
}
