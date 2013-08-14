package org.kevoree.modeling.optimization.genetic.sample.replication_consumption;

import org.kevoree.ContainerNode;
import org.kevoree.ContainerRoot;
import org.kevoree.modeling.optimization.genetic.framework.GeneticEngine;
import org.kevoree.modeling.optimization.genetic.framework.KevoreeSolution;
import org.kevoree.modeling.optimization.genetic.library.operator.AddComponent;
import org.kevoree.modeling.optimization.genetic.library.operator.RemoveComponentOperator;
import org.kevoree.modeling.optimization.genetic.sample.fillAllNodes.MiniCloudPopulationFactory;
import org.kevoree.modeling.api.KMFContainer;
import org.kevoree.modeling.genetic.api.FitnessFunction;
import org.kevoree.modeling.genetic.api.ResolutionEngine;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 17:51
 */
public class Runner_ReplicationConsumption implements FitnessFunction {

    private static int bestReplicat = 4;

    public static void main(String[] args) throws Exception {

        //Init engine
        ResolutionEngine engine = new GeneticEngine()
                .addFitnessFuntion(new Runner_ReplicationConsumption())
                .addFitnessFuntion(new ConsumptionFitness())
                .addOperator(new AddComponent().setComponentTypeName("FakeConsole").setSelectorQuery("nodes[{components.size < " + bestReplicat + " }]"))
                .addOperator(new RemoveComponentOperator().setSelectorQuery("nodes[*]/components[*]"))
                .setPopulationFactory(new MiniCloudPopulationFactory());

        engine.setMaxGeneration(1000);
        //engine.setDistributed(true);
        //engine.setMaxTime(500l);
        //Solve and print solutions
        long currentTime = System.currentTimeMillis();
        List<KevoreeSolution> result = engine.solve();
        System.out.println("Find solutions in " + (System.currentTimeMillis() - currentTime) + " ms");
        for(KevoreeSolution sol : result){
            sol.print(System.out, Arrays.asList("org.kevoree.ContainerRoot", "org.kevoree.ContainerNode", "org.kevoree.ComponentInstance"));
        }
    }

    @Override
    public double evaluate(KMFContainer model) {
        double res = 0;
        for (ContainerNode n : ((ContainerRoot)model).getNodes()) {
            res = res + Math.abs(bestReplicat - n.getComponents().size());
        }
        return res;
    }

}
