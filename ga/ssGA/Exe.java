///////////////////////////////////////////////////////////////////////////////
///            Steady State Genetic Algorithm v1.0                          ///
///                by Enrique Alba, July 2000                               ///
///                                                                         ///
///   Executable: set parameters, problem, and execution details here       ///
///////////////////////////////////////////////////////////////////////////////

package ga.ssGA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Exe {  
  // Generate a set of natural numbers between 1 and size "s" (inclusive) that are not repeated.
  public static int[] generate_problem_set(int size, int bound) {
    List<Integer> result = new java.util.ArrayList<Integer>();
    Random r = new Random();
    for (int i = 0; i < size; i++) {
      int n = (int) (r.nextInt(bound) + 1);
      while (result.contains(n)) {
        n = (int) (r.nextInt(bound) + 1);
      }
      result.add(n);
    }
    int[] prob_set = result.stream().mapToInt(i -> i).toArray();

    return prob_set;
  }

  // TODO: get rando solution number - C

  public static void main(String args[]) throws Exception {
    // PARAMETERS SUBSETSUM
    int gn = 100; // Gene number || 128 or 30
    int gl = 1; // Gene length
    double pc = 0.8; // Crossover probability
    double pm = 0.2; // Crossover probability
    double tf = (double) 300500; // Target fitness beign sought
    long max_steps = 50000;
    int EXECUTIONS = 30;

    // int[] problem_set={2902, 5235, 357, 6058, 4846, 8280, 1295, 181, 3264,
    //         7285, 8806, 2344, 9203, 6806, 1511, 2172, 843, 4697,
    //         3348, 1866, 5800, 4094, 2751, 64, 7181, 9167, 5579,
    //         9461, 3393, 4602, 1796, 8174, 1691, 8854, 5902, 4864,
    //         5488, 1129, 1111, 7597, 5406, 2134, 7280, 6465, 4084,
    //         8564, 2593, 9954, 4731, 1347, 8984, 5057, 3429, 7635,
    //         1323, 1146, 5192, 6547, 343, 7584, 3765, 8660, 9318,
    //         5098, 5185, 9253, 4495, 892, 5080, 5297, 9275, 7515,
    //         9729, 6200, 2138, 5480, 860, 8295, 8327, 9629, 4212,
    //         3087, 5276, 9250, 1835, 9241, 1790, 1947, 8146, 8328,
    //         973, 1255, 9733, 4314, 6912, 8007, 8911, 6802, 5102,
    //         5451, 1026, 8029, 6628, 8121, 5509, 3603, 6094, 4447,
    //         683, 6996, 3304, 3130, 2314, 7788, 8689, 3253, 5920,
    //         3660, 2489, 8153, 2822, 6132, 7684, 3032, 9949, 59,
    //         6669, 6334};
    int[] problem_set = generate_problem_set(gn, (int) (gn * 1.5));

    System.out.println("Problem set: ");
    for (int i = 0; i < problem_set.length; i++) {
      System.out.print(problem_set[i] + " ");
    }
    
    // Log params
    Execution exec;
    CsvManager csv = new CsvManager("result","","case1"); // initialite csv manager
    List<String[]> allExecLogs = new ArrayList<String[]>(); // Control executions

    // Add header to control list
    String[] header = new String[] {"exec","bestf","worstf","avgf","endstep","duration"}; // declare header
    allExecLogs.add(header); // add headers to list
    
    // Run executions
    for (int exec_id = 1; exec_id <= EXECUTIONS; exec_id++) {
      String[] execLog = new String[]{}; // Sub list

      exec = new Execution(exec_id,gn,gl,pc,pm,tf,max_steps, problem_set); // Create execution
      execLog = exec.run(); // Run execution

      allExecLogs.add(execLog); // Apend to log list
    }
    // Write to file
    csv.overwriteData(allExecLogs);
  } // end main
}
// END OF CLASS: Exe
