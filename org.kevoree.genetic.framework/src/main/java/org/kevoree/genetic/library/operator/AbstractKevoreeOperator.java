package org.kevoree.genetic.library.operator;

import org.kevoree.ContainerRoot;
import org.kevoree.KevoreeContainer;
import org.kevoree.KevoreeFactory;
import org.kevoree.cloner.ModelCloner;
import org.kevoree.genetic.framework.KevoreeMutationOperator;
import org.kevoree.impl.DefaultKevoreeFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 18/02/13
 * Time: 09:34
 */
public abstract class AbstractKevoreeOperator implements KevoreeMutationOperator {

    protected abstract void applyMutation(Object target, ContainerRoot root);

    private ModelCloner cloner = new ModelCloner();

    @Override
    public ContainerRoot mutate(ContainerRoot parent) {
        String query = getSelectorQuery();
        if (query != null && !query.equals("")) {

            List<Object> targets = parent.selectByQuery(query);
            if (targets.isEmpty()) {
                return parent;
            } else {
                ContainerRoot targetModel = cloner.clone(parent, false);
                for (Object o : selectTarget(targetModel, query)) {
                    String equivalentObjectPath = ((KevoreeContainer) o).path();
                    Object equivalentObject = targetModel.findByPath(equivalentObjectPath);
                    applyMutation(equivalentObject, targetModel);
                    if (isEnd) {
                        return targetModel;
                    }
                }
            }


        }
        return parent;
    }

    protected List<Object> selectTarget(ContainerRoot root, String query) {
        return root.selectByQuery(query);
    }

    private Boolean isEnd = false;

    protected void endMutation() {
        isEnd = true;
    }

    private String selectorQuery = null;

    public String getSelectorQuery() {
        return selectorQuery;
    }

    public AbstractKevoreeOperator setSelectorQuery(String selectorQuery) {
        this.selectorQuery = selectorQuery;
        return this;

    }

    protected KevoreeFactory factory = new DefaultKevoreeFactory();

}
