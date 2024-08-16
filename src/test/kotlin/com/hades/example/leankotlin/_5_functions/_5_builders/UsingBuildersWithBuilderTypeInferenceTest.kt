<<<<<<<< HEAD:src/test/kotlin/com/hades/example/leankotlin/concepts/_5_builders/UsingBuildersWithBuilderTypeInferenceTest.kt
package com.hades.example.leankotlin.concepts._5_builders
========
package com.hades.example.leankotlin._5_functions._5_builders
>>>>>>>> parent of 425f83e (refactor):src/test/kotlin/com/hades/example/leankotlin/_5_functions/_5_builders/UsingBuildersWithBuilderTypeInferenceTest.kt

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * https://kotlinlang.org/docs/using-builders-with-builder-inference.html
 */
class UsingBuildersWithBuilderTypeInferenceTest {
    // build is a builder lambda parameter of a function type with a receiver.
    fun <V> buildList(builder: MutableList<V>.() -> Unit) {

    }

    class ItemHolder<T> {
        private val items = mutableListOf<T>()
        fun addItem(x: T) {
            items.add(x)
        }

        fun getLastItem(): T? {
            return items.lastOrNull()
        }

        fun size(): Int {
            return items.size
        }
    }

    fun <T> ItemHolder<T>.addAllItems(list: List<T>) {
        list.forEach {
            addItem(it)
        }
    }

    // TODO:ItemHolder<T>.() -> Unit
    private fun <T> itemHolderBuilder(builder: ItemHolder<T>.() -> Unit): ItemHolder<T> {
        return ItemHolder<T>().apply(builder)
    }

    @Test
    fun test_Writing_own_builders() {
        // ItemHolder<T>
        val itemHolderBuilder1 = itemHolderBuilder {
            addItem("s1")
        }

        assertEquals(1, itemHolderBuilder1.size())
        assertEquals("s1", itemHolderBuilder1.getLastItem())

        // ItemHolder<T>
        val itemHolderBuilder2 = itemHolderBuilder {
            addAllItems(listOf("s2"))
        }
        assertEquals(1, itemHolderBuilder2.size())
        assertEquals("s2", itemHolderBuilder2.getLastItem())

        // ItemHolder<String?>
        val itemHolderBuilder3 = itemHolderBuilder {
            val lastItem: String? = getLastItem()
        }
        assertEquals(null, itemHolderBuilder3.getLastItem())
    }

    @Test
    fun test_Supported_features() {
        // Inferring several type arguments
        fun <K, V> myBuilder(builder: MutableMap<K, V>.() -> Unit): Map<K, V> {
            return mutableMapOf<K, V>().apply(builder)
        }

        // Map<String, Int>
        val result1 = myBuilder {
            put("s", 1)
        }
        assertEquals(1, result1.keys.size)
        assertEquals("s", result1.keys.last())

        // Inferring type arguments of several builder lambdas within one call including interdependent ones
        fun <K, V> myBuilder2(
            listBuilder: MutableList<V>.() -> Unit,
            mapBuilder: MutableMap<K, V>.() -> Unit
        ): Pair<List<V>, Map<K, V>> {
            return mutableListOf<V>().apply(listBuilder) to mutableMapOf<K, V>().apply(mapBuilder)
        }

//        val result2 = myBuilder2(
//            { add(1) }, { put("key", 2L) })
            // result has Pair<List<Int>, Map<String, Int>> type
    }
}