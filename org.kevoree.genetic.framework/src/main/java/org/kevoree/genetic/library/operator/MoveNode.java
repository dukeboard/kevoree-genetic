package org.kevoree.genetic.library.operator;

import org.kevoree.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/03/13
 * Time: 13:48
 */
public class MoveNode extends AbstractKevoreeOperator {

    private String targetNodesQuery = null;

    public String getTargetNodesQuery() {
        return targetNodesQuery;
    }

    public MoveNode setTargetNodesQuery(String targetNodesQuery) {
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
