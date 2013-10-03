package org.kevoree.modeling.genetic.tinycloud;

import org.cloud.Cloud;
import org.kevoree.modeling.genetic.tinycloud.fitnesses.CloudAdaptationCostFitness;
import org.kevoree.modeling.genetic.tinycloud.fitnesses.CloudConsumptionFitness;
import org.kevoree.modeling.genetic.tinycloud.fitnesses.CloudRedondencyFitness;
import org.kevoree.modeling.genetic.tinycloud.mutators.AddNodeMutator;
import org.kevoree.modeling.genetic.tinycloud.mutators.RemoveNodeMutator;
import org.kevoree.modeling.optimization.api.Metrics;
import org.kevoree.modeling.optimization.api.OptimizationEngine;
import org.kevoree.modeling.optimization.api.Solution;
import org.kevoree.modeling.optimization.engine.genetic.GeneticEngine;
import org.kevoree.modeling.optimization.executionmodel.serializer.JSONModelSerializer;
import org.kevoree.modeling.optimization.framework.SolutionPrinter;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/08/13
 * Time: 16:00
 */
public class SampleRunner {


    public static void main(String[] args) throws Exception {

        OptimizationEngine<Cloud> engine = new GeneticEngine<Cloud>();

        engine.activateExecutionModel();


        engine.addOperator(new AddNodeMutator());
        engine.addOperator(new RemoveNodeMutator());
        engine.addFitnessFuntion(new CloudConsumptionFitness());
        engine.addFitnessFuntion(new CloudRedondencyFitness());
        engine.addFitnessFuntion(new CloudAdaptationCostFitness());

        engine.addMetric(new CloudRedondencyFitness(), Metrics.Min);
        engine.addMetric(new CloudRedondencyFitness(), Metrics.Max);

        engine.setMaxGeneration(100);
        engine.setPopulationFactory(new DefaultCloudPopulationFactory().setSize(10));

        List<Solution> result = engine.solve();
        SolutionPrinter printer = new SolutionPrinter();
        for (Solution sol : result) {
            printer.print(sol, System.out);
        }

        JSONModelSerializer saver = new JSONModelSerializer();
        saver.serialize(engine.getExecutionModel(),System.out);


    }

}
