/* Copyright 2009-2012 David Hadka
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
package org.moeaframework.analysis.sensitivity;

import org.moeaframework.core.EpsilonBoxDominanceArchive;
import org.moeaframework.core.Population;
import org.moeaframework.core.Problem;
import org.moeaframework.core.comparator.EpsilonBoxDominanceComparator;

/**
 * Provides &epsilon; values for algorithms using &epsilon;-dominance archives
 * on the standard test problems.
 */
public class EpsilonHelper {
	
	/**
	 * Private constructor to prevent instantiation.
	 */
	private EpsilonHelper() {
		super();
	}

	/**
	 * Returns the &epsilon; value used for the standard problems in sensitivity
	 * analysis.  If the problem is not recognized, the default &epsilon; value
	 * of {@code 0.01} is returned.
	 * 
	 * @param problem the problem
	 * @return the &epsilon; value used for the standard problems in sensitivity
	 *         analysis
	 */
	public static double getEpsilon(Problem problem) {
		String name = problem.getName();
		int numberOfObjectives = problem.getNumberOfObjectives();
		
		if (name.startsWith("DTLZ")) {
			return numberOfObjectives == 2 ? 0.01 : 
					numberOfObjectives == 3 ? 0.05 :
						0.1 + 0.05*(numberOfObjectives-3);
		} else if (name.startsWith("UF")) {
			if (name.equals("UF1")) {
				return 0.001;
			} else if (name.equals("UF3")) {
				return 0.0008;
			} else if (name.equals("UF5")) {
				return 0.000001;
			} else if (name.equals("UF6")) {
				return 0.000001;
			} else if (name.equals("UF8")) {
				return 0.0045;
			} else if (name.equals("UF9")) {
				return 0.008;
			} else if (name.equals("UF10")) {
				return 0.001;
			} else {
				if (numberOfObjectives == 2) {
					return 0.005;
				} else if (numberOfObjectives == 3) {
					return 0.07;
				} else if (numberOfObjectives == 5) {
					return 0.2;
				} else {
					throw new IllegalArgumentException(
							"number of objectives not supported");
				}
			}
		} else {
			return 0.01;
		}
	}
	
	/**
	 * Converts the population to an {@link EpsilonBoxDominanceArchive} with
	 * the given &epsilon; values.  To prevent unnecessary computations, this
	 * conversion only occurs if the original population is not an
	 * {@code EpsilonBoxDominanceArchive} and does not have matching &epsilon;
	 * values.
	 * 
	 * @param population the population to convert
	 * @param epsilon the &epsilon; values
	 * @return the population converted to an {@code EpsilonBoxDominanceArchive}
	 *         with the given &epsilon; values
	 */
	public static EpsilonBoxDominanceArchive convert(Population population, 
			double[] epsilon) {
		boolean isSameEpsilon = false;

		//check if population already has the correct epsilons
		if (population instanceof EpsilonBoxDominanceArchive) {
			EpsilonBoxDominanceArchive result =
					(EpsilonBoxDominanceArchive)population;
			EpsilonBoxDominanceComparator comparator = 
					result.getComparator();

			isSameEpsilon = true;

			for (int i=0; i<epsilon.length; i++) {
				if (epsilon[i] != comparator.getEpsilon(i)) {
					isSameEpsilon = false;
					break;
				}
			}
		}

		//apply epsilons only if necessary
		if (isSameEpsilon) {
			return (EpsilonBoxDominanceArchive)population;
		} else {
			return new EpsilonBoxDominanceArchive(epsilon, population);
		}	
	}
}
