package ethz.ch.pp.searchAndCount;

import ethz.ch.pp.util.Workload;

public class SearchAndCountThreadDivideAndConquer  {

	
	public static int countNoAppearances(int[] input, Workload.Type wt, int cutOff, int numThreads) throws InterruptedException {
		int result = 0;
		int listSize = input.length;
		int subSectionSize = listSize/numThreads;
		
		SumThread[] ts = new SumThread[numThreads];
		
		for (int i = 0; i < numThreads; i++) {
			ts[i] = new SumThread(input, i * subSectionSize, (i + 1) * subSectionSize,numThreads,wt);
			//System.out.println("thread " + ts[i].getId() + " [" + i * subSectionSize + ".." + (i + 1) * subSectionSize + "]");
			ts[i].start();
		}

		// Combine results:
		for (int i = 0; i < numThreads; i++) {
			ts[i].join();
			result += ts[i].result;
		}

		return result;
	}
}
