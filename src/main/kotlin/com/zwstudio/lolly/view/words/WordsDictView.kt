package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.SettingsViewModel
import com.zwstudio.lolly.domain.MDictionary
import javafx.scene.layout.Priority
import javafx.scene.web.WebView
import tornadofx.*

class WordsDictView : Fragment() {
    val dict: MDictionary by param()
    val vmSettings: SettingsViewModel by inject()

    var wvDict: WebView by singleAssign()
    override val root = vbox {
        tag = this@WordsDictView
        wvDict = webview {
            vgrow = Priority.ALWAYS
        }
    }

    fun searchWord(word: String) {
        val url = dict.urlString(word, vmSettings.lstAutoCorrect)
        wvDict.engine.load(url)
    }
}
