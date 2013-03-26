package org.kevoree.genetic.framework;

import org.kevoree.ContainerRoot;
import org.kevoree.genetic.framework.internal.KevoreeProblem;
import org.kevoree.genetic.framework.internal.KevoreeSolutionPrettyPrint;
import org.kevoree.genetic.framework.internal.KevoreeVariable;
import org.moeaframework.core.Solution;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 09/03/13
 * Time: 07:03
 */
public class KevoreeSolution implements Comparable {

    private ContainerRoot model;
    private HashMap<String, Double> results = new HashMap<String, Double>();

    public KevoreeSolution(Solution sol, KevoreeProblem prob) {
        KevoreeVariable var = (KevoreeVariable) sol.getVariable(0);
        model = var.getModel();
        for (int i = 0; i < prob.getFitnesses().size(); i++) {
            results.put(prob.getFitnesses().get(i).getClass().getSimpleName(), sol.getObjective(i));
        }
    }

    public ContainerRoot getModel() {
        return model;
    }

    public Set<String> getFitnessNames() {
        return results.keySet();
    }

    public Double getResultFromFitness(String name) {
        return results.get(name);
    }


    public void print(PrintStream writer) {
        KevoreeSolutionPrettyPrint printer = new KevoreeSolutionPrettyPrint();
        printer.fitnessPrint(this, writer);
        printer.structuralPrint(this, writer);
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof KevoreeSolution) {
            KevoreeSolution remote = (KevoreeSolution) o;
            if (remote.getFitnessNames().size() == getFitnessNames().size()) {
                double currentMean = getFitnessMean();
                double remoteMean = remote.getFitnessMean();
                if (currentMean == remoteMean) {
                    return 0;
                } else {
                    if (currentMean < remoteMean) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public Double getFitnessMean() {
        double value = 0d;
        for (String fitName : getFitnessNames()) {
            value = value + getResultFromFitness(fitName);
        }
        return value / results.size();
    }

}
