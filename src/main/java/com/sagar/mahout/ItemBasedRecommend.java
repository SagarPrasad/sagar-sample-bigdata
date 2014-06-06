package com.sagar.mahout;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.precompute.FileSimilarItemsWriter;
import org.apache.mahout.cf.taste.impl.similarity.precompute.MultithreadedBatchItemSimilarities;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.similarity.precompute.BatchItemSimilarities;

public class ItemBasedRecommend {
	
	private ItemBasedRecommend() {
	}

	public static void main(String[] args) throws Exception {
		File inputFile = new File("data/movies.csv");
		File resultFile = new File("data/ItemCSimilarities.csv");
		preComputeItemBasedRecommends(inputFile, resultFile);
	}

	/**
	 * Create an output file with Item Item relation
	 * @param inputFile
	 * @param resultFile
	 * @throws IOException
	 * @throws TasteException
	 */
	private static void preComputeItemBasedRecommends(File inputFile,
			File resultFile) throws IOException, TasteException {
		DataModel dataModel = new FileDataModel(inputFile);
		ItemBasedRecommender recommender = new GenericItemBasedRecommender(
				dataModel, new TanimotoCoefficientSimilarity(dataModel));
		BatchItemSimilarities batch = new MultithreadedBatchItemSimilarities(
				recommender, 2);
		int numSimilarities = batch.computeItemSimilarities(Runtime
				.getRuntime().availableProcessors(), 1,
				new FileSimilarItemsWriter(resultFile));

		System.out.println("Computed " + numSimilarities + " similarities for "
				+ dataModel.getNumItems() + " items " + "and saved them to "
				+ resultFile.getAbsolutePath());
	}
}
