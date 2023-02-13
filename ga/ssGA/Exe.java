///////////////////////////////////////////////////////////////////////////////
///            Steady State Genetic Algorithm v1.0                          ///
///                by Enrique Alba, July 2000                               ///
///                                                                         ///
///   Executable: set parameters, problem, and execution details here       ///
///////////////////////////////////////////////////////////////////////////////

package ga.ssGA;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Exe {  
  // Generate a set of natural numbers between 1 and size "s" (inclusive) that are not repeated.
  private static int[] generate_problem_set(int size, int bound) {
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

  // Get random solution number - C
  private static double get_C(int[] problem_set) {
    double C = 0;
    for (int i = 0; i < problem_set.length; i++) {
      int binary = (int) (Math.round(Math.random()));
      C += problem_set[i] * binary;
    }
    return C;
  }

  // Write problem_set to file
  private static void write_problem_set (int[] problem_set, String folder_path, String file_name) throws IOException {
    // Convert problem_set to String[]
    String[] problem_set_str = new String[problem_set.length];
    for (int i = 0; i < problem_set.length; i++) {
      problem_set_str[i] = Integer.toString(problem_set[i]);
    }

    List<String[]> problem_set_list = new ArrayList<String[]>();
    problem_set_list.add(problem_set_str);

    // Write problem_set to file
    CsvManager manager = new CsvManager();
    manager.overwriteData(problem_set_list, folder_path, file_name);
  }

  // Read problem_set from file
  private static int[] read_problem_set (String folder_path, String file_name) throws Exception {
    int[] problem_set = null;
    List<String[]> problem_set_list;

    // Write problem_set to file
    CsvManager manager = new CsvManager();
    problem_set_list = manager.readAllDataAtOnce(folder_path,file_name);

    // Convert problem_set_list (List<String[]>) to int[]
    String[] problem_set_str = problem_set_list.get(0);
    problem_set = new int[problem_set_str.length];

    for (int i = 0; i < problem_set_str.length; i++) {
      problem_set[i] = Integer.parseInt(problem_set_str[i]);
    }

    return problem_set;
  }

  // Read problem_set from file
  private static double read_C (String folder_path, String file_name) throws Exception {
    // Write problem_set to file
    List<String[]> c_list;
    CsvManager manager = new CsvManager();
    c_list = manager.readAllDataAtOnce(folder_path,file_name);

    // Convert problem_set_list (List<String[]>) to int[]
    String[] c_str = c_list.get(0);
    double C = Double.parseDouble(c_str[0]);

    return C;
  }
  
  // Write problem_C to file
  private static void write_problem_C (double tarjet, String folder_path, String file_name) throws IOException {
    // Convert tarjet to String[]
    String[] tarjet_str = new String[1];
    tarjet_str[0] = Double.toString(tarjet);
    
    List<String[]> tarjet_list = new ArrayList<String[]>();
    tarjet_list.add(tarjet_str);
    
    // Write problem_C to file
    CsvManager manager = new CsvManager();
    manager.overwriteData(tarjet_list, folder_path, file_name);
  }

  public static void main(String args[]) throws Exception {
    // PARAMETERS SUBSETSUM
    int gn = 10000; // Gene number (problem set size)
    int gl = 1; // Gene length
    double pc = 0.55; // Crossover probability
    double pm = 0.50; // Mutation probability
    double tf = (double) 0; // Target fitness beign sought
    long max_steps = 50000; // Max steps to iterate execution
    int EXECUTIONS = 30; // N Executions to run
    
    // Generate problem set
    int[] problem_set;
    String folder_path_set = "problems/z"+gn+"/";
    String file_name_set = "problemset";
    // problem_set = generate_problem_set(gn, (int) (gn * 1.5));
    // write_problem_set(problem_set,folder_path_set, file_name_set);
    problem_set = read_problem_set(folder_path_set, file_name_set);

    // Get & Set - C
    String folder_path_C = "problems/z"+gn+"/";
    String file_name_C = "tarjet";
    // double C = get_C(problem_set);
    // write_problem_C(C,folder_path_C, file_name_C);
    double C = read_C(folder_path_C, file_name_C);
    tf = C; // Set tarjet fitness

    // Declare execution manager
    Execution exec;

    // Log params
    CsvManager csvResults = new CsvManager(); // initialite csv manager
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
    csvResults.overwriteData(allExecLogs, "case1/z"+gn+"/mediumprob/","result");
  } // end main
}
// END OF CLASS: Exe
