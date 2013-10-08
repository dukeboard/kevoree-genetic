
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
        Solution[] solution = new Solution[arity];
        //determine composition of next population
        //determine composition of next population
        Population offspring = new Population();

        for (int i = 0; i < population.size(); i++) {
            int result = comparator.compare(offspring.get(i), population.get(i));

            if (result < 0) {
                offspring.add(offspring.get(i));
            } else if (result > 0) {
                offspring.add(population.get(i));
            } else {
                offspring.add(offspring.get(i));
                offspring.add(population.get(i));
            }
        }
        return solution;
    }

}
