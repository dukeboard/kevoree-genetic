package org.kevoree.genetic.framework;

import org.kevoree.ContainerRoot;
import org.kevoree.genetic.framework.internal.*;
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

    private List<Variation> operators = new ArrayList<Variation>();

    private List<KevoreeFitnessFunction> fitnesses = new ArrayList<KevoreeFitnessFunction>();

    private KevoreePopulationFactory populationFactory = null;

    private Integer maxStep = 100;
    private Long maxTime = -1l;

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

    public Integer getMaxStep() {
        return maxStep;
    }

    public KevoreeGeneticEngine setMaxStep(Integer maxStep) {
        this.maxStep = maxStep;
        return this;
    }

    public Long getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Long maxTime) {
        this.maxTime = maxTime;
    }

    public List<ContainerRoot> solve() {
        Problem problem = new KevoreeProblem(fitnesses);
        Algorithm algorithm = new NSGAII(problem, new NondominatedSortingPopulation(), new EpsilonBoxDominanceArchive(0.5), new TournamentSelection(), new RandomCompoundVariation(operators), new KevoreeInitialization(populationFactory, problem));
        Long beginTimeMilli = System.currentTimeMillis();
        try {
            while (continueEngineComputation(algorithm, beginTimeMilli)) {
                algorithm.step();
            }
        } finally {
            algorithm.terminate();
            problem.close();
        }
        ArrayList<ContainerRoot> results = new ArrayList<ContainerRoot>();
        Population pop = algorithm.getResult();
        for (Solution s : pop) {
            KevoreeVariable var = (KevoreeVariable) s.getVariable(0);
            results.add(var.getModel());
        }
        return results;
    }

    protected boolean continueEngineComputation(Algorithm alg, Long beginTimeMilli) {
        if (alg.isTerminated()) {
            return false;
        }
        if (maxTime != -1) {
            if ( (System.currentTimeMillis() - beginTimeMilli) >= maxTime ) {
                return false;
            }
        }
        if (alg.getNumberOfEvaluations() >= maxStep) {
            return false;
        }
        return true;
    }


}
