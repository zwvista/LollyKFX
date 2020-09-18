package com.zwstudio.lolly.domain.wpp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MWordsPhrases {

    @SerializedName("records")
    @Expose
    var lst: List<MWordPhrase>? = null
}

class MWordPhrase: Serializable {

    @SerializedName("ID")
    @Expose
    var id = 0
    @SerializedName("WORDID")
    @Expose
    var wordid = 0
    @SerializedName("PHRASEID")
    @Expose
    var phraseid = 0
}
