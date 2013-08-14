package org.kevoree.modeling.optimization.genetic.library.operator;

import org.kevoree.*;
import org.kevoree.impl.DefaultKevoreeFactory;
import org.kevoree.modeling.api.KMFContainer;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 18/02/13
 * Time: 09:39
 */
public class AddComponent extends AbstractKevoreeOperator {

    private String componentTypeName = null;

    public String getComponentTypeName() {
        return componentTypeName;
    }

    public AddComponent setComponentTypeName(String componentTypeName) {
        this.componentTypeName = componentTypeName;
        return this;
    }

    @Override
    protected void applyMutation(Object target, ContainerRoot root) {
        if (componentTypeName != null && target instanceof ContainerNode) {
            TypeDefinition td = root.findTypeDefinitionsByID(componentTypeName);
            if (td != null) {
                ComponentInstance inst = factory.createComponentInstance();
                inst.setName(generateName());
                inst.setTypeDefinition(td);
                ((ContainerNode) target).addComponents(inst);
            }
        }
    }

    protected String generateName() {
        Random r = new Random();
        return componentTypeName + "_" + Math.abs(r.nextInt());
    }

}
