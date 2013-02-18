package org.kevoree.genetic.framework.internal;

import org.kevoree.ContainerRoot;
import org.kevoree.genetic.framework.KevoreeCrossOverOperator;
import org.kevoree.genetic.framework.KevoreeMutationOperator;
import org.kevoree.genetic.framework.KevoreeOperator;
import org.moeaframework.core.Solution;
import org.moeaframework.core.Variation;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 15:35
 */
public class KevoreeVariationAdaptor implements Variation {

    private KevoreeMutationOperator mutator = null;
    private KevoreeCrossOverOperator cross = null;
    private int arity = 0;

    public KevoreeVariationAdaptor(KevoreeOperator _operator) {
        if (_operator instanceof KevoreeMutationOperator) {
            mutator = (KevoreeMutationOperator) _operator;
            arity = 1;
        }
        if (_operator instanceof KevoreeCrossOverOperator) {
            cross = (KevoreeCrossOverOperator) _operator;
            arity = 2;
        }
    }

    @Override
    public int getArity() {
        return arity;
    }

    @Override
    public Solution[] evolve(Solution[] parents) {
        if (mutator != null) {

            KevoreeVariable variable = (KevoreeVariable) parents[0].getVariable(0);
            ContainerRoot m = variable.getModel();
            ContainerRoot afterMutation = mutator.mutate(m);
            Solution s = new Solution(parents[0].getNumberOfVariables(), parents[0].getNumberOfObjectives());
            s.setVariable(0, new KevoreeVariable(afterMutation));
            Solution[] result = new Solution[1];
            result[0]=s;
            return result;
        }
        if (cross != null) {
            KevoreeVariable variable = (KevoreeVariable) parents[0].getVariable(0);
            ContainerRoot m = variable.getModel();
            KevoreeVariable variable2 = (KevoreeVariable) parents[1].getVariable(0);
            ContainerRoot m2 = variable2.getModel();
            ContainerRoot afterMutation = cross.cross(m, m2);
            Solution s = new Solution(parents[0].getNumberOfVariables(), parents[0].getNumberOfObjectives());
            s.setVariable(0, new KevoreeVariable(afterMutation));
            return Arrays.asList(s).toArray(parents);
        }
        return null;
    }
}
