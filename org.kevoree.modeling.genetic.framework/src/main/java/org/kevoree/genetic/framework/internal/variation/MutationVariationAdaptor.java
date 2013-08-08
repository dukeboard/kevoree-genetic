package org.kevoree.genetic.framework.internal.variation;

import org.kevoree.genetic.framework.internal.KevoreeVariable;
import org.kevoree.modeling.genetic.api.MutationOperator;
import org.moeaframework.core.Solution;
import org.moeaframework.core.Variation;

/**
 * Created by duke on 07/08/13.
 */
public class MutationVariationAdaptor implements Variation {

    private MutationOperator operator;

    public MutationVariationAdaptor(MutationOperator _operator) {
        operator = _operator;
    }

    @Override
    public int getArity() {
        return 1;
    }

    @Override
    public Solution[] evolve(Solution[] parents) {
        try {
            Solution clonedSolution = parents[0].copy();
            operator.mutate(((KevoreeVariable) clonedSolution.getVariable(0)).getModel());
            Solution[] result = new Solution[1];
            result[0] = clonedSolution;
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
