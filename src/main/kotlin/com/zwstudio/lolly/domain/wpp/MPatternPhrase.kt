package com.zwstudio.lolly.domain.wpp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javafx.beans.property.SimpleStringProperty
import java.io.Serializable

class MPatternPhrases {

    @SerializedName("records")
    @Expose
    var lst: List<MPatternPhrase>? = null
}

class MPatternPhrase: Serializable {

    @SerializedName("PATTERNID")
    @Expose
    var patternid = 0
    @SerializedName("LANGID")
    @Expose
    var langid = 0
    @SerializedName("PATTERN")
    @Expose
    var pattern = ""
    @SerializedName("NOTE")
    @Expose
    var note = ""
    @SerializedName("ID")
    @Expose
    var id = 0
    @SerializedName("SEQNUM")
    @Expose
    var seqnum = 0
    @SerializedName("PHRASEID")
    @Expose
    var phraseid = 0
    @SerializedName("PHRASE")
    @Expose
    val phraseProperty = SimpleStringProperty("")
    var phrase: String get() = phraseProperty.value; set(value) { phraseProperty.value = value }
    @SerializedName("TRANSLATION")
    @Expose
    val translationProperty = SimpleStringProperty("")
    var translation: String get() = translationProperty.value; set(value) { translationProperty.value = value }
}
