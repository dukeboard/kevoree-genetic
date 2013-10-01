package org.kevoree.modeling.genetic.democloud.fitnesses;

import org.cloud.*;
import org.kevoree.modeling.api.trace.TraceSequence;
import org.kevoree.modeling.optimization.api.FitnessFunction;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/08/13
 * Time: 16:14
 */
public class CloudLatencyFitness implements FitnessFunction<Cloud> {

    //private double maxNode = 10;

    @Override
    public boolean originAware() {
        return false;
    }

    @Override
    public double evaluate(Cloud model, Cloud origin, TraceSequence traceSequence) {

        double latency = 0;


        for(VirtualNode node : model.getNodes())

        {

            for(Software soft : node.getSoftwares())

            {
                latency=latency + soft.getLatency();
                System.out.println(latency);


            }
        }


        return (latency / model.getNodes().size()) * 100;
    }

}
