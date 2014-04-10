package org.kevoree.modeling.genetic.testsuite.evolutionary;

import org.kevoree.modeling.genetic.testsuite.TestPopulationFactory;
import org.kevoree.modeling.genetic.testsuite.fitnesses.DTLZ.FitnessDTLZ;
import org.kevoree.modeling.genetic.testsuite.fitnesses.ZDT.FitnessZDT;
import org.kevoree.modeling.genetic.testsuite.fitnesses.ZDT.FitnessZDTF0;
import org.kevoree.modeling.genetic.testsuite.fitnesses.ZDT.FitnessZDTF1;
import org.kevoree.modeling.genetic.testsuite.mutators.RandomChange;
import org.kevoree.modeling.optimization.api.metric.ParetoMetrics;
import org.kevoree.modeling.optimization.api.mutation.MutationSelectionStrategy;
import org.kevoree.modeling.optimization.api.solution.Solution;
import org.kevoree.modeling.optimization.engine.genetic.GeneticAlgorithm;
import org.kevoree.modeling.optimization.engine.genetic.GeneticEngine;
import org.kevoree.modeling.optimization.executionmodel.ExecutionModel;
import org.kevoree.modeling.optimization.framework.SolutionPrinter;
import org.kevoree.modeling.optimization.web.Server;
import org.testsuite.Dlist;

import java.util.List;

/**
 * Created by assaad.moawad on 4/9/2014.
 */
public class SampleRunnerDTLZNSGAII {
    public static void main(String[] args) throws Exception {

        GeneticEngine<Dlist> engine = new GeneticEngine<Dlist>();
        // engine.desactivateOriginAware();

        engine.addOperator(new RandomChange());

        //Set both fitness to the same zdt;
        FitnessDTLZ.dtlz=4;   //Set DTLZ4
        FitnessDTLZ.numOfObjectif=10;  //Set 10 objectives

        //Add 10 fitness functions

        for(int i=0; i<10 ;i++){
            engine.addFitnessFuntion(new FitnessDTLZ(i));
        }


        engine.setMutationSelectionStrategy(MutationSelectionStrategy.SPUTNIK_CASTE);

        engine.setMaxGeneration(5000);
        engine.setPopulationFactory(new TestPopulationFactory().setSize(30,12));

        engine.setAlgorithm(GeneticAlgorithm.EpsilonNSGII);
        engine.addParetoMetric(ParetoMetrics.HYPERVOLUME);
        List<Solution<Dlist>> result = engine.solve();
        for (Solution sol : result) {
            SolutionPrinter.instance$.simplePrint(sol, System.out);
        }


        // engine.setMutationSelectionStrategy(MutationSelectionStrategy.RANDOM);
        // engine.solve();


        ExecutionModel model = engine.getExecutionModel();



        Server.instance$.serveExecutionModel(model);


    }

}
