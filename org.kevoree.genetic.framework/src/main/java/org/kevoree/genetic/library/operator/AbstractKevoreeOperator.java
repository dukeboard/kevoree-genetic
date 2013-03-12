package org.kevoree.genetic.library.operator;

import org.kevoree.ContainerRoot;
import org.kevoree.KevoreeContainer;
import org.kevoree.KevoreeFactory;
import org.kevoree.cloner.ModelCloner;
import org.kevoree.genetic.framework.KevoreeMutationOperator;
import org.kevoree.impl.DefaultKevoreeFactory;

import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 18/02/13
 * Time: 09:34
 */
public abstract class AbstractKevoreeOperator implements KevoreeMutationOperator {

    protected abstract void applyMutation(Object target, ContainerRoot root);

    private ModelCloner cloner = new ModelCloner();
    protected Random rand = new Random();

    @Override
    public ContainerRoot mutate(ContainerRoot parent) {
        String query = getSelectorQuery();
        if (query != null && !query.equals("")) {
            List<Object> targets = parent.selectByQuery(query);
            if (targets.isEmpty()) {
                return parent;
            } else {
                ContainerRoot targetModel = cloner.cloneMutableOnly(parent, false);
                if (selectionStrategy.equals(TargetSelectionStrategy.random)) {
                    List<Object> elems = selectTarget(targetModel, query);
                    Object select = elems.get(rand.nextInt(elems.size()));
                    String equivalentObjectPath = ((KevoreeContainer) select).path();
                    Object equivalentObject = targetModel.findByPath(equivalentObjectPath);
                    applyMutation(equivalentObject, targetModel);
                    return targetModel;
                } else {
                    for (Object o : selectTarget(targetModel, query)) {
                        String equivalentObjectPath = ((KevoreeContainer) o).path();
                        Object equivalentObject = targetModel.findByPath(equivalentObjectPath);
                        applyMutation(equivalentObject, targetModel);
                    }
                    return targetModel;
                }
            }
        }
        return parent;
    }

    protected List<Object> selectTarget(ContainerRoot root, String query) {
        return root.selectByQuery(query);
    }

    private String selectorQuery = null;

    public String getSelectorQuery() {
        return selectorQuery;
    }

    public enum TargetSelectionStrategy {random, all}

    ;

    private TargetSelectionStrategy selectionStrategy = TargetSelectionStrategy.random;

    public TargetSelectionStrategy getSelectionStrategy() {
        return selectionStrategy;
    }

    public void setSelectionStrategy(TargetSelectionStrategy selectionStrategy) {
        this.selectionStrategy = selectionStrategy;
    }

    public AbstractKevoreeOperator setSelectorQuery(String selectorQuery) {
        this.selectorQuery = selectorQuery;
        return this;

    }

    protected KevoreeFactory factory = new DefaultKevoreeFactory();

}
