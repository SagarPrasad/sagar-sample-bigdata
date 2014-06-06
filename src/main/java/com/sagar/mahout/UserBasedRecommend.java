package com.sagar.mahout;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.*;
import org.apache.mahout.cf.taste.impl.neighborhood.*;
import org.apache.mahout.cf.taste.impl.recommender.*;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.*;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.*;
import org.apache.mahout.cf.taste.similarity.*;

import java.io.*;
import java.util.*;

class UserBasedRecommend {

	private UserBasedRecommend() {
	}

	public static void main(String[] args) throws Exception {
		List<RecommendedItem> recommendations = getUserBasedRecommends("data/intro.csv", 3, 2);

		for (RecommendedItem recommendation : recommendations) {
			System.out.println(recommendation);
		}
	}

	/**
	 * Returns the list of Recommendation
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @throws TasteException
	 */
	private static List<RecommendedItem> getUserBasedRecommends(String filePath, int userId, int noOfRecommendation)
			throws IOException, TasteException {
		// creating data model from the csv file user,item,pref
		DataModel model = new FileDataModel(new File(filePath));
		// Create user similarity based on model
		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		// Creates neighbourhood using similarity & model, first arg define the boundry
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(2,
				similarity, model);
		// Creates recommender based on neighbourhood and similarity
		Recommender recommender = new GenericUserBasedRecommender(model,
				neighborhood, similarity);
		List<RecommendedItem> recommendations = recommender.recommend(userId, noOfRecommendation);
		return recommendations;
	}

}