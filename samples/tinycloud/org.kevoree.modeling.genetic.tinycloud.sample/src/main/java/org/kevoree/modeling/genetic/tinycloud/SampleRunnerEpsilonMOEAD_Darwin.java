package org.kevoree.modeling.genetic.tinycloud;

import org.cloud.Cloud;
import org.kevoree.modeling.genetic.tinycloud.fitnesses.CloudAdaptationCostFitness;
import org.kevoree.modeling.genetic.tinycloud.fitnesses.CloudConsumptionFitness;
import org.kevoree.modeling.genetic.tinycloud.fitnesses.CloudRedondencyFitness;
import org.kevoree.modeling.genetic.tinycloud.mutators.AddNodeMutator;
import org.kevoree.modeling.genetic.tinycloud.mutators.RemoveNodeMutator;
import org.kevoree.modeling.optimization.api.metric.ParetoMetrics;
import org.kevoree.modeling.optimization.api.mutation.MutationSelectionStrategy;
import org.kevoree.modeling.optimization.engine.genetic.GeneticAlgorithm;
import org.kevoree.modeling.optimization.engine.genetic.GeneticEngine;
import org.kevoree.modeling.optimization.executionmodel.ExecutionModel;
import org.kevoree.modeling.optimization.web.Server;

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

        engine.setMaxGeneration(200);
        engine.setPopulationFactory(new DefaultCloudPopulationFactory().setSize(20));
        engine.setAlgorithm(GeneticAlgorithm.EpsilonMOEA);
        //engine.addParetoMetric(ParetoMetrics.HYPERVOLUME);
        //engine.addParetoMetric(ParetoMetrics.MEAN);
        engine.addParetoMetric(ParetoMetrics.MIN_MEAN);



        engine.solve();
        engine.setMutationSelectionStrategy(MutationSelectionStrategy.DARWIN);
        engine.solve();
        engine.solve();

        ExecutionModel model = engine.getExecutionModel();
        Server.instance$.serveExecutionModel(model);


        System.out.println(engine.getMutationSelector().toString());

    }

}
