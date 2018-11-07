package com.system.moneycontrol.infrastructure

abstract class Result<T> () {

    protected var onSuccessList: ((List<T>) -> Unit)? = null
    protected var onSuccessItem: ((T) -> Unit)? = null
    protected var onFailure: ((Exception) -> Unit)? = null
    protected var onWarning: ((String) -> Unit)? = null

    fun addSuccessList(onSuccess: ((List<T>) -> Unit)?) = apply {
        this.onSuccessList = onSuccess
    }

    fun addSuccessItem(onSuccess: ((T) -> Unit)?) = apply {
        this.onSuccessItem = onSuccess
    }

    fun addFailure(onFailure: ((Exception) -> Unit)?) = apply {
        this.onFailure = onFailure
    }

    fun addWarning(onWarning: ((String) -> Unit)?) = apply {
        this.onWarning = onWarning
    }

    abstract fun execute()
}