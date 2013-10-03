package org.kevoree.modeling.genetic.democloud.evolutionary;

import org.cloud.Cloud;
import org.kevoree.modeling.genetic.democloud.fitnesses.*;
import org.kevoree.modeling.genetic.democloud.mutators.*;
import org.kevoree.modeling.genetic.democloud.DefaultCloudPopulationFactory;
import org.kevoree.modeling.optimization.engine.genetic.GeneticAlgorithm;
import org.kevoree.modeling.optimization.api.Solution;
import org.kevoree.modeling.optimization.engine.genetic.GeneticEngine;

import org.kevoree.modeling.optimization.framework.SolutionPrinter;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/08/13
 * Time: 16:00
 */
public class SampleRunnerEpsilonMOEA {


    public static void main(String[] args) throws Exception {

        GeneticEngine<Cloud> engine = new GeneticEngine<Cloud>();

        engine.addOperator(new AddNodeMutator());
        engine.addOperator(new RemoveNodeMutator());
        engine.addOperator(new AddSoftwareMutator());

        engine.addOperator(new CloneNodeMutator());
        engine.addOperator(new RemoveSoftwareMutator());

        engine.addFitnessFuntion(new CloudCostFitness());
        engine.addFitnessFuntion(new CloudLatencyFitness());
        engine.addFitnessFuntion(new CloudRedundancyFitness());
        //engine.addFitnessFuntion(new CloudAdaptationCostFitness());

        engine.setMaxGeneration(100);
        engine.setPopulationFactory(new DefaultCloudPopulationFactory().setSize(10));
        engine.setAlgorithm(GeneticAlgorithm.EpsilonMOEA);

        List<Solution> result = engine.solve();
        SolutionPrinter printer = new SolutionPrinter();
        for (Solution sol : result) {
            printer.print(sol, System.out);
        }

    }

}





