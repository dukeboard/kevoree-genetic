package org.kevoree.modeling.genetic.democloud.greedy;

/**
 * Created with IntelliJ IDEA.
 * User: donia.elkateb
 * Date: 10/15/13
 * Time: 11:31 PM
 * To change this template use File | Settings | File Templates.
 */

import org.cloud.Cloud;

import java.io.File;
import java.util.List;

import org.kevoree.modeling.genetic.democloud.CloudPopulationFactory;
import org.kevoree.modeling.genetic.democloud.fitnesses.CloudCostFitness;
import org.kevoree.modeling.genetic.democloud.fitnesses.CloudLatencyFitness;
import org.kevoree.modeling.genetic.democloud.fitnesses.CloudRedundancyFitness;
import org.kevoree.modeling.genetic.democloud.fitnesses.CloudSimilarityFitness;

import org.kevoree.modeling.genetic.democloud.mutators.SmartMutator.AddSmartMutator;
import org.kevoree.modeling.genetic.democloud.mutators.SmartMutator.RemoveSmartMutator;
import org.kevoree.modeling.optimization.api.fitness.FitnessOrientation;
import org.kevoree.modeling.optimization.api.metric.ParetoFitnessMetrics;
import org.kevoree.modeling.optimization.api.metric.ParetoMetrics;
import org.kevoree.modeling.optimization.engine.genetic.GeneticAlgorithm;
import org.kevoree.modeling.optimization.engine.greedy.GreedyEngine;
import org.kevoree.modeling.optimization.api.solution.Solution;
import org.kevoree.modeling.optimization.executionmodel.ExecutionModel;
import org.kevoree.modeling.optimization.framework.SolutionPrinter;
import org.kevoree.modeling.optimization.util.ExecutionModelExporter;

import org.kevoree.modeling.genetic.democloud.mutators.AddNodeMutator;
import org.kevoree.modeling.genetic.democloud.mutators.RemoveNodeMutator;
import org.kevoree.modeling.genetic.democloud.mutators.RemoveSoftwareMutator;
import org.kevoree.modeling.genetic.democloud.mutators.CloneNodeMutator;

import org.kevoree.modeling.genetic.democloud.mutators.AddSoftwareMutator;


/**
 * Created with IntelliJ IDEA.
 * User: donia.elkateb
 * Date: 10/10/13
 * Time: 9:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class SampleRunnerGreedy {



    public static void main(String[] args) throws Exception {

        GreedyEngine<Cloud> engine = new GreedyEngine<Cloud>();

        //engine.desactivateOriginAware();



        engine.addOperator(new AddNodeMutator());
        engine.addOperator(new RemoveNodeMutator());
        engine.addOperator(new AddSoftwareMutator());

        engine.addOperator(new CloneNodeMutator());

        engine.addOperator(new RemoveSoftwareMutator());
        engine.addOperator(new AddSmartMutator());
        engine.addOperator(new RemoveSmartMutator());

        engine.addFitnessFunction(new CloudCostFitness(), 0.0, 10.0, FitnessOrientation.MINIMIZE);
        engine.addFitnessFunction(new CloudLatencyFitness());
        engine.addFitnessFunction(new CloudRedundancyFitness());
        engine.addFitnessFunction(new CloudSimilarityFitness());
        //engine.addFitnessFuntion(new CloudAdaptationFitness());


        engine.setMaxGeneration(50);
        engine.setPopulationFactory(new CloudPopulationFactory().setSize(10));


        engine.addFitnessMetric(new CloudCostFitness(), ParetoFitnessMetrics.MIN);
        engine.addFitnessMetric(new CloudCostFitness(), ParetoFitnessMetrics.MAX);
        engine.addParetoMetric(ParetoMetrics.MEAN);


        engine.setMaxGeneration(100);
        engine.setPopulationFactory(new CloudPopulationFactory().setSize(10));


        engine.addFitnessMetric(new CloudCostFitness(), ParetoFitnessMetrics.MIN);
        engine.addFitnessMetric(new CloudCostFitness(), ParetoFitnessMetrics.MAX);
        engine.addParetoMetric(ParetoMetrics.HYPERVOLUME);


        List<Solution<Cloud>> result = engine.solve();
        for (Solution sol : result) {
            SolutionPrinter.instance$.print(sol, System.out);
        }


        ExecutionModel model = engine.getExecutionModel();
        ExecutionModelExporter.instance$.exportMetrics(model,new File("results"));

    }
}
