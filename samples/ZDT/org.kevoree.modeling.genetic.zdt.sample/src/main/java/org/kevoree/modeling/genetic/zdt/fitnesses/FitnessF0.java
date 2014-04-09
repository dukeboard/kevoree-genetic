package org.kevoree.modeling.genetic.zdt.fitnesses;

import org.kevoree.modeling.genetic.zdt.ZDT;
import org.kevoree.modeling.optimization.api.GenerationContext;
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction;
import org.zdt.Dlist;

/**
 * Created by assaad.moawad on 4/9/2014.
 */
public class FitnessF0 extends FitnessF {
   @Override
    public double evaluate(Dlist model, GenerationContext<Dlist> context) {
        return ZDT.calculate(model,zdt,0);
    }

    @Override
    public double max() {
        return 5.0;
    }

    @Override
    public double min() {
        return 0;
    }
}
