package org.kevoree.modeling.genetic.tinycloud.fitnesses;

import org.cloud.Cloud;
import org.kevoree.modeling.optimization.api.GenerationContext;
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction;

/**
 * Created by duke on 08/08/13.
 */
public class CloudRedondencyFitness implements FitnessFunction<Cloud> {

    private double bestRedondency = 10;


    @Override
    public double evaluate(Cloud model, GenerationContext<Cloud> cloudGenerationContext) {


        double pres = (  (bestRedondency - model.getNodes().size()) / bestRedondency) * 100;

        System.out.println(model.getNodes().size()+"->"+pres);


        return Math.min(Math.max(0,pres),100);
    }
}
