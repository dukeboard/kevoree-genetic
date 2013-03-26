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
                .addOperator(new RemoveComponent().setSelectorQuery("nodes[*]/components[*]"))
                .setPopulationFactory(new MiniCloudPopulationFactory().setPopulationSize(100));

        engine.setMaxGeneration(200);

      //  engine.setMonitored(true);

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
       return model.selectByQuery("nodes[{components.size = 0 }]").size();
    }

}
