package org.kevoree.genetic.sample.fillAllNodes;

import org.kevoree.ContainerRoot;
import org.kevoree.genetic.framework.GeneticEngine;
import org.kevoree.genetic.framework.KevoreeSolution;
import org.kevoree.genetic.library.operator.AddComponent;
import org.kevoree.genetic.library.operator.RemoveComponentOperator;
import org.kevoree.modeling.genetic.api.FitnessFunction;
import org.kevoree.modeling.genetic.api.ResolutionEngine;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 17:51
 */
public class Runner_FillAllNode implements FitnessFunction<ContainerRoot> {

    public static void main(String[] args) throws Exception {

        //Init engine
        ResolutionEngine engine = new GeneticEngine()
                .addFitnessFuntion(new Runner_FillAllNode())
                .addOperator(new AddComponent().setComponentTypeName("FakeConsole").setSelectorQuery("nodes[{components.size < 4 }]"))
                .addOperator(new RemoveComponentOperator().setSelectorQuery("nodes[*]/components[*]"))
                .setPopulationFactory(new MiniCloudPopulationFactory().setPopulationSize(100));

        engine.setMaxGeneration(100);

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
