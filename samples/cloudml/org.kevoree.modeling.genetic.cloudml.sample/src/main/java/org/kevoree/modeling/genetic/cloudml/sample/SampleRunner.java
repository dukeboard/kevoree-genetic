package org.kevoree.modeling.genetic.cloudml.sample;

import org.kevoree.modeling.optimization.genetic.framework.GeneticEngine;
import org.kevoree.modeling.optimization.genetic.framework.KevoreeSolution;
import org.kevoree.modeling.genetic.api.ResolutionEngine;
import org.kevoree.modeling.genetic.cloudml.sample.fitness.CloudConsumptionFitness;
import org.kevoree.modeling.genetic.cloudml.sample.fitness.CloudRedondencyFitness;
import org.kevoree.modeling.genetic.cloudml.sample.mutator.AddNodeMutator;
import org.kevoree.modeling.genetic.cloudml.sample.mutator.RemoveNodeMutator;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/08/13
 * Time: 16:00
 */
public class SampleRunner {


    public static void main(String[] args) throws Exception {

        ResolutionEngine engine = new GeneticEngine();
        engine.addOperator(new AddNodeMutator());
        engine.addOperator(new RemoveNodeMutator());
        engine.addFitnessFuntion(new CloudConsumptionFitness());
        engine.addFitnessFuntion(new CloudRedondencyFitness());

        engine.setMaxGeneration(100);
        engine.setPopulationFactory(new DefaultCloudPopulationFactory());

        List<KevoreeSolution> result = engine.solve();
        for (KevoreeSolution sol : result) {
            sol.print(System.out);
        }

    }

}
