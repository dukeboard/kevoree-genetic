package org.kevoree.genetic.library.operator;

import org.kevoree.ComponentInstance;
import org.kevoree.ContainerNode;
import org.kevoree.ContainerRoot;
import org.kevoree.modeling.api.KMFContainer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/03/13
 * Time: 13:48
 */
public class MoveComponentOperator extends AbstractKevoreeOperator {

    private String targetNodesQuery = null;

    public String getTargetNodesQuery() {
        return targetNodesQuery;
    }

    public MoveComponentOperator setTargetNodesQuery(String targetNodesQuery) {
        this.targetNodesQuery = targetNodesQuery;
        return this;
    }

    @Override
    protected void applyMutation(Object target, KMFContainer root_) {

        ContainerRoot root = (ContainerRoot) root_;

        if (target instanceof ComponentInstance) {
            ComponentInstance targetComponent = (ComponentInstance) target;
            List<Object> targets = root.selectByQuery(targetNodesQuery);
            ContainerNode targetParentNode = (ContainerNode) targets.get(rand.nextInt(targets.size()));
            targetParentNode.addComponents(targetComponent);
        }
    }
}
