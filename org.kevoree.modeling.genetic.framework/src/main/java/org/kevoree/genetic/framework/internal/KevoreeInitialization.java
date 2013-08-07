package org.kevoree.genetic.framework.internal;

import org.kevoree.genetic.framework.KevoreePopulationFactory;
import org.kevoree.modeling.api.KMFContainer;
import org.moeaframework.core.Initialization;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 17:21
 */
public class KevoreeInitialization implements Initialization {

    private KevoreePopulationFactory factory = null;
    private Problem problem = null;

    public KevoreeInitialization(KevoreePopulationFactory _factory, Problem _problem) {
        factory = _factory;
        problem = _problem;
    }

    @Override
    public Solution[] initialize() {
        List<KMFContainer> models = factory.createPopulation();
        Solution[] results = new Solution[models.size()];
        for (int i = 0; i < models.size(); i++) {
            Solution s = problem.newSolution();
            s.setVariable(0, new KevoreeVariable(models.get(i), factory.getCloner()).setOrigin(models.get(i)));
            results[i] = s;
        }
        return results;
    }
}
