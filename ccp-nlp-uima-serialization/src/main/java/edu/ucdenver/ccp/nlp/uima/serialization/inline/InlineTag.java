package edu.ucdenver.ccp.nlp.uima.serialization.inline;

/*
 * #%L
 * Colorado Computational Pharmacology's nlp module
 * %%
 * Copyright (C) 2012 - 2014 Regents of the University of Colorado
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Regents of the University of Colorado nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

import java.util.Comparator;

import edu.ucdenver.ccp.nlp.core.annotation.Span;

/**
 * This is a base class designed to represent an inline tag. No assumptions are made as to the
 * content of this tag, that is left to be created/controlled elsewhere.
 * 
 * @author Colorado Computational Pharmacology, UC Denver; ccpsupport@ucdenver.edu
 * 
 */
public abstract class InlineTag implements Comparable<InlineTag> {
	/**
	 * Stores the tag contents, e.g. "<protein>" or perhaps "/NN"
	 */
	private final String tagContents;

	/**
	 * The span for the annotation that this tag corresponds to
	 */
	private final Span annotationSpan;

	/**
	 * Constructs a new {@link InlineTag} object
	 * 
	 * @param tagContents
	 *            the tag contents, e.g. "<protein>" or perhaps "/NN"
	 * @param annotationSpan
	 *            the span for the annotation that this tag corresponds to
	 */
	public InlineTag(String tagContents, Span annotationSpan) {
		super();
		this.tagContents = tagContents;
		this.annotationSpan = annotationSpan;
	}

	/**
	 * @return the tag contents
	 */
	public String getTagContents() {
		return tagContents;
	}

	/**
	 * @return the span for the annotation that this tag corresponds to
	 */
	public Span getAnnotationSpan() {
		return annotationSpan;
	}

	/**
	 * Comparison of InlineTags will differ depending on context. This method is therefore not
	 * implemented. Please use the implemented comparators instead when sorting. See
	 * getInlinePrefixTagComparator() and getInlinePostfixTagComparator().
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * @throws UnsupportedOperationException
	 *             if this method is called. See comment above.
	 */
	@Override
	public int compareTo(@SuppressWarnings("unused") InlineTag o) {
		throw new UnsupportedOperationException(
				"Comparison of InlineTags will differ depending on context. This method is therefore not implemented. Please use the implemented comparators instead when sorting. See getInlinePrefixTagComparator() and getInlinePostfixTagComparator().");
	}

	/**
	 * Represents an inline tag that occurs before the annotation it is associated with, e.g.
	 * "<protein>"
	 * 
	 * @author bill
	 * 
	 */
	public static class InlinePrefixTag extends InlineTag {

		/**
		 * Constructs a new {@link InlinePrefixTag} object
		 * 
		 * @param tagContents
		 *            the tag contents, e.g. "<protein>" or perhaps "/NN"
		 * @param annotationSpan
		 *            the span for the annotation that this tag corresponds to
		 */
		public InlinePrefixTag(String tagContents, Span annotationSpan) {
			super(tagContents, annotationSpan);
		}

	}

	/**
	 * Represents an inline tag that occurs after the annotation it is associated with, e.g.
	 * "</protein>" or "/NN"
	 * 
	 * @author bill
	 * 
	 */
	public static class InlinePostfixTag extends InlineTag {

		/**
		 * Constructs a new {@link InlinePostfixTag} object
		 * 
		 * @param tagContents
		 *            the tag contents, e.g. "<protein>" or perhaps "/NN"
		 * @param annotationSpan
		 *            the span for the annotation that this tag corresponds to
		 */
		public InlinePostfixTag(String tagContents, Span annotationSpan) {
			super(tagContents, annotationSpan);
		}
	}

	/**
	 * Compares {@link InlinePrefixTag} instances. If the end spans are not the same, then the tags
	 * are ordered such that those with a larger end span appear first. If the end spans are
	 * identical then the sort order defaults to a comparison of the tag content {@link String}. It
	 * is assumed that the tag span starts are identical.
	 * 
	 * @return an initialized {@link Comparator} suitable for sorting {@link InlinePrefixTag}
	 *         instances that have identical start span values
	 */
	public static Comparator<? super InlinePrefixTag> getInlinePrefixTagComparator() {
		return new Comparator<InlinePrefixTag>() {

			@Override
			public int compare(InlinePrefixTag tag1, InlinePrefixTag tag2) {
				return tag1.getAnnotationSpan().getSpanEnd() == tag2.getAnnotationSpan().getSpanEnd() ? tag1
						.getTagContents().compareTo(tag2.getTagContents()) : tag2.getAnnotationSpan().getSpanEnd()
						- tag1.getAnnotationSpan().getSpanEnd();
			}

		};
	}

	/**
	 * Compares {@link InlinePostfix} instances. If the start spans are not the same, then the tags
	 * are ordered such that those with a small start span offset appear first. If the start spans
	 * are identical then the sort order defaults to a comparison of the tag content {@link String}.
	 * It is assumed that the tag span end offsets are identical.
	 * 
	 * @return an initialized {@link Comparator} suitable for sorting {@link InlinePrefixTag}
	 *         instances that have identical start span values
	 */
	public static Comparator<? super InlinePostfixTag> getInlinePostfixTagComparator() {
		return new Comparator<InlinePostfixTag>() {

			@Override
			public int compare(InlinePostfixTag tag1, InlinePostfixTag tag2) {
				return tag1.getAnnotationSpan().getSpanStart() == tag2.getAnnotationSpan().getSpanStart() ? tag2
						.getTagContents().compareTo(tag1.getTagContents()) : tag2.getAnnotationSpan().getSpanStart()
						- tag1.getAnnotationSpan().getSpanStart();
			}

		};
	}
}
