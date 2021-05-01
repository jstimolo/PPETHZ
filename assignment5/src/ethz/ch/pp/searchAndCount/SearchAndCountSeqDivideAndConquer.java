package ethz.ch.pp.searchAndCount;

import java.util.ArrayList;

import ethz.ch.pp.util.Workload;

public class SearchAndCountSeqDivideAndConquer {
	
	
	/*
	 * 	Divide and Conquer: 
	 * 		if cannot divide: 
	 * 			return unitary solution (stop recursion) 
	 * 		divide problem into two 
	 * 		solve first (recursively) 
	 * 		solve second (recursively)
	 * 		return combine solutions
	*/
	
	public static int countNoAppearances(int[] input, Workload.Type wt) {
		ArrayList<Integer> list = new ArrayList<Integer>();

		//Filter out LIGHT from HEAVY (count only heavy)
		for (int i = 0; i < input.length; i++) {
			if (Workload.doWork(input[i], wt))
				list.add(input[i]);
		}
		return count_rec(list, 0, list.size() - 1);
	}
	

	//Don't count content!! just add one if not heavy:
	public static int count_rec(ArrayList<Integer> list, int lo, int hi) {
		int mid = (lo + hi) / 2;

		if (lo == hi) {
			return 1;
		}
		
		int sum_left = count_rec(list, lo, mid);
		int sum_right = count_rec(list, mid+1, hi);
		return sum_left + sum_right;
	}
}