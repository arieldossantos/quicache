package br.eng.r2a.quicache.engine.actions

interface IAction {
    fun run(): ByteArray?
}