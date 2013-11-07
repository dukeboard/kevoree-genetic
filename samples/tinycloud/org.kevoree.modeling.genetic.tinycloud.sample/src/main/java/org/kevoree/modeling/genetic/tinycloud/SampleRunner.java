package org.kevoree.modeling.genetic.tinycloud;

import org.cloud.Cloud;
import org.kevoree.modeling.genetic.tinycloud.fitnesses.CloudAdaptationCostFitness;
import org.kevoree.modeling.genetic.tinycloud.fitnesses.CloudConsumptionFitness;
import org.kevoree.modeling.genetic.tinycloud.fitnesses.CloudRedondencyFitness;
import org.kevoree.modeling.genetic.tinycloud.mutators.AddNodeMutator;
import org.kevoree.modeling.genetic.tinycloud.mutators.RemoveNodeMutator;
import org.kevoree.modeling.optimization.api.OptimizationEngine;
import org.kevoree.modeling.optimization.api.metric.ParetoFitnessMetrics;
import org.kevoree.modeling.optimization.api.metric.ParetoMetrics;
import org.kevoree.modeling.optimization.api.solution.Solution;
import org.kevoree.modeling.optimization.engine.genetic.GeneticEngine;
import org.kevoree.modeling.optimization.executionmodel.ExecutionModel;
import org.kevoree.modeling.optimization.executionmodel.serializer.JSONModelSerializer;
import org.kevoree.modeling.optimization.framework.SolutionPrinter;
import org.kevoree.modeling.optimization.util.ExecutionModelExporter;

import java.io.File;
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

        engine.addOperator(new AddNodeMutator());
        engine.addOperator(new RemoveNodeMutator());
       engine.addFitnessFuntion(new CloudConsumptionFitness());
        engine.addFitnessFuntion(new CloudRedondencyFitness());
        engine.addFitnessFuntion(new CloudAdaptationCostFitness());

        engine.addFitnessMetric(new CloudRedondencyFitness(), ParetoFitnessMetrics.MIN);
        engine.addFitnessMetric(new CloudRedondencyFitness(), ParetoFitnessMetrics.MAX);
       engine.addParetoMetric(ParetoMetrics.MEAN);

       /* engine.addFitnessMetric(new CloudConsumptionFitness(), ParetoFitnessMetrics.MIN);
        engine.addFitnessMetric(new CloudConsumptionFitness(), ParetoFitnessMetrics.MAX);
         engine.addParetoMetric(ParetoMetrics.MEAN);   */

      /*  engine.addFitnessMetric(new CloudAdaptationCostFitness(), ParetoFitnessMetrics.MIN);
        engine.addFitnessMetric(new CloudAdaptationCostFitness(), ParetoFitnessMetrics.MAX);
        engine.addParetoMetric(ParetoMetrics.MEAN);       */



        engine.setMaxGeneration(100);     //100 iterations of the genetic algorithm
        engine.setPopulationFactory(new DefaultCloudPopulationFactory().setSize(10));    //Population size 10 clouds

        List<Solution<Cloud>> result = engine.solve();          //Run the genetic optimization

        for (Solution sol : result) {                                  //Print the 10 solutions, normally should be the latest 10 clouds in the population
            SolutionPrinter.instance$.print(sol, System.out);
        }


        ExecutionModel model = engine.getExecutionModel();
        ExecutionModelExporter.instance$.exportMetrics(model,new File("results"));


        JSONModelSerializer saver = new JSONModelSerializer();
       // File temp = File.createTempFile("temporaryOutput",".json");
       // FileOutputStream fou = new FileOutputStream(temp);
        //saver.serializeToStream(engine.getExecutionModel(),System.err);

        //fou.close();
        //System.out.println(temp.getAbsolutePath());







    }

}
