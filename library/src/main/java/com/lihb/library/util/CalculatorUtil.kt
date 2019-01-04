package com.lihb.library.util

import android.text.TextUtils
import java.math.BigDecimal

class CalculatorUtil {
    companion object {
        /**
         * 简单计算，只能计算加减乘除而且只有一步的运算公式
         * @param equation 要运算的公式
         * @param scale 如果时相除最大保留到小数点后多少位
         */
        fun simpleCalculator(equation: String?, scale: Int = 10): String {
            var finalString = "0"
            var finalScale = scale
            if (finalScale < 0) {
                finalScale = 10
            }
            if (!TextUtils.isEmpty(equation)) {
                equation!!
                var replace = equation.replace(" ", "")//去除公式中的所有空格

                //去除末尾的运算符
                while (replace.endsWith("+")
                        || replace.endsWith("-")
                        || replace.endsWith("*")
                        || replace.endsWith("/")) {
                    replace = replace.substring(0, replace.length - 1)
                }

                if (!TextUtils.isEmpty(replace)) {
                    //去掉末尾运算符和公式中所有空格后不为空才继续
                    finalString = replace
                    var isMinus = false //第一个数是否为负数
                    if (replace.startsWith("-", true)) {
                        isMinus = true
                        replace = replace.substring(1)
                    } else if (replace.startsWith("+", true)) {
                        replace = replace.substring(1)
                    }

                    if (!replace.contains("+")
                            && !replace.contains("-")
                            && !replace.contains("*")
                            && !replace.contains("/")
                    ) {
                        //如果到这里公式不包含运算符
//                        finalString = replace
                    } else {
                        if (replace.contains("+", true)) {
                            val addIndex = replace.indexOf("+")
                            var one = replace.subSequence(0, addIndex)
                            val two = replace.subSequence(addIndex + 1, replace.length)
                            if (isMinus) {
                                one = "-".plus(one)
                            }
                            val bigDecimalOne = BigDecimal(one.toString())
                            val bigDecimalTwo = BigDecimal(two.toString())
                            finalString = bigDecimalOne.add(bigDecimalTwo).toString()
                        } else if (replace.contains("-", true)) {
                            val addIndex = replace.indexOf("-")
                            var one = replace.subSequence(0, addIndex)
                            val two = replace.subSequence(addIndex + 1, replace.length)
                            if (isMinus) {
                                one = "-".plus(one)
                            }
                            val bigDecimalOne = BigDecimal(one.toString())
                            val bigDecimalTwo = BigDecimal(two.toString())
                            finalString = bigDecimalOne.subtract(bigDecimalTwo).toString()
                        } else if (replace.contains("*", true)) {
                            val addIndex = replace.indexOf("*")
                            var one = replace.subSequence(0, addIndex)
                            val two = replace.subSequence(addIndex + 1, replace.length)
                            if (isMinus) {
                                one = "-".plus(one)
                            }
                            val bigDecimalOne = BigDecimal(one.toString())
                            val bigDecimalTwo = BigDecimal(two.toString())
                            finalString = bigDecimalOne.multiply(bigDecimalTwo).toString()
                        } else if (replace.contains("/", true)) {
                            val addIndex = replace.indexOf("/")
                            val two = replace.subSequence(addIndex + 1, replace.length)
                            val bigDecimalTwo = BigDecimal(two.toString())
                            if (bigDecimalTwo.toDouble() != 0.toDouble()) {
                                //只有被除数不为0才计算，否则返回0
                                var one = replace.subSequence(0, addIndex)
                                if (isMinus) {
                                    one = "-".plus(one)
                                }
                                val bigDecimalOne = BigDecimal(one.toString())
                                finalString = try {
                                    bigDecimalOne.divide(bigDecimalTwo).toString()
                                } catch (e: ArithmeticException) {
                                    //只有除不尽才使用 finalScale
                                    bigDecimalOne.divide(bigDecimalTwo, finalScale, BigDecimal.ROUND_HALF_UP).toString()
                                }
                            } else {
                                //只有被除数为0,直接返回0
                                finalString = "0"
                            }
                        }
                    }
                }
            }
            return removeEndOfZero(finalString)
        }

        /**
         * 去掉末位的0或者小数点
         */
        private fun removeEndOfZero(string: String?): String {
            var finalString = string
            if (TextUtils.isEmpty(finalString)) {
                return "0"
            }

            while (
                    !TextUtils.isEmpty(finalString)
                    && finalString!!.contains(".")
                    && (finalString.endsWith("0") || finalString.endsWith("."))
            ) {
                finalString = finalString.substring(0, finalString.length - 1)
            }

            if (TextUtils.isEmpty(finalString)) {
                finalString = "0"
            }
            return finalString!!
        }
    }
}