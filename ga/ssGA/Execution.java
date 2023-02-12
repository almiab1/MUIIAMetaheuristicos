package ga.ssGA;

import java.util.ArrayList;
import java.util.List;

public class Execution {
    // PARAMETERS Execution
    private int GN, GL;         // Gene number in one string and length
    private int POP_SIZE;       // The number of individuals
    private double PC, PM;      // Probability of applying crossover and mutation
    private double TF;          // Tarjet fitness
    private long MAX_STEPS;     // Maximun steps
    private Problem PROBLEM;    // The problem being solved
    private Algorithm GA;       // The ssGA being used
    private int EXEC_ID;        // Execution id
    private CsvManager CSV;     // Log manager
    private int[] PROBLEM_SET;

    public Execution(int execId,int gn,int gl ,double pc, double pm,double tf,long max_steps, int[] problem_set) {
        GN = gn;
        GL = gl;
        POP_SIZE = gn;
        PC = pc;
        // PM = 1 / (double) ((double) gn * (double) gl);
        PM = pm;
        TF = tf;
        MAX_STEPS = max_steps;
        EXEC_ID = execId;
        PROBLEM_SET = problem_set;
        CSV = new CsvManager("result"+execId,"executions/","case1");
    }

    private void debug(Algorithm ga,int gn, int gl) throws Exception {
        System.out.print("Solution Exec " + EXEC_ID + " ");
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
        // Get initial time in miliseconds
        long startTime = System.currentTimeMillis();
        
        // Init problem subset sum
        PROBLEM = new ProblemSubsetSum(); // Create the problem
        PROBLEM.set_geneN(GN); // Set gene number to problem
        PROBLEM.set_geneL(GL); // Set gene length to problem
        PROBLEM.set_target_fitness(TF); // Set tarjet fitness to problem
        PROBLEM.set_problem_set(PROBLEM_SET); // Set problem set of numbers to problem

        GA = new Algorithm(PROBLEM, POP_SIZE, GN, GL, PC, PM); // Create GA

        // Declare log stats
        List<String[]> execStepLog = new ArrayList<String[]>(); // create execution log list of step log arrays
        String execStr = Integer.toString(EXEC_ID); // execution id
        String stepStr= ""; // step ref
        String bestFitness= ""; // best fitness string
        String worstFitness= ""; // worst fitness string
        String avgFitness= ""; // averge fitness string

        // Add header to control list
        String[] header = new String[] {"exec","step","bestf","worstf","avgf"}; // declare header
        execStepLog.add(header); // add headers to list
        
        // Init search
        for (int step = 0; step < MAX_STEPS; step++) {
            // next step
            GA.go_one_step();

            stepStr = Integer.toString(step); // asing step ref
            bestFitness = Double.toString(GA.get_bestf()); // asing best fitness of step
            worstFitness = Double.toString(GA.get_worstf()); // asing worst fitness of step
            avgFitness = Double.toString(GA.get_avgf()); // asing average fitness of step
            String[] stepLog = new String[]{execStr,stepStr,bestFitness,worstFitness,avgFitness}; // step log array
            execStepLog.add(stepLog); // add step log array to execution log list
            
            // End the iteration if the solution is reached
            if ((PROBLEM.tf_known()) && (GA.get_solution()).get_fitness() >= PROBLEM.get_target_fitness()) {
                // System.out.print("Solution Found! After " + PROBLEM.get_fitness_counter() + " evaluations");
                break;
            }
        }
        
        // Print the solution
        debug(GA,GN,GL);
        
        // Save log to this execution log file (./src/executions/result<execid>.csv)
        CSV.overwriteData(execStepLog);
        
        // Get execution time
        long endTime = System.currentTimeMillis(); // get end time in miliseconds
        String durationStr = Long.toString(endTime - startTime); // calculate execution time
        String stepsStr = Long.toString(PROBLEM.get_fitness_counter()); // parse execution time to string
        
        // return run outputs to executions manager
        String[] execLog = new String[]{execStr,bestFitness,worstFitness,avgFitness,stepsStr,durationStr};
        return execLog;
    } // end run
} // END OF CLASS: Execution
