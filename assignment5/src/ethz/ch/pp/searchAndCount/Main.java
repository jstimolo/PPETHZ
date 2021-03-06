                                                                              package ethz.ch.pp.searchAndCount;

import ethz.ch.pp.util.RandomGenerator;
import ethz.ch.pp.util.Workload;

public class Main {

	public static void main(String[] args) throws InterruptedException  {
		RandomGenerator dg = new RandomGenerator();
		int[] input = dg.randomArray(1024*1024);
		
		sequential(input, Workload.Type.HEAVY);
		
		taskA(input, Workload.Type.HEAVY);
		taskB(input, Workload.Type.HEAVY);		
		taskC(input, Workload.Type.HEAVY);		
	}

	public static void sequential(int[] input, Workload.Type wt){
		long t0 = System.currentTimeMillis();
		for (int i = 0; i < 5; i++) {
			SearchAndCountSeq.countNoAppearances(input, wt);
		}
		long t1 = System.currentTimeMillis();
		System.out.println("For (inputsize=" + input.length + ",workload=" + wt + ") SearchAndCountSeq takes "
				+ ((t1 - t0)/5) + " msec");
	}
	
	//non-zero number => light workload
	//prime => heavy workload
	
	
	/*
	 * Task A: The HR manager hired you to speed-up the screening process by
	 * creating a parallel version of the program. As a first step, you need to
	 * decide how to split the problem into smaller tasks to be solved in parallel.
	 * For this purpose rewrite the sequential version using the Divide and Conquer
	 * design pattern: 
	 * 
	 * 	Divide and Conquer: 
	 * 		if cannot divide: 
	 * 			return unitary solution (stop recursion) 
	 * 		divide problem into two 
	 * 		solve first (recursively) 
	 * 		solve second (recursively)
	 * 		return combine solutions
	 * 
	 * Complete the sequential implementation using the Divide and Conquer design 
	 * pattern in the provided skeleton class SearchAndCountSeqDivideAndConquer. 
	 * Make sure that your implementation is correct.
	 */

	public static void taskA(int[] input, Workload.Type wt){
		System.out.println("=====================================");
		System.out.println("TaskA");
			
		System.out.println("Check if Div&Conq_Seq gives same result as seqSearch:");
			System.out.println("DivAndConq_Seq:  "+SearchAndCountSeqDivideAndConquer.countNoAppearances(input,wt));
			System.out.println("Seq_Search:      "+SearchAndCountSeq.countNoAppearances(input,wt));
	}
	
	
	public static void taskB(int[] input, Workload.Type wt) throws InterruptedException {
		System.out.println("=====================================");
		System.out.println("TaskB");
		
		final int CUTOFF = 1;
		int res = 0;
		
		res = SearchAndCountThreadDivideAndConquer.countNoAppearances(input,wt,1,10);
		
		System.out.println("Check if Div&Conq_Par gives same result as seqSearch:");
		System.out.println("DivAndConq_Par:  "+res);
		System.out.println("Seq_Search:      "+SearchAndCountSeq.countNoAppearances(input,wt));
	}
	
	public static void taskC(int[] input, Workload.Type wt){
		System.out.println("=====================================");
		System.out.println("TaskC");
		//TODO: implement
	}
	
	public static void taskD(int[] input, Workload.Type wt){
		System.out.println("=====================================");
		System.out.println("TaskD");
		//TODO: implement
	}

}
