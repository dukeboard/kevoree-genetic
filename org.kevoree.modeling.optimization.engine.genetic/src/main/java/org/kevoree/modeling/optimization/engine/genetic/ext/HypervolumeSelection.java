
package org.kevoree.modeling.optimization.engine.genetic.ext;

import org.moeaframework.core.Population;
import org.moeaframework.core.Selection;
import org.moeaframework.core.Solution;


public class HypervolumeSelection implements Selection {

    private final HypervolumeComparator comparator;

    public HypervolumeSelection(HypervolumeComparator comparator) {
        this.comparator = comparator;
    }

    @Override
    public Solution[] select(int arity, Population population) {
        //determine composition of next population
        Population offspring = new Population();
        Solution[] solution = new Solution[arity];
        for (int i = 0; i < population.size(); i++) {
            int result = comparator.compare(population.get(i), population.get(population.size() - i));
            while (offspring.size() < arity) {
                if (result < 0) {
                    offspring.add(population.get(i));
                } else if (result > 0) {
                    offspring.add(population.get(population.size() - i));
                } else {
                    offspring.add(population.get(i));
                    offspring.add(population.get(population.size() - i));
                }
            }
        }
        for (int i = 0; i < arity; i++) {
            solution[i] = population.get(i);
        }
        return solution;
    }

}
