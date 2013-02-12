package org.kevoree.genetic.framework.internal;

import org.kevoree.genetic.framework.KevoreeFitnessFunction;
import org.kevoree.impl.ContainerRootImpl;
import org.moeaframework.core.Solution;
import org.moeaframework.problem.AbstractProblem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jbourcie
 * Date: 11/02/13
 * Time: 14:57
 */
public class KevoreeProblem extends AbstractProblem {


    private List<KevoreeFitnessFunction> fitness = new ArrayList<KevoreeFitnessFunction>();

    public KevoreeProblem(int numberOfObjectives) {
        // 1 variable = 1 Kevoree Variable
        // Number of Objectives = number of Objectives of the subproblem
        super(1, numberOfObjectives);
    }

    public List<KevoreeFitnessFunction> getFitness() {
        return fitness;
    }

    public void setFitness(List<KevoreeFitnessFunction> fitnessList) {
        this.fitness = fitness;
    }

    @Override
    public void evaluate(Solution solution) {
        //TODO call the fitness functions here and set the result to the fitness function solution.setObjective( 0 , double);
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(1, numberOfObjectives);
        solution.setVariable(0, new KevoreeVariable(new ContainerRootImpl()));
        return solution;
    }
}
