package az.vtb.msaccount.exception

import az.vtb.msaccount.exception.ExceptionMessages.*
import mu.KotlinLogging
import org.springframework.http.HttpStatus.*
import org.springframework.http.converter.HttpMessageNotReadableException
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
        log.error("Exception: {}", exception.stackTraceToString())
        return ErrorResponse(UNEXPECTED_ERROR.message)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    @ResponseStatus(METHOD_NOT_ALLOWED)
    fun handle(methodNotSupportedException: HttpRequestMethodNotSupportedException): ErrorResponse {
        log.error("HttpRequestMethodNotSupportedException {}", methodNotSupportedException.stackTraceToString())
        return ErrorResponse(METHOD_NOT_ALLOWED_ERROR.message)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(BAD_REQUEST)
    fun handle(httpMessageNotReadableException: HttpMessageNotReadableException): ErrorResponse {
        log.error("HttpMessageNotReadableException {}", httpMessageNotReadableException.stackTraceToString())
        return ErrorResponse(METHOD_NOT_ALLOWED_ERROR.message)
    }

    @ExceptionHandler(IllegalStateException::class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    fun handle(illegalStateException: IllegalStateException): ErrorResponse {
        log.error("IllegalStateException {}", illegalStateException.stackTraceToString())
        return ErrorResponse(illegalStateException.message!!)
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun handle(notFoundException: NotFoundException): ErrorResponse {
        log.error("NotFoundException {}", notFoundException.stackTraceToString())
        return ErrorResponse(NOT_FOUND_ERROR.message)
    }

    @ExceptionHandler(ConflictException::class)
    @ResponseStatus(CONFLICT)
    fun handle(conflictException: ConflictException): ErrorResponse {
        log.error("ConflictException {}", conflictException.stackTraceToString())
        return ErrorResponse(ALREADY_EXISTS_ERROR.message)
    }

    @ExceptionHandler(InsufficientBalanceException::class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    fun handle(insufficientBalanceException: InsufficientBalanceException): ErrorResponse {
        log.error("InsufficientBalanceException {}", insufficientBalanceException.stackTraceToString())
        return ErrorResponse(INSUFFICIENT_BALANCE_ERROR.message)
    }
}
