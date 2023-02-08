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
    double pm = 0.2; // Crossover probability
    double tf = (double) 300500; // Target fitness beign sought
    long MAX_STEPS = 50000;
    int EXECUTIONS = 30;
    
    
    // Log params
    Execution exec;
    CsvManager csv = new CsvManager("result",""); // initialite csv manager
    List<String[]> allExecLogs = new ArrayList<String[]>(); // Control executions

    // Add header to control list
    String[] header = new String[] {"exec","bestf","worstf","avgf","endstep","duration"}; // declare header
    allExecLogs.add(header); // add headers to list
    
    // Run executions
    for (int exec_id = 1; exec_id <= EXECUTIONS; exec_id++) {
      // Sub list
      String[] execLog = new String[]{};
      // Create execution
      exec = new Execution(exec_id,gn,gl,pc,pm,tf,MAX_STEPS);
      // Run execution
      execLog = exec.run();
      // Apend to log list
      allExecLogs.add(execLog);
    }
    // Write to file
    csv.overwriteData(allExecLogs);
  } // end main
}
// END OF CLASS: Exe
