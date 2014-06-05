/**
 * 
 */
package com.sagar.mahout;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.DataModelBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/**
 * @author A039883
 *
 */
public class EvaluateRecommender {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws TasteException 
	 */
	public static void main(String[] args) throws IOException, TasteException {
		DataModel model = new FileDataModel(new File("data/intro.csv"));
		RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
		EvaluateRecommender er = new EvaluateRecommender();
		RecommenderBuilder builder = new MyRecommenderBuilder();
		builder.buildRecommender(model);
		DataModelBuilder dmb = new MyBuilder();
		double result = evaluator.evaluate(builder, dmb, model, 0.7, 0.3);
		System.out.println(result);
	}
}

class MyRecommenderBuilder implements RecommenderBuilder {
	public Recommender buildRecommender(DataModel dataModel)
			throws TasteException {
		UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, dataModel);
		return new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
	}
}

class MyBuilder implements DataModelBuilder {
	public DataModel buildDataModel(FastByIDMap<PreferenceArray> trainingData) {
		 return new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(trainingData));
	}
	
}