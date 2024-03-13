package az.vtb.msaccount.exception

import az.vtb.msaccount.exception.ExceptionMessages.*
import org.springframework.http.HttpStatus.*
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice


data class ErrorResponse(var message: String)

@RestControllerAdvice
class ErrorHandler {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun handle(exception: Exception): ErrorResponse {
        println(exception)
        return ErrorResponse(UNEXPECTED_ERROR.message)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    @ResponseStatus(METHOD_NOT_ALLOWED)
    fun handle(methodNotSupportedException: HttpRequestMethodNotSupportedException): ErrorResponse {
        println(methodNotSupportedException)
        return ErrorResponse(METHOD_NOT_ALLOWED_ERROR.message)
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun handle(notFoundException: NotFoundException): ErrorResponse {
        println(notFoundException)
        return ErrorResponse(NOT_FOUND_ERROR.message)
    }

    @ExceptionHandler(ConflictException::class)
    @ResponseStatus(CONFLICT)
    fun handle(conflictException: ConflictException): ErrorResponse {
        println(conflictException)
        return ErrorResponse(ALREADY_EXISTS_ERROR.message)
    }

    @ExceptionHandler(InsufficientBalanceException::class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    fun handle(insufficientBalanceException: InsufficientBalanceException): ErrorResponse {
        println(insufficientBalanceException)
        return ErrorResponse(INSUFFICIENT_BALANCE_ERROR.message)
    }
}