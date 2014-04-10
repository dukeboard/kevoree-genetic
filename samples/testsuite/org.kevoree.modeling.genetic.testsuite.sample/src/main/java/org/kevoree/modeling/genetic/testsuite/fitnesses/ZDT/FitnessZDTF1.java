package org.kevoree.modeling.genetic.testsuite.fitnesses.ZDT;

import org.kevoree.modeling.genetic.testsuite.fitnesses.ZDT.FitnessZDT;
import org.kevoree.modeling.optimization.api.GenerationContext;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.RealVariable;
import org.moeaframework.problem.ZDT.*;
import org.testsuite.Dlist;

/**
 * Created by assaad.moawad on 4/9/2014.
 */
public class FitnessZDTF1 extends FitnessZDT {

    @Override
    public double evaluate(Dlist model, GenerationContext<Dlist> context) {


        int fctnum=1;

        int var = model.getValues().size();
        Solution s = new Solution(var,2);

        for (int i=0; i<var; i++){
            s.setVariable(i,new RealVariable(model.getValues().get(i).getDoubleValue(),0.0,1));
        }
        switch (zdt){
            case 1:
            {
                ZDT1 zdtfct = new ZDT1(var);
                zdtfct.evaluate(s);
                return s.getObjective(fctnum);
            }
            case 2:
            {
                ZDT2 zdtfct = new ZDT2(var);
                zdtfct.evaluate(s);
                return s.getObjective(fctnum);
            }
            case 3:
            {
                ZDT3 zdtfct = new ZDT3(var);
                zdtfct.evaluate(s);
                return s.getObjective(fctnum);

            }
            case 4:
            {
                ZDT4 zdtfct = new ZDT4(var);
                zdtfct.evaluate(s);
                return s.getObjective(fctnum);
            }
            case 5:
            {
                ZDT5 zdtfct = new ZDT5(var);
                zdtfct.evaluate(s);
                return s.getObjective(fctnum);

            }
            case 6:
            {
                ZDT6 zdtfct = new ZDT6(var);
                zdtfct.evaluate(s);
                return s.getObjective(fctnum);
            }


        }
        return -1;

    }

    @Override
    public double max() {
        return 5.0;
    }

    @Override
    public double min() {
        return 0;
    }
}
