package com.zwstudio.lolly.domain.wpp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.zwstudio.lolly.domain.MSelectItem
import com.zwstudio.lolly.domain.MTextbook
import javafx.beans.property.Property
import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel
import java.io.Serializable

class MUnitWords {

    @SerializedName("records")
    @Expose
    var lst: List<MUnitWord>? = null
}

class MUnitWord: Serializable {

    @SerializedName("ID")
    @Expose
    var id = 0
    @SerializedName("LANGID")
    @Expose
    var langid = 0
    @SerializedName("TEXTBOOKID")
    @Expose
    var textbookid = 0
    @SerializedName("TEXTBOOKNAME")
    @Expose
    var textbookname = ""
    @SerializedName("UNIT")
    @Expose
    var unit = 0
    @SerializedName("PART")
    @Expose
    var part = 0
    @SerializedName("SEQNUM")
    @Expose
    var seqnum = 0
    @SerializedName("WORD")
    @Expose
    val wordProperty = SimpleStringProperty()
    var word: String get() = wordProperty.value; set(value) { wordProperty.value = value }
    @SerializedName("NOTE")
    @Expose
    val noteProperty = SimpleStringProperty()
    var note: String get() = noteProperty.value; set(value) { noteProperty.value = value }
    @SerializedName("WORDID")
    @Expose
    var wordid = 0
    @SerializedName("FAMIID")
    @Expose
    var famiid = 0
    @SerializedName("LEVEL")
    @Expose
    var level = 0
    @SerializedName("CORRECT")
    @Expose
    var correct = 0
    @SerializedName("TOTAL")
    @Expose
    var total = 0

    lateinit var textbook: MTextbook
    val unitstr: String
        get() = textbook.unitstr(unit)
    var unititem: MSelectItem? = null
    val partstr: String
        get() = textbook.partstr(part)
    var partitem: MSelectItem? = null
    val unitpartseqnum: String
        get() = "$unitstr $seqnum\n$partstr"
    val wordnote: String
        get() = word + (if (note.isNullOrEmpty()) "" else "($note)")
    val accuracy: String
        get() = if (total == 0) "N/A" else "${Math.floor(correct.toDouble() / total.toDouble() * 1000) / 10}%"
}

class UnitWordViewModel(item: MUnitWord) : ItemViewModel<MUnitWord>(item) {
    val id = bind(MUnitWord::id)
    val textbookname = bind(MUnitWord::textbookname)
    val unititem: Property<MSelectItem>
    val partitem: Property<MSelectItem>
    val seqnum = bind(MUnitWord::seqnum)
    val wordid = bind(MUnitWord::wordid)
    val word = bind(MUnitWord::wordProperty)
    val note = bind(MUnitWord::noteProperty)
    val famiid = bind(MUnitWord::famiid)
    val level = bind(MUnitWord::level)
    val accuracy = bind(MUnitWord::accuracy)

    init {
        item.unititem = item.textbook.lstUnits.first { it.value == item.unit }
        item.partitem = item.textbook.lstParts.first { it.value == item.part }
        unititem = bind(MUnitWord::unititem)
        partitem = bind(MUnitWord::partitem)
    }
}
