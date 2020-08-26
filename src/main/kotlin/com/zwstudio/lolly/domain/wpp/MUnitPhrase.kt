package com.zwstudio.lolly.domain.wpp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.zwstudio.lolly.domain.MSelectItem
import com.zwstudio.lolly.domain.MTextbook
import javafx.beans.property.Property
import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel
import java.io.Serializable

class MUnitPhrases {

    @SerializedName("records")
    @Expose
    var lst: List<MUnitPhrase>? = null
}

class MUnitPhrase: Serializable {

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
    @SerializedName("PHRASEID")
    @Expose
    var phraseid = 0
    @SerializedName("PHRASE")
    @Expose
    val phraseProperty = SimpleStringProperty()
    var phrase: String get() = phraseProperty.value; set(value) { phraseProperty.value = value }
    @SerializedName("TRANSLATION")
    @Expose
    val translationProperty = SimpleStringProperty()
    var translation: String get() = translationProperty.value; set(value) { translationProperty.value = value }

    lateinit var textbook: MTextbook
    val unitstr: String
        get() = textbook.unitstr(unit)
    var unititem: MSelectItem? = null
    val partstr: String
        get() = textbook.partstr(part)
    var partitem: MSelectItem? = null
    val unitpartseqnum: String
        get() = "$unitstr $seqnum\n$partstr"
}

class UnitPhraseViewModel(item: MUnitPhrase) : ItemViewModel<MUnitPhrase>(item) {
    val id = bind(MUnitPhrase::id)
    val textbookname = bind(MUnitPhrase::textbookname)
    val unititem: Property<MSelectItem>
    val partitem: Property<MSelectItem>
    val seqnum = bind(MUnitPhrase::seqnum)
    val phraseid = bind(MUnitPhrase::phraseid)
    val phrase = bind(MUnitPhrase::phraseProperty)
    val translation = bind(MUnitPhrase::translationProperty)

    init {
        item.unititem = item.textbook.lstUnits.first { it.value == item.unit }
        item.partitem = item.textbook.lstParts.first { it.value == item.part }
        unititem = bind(MUnitPhrase::unititem)
        partitem = bind(MUnitPhrase::partitem)
    }
}
