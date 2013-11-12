package org.kevoree.modeling.genetic.genetictest.imagereconstruction;

import org.imagereconstruction.impl.DefaultImagereconstructionFactory;

import java.util.Random;

/**
 * User: assaad.moawad
 * Date: 12/11/13
 * Time: 11:11
 * University of Luxembourg - Snt
 * assaad.mouawad@gmail.com
 */
public class BasicSettings {
    protected static int X=200;
    protected static int Y=300;
    protected static int MAXSHAPES =50;
    protected static int COLORSPACE=256;
    protected static Random RAND = new Random();
    protected static double PROBA = 0.1;


    protected DefaultImagereconstructionFactory imagefactory = new DefaultImagereconstructionFactory();

    public static void setParam(int x, int y, int maxShapes, double mutationProba)
    {
        X=x;
        Y=y;
        MAXSHAPES =maxShapes;
        PROBA=mutationProba;
    }

}
