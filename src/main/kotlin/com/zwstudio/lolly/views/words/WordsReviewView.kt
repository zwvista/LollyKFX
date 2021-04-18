package com.zwstudio.lolly.views.words

import com.zwstudio.lolly.viewmodels.misc.ReviewOptionsViewModel
import com.zwstudio.lolly.viewmodels.misc.applyIO
import com.zwstudio.lolly.viewmodels.words.WordsReviewViewModel
import com.zwstudio.lolly.views.ILollySettings
import com.zwstudio.lolly.views.MainView
import com.zwstudio.lolly.views.misc.ReviewOptionsView
import io.reactivex.rxjava3.core.Observable
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import javafx.scene.layout.Region
import tornadofx.*
import java.util.concurrent.TimeUnit

class WordsReviewView : Fragment("Words Review"), ILollySettings {
    var vm = WordsReviewViewModel()

    override val root = vbox {
        tag = this@WordsReviewView
        toolbar {
            button("New Test").action {
                onSettingsChanged()
            }
            checkbox("Speak?", vm.isSpeaking)
            button("Speak")
        }
        gridpane {
            vgrow = Priority.ALWAYS
            paddingAll = 10.0
            hgap = 10.0
            vgap = 10.0
            constraintsForRow(0).percentHeight = 12.5
            constraintsForRow(1).percentHeight = 12.5
            constraintsForRow(2).percentHeight = 50.0
            constraintsForRow(3).percentHeight = 12.5
            constraintsForRow(4).percentHeight = 12.5
            constraintsForColumn(0).hgrow = Priority.ALWAYS
            style {
                fontSize = 24.px
            }
            row {
                hbox(10.0) {
                    label(vm.indexString) {
                        visibleWhen(vm.indexVisible)
                    }
                    stackpane {
                        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE)
                        label("Correct") {
                            visibleWhen(vm.correctVisible)
                            style {
                                textFill = c("green")
                            }
                        }
                        label("Incorrect") {
                            visibleWhen(vm.incorrectVisible)
                            style {
                                textFill = c("red")
                            }
                        }
                    }
                    region {
                        hgrow = Priority.ALWAYS
                    }
                    label(vm.accuracyString) {
                        visibleWhen(vm.accuracyVisible)
                    }
                }
            }
            row {
                vbox {
                    hbox(10.0) {
                        alignment = Pos.CENTER_LEFT
                        label(vm.wordTargetString) {
                            visibleWhen(vm.wordTargetVisible)
                            style {
                                textFill = c("orange")
                            }
                        }
                        label(vm.noteTargetString) {
                            visibleWhen(vm.noteTargetVisible)
                            style {
                                textFill = c("magenta")
                                fontSize = 18.px
                            }
                        }
                        label(vm.wordHintString) {
                            visibleWhen(vm.wordHintVisible)
                        }
                    }
                }
            }
            row {
                textarea(vm.translationString)
            }
            row {
                textfield(vm.wordInputString).action {
                    vm.check()
                }
            }
            row {
                hbox {
                    button("Search") {
                        enableWhen(vm.searchEnabled)
                    }.action {
                        find<MainView>().searchNewWord(vm.currentWord)
                    }
                    region {
                        hgrow = Priority.ALWAYS
                    }
                    button(vm.checkString).action {
                        vm.check()
                    }
                }
            }
        }
    }

    init {
        Observable.timer(1, TimeUnit.SECONDS).applyIO().subscribe {
            onSettingsChanged()
        }
    }

    override fun onSettingsChanged() {
        val modal = find<ReviewOptionsView>("vm" to ReviewOptionsViewModel(vm.options)) { openModal(block = true) }
        if (modal.result)
            vm.newTest()
    }
}
