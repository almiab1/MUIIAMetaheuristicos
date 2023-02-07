package ga.ssGA;

import java.util.ArrayList;
import java.util.List;

public class Execution {
    // PARAMETERS Execution
    int GN ;
    int GL ;
    int POP_SIZE;
    double PC ;
    double PM;
    double TF;
    long MAX_ISTEPS;
    Problem PROBLEM; // The problem being solved
    Algorithm GA; // The ssGA being used
    int EXEC_ID;
    CsvManager CSV;

    public Execution(int execId,int gn,int gl ,double pc,double tf,long max_steps) {
        // PARAMETERS Execution
        GN = gn; // Gene number || 128 or 30
        GL = gl; // Gene length
        POP_SIZE = gn; // Population size
        PC = pc; // Crossover probability
        PM = 1 / (double) ((double) gn * (double) gl); // Mutation probability
        TF = tf; // Target fitness beign sought
        MAX_ISTEPS = max_steps;
        EXEC_ID = execId;
        CSV = new CsvManager("result"+execId,"executions/");
    }

    private void debug(Algorithm ga,int gn, int gl) throws Exception {
        System.out.print("Solution Step " + EXEC_ID + " ");
        // System.out.print("Allele: ");
        // for (int i = 0; i < gn * gl; i++)
        // {
        //     System.out.print((ga.get_solution()).get_allele(i));
        // }
        // System.out.println();
        System.out.print("Final fitness: " + (ga.get_solution()).get_fitness());
        System.out.println(" After " + PROBLEM.get_fitness_counter() + " evaluation");
    }

    public String[] run() throws Exception {
        // Init run time
        long startTime = System.currentTimeMillis();

        // Create the problem
        PROBLEM = new ProblemSubsetSum();
        // Set parameters
        PROBLEM.set_geneN(GN);
        PROBLEM.set_geneL(GL);
        PROBLEM.set_target_fitness(TF);
        // Create GA
        GA = new Algorithm(PROBLEM, POP_SIZE, GN, GL, PC, PM);

        // Control steps
        String execStr = "";
        String stepStr= "";
        String bestFitness= "";
        String worstFitness= "";
        String avgFitness= "";
        List<String[]> execStepLog = new ArrayList<String[]>();

        // Add header to control list
        String[] header = new String[] {"exec","step","bestf","worstf","avgf"}; // declare header
        execStepLog.add(header); // add headers to list
        
        // Init search
        for (int step = 0; step < MAX_ISTEPS; step++) {
            // next step
            GA.go_one_step();
            
            // Parse to string
            execStr = Integer.toString(EXEC_ID);
            stepStr = Integer.toString(step);
            bestFitness = Double.toString(GA.get_bestf());
            worstFitness = Double.toString(GA.get_worstf());
            avgFitness = Double.toString(GA.get_avgf());
            String[] stepLog = new String[]{execStr,stepStr,bestFitness,worstFitness,avgFitness};
            execStepLog.add(stepLog);
            
            if ((PROBLEM.tf_known()) && (GA.get_solution()).get_fitness() >= PROBLEM.get_target_fitness()) {
                // System.out.print("Solution Found! After ");
                // System.out.print(PROBLEM.get_fitness_counter());
                // System.out.println(" evaluations");
                break;
            }
        }
        
        // Print the solution
        debug(GA,GN,GL);
        
        // Save to csv
        CSV.overwriteData(execStepLog);
        
        // Calcule execution time in mili seconds
        long endTime = System.currentTimeMillis();
        String durationStr = Long.toString(endTime - startTime);
        String stepsStr = Long.toString(PROBLEM.get_fitness_counter());
        
        // return run outputs
        String[] execLog = new String[]{execStr,bestFitness,worstFitness,avgFitness,stepsStr,durationStr};
        return execLog;
    } // end run
} // END OF CLASS: Execution
