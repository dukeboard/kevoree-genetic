package org.kevoree.modeling.genetic.zdt;

import org.jetbrains.annotations.NotNull;
import org.kevoree.modeling.api.ModelCloner;
import org.kevoree.modeling.api.compare.ModelCompare;
import org.kevoree.modeling.optimization.api.PopulationFactory;
import org.zdt.Dlist;
import org.zdt.DoubleContainer;
import org.zdt.compare.DefaultModelCompare;
import org.zdt.impl.DefaultZDTFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by assaad.moawad on 4/9/2014.
 */


public class ZDTPopulationFactory implements PopulationFactory<Dlist> {

    private DefaultZDTFactory factory = new DefaultZDTFactory();

    private int size = 5;
    private int width = 30;

    public ZDTPopulationFactory setSize(int xwidth, int populationSize) {
        size = populationSize;
        width=xwidth;
        return this;
    }

    @NotNull
    @Override
    public List<Dlist> createPopulation() {


        ArrayList<Dlist> populations = new ArrayList<Dlist>();
        for (int i = 0; i < size; i++) {
            Dlist pop = factory.createDlist();
            for(int j=0; j<width;j++){
                DoubleContainer dc= factory.createDoubleContainer();
                dc.setDoubleValue(0.0);
                pop.addValues(dc);
            }
            populations.add(pop);
        }
        return populations;
    }

    @NotNull
    @Override
    public ModelCloner getCloner() {
        return new org.zdt.cloner.DefaultModelCloner();
    }

    @NotNull
    @Override
    public ModelCompare getModelCompare() {
        return new DefaultModelCompare();
    }
}
