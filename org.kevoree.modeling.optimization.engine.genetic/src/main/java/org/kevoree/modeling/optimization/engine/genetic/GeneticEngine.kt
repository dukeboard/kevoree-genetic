package org.kevoree.modeling.optimization.engine.genetic

import org.kevoree.modeling.optimization.api.OptimizationEngine
import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.MutationOperator
import org.kevoree.modeling.optimization.api.FitnessFunction
import org.kevoree.modeling.optimization.api.PopulationFactory
import org.kevoree.modeling.optimization.api.Solution
import java.util.ArrayList
import org.moeaframework.core.Algorithm
import org.kevoree.genetic.framework.internal.ModelOptimizationProblem
import org.moeaframework.algorithm.NSGAII
import org.moeaframework.core.NondominatedSortingPopulation
import org.moeaframework.core.EpsilonBoxDominanceArchive
import org.moeaframework.core.operator.TournamentSelection
import org.kevoree.modeling.optimization.engine.genetic.impl.ModelInitialization
import org.kevoree.modeling.optimization.framework.DefaultSolution
import org.kevoree.modeling.optimization.engine.genetic.impl.ModelVariable
import org.kevoree.modeling.optimization.engine.genetic.impl.RandomCompoundVariation
import org.kevoree.modeling.optimization.engine.genetic.impl.MutationVariationAdaptor
import org.moeaframework.algorithm.EpsilonMOEA
import org.moeaframework.algorithm.RandomSearch
import org.moeaframework.core.NondominatedPopulation
import org.moeaframework.core.comparator.ChainedComparator
import org.moeaframework.core.comparator.ParetoDominanceComparator
import org.moeaframework.core.comparator.CrowdingComparator

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 04/09/13
 * Time: 15:36
 */

class GeneticEngine<A : KMFContainer> : OptimizationEngine<A> {

    private var _operators: MutableList<MutationOperator<A>> = ArrayList<MutationOperator<A>>();
    private var _fitnesses: MutableList<FitnessFunction<A>> = ArrayList<FitnessFunction<A>>();
    private var _populationFactory: PopulationFactory<A>? = null;
    private var _maxGeneration = 100;
    private var _maxTime: Long = -1.toLong();
    private var _algorithm: GeneticAlgorithm = GeneticAlgorithm.EpsilonNSGII
    private var _dominanceEpsilon = 10.0;

    public fun setAlgorithm(alg: GeneticAlgorithm) {
        _algorithm = alg;
    }

    public fun setEpsilonDominance(dd: Double) {
        _dominanceEpsilon = dd
    }

    public override fun addOperator(operator: MutationOperator<A>): OptimizationEngine<A> {
        _operators.add(operator);
        return this;
    }
    public override fun addFitnessFuntion(function: FitnessFunction<A>): OptimizationEngine<A> {
        _fitnesses.add(function);
        return this;
    }
    public override fun setPopulationFactory(populationFactory: PopulationFactory<A>): OptimizationEngine<A> {
        _populationFactory = populationFactory;
        return this;
    }
    public override fun setMaxGeneration(maxGeneration: Int): OptimizationEngine<A> {
        _maxGeneration = maxGeneration;
        return this;
    }
    public override fun setMaxTime(maxTime: Long): OptimizationEngine<A> {
        _maxTime = maxTime;
        return this;
    }
    public override fun solve(): List<Solution> {
        if (_operators.isEmpty()) {
            throw Exception("No operators are configured, please configure at least one");
        }
        if (_fitnesses.isEmpty()) {
            throw Exception("No fitness function are configured, please configure at least one");
        }
        if(_populationFactory == null){
            throw Exception("No population factory are configured, please configure at least one");
        }

        var originAware = false
        for(fitness in _fitnesses){
            if(fitness.originAware()){
                originAware = true
            }
        }

        val problem = ModelOptimizationProblem(_fitnesses, _populationFactory!!.getCloner(), _populationFactory!!.getModelCompare());
        val variations = RandomCompoundVariation();
        for (operator in _operators){
            variations.appendOperator(MutationVariationAdaptor(operator));
        }

        var kalgo: Algorithm = NSGAII(problem, NondominatedSortingPopulation(), EpsilonBoxDominanceArchive(_dominanceEpsilon), TournamentSelection(), variations, ModelInitialization(_populationFactory!!, problem, originAware));
        when(_algorithm){
            GeneticAlgorithm.EpsilonNSGII -> {
                //don't do nothing -> default case
            }
            GeneticAlgorithm.NSGII -> {
                val selection = TournamentSelection(2, ChainedComparator(ParetoDominanceComparator(), CrowdingComparator()));
                kalgo = NSGAII(problem, NondominatedSortingPopulation(), null, selection, variations, ModelInitialization(_populationFactory!!, problem, originAware));
                //don't do nothing -> default case
            }
            GeneticAlgorithm.EpsilonMOEA -> {
                kalgo = EpsilonMOEA(problem, NondominatedSortingPopulation(), EpsilonBoxDominanceArchive(_dominanceEpsilon), TournamentSelection(), variations, ModelInitialization(_populationFactory!!, problem, originAware));
            }
            GeneticAlgorithm.EpsilonRandom -> {
                kalgo = RandomSearch(problem, ModelInitialization(_populationFactory!!, problem, originAware), NondominatedPopulation());
            }
            GeneticAlgorithm.EpsilonCrowdingNSGII -> {
                val selection = TournamentSelection(2, ChainedComparator(ParetoDominanceComparator(), CrowdingComparator()));
                kalgo = NSGAII(problem, NondominatedSortingPopulation(), EpsilonBoxDominanceArchive(_dominanceEpsilon), selection, variations, ModelInitialization(_populationFactory!!, problem, originAware));
            }
        //newSMSEMOA
            else -> {
            }
        }

        var generation = 0;
        var beginTimeMilli = System.currentTimeMillis();
        try {
            while (continueEngineComputation(kalgo, beginTimeMilli, generation)) {
                kalgo.step();
                generation++;
            }
        } finally {
            kalgo.terminate();
            problem.close();
        }
        return buildPopulation(kalgo)
    }

    private fun continueEngineComputation(alg: Algorithm, beginTimeMilli: Long, nbGeneration: Int): Boolean {
        if (alg.isTerminated()) {
            return false;
        }
        if (_maxTime != -1.toLong()) {
            if ((System.currentTimeMillis() - beginTimeMilli) >= _maxTime) {
                return false;
            }
        }
        if (nbGeneration >= _maxGeneration) {
            return false;
        }
        return true;
    }

    private fun buildPopulation(algo: Algorithm): List<Solution> {
        val results = ArrayList<Solution>();
        val population = algo.getResult();
        for (solution in population?.iterator()) {
            var loopvar = solution.getVariable(0) as ModelVariable;
            var modelSolution = DefaultSolution(loopvar.model!!, loopvar.origin, loopvar.traceSequence);
            for(fitness in _fitnesses){
                modelSolution.results.put(fitness.javaClass.getSimpleName(), fitness.evaluate(loopvar.model as A, loopvar.origin as A, loopvar.traceSequence))
            }
            results.add(modelSolution);
        }
        return results;
    }

}