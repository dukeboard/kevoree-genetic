package org.kevoree.modeling.genetic.democloud.evolutionary;

import org.cloud.Cloud;
import org.kevoree.modeling.genetic.democloud.DefaultCloudPopulationFactory;
import org.kevoree.modeling.genetic.democloud.fitnesses.*;
import org.kevoree.modeling.genetic.democloud.mutators.*;
import org.kevoree.modeling.genetic.democloud.mutators.SmartMutator.AddSmartMutator;
import org.kevoree.modeling.genetic.democloud.mutators.SmartMutator.RemoveSmartMutator;
import org.kevoree.modeling.optimization.api.Solution;
import org.kevoree.modeling.optimization.engine.genetic.GeneticAlgorithm;
import org.kevoree.modeling.optimization.engine.genetic.GeneticEngine;
import org.kevoree.modeling.optimization.framework.SolutionPrinter;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: donia.elkateb
 * Date: 9/30/13
 * Time: 4:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class SampleRunnerEpsilonRandom {

    public static void main(String[] args) throws Exception {

        GeneticEngine<Cloud> engine = new GeneticEngine<Cloud>();
        engine.addOperator(new AddNodeMutator());
        engine.addOperator(new RemoveNodeMutator());
        engine.addOperator(new AddSoftwareMutator());

        engine.addOperator(new CloneNodeMutator());
        engine.addOperator(new RemoveSoftwareMutator());

        engine.addOperator(new AddSmartMutator());
        engine.addOperator(new RemoveSmartMutator());
        engine.addFitnessFuntion(new CloudCostFitness());
        engine.addFitnessFuntion(new CloudLatencyFitness());
        engine.addFitnessFuntion(new CloudRedundancyFitness());
        //engine.addFitnessFuntion(new CloudAdaptationCostFitness());

        engine.setMaxGeneration(100);
        engine.setPopulationFactory(new DefaultCloudPopulationFactory().setSize(10));
        engine.setAlgorithm(GeneticAlgorithm.EpsilonRandom);

        List<Solution> result = engine.solve();
        SolutionPrinter printer = new SolutionPrinter();
        for (Solution sol : result) {
            printer.print(sol, System.out);
        }

    }
}
