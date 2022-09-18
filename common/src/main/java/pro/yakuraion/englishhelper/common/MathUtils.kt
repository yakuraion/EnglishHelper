package pro.yakuraion.englishhelper.common

infix fun Int.pow(x: Int): Int = this.toBigInteger().pow(x).toInt()
