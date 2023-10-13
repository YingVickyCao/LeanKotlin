package com.hades.example.leankotlin._4_class_and_object._5_extension

// https://kotlinlang.org/docs/extensions.html#declaring-extensions-as-members
fun main() {
    val example = DeclaringExtensionAsMemberExample()
    example.test()
}

class DeclaringExtensionAsMemberExample {
    fun test() {
        // Example 1 : Declaring extensions as members
        run {
            // (1)
            val host = Host("127.2.2.12")
            val connection = Connection(host, 443)
            connection.connect()    // 127.2.2.12:443

            // (2)
            // Host(hostname='127.2.2.12')
            // Connection(host=Host(hostname='127.2.2.12'), port=443)
            connection.connectString()

            // (3)

            val base = Base()
            val derived = Derived()
            val baseCaller = BaseCaller();
            val derivedCaller = DerivedCaller();

            baseCaller.call(base)           // Base extension function in BaseCaller
            derivedCaller.call(base)        // Base extension function in DerivedCaller   => dispatch receiver is resolved virtually
            derivedCaller.call(derived)     // Base extension function in DerivedCaller   =>  extension receiver is resolved statically

        }
    }
}

// Example 1 : Declaring extensions as members, START
// extension receiver
// dispatch receiver
private class Host(val hostname: String) {
    fun printHostName() {
        print(hostname)
    }

    override fun toString(): String {
        return "Host(hostname='$hostname')"
    }
}

private class Connection(val host: Host, val port: Int) {
    fun printPort() {
        println(port)
    }

    // (1) Declare extensions for one class inside another class. Inside such an extension, there are multiple implicit receivers - objects whose members can be accessed without a qualifier.
    // An instance of a class in which the extension is declared is called a dispatch receiver. => Connection
    // An instance of the receiver type of the extension method is called an extension receiver.   => Host
    fun Host.printConnectionString() {  // An extension function
        printHostName() // Calls Host.printHostName
        print(":")
        printPort()     // Calls Connection.printPort
    }

    fun connect() {
        host.printConnectionString()    // Calls the extension function
    }

    // (2) In the event of a name conflict between the members of a dispatch receiver and an extension receiver, the extension receiver takes precedence.
    // extension receiver > a dispatch receiver
    fun Host.getConnectionString() {
        println(toString())                  // Calls Host.toString
        println(this@Connection.toString())  // Calls Connection.toString
    }

    fun connectString() {
        host.getConnectionString()
    }

    override fun toString(): String {
        return "Connection(host=$host, port=$port)"
    }
}


// (3)Extensions declared as members can be declared as open and overridden in subclasses.
// This means that the dispatch of such functions is virtual with regard to the dispatch receiver type, but static with regard to the extension receiver type.
private open class Base {

}

private class Derived : Base() {
}

private open class BaseCaller {
    open fun Base.printFunctionInfo() {
        println("Base extension function in BaseCaller")
    }

    open fun Derived.printFunctionInfo() {
        println("Derived extension function in BaseCaller")
    }

    open fun call(b: Base) {
        b.printFunctionInfo()   // Calls the extension function
    }
}

private open class DerivedCaller : BaseCaller() {
    override fun Base.printFunctionInfo() {
        println("Base extension function in DerivedCaller")
    }

    override fun Derived.printFunctionInfo() {
        println("Derived extension function in DerivedCaller")
    }
}
// Example 3 : Declaring extensions as members, END