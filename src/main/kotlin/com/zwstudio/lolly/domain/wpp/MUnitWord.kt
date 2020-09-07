package com.zwstudio.lolly.domain.wpp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.zwstudio.lolly.domain.MSelectItem
import com.zwstudio.lolly.domain.MTextbook
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
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
    val wordProperty = SimpleStringProperty("")
    var word: String get() = wordProperty.value; set(value) { wordProperty.value = value }
    @SerializedName("NOTE")
    @Expose
    val noteProperty = SimpleStringProperty()
    var note: String? get() = noteProperty.value; set(value) { noteProperty.value = value }
    @SerializedName("WORDID")
    @Expose
    var wordid = 0
    @SerializedName("FAMIID")
    @Expose
    var famiid = 0
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
    val accuracy: String
        get() = if (total == 0) "N/A" else "${Math.floor(correct.toDouble() / total.toDouble() * 1000) / 10}%"
    val isChecked = SimpleBooleanProperty()
}
