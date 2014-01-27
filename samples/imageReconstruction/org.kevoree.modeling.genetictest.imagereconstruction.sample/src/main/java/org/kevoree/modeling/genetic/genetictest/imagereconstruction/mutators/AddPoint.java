package org.kevoree.modeling.genetic.genetictest.imagereconstruction.mutators;

import org.imagereconstruction.*;
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
public class AddPoint extends BasicSettings implements MutationOperator<RImage> {
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

        int posMax = parent.getShapes().size();

        if(posMax==0)
            return;

         int pos = RAND.nextInt(posMax);
        Shape s =  parent.getShapes().get(pos);
        if( s.getPoints().size()==MAXPOINTPERSHAPE)
            return;

        Point p=imagefactory.createPoint();
        p.setX(RAND.nextInt(X));
        p.setY(RAND.nextInt(Y));
        s.addPoints(p);

    }

    }
