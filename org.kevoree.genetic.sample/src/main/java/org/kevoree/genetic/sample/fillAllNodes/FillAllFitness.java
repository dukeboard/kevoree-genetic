package org.kevoree.genetic.sample.fillAllNodes;

import org.kevoree.ContainerRoot;
import org.kevoree.genetic.framework.KevoreeFitnessFunction;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 16:43
 */
public class FillAllFitness implements KevoreeFitnessFunction {
    @Override
    public double evaluate(ContainerRoot model) {
        return model.selectByQuery("nodes[{components.size > 0 }]").size();
    }

    @Override
    public String getName() {
        return "FillAllFitness";
    }
}
