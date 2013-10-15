package org.kevoree.modeling.optimization.engine.fullsearch

import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.PopulationFactory
import org.kevoree.modeling.optimization.api.Solution
import java.util.ArrayList
import org.kevoree.modeling.optimization.framework.DefaultSolution
import org.kevoree.modeling.optimization.executionmodel.impl.DefaultExecutionModelFactory
import org.kevoree.modeling.optimization.executionmodel.ExecutionModel
import org.kevoree.modeling.optimization.framework.AbstractOptimizationEngine
import org.kevoree.modeling.optimization.framework.FitnessMetric
import org.kevoree.modeling.optimization.api.mutation.MutationOperator
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction
import org.kevoree.modeling.optimization.api.GenerationContext
import org.kevoree.modeling.api.compare.ModelCompare
import java.util.HashMap
import org.kevoree.modeling.optimization.api.mutation.QueryVar
import org.kevoree.modeling.optimization.api.mutation.EnumVar
import org.kevoree.modeling.optimization.api.mutation.MutationParameters
import org.kevoree.modeling.api.ModelCloner
import org.kevoree.modeling.api.trace.Event2Trace
import org.kevoree.modeling.api.events.ModelElementListener
import org.kevoree.modeling.api.events.ModelEvent
import org.kevoree.modeling.optimization.api.SolutionComparator
import java.util.Date
import org.kevoree.modeling.optimization.executionmodel.Metric
import org.kevoree.modeling.optimization.executionmodel.Run

/**
 * Created by duke on 14/08/13.
 */

public class FullSearchEngine<A : KMFContainer> : AbstractOptimizationEngine<A> {
    var mainComparator: SolutionComparator<A>? = null
    override var _operators: MutableList<MutationOperator<A>> = ArrayList<MutationOperator<A>>()
    override var _fitnesses: MutableList<FitnessFunction<A>> = ArrayList<FitnessFunction<A>>()
    override var _populationFactory: PopulationFactory<A>? = null
    override var _maxGeneration: Int = 100
    override var _maxTime: Long = -1.toLong()
    override var _executionModel: ExecutionModel? = null
    override var _executionModelFactory: DefaultExecutionModelFactory? = null
    override var _metricsName: MutableList<FitnessMetric> = ArrayList<FitnessMetric>()
    var originAware = true
    var modelCompare: ModelCompare? = null
    var front = ArrayList<Solution<A>>()
    var modelCloner: ModelCloner? = null
    var event2Trace: Event2Trace? = null
    var solutionIndex = IndexSolutionHandler<A>()
    private var currentRun: Run? = null;

    override fun desactivateOriginAware() {
        originAware = false;
    }

    override fun setComparator(solC: SolutionComparator<A>) {
        mainComparator = solC;
    }

    private fun mutate(solution: Solution<A>, operator: MutationOperator<A>, parameters: MutationParameters): Solution<A> {
        val clonedModel = modelCloner!!.clone(solution.model)!!
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
        //evaluate new solution
        for(fit in _fitnesses){
            newSolution.results.put(fit.javaClass.getSimpleName(), fit.evaluate(newSolution.model, clonedContext))
        }
        return newSolution
    }

    private val date = Date()

    private fun computeStep(solution: Solution<A>) {
        for(operator in _operators){
            val enumerationVariables = operator.enumerateVariables(solution.model);
            val enumeratedValues = HashMap<String, List<Any>>()
            //flat all variables
            for(variable in enumerationVariables){
                when(variable) {
                    is QueryVar -> {
                        var queryResult = solution.model.selectByQuery(variable.query)
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
                    val previousTime = date.getTime()
                    var mutatedSolution = mutate(solution, operator, paramters)

                    if(solutionIndex.addSolution(mutatedSolution)){
                        //if solution already present, do not add it to pareto Front
                        front.add(mutatedSolution)

                        //create trace model


                        val newStep = _executionModelFactory!!.createStep()
                        newStep.startTime = previousTime
                        newStep.endTime = date.getTime()
                        newStep.generationNumber = front.size
                        val modelSolution = _executionModelFactory!!.createSolution()
                        newStep.addSolutions(modelSolution)
                        for(fitness in mutatedSolution.getFitnesses()){
                            val newScore = _executionModelFactory!!.createScore()
                            newScore.fitness = _executionModel!!.findFitnessByID(fitness)!!
                            newScore.value = mutatedSolution.getScoreForFitness(fitness)!!
                            newScore.name = newScore.fitness!!.name
                            modelSolution.addScores(newScore)
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
                        currentRun!!.addSteps(newStep)


                    }
                    if(solutionIndex.getNumberOfSolution() > _maxGeneration){
                        return;
                    }
                    enumeratedValuesIndice.put(keyName, enumeratedValuesIndice.get(keyName)!! + 1)
                }
                for(variable in enumerationVariables){
                    enumeratedValuesIndice.set(variable.name, 0)
                }
            }
        }
    }


    public override fun solve(): List<Solution<A>> {
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
            currentRun!!.algName = "fullsearch";
            _executionModel!!.addRuns(currentRun!!);
            currentRun!!.startTime = Date().getTime();
        }
        for(fitness in _fitnesses){
            if(_executionModel != null && _executionModel!!.findFitnessByID(fitness.javaClass.getSimpleName()) == null){
                val newFitness = _executionModelFactory!!.createFitness()
                newFitness.name = fitness.javaClass.getSimpleName()
                _executionModel!!.addFitness(newFitness)
            }
        }


        modelCompare = _populationFactory!!.getModelCompare()
        modelCloner = _populationFactory!!.getCloner()
        event2Trace = Event2Trace(modelCompare!!)
        solutionIndex.clear()
        solutionIndex.modelCompare = modelCompare
        var population = _populationFactory!!.createPopulation();
        for(initElem in population){
            val defaultSolution = DefaultSolution(initElem, GenerationContext(null, initElem, initElem, modelCompare!!.createSequence(), null))
            var previousNb = solutionIndex.getNumberOfSolution();
            computeStep(defaultSolution)
            if(solutionIndex.getNumberOfSolution() >= _maxGeneration || previousNb == solutionIndex.getNumberOfSolution()){
                return buildSolutions();
            }
        }

        //Front is ready next step
        while(solutionIndex.getNumberOfSolution() < _maxGeneration){
            val clonedFront = ArrayList<Solution<A>>()
            clonedFront.addAll(front)
            front.clear()
            for(sol in clonedFront){
                var previousNb = solutionIndex.getNumberOfSolution();
                computeStep(sol)
                if(solutionIndex.getNumberOfSolution() >= _maxGeneration || previousNb == solutionIndex.getNumberOfSolution()){
                    return buildSolutions();
                }
            }
        }
        return buildSolutions();
    }

    private fun buildSolutions(): List<Solution<A>> {
        //TODO SORT using comparator
        return solutionIndex.getAllSolutions()
    }

}