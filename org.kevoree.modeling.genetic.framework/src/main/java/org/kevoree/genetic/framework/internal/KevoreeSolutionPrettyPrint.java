package org.kevoree.genetic.framework.internal;

import org.kevoree.genetic.framework.KevoreeSolution;
import org.kevoree.modeling.api.KMFContainer;

import java.io.PrintStream;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/03/13
 * Time: 07:32
 */
public class KevoreeSolutionPrettyPrint {

    public void fitnessPrint(KevoreeSolution solution, PrintStream writer) {
        writer.print("Solution( ");
        boolean isFirst = true;
        for (String name : solution.getFitnessNames()) {
            if (!isFirst) {
                writer.print(" , ");
            }
            writer.print(name);
            writer.print("=");
            writer.print(solution.getResultFromFitness(name));
            isFirst = false;
        }
        writer.print(" ) / " + solution.getFitnessMean());
        writer.println();
    }

    public void structuralPrint(KevoreeSolution solution, PrintStream writer) {
        structuralPrint(solution, writer, null);
    }

    public void structuralPrint(KevoreeSolution solution, PrintStream writer, List<String> filters) {
        if (filters != null) {
            if (!filters.contains(solution.getModel().metaClassName())) {
                return;
            }
        }
        System.out.println(solution.getModel().metaClassName() + "-" + solution.getModel().path());
        for (Object node : solution.getModel().containedElementsList()) {
            printNode((KMFContainer) node, writer, 1, filters);
        }
    }

    private void printNode(KMFContainer node, PrintStream writer, Integer tab, List<String> filters) {

        if (filters != null) {
            if (!filters.contains(node.metaClassName())) {
                return;
            }
        }


        for (int i = 0; i < tab; i++) {
            writer.print('\t');
        }
        writer.println(node.metaClassName() + "-" + node.path());
        //print components
        for (Object subNode : node.containedElementsList()) {
            printNode((KMFContainer) subNode, writer, tab + 1,filters);
        }
    }

}
