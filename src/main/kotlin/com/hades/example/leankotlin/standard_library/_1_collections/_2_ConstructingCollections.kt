package com.hades.example.leankotlin.standard_library._1_collections

import java.util.ArrayList
import java.util.HashSet
import java.util.LinkedList

// https://kotlinlang.org/docs/constructing-collections.html
// Constructing collections


/**
 * Construct from elements
 */

private fun test1() {
    val numbersSet = setOf("one", "two") // compiler detects the element type automatically
    val numbersSet2 = setOf<String>("one", "two")
    val numbersSet3 = mutableSetOf<String>("one", "two") // specify the type explicitly
    val numbersSet4 = mutableSetOf<String>() // specify the type explicitly

    val numbersMap = mapOf("key1" to 1, "key2" to 2) // // compiler detects the element type automatically
    // If performance isn't critical, recommend to use like this way
    val numbersMap2 = mapOf<String, Int>("key1" to 1, "key2" to 2) // specify the type explicitly
    // To avoid excessive memory issue, use apply way
    val numbersMap3 = mutableMapOf<String, Int>().apply { // specify the type explicitly
        this["key1"] = 1;this["key2"] = 2
        this["key3"] = 3
    }
}


/**
 * Create with collection builder functions
 */

private fun test2() {
    // buildSet / buildList / buildMap  - create a new mutable collection of the typ, and return a read-only collection with same elements.
    val map: Map<String, Int> = buildMap {
        put("a", 1)
        put("b", 0)
        put("c", 4)
    }
    println(map) // {a=1, b=0, c=4}

    val set: Set<String> = buildSet {
        add("one")
        add("two");add("three")
    }
    println(set) // [one, two, three]

    val list: List<String> = buildList {
        add("one")
        add("two")
    }
    println(list) // [one, two]
}

/**
 * Empty collections
 */

private fun test3() {
    // Use emptyList / emptySet / emptyMap to create empty collections
    val emptyList: List<String> = emptyList<String>()
    println(emptyList)

    val emptySet: Set<String> = emptySet<String>()
    println(emptySet) // []

    val emptyMap: Map<String, Int> = emptyMap<String, Int>()
    println(emptyMap) // {}
}

/**
 * Initializer functions for lists
 */
private fun test4() {
    // For lists, there is a constructor-like function that takes the list size and the initializer function that defines the element value based on its index.
    val list: List<Int> = List(3, { it }) // [0, 1, 2]
    println(list)

    val doubledList: List<Int> = List(3, { it * 2 }) // or MutableList if you can change its value later
    println(doubledList) // [0, 2, 4]

    val mutableList: MutableList<Int> = MutableList(3, { it })
    println(mutableList) // [0, 1, 2]

    val doubledMutableList: MutableList<Int> = MutableList(3, { it * 2 })
    println(doubledMutableList) // [0, 2, 4]
}

/**
 * Concrete type constructors
 */

private fun test5() {
    // LinkedList is mutable list
    val linkedList = LinkedList<String>(listOf("one", "two", "three"))
    println(linkedList) // [one, two, three]
    linkedList.add("four") // [one, two, three, four]
    println(linkedList)

    // ArrayList is mutable list
    val arrayList = ArrayList<String>(listOf("one", "two", "three"))
    println(arrayList) // [one, two, three]
    arrayList.add("four")
    println(arrayList) // [one, two, three, four]

    // HashSet is a mutable set
    val hashSet = HashSet<String>(2)
    hashSet.add("one")
    hashSet.add("two")
    println(hashSet) // [two, one]

    val hashSet2: HashSet<String> = hashSetOf("one", "two")
    println(hashSet2) // [two, one]

    val map: HashMap<String, Int> = HashMap<String, Int>(2)
    map.put("k1", 1)
    map.put("k2", 2)
    println(map) // {k1=1, k2=2}
}

/**
 * Copy
 */
private fun test6() {
    fun test6_example1() {
        class Person(var name: String) {
            override fun toString(): String {
                return "Person(name='$name')"
            }
        }

        val alice = Person("Alice")
        val sourceList = mutableListOf(alice, Person("Bob"))
        println(sourceList) // [Person(name='Alice'), Person(name='Bob')]

        val copyList = sourceList.toList()
        println(copyList) // [Person(name='Alice'), Person(name='Bob')]


        sourceList.add(Person("Charles"))
        println(sourceList) // [Person(name='Alice'), Person(name='Bob'), Person(name='Charles')]
        println(copyList) // [Person(name='Alice'), Person(name='Bob')]

        alice.name = "Alice2"
        println(sourceList) // [Person(name='Alice2'), Person(name='Bob'), Person(name='Charles')]
        println(copyList) // [Person(name='Alice2'), Person(name='Bob')]
    }

    fun test6_example2() {
        // convert collection to other type
        val sourceList = mutableListOf(1, 2, 3)
        val copySet = sourceList.toMutableSet() // List -> Set
        copySet.add(3)
        copySet.add(4)
        println(sourceList) // [1, 2, 3]
        println(copySet) // [1, 2, 3, 4]
    }

    fun test6_example3() {
        // create new reference to the same collection instance.
        val sourceList = mutableListOf(1, 2, 3)
        val referenceList = sourceList
        referenceList.add(4)
        println(sourceList.size) // 4
        println(referenceList.size) // 4
    }

    fun test6_example4() {
        val sourceList = mutableListOf(1, 2, 3)
        val referenceList: List<Int> = sourceList
//        referenceList.add(4) // compilation error
        sourceList.add(4)
        println(sourceList) // [1, 2, 3, 4]
        println(referenceList) // [1, 2, 3, 4]
    }
//    test6_example1()
//    test6_example2()
//    test6_example3()
    test6_example4()
}


/**
 * Invoke functions on other collections
 */
fun main() {
    test6()
}