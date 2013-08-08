package org.kevoree.genetic.framework;

import org.kevoree.modeling.api.KMFContainer;
import org.kevoree.modeling.genetic.api.FitnessFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 15/03/13
 * Time: 16:20
 */
public class CompositeFitnessFunction implements FitnessFunction {

    private List<FitnessFunction> fitnesses = new ArrayList<FitnessFunction>();

    public CompositeFitnessFunction addFitness(FitnessFunction fitness) {
        fitnesses.add(fitness);
        return this;
    }

    @Override
    public double evaluate(KMFContainer model) {
        double value = 0d;
        for (FitnessFunction fit : fitnesses) {
            value = value + fit.evaluate(model);
        }
        return value / fitnesses.size();
    }

    public List<FitnessFunction> getFitnesses(){
        return fitnesses;
    }

}
