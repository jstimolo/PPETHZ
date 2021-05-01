package ethz.ch.pp.searchAndCount;

import ethz.ch.pp.util.Workload;

public class SearchAndCountSeq {

	private int[] input;	
	Workload.Type workloadType;

	SearchAndCountSeq(int[] input, Workload.Type wt) {
		this.input = input;		
		this.workloadType = wt;
	}

	public static int countNoAppearances(int[] input, Workload.Type wt) {
		SearchAndCountSeq counter = new SearchAndCountSeq(input, wt);
		int num = counter.count();
		return num;
	}

	// count the number of elements for which doWork returns true
	int count() {
		int count = 0;
		for (int i = 0; i < input.length; i++) {
			//Count if HEAWY
			if (Workload.doWork(input[i], workloadType))
				count++;
		}		
		return count;		
	}

}