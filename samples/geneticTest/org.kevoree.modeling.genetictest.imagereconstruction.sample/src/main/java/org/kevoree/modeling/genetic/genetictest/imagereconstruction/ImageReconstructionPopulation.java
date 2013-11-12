package org.kevoree.modeling.genetic.genetictest.imagereconstruction;

import org.imagereconstruction.RImage;
import org.imagereconstruction.cloner.DefaultModelCloner;
import org.imagereconstruction.compare.DefaultModelCompare;
import org.imagereconstruction.impl.DefaultImagereconstructionFactory;
import org.kevoree.modeling.api.ModelCloner;
import org.kevoree.modeling.api.compare.ModelCompare;
import org.kevoree.modeling.optimization.api.PopulationFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Assaad
 * Date: 06/11/13
 * Time: 16:27
 */
public class ImageReconstructionPopulation implements PopulationFactory<RImage> {

   private DefaultImagereconstructionFactory imagefactory = new DefaultImagereconstructionFactory();

    private Integer size = 100;

    public ImageReconstructionPopulation setSize(Integer nSize) {
        size = nSize;
        return this;
    }

    @Override
    public List<RImage> createPopulation() {
        ArrayList<RImage> populations =  new ArrayList<RImage>();
        RImage temp;

        for (int i = 0; i < size; i++) {
            temp=  imagefactory.createRImage();
            populations.add(temp);
        }
        return populations;
    }

    @Override
    public ModelCloner getCloner() {
        return new DefaultModelCloner();
    }

    @Override
    public ModelCompare getModelCompare() {
        return new DefaultModelCompare();
    }

}
