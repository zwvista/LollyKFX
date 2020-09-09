package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.DictWebBrowserStatus
import com.zwstudio.lolly.data.SettingsViewModel
import com.zwstudio.lolly.data.openPage
import com.zwstudio.lolly.domain.misc.MDictionary
import com.zwstudio.lolly.service.HtmlService
import javafx.concurrent.Worker
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import javafx.scene.web.WebView
import tornadofx.*

class WordsDictView : Fragment() {
    val dict: MDictionary by param()
    val vmSettings: SettingsViewModel by inject()
    var dictStatus = DictWebBrowserStatus.Ready
    var url = ""
    var word = ""

    var wvDict: WebView by singleAssign()
    var tfURL: TextField by singleAssign()

    val htmlService: HtmlService by inject()

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
                    DictWebBrowserStatus.Ready -> return@ChangeListener
                    DictWebBrowserStatus.Automating -> {
                        val s = dict.automation!!.replace("{0}", word)
                        engine.executeScript(s)
                        dictStatus = DictWebBrowserStatus.Ready
                        if (dict.dicttypename == "OFFLINE-ONLINE")
                            dictStatus = DictWebBrowserStatus.Navigating
                    }
                    DictWebBrowserStatus.Navigating -> {
                        // https://stackoverflow.com/questions/14273450/get-the-contents-from-the-webview-using-javafx
                        val html = engine.executeScript("document.documentElement.outerHTML") as String
                        val str = dict.htmlString(html, word, false)
                        dictStatus = DictWebBrowserStatus.Ready
                        wvDict.engine.loadContent(str)
                    }
                }
            })
        }
    }

    fun searchWord(word: String) {
        this.word = word
        dictStatus = DictWebBrowserStatus.Ready
        url = dict.urlString(word, vmSettings.lstAutoCorrect)
        if (dict.dicttypename == "OFFLINE") {
            wvDict.engine.load("about:blank")
            htmlService.getHtml(url).subscribe {
                val str = dict.htmlString(it, word, false)
                wvDict.engine.loadContent(str)
            }
        } else {
            wvDict.engine.load(url)
            if (dict.automation != null)
                dictStatus = DictWebBrowserStatus.Automating
            else if (dict.dicttypename == "OFFLINE-ONLINE")
                dictStatus = DictWebBrowserStatus.Navigating
        }
    }
}
