package com.zwstudio.lolly.models.wpp

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MWordsPhrases {

    @SerializedName("records")
    var lst: List<MWordPhrase>? = null
}

class MWordPhrase: Serializable {

    @SerializedName("ID")
    var id = 0
    @SerializedName("WORDID")
    var wordid = 0
    @SerializedName("PHRASEID")
    var phraseid = 0
}
