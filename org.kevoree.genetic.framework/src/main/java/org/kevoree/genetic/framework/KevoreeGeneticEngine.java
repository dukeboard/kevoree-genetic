package org.kevoree.genetic.framework;

import org.kevoree.ContainerRoot;
import org.kevoree.genetic.framework.internal.KevoreeInitialization;
import org.kevoree.genetic.framework.internal.KevoreeProblem;
import org.kevoree.genetic.framework.internal.KevoreeVariable;
import org.kevoree.genetic.framework.internal.KevoreeVariationAdaptor;
import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.core.*;
import org.moeaframework.core.operator.TournamentSelection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 17:41
 */
public class KevoreeGeneticEngine {

    private List<KevoreeVariationAdaptor> operators = new ArrayList<KevoreeVariationAdaptor>();

    private List<KevoreeFitnessFunction> fitnesses = new ArrayList<KevoreeFitnessFunction>();

    private KevoreePopulationFactory populationFactory = null;

    public KevoreeGeneticEngine addOperator(KevoreeOperator operator) {
        operators.add(new KevoreeVariationAdaptor(operator));
        return this;
    }

    public KevoreeGeneticEngine addFitnessFuntion(KevoreeFitnessFunction function) {
        fitnesses.add(function);
        return this;
    }

    public KevoreeGeneticEngine setPopulationFactory(KevoreePopulationFactory _populationFactory) {
        populationFactory = _populationFactory;
        return this;
    }

    public List<ContainerRoot> solve() {

        Problem problem = new KevoreeProblem(fitnesses);
        Algorithm algorithm = new NSGAII(problem, new NondominatedSortingPopulation(), new EpsilonBoxDominanceArchive(0.5), new TournamentSelection(), operators.get(0) /* TODO REMOVE WHY ONLY ONE ? */, new KevoreeInitialization(populationFactory, problem));
        int maxEvaluations = 10000;
        try {
            while (!algorithm.isTerminated() &&
                    (algorithm.getNumberOfEvaluations() < maxEvaluations)) {
                algorithm.step();
            }
            NondominatedPopulation result = new NondominatedPopulation();
            result.addAll(algorithm.getResult());
        } finally {
            if (algorithm != null) {
                algorithm.terminate();
            }
            if (problem != null) {
                problem.close();
            }
        }

        ArrayList<ContainerRoot> results = new ArrayList<ContainerRoot>();
        Population pop = algorithm.getResult();
        for (Solution s : pop) {
            KevoreeVariable var = (KevoreeVariable) s.getVariable(0);
            results.add(var.getModel());
        }
        return results;
    }


}
