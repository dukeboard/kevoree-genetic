package org.kevoree.modeling.genetic.tinycloud;

import org.cloud.Cloud;
import org.kevoree.genetic.framework.KevoreeGeneticEngine;
import org.kevoree.genetic.framework.KevoreePopulationFactory;
import org.kevoree.genetic.framework.KevoreeSolution;
import org.kevoree.modeling.genetic.tinycloud.fitnesses.CloudConsumptionFitness;
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

        KevoreeGeneticEngine engine = new KevoreeGeneticEngine();
        engine.addOperator(new AddNodeMutator());
        engine.addOperator(new RemoveNodeMutator());
        engine.addFitnessFuntion(new CloudConsumptionFitness());
        engine.setMaxGeneration(100);
        engine.setPopulationFactory(new DefaultCloudPopulationFactory());

        List<KevoreeSolution> result = engine.solve();
        for(KevoreeSolution sol : result){
            sol.print(System.out);
        }

    }

}
