package com.pagination.app.errors


interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}
