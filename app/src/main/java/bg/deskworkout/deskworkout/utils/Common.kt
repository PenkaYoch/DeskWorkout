package bg.deskworkout.deskworkout.utils

import java.lang.Exception

class ErrorException(override var message: String): Exception()

fun errorException(message: String): Nothing = throw ErrorException(message = message)