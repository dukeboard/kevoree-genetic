package org.kevoree.modeling.genetic.democloud.fitnesses;

import org.cloud.Amazon;
import org.cloud.Rackspace;
import org.cloud.Cloud;
import org.cloud.VirtualNode;
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
public class CloudCostFitness implements FitnessFunction<Cloud> {



    @Override
    public double evaluate(Cloud model, GenerationContext<Cloud> cloudGenerationContext) {

        double pres = 0;



        for(VirtualNode node : model.getNodes())
        {

            if ((node instanceof  Amazon) || (node instanceof  Rackspace)  )

            {

                //System.out.println(node.getId());
                pres=pres + node.getPricePerHour();

            }

        }

        return ((pres / (model.getNodes().size()*10)));
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
