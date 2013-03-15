package org.kevoree.genetic.framework.internal;

import org.kevoree.ComponentInstance;
import org.kevoree.ContainerNode;
import org.kevoree.genetic.framework.KevoreeSolution;

import java.io.PrintStream;

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
        writer.print(" ) / "+solution.getFitnessMean());
        writer.println();
    }

    public void structuralPrint(KevoreeSolution solution, PrintStream writer) {
        for (ContainerNode node : solution.getModel().getNodes()) {
            if(node.getHost() == null){
                printNode(node, writer, 1);
            }
        }
    }

    private void printNode(ContainerNode node, PrintStream writer, Integer tab) {
        for (int i = 0; i < tab; i++) {
            writer.print('\t');
        }
        writer.println("Node " + node.getName() + " : " + node.getTypeDefinition().getName());
        //print components
        printComponents(node, writer, tab);
        for (ContainerNode subNode : node.getHosts()) {
            printNode(subNode, writer, tab+1);
        }
    }

    private void printComponents(ContainerNode node, PrintStream writer, Integer tab) {
        for (ComponentInstance ci : node.getComponents()) {
            for (int i = 0; i < tab; i++) {
                writer.print('\t');
            }
            writer.println(" - Component " + ci.getName() + " : " + ci.getTypeDefinition().getName());
        }
    }


}
