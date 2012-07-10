/*
 Copyright (c) 2012, Regents of the University of Colorado
 All rights reserved.

 Redistribution and use in source and binary forms, with or without modification, 
 are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this 
    list of conditions and the following disclaimer.
   
 * Redistributions in binary form must reproduce the above copyright notice, 
    this list of conditions and the following disclaimer in the documentation 
    and/or other materials provided with the distribution.
   
 * Neither the name of the University of Colorado nor the names of its 
    contributors may be used to endorse or promote products derived from this 
    software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
 ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package edu.ucdenver.ccp.nlp.core.annotation.comparison;

import java.util.List;

import edu.ucdenver.ccp.nlp.core.annotation.Span;

/**
 * 
 * For "shared start" comparison, the spans are required to have the same starting index in order to return 0. If they
 * do not share the same span start then if the span start for span1 is prior to the span start for span2, then -1 is
 * returned, 1 otherwise.
 * 
 * @author Colorado Computational Pharmacology, UC Denver; ccpsupport@ucdenver.edu
 * 
 */
public class SharedStartSpanComparator extends SpanComparator {

	public SharedStartSpanComparator() {
		/* spansMustOverlapToMatch = true */
		super(true);
	}

	/**
	 * Compare two spans using the shares-span-start match criteria.
	 */
	@Override
	public int compare(Span span1, Span span2) {
		return sharesSpanStart(span1, span2);
	}

	/**
	 * Compare two lists of spans using the shares-span-start match criteria.
	 * 
	 * @param spanList1
	 * @param spanList2
	 * @return
	 */
	@Override
	public int compare(List<Span> spanList1, List<Span> spanList2) {
		return sharesSpanStart(spanList1, spanList2);
	}

}
