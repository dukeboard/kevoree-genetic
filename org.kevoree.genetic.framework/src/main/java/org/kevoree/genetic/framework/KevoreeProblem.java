package org.kevoree.genetic.framework;

import org.kevoree.impl.ContainerRootImpl;
import org.moeaframework.core.Solution;
import org.moeaframework.problem.AbstractProblem;

/**
 * Created with IntelliJ IDEA.
 * User: jbourcie
 * Date: 11/02/13
 * Time: 14:57
 * To change this template use File | Settings | File Templates.
 */
public class KevoreeProblem extends AbstractProblem{

    public KevoreeProblem(int numberOfObjectives) {
        // 1 variable = 1 Kevoree Variable
        // Number of Objectives = number of Objectives of the subproblem
        super(1, numberOfObjectives);
    }

    @Override
    public void evaluate(Solution solution) {
        //TODO call the fitness function here and set the result to the fitness function solution.setObjective( 0 , double);
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(1, numberOfObjectives);
        solution.setVariable(0, new KevoreeVariable(new ContainerRootImpl()));
        return solution;
    }
}
