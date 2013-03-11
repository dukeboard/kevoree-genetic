package org.kevoree.genetic.framework;

import org.kevoree.ContainerRoot;
import org.kevoree.genetic.framework.internal.KevoreeProblem;
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
public class KevoreeSolution {

    private ContainerRoot model;
    private HashMap<String, Double> results = new HashMap<String, Double>();

    public KevoreeSolution(Solution sol, KevoreeProblem prob) {
        KevoreeVariable var = (KevoreeVariable) sol.getVariable(0);
        model = var.getModel();
        for (int i = 0; i < prob.getFitnesses().size(); i++) {
            results.put(prob.getFitnesses().get(i).getName(), sol.getObjective(i));
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
        writer.print("Solution( ");
        boolean isFirst = true;
        for (String name : results.keySet()) {
            if (!isFirst) {
                writer.print(" , ");
            }
            writer.print(name);
            writer.print("=");
            writer.print(results.get(name));
            isFirst = false;
        }
        writer.print(" )");
        writer.println();
    }


}
