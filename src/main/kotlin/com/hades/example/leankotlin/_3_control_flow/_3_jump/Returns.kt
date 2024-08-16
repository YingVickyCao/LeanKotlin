package com.hades.example.leankotlin._3_control_flow._3_jump

fun main() {
    printName("Li Ming")
    println(getSum(1, 4))
    foo()
    foo2()
    foo3()
    foo4()
    foo5()
}

// returns : return-expression returns from the nearest enclosing function
// unlabeled return,START
fun printName(p: String) {
    val s = p ?: return  // return nothing
}

fun getSum(n1: Int, n2: Int): Int {
    return n1 + n2  // return a value
}

fun foo() {
    println("Example : foo")/*
    1
    2
     */
    // non-local returns are supported only for lambda expressions passed to inline functions.
    listOf(1, 2, 3, 4, 5).forEach {// forEach is a lambda expression
        if (it == 3) {
            return      // non-local return directly to the caller of foo
        }
        println(it)
    }
    println("This is not unreachable")
}
// unlabeled return,END

// labeled return,START
// returns only from the lambda expression by using an explicit label
fun foo2() {
    println("Example : foo2")/*
    1
    2
    4
    5
     */
    listOf(1, 2, 3, 4, 5).forEach lit@{ // "{" after "lit@" is a lambda expression
        if (it == 3) {
            // lit is an explicit label
            //  this "return@lit"  == continue
            return@lit  // local return to the caller of lambda - foreach loop
        }
        println(it)
    }
    println("done with explicit label")
}

// returns only from the lambda expression by using an implicit label
fun foo3() {
    println("Example : foo3")/*
    1
    2
    4
    5
     */
    listOf(1, 2, 3, 4, 5).forEach { // forEach is a lambda expression
        if (it == 3) {
            // forEach is an implicit label
            //  this "return@forEach"  == continue
            return@forEach  // local return to the caller of lambda - foreach loop
        }
        println(it)
    }
    println("done with implicit label")
}

// A return statement in an anonymous function will return from the anonymous function itself.
fun foo4() {
    println("Example : foo4")/*
    1
    2
    4
    5
     */
    listOf(1, 2, 3, 4, 5).forEach(fun(value: Int) { // fun is an anonymous function
        if (value == 3) {
            return  // local return to the caller of the anonymous function - the forEach loop
        }
        println(value)
    })
    println("done with anonymous function")
}

// returns only from the function block by using an explicit label
fun foo5() {
    println("Example : foo5")/*
    1
    2
    */
    run loop@{ // "{" after loop is a function block
        listOf(1, 2, 3, 4, 5).forEach {// forEach is an anonymous function
            if (it == 3) {
                // by adding another nesting lambda and non-locally returning from it, this "return@loop" == break
                return@loop  // non-local return  from the lambda passed to run
            }
            println(it)
        }
    }
    println("done with nested loop")
}
// labeled return,END