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
package org.moeaframework.util;

import org.apache.commons.math.util.MathUtils;

/**
 * Defines mathematical operations that are applied to arrays.
 * 
 * @deprecated Will be removed in version 2.0
 */
@Deprecated
public class ArrayMath {

	/**
	 * Private constructor to prevent instantiation.
	 * 
	 * @deprecated Will be removed in version 2.0
	 */
	@Deprecated
	private ArrayMath() {
		super();
	}

	/**
	 * Returns the sum of all values in the specified array.
	 * 
	 * @param array the array of values
	 * @return the sum of all values in the specified array
	 * @throws ArithmeticException if the result can not be represented as an
	 *         {@code int}
	 * @deprecated Will be removed in version 2.0; only used in one location,
	 *             and the sum can be calculated alongside the array
	 */
	@Deprecated
	public static int sum(int[] array) {
		int sum = 0;

		for (int value : array) {
			sum = MathUtils.addAndCheck(sum, value);
		}

		return sum;
	}

	/**
	 * Returns the sum of all values in the specified array.
	 * 
	 * @param array the array of values
	 * @return the sum of all values in the specified array
	 * @deprecated Will be removed in version 2.0; should use
	 *             StatUtils#sum(double[]) instead
	 */
	@Deprecated
	public static double sum(double[] array) {
		double sum = 0.0;

		for (double value : array) {
			sum += value;
		}

		return sum;
	}

	/**
	 * Returns the minimum value in the specified array.
	 * 
	 * @param array the array of values
	 * @return the minimum value in the specified array
	 * @throws IllegalArgumentException if the array is empty
	 * @deprecated Will be removed in version 2.0; should use 
	 *             StatUtils#min(double[]) instead
	 */
	@Deprecated
	public static double min(double[] array) {
		if (array.length == 0) {
			throw new IllegalArgumentException("no minimum element in empty array");
		}

		double min = Double.POSITIVE_INFINITY;

		for (double value : array) {
			min = Math.min(min, value);
		}

		return min;
	}

	/**
	 * Returns the maximum value in the specified array.
	 * 
	 * @param array the array of values
	 * @return the maximum value in the specified array
	 * @throws IllegalArgumentException if the array is empty
	 * @deprecated Will be removed in version 2.0; should use 
	 *             StatUtils#min(double[]) instead
	 */
	@Deprecated
	public static double max(double[] array) {
		if (array.length == 0) {
			throw new IllegalArgumentException("no maximum element in empty array");
		}

		double max = Double.NEGATIVE_INFINITY;

		for (double value : array) {
			max = Math.max(max, value);
		}

		return max;
	}

}
