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

       int x=RAND.nextInt(3);
        switch (x)
        {
            case 0:
            {
                Triangle t= imagefactory.createTriangle();
                t.setX0(RAND.nextInt(X));
                t.setY0(RAND.nextInt(Y));
                t.setX1(RAND.nextInt(X));
                t.setY1(RAND.nextInt(Y));
                t.setX2(RAND.nextInt(X));
                t.setY2(RAND.nextInt(Y));

                Color c = imagefactory.createColor();
                c.setA(RAND.nextInt(COLORSPACE));
                c.setR(RAND.nextInt(COLORSPACE));
                c.setG(RAND.nextInt(COLORSPACE));
                c.setB(RAND.nextInt(COLORSPACE));
                t.setColor(c);
                parent.addShapes(t);
                return;

            }
            case 1:
            {
                Rectangle r = imagefactory.createRectangle();
                r.setX0(RAND.nextInt(X));
                r.setY0(RAND.nextInt(Y));
                r.setX1(RAND.nextInt(X));
                r.setY1(RAND.nextInt(Y));
                Color c = imagefactory.createColor();
                c.setA(RAND.nextInt(COLORSPACE));
                c.setR(RAND.nextInt(COLORSPACE));
                c.setG(RAND.nextInt(COLORSPACE));
                c.setB(RAND.nextInt(COLORSPACE));
                r.setColor(c);
                parent.addShapes(r);
                return;
            }
            case 2:
            {
                Circle cir= imagefactory.createCircle();
                cir.setX0(RAND.nextInt(X-1));
                cir.setY0(RAND.nextInt(Y-1));
                int rad = Math.min(Math.min(cir.getX0(),X-cir.getX0()),Math.min(cir.getY0(),Y-cir.getY0()));
                if(rad==0)
                    return;

                cir.setRadius(RAND.nextInt(rad));
                Color c = imagefactory.createColor();
                c.setA(RAND.nextInt(COLORSPACE));
                c.setR(RAND.nextInt(COLORSPACE));
                c.setG(RAND.nextInt(COLORSPACE));
                c.setB(RAND.nextInt(COLORSPACE));
                cir.setColor(c);
                parent.addShapes(cir);
                return;
            }
        }


        //Shape x = ((Shape) mutationParameters.getParam("target"));




    }

}
