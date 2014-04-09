package org.kevoree.modeling.genetic.zdt.evolutionary;

import org.kevoree.modeling.genetic.zdt.ZDTPopulationFactory;
import org.kevoree.modeling.genetic.zdt.fitnesses.FitnessF;
import org.kevoree.modeling.genetic.zdt.fitnesses.FitnessF0;
import org.kevoree.modeling.genetic.zdt.fitnesses.FitnessF1;
import org.kevoree.modeling.genetic.zdt.mutators.RandomChange;
import org.kevoree.modeling.optimization.api.metric.ParetoMetrics;
import org.kevoree.modeling.optimization.api.mutation.MutationSelectionStrategy;
import org.kevoree.modeling.optimization.api.solution.Solution;
import org.kevoree.modeling.optimization.engine.genetic.GeneticAlgorithm;
import org.kevoree.modeling.optimization.engine.genetic.GeneticEngine;
import org.kevoree.modeling.optimization.executionmodel.ExecutionModel;
import org.kevoree.modeling.optimization.framework.SolutionPrinter;
import org.kevoree.modeling.optimization.web.Server;
import org.zdt.Dlist;

import java.util.List;

/**
 * Created by assaad.moawad on 4/9/2014.
 */
public class SampleRunnerNSGAII {
    public static void main(String[] args) throws Exception {

        GeneticEngine<Dlist> engine = new GeneticEngine<Dlist>();
        // engine.desactivateOriginAware();

        engine.addOperator(new RandomChange());

        //Set both fitness to the same zdt;
        FitnessF.zdt=1;


        engine.addFitnessFuntion(new FitnessF0());
        engine.addFitnessFuntion(new FitnessF1());


        engine.setMutationSelectionStrategy(MutationSelectionStrategy.SPUTNIK_CASTE);

        engine.setMaxGeneration(5000);
        engine.setPopulationFactory(new ZDTPopulationFactory().setSize(30,12));

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
