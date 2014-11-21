package org.kevoree.modeling.genetic.democloud.evolutionary;

import org.cloud.Cloud;
import org.kevoree.modeling.genetic.democloud.CloudPopulationFactory;
import org.kevoree.modeling.genetic.democloud.fitnesses.CloudCostFitness;
import org.kevoree.modeling.genetic.democloud.fitnesses.CloudLatencyFitness;
import org.kevoree.modeling.genetic.democloud.fitnesses.CloudRedundancyFitness;

import org.kevoree.modeling.genetic.democloud.mutators.AddNodeMutator;
import org.kevoree.modeling.genetic.democloud.mutators.RemoveNodeMutator;
import org.kevoree.modeling.genetic.democloud.mutators.CloneNodeMutator;

import org.kevoree.modeling.genetic.democloud.mutators.AddSoftwareMutator;
import org.kevoree.modeling.genetic.democloud.mutators.SmartMutator.AddSmartMutator;

import org.kevoree.modeling.optimization.api.fitness.FitnessOrientation;
import org.kevoree.modeling.optimization.api.metric.ParetoFitnessMetrics;
import org.kevoree.modeling.optimization.api.metric.ParetoMetrics;
import org.kevoree.modeling.optimization.api.solution.Solution;
import org.kevoree.modeling.optimization.engine.genetic.GeneticAlgorithm;
import org.kevoree.modeling.optimization.engine.genetic.GeneticEngine;
import org.kevoree.modeling.optimization.framework.SolutionPrinter;
import org.kevoree.modeling.optimization.executionmodel.ExecutionModel;
import org.kevoree.modeling.optimization.util.ExecutionModelExporter;
import org.kevoree.modeling.optimization.web.Server;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: donia.elkateb
 * Date: 9/30/13
 * Time: 4:21 PM
 */
public class SampleRunnerEpsilonNSGII {

    public static void main(String[] args) throws Exception {

        GeneticEngine<Cloud> engine = new GeneticEngine<Cloud>();
        engine.addOperator(new AddNodeMutator());
        engine.addOperator(new RemoveNodeMutator());
        engine.addOperator(new AddSoftwareMutator());

        engine.addOperator(new CloneNodeMutator());
        // engine.addOperator(new RemoveSoftwareMutator());

        engine.addOperator(new AddSmartMutator());
        //  engine.addOperator(new RemoveSmartMutator());

        engine.addFitnessFunction(new CloudCostFitness(), 0, 10, FitnessOrientation.MINIMIZE);
        engine.addFitnessFunction(new CloudLatencyFitness(), 0, 100, FitnessOrientation.MINIMIZE);
        engine.addFitnessFunction(new CloudRedundancyFitness(), 0, 1, FitnessOrientation.MINIMIZE);


        //engine.addFitnessFuntion(new CloudAdaptationCostFitness());

        engine.setMaxGeneration(100);
        engine.setPopulationFactory(new CloudPopulationFactory().setSize(10));

        engine.setAlgorithm(GeneticAlgorithm.EpsilonNSGII);

        engine.addFitnessMetric(new CloudCostFitness(), ParetoFitnessMetrics.MIN);
        engine.addFitnessMetric(new CloudCostFitness(), ParetoFitnessMetrics.MAX);
        engine.addParetoMetric(ParetoMetrics.MEAN);

        List<Solution<Cloud>> result = engine.solve();
        for (Solution sol : result) {
            SolutionPrinter.instance$.print(sol, System.out);
        }


        ExecutionModel model = engine.getExecutionModel();
        Server.instance$.serveExecutionModel(model);


       // ExecutionModelExporter.instance$.exportMetrics(model, new File("results"));


    }
}
