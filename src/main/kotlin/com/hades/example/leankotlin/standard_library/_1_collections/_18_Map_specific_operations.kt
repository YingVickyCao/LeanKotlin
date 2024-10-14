package com.hades.example.leankotlin.standard_library._1_collections

import com.hades.example.leankotlin.concepts._1_types._2_data_type._1_basic_types._1_5_file_name._custom_class_name.f

/**
 * Retrieve keys and values
 */
private fun test1() {
    fun test1_example1() {
        val numbersMap = mapOf("one" to 1, "two" to 2, "three" to 3)
        println(numbersMap.get("one")) // 1
        println(numbersMap["one"]) // 1

        // getOrDefault() : return specific default value if the key is not found in the map
        println(numbersMap.getOrDefault("four", 10)) // 10

        // getOrElse() : return from the given lambda function if the key is not found in the map
        println(numbersMap.getOrElse("five") { 5 })

        // get() : return null if the key is not found in the map
        println(numbersMap.get("five")) // null

        // getValue() : throw an exception if the key is not found in the map
//        println(numbersMap.getValue("five")) // ava.util.NoSuchElementException: Key five is missing in the map.
    }

    fun test1_example2() {
        // key: retrieve all the keys of a map
        // values: retrieve all the values of a map
        val numbersMap = mapOf("one" to 1, "two" to 2, "three" to 3)
        println(numbersMap.keys) // [one, two, three]
        println(numbersMap.values) // [1, 2, 3]
    }
//    test1_example1()
    test1_example2()
}

/**
 * Filter
 */
private fun test2() {
    fun test2_example1() {
        val numbersMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key11" to 11)
        val filteredMap = numbersMap.filter { (key, value) -> key.endsWith("1") && value > 10 }
        println(filteredMap) // {key11=11}
    }

    fun test2_example2() {
        // filterKeys() : filter map by keys
        // filterValues() : filter map by values
        val numbersMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key11" to 11)
        val filteredKeysMap = numbersMap.filterKeys { it.endsWith("1") }
        println(filteredKeysMap) // {key1=1, key11=11}

        val filteredValuesMap = numbersMap.filterValues { it < 10 }
        println(filteredValuesMap) // {key1=1, key2=2, key3=3}
    }
//    test2_example1()
    test2_example2()
}

/**
 * Plus and minus operators
 */
private fun test3() {
    fun test3_example1() {
        // plus(+) : eturns a Map that contains elements of its both operands: a Map on the left and a Pair or another Map on the right.
        // When the right-hand side operand contains entries with keys present in the left-hand side Map, the result map contains the entries from the right side.
        val numbersMap = mapOf("one" to 1, "two" to 2, "three" to 3)
        println(numbersMap + Pair("four", 4))  // {one=1, two=2, three=3, four=4}
        println(numbersMap + Pair("one", 10)) // {one=10, two=2, three=3}
        println(numbersMap + mapOf("key1" to 5, "one" to 11)) // {key1=5, key2=2, key3=3, one=11}
    }

    fun test3_example2() {
        // minus(-) : creates a Map from entries of a Map on the left except those with keys from the right-hand side operand. So, the right-hand side operand can be either a single key or a collection of keys: list, set, and so on.
        val numbersMap = mapOf("one" to 1, "two" to 2, "three" to 3)
        println(numbersMap - "one") // {two=2, three=3}
        println(numbersMap - listOf("two", "four")) // {one=1, three=3}
    }
    test3_example1()
//    test3_example2()

    // For details on using plusAssign (+=) and minusAssign (-=) operators on mutable maps, see Map write operations below.
}

/**
 * Map write operations
 */

/**
 * Map write operations - Add and update entries
 */
private fun test4_1() {
    fun test4_1_example1() {
        // put() : add a new key-value pair to a mutable map.
        val numbersMap = mutableMapOf("one" to 1, "two" to 2)
//    numbersMap.put("three", 3)
        numbersMap["three"] = 3 // equal to put()
        println(numbersMap) // {one=1, two=2, three=3}
    }

    fun test4_1_example2() {
        // putAll : add multiple entries a a time
        val numbersMap = mutableMapOf("one" to 1, "two" to 2, "three" to 3)
        numbersMap.putAll(setOf("four" to 4, "five" to 5))
        println(numbersMap) // {one=1, two=2, three=3, four=4, five=5}
    }

    fun test4_1_example3() {
        // put() and putAll() overwrite the values if the given keys already exist in the map. Thus, you can use them to update values of map entries.
        val numbersMap = mutableMapOf("one" to 1, "two" to 2)
        val previousValue = numbersMap.put("one", 11)
        println(previousValue) // 1
        println(numbersMap) // {one=11, two=2}
    }

    fun test4_1_example4() {
        // plusAssign(+=) operator
        // [] operator alias for set()
        val numbersMap = mutableMapOf("one" to 1, "two" to 2)
        numbersMap["three"] = 3
        println(numbersMap) // {one=1, two=2, three=3}

        numbersMap += mapOf("four" to 4, "five" to 5)
        println(numbersMap) // {one=1, two=2, three=3, four=4, five=5}
    }
//    test4_1_example1()
//    test4_1_example2()
//    test4_1_example3()
    test4_1_example4()
}

/**
 * Map write operations - Remove entries
 */
private fun test4_2() {
    fun test4_2_example1() {
        val numbersMap = mutableMapOf("one" to 1, "two" to 2, "three" to 3)
        numbersMap.remove("one")
        println(numbersMap) // {two=2, three=3}

        numbersMap.remove("three", 4) // does not remove anything
        println(numbersMap) // {two=2, three=3}
    }

    fun test4_2_example2() {
        // remove(): also remove the entries from a mutable map by keys or values
        val numbersMap = mutableMapOf("one" to 1, "two" to 2, "three" to 3)
        numbersMap.keys.remove("one")
        println(numbersMap) // {two=2, three=3}

        numbersMap.values.remove(3)
        println(numbersMap) // {two=2}
    }

    fun test4_2_example3() {
        // minusAssign(-=) 
        val numbersMap = mutableMapOf("one" to 1, "two" to 2, "three" to 3)
        numbersMap -= "two"
        println(numbersMap) // {one=1, three=3}

        numbersMap -= "five" // does not remove anything
        println(numbersMap) // {one=1, three=3}
    }
    test4_2_example3()
}

fun main() {
//    test1()
//    test2()
//    test3()
//    test4_1()
    test4_2()
}