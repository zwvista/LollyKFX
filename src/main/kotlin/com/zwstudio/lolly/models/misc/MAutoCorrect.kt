package com.zwstudio.lolly.models.misc

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MAutoCorrects {

    @SerializedName("records")
    var lst: List<MAutoCorrect>? = null
}

class MAutoCorrect: Serializable {

    @SerializedName("ID")
    var id = 0
    @SerializedName("LANGID")
    var langid = 0
    @SerializedName("SEQNUM")
    var seqnum = 0
    @SerializedName("INPUT")
    var input = ""
    @SerializedName("EXTENDED")
    var extended = ""
    @SerializedName("BASIC")
    var basic = ""
}

fun autoCorrect(text: String, lstAutoCorrects: List<MAutoCorrect>,
                colFunc1: (MAutoCorrect) -> String, colFunc2: (MAutoCorrect) -> String) =
    lstAutoCorrects.fold(text) { str, row -> str.replace(colFunc1(row), colFunc2(row)) }
