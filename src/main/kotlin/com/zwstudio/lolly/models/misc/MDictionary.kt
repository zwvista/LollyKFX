package com.zwstudio.lolly.models.misc

// Generated 2014-10-12 21:44:14 by Hibernate Tools 4.3.1

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.zwstudio.lolly.common.applyTemplate
import com.zwstudio.lolly.common.extractTextFrom
import java.io.Serializable
import java.net.URLEncoder

data class MDictionaries(
    @SerializedName("records")
    var lst: List<MDictionary> = emptyList()
)

data class MDictionary(
    @Expose(serialize = false, deserialize = true)
    @SerializedName("ID")
    var id: Int = 0,
    @SerializedName("DICTID")
    var dictid: Int = 0,
    @SerializedName("LANGIDFROM")
    var langidfrom: Int = 0,
    @SerializedName("LANGNAMEFROM")
    var langnamefrom: String = "",
    @SerializedName("LANGIDTO")
    var langidto: Int = 0,
    @SerializedName("LANGNAMETO")
    var langnameto: String = "",
    @SerializedName("SEQNUM")
    var seqnum: Int = 0,
    @SerializedName("DICTTYPECODE")
    var dicttypecode: Int = 0,
    @SerializedName("DICTTYPENAME")
    var dicttypename: String = "",
    @SerializedName("NAME")
    var dictname: String = "",
    @SerializedName("URL")
    var url: String = "",
    @SerializedName("CHCONV")
    var chconv: String = "",
    @SerializedName("SITEID")
    var siteid: Int = 0,
    @SerializedName("AUTOMATION")
    var automation: String = "",
    @SerializedName("TRANSFORM")
    var transform: String = "",
    @SerializedName("WAIT")
    var wait: Int = 0,
    @SerializedName("TEMPLATE")
    var template: String = "",
    @SerializedName("TEMPLATE2")
    var template2: String = "",
) : Serializable {
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
