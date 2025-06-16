package test

fun main() {
    val p1 = BasePeople.Student
    val p2 = BasePeople.People("Android")
    val p3 = BasePeople.People("iOS")

    println("p2 = $p2")

//    when(p2){
//        is BasePeople.People -> println("yes")
//        is BasePeople.Student -> println("Student")
//    }

}
sealed class BasePeople {
    data class People(val name: String) : BasePeople()
    object Student : BasePeople()
}