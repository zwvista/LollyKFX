package com.zwstudio.lolly.models.wpp

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MWordsFami {

    @SerializedName("records")
    var lst: List<MWordFami>? = null
}

class MWordFami: Serializable {

    @SerializedName("ID")
    var id = 0
    @SerializedName("USERID")
    var userid = ""
    @SerializedName("WORDID")
    var wordid = 0
    @SerializedName("CORRECT")
    var correct = 0
    @SerializedName("TOTAL")
    var total = 0
}
