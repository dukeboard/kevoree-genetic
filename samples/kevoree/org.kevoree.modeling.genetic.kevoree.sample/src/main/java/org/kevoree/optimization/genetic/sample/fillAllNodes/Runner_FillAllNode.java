package org.kevoree.modeling.optimization.genetic.sample.fillAllNodes;

import org.kevoree.ContainerRoot;
import org.kevoree.modeling.api.trace.TraceSequence;
import org.kevoree.modeling.optimization.api.FitnessFunction;
import org.kevoree.modeling.optimization.api.OptimizationEngine;
import org.kevoree.modeling.optimization.api.Solution;
import org.kevoree.modeling.optimization.engine.genetic.GeneticEngine;

import java.util.Arrays;
import java.util.List;
import org.kevoree.optimization.genetic.sample.operator.*;


/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 17:51
 */
public class Runner_FillAllNode implements FitnessFunction<ContainerRoot> {

    public static void main(String[] args) throws Exception {

        //Init engine
        OptimizationEngine<ContainerRoot> engine = new GeneticEngine<ContainerRoot>()
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
        List<Solution> result = engine.solve();
        System.out.println("Find solutions in " + (System.currentTimeMillis() - currentTime) + " ms");
        for(Solution sol : result){
            //Filter the PrettyPrint to only interesting elements
            //sol.print(System.out, Arrays.asList("org.kevoree.ContainerRoot", "org.kevoree.ContainerNode", "org.kevoree.ComponentInstance"));
        }
    }

    @Override
    public boolean originAware(){
        return false;
    }

    @Override
    public double evaluate(ContainerRoot model, ContainerRoot origin, TraceSequence traceSequence) {
       return model.selectByQuery("nodes[{components.size = 0 }]").size();
    }

}
