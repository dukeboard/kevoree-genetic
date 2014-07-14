package org.kevoree.modeling.genetic.genetictest.fitnesses;

import org.genetictest.BinaryString;
import org.genetictest.MyBoolean;
import org.kevoree.modeling.optimization.api.GenerationContext;
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction;
import org.kevoree.modeling.optimization.api.fitness.FitnessOrientation;

/**
 * Created with IntelliJ IDEA.
 * User: donia.elkateb
 * Date: 10/2/13
 * Time: 9:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class MaximizeDiversityFitness implements FitnessFunction<BinaryString> {

    @Override
    public double evaluate(BinaryString model, GenerationContext<BinaryString> cloudGenerationContext) {

        double result = 0;
        boolean prev=false;
        boolean now;
        boolean firstval=false;
        boolean first=true;



        for (MyBoolean b : model.getValues()) {
            if(first)
            {
                prev=b.getValue();
                firstval=prev;
                first=false;
            }
            else
            {
                now=b.getValue();
                if(now!=prev)
                    result++;
                prev=now;
            }
        }
        if(prev!=firstval)
            result++;

        return result;

    }


}
