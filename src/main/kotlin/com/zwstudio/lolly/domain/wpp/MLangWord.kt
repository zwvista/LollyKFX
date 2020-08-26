package com.zwstudio.lolly.domain.wpp

// Generated 2014-10-12 21:44:14 by Hibernate Tools 4.3.1

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel
import java.io.Serializable

class MLangWords {

    @SerializedName("records")
    @Expose
    var lst: List<MLangWord>? = null
}

class MLangWord: Serializable {

    @SerializedName("ID")
    @Expose
    var id = 0
    @SerializedName("LANGID")
    @Expose
    var langid = 0
    @SerializedName("WORD")
    @Expose
    val wordProperty = SimpleStringProperty()
    var word: String get() = wordProperty.value; set(value) { wordProperty.value = value }
    @SerializedName("NOTE")
    @Expose
    val noteProperty = SimpleStringProperty()
    var note: String get() = noteProperty.value; set(value) { noteProperty.value = value }
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

    val wordnote: String
        get() = word + (if (note.isNullOrEmpty()) "" else "($note)")
    val accuracy: String
        get() = if (total == 0) "N/A" else "${Math.floor(correct.toDouble() / total.toDouble() * 1000) / 10}%"
}

class LangWordViewModel(item: MLangWord) : ItemViewModel<MLangWord>(item) {
    val id = bind(MLangWord::id)
    val word = bind(MLangWord::wordProperty)
    val note = bind(MLangWord::noteProperty)
    val famiid = bind(MLangWord::famiid)
    val level = bind(MLangWord::level)
    val accuracy = bind(MLangWord::accuracy)
}
