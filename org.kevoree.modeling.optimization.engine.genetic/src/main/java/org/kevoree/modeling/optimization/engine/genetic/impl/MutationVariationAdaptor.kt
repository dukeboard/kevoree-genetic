package org.kevoree.modeling.optimization.engine.genetic.impl;

import org.moeaframework.core.Solution
import org.moeaframework.core.Variation
import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.mutation.MutationOperator
import org.kevoree.modeling.optimization.api.mutation.MutationParameters
import org.kevoree.modeling.optimization.api.mutation.QueryVar
import org.kevoree.modeling.optimization.api.mutation.EnumVar
import java.util.Random
import org.kevoree.modeling.optimization.api.mutation.MutationVariable
import org.kevoree.modeling.optimization.engine.genetic.GeneticEngine

/**
 * Created by duke on 07/08/13.
 */
public class MutationVariationAdaptor<A : KMFContainer>(val operator: MutationOperator<A>, val geneticEngine: GeneticEngine<A>) : Variation, MutationOperator<A> {

    override fun enumerateVariables(model: A): List<MutationVariable> {
        return operator.enumerateVariables(model)
    }
    override fun mutate(model: A, params: MutationParameters) {
        return operator.mutate(model, params)
    }
    public override fun getArity(): Int {
        return 1;
    }

    private val random = Random()

    public override fun evolve(parents: Array<out Solution>?): Array<Solution>? {
        try {
            var previousSolution = parents?.get(0)
            var clonedSolution = previousSolution?.copy()
            var clonedVar = clonedSolution?.getVariable(0) as ModelVariable<A>;
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
            if(previousSolution != null){
                for(listener in geneticEngine.solutionMutationListeners){
                    listener.process(previousSolution as org.kevoree.modeling.optimization.api.Solution<A>, result as org.kevoree.modeling.optimization.api.Solution<A>)
                }
            }

            return result
        } catch (e: Throwable) {
            e.printStackTrace();
            return null;
        }
    }

}
