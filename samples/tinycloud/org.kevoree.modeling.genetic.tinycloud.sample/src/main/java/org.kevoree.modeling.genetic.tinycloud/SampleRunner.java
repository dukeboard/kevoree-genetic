package org.kevoree.modeling.genetic.tinycloud;

import org.kevoree.genetic.framework.GeneticEngine;
import org.kevoree.genetic.framework.KevoreeSolution;
import org.kevoree.modeling.genetic.api.ResolutionEngine;
import org.kevoree.modeling.genetic.tinycloud.fitnesses.CloudConsumptionFitness;
import org.kevoree.modeling.genetic.tinycloud.fitnesses.CloudRedondencyFitness;
import org.kevoree.modeling.genetic.tinycloud.mutators.AddNodeMutator;
import org.kevoree.modeling.genetic.tinycloud.mutators.RemoveNodeMutator;

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
