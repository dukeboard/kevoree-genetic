package org.kevoree.modeling.genetic.genetictest;


import org.genetictest.BinaryString;
import org.genetictest.MyBoolean;
import org.genetictest.impl.DefaultGeneticTestFactory;
import org.kevoree.modeling.api.ModelCloner;
import org.kevoree.modeling.api.compare.ModelCompare;
import org.kevoree.modeling.optimization.api.PopulationFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Assaad
 * Date: 06/11/13
 * Time: 16:27
 */
public class BinaryStringFactoryMaxPopulation implements PopulationFactory<BinaryString> {

    private DefaultGeneticTestFactory binaryfactory = new DefaultGeneticTestFactory();

    private Integer size=4;

    public BinaryStringFactoryMaxPopulation setSize(Integer nSize) {
        size = nSize;
        return this;
    }

    @Override
    public List<BinaryString> createPopulation() {
        ArrayList<BinaryString> populations =  new ArrayList<BinaryString>();
        BinaryString temp;
        MyBoolean bit;

        for (int i = 0; i < size; i++) {
            temp=  binaryfactory.createBinaryString();
            populations.add(temp);
        }
        return populations;
    }

    @Override
    public ModelCloner getCloner() {
        return new org.genetictest.cloner.DefaultModelCloner();
    }

    @Override
    public ModelCompare getModelCompare() {
        return new org.genetictest.compare.DefaultModelCompare();
    }

}
