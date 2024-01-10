package com.mboxvn.utils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class MathFormulaGenerator {

	static final int[] Numbers = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

	static final char[] Operator = "+-*/".toCharArray();

	public static String gen() {

		ThreadLocalRandom random = ThreadLocalRandom.current();

		char operator = Operator[random.nextInt(Operator.length)];

		if (operator == '+') {
			int v1 = Numbers[random.nextInt(Numbers.length)];
			int v2 = Numbers[random.nextInt(Numbers.length)];

			int val = v1 + v2;

			return v1 + " + " + v2 + " = ?#" + val;

		} else if (operator == '-') {

			int v1 = Numbers[random.nextInt(Numbers.length)];
			int v2 = Numbers[random.nextInt(Numbers.length)];

//			v1 = v1 > v2 ? v1 : v2; // 保证大减小

			int val = v1 - v2;

			return v1 + " - " + v2 + " = ?#" + val;
		} else if (operator == '*') {
			int v1 = Numbers[random.nextInt(Numbers.length)];
			int v2 = Numbers[random.nextInt(Numbers.length)];

			int val = v1 * v2;

			return v1 + " * " + v2 + " = ?#" + val;
		} else {
			int v1 = Numbers[random.nextInt(Numbers.length)];

			int[] arr = IntStream.of(Numbers).filter(item -> item != 0 && v1 % item == 0).toArray();

			int v2 = arr[random.nextInt(arr.length)];

			int val = v1 / v2;

			return v1 + " / " + v2 + " = ?#" + val;
		}
	}
}
