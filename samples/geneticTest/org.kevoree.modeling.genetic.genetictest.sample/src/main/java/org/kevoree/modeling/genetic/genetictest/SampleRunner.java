package org.kevoree.modeling.genetic.genetictest;

import org.genetictest.BinaryString;
import org.kevoree.modeling.genetic.genetictest.fitnesses.MaximizeDiversity;
import org.kevoree.modeling.genetic.genetictest.mutators.SwitchMutator;
import org.kevoree.modeling.optimization.api.solution.Solution;
import org.kevoree.modeling.optimization.engine.genetic.GeneticAlgorithm;
import org.kevoree.modeling.optimization.engine.genetic.GeneticEngine;
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


        }



}
