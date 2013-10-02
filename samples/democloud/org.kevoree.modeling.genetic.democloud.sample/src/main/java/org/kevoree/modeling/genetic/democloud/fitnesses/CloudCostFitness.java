package org.kevoree.modeling.genetic.democloud.fitnesses;

import org.cloud.Amazon;
import org.cloud.Rackspace;
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
public class CloudCostFitness implements FitnessFunction<Cloud> {

    @Override
    public boolean originAware() {
        return false;
    }

    @Override
    public double evaluate(Cloud model, Cloud origin, TraceSequence traceSequence) {

        double pres = 0;

        for(VirtualNode node : model.getNodes())

        {

            if ((node instanceof  Amazon) || (node instanceof  Rackspace)  )

            {

                System.out.println(node.getId());
                pres=pres + node.getPricePerHour();

            }

        }

        return (pres / model.getNodes().size()) * 100;
    }

}
