package org.kevoree.modeling.optimization.engine.genetic

import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.PopulationFactory
import org.kevoree.modeling.optimization.api.solution.Solution
import java.util.ArrayList
import org.moeaframework.core.Algorithm
import org.kevoree.genetic.framework.internal.ModelOptimizationProblem
import org.moeaframework.algorithm.NSGAII
import org.moeaframework.core.NondominatedSortingPopulation
import org.moeaframework.core.EpsilonBoxDominanceArchive
import org.moeaframework.core.operator.TournamentSelection
import org.kevoree.modeling.optimization.engine.genetic.impl.ModelInitialization
import org.kevoree.modeling.optimization.engine.genetic.impl.CompoundVariation
import org.moeaframework.algorithm.EpsilonMOEA
import org.moeaframework.algorithm.RandomSearch
import org.moeaframework.core.NondominatedPopulation
import org.moeaframework.core.comparator.ChainedComparator
import org.moeaframework.core.comparator.ParetoDominanceComparator
import org.moeaframework.core.comparator.CrowdingComparator
import org.kevoree.modeling.optimization.framework.AbstractOptimizationEngine
import org.kevoree.modeling.optimization.executionmodel.ExecutionModel
import org.kevoree.modeling.optimization.executionmodel.Run
import java.util.Date
import org.kevoree.modeling.optimization.framework.FitnessMetric
import org.kevoree.modeling.optimization.executionmodel.Metric
import org.kevoree.modeling.optimization.api.mutation.MutationOperator
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction
import org.kevoree.modeling.optimization.engine.genetic.ext.HypervolumeComparator
import org.kevoree.modeling.optimization.api.solution.SolutionComparator
import org.kevoree.modeling.optimization.api.mutation.MutationOperatorSelector
import org.kevoree.modeling.optimization.framework.selector.DefaultRandomOperatorSelector
import org.kevoree.modeling.optimization.framework.comparator.MeanSolutionComparator
import org.kevoree.modeling.optimization.api.solution.SolutionMutationListener
import org.kevoree.modeling.optimization.engine.genetic.ext.HypervolumeSelection
import org.kevoree.modeling.optimization.api.Context
import org.kevoree.modeling.optimization.framework.DefaultContext
import org.kevoree.modeling.optimization.util.FitnessMetaData
import org.kevoree.modeling.optimization.executionmodel.factory.DefaultExecutionModelFactory
import org.kevoree.modeling.optimization.util.MetricUpdater

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 04/09/13
 * Time: 15:36
 */

class GeneticEngine<A : KMFContainer> : AbstractOptimizationEngine<A> {

    override val context: Context = DefaultContext()
    override var solutionComparator: SolutionComparator<A> = MeanSolutionComparator()
    override var _metricsName: MutableList<FitnessMetric> = ArrayList<FitnessMetric>()
    override var _operators: MutableList<MutationOperator<A>> = ArrayList<MutationOperator<A>>()
    override var mutationSelector: MutationOperatorSelector<A> = DefaultRandomOperatorSelector(_operators)
    override var _fitnesses: MutableList<FitnessMetaData<A>> = ArrayList<FitnessMetaData<A>>()
    override var populationFactory: PopulationFactory<A>? = null
    override var maxGeneration: Int = 100
    override var maxTime: Long = -1.toLong()
    override var executionModel: ExecutionModel? = null
    override var _executionModelFactory: DefaultExecutionModelFactory? = null
    override var solutionMutationListeners: MutableList<SolutionMutationListener<A>> = ArrayList<SolutionMutationListener<A>>()

    private var _algorithm: GeneticAlgorithm = GeneticAlgorithm.EpsilonNSGII
    private var _dominanceEpsilon = 0.05;
    private  var originAware = true

    private var currentRun: Run? = null;

    public fun setAlgorithm(alg: GeneticAlgorithm) {
        _algorithm = alg;
    }

    override fun desactivateOriginAware() {
        originAware = false
    }

    public fun setEpsilonDominance(dd: Double) {
        _dominanceEpsilon = dd
    }

    public override fun solve(): List<Solution<A>> {
        if (_operators.isEmpty()) {
            throw Exception("No operators are configured, please configure at least one");
        }
        if (_fitnesses.isEmpty()) {
            throw Exception("No fitness function are configured, please configure at least one");
        }
        if(populationFactory == null){
            throw Exception("No population factory are configured, please configure at least one");
        }
        if(executionModel != null){
            //create RUN
            currentRun = _executionModelFactory!!.createRun();
            currentRun!!.algName = _algorithm.name() + "-" + mutationSelectionStrategy.name();
            executionModel!!.addRuns(currentRun!!);
            currentRun!!.startTime = Date().getTime();
        }
        for(fitness in _fitnesses){
            if(executionModel != null && executionModel!!.findFitnessByID(fitness.javaClass.getSimpleName()) == null){
                val newFitness = _executionModelFactory!!.createFitness()
                newFitness.name = fitness.javaClass.getSimpleName()
                executionModel!!.addFitness(newFitness)
            }
        }
        val problem = ModelOptimizationProblem(_fitnesses, populationFactory!!.getCloner(), populationFactory!!.getModelCompare());
        val variations = CompoundVariation(this, problem);


        var kalgo: Algorithm = NSGAII(problem, NondominatedSortingPopulation(), EpsilonBoxDominanceArchive(_dominanceEpsilon), TournamentSelection(), variations, ModelInitialization(populationFactory!!, problem, originAware,context));
        when(_algorithm) {
            GeneticAlgorithm.EpsilonNSGII -> {
                //don't do nothing -> default case
            }
            GeneticAlgorithm.NSGAII -> {
                val selection = TournamentSelection(2, ChainedComparator(ParetoDominanceComparator(), CrowdingComparator()));
                kalgo = NSGAII(problem, NondominatedSortingPopulation(), null, selection, variations, ModelInitialization(populationFactory!!, problem, originAware,context));
            }
            GeneticAlgorithm.HypervolumeNSGAII -> {
                val selection = TournamentSelection(2, ChainedComparator(ParetoDominanceComparator(), CrowdingComparator(),  HypervolumeComparator(problem)));
                kalgo = NSGAII(problem, NondominatedSortingPopulation(), EpsilonBoxDominanceArchive(_dominanceEpsilon), selection, variations, ModelInitialization(populationFactory!!, problem, originAware,context));
            }
            /* TO CHECK, BAD BEHAVIOR */
            GeneticAlgorithm.EpsilonMOEA -> {
                kalgo = EpsilonMOEA(problem, NondominatedSortingPopulation(), EpsilonBoxDominanceArchive(_dominanceEpsilon), TournamentSelection(), variations, ModelInitialization(populationFactory!!, problem, originAware,context));
            }
            /* TO CHECK, BAD BEHAVIOR */
            GeneticAlgorithm.HypervolumeMOEA -> {
                val selection = TournamentSelection(2, ChainedComparator(ParetoDominanceComparator(), HypervolumeComparator(problem)));
                kalgo = EpsilonMOEA(problem, NondominatedSortingPopulation(), EpsilonBoxDominanceArchive(_dominanceEpsilon), selection, variations, ModelInitialization(populationFactory!!, problem, originAware,context));
            }
            GeneticAlgorithm.EpsilonRandom -> {
                kalgo = RandomSearch(problem, ModelInitialization(populationFactory!!, problem, originAware,context), NondominatedPopulation());
            }
            GeneticAlgorithm.EpsilonCrowdingNSGII -> {
                val selection = TournamentSelection(2, ChainedComparator(ParetoDominanceComparator(), CrowdingComparator()));
                kalgo = NSGAII(problem, NondominatedSortingPopulation(), EpsilonBoxDominanceArchive(_dominanceEpsilon), selection, variations, ModelInitialization(populationFactory!!, problem, originAware,context));
            }
            else -> {
            }
        }

        var generation = 0;
        var beginTimeMilli = System.currentTimeMillis();
        try {
            var previousTime: Long? = null
            while (continueEngineComputation(kalgo, beginTimeMilli, generation)) {
                previousTime = System.currentTimeMillis() - beginTimeMilli
                kalgo.step();
                if(executionModel != null){
                    //update the execution model
                    val newStep = _executionModelFactory!!.createStep()
                    newStep.startTime = previousTime
                    newStep.endTime = System.currentTimeMillis() - beginTimeMilli
                    newStep.generationNumber = generation
                    currentRun!!.addSteps(newStep)
                    //build solution model
                    val population = kalgo.getResult();
                    for (solution in population?.iterator()) {
                        val modelSolution = _executionModelFactory!!.createSolution()
                        newStep.addSolutions(modelSolution)
                        for(i in 0..solution.getNumberOfObjectives() - 1){
                            val fitnessName = problem.fitnessFromIndice.get(i).javaClass.getSimpleName()
                            val value = solution.getObjective(i);
                            val newScore = _executionModelFactory!!.createScore()
                            newScore.fitness = executionModel!!.findFitnessByID(fitnessName)
                            newScore.value = value
                            newScore.name = newScore.fitness!!.name
                            modelSolution.addScores(newScore)
                        }
                    }
                    //add metric and call update
                    for(loopFitnessMetric in _metricsName){
                        val metric: Metric = _executionModelFactory!!.create(loopFitnessMetric.metricClassName) as Metric
                        if(metric is org.kevoree.modeling.optimization.executionmodel.FitnessMetric){
                            val fitMet = metric as org.kevoree.modeling.optimization.executionmodel.FitnessMetric
                            fitMet.fitness = executionModel!!.findFitnessByID(loopFitnessMetric.fitnessName!!)
                        }
                        newStep.addMetrics(metric) //add before update ! mandatory !
                        MetricUpdater.update(metric)
                    }
                }
                generation++;
            }
        } finally {
            kalgo.terminate();
            problem.close();
        }

        if(executionModel != null){
            //create RUN
            currentRun!!.endTime = Date().getTime();
        }

        val population = kalgo.getResult();
        var results = ArrayList<org.kevoree.modeling.optimization.api.solution.Solution<A>>()
        for (solution in population?.iterator()) {
            results.add(solution as org.kevoree.modeling.optimization.api.solution.Solution<A>)
        }
        //TODO sort result using comparator
        return results
    }

    private fun continueEngineComputation(alg: Algorithm, beginTimeMilli: Long, nbGeneration: Int): Boolean {
        if (alg.isTerminated()) {
            return false;
        }
        if (maxTime != -1.toLong()) {
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