package com.hades.example.leankotlin.concepts._11_annotations

// https://kotlinlang.org/docs/annotations.html
// Annotations

fun main() {

}

/**
 * Usage
 */
@Target(
    AnnotationTarget.CLASS /*1*/,
    AnnotationTarget.FUNCTION /*2*/,
    AnnotationTarget.VALUE_PARAMETER /*3*/,
    AnnotationTarget.TYPE,
    AnnotationTarget.EXPRESSION/*4*/,
    AnnotationTarget.PROPERTY /*5*/
)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class Fancy {

}

@Fancy /*1*/
class Foo {
    /*5*/
    @Fancy
    val x: Int? = null

    @Fancy /*2*/
    fun baz(@Fancy /*3*/ foo: Int): Int {
        return (@Fancy  /*4*/1)
    }
}