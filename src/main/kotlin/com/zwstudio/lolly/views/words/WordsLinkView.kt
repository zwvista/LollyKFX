package com.zwstudio.lolly.views.words

import com.zwstudio.lolly.viewmodels.misc.SettingsViewModel
import com.zwstudio.lolly.viewmodels.words.WordsLinkViewModel
import com.zwstudio.lolly.models.wpp.MLangWord
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.SelectionMode
import javafx.scene.control.TableView
import javafx.scene.layout.Priority
import tornadofx.*

class WordsLinkView : Fragment("Link Words") {
    var tvWords: TableView<MLangWord> by singleAssign()
    val vm: WordsLinkViewModel by param()
    var result = false

    override val root = vbox(10.0) {
        paddingAll = 10.0
        gridpane {
            hgap = 10.0
            vgap = 10.0
            constraintsForColumn(1).hgrow = Priority.ALWAYS
            constraintsForColumn(3).hgrow = Priority.ALWAYS
            row {
                label("Word Scope:")
                label("Language")
            }
            row {
                label("Filter Scope:")
                choicebox(vm.scopeFilter, SettingsViewModel.lstScopeWordFilters)
                label("Filter Text:")
                textfield(vm.textFilter)
            }
        }
        hbox(10.0) {
            alignment = Pos.CENTER
            button("Check All").action {
                vm.checkItems(0, tvWords.selectionModel.selectedItems)
            }
            button("Uncheck All").action {
                vm.checkItems(1, tvWords.selectionModel.selectedItems)
            }
            button("Check Selected").action {
                vm.checkItems(2, tvWords.selectionModel.selectedItems)
            }
            button("Uncheck Selected").action {
                vm.checkItems(3, tvWords.selectionModel.selectedItems)
            }
            children.forEach {
                (it as Button).prefWidth = 150.0
            }
        }
        tvWords = tableview(vm.lstWords) {
            column("", MLangWord::isChecked).makeEditable()
            readonlyColumn("ID", MLangWord::id)
            readonlyColumn("WORD", MLangWord::wordProperty)
            readonlyColumn("NOTE", MLangWord::noteProperty)
            readonlyColumn("ACCURACY", MLangWord::accuracy)
            readonlyColumn("FAMIID", MLangWord::famiid)
            selectionModel.selectionMode = SelectionMode.MULTIPLE
        }
        buttonbar {
            button("OK") {
                isDefaultButton = true
            }.action {
                result = true
                vm.commit()
                close()
            }
            button("Cancel") {
                isCancelButton = true
            }.action {
                close()
            }
        }
    }
}

