package az.vtb.msaccount.exception

import az.vtb.msaccount.exception.ExceptionMessages.*
import mu.KotlinLogging
import org.springframework.http.HttpStatus.*
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

data class ErrorResponse(var message: String)

@RestControllerAdvice
class ErrorHandler {

    private val log = KotlinLogging.logger(this.javaClass.name)


    @ExceptionHandler(Exception::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun handle(exception: Exception): ErrorResponse {
        log.error(exception.stackTraceToString())
        return ErrorResponse(UNEXPECTED_ERROR.message)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    @ResponseStatus(METHOD_NOT_ALLOWED)
    fun handle(methodNotSupportedException: HttpRequestMethodNotSupportedException): ErrorResponse {
        log.error(methodNotSupportedException.stackTraceToString())
        return ErrorResponse(METHOD_NOT_ALLOWED_ERROR.message)
    }

    @ExceptionHandler(IllegalStateException::class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    fun handle(illegalStateException: IllegalStateException): ErrorResponse {
        log.error(illegalStateException.stackTraceToString())
        return ErrorResponse(illegalStateException.message!!)
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun handle(notFoundException: NotFoundException): ErrorResponse {
        log.error(notFoundException.stackTraceToString())
        return ErrorResponse(NOT_FOUND_ERROR.message)
    }

    @ExceptionHandler(ConflictException::class)
    @ResponseStatus(CONFLICT)
    fun handle(conflictException: ConflictException): ErrorResponse {
        log.error(conflictException.stackTraceToString())
        return ErrorResponse(ALREADY_EXISTS_ERROR.message)
    }

    @ExceptionHandler(InsufficientBalanceException::class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    fun handle(insufficientBalanceException: InsufficientBalanceException): ErrorResponse {
        log.error(insufficientBalanceException.stackTraceToString())
        return ErrorResponse(INSUFFICIENT_BALANCE_ERROR.message)
    }
}
