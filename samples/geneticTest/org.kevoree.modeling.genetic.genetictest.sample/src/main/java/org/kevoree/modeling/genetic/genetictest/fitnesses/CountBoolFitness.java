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
public class CountBoolFitness extends FitnessFunction<BinaryString> {
    public static double MAX=10.0;


    @Override
    public double evaluate(BinaryString model, GenerationContext<BinaryString> cloudGenerationContext) {

        System.out.println("Inside fitness "+model.getValues().size());
        return model.getValues().size();

    }




    @Override
    public double min() {

          return 0.0;
    }
    @Override
    public double max() {

        return 20;
    }

    @Override
    public FitnessOrientation orientation(){
        return FitnessOrientation.MAXIMIZE;
    }
}
