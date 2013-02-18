package org.kevoree.genetic.sample.fillAllNodes;

import org.kevoree.ContainerNode;
import org.kevoree.ContainerRoot;
import org.kevoree.genetic.framework.KevoreeFitnessFunction;
import org.kevoree.genetic.framework.KevoreeGeneticEngine;
import org.kevoree.genetic.library.operator.AddComponent;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 17:51
 */
public class Runner_FillAllNode implements KevoreeFitnessFunction {

    public static void main(String[] args) {

        long currentTime = System.currentTimeMillis();

        //Init engine
        KevoreeGeneticEngine engine = new KevoreeGeneticEngine()
                .addFitnessFuntion(new Runner_FillAllNode())
                .addOperator(new AddComponent().setComponentTypeName("FakeConsole").setSelectorQuery("nodes[{components.size = 0 }]"))
                .setPopulationFactory(new MiniCloudPopulationFactory());

        //Solve and print solutions
        List<ContainerRoot> result = engine.solve();

        System.out.println("Find solutions in "+(System.currentTimeMillis()-currentTime)+" ms");

        int i = 0;
        for (ContainerRoot solution : result) {
            i++;
            System.out.println("Solution " + i);
            for (ContainerNode node : solution.getNodes()) {
                System.out.println("    Node:" + node.getName() + ",components=" + node.getComponents().size());
            }
        }
    }

    @Override
    public double evaluate(ContainerRoot model) {
        return model.selectByQuery("nodes[{components.size = 0 }]").size();
    }

    @Override
    public String getName() {
        return "FillAllFitness";
    }
}
