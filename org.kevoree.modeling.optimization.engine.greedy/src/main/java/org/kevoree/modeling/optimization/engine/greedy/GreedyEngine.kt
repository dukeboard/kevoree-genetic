package org.kevoree.modeling.optimization.engine.greedy;

import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.PopulationFactory
import org.kevoree.modeling.optimization.api.solution.Solution
import java.util.ArrayList
import org.kevoree.modeling.optimization.framework.AbstractOptimizationEngine
import org.kevoree.modeling.optimization.executionmodel.ExecutionModel
import org.kevoree.modeling.optimization.framework.FitnessMetric
import org.kevoree.modeling.optimization.api.mutation.MutationOperator
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction
import org.kevoree.modeling.api.compare.ModelCompare
import org.kevoree.modeling.api.ModelCloner
import org.kevoree.modeling.api.trace.Event2Trace
import org.kevoree.modeling.optimization.framework.DefaultSolution
import org.kevoree.modeling.api.events.ModelElementListener
import org.kevoree.modeling.api.events.ModelEvent
import org.kevoree.modeling.optimization.api.mutation.MutationParameters
import java.util.HashMap
import org.kevoree.modeling.optimization.api.mutation.QueryVar
import org.kevoree.modeling.optimization.api.mutation.EnumVar
import org.kevoree.modeling.optimization.api.GenerationContext
import org.kevoree.modeling.optimization.api.solution.SolutionComparator
import org.kevoree.modeling.optimization.framework.comparator.MeanSolutionComparator
import org.kevoree.modeling.optimization.executionmodel.Run
import java.util.Date
import org.kevoree.modeling.optimization.executionmodel.Metric
import org.kevoree.modeling.optimization.api.solution.SolutionMutationListener
import org.kevoree.modeling.optimization.api.mutation.MutationOperatorSelector
import org.kevoree.modeling.optimization.framework.selector.DefaultRandomOperatorSelector
import org.kevoree.modeling.optimization.util.FitnessNormalizer
import org.kevoree.modeling.optimization.api.Context
import org.kevoree.modeling.optimization.framework.DefaultContext
import org.kevoree.modeling.optimization.util.FitnessMetaData
import org.kevoree.modeling.api.trace.TraceSequence
import org.kevoree.modeling.optimization.util.MetricUpdater
import org.kevoree.modeling.optimization.executionmodel.factory.DefaultExecutionModelFactory

/**
 * Created by duke on 14/08/13.
 */
public class GreedyEngine<A : KMFContainer> : AbstractOptimizationEngine<A> {

    override val context: Context = DefaultContext()

    override var _operators: MutableList<MutationOperator<A>> = ArrayList<MutationOperator<A>>()
    override var _fitnesses: MutableList<FitnessMetaData<A>> = ArrayList<FitnessMetaData<A>>()

    override var mutationSelector: MutationOperatorSelector<A> = DefaultRandomOperatorSelector(_operators)
    override var populationFactory: PopulationFactory<A>? = null
    override var maxGeneration: Int = 100
    override var maxTime: Long = -1.toLong()
    override var executionModel: ExecutionModel? = null
    override var solutionMutationListeners: MutableList<SolutionMutationListener<A>> = ArrayList<SolutionMutationListener<A>>()
    override var solutionComparator: SolutionComparator<A> = MeanSolutionComparator()

    override var _executionModelFactory: DefaultExecutionModelFactory? = null
    override var _metricsName: MutableList<FitnessMetric> = ArrayList<FitnessMetric>()

    var mainComparator: SolutionComparator<A>? = MeanSolutionComparator<A>()
    var originAware = true
    var modelCompare: ModelCompare? = null
    var front: Solution<A>? = null
    var nbMutation: Int = 0
    var modelCloner: ModelCloner? = null
    var event2Trace: Event2Trace? = null
    private var currentRun: Run? = null;

    override fun desactivateOriginAware() {
        originAware = false;
    }

    private fun mutate(solution: Solution<A>, operator: MutationOperator<A>, parameters: MutationParameters): Solution<A> {
        val clonedModel = modelCloner!!.clone(solution.model)!!
        //Error is here Asssaad - Should change the parameters as well to point to the clonedContext and newSolution objects instead of old ones

        /* try to unresolved if contained elements */
        for(param in parameters.getKeys()){
            val value = parameters.getParam(param)
            if(value is KMFContainer){
                val resolved = clonedModel.findByPath((value as KMFContainer).path()!!)
                if(resolved != null && resolved.metaClassName() == value.metaClassName()){
                    parameters.setParam(param, resolved)
                }
            }
        }
        val clonedContext = solution.context.createChild(modelCompare!!, clonedModel, true)
        val newSolution = DefaultSolution(clonedModel, clonedContext)
        val modelListener = object : ModelElementListener {
            override fun elementChanged(evt: ModelEvent) {
                if(clonedContext.traceSequence != null){
                    clonedContext.traceSequence.populate(event2Trace!!.convert(evt).traces);
                }
            }
        }
        clonedModel.addModelTreeListener(modelListener)
        //do real operation
        operator.mutate(clonedModel, parameters)
        clonedContext.operator = operator
        nbMutation++
        //evaluate new solution
        for(fit in _fitnesses){
            val rawValue = fit.fitness.evaluate(newSolution.model, clonedContext)
            newSolution.results.put(fit.fitness, FitnessNormalizer.norm(rawValue, fit))
            newSolution.rawResults.put(fit.fitness, rawValue)
        }
        return newSolution
    }

    private val date = Date()

    private fun computeStep(solution: Solution<A>) {
        val previousTime = date.getTime()
        for(operator in _operators){
            val enumerationVariables = operator.enumerateVariables(solution.model);
            val enumeratedValues = HashMap<String, List<Any>>()
            //flat all variables
            for(variable in enumerationVariables){
                when(variable) {
                    is QueryVar -> {
                        var queryResult = solution.model.select(variable.query)
                        enumeratedValues.put(variable.name, queryResult)
                    }
                    is EnumVar -> {
                        enumeratedValues.put(variable.name, variable.elements)
                    }
                    else -> {
                        throw Exception("Unknow Mutator Variable (Enum/Query)" + variable)
                    }
                }
            }
            //indice for enumerate all possible paretoFront
            val enumeratedValuesIndice = HashMap<String, Int>()
            for(variable in enumerationVariables){
                enumeratedValuesIndice.set(variable.name, 0)
            }
            //enumerate all candidates
            for(keyName in enumeratedValues.keySet()){
                while(enumeratedValuesIndice.get(keyName)!! < enumeratedValues.get(keyName)!!.size){
                    var paramters = MutationParameters()
                    for(keyName2 in enumeratedValues.keySet()){
                        val indice = enumeratedValuesIndice.get(keyName2)!!
                        val value = enumeratedValues.get(keyName2)!!.get(indice)
                        paramters.setParam(keyName2, value)
                    }
                    var mutatedSolution = mutate(solution, operator, paramters)
                    if(front == null || mainComparator!!.compare(front!!, mutatedSolution)){
                        switchToNewFrontSolution(mutatedSolution)
                    }
                    if(nbMutation > maxGeneration){
                        return;
                    }
                    enumeratedValuesIndice.put(keyName, enumeratedValuesIndice.get(keyName)!! + 1)
                }
            }
        }
        if(front != null){

            if(executionModel != null){ //this line was added by Assaad
            val newStep = _executionModelFactory!!.createStep()
            newStep.startTime = previousTime
            newStep.endTime = date.getTime()
            newStep.generationNumber = nbMutation
            val modelSolution = _executionModelFactory!!.createSolution()
            newStep.addSolutions(modelSolution)
            for(fitness in front!!.getFitnesses()){
                val newScore = _executionModelFactory!!.createScore()
                newScore.fitness = executionModel!!.findFitnessByID(fitness.javaClass.getSimpleName())
                newScore.value = front!!.getScoreForFitness(fitness)!!
                newScore.name = newScore.fitness!!.name
                modelSolution.addScores(newScore)
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
            currentRun!!.addSteps(newStep)
        }
        } //this line was added by Assaad
   }

    private var isChangedSinceLastStep = false

    private fun switchToNewFrontSolution(newSol: Solution<A>) {
        front = newSol
        isChangedSinceLastStep = true
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
            currentRun!!.algName = "greedy_" + mainComparator.javaClass.getSimpleName();
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
        nbMutation = 0 // init nb mutation counter
        modelCompare = populationFactory!!.getModelCompare()
        modelCloner = populationFactory!!.getCloner()
        event2Trace = Event2Trace(modelCompare!!)
        var population = populationFactory!!.createPopulation();
        if(population.size() > maxGeneration){
            System.err.println("Warning Population Size > MaxGenetation !!!");
        }
        for(initElem in population){
            //create an initial solution
            val defaultSolution = DefaultSolution(initElem, GenerationContext(null, initElem, initElem, TraceSequence(modelCompare!!.factory), null,context))
            //evaluate initial solution
            for(fit in _fitnesses){
                val rawValue = fit.fitness.evaluate(defaultSolution.model, defaultSolution.context)
                defaultSolution.results.put(fit.fitness, FitnessNormalizer.norm(rawValue, fit))
                defaultSolution.rawResults.put(fit.fitness, rawValue)
            }
            isChangedSinceLastStep = false //track modification
            computeStep(defaultSolution)
            if(nbMutation >= maxGeneration || !isChangedSinceLastStep){
                return buildSolutions();
            }
        }
        //Front is ready next step
        while(nbMutation < maxGeneration && front != null){
            isChangedSinceLastStep = false //track modification
            computeStep(front!!)
            if(nbMutation >= maxGeneration || !isChangedSinceLastStep){
                return buildSolutions();
            }
        }
        return buildSolutions();
    }

    private fun buildSolutions(): List<Solution<A>> {
        var solutions = ArrayList<Solution<A>>()
        if(front != null){
            solutions.add(front!!)
        }
        return solutions
    }

}
