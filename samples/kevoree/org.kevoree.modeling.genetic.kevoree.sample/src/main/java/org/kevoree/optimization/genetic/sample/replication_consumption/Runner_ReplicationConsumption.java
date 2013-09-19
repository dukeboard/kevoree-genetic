package org.kevoree.modeling.optimization.genetic.sample.replication_consumption;

import org.kevoree.ContainerNode;
import org.kevoree.ContainerRoot;
import org.kevoree.modeling.api.trace.TraceSequence;
import org.kevoree.modeling.optimization.api.FitnessFunction;
import org.kevoree.modeling.optimization.api.OptimizationEngine;
import org.kevoree.modeling.api.KMFContainer;
import org.kevoree.modeling.optimization.api.Solution;
import org.kevoree.modeling.optimization.engine.genetic.GeneticEngine;
import org.kevoree.optimization.genetic.sample.operator.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 17:51
 */
public class Runner_ReplicationConsumption implements FitnessFunction<ContainerRoot> {

    private static int bestReplicat = 4;

    public static void main(String[] args) throws Exception {

        //Init engine
        OptimizationEngine<ContainerRoot> engine = new GeneticEngine<ContainerRoot>()
                .addFitnessFuntion(new Runner_ReplicationConsumption())
                .addFitnessFuntion(new ConsumptionFitness())
                .addOperator(new AddComponent().setComponentTypeName("FakeConsole").setSelectorQuery("nodes[{components.size < " + bestReplicat + " }]"))
                .addOperator(new RemoveComponentOperator().setSelectorQuery("nodes[*]/components[*]"))
                .setPopulationFactory(new org.kevoree.modeling.optimization.genetic.sample.fillAllNodes.MiniCloudPopulationFactory());

        engine.setMaxGeneration(1000);
        //engine.setDistributed(true);
        //engine.setMaxTime(500l);
        //Solve and print solutions
        long currentTime = System.currentTimeMillis();
        List<Solution> result = engine.solve();
        System.out.println("Find solutions in " + (System.currentTimeMillis() - currentTime) + " ms");
        for(Solution sol : result){
            //sol.print(System.out, Arrays.asList("org.kevoree.ContainerRoot", "org.kevoree.ContainerNode", "org.kevoree.ComponentInstance"));
        }
    }

    @Override
    public boolean originAware(){
        return false;
    }

    @Override
    public double evaluate(ContainerRoot model, ContainerRoot origin, TraceSequence traceSequence) {
        double res = 0;
        for (ContainerNode n : ((ContainerRoot)model).getNodes()) {
            res = res + Math.abs(bestReplicat - n.getComponents().size());
        }
        return res;
    }

}
