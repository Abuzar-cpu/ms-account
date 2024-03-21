package az.vtb.msaccount.annotation

import mu.KLogger
import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component

@Aspect
@Component
class LogInterceptor {

    companion object {
        private val log: KLogger = KotlinLogging.logger("LogInterceptor")
    }

    @Before("@within(az.vtb.msaccount.annotation.Log) && execution(* *(..))")
    fun logStart(joinPoint: JoinPoint) {
        val method = joinPoint.signature
        log.info { "ActionLog.${method.name}.start: ${joinPoint.args.joinToString(", ") { it.toString() }}" }
    }

    @AfterThrowing("@within(az.vtb.msaccount.annotation.Log) && execution(* *(..))")
    fun logError(joinPoint: JoinPoint) {
        val method = joinPoint.signature
        log.error { "ActionLog.${method.name}.error" }
    }

    @AfterReturning("@within(az.vtb.msaccount.annotation.Log) && execution(* *(..))", returning = "result")
    fun logEnd(joinPoint: JoinPoint, result: Any?) {
        val method = joinPoint.signature
        log.info { "ActionLog.${method.name}.end: ${result.toString()}" }
    }
}
