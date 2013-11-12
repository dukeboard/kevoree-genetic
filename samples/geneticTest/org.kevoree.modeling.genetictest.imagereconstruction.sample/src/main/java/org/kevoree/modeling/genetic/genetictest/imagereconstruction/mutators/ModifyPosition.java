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
public class ModifyPosition extends BasicSettings implements MutationOperator<RImage> {
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

        if(x instanceof Triangle)
        {
            Triangle t= (Triangle)x;
            if(RAND.nextDouble()<PROBA)
            {
                t.setX0(RAND.nextInt(X));
                t.setY0(RAND.nextInt(Y));
            }
            if(RAND.nextDouble()<PROBA)
            {
                t.setX1(RAND.nextInt(X));
                t.setY1(RAND.nextInt(Y));
            }
            if(RAND.nextDouble()<PROBA)
            {
                t.setX2(RAND.nextInt(X));
                t.setY2(RAND.nextInt(Y));
            }

        }
        else if (x instanceof Rectangle)
        {
            Rectangle r = (Rectangle)x;

            if(RAND.nextDouble()<PROBA)
            {
            r.setX0(RAND.nextInt(X));
            r.setY0(RAND.nextInt(Y));
            }
            if(RAND.nextDouble()<PROBA)
            {
            r.setX1(RAND.nextInt(X));
            r.setY1(RAND.nextInt(Y));
            }

        }
        else if (x instanceof Circle)
        {
            Circle cir = (Circle) x;

            if(RAND.nextDouble()<PROBA)
            {
            cir.setX0(RAND.nextInt(X));
            cir.setY0(RAND.nextInt(Y));
            }
            if(RAND.nextDouble()<PROBA)
            {
            cir.setRadius(RAND.nextInt(Math.min(Math.min(cir.getX0(),X-cir.getX0()),Math.min(cir.getY0(),Y-cir.getY0()))));
            }

        }




    }

    }
