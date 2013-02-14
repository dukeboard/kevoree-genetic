package org.kevoree.genetic.framework.internal;

import org.moeaframework.core.FrameworkException;
import org.moeaframework.core.Solution;
import org.moeaframework.core.Variation;
import org.moeaframework.core.operator.CompoundVariation;

import java.util.Arrays;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 14/02/13
 * Time: 10:27
 */
public class RandomCompoundVariation extends CompoundVariation {

    Random rand = new Random();

    @Override
    public Solution[] evolve(Solution[] parents) {
        int indice = rand.nextInt(operators.size());
        return operators.get(indice).evolve(parents);
    }


}
