package com.zwstudio.lolly.views.words

import com.zwstudio.lolly.common.openPage
import com.zwstudio.lolly.models.misc.MDictionary
import com.zwstudio.lolly.viewmodels.misc.SettingsViewModel
import io.reactivex.rxjava3.kotlin.subscribeBy
import javafx.concurrent.Worker
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import javafx.scene.web.WebView
import tornadofx.*

enum class DictWebViewStatus {
    Ready, Navigating, Automating
}

class WordsDictView : Fragment() {
    val dict: MDictionary by param()
    private val vmSettings: SettingsViewModel by inject()
    var dictStatus = DictWebViewStatus.Ready
    var url = ""
    var word = ""

    var wvDict: WebView by singleAssign()
    var tfURL: TextField by singleAssign()

    override val root = vbox {
        tag = this@WordsDictView
        hbox {
            tfURL = textfield {
                hgrow = Priority.ALWAYS
            }
            button("Open").action {
                openPage(tfURL.text)
            }
        }
        wvDict = webview {
            vgrow = Priority.ALWAYS
            engine.loadWorker.stateProperty().addListener(ChangeListener { _, _, newState ->
                if (newState != Worker.State.SUCCEEDED) return@ChangeListener
                tfURL.text = engine.location
                when (dictStatus) {
                    DictWebViewStatus.Ready -> return@ChangeListener
                    DictWebViewStatus.Automating -> {
                        val s = dict.automation.replace("{0}", word)
                        engine.executeScript(s)
                        dictStatus = DictWebViewStatus.Ready
                        if (dict.dicttypename == "OFFLINE-ONLINE")
                            dictStatus = DictWebViewStatus.Navigating
                    }
                    DictWebViewStatus.Navigating -> {
                        // https://stackoverflow.com/questions/14273450/get-the-contents-from-the-webview-using-javafx
                        val html = engine.executeScript("document.documentElement.outerHTML") as String
                        val str = dict.htmlString(html, word, false)
                        dictStatus = DictWebViewStatus.Ready
                        wvDict.engine.loadContent(str)
                    }
                }
            })
        }
    }

    fun searchDict(word: String) {
        this.word = word
        dictStatus = DictWebViewStatus.Ready
        url = dict.urlString(word, vmSettings.lstAutoCorrect)
        if (dict.dicttypename == "OFFLINE") {
            wvDict.engine.load("about:blank")
            vmSettings.getHtml(url).subscribeBy {
                val str = dict.htmlString(it, word, false)
                wvDict.engine.loadContent(str)
            }
        } else {
            wvDict.engine.load(url)
            if (dict.automation.isNotEmpty())
                dictStatus = DictWebViewStatus.Automating
            else if (dict.dicttypename == "OFFLINE-ONLINE")
                dictStatus = DictWebViewStatus.Navigating
        }
    }
}
