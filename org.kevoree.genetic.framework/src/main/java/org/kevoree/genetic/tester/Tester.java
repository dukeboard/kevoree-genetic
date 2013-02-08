package org.kevoree.genetic.tester;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.problem.StandardProblems;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 08/02/13
 * Time: 13:36
 */
public class Tester {

    public static void main(String[] args) throws ClassNotFoundException {

        StandardProblems pbs = new StandardProblems();

        //configure and run this experiment
        NondominatedPopulation result = new Executor()
                .withProblem("UF1")
                .withAlgorithm("NSGAII")
                .withMaxEvaluations(10000)
                .distributeOnAllCores()
                .run();

        //display the results
        for (Solution solution : result) {
            System.out.println(solution.getObjective(0) + " " +
                    solution.getObjective(1));
        }
    }

}
