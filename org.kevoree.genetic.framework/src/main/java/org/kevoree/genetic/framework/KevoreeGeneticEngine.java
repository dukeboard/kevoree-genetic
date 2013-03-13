package org.kevoree.genetic.framework;

import org.kevoree.ContainerRoot;
import org.kevoree.genetic.framework.internal.*;
import org.moeaframework.algorithm.GDE3;
import org.moeaframework.algorithm.MOEAD;
import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.core.*;
import org.moeaframework.core.comparator.ParetoDominanceComparator;
import org.moeaframework.core.operator.TournamentSelection;
import org.moeaframework.core.operator.real.DifferentialEvolutionSelection;
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
public class KevoreeGeneticEngine {

    public enum KevoreeGeneticAlgorithms {NSGAII, MOEAD, GDE3}

    private List<KevoreeOperator> operators = new ArrayList<KevoreeOperator>();

    private List<KevoreeFitnessFunction> fitnesses = new ArrayList<KevoreeFitnessFunction>();

    private KevoreePopulationFactory populationFactory = null;

    private Integer maxGeneration = 100;
    private Long maxTime = -1l;

    private KevoreeGeneticAlgorithms algorithm = KevoreeGeneticAlgorithms.NSGAII;

    public KevoreeGeneticAlgorithms getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(KevoreeGeneticAlgorithms algorithm) {
        this.algorithm = algorithm;
    }

    public KevoreeGeneticEngine addOperator(KevoreeOperator operator) {
        operators.add(operator);
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

    public Integer getMaxGeneration() {
        return maxGeneration;
    }

    public KevoreeGeneticEngine setMaxGeneration(Integer maxGeneration) {
        this.maxGeneration = maxGeneration;
        return this;
    }

    public Long getMaxTime() {
        return maxTime;
    }

    public KevoreeGeneticEngine setMaxTime(Long maxTime) {
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

    public List<KevoreeSolution> solve() throws Exception {

        if (operators.isEmpty()) {
            throw new Exception("No Kevoree operator are added, please configure at least one");
        }

        KevoreeProblem kprob = new KevoreeProblem(fitnesses);
        Problem problem = kprob;
        if (isDistributed()) {
            if (executor == null) {
                executor = Executors.newFixedThreadPool(nbCore);
            }
            problem = new DistributedProblem(kprob, executor);
        }
        RandomCompoundVariation variations = new RandomCompoundVariation();
        for (KevoreeOperator operator : operators) {
            variations.appendOperator(new KevoreeVariationAdaptor(operator, problem));
        }
        Algorithm kalgo = null;
        if (this.algorithm.equals(KevoreeGeneticAlgorithms.NSGAII)) {
            kalgo = new NSGAII(problem, new NondominatedSortingPopulation(), new EpsilonBoxDominanceArchive(0.5), new TournamentSelection(), variations, new KevoreeInitialization(populationFactory, problem));
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
        ArrayList<KevoreeSolution> results = new ArrayList<KevoreeSolution>();
        Population pop = kalgo.getResult();
        for (Solution s : pop) {
            KevoreeVariable var = (KevoreeVariable) s.getVariable(0);
            KevoreeSolution ksol = new KevoreeSolution(s, kprob);
            results.add(ksol);
        }
        if (executor != null) {
            executor.shutdownNow();
            executor = null;
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
