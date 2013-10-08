package org.kevoree.modeling.optimization.engine.genetic

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
import org.kevoree.modeling.optimization.framework.AbstractOptimizationEngine
import org.kevoree.modeling.optimization.executionmodel.ExecutionModel
import org.kevoree.modeling.optimization.executionmodel.impl.DefaultExecutionModelFactory
import org.kevoree.modeling.optimization.executionmodel.Run
import java.util.Date
import org.kevoree.modeling.optimization.framework.FitnessMetric
import org.kevoree.modeling.optimization.executionmodel.Metric
import org.moeaframework.core.comparator.HypervolumeComparator

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 04/09/13
 * Time: 15:36
 */

class GeneticEngine<A : KMFContainer> : AbstractOptimizationEngine<A> {
    override var _metricsName: MutableList<FitnessMetric> = ArrayList<FitnessMetric>()
    override var _operators: MutableList<MutationOperator<A>> = ArrayList<MutationOperator<A>>()
    override var _fitnesses: MutableList<FitnessFunction<A>> = ArrayList<FitnessFunction<A>>()
    override var _populationFactory: PopulationFactory<A>? = null
    override var _maxGeneration: Int = 100
    override var _maxTime: Long = -1.toLong()
    override var _executionModel: ExecutionModel? = null
    override var _executionModelFactory: DefaultExecutionModelFactory? = null

    private var _algorithm: GeneticAlgorithm = GeneticAlgorithm.EpsilonNSGII
    private var _dominanceEpsilon = 5.0;

    private var currentRun: Run? = null;

    public fun setAlgorithm(alg: GeneticAlgorithm) {
        _algorithm = alg;
    }

    public fun setEpsilonDominance(dd: Double) {
        _dominanceEpsilon = dd
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
        if(_executionModel != null){
            //create RUN
            currentRun = _executionModelFactory!!.createRun();
            currentRun!!.algName = _algorithm.name();
            _executionModel!!.addRuns(currentRun!!);
            currentRun!!.startTime = Date().getTime();
        }
        var originAware = false
        for(fitness in _fitnesses){
            if(fitness.originAware()){
                originAware = true
            }
            if(_executionModel!=null &&_executionModel!!.findFitnessByID(fitness.javaClass.getCanonicalName()) == null){
                val newFitness = _executionModelFactory!!.createFitness()
                newFitness.name = fitness.javaClass.getCanonicalName()
                _executionModel!!.addFitness(newFitness)
            }
        }

        val problem = ModelOptimizationProblem(_fitnesses, _populationFactory!!.getCloner(), _populationFactory!!.getModelCompare());
        val variations = RandomCompoundVariation();
        for (operator in _operators){
            variations.appendOperator(MutationVariationAdaptor(operator));
        }

        var kalgo: Algorithm = NSGAII(problem, NondominatedSortingPopulation(), EpsilonBoxDominanceArchive(_dominanceEpsilon), TournamentSelection(), variations, ModelInitialization(_populationFactory!!, problem, originAware));
        when(_algorithm) {
            GeneticAlgorithm.EpsilonNSGII -> {
                //don't do nothing -> default case
            }
            GeneticAlgorithm.NSGAII -> {
                val selection = TournamentSelection(2, ChainedComparator(ParetoDominanceComparator(), CrowdingComparator()));
                kalgo = NSGAII(problem, NondominatedSortingPopulation(), null, selection, variations, ModelInitialization(_populationFactory!!, problem, originAware));
                //don't do nothing -> default case
            }
            GeneticAlgorithm.HypervolumeNSGAII -> {
                val selection = TournamentSelection(2, ChainedComparator(ParetoDominanceComparator(), HypervolumeComparator(problem)));
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
            else -> {
            }
        }

        var generation = 0;
        var beginTimeMilli = System.currentTimeMillis();
        try {
            val date = Date()
            var previousTime: Long? = null
            while (continueEngineComputation(kalgo, beginTimeMilli, generation)) {
                previousTime = date.getTime()
                kalgo.step();
                if(_executionModel != null){
                    //update the execution model
                    val newStep = _executionModelFactory!!.createStep()
                    newStep.startTime = previousTime
                    newStep.endTime = date.getTime()
                    newStep.generationNumber = generation
                    currentRun!!.addSteps(newStep)
                    //build solution model
                    val population = kalgo.getResult();
                    for (solution in population?.iterator()) {
                        val modelSolution = _executionModelFactory!!.createSolution()
                        newStep.addSolutions(modelSolution)
                        for(i in 0..solution.getNumberOfObjectives()-1){
                            val fitnessName = problem.fitnessFromIndice.get(i).javaClass.getCanonicalName()
                            val value = solution.getObjective(i);
                            val newScore = _executionModelFactory!!.createScore()
                            newScore.fitness = _executionModel!!.findFitnessByID(fitnessName)
                            newScore.value = value
                            modelSolution.addScores(newScore)
                        }
                    }
                    //add metric and call update
                    for(loopFitnessMetric in _metricsName){
                        val metric: Metric = _executionModelFactory!!.create(loopFitnessMetric.metricClassName) as Metric
                        if(metric is org.kevoree.modeling.optimization.executionmodel.FitnessMetric){
                            val fitMet = metric as org.kevoree.modeling.optimization.executionmodel.FitnessMetric
                            fitMet.fitness = _executionModel!!.findFitnessByID(loopFitnessMetric.fitnessName)
                        }
                        newStep.addMetrics(metric) //add before update ! mandatory !
                        metric.update()
                    }
                }
                generation++;
            }
        } finally {
            kalgo.terminate();
            problem.close();
        }

        if(_executionModel != null){
            //create RUN
            currentRun!!.endTime = Date().getTime();
        }

        return buildPopulation(kalgo, problem)
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

    private fun buildPopulation(algo: Algorithm, problem: ModelOptimizationProblem<A>): List<Solution> {
        val results = ArrayList<Solution>();
        val population = algo.getResult();
        for (solution in population?.iterator()) {
            var loopvar = solution.getVariable(0) as ModelVariable;
            var modelSolution = DefaultSolution(loopvar.model!!, loopvar.origin, loopvar.traceSequence);
            for(fitness in _fitnesses){
                modelSolution.results.put(fitness.javaClass.getSimpleName(), solution.getObjective(problem.indiceFromFitness.get(fitness)!!))
            }
            results.add(modelSolution);
        }
        return results;
    }

}