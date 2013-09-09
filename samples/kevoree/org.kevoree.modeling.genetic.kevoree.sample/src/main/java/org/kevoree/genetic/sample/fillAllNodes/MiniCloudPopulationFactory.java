package org.kevoree.modeling.optimization.genetic.sample.fillAllNodes;

import org.kevoree.*;
import org.kevoree.cloner.DefaultModelCloner;
import org.kevoree.impl.DefaultKevoreeFactory;
import org.kevoree.loader.XMIModelLoader;
import org.kevoree.modeling.api.KMFContainer;
import org.kevoree.modeling.api.ModelCloner;
import org.kevoree.modeling.api.ModelLoader;
import org.kevoree.modeling.api.compare.ModelCompare;
import org.kevoree.modeling.optimization.api.PopulationFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 17:14
 */
public class MiniCloudPopulationFactory implements PopulationFactory<ContainerRoot> {

    private Integer populationSize = 10;

    public Integer getPopulationSize() {
        return populationSize;
    }

    public MiniCloudPopulationFactory setPopulationSize(Integer populationSize) {
        this.populationSize = populationSize;
        return this;
    }

    @Override
    public List<ContainerRoot> createPopulation() {
        ArrayList<ContainerRoot> population = new ArrayList<ContainerRoot>();
        KevoreeFactory factory = new DefaultKevoreeFactory();
        ModelLoader loader = new XMIModelLoader();
        ModelCloner cloner = new DefaultModelCloner();
        //Create init Model
        ContainerRoot rootModel = (ContainerRoot) loader.loadModelFromStream(this.getClass().getClassLoader().getResourceAsStream("base.kev")).get(0);

        /* Fix Immutable */
        for (TypeDefinition td : rootModel.getTypeDefinitions()) {
            td.setRecursiveReadOnly();
        }
        for (DeployUnit du : rootModel.getDeployUnits()) {
            du.setRecursiveReadOnly();
        }
        for (Repository r : rootModel.getRepositories()) {
            r.setRecursiveReadOnly();
        }

        for (int i = 0; i < 5; i++) {
            ContainerNode n = factory.createContainerNode();
            n.setName("node_" + i);
            n.setTypeDefinition(rootModel.findTypeDefinitionsByID("JavaSENode"));
            rootModel.addNodes(n);
        }
        //Add to population
        for (int i = 0; i < populationSize; i++) {
            population.add(cloner.cloneMutableOnly(rootModel, false));
        }
        return population;
    }

    @Override
    public org.kevoree.modeling.api.ModelCloner getCloner() {
        return new org.kevoree.cloner.DefaultModelCloner();
    }

    @Override
    public ModelCompare getModelCompare() {
        return new org.kevoree.compare.DefaultModelCompare();
    }

}


