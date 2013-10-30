package org.kevoree.modeling.genetic.tinycloud;

import org.cloud.Cloud;
import org.kevoree.modeling.genetic.tinycloud.fitnesses.CloudAdaptationCostFitness;
import org.kevoree.modeling.genetic.tinycloud.fitnesses.CloudConsumptionFitness;
import org.kevoree.modeling.genetic.tinycloud.fitnesses.CloudRedondencyFitness;
import org.kevoree.modeling.genetic.tinycloud.mutators.AddNodeMutator;
import org.kevoree.modeling.genetic.tinycloud.mutators.RemoveNodeMutator;
import org.kevoree.modeling.optimization.api.metric.ParetoMetrics;
import org.kevoree.modeling.optimization.api.mutation.MutationSelectionStrategy;
import org.kevoree.modeling.optimization.api.solution.Solution;
import org.kevoree.modeling.optimization.engine.genetic.GeneticAlgorithm;
import org.kevoree.modeling.optimization.engine.genetic.GeneticEngine;
import org.kevoree.modeling.optimization.executionmodel.ExecutionModel;
import org.kevoree.modeling.optimization.framework.SolutionPrinter;
import org.kevoree.modeling.optimization.web.Server;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/08/13
 * Time: 16:00
 */
public class SampleRunnerEpsilonMOEAD_Darwin {


    public static void main(String[] args) throws Exception {

        GeneticEngine<Cloud> engine = new GeneticEngine<Cloud>();

        engine.setMutationSelectionStrategy(MutationSelectionStrategy.RANDOM);

        engine.addOperator(new AddNodeMutator());
        engine.addOperator(new RemoveNodeMutator());
        engine.addFitnessFuntion(new CloudConsumptionFitness());
        engine.addFitnessFuntion(new CloudRedondencyFitness());
        engine.addFitnessFuntion(new CloudAdaptationCostFitness());

        engine.setMaxGeneration(100);
        engine.setPopulationFactory(new DefaultCloudPopulationFactory().setSize(20));
        engine.setAlgorithm(GeneticAlgorithm.EpsilonMOEA);
        engine.addParetoMetric(ParetoMetrics.HYPERVOLUME);
        engine.addParetoMetric(ParetoMetrics.MEAN);
        engine.addParetoMetric(ParetoMetrics.MIN_MEAN);



        List<Solution<Cloud>> result = engine.solve();
        engine.setMutationSelectionStrategy(MutationSelectionStrategy.DARWIN);
        List<Solution<Cloud>> result2 = engine.solve();

        for (Solution sol : result) {
            SolutionPrinter.instance$.print(sol, System.out);
        }
        for (Solution sol : result2) {
            SolutionPrinter.instance$.print(sol, System.out);
        }

        ExecutionModel model = engine.getExecutionModel();
        Server.instance$.serveExecutionModel(model);



    }

}
