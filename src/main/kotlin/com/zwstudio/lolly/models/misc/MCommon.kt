package com.zwstudio.lolly.models.misc

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MSelectItem(val value: Int, val label: String): Serializable {
    override fun toString() = label
}

data class MSPResult(
    @SerializedName("NEW_ID")
    var newid: String? = null,
    @SerializedName("result")
    var result: String = "",
) : Serializable

class MCodes {

    @SerializedName("records")
    var lst: List<MCode> = emptyList()
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
