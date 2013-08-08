package org.kevoree.genetic.framework.internal.variation;

import org.kevoree.modeling.genetic.api.CrossoverOperator;
import org.moeaframework.core.Solution;
import org.moeaframework.core.Variation;

/**
 * Created by duke on 07/08/13.
 */
public class CrossoverVariationAdaptor implements Variation {

    private CrossoverOperator cross = null;

    public CrossoverVariationAdaptor(CrossoverOperator _cross) {
        this.cross = _cross;
    }

    @Override
    public int getArity() {
        return 2;
    }

    @Override
    public Solution[] evolve(Solution[] parents) {
        return new Solution[0];
    }
}
