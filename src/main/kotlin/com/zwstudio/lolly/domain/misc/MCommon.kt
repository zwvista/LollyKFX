package com.zwstudio.lolly.domain.misc

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MSelectItem(val value: Int, val label: String): Serializable {
    override fun toString() = label
}

class MSPResult: Serializable {

    @SerializedName("NEW_ID")
    var newid: String? = null
    @SerializedName("result")
    var result = ""
}

class MCodes {

    @SerializedName("records")
    var lst: List<MCode>? = null
}

class MCode: Serializable {

    @SerializedName("CODE")
    var code = 0
    @SerializedName("NAME")
    var name = ""
}

class MTransformItem {
    var index = 0
    var extractor = ""
    var replacement = ""
}
