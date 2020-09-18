package com.zwstudio.lolly.data.misc

import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import tornadofx.UIComponent
import java.net.URLEncoder

object GlobalConstants {
    val userid = 1
}

enum class DictWebViewStatus {
    Ready, Navigating, Automating
}

enum class UnitPartToType {
    Unit, Part, To
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

fun extractTextFrom(html: String, transform: String, template: String, templateHandler: (String, String) -> String): String {
    val dic = mapOf("<delete>" to "", "\\t" to "\t", "\\n" to "\n")

    var text = html
    do {
        if (transform.isEmpty()) break
        var lst = transform.split("\r\n")
        if (lst.size % 2 == 1) lst = lst.dropLast(1)

        for (i in lst.indices step 2) {
            val regex = Regex(lst[i])
            var replacer = lst[i + 1]
            if (replacer.startsWith("<extract>")) {
                replacer = replacer.drop("<extract>".length)
                val ms = regex.findAll(text)
                text = ms.joinToString { m -> m.groupValues[0] }
                if (text.isEmpty()) break
            }
            for ((key, value) in dic)
                replacer = replacer.replace(key, value)
            text = regex.replace(text, replacer)
        }

        if (template.isEmpty()) break
        text = templateHandler(text, template)

    } while (false)
    return text
}