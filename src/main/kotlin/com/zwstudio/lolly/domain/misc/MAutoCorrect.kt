package com.zwstudio.lolly.domain.misc

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MAutoCorrects {

    @SerializedName("records")
    @Expose
    var lst: List<MAutoCorrect>? = null
}

class MAutoCorrect: Serializable {

    @SerializedName("ID")
    @Expose
    var id = 0
    @SerializedName("LANGID")
    @Expose
    var langid = 0
    @SerializedName("SEQNUM")
    @Expose
    var seqnum = 0
    @SerializedName("INPUT")
    @Expose
    var input = ""
    @SerializedName("EXTENDED")
    @Expose
    var extended = ""
    @SerializedName("BASIC")
    @Expose
    var basic = ""
}

fun autoCorrect(text: String, lstAutoCorrects: List<MAutoCorrect>,
                colFunc1: (MAutoCorrect) -> String, colFunc2: (MAutoCorrect) -> String) =
    lstAutoCorrects.fold(text) { str, row -> str.replace(colFunc1(row), colFunc2(row)) }
