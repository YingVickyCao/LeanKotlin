package com.hades.example.leankotlin.standard_library._1_collections

// https://kotlinlang.org/docs/collection-grouping.html
// Group elements

/**
 * Grouping
 */

private fun test1() {
    fun test1_example1() {
        // groupBy() : grouping collection elements. returns a map,In this map, each key is the lambda result, and the corresponding value is the List of elements on which this result is returned.
        val numbers = listOf("one", "two", "three", "four", "five")

        // Group the strings by the first letter using groupBy
        val groupByFirstLetter: Map<String, List<String>> = numbers.groupBy { it.first().uppercase() }
        // {O=[one], T=[two, three], F=[four, five]}
        println(groupByFirstLetter)

        for ((key, value) in groupByFirstLetter) {
//        key:O, value=[one]
//        key:T, value=[two, three]
//        key:F, value=[four, five]
            println("key:$key, value=$value")
        }
    }

    fun test1_example2() {
        // group elements and then apply an operation to all groups at one time, use the function groupingBy(). It returns an instance of the Grouping type. The Grouping instance lets you apply operations to all groups in a lazy manner: the groups are actually built right before the operation execution.
        // The Grouping instance lets you apply operations to all groups in a lazy manner: the groups are actually built right before the operation execution.
        // eachCount()
        // fold()
        // reduce()
        val numbers = listOf("one", "two", "three", "four", "five")

        // groupingBy(), eachCount()
        val grouped: Map<Char, Int> = numbers.groupingBy { it.first() }.eachCount() // {o=1, t=2, f=2}
        println(grouped)

        for ((key, value) in grouped) {
            // key:o, value=1
            //key:t, value=2
            //key:f, value=2
            println("key:$key, value=$value")
        }

//        val groupingBy: Grouping<String, Char> = numbers.groupingBy { it.first() }
//        // $test1$test1_example2$$inlined$groupingBy$2@30dae81
//        println(groupingBy)
//        // {o=1, t=2, f=2}
//        println(groupingBy.eachCount())

        // fold()
        val folded = numbers.groupingBy { it }.fold("element") { r, t ->
            "$r $t"
        }
        println(folded) // {one=element one, two=element two, three=element three, four=element four, five=element five}

        // reduce()
        val reduced = numbers.groupingBy { it.uppercase() }.reduce { a, b, c -> a + b + c }
        println(reduced) // {ONE=one, TWO=two, THREE=three, FOUR=four, FIVE=five}

        val numbers2 = listOf(1, 4, 5, 6, 7, 8, 9)
        // aggregate()
        val aggregated = numbers2.groupingBy { it%3 }.aggregate { key, accumulator: StringBuilder?, element, first ->
            if (first) {
                StringBuilder().append(key).append(":").append(element)
            } else {
                accumulator?.append("-")?.append(element)
            }
        }
        println(aggregated) // {1=1:1-4-7, 2=2:5-8, 0=0:6-9}
        println(aggregated.values) // [1:1-4-7, 2:5-8, 0:6-9]
    }

//    test1_example1()
    test1_example2()
}

fun main() {
    test1()
}