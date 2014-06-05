/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.mahout.cf.taste.similarity.precompute.example;

import java.io.File;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.precompute.FileSimilarItemsWriter;
import org.apache.mahout.cf.taste.impl.similarity.precompute.MultithreadedBatchItemSimilarities;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.similarity.precompute.BatchItemSimilarities;

/**
 * Example that precomputes all item similarities of the Movielens1M dataset
 * 
 * Usage: download movielens1M from http://www.grouplens.org/node/73 , unzip it
 * and invoke this code with the path to the ratings.dat file as argument
 * 
 */
public final class BatchItemSimilaritiesGroupLens {

	private BatchItemSimilaritiesGroupLens() {
	}

	public static void main(String[] args) throws Exception {
		
		//File inputFile = new File("data/ContentBasedItem.csv");
		File inputFile = new File("data/ContentBasedItem.csv");
		File resultFile = new File("data/ItemCSimilarities.csv");
		if (resultFile.exists()) {
			resultFile.delete();
		}

		DataModel dataModel = new FileDataModel(inputFile);
		ItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, new TanimotoCoefficientSimilarity(dataModel));
		BatchItemSimilarities batch = new MultithreadedBatchItemSimilarities(recommender, 2);
		int numSimilarities = batch.computeItemSimilarities(Runtime.getRuntime().availableProcessors(), 1,
				new FileSimilarItemsWriter(resultFile));

		System.out.println("Computed " + numSimilarities + " similarities for "
				+ dataModel.getNumItems() + " items " + "and saved them to "
				+ resultFile.getAbsolutePath());
		
		//System.out.println("Item- Item Recommendation -" + recommender.recommend(10001, 2));
		//System.out.println("Item- Item Recommendation -" + recommender.recommend(10003, 2));
		
		/*System.out.println("*******************User Based Algorith on same data******************");
		
		
		DataModel udataModel = new FileDataModel(new File(System.getProperty("java.io.tmpdir"), "ratings.txt"));
		UserSimilarity similarity = new PearsonCorrelationSimilarity(udataModel);
	    UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, udataModel);

	    Recommender urecommender = new GenericUserBasedRecommender(udataModel, neighborhood, similarity);
	    
	    System.out.println("User- User Recommendation -" + urecommender.recommend(10001, 2));
		System.out.println("User- User Recommendation -" + urecommender.recommend(10003, 2));*/
		
	}

}
