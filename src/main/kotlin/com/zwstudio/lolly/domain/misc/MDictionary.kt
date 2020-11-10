package com.zwstudio.lolly.domain.misc

// Generated 2014-10-12 21:44:14 by Hibernate Tools 4.3.1

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.zwstudio.lolly.data.misc.applyTemplate
import com.zwstudio.lolly.data.misc.extractTextFrom
import java.io.Serializable
import java.net.URLEncoder

class MDictionaries {

    @SerializedName("records")
    @Expose
    var lst: List<MDictionary>? = null
}

class MDictionary: Serializable {

    @SerializedName("ID")
    @Expose
    var id = 0
    @SerializedName("DICTID")
    @Expose
    var dictid = 0
    @SerializedName("LANGIDFROM")
    @Expose
    var langidfrom = 0
    @SerializedName("LANGNAMEFROM")
    @Expose
    var langnamefrom = ""
    @SerializedName("LANGIDTO")
    @Expose
    var langidto = 0
    @SerializedName("LANGNAMETO")
    @Expose
    var langnameto = ""
    @SerializedName("SEQNUM")
    @Expose
    var seqnum = 0
    @SerializedName("DICTTYPECODE")
    @Expose
    var dicttypecode = 0
    @SerializedName("DICTTYPENAME")
    @Expose
    var dicttypename = ""
    @SerializedName("NAME")
    @Expose
    var dictname = ""
    @SerializedName("URL")
    @Expose
    var url = ""
    @SerializedName("CHCONV")
    @Expose
    var chconv = ""
    @SerializedName("SITEID")
    @Expose
    var siteid = 0
    @SerializedName("AUTOMATION")
    @Expose
    var automation = ""
    @SerializedName("TRANSFORM")
    @Expose
    var transform = ""
    @SerializedName("WAIT")
    @Expose
    var wait = 0
    @SerializedName("TEMPLATE")
    @Expose
    var template = ""
    @SerializedName("TEMPLATE2")
    @Expose
    var template2 = ""

    var langtoitem: MLanguage? = null
    var dicttypeitem: MCode? = null

    fun urlString(word: String, lstAutoCorrects: List<MAutoCorrect>): String {
        val word2 =
            if (chconv == "BASIC")
                autoCorrect(word, lstAutoCorrects, { it.extended }, { it.basic })
            else
                word
        val wordUrl = url.replace("{0}", URLEncoder.encode(word2, "UTF-8"))
        println("urlString: $wordUrl")
        return wordUrl
    }

    fun htmlString(html: String, word: String, useTemplate2: Boolean): String {
        val t = if (useTemplate2 && !template2.isEmpty()) template2 else template
        return extractTextFrom(html, transform, t) { text, template -> applyTemplate(template, word, text) }
    }
}
