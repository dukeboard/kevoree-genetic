package org.kevoree.genetic.sample.fillAllNodes;

import org.kevoree.ContainerNode;
import org.kevoree.ContainerRoot;
import org.kevoree.genetic.framework.KevoreeFitnessFunction;
import org.kevoree.genetic.framework.KevoreeGeneticEngine;
import org.kevoree.genetic.framework.KevoreeSolution;
import org.kevoree.genetic.library.operator.AddComponent;
import org.kevoree.genetic.library.operator.RemoveComponent;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 17:51
 */
public class Runner_FillAllNode implements KevoreeFitnessFunction {

    public static void main(String[] args) throws Exception {

        //Init engine
        KevoreeGeneticEngine engine = new KevoreeGeneticEngine()
                .addFitnessFuntion(new Runner_FillAllNode())
                .addOperator(new AddComponent().setComponentTypeName("FakeConsole").setSelectorQuery("nodes[{components.size < 4 }]"))
                .addOperator(new RemoveComponent().setSelectorQuery("nodes[{name = * }]/components[{name = *}]"))
                .setPopulationFactory(new MiniCloudPopulationFactory().setPopulationSize(400));

        engine.setMaxGeneration(200);
       // engine.setDistributed(true);
        //engine.setMaxTime(500l);
        //Solve and print solutions
        long currentTime = System.currentTimeMillis();
        List<KevoreeSolution> result = engine.solve();
        System.out.println("Find solutions in " + (System.currentTimeMillis() - currentTime) + " ms");
        for(KevoreeSolution sol : result){
            sol.print(System.out);
        }
    }

    @Override
    public double evaluate(ContainerRoot model) {
        double res = 0;
        for (ContainerNode n : model.getNodes()) {
            if (n.getComponents().isEmpty()) {
                res++;
            }
        }
        return res;
        // return model.selectByQuery("nodes[{components.size = 0 }]").size();
    }

    @Override
    public String getName() {
        return "FillAllFitness";
    }
}
