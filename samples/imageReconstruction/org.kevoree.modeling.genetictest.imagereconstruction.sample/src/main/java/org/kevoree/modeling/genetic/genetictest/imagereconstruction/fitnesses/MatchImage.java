package org.kevoree.modeling.genetic.genetictest.imagereconstruction.fitnesses;

import org.imagereconstruction.RImage;
import org.imagereconstruction.Shape;
import org.kevoree.modeling.genetic.genetictest.imagereconstruction.BasicSettings;
import org.kevoree.modeling.optimization.api.GenerationContext;
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction;
import org.kevoree.modeling.optimization.api.fitness.FitnessOrientation;

import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

/**
 * User: assaad.moawad
 * Date: 20/11/13
 * Time: 11:20
 * University of Luxembourg - Snt
 * assaad.mouawad@gmail.com
 */
public class MatchImage  extends BasicSettings implements FitnessFunction<RImage> {
    @Override
    public double evaluate(RImage model, GenerationContext<RImage> cloudGenerationContext) {

        BufferedImage curr = express(model);
        return compare(curr);

    }

    private double compare(BufferedImage generated) {

        final int[] generatedPixels = new int[generated.getWidth() *
                generated.getHeight()];
        PixelGrabber pg = new PixelGrabber(generated, 0, 0, generated.getWidth(),
                generated.getHeight(), generatedPixels,
                0, generated.getWidth());
        try {
            pg.grabPixels();
        } catch (InterruptedException ex) {
           System.out.println(ex.getMessage());
        }
        double sum = 0;
        for (int i = 0; i < generatedPixels.length && i < targetPixels.length; i++) {
            int c1 = targetPixels[i];
            int c2 = generatedPixels[i];
            int r = ( (c1 >> 16) & 0xff) - ( (c2 >> 16) & 0xff);
            int g = ( (c1 >> 8) & 0xff) - ( (c2 >> 8) & 0xff);
            int b = (c1 & 0xff) - (c2 & 0xff);
            sum += r * r + g * g + b * b;
        }
        return Math.sqrt(sum);
    }


    @Override
    public double min() {

        return 0.0;
    }
    public double max() {

        return X*Y*Math.sqrt(196608);
    }
    public FitnessOrientation orientation() {

        return FitnessOrientation.MINIMIZE;
    }
}
