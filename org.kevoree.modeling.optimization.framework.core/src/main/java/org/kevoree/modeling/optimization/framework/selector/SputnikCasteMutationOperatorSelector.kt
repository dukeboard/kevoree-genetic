package org.kevoree.modeling.optimization.engine.genetic.selector

import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.mutation.MutationOperator
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 28/10/2013
 * Time: 18:29
 */
public class SputnikCasteMutationOperatorSelector<A : KMFContainer>(override val operators: List<MutationOperator<A>>, val probability2: Double) : SputnikElitistMutationOperatorSelector<A>(operators, probability2) {

    override fun updateSelectionProbability(fitness: FitnessFunction<A>) {
        val fitnessRanks = positiveRanking.get(fitness)
        if(fitnessRanks != null && !fitnessRanks.isEmpty()){
            var nbProba = fitnessRanks.size()
            for (r in fitnessRanks.values()) {
                r.selectionProbability = 1.0 / nbProba
            }
        }
    }


}

