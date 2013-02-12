package org.kevoree.genetic.sample.fillAllNodes;

import org.kevoree.ContainerNode;
import org.kevoree.ContainerRoot;
import org.kevoree.KevoreeFactory;
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
    @Override
    public List<ContainerRoot> createPopulation() {

        ArrayList<ContainerRoot> population = new ArrayList<ContainerRoot>();
        KevoreeFactory factory = new DefaultKevoreeFactory();
        ModelLoader loader = new ModelLoader();
        ModelCloner cloner = new ModelCloner();

        //CReate init Model
        ContainerRoot rootModel = loader.loadModelFromStream(this.getClass().getClassLoader().getResourceAsStream("base.kev")).get(0);
        for (int i = 0; i < 5; i++) {
            ContainerNode n = factory.createContainerNode();
            n.setName("node_" + i);
            n.setTypeDefinition(rootModel.findTypeDefinitionsByID("JavaSENode"));
            rootModel.addNodes(n);
        }
        //Add to population
        for (int i = 0; i < 10; i++) {
            population.add(cloner.clone(rootModel));
        }
        return population;

    }
}
