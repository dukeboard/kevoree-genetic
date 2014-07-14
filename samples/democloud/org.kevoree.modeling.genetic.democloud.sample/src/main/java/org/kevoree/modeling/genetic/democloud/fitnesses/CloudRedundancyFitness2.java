package org.kevoree.modeling.genetic.democloud.fitnesses;

import org.cloud.Cloud;
import org.cloud.VirtualNode;
import org.cloud.impl.DefaultCloudFactory;
import org.kevoree.modeling.genetic.democloud.Requirements;
import org.kevoree.modeling.optimization.api.GenerationContext;
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction;

/**
 * Created with IntelliJ IDEA.
 * User: donia.elkateb
 * Date: 10/2/13
 * Time: 9:27 AM
 */
public class CloudRedundancyFitness2 implements FitnessFunction<Cloud> {

    private DefaultCloudFactory cloudfactory = new DefaultCloudFactory();

    @Override
    public double evaluate(Cloud model, GenerationContext<Cloud> cloudGenerationContext) {
        int size = 1;
        Requirements redundancy = new Requirements();
        double count = redundancy.Redunduncy(model);
        for (VirtualNode vnode : model.getNodes()) {
            size = size + vnode.getSoftwares().size();
        }
        double redundancyeval = (count / size);
        return Math.min(redundancyeval, 1);
    }

    @Override
    public double min() {
        return 0.0;
    }

    public double max() {
        return 1.0;
    }

}
