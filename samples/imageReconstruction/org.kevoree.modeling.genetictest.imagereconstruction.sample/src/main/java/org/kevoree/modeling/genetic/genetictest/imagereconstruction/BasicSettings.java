package org.kevoree.modeling.genetic.genetictest.imagereconstruction;

import org.imagereconstruction.RImage;
import org.imagereconstruction.impl.DefaultImagereconstructionFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
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
    protected static int MAXPOINTPERSHAPE = 15;
    protected static int COLORSPACE=256;
    protected static Random RAND = new Random();
    protected static double PROBA = 0.1;
    protected static  int[] targetPixels=null;


    protected DefaultImagereconstructionFactory imagefactory = new DefaultImagereconstructionFactory();

    public static void setParam(int maxShapes, int maxPointsPerShape, double mutationProba, String file) throws IOException, InterruptedException {
        BufferedImage img = null;
            img = ImageIO.read(new File(file));
        X=img.getWidth();
        Y=img.getHeight();
        targetPixels = new int[X*Y];
        PixelGrabber pg = new PixelGrabber(img, 0, 0, X,
               Y, targetPixels, 0,
                X);
        pg.grabPixels();
            System.out.println("Image loaded "+X+" , "+ Y);


        MAXPOINTPERSHAPE = maxPointsPerShape;
        MAXSHAPES =maxShapes;
        PROBA=mutationProba;
    }


    public static BufferedImage express(RImage individual) {

        BufferedImage m_generated;
        Graphics m_graphics;

        m_generated = new BufferedImage(X,Y,BufferedImage.TYPE_INT_ARGB);
        m_graphics = m_generated.getGraphics();

        m_graphics.setColor(Color.white);
        m_graphics.fillRect(0, 0, m_generated.getWidth(), m_generated.getHeight());
        for (int i = 0; i < individual.getShapes().size(); i++) {
            org.imagereconstruction.Shape cur = individual.getShapes().get(i);
            org.imagereconstruction.Color c = cur.getColor();
            m_graphics.setColor(new Color(c.getR(),c.getG(),c.getB(),c.getA()));
            m_graphics.fillPolygon(expressPolygon(cur));
        }
        return m_generated;
    }

    public static Polygon expressPolygon(org.imagereconstruction.Shape current) {
        int size=       current.getPoints().size();
        int[] xpoints = new int[size];
        int[] ypoints = new int[size];

        for (int j = 0; j <size; j++) {
            xpoints[j] = current.getPoints().get(j).getX();
            ypoints[j] = current.getPoints().get(j).getY();
        }
        return new Polygon(xpoints, ypoints, size);
    }


}
