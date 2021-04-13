package com.zwstudio.lolly.models.misc

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MUSMappings {

    @SerializedName("records")
    var lst: List<MUSMapping>? = null
}

class MUSMapping: Serializable {

    @SerializedName("ID")
    var id = 0
    @SerializedName("NAME")
    var name = ""
    @SerializedName("KIND")
    var kind = 0
    @SerializedName("ENTITYID")
    var entityid = 0
    @SerializedName("VALUEID")
    var valueid = 0
    @SerializedName("LEVEL")
    var level = 0

    companion object Companion {

        val NAME_USLANG = "USLANG"
        val NAME_USROWSPERPAGEOPTIONS = "USROWSPERPAGEOPTIONS"
        val NAME_USROWSPERPAGE = "USROWSPERPAGE"
        val NAME_USLEVELCOLORS = "USLEVELCOLORS"
        val NAME_USSCANINTERVAL = "USSCANINTERVAL"
        val NAME_USREVIEWINTERVAL = "USREVIEWINTERVAL"

        val NAME_USTEXTBOOK = "USTEXTBOOK"
        val NAME_USDICTREFERENCE = "USDICTREFERENCE"
        val NAME_USDICTNOTE = "USDICTNOTE"
        val NAME_USDICTSREFERENCE = "USDICTSREFERENCE"
        val NAME_USDICTTRANSLATION = "USDICTTRANSLATION"
        val NAME_USMACVOICE = "USMACVOICE"
        val NAME_USIOSVOICE = "USIOSVOICE"
        val NAME_USANDROIDVOICE = "USANDROIDVOICE"
        val NAME_USWEBVOICE = "USWEBVOICE"
        val NAME_USWINDOWSVOICE = "USWINDOWSVOICE"

        val NAME_USUNITFROM = "USUNITFROM"
        val NAME_USPARTFROM = "USPARTFROM"
        val NAME_USUNITTO = "USUNITTO"
        val NAME_USPARTTO = "USPARTTO"
    }
}
