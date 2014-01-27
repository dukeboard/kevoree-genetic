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
 * Time: 10:48
 * University of Luxembourg - Snt
 * assaad.mouawad@gmail.com
 */
public class AddShape  extends BasicSettings implements MutationOperator<RImage> {

    @Override
    public List<MutationVariable> enumerateVariables(RImage bs) {
        return Arrays.asList((MutationVariable) new QueryVar("target", "shapes[*]"));
        // what to do here??
    }

    @Override
    public void mutate(RImage parent, MutationParameters mutationParameters) {

        if(parent.getShapes().size()>= MAXSHAPES)
            return;

        Shape temp = imagefactory.createShape();
        Color c = imagefactory.createColor();
        c.setA(RAND.nextInt(COLORSPACE));
        c.setR(RAND.nextInt(COLORSPACE));
        c.setG(RAND.nextInt(COLORSPACE));
        c.setB(RAND.nextInt(COLORSPACE));
        temp.setColor(c);
        Point p;

        for(int i=0; i<3; i++)
        {
            p=imagefactory.createPoint();
            p.setX(RAND.nextInt(X));
            p.setY(RAND.nextInt(Y));
            temp.addPoints(p);
        }
        parent.addShapes(temp);




        //Shape x = ((Shape) mutationParameters.getParam("target"));




    }

}
