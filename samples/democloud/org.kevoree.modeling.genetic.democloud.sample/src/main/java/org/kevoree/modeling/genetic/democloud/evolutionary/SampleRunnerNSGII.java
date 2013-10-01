package org.kevoree.modeling.genetic.democloud.evolutionary;

import org.cloud.Cloud;
import org.kevoree.modeling.genetic.democloud.CloudPopulationFactory;
import org.kevoree.modeling.genetic.democloud.fitnesses.CloudConsumptionFitness;
import org.kevoree.modeling.genetic.democloud.fitnesses.CloudRedondencyFitness;
import org.kevoree.modeling.genetic.democloud.mutators.AddNodeMutator;
import org.kevoree.modeling.genetic.democloud.mutators.RemoveNodeMutator;
import org.kevoree.modeling.optimization.api.Solution;
import org.kevoree.modeling.optimization.engine.genetic.GeneticAlgorithm;
import org.kevoree.modeling.optimization.engine.genetic.GeneticEngine;
import org.kevoree.modeling.optimization.framework.SolutionPrinter;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: donia.elkateb
 * Date: 9/30/13
 * Time: 4:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class SampleRunnerNSGII {

    public static void main(String[] args) throws Exception {

        GeneticEngine<Cloud> engine = new GeneticEngine<Cloud>();
        engine.addOperator(new AddNodeMutator());
        engine.addOperator(new RemoveNodeMutator());
        engine.addFitnessFuntion(new CloudConsumptionFitness());
        engine.addFitnessFuntion(new CloudRedondencyFitness());
        //engine.addFitnessFuntion(new CloudAdaptationCostFitness());

        engine.setMaxGeneration(100);
        engine.setPopulationFactory(new CloudPopulationFactory().setSize(10));
        engine.setAlgorithm(GeneticAlgorithm.NSGII);

        List<Solution> result = engine.solve();
        SolutionPrinter printer = new SolutionPrinter();
        for (Solution sol : result) {
        printer.print(sol, System.out);
        }

    }
}
