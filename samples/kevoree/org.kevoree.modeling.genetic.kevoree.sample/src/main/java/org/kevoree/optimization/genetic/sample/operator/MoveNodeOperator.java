package org.kevoree.optimization.genetic.sample.operator;

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
public class MoveNodeOperator extends AbstractKevoreeOperator {

    private String targetNodesQuery = null;

    public String getTargetNodesQuery() {
        return targetNodesQuery;
    }

    public MoveNodeOperator setTargetNodesQuery(String targetNodesQuery) {
        this.targetNodesQuery = targetNodesQuery;
        return this;
    }

    @Override
    protected void applyMutation(Object target, ContainerRoot root) {
        if (target instanceof ContainerNode) {
            ContainerNode targetChildNode = (ContainerNode) target;
            List<Object> targets = root.selectByQuery(targetNodesQuery);
            ContainerNode targetParentNode = (ContainerNode) targets.get(rand.nextInt(targets.size()));
            targetChildNode.getHost().removeHosts(targetChildNode);
            targetParentNode.addHosts(targetChildNode);
        }
    }
}
