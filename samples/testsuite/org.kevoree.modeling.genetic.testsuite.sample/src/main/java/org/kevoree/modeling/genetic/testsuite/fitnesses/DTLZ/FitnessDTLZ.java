package org.kevoree.modeling.genetic.testsuite.fitnesses.DTLZ;

import org.kevoree.modeling.optimization.api.GenerationContext;
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.RealVariable;
import org.moeaframework.problem.DTLZ.*;
import org.testsuite.Dlist;

/**
 * Created by assaad.moawad on 4/9/2014.
 */
public class FitnessDTLZ extends FitnessFunction<Dlist> {
    public static int dtlz=1; //SAME ZDT NUM SHOULD BE USED IN BOTH FITNESS FCT zdt can be 1,2,3,4,6 - 5 not implemented
    public static int numOfObjectif=3;
    private int fitnessNumber;

    public FitnessDTLZ(int fitnessNumber){
        this.fitnessNumber= fitnessNumber;
    }

    @Override
    public double evaluate(Dlist model, GenerationContext<Dlist> context) {

        int var = model.getValues().size();
        Solution s = new Solution(var,numOfObjectif);

        for (int i=0; i<var; i++){
            s.setVariable(i,new RealVariable(model.getValues().get(i).getDoubleValue(),0.0,1));
        }
        switch (dtlz){
            case 1:
            {
                DTLZ1 dtlz1 = new DTLZ1(var,numOfObjectif);
                dtlz1.evaluate(s);
                return s.getObjective(fitnessNumber);
            }
            case 2:
            {
                DTLZ2 dtlz2 = new DTLZ2(var,numOfObjectif);
                dtlz2.evaluate(s);
                return s.getObjective(fitnessNumber);
            }
            case 3:
            {
                DTLZ3 dtlz3 = new DTLZ3(var,numOfObjectif);
                dtlz3.evaluate(s);
                return s.getObjective(fitnessNumber);

            }
            case 4:
            {
                DTLZ4 dtlz4 = new DTLZ4(var,numOfObjectif);
                dtlz4.evaluate(s);
                return s.getObjective(fitnessNumber);
            }
            case 7:
            {
                DTLZ7 dtlz7 = new DTLZ7(var,numOfObjectif);
                dtlz7.evaluate(s);
                return s.getObjective(fitnessNumber);
            }


        }
        return -1;

    }

    @Override
    public double max() {
        return 10.0;
    }

    @Override
    public double min() {
        return 0;
    }
}
