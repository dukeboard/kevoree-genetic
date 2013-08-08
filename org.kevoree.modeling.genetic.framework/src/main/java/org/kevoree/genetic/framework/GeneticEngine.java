package org.kevoree.genetic.framework;

import org.kevoree.genetic.framework.internal.*;
import org.kevoree.genetic.framework.internal.variation.MutationVariationAdaptor;
import org.kevoree.modeling.genetic.api.FitnessFunction;
import org.kevoree.modeling.genetic.api.MutationOperator;
import org.kevoree.modeling.genetic.api.PopulationFactory;
import org.kevoree.modeling.genetic.api.ResolutionEngine;
import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.core.*;
import org.moeaframework.core.operator.TournamentSelection;
import org.moeaframework.util.distributed.DistributedProblem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 17:41
 */
public class GeneticEngine implements ResolutionEngine {

    private List<MutationOperator> operators = new ArrayList<MutationOperator>();
    private List<FitnessFunction> fitnesses = new ArrayList<FitnessFunction>();
    private PopulationFactory populationFactory = null;
    private Integer maxGeneration = 100;
    private Long maxTime = -1l;


    private KevoreeGeneticAlgorithms algorithm = KevoreeGeneticAlgorithms.NSGAII;
    public enum KevoreeGeneticAlgorithms {NSGAII, MOEAD, GDE3}

    public KevoreeGeneticAlgorithms getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(KevoreeGeneticAlgorithms algorithm) {
        this.algorithm = algorithm;
    }

    public GeneticEngine addOperator(MutationOperator operator) {
        operators.add(operator);
        return this;
    }

    public GeneticEngine addFitnessFuntion(FitnessFunction function) {
        fitnesses.add(function);
        return this;
    }

    public GeneticEngine setPopulationFactory(PopulationFactory _populationFactory) {
        populationFactory = _populationFactory;
        return this;
    }

    public Integer getMaxGeneration() {
        return maxGeneration;
    }

    public GeneticEngine setMaxGeneration(Integer maxGeneration) {
        this.maxGeneration = maxGeneration;
        return this;
    }

    public Long getMaxTime() {
        return maxTime;
    }

    public GeneticEngine setMaxTime(Long maxTime) {
        this.maxTime = maxTime;
        return this;
    }

    static final Integer nbCore = Runtime.getRuntime().availableProcessors();

    private ExecutorService executor = null;

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    private boolean distributed = false;

    public boolean isDistributed() {
        return distributed;
    }

    public void setDistributed(boolean distributed) {
        this.distributed = distributed;
    }

    private boolean monitored = false;

    public boolean isMonitored() {
        return monitored;
    }

    public void setMonitored(boolean monitored) {
        this.monitored = monitored;
    }


    private Double dominanceDelta = 10d;

    public Double getDominanceDelta() {
        return dominanceDelta;
    }

    public void setDominanceDelta(Double dominanceDelta) {
        this.dominanceDelta = dominanceDelta;
    }

    public List<KevoreeSolution> solve() throws Exception {

        if (operators.isEmpty()) {
            throw new Exception("No operators are configured, please configure at least one");
        }
        if (fitnesses.isEmpty()) {
            throw new Exception("No fitness function are configured, please configure at least one");
        }
        if(populationFactory == null){
            throw new Exception("No population factory are configured, please configure at least one");
        }

        KevoreeProblem kprob = new KevoreeProblem(fitnesses, populationFactory.getCloner());
        Problem problem = kprob;
        if (isDistributed()) {
            if (executor == null) {
                executor = Executors.newFixedThreadPool(nbCore);
            }
            problem = new DistributedProblem(kprob, executor);
        }
        RandomCompoundVariation variations = new RandomCompoundVariation();
        for (MutationOperator operator : operators) {
            variations.appendOperator(new MutationVariationAdaptor(operator));
        }
        Algorithm kalgo = null;
        if (this.algorithm.equals(KevoreeGeneticAlgorithms.NSGAII)) {
            kalgo = new NSGAII(problem, new NondominatedSortingPopulation(), new EpsilonBoxDominanceArchive(dominanceDelta), new TournamentSelection(), variations, new KevoreeInitialization(populationFactory, problem));
        }
        /*
        if(this.algorithm.equals(KevoreeGeneticAlgorithms.MOEAD)){
            kalgo = new MOEAD(problem, new NondominatedSortingPopulation(), new EpsilonBoxDominanceArchive(0.5), new TournamentSelection(), new RandomCompoundVariation(operators), new KevoreeInitialization(populationFactory, problem));
        } */
        /*
        if(this.algorithm.equals(KevoreeGeneticAlgorithms.GDE3)){
            kalgo = new GDE3(problem, new NondominatedSortingPopulation(), new ParetoDominanceComparator() , new DifferentialEvolutionSelection() , new RandomCompoundVariation(operators), new KevoreeInitialization(populationFactory, problem));
        }*/

        Integer generation = 0;
        Long beginTimeMilli = System.currentTimeMillis();
        try {
            while (continueEngineComputation(kalgo, beginTimeMilli, generation)) {
                kalgo.step();
                generation++;
            }
        } finally {
            kalgo.terminate();
            problem.close();
        }
        List<KevoreeSolution> results = buildPopulation(kalgo, kprob);
        if (executor != null) {
            executor.shutdownNow();
            executor = null;
        }
        return results;
    }

    private List<KevoreeSolution> buildPopulation(Algorithm kalgo, KevoreeProblem kprob) {
        ArrayList<KevoreeSolution> results = new ArrayList<KevoreeSolution>();
        Population pop = kalgo.getResult();
        for (Solution s : pop) {
            KevoreeVariable var = (KevoreeVariable) s.getVariable(0);
            KevoreeSolution ksol = new KevoreeSolution(s, kprob);
            results.add(ksol);
        }
        return results;
    }

    protected boolean continueEngineComputation(Algorithm alg, Long beginTimeMilli, Integer nbGeneration) {
        if (alg.isTerminated()) {
            return false;
        }
        if (maxTime != -1) {
            if ((System.currentTimeMillis() - beginTimeMilli) >= maxTime) {
                return false;
            }
        }
        if (nbGeneration >= maxGeneration) {
            return false;
        }
        return true;
    }


}