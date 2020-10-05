package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.misc.DictWebViewStatus
import com.zwstudio.lolly.data.misc.SettingsViewModel
import com.zwstudio.lolly.data.misc.openPage
import com.zwstudio.lolly.domain.misc.MDictionary
import javafx.concurrent.Worker
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import javafx.scene.web.WebView
import tornadofx.*

class WordsDictView : Fragment() {
    val dict: MDictionary by param()
    val vmSettings: SettingsViewModel by inject()
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
                        val s = dict.automation!!.replace("{0}", word)
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

    fun searchWord(word: String) {
        this.word = word
        dictStatus = DictWebViewStatus.Ready
        url = dict.urlString(word, vmSettings.lstAutoCorrect)
        if (dict.dicttypename == "OFFLINE") {
            wvDict.engine.load("about:blank")
            vmSettings.getHtml(url).subscribe {
                val str = dict.htmlString(it, word, false)
                wvDict.engine.loadContent(str)
            }
        } else {
            wvDict.engine.load(url)
            if (dict.automation != null)
                dictStatus = DictWebViewStatus.Automating
            else if (dict.dicttypename == "OFFLINE-ONLINE")
                dictStatus = DictWebViewStatus.Navigating
        }
    }
}
