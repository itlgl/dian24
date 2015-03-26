package lgl.dian24;

import java.util.Set;

public class Dian24 {
	/**
	 * 取出数组中两个数做加减乘除操作，结果和剩余的数字再组成一个数组，循环操作，直到数组只有一个数字，判断是不是24
	 * 在进行运算时，如果是加和乘操作，是没有先后顺序的，所以此时只需要做一遍，条件为i<j 如果是乘和除操作，外面不用加括号
	 * 判断数组中是否有重复数字，可以避免重复结果，去除i和j区间内是有和i、j重复的数字的情况
	 * 
	 * @param nums
	 */
	public static void dian24(Num[] nums, Set<String> resultList) { // polynomials
																	// 多项式
		if (nums.length == 1) {
			if (nums[0].number == 24) {
				// System.out.println(nums[0].polynomial + "=24");
				resultList.add(nums[0].polynomial + "=24");
			}
		} else {
			for (int i = 0; i < nums.length; i++) {
				for (int j = 0; j < nums.length; j++) {
					if (i == j) {
						continue;
					}

					Num[] next = new Num[nums.length - 1];
					int index = 1;
					for (int k = 0; k < nums.length; k++) {
						if (k == i || k == j) {
							continue;
						}
						next[index++] = nums[k];
					}
					if (i < j) {// 去除重复，因为加和乘没有先后顺序
						next[0] = Num.operat(nums[i], nums[j], '+');
						dian24(next, resultList);
					}
					next[0] = Num.operat(nums[i], nums[j], '-');
					dian24(next, resultList);
					if (i < j) {
						next[0] = Num.operat(nums[i], nums[j], '*');
						dian24(next, resultList);
					}
					next[0] = Num.operat(nums[i], nums[j], '/');
					dian24(next, resultList);
				}
			}
		}
	}

	static class Num {
		float number;
		String polynomial;// number运算过程字符串
		char operation; // 上一次的操作符，默认没有(0)

		public Num() {
		}

		public Num(float number) {
			this.number = number;
			this.polynomial = String.valueOf((int) number);
			operation = 0;
		}

		// test
		public Num(float number, int index) {
			this.number = number;
			this.polynomial = String.valueOf((int) number + "(" + index + ")");
			operation = 0;
		}

		public static Num operat(Num num1, Num num2, char operation) {
			Num num = new Num();
			boolean flag1 = false, flag2 = false;// 是否需要加括号的标志位
			String num1Poly, num2Poly;
			switch (operation) {
			case '+':
				num.number = num1.number + num2.number;
				break;
			case '-':
				num.number = num1.number - num2.number;
				if (num2.operation == '+' || num2.operation == '-') {
					flag2 = true;
				}
				break;
			case '*':
				num.number = num1.number * num2.number;
				if (num1.operation == '-' || num1.operation == '+') {
					flag1 = true;
				}
				if (num2.operation == '-' || num2.operation == '+') {
					flag2 = true;
				}
				break;
			case '/':
				num.number = num1.number / num2.number;
				if (num1.operation == '-' || num1.operation == '+') {
					flag1 = true;
				}
				if (num2.operation == '-' || num2.operation == '+'
						|| num2.operation == '*' || num2.operation == '/') {
					flag2 = true;
				}
				break;
			}
			if (flag1) {
				num1Poly = "(" + num1.polynomial + ")";
			} else {
				num1Poly = num1.polynomial;
			}
			if (flag2) {
				num2Poly = "(" + num2.polynomial + ")";
			} else {
				num2Poly = num2.polynomial;
			}
			num.operation = operation;
			num.polynomial = num1Poly + operation + num2Poly;
			return num;
		}
	}
}
