/**
 * 
 */
package edu.ucdenver.ccp.nlp.ext.uima.annotators.entitynormalization.protein.evaluation;

import java.io.File;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.junit.Test;
import org.uimafit.factory.TypeSystemDescriptionFactory;

import edu.ucdenver.ccp.common.test.DefaultTestCase;
import edu.ucdenver.ccp.nlp.core.uima.util.AnalysisEngineType;
import edu.ucdenver.ccp.nlp.core.uima.util.TypeSystemUtil;
import edu.ucdenver.ccp.nlp.ext.uima.annotators.entitynormalization.gene.evaluation.BioCreative3GnEvaluationPipeline;
import edu.ucdenver.ccp.nlp.ext.uima.annotators.entitynormalization.protein.ProOntologyProteinNormalization_AAE;

/**
 * Evaluates the HomologeneGroupGeneNormalization pipeline against gold standard corpora
 * 
 * @author Center for Computational Pharmacology, UC Denver; ccpsupport@ucdenver.edu
 * 
 */
public class ProteinOntologyNormalizationAE_Evaluation extends DefaultTestCase {

	private static final TypeSystemDescription TSD = TypeSystemDescriptionFactory.createTypeSystemDescription(
			TypeSystemUtil.CCP_TYPE_SYSTEM, "edu.ucdenver.ccp.nlp.ext.uima.syntax.TypeSystem");

	private static final File PRO_DICTIONARY_DIRECTORY = new File(
			"/data/NOT_BACKED_UP/projects/btrc/pro-dictionary/dictionary");

	@Test
	public void evaluateOnBioCreative3TrainingDataSet() throws AnalysisEngineProcessException,
			ResourceInitializationException {
		BioCreative3GnEvaluationPipeline evalPipeline = new BioCreative3GnEvaluationPipeline(TSD);
		evalPipeline.evaluateNormalizationPipeline(
				ProOntologyProteinNormalization_AAE.getAggregateDescription(TSD, PRO_DICTIONARY_DIRECTORY),
				AnalysisEngineType.AGGREGATE);
	}

}
