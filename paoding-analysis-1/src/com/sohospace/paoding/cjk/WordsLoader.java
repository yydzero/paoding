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

import java.util.LinkedList;
/**
 * 
 * @author Zhiliang Wang [qieqie.wang@gmail.com]
 *
 */
public interface WordsLoader {

	public LinkedList<String> loadCJKVocabulary();

	public LinkedList<String> loadCJKConfucianFamilyNames();

	public LinkedList<String> loadCJKXwords();

	public LinkedList<String> loadCJKXchars();

	public LinkedList<String> loadCJKUnit();
}
