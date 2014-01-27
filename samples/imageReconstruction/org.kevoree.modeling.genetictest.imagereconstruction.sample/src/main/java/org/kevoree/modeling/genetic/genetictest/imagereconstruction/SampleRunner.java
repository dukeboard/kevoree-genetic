package org.kevoree.modeling.genetic.genetictest.imagereconstruction;

import org.imagereconstruction.RImage;
import org.kevoree.modeling.genetic.genetictest.imagereconstruction.fitnesses.MatchImage;
import org.kevoree.modeling.genetic.genetictest.imagereconstruction.fitnesses.MaximizeTriangle;
import org.kevoree.modeling.genetic.genetictest.imagereconstruction.mutators.*;
import org.kevoree.modeling.optimization.api.solution.Solution;
import org.kevoree.modeling.optimization.engine.genetic.GeneticAlgorithm;
import org.kevoree.modeling.optimization.engine.genetic.GeneticEngine;
import org.kevoree.modeling.optimization.framework.SolutionPrinter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/08/13
 * Time: 16:00
 */
public class SampleRunner {
    private static int counter=0;

    public static void print(Solution sol)
    {
        try {
            // retrieve image
            RImage t = (RImage)  sol.getModel()   ;
            BufferedImage bi = BasicSettings.express(t);
            File outputfile = new File("saved"+counter+".png");
            ImageIO.write(bi, "png", outputfile);
            counter++;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) throws Exception {


        GeneticEngine<RImage> engine = new GeneticEngine<RImage>();

        try
        {
        BasicSettings.setParam(50,15,0.1,"D:\\workspace\\Github\\kevoree-genetic\\samples\\geneticTest\\org.kevoree.modeling.genetictest.imagereconstruction.sample\\src\\main\\resources\\test.jpg");

        engine.addOperator(new AddPoint());
        engine.addOperator(new AddShape());
        engine.addOperator(new ModifyColor());
        engine.addOperator(new RemovePoint());
        engine.addOperator(new RemoveShape());


        engine.setAlgorithm(GeneticAlgorithm.HypervolumeNSGAII);




        engine.addFitnessFuntion(new MatchImage());

        engine.setMaxGeneration(1000)  ;
        engine.setPopulationFactory(new ImageReconstructionPopulation());

        List<Solution<RImage>> result = engine.solve();
        for (Solution sol : result) {
            SolutionPrinter.instance$.print(sol, System.out);
            print(sol);
        }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }




        }



}
