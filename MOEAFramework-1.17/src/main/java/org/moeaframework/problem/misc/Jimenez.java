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
package org.moeaframework.problem.misc;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.RealVariable;
import org.moeaframework.problem.AbstractProblem;

/**
 * The Jimenez problem.
 * <p>
 * Properties:
 * <ul>
 *   <li>Connected and symmetric Pareto set
 *   <li>Convex Pareto front
 *   <li>Constrained
 *   <li>Maximization (objectives are negated)
 * </ul>
 * <p>
 * References:
 * <ol>
 *   <li>Van Veldhuizen, D. A (1999).  "Multiobjective Evolutionary Algorithms: 
 *       Classifications, Analyses, and New Innovations."  Air Force Institute
 *       of Technology, Ph.D. Thesis, Appendix B.
 * </ol>
 */
public class Jimenez extends AbstractProblem {

	/**
	 * Constructs the Jimenez problem.
	 */
	public Jimenez() {
		super(2, 2, 4);
	}

	@Override
	public void evaluate(Solution solution) {
		double x = ((RealVariable)solution.getVariable(0)).getValue();
		double y = ((RealVariable)solution.getVariable(1)).getValue();
		double f1 = 5.0*x + 3.0*y;
		double f2 = 2.0*x + 8.0*y;
		double c1 = x + 4.0*y - 100.0;
		double c2 = 3.0*x + 2.0*y - 150.0;
		double c3 = 200.0 - 5.0*x - 3.0*y;
		double c4 = 75.0 - 2.0*x - 8.0*y;
		
		solution.setObjective(0, -f1);
		solution.setObjective(1, -f2);
		solution.setConstraint(0, c1 <= 0.0 ? 0.0 : c1);
		solution.setConstraint(1, c2 <= 0.0 ? 0.0 : c2);
		solution.setConstraint(2, c3 <= 0.0 ? 0.0 : c3);
		solution.setConstraint(3, c4 <= 0.0 ? 0.0 : c4);
	}

	@Override
	public Solution newSolution() {
		Solution solution = new Solution(2, 2, 4);
		
		solution.setVariable(0, new RealVariable(0.0, 50.0));
		solution.setVariable(1, new RealVariable(0.0, 50.0));
		
		return solution;
	}

}
