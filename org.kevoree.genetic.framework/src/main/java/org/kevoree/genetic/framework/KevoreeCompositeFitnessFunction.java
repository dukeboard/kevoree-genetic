package org.kevoree.genetic.framework;

import org.kevoree.ContainerRoot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 15/03/13
 * Time: 16:20
 */
public class KevoreeCompositeFitnessFunction implements KevoreeFitnessFunction {

    private List<KevoreeFitnessFunction> fitnesses = new ArrayList<KevoreeFitnessFunction>();

    public KevoreeCompositeFitnessFunction addFitness(KevoreeFitnessFunction fitness) {
        fitnesses.add(fitness);
        return this;
    }

    @Override
    public double evaluate(ContainerRoot model) {
        double value = 0d;
        for (KevoreeFitnessFunction fit : fitnesses) {
            value = value + fit.evaluate(model);
        }
        return value / fitnesses.size();
    }

    @Override
    public String getName() {
        StringBuilder name = new StringBuilder();
        for (KevoreeFitnessFunction fit : fitnesses) {
            name.append("/" + fit.getName());
        }
        return name.toString();
    }
}
