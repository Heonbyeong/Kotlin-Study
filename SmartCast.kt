package SmartCast

import kotlin.IllegalArgumentException

interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr

// if 중첩
//fun eval(e: Expr): Int {
//    if(e is Num){
//        val n = e as Num
//        return n.value
//    }
//    if(e is Sum){
//        return eval(e.right) + eval(e.left)
//    }
//    throw IllegalArgumentException("Unknown expression")
//}

// if 중첩 대신 when 사용
fun eval(e: Expr) : Int = when(e){
    is Num -> e.value
    is Sum -> eval(e.right) + eval(e.left)
    else -> throw IllegalArgumentException("Unknown expression")
}

// 분기에서 블록 사용
fun evalWithLogging(e: Expr): Int = when(e) {
    is Num -> {
        println("num: $(e.value)")
        e.value
    }
    is Sum -> {
        val left = evalWithLogging(e.left)
        val right = evalWithLogging(e.right)
        println("sum: $left + $right")
        left + right
    }
    else -> throw IllegalArgumentException("Unknown expression")
}

fun main() {
    println(eval(Sum(Sum(Num(1), Num(2)), Num(4))))
    println()
    println(evalWithLogging(Sum(Sum(Num(1), Num(2)), Num(4))))
}