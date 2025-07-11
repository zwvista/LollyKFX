package com.zwstudio.lolly.models.misc

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MUSMappings(
    @SerializedName("records")
    var lst: List<MUSMapping> = emptyList()
)

data class MUSMapping(
    @Expose(serialize = false, deserialize = true)
    @SerializedName("ID")
    var id: Int = 0,
    @SerializedName("NAME")
    var name: String = "",
    @SerializedName("KIND")
    var kind: Int = 0,
    @SerializedName("ENTITYID")
    var entityid: Int = 0,
    @SerializedName("VALUEID")
    var valueid: Int = 0,
    @SerializedName("LEVEL")
    var level: Int = 0,
) : Serializable {
    companion object Companion {

        val NAME_USLANG = "USLANG"
        val NAME_USLEVELCOLORS = "USLEVELCOLORS"
        val NAME_USSCANINTERVAL = "USSCANINTERVAL"
        val NAME_USREVIEWINTERVAL = "USREVIEWINTERVAL"

        val NAME_USTEXTBOOK = "USTEXTBOOK"
        val NAME_USDICTNOTE = "USDICTNOTE"
        val NAME_USDICTSREFERENCE = "USDICTSREFERENCE"
        val NAME_USDICTTRANSLATION = "USDICTTRANSLATION"
        val NAME_USVOICE = "USMACVOICE"

        val NAME_USUNITFROM = "USUNITFROM"
        val NAME_USPARTFROM = "USPARTFROM"
        val NAME_USUNITTO = "USUNITTO"
        val NAME_USPARTTO = "USPARTTO"
    }
}

