package com.zwstudio.lolly.common

import com.zwstudio.lolly.models.misc.MSPResult
import com.zwstudio.lolly.models.misc.MTransformItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import tornadofx.UIComponent
import java.net.URLEncoder
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.isSupertypeOf
import kotlin.reflect.full.memberProperties

object GlobalUser {
    var userid = ""
}

fun copyText(text: String?) {
    if (text == null) return
    // http://www.java2s.com/Code/Java/JavaFX/PutstringvaluetoSystemClipboard.htm
    val clipboard = Clipboard.getSystemClipboard()
    val content = ClipboardContent()
    content.putString(text)
    clipboard.setContent(content)
}

fun UIComponent.openPage(url: String) =
    // https://stackoverflow.com/questions/16604341/how-can-i-open-the-default-system-browser-from-a-java-fx-application
    app.hostServices.showDocument(url)

fun UIComponent.googleString(text: String?) {
    if (text == null) return
    val url = "https://www.google.com/search?q=" + URLEncoder.encode(text, "UTF-8")
    openPage(url)
}

fun removeReturns(text: String) =
    text.replace("\r\n", "\n")

fun toTransformItems(transform: String): List<MTransformItem> {
    val lst = transform.split("\r\n")
    val lst2 = lst.subList(0, lst.size / 2 * 2).chunked(2).mapIndexed { i, g ->
        MTransformItem().apply {
            index = i + 1
            extractor = g[0]
            replacement = g[1]
        }
    }
    return lst2
}

fun doTransform(text: String, item: MTransformItem): String {
    val dic = mapOf("<delete>" to "", "\\t" to "\t", "\\n" to "\n")

    val regex = Regex(item.extractor)
    var replacement = item.replacement
    var s = text
    if (replacement.startsWith("<extract>")) {
        replacement = replacement.drop("<extract>".length)
        val ms = regex.findAll(s)
        s = ms.joinToString { m -> m.groupValues[0] }
    }
    for ((key, value) in dic)
        replacement = replacement.replace(key, value)
    s = regex.replace(s, replacement)
    return s
}

fun extractTextFrom(html: String, transform: String, template: String, templateHandler: (String, String) -> String): String {
    var text = removeReturns(html)
    do {
        if (transform.isEmpty()) break
        val items = toTransformItems(transform)
        for (item in items)
            text = doTransform(text, item)
        if (template.isEmpty()) break
        text = templateHandler(text, template)
    } while (false)
    return text
}

fun toHtml(text: String) = """
    <!doctype html>
    <html>
    <head>
    <meta charset=""utf-8"">
    <meta http-equiv=""Content-Type"" content=""text/html; charset=utf-8"">
    </head>
    <body>
    $text
    </body>
    </html>
    """.trimIndent()

const val cssFolder = "https://zwvista.com/lolly/css/"

fun applyTemplate(template: String, word: String, text: String) =
    template.replace("{0}", word).replace("{1}", cssFolder).replace("{2}", text)

fun List<String>.splitUsingCommaAndMerge(): String =
    flatMap { it.split(',') }.filter { it.isNotEmpty() }.sorted().distinct().joinToString(",")

// https://stackoverflow.com/questions/49042656/simple-code-to-copy-same-name-properties
fun <T : Any, R : Any> T.copyPropsFrom(fromObject: R, vararg props: KProperty<*>) {
    // only consider mutable properties
    val mutableProps = this::class.memberProperties.filterIsInstance<KMutableProperty<*>>()
    // if source list is provided use that otherwise use all available properties
    val sourceProps = if (props.isEmpty()) fromObject::class.memberProperties else props.toList()
    // copy all matching
    mutableProps.forEach { targetProp ->
        sourceProps.find {
            // make sure properties have same name and compatible types
            it.name == targetProp.name && targetProp.returnType.isSupertypeOf(it.returnType)
        }?.let { matchingProp ->
            targetProp.setter.call(this, matchingProp.getter.call(fromObject))
        }
    }
}

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
