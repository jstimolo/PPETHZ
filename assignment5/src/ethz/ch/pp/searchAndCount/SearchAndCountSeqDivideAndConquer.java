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
		return count_rec(input, 0, input.length - 1,wt);
	}
	

	//Don't count content!! just add one if not heavy:
	public static int count_rec(int[] list, int lo, int hi, Workload.Type wt) {
		int mid = (lo + hi) / 2;
		boolean heavy = (Workload.doWork(list[lo], wt));

		
		if (lo == hi) {
			//System.out.println(list[lo]+" "+heavy);
			if(!heavy) {
				return 0;
			} else {
				return 1;
			}
		} 
			
		int sum_left = count_rec(list, lo, mid, wt);
		int sum_right = count_rec(list, mid+1, hi, wt);
		return sum_left + sum_right;
	}
}