package org.kevoree.modeling.genetic.genetictest.imagereconstruction.mutators;

import org.imagereconstruction.Color;
import org.imagereconstruction.RImage;
import org.imagereconstruction.Shape;
import org.kevoree.modeling.genetic.genetictest.imagereconstruction.BasicSettings;
import org.kevoree.modeling.optimization.api.mutation.MutationOperator;
import org.kevoree.modeling.optimization.api.mutation.MutationParameters;
import org.kevoree.modeling.optimization.api.mutation.MutationVariable;
import org.kevoree.modeling.optimization.api.mutation.QueryVar;

import java.util.Arrays;
import java.util.List;

/**
 * User: assaad.moawad
 * Date: 12/11/13
 * Time: 11:09
 * University of Luxembourg - Snt
 * assaad.mouawad@gmail.com
 */
public class ModifyColor extends BasicSettings implements MutationOperator<RImage> {
    @Override
    public List<MutationVariable> enumerateVariables(RImage bs) {
        return Arrays.asList((MutationVariable) new QueryVar("target", "shapes[*]"));
        // what to do here??
    }

    @Override
    public void mutate(RImage parent, MutationParameters mutationParameters) {
        Shape x = ((Shape) mutationParameters.getParam("target"));
        if(x==null)
            return;

        Color c = x.getColor();

        if(RAND.nextDouble()<PROBA)
        {
            c.setA(RAND.nextInt(COLORSPACE));
        }
        if(RAND.nextDouble()<PROBA)
        {
            c.setR(RAND.nextInt(COLORSPACE));
        }
        if(RAND.nextDouble()<PROBA)
        {
            c.setG(RAND.nextInt(COLORSPACE));
        }
        if(RAND.nextDouble()<PROBA)
        {
            c.setB(RAND.nextInt(COLORSPACE));
        }

    }

    }
