package org.kevoree.modeling.genetic.genetictest.imagereconstruction;

import org.imagereconstruction.RImage;
import org.kevoree.modeling.genetic.genetictest.imagereconstruction.fitnesses.MaximizeTriangle;
import org.kevoree.modeling.genetic.genetictest.imagereconstruction.mutators.AddShape;
import org.kevoree.modeling.genetic.genetictest.imagereconstruction.mutators.ModifyColor;
import org.kevoree.modeling.genetic.genetictest.imagereconstruction.mutators.ModifyPosition;
import org.kevoree.modeling.genetic.genetictest.imagereconstruction.mutators.RemoveShape;
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


        GeneticEngine<RImage> engine = new GeneticEngine<RImage>();


        engine.addOperator(new AddShape());
        engine.addOperator(new ModifyColor());
        engine.addOperator(new ModifyPosition());
        engine.addOperator(new RemoveShape());


        engine.setAlgorithm(GeneticAlgorithm.HypervolumeNSGAII);




        engine.addFitnessFuntion(new MaximizeTriangle());

        engine.setMaxGeneration(1000)  ;
        engine.setPopulationFactory(new ImageReconstructionPopulation());

        List<Solution<RImage>> result = engine.solve();
        for (Solution sol : result) {
            SolutionPrinter.instance$.print(sol, System.out);
        }




        }



}
