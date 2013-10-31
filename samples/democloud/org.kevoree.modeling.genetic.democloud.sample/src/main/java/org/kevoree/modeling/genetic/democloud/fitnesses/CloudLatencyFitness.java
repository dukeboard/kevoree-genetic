package org.kevoree.modeling.genetic.democloud.fitnesses;

import org.cloud.*;
import org.kevoree.modeling.api.trace.TraceSequence;
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
public class CloudLatencyFitness implements FitnessFunction<Cloud> {

    //private double maxNode = 10;



    public double evaluate(Cloud model, GenerationContext<Cloud> cloudGenerationContext) {

        double latency = 0;


        for(VirtualNode node : model.getNodes())

        {

            for(Software soft : node.getSoftwares())

            {
                latency=latency + soft.getLatency();
                //System.out.println(latency);


            }
        }


        return (latency / (model.getNodes().size()*110)) * 100;
    }


    @Override
    public double min() {

        return 0.0;
    }
    public double max() {

        return 10.0;
    }
    public FitnessOrientation orientation() {

        return FitnessOrientation.MINIMIZE;
    }
}
