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

        CSV = new CsvManager("result"+EXEC_ID);
    }

    private void debug(Algorithm ga,int gn, int gl) throws Exception {
        System.out.println("Solution:");
        System.out.print("Allele: ");
        for (int i = 0; i < gn * gl; i++)
        {
            System.out.print((ga.get_solution()).get_allele(i));
        }
        System.out.println();
        System.out.println("Final fitness: " + (ga.get_solution()).get_fitness());
    }

    public void run() throws Exception {

        PROBLEM = new ProblemSubsetSum();

        PROBLEM.set_geneN(GN);
        PROBLEM.set_geneL(GL);
        PROBLEM.set_target_fitness(TF);

        GA = new Algorithm(PROBLEM, POP_SIZE, GN, GL, PC, PM);

        // Control steps
        List<String[]> allStepLog = new ArrayList<String[]>();
        String[] header = new String[] { "step", "bestf" };
        allStepLog.add(header);

        // Init search
        for (int step = 0; step < MAX_ISTEPS; step++) {
            GA.go_one_step();

            System.out.println("Step: " + step + " | Best fintess: " + GA.get_bestf());

            // Parse to string
            String stepStr = Integer.toString(step);
            String bestFitness = Double.toString(GA.get_bestf());
            String[] stepLog = new String[]{stepStr,bestFitness};
            allStepLog.add(stepLog);

            if ((PROBLEM.tf_known()) && (GA.get_solution()).get_fitness() >= PROBLEM.get_target_fitness()) {
                System.out.print("Solution Found! After ");
                System.out.print(PROBLEM.get_fitness_counter());
                System.out.println(" evaluations");
                break;
            }
        }

        // Print the solution
        debug(GA,GN,GL);

        // Save CSV execution result
        CSV.writeCSV(allStepLog);
    }
}
