package br.eng.r2a.quicache.engine.store

import java.util.*
import java.util.function.Predicate
import kotlin.concurrent.timerTask

/**
 * Quicache general context
 *
 * @author Ari
 */
internal object QuicacheContext {
    private val currentContext = mutableListOf<QuicacheObject>()

    fun addToContext(quicacheObject: QuicacheObject) {
        this.currentContext.add(quicacheObject)
    }

    @ExperimentalMultiplatform
    fun unsafeResetContext() {
        this.currentContext.removeIf { true }
    }

    fun scheduleWithTTL(quicacheObject: QuicacheObject) {
        if(quicacheObject.ttl > 0) return;
        val condition = Predicate { current: QuicacheObject -> current.key == quicacheObject.key }
        Timer(quicacheObject.key, true).
            schedule(
                timerTask {
                    currentContext.removeIf(condition)
                },
                quicacheObject.ttl
            )
    }
}