package org.kevoree.modeling.genetic.democloud.fitnesses;

import org.cloud.Cloud;
import org.cloud.VirtualNode;
import org.kevoree.modeling.api.trace.TraceSequence;
import org.kevoree.modeling.optimization.api.FitnessFunction;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/08/13
 * Time: 16:14
 */
public class CloudConsumptionFitness implements FitnessFunction<Cloud> {

    private double maxNode = 10;

    @Override
    public boolean originAware() {
        return false;
    }

    @Override
    public double evaluate(Cloud model, Cloud origin, TraceSequence traceSequence) {

        double pres = 0;


        for (int i=0 ;i<model.getNodes().size();i++)
        {
        VirtualNode ec2node = model.findNodesByID("EC2_"+i);
        pres =  pres + ec2node.getPricePerHour();
        System.out.println(pres);

        VirtualNode rackspace = model.findNodesByID("Rack_"+i);
        pres   =  pres + rackspace.getPricePerHour();
        }


        return (pres / maxNode) * 100;
    }

}
