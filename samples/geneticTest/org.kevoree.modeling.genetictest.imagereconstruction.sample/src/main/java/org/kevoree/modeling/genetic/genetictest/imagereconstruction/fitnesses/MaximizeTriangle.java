package org.kevoree.modeling.genetic.genetictest.imagereconstruction.fitnesses;

import org.imagereconstruction.RImage;
import org.imagereconstruction.Shape;
import org.imagereconstruction.Triangle;
import org.kevoree.modeling.genetic.genetictest.imagereconstruction.BasicSettings;
import org.kevoree.modeling.optimization.api.GenerationContext;
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction;
import org.kevoree.modeling.optimization.api.fitness.FitnessOrientation;

/**
 * User: assaad.moawad
 * Date: 12/11/13
 * Time: 11:39
 * University of Luxembourg - Snt
 * assaad.mouawad@gmail.com
 */
public class MaximizeTriangle extends BasicSettings implements FitnessFunction<RImage> {
    @Override
    public double evaluate(RImage model, GenerationContext<RImage> cloudGenerationContext) {

        int counter =0;
        for(Shape x: model.getShapes())
        {
            if(x instanceof Triangle)
                counter++;
        }
        return  counter;

    }




    @Override
    public double min() {

        return 0.0;
    }
    public double max() {

        return MAXSHAPES;
    }
    public FitnessOrientation orientation() {

        return FitnessOrientation.MAXIMIZE;
    }
}
