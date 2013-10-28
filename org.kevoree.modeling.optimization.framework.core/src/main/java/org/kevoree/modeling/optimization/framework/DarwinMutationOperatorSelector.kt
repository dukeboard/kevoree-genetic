package org.kevoree.modeling.optimization.engine.genetic

import org.kevoree.modeling.optimization.api.mutation.MutationOperatorSelector
import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.mutation.MutationOperator
import org.kevoree.modeling.optimization.api.Solution
import org.kevoree.modeling.optimization.SolutionMutationListener
import java.util.Random
import java.util.HashMap

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 28/10/2013
 * Time: 18:29
 */
class DarwinMutationOperatorSelector<A : KMFContainer>(override val operators: List<MutationOperator<A>>, val probability: Double) : MutationOperatorSelector<A>, SolutionMutationListener<A> {

    val random = Random()
    val ranking = HashMap<String, HashMap<MutationOperator<A>, MutatorRanking<A>>>()
    val bestRanks = HashMap<String, MutationOperator<A>>()
    val bestRanking = HashMap<String, MutatorRanking<A>>()

    override fun process(previousSolution: Solution<A>, solution: Solution<A>) {
        for(fitness in solution.getFitnesses()){
            val currentScore = solution.getScoreForFitness(fitness)!!
            val previousScore = previousSolution.getScoreForFitness(fitness)!!
            val impactOnFitness = currentScore - previousScore
            val lastOperator = solution.context.operator!!
            val rankinValues: HashMap<MutationOperator<A>, MutatorRanking<A>> = ranking.getOrPut(fitness, {
                HashMap<MutationOperator<A>, MutatorRanking<A>>()
            })
            val currentRanking: MutatorRanking<A> = rankinValues.getOrPut(lastOperator, {
                MutatorRanking<A>(0.0, 0.0, 0)
            })
            currentRanking.sum = currentRanking.sum + impactOnFitness
            currentRanking.nbSelection = currentRanking.nbSelection + 1
            currentRanking.mean = currentRanking.sum / currentRanking.nbSelection
            //update best score
            val bestFoundedRanking = bestRanking.get(fitness)
            if(bestFoundedRanking == null){
                bestRanks.put(fitness, lastOperator)
                bestRanking.put(fitness, currentRanking)
            } else {
                if(bestFoundedRanking.mean < currentRanking.mean){
                    bestRanks.put(fitness, lastOperator)
                    bestRanking.put(fitness, currentRanking)
                }
            }
        }

    }

    override fun select(solution: Solution<A>): MutationOperator<A> {
        val randomNb = random.nextInt(100)
        if(randomNb < probability){
            val worstFitnessToFix = selectWorstCurrentFitness(solution)
            val currentBestOperator = bestRanks.get(worstFitnessToFix)
            if(currentBestOperator == null){
                //random
                var indice = random.nextInt(operators.size())
                return operators.get(indice)
            } else {
                return currentBestOperator
            }
        } else {
            //random selection
            var indice = random.nextInt(operators.size())
            return operators.get(indice)
        }
    }

    private fun selectWorstCurrentFitness(solution: Solution<A>): String {
        var worstScore = 0.0
        var worstName: String = ""
        for(fitness in solution.getFitnesses()){
            val loopScore = solution.getScoreForFitness(fitness)!!
            if(worstScore < solution.getScoreForFitness(fitness)!!){
                worstName = fitness
                worstScore = loopScore
            }
        }
        return worstName
    }

}

data class MutatorRanking<A : KMFContainer>(var mean: Double, var sum: Double, var nbSelection: Int)
