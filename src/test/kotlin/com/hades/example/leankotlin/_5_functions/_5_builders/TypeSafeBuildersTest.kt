package com.hades.example.leankotlin._5_functions._5_builders

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.text.StringBuilder

/**
 * Create html tags
 */
class TypeSafeBuildersTest {
    @DslMarker
    annotation class HtmlTagMarker

    interface Element {
        fun render(builder: StringBuilder, indent: String)
    }

    class TextElement(val text: String) : Element {
        override fun render(builder: StringBuilder, indent: String) {
            builder.append("$indent$text\n")
        }
    }

    @HtmlTagMarker
    abstract class Tag(val name: String) : Element {
        val children = arrayListOf<Element>()
        val attributes = hashMapOf<String, String>()

        protected fun <T : Element> initTag(tag: T, init: T.() -> Unit): T {
            tag.init()
            children.add(tag)
            return tag
        }

        override fun render(builder: StringBuilder, indent: String) {
            builder.append("$indent<$name${renderAttributes()}>\n")
            for (c in children) {
                c.render(builder, indent + "")
            }
            builder.append("$indent</$name>\n")
        }

        private fun renderAttributes(): String {
            val builder = StringBuilder()
            for ((attr, value) in attributes) {
                builder.append(" $attr=\"$value\"")
            }
            return builder.toString()
        }

        override fun toString(): String {
            val builder = StringBuilder()
            render(builder, "")
            return builder.toString()
        }
    }

    abstract class TagWithText(name: String) : Tag(name) {
        operator fun String.unaryPlus() {
            children.add(TextElement(this))
        }
    }

    class HTML() : TagWithText("html") {
        fun head(init: Head.() -> Unit) {
            initTag(Head(), init)
        }

        fun body(init: Body.() -> Unit) {
            initTag(Body(), init)
        }
    }

    class Head : TagWithText("head") {
        fun title(init: Title.() -> Unit) {
            initTag(Title(), init)
        }
    }

    abstract class BodyTag(name: String) : TagWithText(name) {
        fun p(init: P.() -> Unit) {
            initTag(P(), init)
        }

        fun h1(init: H1.() -> Unit) {
            initTag(H1(), init)
        }
    }

    class Body : BodyTag("body")
    class P : BodyTag("p")
    class H1 : BodyTag("h1")
    class Title : TagWithText("title")


    // TODO:init
    // 这个函数接受一个名为 init 的参数，该参数本身就是一个函数。 该函数的类型是 HTML.() -> Unit，它是一个带接收者的函数类型。 这意味着需要向函数传递一个 HTML 类型的实例（接收者）， 并且可以在函数内部调用该实例的成员。
    // "init" : parameter name init , which itself a function
    // "HTML.() -> Unit": function type with receiver
    fun html(init: HTML.() -> Unit): HTML {
        val html = HTML()
        html.init()
        return html
    }

    @Test
    fun test_HtmlTagsCreation() {
        fun result() = html {
            head {
                title { +"title with kotlin" }
            }
            body {
                h1 { +"h1 example" }
                p { +"p example" }
            }
        }

        val result = result()

        val expected = "<html>\n" +
                "<head>\n" +
                "<title>\n" +
                "title with kotlin\n" +
                "</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>\n" +
                "h1 example\n" +
                "</h1>\n" +
                "<p>\n" +
                "p example\n" +
                "</p>\n" +
                "</body>\n" +
                "</html>\n"
        assertEquals(expected, result.toString())
    }
}