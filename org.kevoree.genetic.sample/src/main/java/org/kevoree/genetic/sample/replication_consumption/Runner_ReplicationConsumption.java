package org.kevoree.genetic.sample.replication_consumption;

import org.kevoree.ContainerNode;
import org.kevoree.ContainerRoot;
import org.kevoree.genetic.framework.KevoreeFitnessFunction;
import org.kevoree.genetic.framework.KevoreeGeneticEngine;
import org.kevoree.genetic.framework.KevoreeSolution;
import org.kevoree.genetic.library.operator.AddComponent;
import org.kevoree.genetic.library.operator.RemoveComponent;
import org.kevoree.genetic.sample.fillAllNodes.MiniCloudPopulationFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 17:51
 */
public class Runner_ReplicationConsumption implements KevoreeFitnessFunction {

    private static int bestReplicat = 4;

    public static void main(String[] args) throws Exception {

        //Init engine
        KevoreeGeneticEngine engine = new KevoreeGeneticEngine()
                .addFitnessFuntion(new Runner_ReplicationConsumption())
                .addFitnessFuntion(new ConsumptionFitness())
                .addOperator(new AddComponent().setComponentTypeName("FakeConsole").setSelectorQuery("nodes[{components.size < " + bestReplicat + " }]"))
                .addOperator(new RemoveComponent().setSelectorQuery("nodes[*]/components[*]"))
                .setPopulationFactory(new MiniCloudPopulationFactory());

        engine.setMaxGeneration(1000);
        //engine.setDistributed(true);
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
            res = res + Math.abs(bestReplicat - n.getComponents().size());
        }
        return res;
    }

}
