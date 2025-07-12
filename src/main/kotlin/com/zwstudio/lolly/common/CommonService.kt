package com.zwstudio.lolly.common

import com.zwstudio.lolly.models.misc.MSPResult
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

fun Any.logDebug(message: String) {
    println("${this::class.java.simpleName}: $message")
}

fun Single<Int>.debugCreate(): Single<Int> =
    map {
        it.also { logDebug("Created new item, result=$it") }
    }

fun Single<Int>.completeCreate(): Completable =
    flatMapCompletable {
        logDebug("Created new item, result=$it")
        Completable.complete()
    }

fun Single<Int>.completeUpdate(id: Int): Completable =
    flatMapCompletable {
        logDebug("Updated item ID=${id}, result=$it")
        Completable.complete()
    }

fun Single<Int>.completeDelete(): Completable =
    flatMapCompletable {
        logDebug("Deleted item, result=$it")
        Completable.complete()
    }

fun Single<List<List<MSPResult>>>.debugCreateResult(): Single<Int> =
    map {
        logDebug("Created new item, result=$it")
        it[0][0].newid!!.toInt()
    }

fun Single<List<List<MSPResult>>>.completeUpdateResult(id: Int): Completable =
    flatMapCompletable {
        logDebug("Updated item ID=${id}, result=$it")
        Completable.complete()
    }

fun Single<List<List<MSPResult>>>.completeDeleteResult(): Completable =
    flatMapCompletable {
        logDebug("Deleted item, result=$it")
        Completable.complete()
    }
