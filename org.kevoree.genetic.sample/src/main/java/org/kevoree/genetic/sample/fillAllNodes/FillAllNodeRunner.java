package org.kevoree.genetic.sample.fillAllNodes;

import org.kevoree.ContainerNode;
import org.kevoree.ContainerRoot;
import org.kevoree.genetic.framework.KevoreeGeneticEngine;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 17:51
 */
public class FillAllNodeRunner {

    public static void main(String[] args) {
        //Init engine
        KevoreeGeneticEngine engine = new KevoreeGeneticEngine()
                .addFitnessFuntion(new FillAllFitness())
                .addOperator(new AddComponentMutator("FakeConsole"))
                .setPopulationFactory(new MiniCloudPopulationFactory());

        //Solve and print solutions
        List<ContainerRoot> result = engine.solve();
        int i = 0;
        for (ContainerRoot solution : result) {
            i++;
            System.out.println("Solution " + i);
            for (ContainerNode node : solution.getNodes()) {
                System.out.println("    Node:" + node.getName() + ",components=" + node.getComponents().size());
            }
        }


    }

}
