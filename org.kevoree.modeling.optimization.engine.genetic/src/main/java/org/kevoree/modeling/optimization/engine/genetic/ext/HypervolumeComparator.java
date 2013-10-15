/* Copyright 2009-2013 David Hadka
 *
 * This file is part of the MOEA Framework.
 *
 * The MOEA Framework is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * The MOEA Framework is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the MOEA Framework.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.kevoree.modeling.optimization.engine.genetic.ext;

import org.moeaframework.core.comparator.DominanceComparator;
import org.moeaframework.core.fitness.IndicatorFitnessEvaluator;
import java.io.Serializable;
import java.util.Comparator;
import org.moeaframework.core.Problem;
import org.moeaframework.core.comparator.ParetoDominanceComparator;
import org.moeaframework.core.Solution;

public class HypervolumeComparator extends IndicatorFitnessEvaluator implements DominanceComparator,
        Comparator<Solution>, Serializable {

    public static final double rho = 2.0;

    public HypervolumeComparator(Problem problem) {
        super(problem);
    }

    @Override
    protected double calculateIndicator(Solution solution1, Solution solution2) {
        return 0;
    }

    private static final ParetoDominanceComparator dominanceComparator =
            new ParetoDominanceComparator();

    public int compare(Solution solution1, Solution solution2, int d) {
        double hypervolume1 = calculateHypervolume(solution1,solution2, getProblem()
                .getNumberOfObjectives());
        double hypervolume2 = calculateHypervolume(solution2, solution1,getProblem()
                .getNumberOfObjectives());

        if (hypervolume1 > hypervolume2) {
            return -1;
        } else if (hypervolume2 < hypervolume2) {
            return 1;
        } else {
            return 0;
        }
    }

    protected double calculateHypervolume(Solution solution1,
                                          Solution solution2, int d) {
        double max = rho;
        double a = solution1.getObjective(d - 1);
        double b = max;

        if (solution2 != null) {
            b = solution2.getObjective(d - 1);
        }

        double volume = 0.0;

        if (d == 1) {
            if (a < b) {
                volume = (b - a) / rho;
            }
        } else {
            if (a < b) {
                volume = calculateHypervolume(solution1, null, d - 1) *
                        (b - a) / rho
                        + calculateHypervolume(solution1, solution2, d - 1) *
                        (max - b) / rho;
            } else {
                volume = calculateHypervolume(solution1, solution2, d - 1) *
                        (max - b) / rho;
            }
        }

        return volume;
    }


    @Override
    public int compare(Solution solution1, Solution solution2) {
        return compare(solution1,solution2,getProblem().getNumberOfObjectives());  //To change body of implemented methods use File | Settings | File Templates.
    }
}
