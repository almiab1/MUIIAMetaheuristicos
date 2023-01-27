///////////////////////////////////////////////////////////////////////////////
///            Steady State Genetic Algorithm v1.0                          ///
///                by Enrique Alba, July 2000                               ///
///                                                                         ///
///   Executable: set parameters, problem, and execution details here       ///
///////////////////////////////////////////////////////////////////////////////

package ga.ssGA;

import java.util.ArrayList;
import java.util.List;

public class Exe {
  public static void main(String args[]) throws Exception {
    // PARAMETERS SUBSETSUM
    int gn = 128; // Gene number || 128 or 30
    int gl = 1; // Gene length
    int popsize = gn; // Population size
    double pc = 0.8; // Crossover probability
    double pm = 1 / (double) ((double) gn * (double) gl); // Mutation probability
    double tf = (double) 300500; // Target fitness beign sought
    long MAX_ISTEPS = 500000;

    /*
     * // PARAMETERS ONEMAX
     * int gn = 512; // Gene number
     * int gl = 1; // Gene length
     * int popsize = 512; // Population size
     * double pc = 0.8; // Crossover probability
     * double pm = 1.0/(double)((double)gn*(double)gl); // Mutation probability
     * double tf = (double)gn*gl ; // Target fitness being sought
     * long MAX_ISTEPS = 50000;
     */

    Problem problem; // The problem being solved

    problem = new ProblemSubsetSum();
    // problem = new ProblemPPeaks();
    // problem = new ProblemOneMax();

    problem.set_geneN(gn);
    problem.set_geneL(gl);
    problem.set_target_fitness(tf);

    Algorithm ga; // The ssGA being used
    ga = new Algorithm(problem, popsize, gn, gl, pc, pm);

    // Control steps
    List<String[]> allStepLog = new ArrayList<String[]>();
    String[] header = new String[] { "step", "bestf" };
    allStepLog.add(header);


    for (int step = 0; step < MAX_ISTEPS; step++) {
      ga.go_one_step();

      System.out.println("Step: " + step + " | Best fintess: " + ga.get_bestf());

      // Parse to string
      String stepStr = Integer.toString(step);
      String bestFitness = Double.toString(ga.get_bestf());
      String[] stepLog = new String[]{stepStr,bestFitness};
      allStepLog.add(stepLog);

      if ((problem.tf_known()) && (ga.get_solution()).get_fitness() >= problem.get_target_fitness()) {
        System.out.print("Solution Found! After ");
        System.out.print(problem.get_fitness_counter());
        System.out.println(" evaluations");
        break;
      }
    }

    // Print the solution
    System.out.println("Solution:");
    System.out.print("Allele: ");
    for (int i = 0; i < gn * gl; i++)
    {
      System.out.print((ga.get_solution()).get_allele(i));
    }
    System.out.println();
    System.out.println("Final fitness: " + (ga.get_solution()).get_fitness());

    // TODO: Save results on csv

    CsvManager csv = new CsvManager();
    csv.writeCSV(allStepLog);
    
    // TODO: Generate plots each step & general pop
    // TODO: sys to execute N experiments
  }
  
}
// END OF CLASS: Exe
