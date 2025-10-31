import java.util.*

fun isValid(s: String): Boolean {
    /**
    for 变量字符串{

    if(stack is empty){
    put to stack
    }
    else {
    if( == top of stack){
    if(yes){
    pop top of stack
    }
    else{
    put to stack
    }
    }
    }
    }

    }

    if(stack is empty){
    // string is valid
    }
    else {
    string not valid
    }
     */

    val stack = Stack<Char>()
    for (i in 0 until s.length) {
        if (stack.isEmpty()) {
            stack.push(s[i])
            println(s[i])
        } else {
            val top = stack.peek()
            if ((s[i] == ')' && top == '(')
                || (s[i] == '}' && top == '{')
                || (s[i] == ']' && top == '[')
            ) {
                stack.pop()
            } else {
                stack.push(s[i])
            }
        }
    }
    return stack.isEmpty()
}