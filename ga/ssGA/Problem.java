///////////////////////////////////////////////////////////////////////////////
///                 Steady State Genetic Algorithm v1.0                     ///
///                      by Enrique Alba, July 2000                         ///
///                                                                         ///
///         Problem Function AND Representation (GL, GN, Ranges)            ///
///////////////////////////////////////////////////////////////////////////////

package ga.ssGA;

import java.util.Random;


public abstract class Problem                    // Maximization task
{
    protected int GL=1;               	// Gene lenth in binary
    protected int GN=1;               	// Gene number in one string
    protected int CL=GN*GL;           	// Chromosome length
    protected long fitness_counter;   	// Number of evaluations
    protected double target_fitness;  	// Target fitness value -MAXIMUM-
    protected boolean tf_known;       	// Is the taret fitness known????
    protected static Random r = new Random();	// Random uniform variable

    // UPDATE GENERATE RANDOM SET
    protected int[] PROBLEM_SET;			// Population
    
    public Problem() {
        CL              = GN*GL; // Chromosome length
        fitness_counter = 0; // Number of evaluations
        tf_known        = false; // Is the taret fitness known????
        target_fitness  = -999999.9; // Target fitness value -MAXIMUM-
    }
    
    public int     get_geneL()           { return GL; }
    public int     get_geneN()           { return GN; }
    public void    set_geneL(int gl)     { GL = gl; CL=GN*GL; }
    public void    set_geneN(int gn)     { GN = gn; CL=GN*GL; }
    public long    get_fitness_counter() { return fitness_counter; }
    public double  get_target_fitness()  { return target_fitness;  }
    public boolean tf_known()            { return tf_known;        }
    
    // UPDATE GENERATE RANDOM SET
    public int[]  get_problem_set()  { return PROBLEM_SET;  }
    public void    set_problem_set(int[] set)     { PROBLEM_SET = set; }

    public void    set_target_fitness(double tf) {
        target_fitness = tf;
        tf_known       = true;
    }
    
    
    public double evaluateStep(Individual Indiv) {
            fitness_counter++ ;
            return Evaluate(Indiv) ;
    } 
    
    public abstract double Evaluate(Individual Indiv);
}
// END OF CLASS: Problem
