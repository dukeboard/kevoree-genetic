package org.kevoree.modeling.genetic.genetictest;

import org.genetictest.BinaryString;

import org.kevoree.modeling.genetic.genetictest.fitnesses.MaximizeDiversity;
import org.kevoree.modeling.genetic.genetictest.fitnesses.MaximizeTotalFitness;
import org.kevoree.modeling.genetic.genetictest.mutators.SwitchMutator;
import org.kevoree.modeling.genetic.genetictest.mutators.SwitchMutatorAll;
import org.kevoree.modeling.optimization.api.OptimizationEngine;
import org.kevoree.modeling.optimization.api.metric.ParetoFitnessMetrics;
import org.kevoree.modeling.optimization.api.metric.ParetoMetrics;
import org.kevoree.modeling.optimization.api.solution.Solution;
import org.kevoree.modeling.optimization.engine.genetic.GeneticAlgorithm;
import org.kevoree.modeling.optimization.engine.genetic.GeneticEngine;
import org.kevoree.modeling.optimization.executionmodel.ExecutionModel;
import org.kevoree.modeling.optimization.executionmodel.serializer.JSONModelSerializer;
import org.kevoree.modeling.optimization.framework.SolutionPrinter;
import org.kevoree.modeling.optimization.util.ExecutionModelExporter;

import org.kevoree.modeling.optimization.api.OptimizationEngine;
import org.kevoree.modeling.optimization.api.metric.ParetoFitnessMetrics;
import org.kevoree.modeling.optimization.api.metric.ParetoMetrics;
import org.kevoree.modeling.optimization.api.solution.Solution;
import org.kevoree.modeling.optimization.engine.fullsearch.FullSearchEngine;
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

   OptimizationEngine<BinaryString> engine = new FullSearchEngine<BinaryString>();

        engine.addOperator(new SwitchMutator());

        engine.addFitnessFuntion(new MaximizeTotalFitness());
      //  engine.addFitnessFuntion(new MaximizeDiversity());


        engine.setMaxGeneration(65536);
        engine.setPopulationFactory(new DefaultBinaryStringFactory().setSize(10));

        List<Solution<BinaryString>> result = engine.solve();
        for (Solution sol : result) {
            SolutionPrinter.instance$.print(sol, System.out);
        }
        ExecutionModel model = engine.getExecutionModel();
        ExecutionModelExporter.instance$.exportMetrics(model, new File("results"));
        JSONModelSerializer saver = new JSONModelSerializer();
        // File temp = File.createTempFile("temporaryOutput",".json");
        // FileOutputStream fou = new FileOutputStream(temp);
        saver.serializeToStream(engine.getExecutionModel(), System.err);

        /*


        GeneticEngine<BinaryString> engine = new GeneticEngine<BinaryString>();
        engine.addOperator(new SwitchMutator());

        engine.setAlgorithm(GeneticAlgorithm.HypervolumeNSGAII);


        engine.addFitnessFuntion(new MaximizeDiversity());
      //  engine.addFitnessFuntion(new MaximizeTotalFitness());

        engine.setMaxGeneration(1000)  ;
        engine.setPopulationFactory(new DefaultBinaryStringFactory());

        List<Solution<BinaryString>> result = engine.solve();
        for (Solution sol : result) {
            SolutionPrinter.instance$.print(sol, System.out);
        }
              */

    /*   engine.addOperator(new AddNodeMutator());
        engine.addOperator(new RemoveNodeMutator());
        engine.addFitnessFuntion(new CloudCostFitness());
        engine.addFitnessFuntion(new CloudRedundancyFitness());
        engine.addFitnessFuntion(new CloudAdaptationCostFitness());

        engine.setMaxGeneration(100);
        engine.setPopulationFactory(new DefaultCloudPopulationFactory().setSize(10));

        List<Solution<Cloud>> result = engine.solve();
        for (Solution sol : result) {
            SolutionPrinter.instance$.print(sol, System.out);
        }


        ExecutionModel model = engine.getExecutionModel();
        ExecutionModelExporter.instance$.exportMetrics(model,new File("results"));


        JSONModelSerializer saver = new JSONModelSerializer();

                                    */

        }



}
