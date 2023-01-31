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
    double pc = 0.8; // Crossover probability
    double tf = (double) 300500; // Target fitness beign sought
    long MAX_STEPS = 10000;
    int EXECUTIONS = 30;
    Execution exec;
    CsvManager csv = new CsvManager("result");

    // Control executions
    List<String[]> all_step_logs = new ArrayList<String[]>();
    String[] header = new String[] {"exec","step", "bestf" };
    all_step_logs.add(header); // add headers to list
    
    // Run executions
    for (int exec_id = 1; exec_id <= EXECUTIONS; exec_id++) {
      // Sub list
      List<String[]> exec_log = new ArrayList<String[]>();
      // Create execution
      exec = new Execution(exec_id,gn,gl,pc,tf,MAX_STEPS);
      // Run execution
      exec_log = exec.run();
      // Apend to log list
      all_step_logs.addAll(exec_log);
    }
    // Write to file
    csv.overwriteData(all_step_logs);
  } // end main
}
// END OF CLASS: Exe
