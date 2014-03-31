package org.kevoree.modeling.optimization.engine.genetic.impl

import org.moeaframework.core.Solution
import org.kevoree.modeling.optimization.engine.genetic.GeneticEngine
import org.moeaframework.core.Variation
import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.mutation.MutationParameters
import org.kevoree.modeling.optimization.api.mutation.QueryVar
import org.kevoree.modeling.optimization.api.mutation.EnumVar
import java.util.Random
import org.kevoree.genetic.framework.internal.ModelOptimizationProblem

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 04/09/13
 * Time: 17:54
 */
class CompoundVariation<A : KMFContainer>(val engine: GeneticEngine<A>, val problem : ModelOptimizationProblem<A>) : Variation {

    override fun getArity(): Int {
        return 1
    }

    private val random = Random()

    public override fun evolve(parents: Array<out Solution>?): Array<Solution>? {
        val previousSolution = parents!!.get(0)
        val operator = engine.mutationSelector.select(previousSolution as org.kevoree.modeling.optimization.api.solution.Solution<A>)
        try {
            var clonedSolution = previousSolution.copy()!!
            var clonedVar = clonedSolution.getVariable(0) as ModelVariable<A>;
            var variables = operator.enumerateVariables(clonedVar.model)
            var params = MutationParameters()
            //random get
            for(variable in variables){
                when(variable) {
                    is QueryVar -> {
                        var queryResult = clonedVar.model.selectByQuery(variable.query)
                        if(!queryResult.isEmpty()){
                            var indice = random.nextInt(queryResult.size())
                            params.setParam(variable.name, queryResult.get(indice))
                        }
                    }
                    is EnumVar -> {
                        var indice = random.nextInt(variable.elements.size())
                        params.setParam(variable.name, variable.elements.get(indice))
                    }
                    else -> {
                        throw Exception("Unknow Mutator Variable (Enum/Query)" + variable)
                    }
                }
            }
            operator.mutate(clonedVar.model, params);
            val result = Array<Solution>(1, { i ->
                clonedSolution!!
            });
            clonedVar.context.operator = operator
            //call all solution mutation listener

            //pre evalute solution
            problem.evaluate(clonedSolution!!)
            if(previousSolution as?Solution !=null){
                for(listener in engine.solutionMutationListeners){
                    listener.process(previousSolution as org.kevoree.modeling.optimization.api.solution.Solution<A>, clonedSolution as org.kevoree.modeling.optimization.api.solution.Solution<A>)
                }
            }
            return result
        } catch (e: Throwable) {
            e.printStackTrace();
            return null;
        }
    }
}