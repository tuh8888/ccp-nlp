package edu.ucdenver.ccp.nlp.uima.mention.impl;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.DoubleArray;

import edu.ucdenver.ccp.nlp.core.annotation.impl.KnowledgeRepresentationWrapperException;
import edu.ucdenver.ccp.nlp.core.mention.DoubleSlotMention;
import edu.ucdenver.ccp.nlp.core.mention.InvalidInputException;
import edu.ucdenver.ccp.nlp.core.uima.mention.CCPDoubleSlotMention;
import edu.ucdenver.ccp.nlp.uima.util.UIMA_Util;

/**
 * Wrapper class for the {@link CCPDoubleSlotMention} that complies with the
 * {@link DoubleSlotMention} abstract class
 * 
 * @author Colorado Computational Pharmacology, UC Denver; ccpsupport@ucdenver.edu
 * 
 */
public class WrappedCCPDoubleSlotMention extends DoubleSlotMention {

	private CCPDoubleSlotMention wrappedSM;
	private JCas jcas;

	public WrappedCCPDoubleSlotMention(CCPDoubleSlotMention ccpSSM) {
		super(ccpSSM);
	}

	@Override
	public CCPDoubleSlotMention getWrappedObject() {
		return wrappedSM;
	}

	@Override
	protected void initializeFromWrappedMention(Object... wrappedObjectPlusGlobalVars) {
		if (wrappedObjectPlusGlobalVars.length == 1) {
			Object wrappedObject = wrappedObjectPlusGlobalVars[0];
			if (wrappedObject instanceof CCPDoubleSlotMention) {
				wrappedSM = (CCPDoubleSlotMention) wrappedObject;
				try {
					jcas = wrappedSM.getCAS().getJCas();
				} catch (CASException e) {
					throw new RuntimeException(e);
				}
			} else {
				throw new KnowledgeRepresentationWrapperException(
						"Expected CCPNonComplexSlotMention. Cannot wrap class " + wrappedObject.getClass().getName()
								+ " inside a WrappedCCPDoubleSlotMention.");
			}
		} else {
			throw new KnowledgeRepresentationWrapperException(
					"Single input parameter expected for WrappedCCPDoubleMention. Instead, observed "
							+ wrappedObjectPlusGlobalVars.length + " parameter(s)");
		}
	}

	public void addSlotValue(Double slotValue) throws InvalidInputException {
		DoubleArray updatedDoubleArray = UIMA_Util.addToDoubleArray(wrappedSM.getSlotValues(), slotValue, jcas);
		wrappedSM.setSlotValues(updatedDoubleArray);
	}

	public void addSlotValues(Collection<Double> slotValues) throws InvalidInputException {
		for (Double floatToAdd : slotValues) {
			addSlotValue(floatToAdd);
		}
	}

	public Collection<Double> getSlotValues() {
		Collection<Double> slotValuesToReturn = new ArrayList<Double>();
		DoubleArray slotValues = wrappedSM.getSlotValues();
		for (int i = 0; i < slotValues.size(); i++) {
			slotValuesToReturn.add(slotValues.get(i));
		}
		return slotValuesToReturn;
	}

	public void overwriteSlotValues(Double slotValue) throws InvalidInputException {
		DoubleArray floatArray = new DoubleArray(jcas, 1);
		floatArray.set(0, slotValue);
		wrappedSM.setSlotValues(floatArray);
	}

	public void setSlotValues(Collection<Double> slotValues) throws InvalidInputException {
		List<Double> slotValuesList = new ArrayList<Double>(slotValues);
		DoubleArray floatArray = new DoubleArray(jcas, slotValues.size());
		for (int i = 0; i < slotValues.size(); i++) {
			floatArray.set(i, slotValuesList.get(i));
		}
		wrappedSM.setSlotValues(floatArray);
	}

	@Override
	public long getMentionID() {
		return wrappedSM.getMentionID();
	}

	@Override
	public String getMentionName() {
		return wrappedSM.getMentionName();
	}

	@Override
	public void setMentionID(long mentionID) {
		wrappedSM.setMentionID(mentionID);
	}

	@Override
	public void setMentionName(String mentionName) {
		wrappedSM.setMentionName(mentionName);
	}

}
