package com.zwstudio.lolly.view.misc

import com.zwstudio.lolly.data.misc.BlogViewModel
import com.zwstudio.lolly.data.misc.copyText
import javafx.geometry.Orientation
import javafx.scene.control.TextArea
import javafx.scene.input.Clipboard
import javafx.scene.layout.Priority
import javafx.scene.web.WebView
import tornadofx.*

class BlogView : Fragment("Blog") {
    var taMarkedText: TextArea by singleAssign()
    var wvBlog: WebView by singleAssign()
    var vm = BlogViewModel()

    private fun replaceSelection(f: (BlogViewModel, String) -> String) =
        taMarkedText.replaceSelection(f(vm, taMarkedText.selectedText))

    override val root = vbox {
        tag = this@BlogView
        toolbar {
            button("HtmlToMarked").action {
                vm.htmlToMarked()
            }
            button("Add B").action {
                replaceSelection(BlogViewModel::addTagB)
            }
            button("Add I").action {
                replaceSelection(BlogViewModel::addTagI)
            }
            button("Remove BI").action {
                replaceSelection(BlogViewModel::removeTagBI)
            }
            button("Exchange BI").action {
                replaceSelection(BlogViewModel::exchangeTagBI)
            }
            button("AddExplanation").action {
                val text = Clipboard.getSystemClipboard().getString()
                taMarkedText.replaceSelection(vm.getExplanation(text))
            }
            button("MarkedToHtml").action {
                val str = vm.markedToHtml()
                wvBlog.engine.loadContent(str)
                copyText(vm.htmlText.value)
            }
            button("PatternToHtml").action {
                wvBlog.engine.load(vm.patterUrl)
            }
            textfield(vm.patternNo)
            button("PatternMarkDown").action {
                copyText(vm.patternMarkDown)
            }
            textfield(vm.patternText)
            button("AddNotesCommand")
        }
        splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            splitpane(Orientation.VERTICAL) {
                setDividerPosition(0, 0.8)
                taMarkedText = textarea(vm.markedText) {
                    isWrapText = true
                }
                textarea(vm.htmlText) {
                    isWrapText = true
                }
            }
            wvBlog = webview {

            }
        }
    }
}
