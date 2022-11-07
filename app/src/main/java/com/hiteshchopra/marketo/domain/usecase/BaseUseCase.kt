package com.hiteshchopra.marketo.domain.usecase


interface BaseUseCase<out Result, in ExecutableParam> {

    /**
     * Perform an async operation with no input parameters.
     * Will throw an exception by default, if not implemented but invoked.
     *
     * @return
     */
    suspend fun perform(): Result = throw NotImplementedError()

    /**
     *  Perform an async operation with input parameters.
     *  Will throw an exception by default, if not implemented but invoked.
     *
     * @param params
     * @return
     */
    suspend fun perform(params: ExecutableParam): Result = throw NotImplementedError()
}