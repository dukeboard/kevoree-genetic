package org.kevoree.genetic.sample.fillAllNodes;

import org.kevoree.*;
import org.kevoree.cloner.ModelCloner;
import org.kevoree.genetic.framework.KevoreePopulationFactory;
import org.kevoree.impl.DefaultKevoreeFactory;
import org.kevoree.loader.ModelLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 17:14
 */
public class MiniCloudPopulationFactory implements KevoreePopulationFactory {

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
        ModelLoader loader = new ModelLoader();
        ModelCloner cloner = new ModelCloner();
        //CReate init Model
        ContainerRoot rootModel = loader.loadModelFromStream(this.getClass().getClassLoader().getResourceAsStream("base.kev")).get(0);

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
}
