package org.kevoree.modeling.genetic.genetictest;

import org.genetictest.BinaryString;
import org.kevoree.modeling.genetic.genetictest.fitnesses.DecimalEnumeratelFitness;
import org.kevoree.modeling.genetic.genetictest.fitnesses.MaximizeDiversityFitness;
import org.kevoree.modeling.genetic.genetictest.fitnesses.MaximizeTotalFitness;
import org.kevoree.modeling.genetic.genetictest.mutators.SwitchMutator;
import org.kevoree.modeling.optimization.api.OptimizationEngine;
import org.kevoree.modeling.optimization.api.solution.Solution;
import org.kevoree.modeling.optimization.engine.greedy.GreedyEngine;
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
public class SampleRunnerGreedy {

    private static void setBool(int x)
    {
        MaximizeTotalFitness.MAX = (double) x;
        MaximizeDiversityFitness.MAX = (double) x;
        DecimalEnumeratelFitness.MAX =(double)x;
        DefaultBinaryStringFactory.MAX =x;

    }


    public static void main(String[] args) throws Exception {

        OptimizationEngine<BinaryString> engine = new GreedyEngine<BinaryString>();

        engine.addOperator(new SwitchMutator());
        // engine.addFitnessFuntion(new MaximizeTotalFitness());



        engine.addFitnessFuntion(new DecimalEnumeratelFitness());

     /*  engine.addFitnessMetric(new DecimalEnumeratelFitness(), ParetoFitnessMetrics.MIN);
       engine.addFitnessMetric(new MaximizeTotalFitness(), ParetoFitnessMetrics.MAX);
       engine.addParetoMetric(ParetoMetrics.MIN_MEAN);*/


        setBool(20);
        engine.setPopulationFactory(new DefaultBinaryStringFactory().setSize(1));

        engine.setMaxGeneration(5000000);


        List<Solution<BinaryString>> result = engine.solve();

        System.out.println(result.size());
        System.out.println(DecimalEnumeratelFitness.total);

     for (Solution sol : result) {
            SolutionPrinter.instance$.print(sol, System.out);
        }

/*
        ExecutionModel model = engine.getExecutionModel();
        ExecutionModelExporter.instance$.exportMetrics(model, new File("results"));
        JSONModelSerializer saver = new JSONModelSerializer();
        // File temp = File.createTempFile("temporaryOutput",".json");
        // FileOutputStream fou = new FileOutputStream(temp);
        saver.serializeToStream(engine.getExecutionModel(), System.err);*/


    }



}
