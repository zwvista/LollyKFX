package com.zwstudio.lolly.viewmodels.misc

import com.zwstudio.lolly.models.misc.*
import com.zwstudio.lolly.services.misc.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.Singles
import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ListChangeListener
import tornadofx.*
import java.util.concurrent.TimeUnit

enum class UnitPartToType {
    Unit, Part, To
}

class SettingsViewModel : Component(), ScopedInstance {

    var lstUSMappings = listOf<MUSMapping>()
    var lstUserSettings = listOf<MUserSetting>()
    private fun getUSValue(info: MUserSettingInfo): String? {
        val o = lstUserSettings.find { it.id == info.usersettingid }!!
        return when (info.valueid) {
            1 -> o.value1
            2 -> o.value2
            3 -> o.value3
            4 -> o.value4
            else -> null
        }
    }
    private fun setUSValue(info: MUserSettingInfo, value: String) {
        val o = lstUserSettings.find { it.id == info.usersettingid }!!
        when (info.valueid) {
            1 -> o.value1 = value
            2 -> o.value2 = value
            3 -> o.value3 = value
            4 -> o.value4 = value
            else -> {}
        }
    }
    private var INFO_USLANG = MUserSettingInfo()
    var uslang: Int
        get() = getUSValue(INFO_USLANG)!!.toInt()
        set(value) = setUSValue(INFO_USLANG, value.toString())
    private var INFO_USTEXTBOOK = MUserSettingInfo()
    var ustextbook: Int
        get() = getUSValue(INFO_USTEXTBOOK)!!.toInt()
        set(value) = setUSValue(INFO_USTEXTBOOK, value.toString())
    private var INFO_USDICTSREFERENCE = MUserSettingInfo()
    var usdictsreference: String
        get() = getUSValue(INFO_USDICTSREFERENCE) ?: ""
        set(value) = setUSValue(INFO_USDICTSREFERENCE, value)
    private var INFO_USDICTNOTE = MUserSettingInfo()
    var usdictnote: Int
        get() = (getUSValue(INFO_USDICTNOTE) ?: "0").toInt()
        set(value) = setUSValue(INFO_USDICTNOTE, value.toString())
    private var INFO_USDICTTRANSLATION = MUserSettingInfo()
    var usdicttranslation: Int
        get() = (getUSValue(INFO_USDICTTRANSLATION) ?: "0").toInt()
        set(value) = setUSValue(INFO_USDICTTRANSLATION, value.toString())
    private var INFO_USVOICE = MUserSettingInfo()
    var usvoice: Int
        get() = (getUSValue(INFO_USVOICE) ?: "0").toInt()
        set(value) = setUSValue(INFO_USVOICE, value.toString())
    private var INFO_USUNITFROM = MUserSettingInfo()
    var usunitfrom: Int
        get() = getUSValue(INFO_USUNITFROM)!!.toInt()
        set(value) {
            setUSValue(INFO_USUNITFROM, value.toString())
            usunitfromItem.value = lstUnits.first { it.value == usunitfrom }
        }
    val usunitfromstr get() = selectedTextbook.unitstr(usunitfrom)
    private var INFO_USPARTFROM = MUserSettingInfo()
    var uspartfrom: Int
        get() = getUSValue(INFO_USPARTFROM)!!.toInt()
        set(value) {
            setUSValue(INFO_USPARTFROM, value.toString())
            uspartfromItem.value = lstParts.first { it.value == uspartfrom }
        }
    val uspartfromstr get() = selectedTextbook.partstr(uspartfrom)
    private var INFO_USUNITTO = MUserSettingInfo()
    var usunitto: Int
        get() = getUSValue(INFO_USUNITTO)!!.toInt()
        set(value) {
            setUSValue(INFO_USUNITTO, value.toString())
            usunittoItem.value = lstUnits.first { it.value == usunitto }
        }
    val usunittostr get() = selectedTextbook.unitstr(usunitto)
    private var INFO_USPARTTO = MUserSettingInfo()
    var uspartto: Int
        get() = getUSValue(INFO_USPARTTO)!!.toInt()
        set(value) {
            setUSValue(INFO_USPARTTO, value.toString())
            usparttoItem.value = lstParts.first { it.value == uspartto }
        }
    val usparttostr get() = selectedTextbook.partstr(uspartto)
    val usunitpartfrom: Int
        get() = usunitfrom * 10 + uspartfrom
    val usunitpartto: Int
        get() = usunitto * 10 + uspartto
    val isSingleUnitPart: Boolean
        get() = usunitpartfrom == usunitpartto
    val isSingleUnit: Boolean
        get() = usunitfrom == usunitto && uspartfrom == 1 && uspartto == partCount
    val isInvalidUnitPart: Boolean
        get() = usunitpartfrom > usunitpartto

    var lstLanguagesAll = listOf<MLanguage>()
    var lstLanguages = listOf<MLanguage>()
    val selectedLangProperty = SimpleObjectProperty<MLanguage>()
    var selectedLang by selectedLangProperty

    var lstVoices = mutableListOf<MVoice>().asObservable()
    val selectedVoiceProperty = SimpleObjectProperty<MVoice>()
    var selectedVoice by selectedVoiceProperty

    var lstTextbooks = mutableListOf<MTextbook>().asObservable()
    val selectedTextbookProperty = SimpleObjectProperty<MTextbook>()
    var selectedTextbook by selectedTextbookProperty
    var lstTextbookFilters = listOf<MSelectItem>()

    var lstDictsReference = listOf<MDictionary>()
    var selectedDictsReference = mutableListOf<MDictionary>().asObservable()

    var lstDictsNote = mutableListOf<MDictionary>().asObservable()
    val selectedDictNoteProperty = SimpleObjectProperty<MDictionary>()
    var selectedDictNote by selectedDictNoteProperty

    var lstDictsTranslation = mutableListOf<MDictionary>().asObservable()
    val selectedDictTranslationProperty = SimpleObjectProperty<MDictionary>()
    var selectedDictTranslation by selectedDictTranslationProperty

    val lstUnits = mutableListOf<MSelectItem>().asObservable()
    var usunitfromItem = SimpleObjectProperty<MSelectItem>()
    var usunittoItem = SimpleObjectProperty<MSelectItem>()
    val unitCount: Int
        get() = lstUnits.size
    val unitsInAll = SimpleStringProperty()
    val lstParts = mutableListOf<MSelectItem>().asObservable()
    var uspartfromItem = SimpleObjectProperty<MSelectItem>()
    var usparttoItem = SimpleObjectProperty<MSelectItem>()
    val partCount: Int
        get() = lstParts.size
    val isSinglePart: Boolean
        get() = partCount == 1
    val langInfo get() = selectedLang.langname
    val textbookInfo get() = "$langInfo/${selectedTextbook.textbookname}"
    val unitInfo get() = "$textbookInfo/$usunitfromstr $uspartfromstr ~ $usunittostr $usparttostr"

    var lstAutoCorrect = listOf<MAutoCorrect>()
    var lstDictTypeCodes = listOf<MCode>()

    val lstToTypes = UnitPartToType.values().map { v -> Pair(v, v.toString()) }
    val toTypeProperty = SimpleObjectProperty<Pair<UnitPartToType, String>>()
    var toType: UnitPartToType
        get() = toTypeProperty.value.first
        set(value) = toTypeProperty.setValue(lstToTypes[value.ordinal])
    val toTypeMovable get() = toType != UnitPartToType.To
    val unitToEnabled = SimpleBooleanProperty()
    val partToEnabled = SimpleBooleanProperty()
    val previousEnabled = SimpleBooleanProperty()
    val nextEnabled = SimpleBooleanProperty()
    val previousText = SimpleStringProperty()
    val nextText = SimpleStringProperty()
    val partFromEnabled = SimpleBooleanProperty()

    companion object {
        val lstScopeWordFilters = listOf("Word", "Note")
        val lstScopePhraseFilters = listOf("Phrase", "Translation")
        val lstScopePatternFilters = listOf("Pattern", "Tags")
        val lstReviewModes = ReviewMode.values().mapIndexed { index, s -> MSelectItem(index, s.toString()) }
        val zeroNote = "O"
    }

    private val languageService: LanguageService by inject()
    private val usMappingService: USMappingService by inject()
    private val userSettingService: UserSettingService by inject()
    private val codeService: CodeService by inject()
    private val dictionaryService: DictionaryService by inject()
    private val textbookService: TextbookService by inject()
    private val autoCorrectService: AutoCorrectService by inject()
    private val voiceService: VoiceService by inject()
    private val htmlService: HtmlService by inject()
    private val compositeDisposable = CompositeDisposable()

    init {
        selectedLangProperty.addListener { _, _, newValue ->
            if (newValue == null) return@addListener
            val newVal = newValue.id
            val dirty = uslang != newVal
            uslang = newVal
            INFO_USTEXTBOOK = getUSInfo(MUSMapping.NAME_USTEXTBOOK)
            INFO_USDICTSREFERENCE = getUSInfo(MUSMapping.NAME_USDICTSREFERENCE)
            INFO_USDICTNOTE = getUSInfo(MUSMapping.NAME_USDICTNOTE)
            INFO_USDICTTRANSLATION = getUSInfo(MUSMapping.NAME_USDICTTRANSLATION)
            INFO_USVOICE = getUSInfo(MUSMapping.NAME_USVOICE)
            Singles.zip(dictionaryService.getDictsReferenceByLang(uslang),
                dictionaryService.getDictsNoteByLang(uslang),
                dictionaryService.getDictsTranslationByLang(uslang),
                textbookService.getDataByLang(uslang),
                autoCorrectService.getDataByLang(uslang),
                voiceService.getDataByLang(uslang)) {
                res1, res2, res3, res4, res5, res6 ->
                Platform.runLater {
                    lstDictsReference = res1
                    selectedDictsReference.setAll(usdictsreference.split(",").flatMap { d -> lstDictsReference.filter { it.dictid.toString() == d } })
                    lstDictsNote.setAll(res2)
                    selectedDictNote = lstDictsNote.firstOrNull { it.dictid == usdictnote } ?: lstDictsNote.firstOrNull()
                    lstDictsTranslation.setAll(res3)
                    selectedDictTranslation = lstDictsTranslation.firstOrNull { it.dictid == usdicttranslation } ?: lstDictsTranslation.firstOrNull()
                    lstTextbooks.setAll(res4)
                    selectedTextbook = lstTextbooks.first { it.id == ustextbook }
                    lstTextbookFilters = listOf(MSelectItem(0, "All Textbooks")) + lstTextbooks.map { MSelectItem(it.id, it.textbookname) }
                    lstAutoCorrect = res5
                    lstVoices.setAll(res6)
                    selectedVoice = lstVoices.firstOrNull { it.id == usvoice } ?: lstVoices.firstOrNull()
                }
            }.flatMapCompletable {
                if (!dirty) Completable.complete() else userSettingService.update(INFO_USLANG, uslang)
            }.applyIO().subscribe()
        }
        selectedVoiceProperty.addListener { _, _, newValue ->
            if (newValue == null) return@addListener
            val newVal = newValue.id
            val dirty = usvoice != newVal
            usvoice = newVal
            (if (!dirty) Completable.complete() else userSettingService.update(INFO_USVOICE, usvoice)).subscribe()
        }
        selectedDictsReference.addListener(ListChangeListener {
            if (it == null) return@ListChangeListener
            val newVal = selectedDictsReference.joinToString(",") { it.dictid.toString() }
            val dirty = usdictsreference != newVal
            usdictsreference = newVal
            (if (!dirty) Completable.complete() else userSettingService.update(INFO_USDICTSREFERENCE, usdictsreference)).subscribe()
        })
        selectedDictNoteProperty.addListener { _, _, newValue ->
            if (newValue == null) return@addListener
            val newVal = newValue.dictid
            val dirty = usdictnote != newVal
            usdictnote = newVal
            (if (!dirty) Completable.complete() else userSettingService.update(INFO_USDICTNOTE, usdictnote)).subscribe()
        }
        selectedDictTranslationProperty.addListener { _, _, newValue ->
            if (newValue == null) return@addListener
            val newVal = newValue.dictid
            val dirty = usdicttranslation != newVal
            usdicttranslation = newVal
            (if (!dirty) Completable.complete() else userSettingService.update(INFO_USDICTTRANSLATION, usdicttranslation)).subscribe()
        }
        selectedTextbookProperty.addListener { _, _, newValue ->
            if (newValue == null) return@addListener
            val newVal = newValue.id
            val dirty = ustextbook != newVal
            ustextbook = newVal
            lstUnits.setAll(newValue.lstUnits)
            unitsInAll.value = "($unitCount in all)"
            lstParts.setAll(newValue.lstParts)
            INFO_USUNITFROM = getUSInfo(MUSMapping.NAME_USUNITFROM)
            usunitfrom = usunitfrom
            INFO_USPARTFROM = getUSInfo(MUSMapping.NAME_USPARTFROM)
            uspartfrom = uspartfrom
            INFO_USUNITTO = getUSInfo(MUSMapping.NAME_USUNITTO)
            usunitto = usunitto
            INFO_USPARTTO = getUSInfo(MUSMapping.NAME_USPARTTO)
            uspartto = uspartto
            toType = if (isSingleUnit) UnitPartToType.Unit else if (isSingleUnitPart) UnitPartToType.Part else UnitPartToType.To
            (if (!dirty) Completable.complete() else userSettingService.update(INFO_USTEXTBOOK, ustextbook)).subscribe()
        }
        toTypeProperty.addListener { _, oldValue, _ ->
            val b = toType == UnitPartToType.To
            unitToEnabled.value = b
            partToEnabled.value = b && !isSinglePart
            previousEnabled.value = !b
            nextEnabled.value = !b
            val b2 = toType != UnitPartToType.Unit
            val t = if (!b2) "Unit" else "Part"
            previousText.value = "Previous $t"
            nextText.value = "Next $t"
            partFromEnabled.value = b2 && !isSinglePart
            if (oldValue != null)
                if (toType == UnitPartToType.Unit)
                    doUpdateSingleUnit().subscribe()
                else if (toType == UnitPartToType.Part)
                    doUpdateUnitPartTo().subscribe()
        }
        usunitfromItem.addListener { _, oldValue, newValue ->
            if (oldValue == null || newValue == null) return@addListener
            usunitfrom = newValue.value
            doUpdateUnitFrom(usunitfrom).andThen {
                if (toType == UnitPartToType.Unit)
                    doUpdateSingleUnit()
                else if (toType == UnitPartToType.Part || isInvalidUnitPart)
                    doUpdateUnitPartTo()
                else
                    Completable.complete()
            }.subscribe()
        }
        uspartfromItem.addListener { _, oldValue, newValue ->
            if (oldValue == null || newValue == null) return@addListener
            uspartfrom = newValue.value
            doUpdatePartFrom(uspartfrom).andThen {
                if (toType == UnitPartToType.Part || isInvalidUnitPart)
                    doUpdateUnitPartTo()
                else
                    Completable.complete()
            }.subscribe()
        }
        usunittoItem.addListener { _, oldValue, newValue ->
            if (oldValue == null || newValue == null) return@addListener
            usunitto = newValue.value
            doUpdateUnitTo(usunitto).andThen {
                if (isInvalidUnitPart)
                    doUpdateUnitPartFrom()
                else
                    Completable.complete()
            }.subscribe()
        }
        usparttoItem.addListener { _, oldValue, newValue ->
            if (oldValue == null || newValue == null) return@addListener
            uspartto = newValue.value
            doUpdatePartTo(uspartto).andThen {
                if (isInvalidUnitPart)
                    doUpdateUnitPartFrom()
                else
                    Completable.complete()
            }.subscribe()
        }
    }

    private fun getUSInfo(name: String): MUserSettingInfo {
        val o = lstUSMappings.find { it.name == name }!!
        val entityid = when {
            o.entityid != -1 -> o.entityid
            o.level == 1 -> selectedLang.id
            o.level == 2 -> selectedTextbook.id
            else -> 0
        }
        val o2 = lstUserSettings.find { it.kind == o.kind && it.entityid == entityid }!!
        return MUserSettingInfo(o2.id, o.valueid)
    }

    fun getData(): Completable =
        Singles.zip(languageService.getData(),
                usMappingService.getData(),
                userSettingService.getData(),
                codeService.getDictCodes()) {
            res1, res2, res3, res4 ->
            lstLanguagesAll = res1
            lstLanguages = lstLanguagesAll.filter { it.id != 0 }
            lstUSMappings = res2
            lstUserSettings = res3
            lstDictTypeCodes = res4
            INFO_USLANG = getUSInfo(MUSMapping.NAME_USLANG)
            selectedLang = lstLanguages.first { it.id == uslang }
        }.flatMapCompletable { Completable.complete() }

    fun autoCorrectInput(text: String): String =
        autoCorrect(text, lstAutoCorrect, { it.input }, { it.extended })

    fun toggleUnitPart(part: Int): Completable =
        if (toType == UnitPartToType.Unit) {
            toType = UnitPartToType.Part
            Completable.mergeArray(doUpdatePartFrom(part), doUpdateUnitPartTo())
        } else if (toType == UnitPartToType.Part) {
            toType = UnitPartToType.Unit
            doUpdateSingleUnit()
        } else
            Completable.complete()

    fun previousUnitPart(): Completable =
        if (toType == UnitPartToType.Unit)
            if (usunitfrom > 1)
                Completable.mergeArray(doUpdateUnitFrom(usunitfrom - 1), doUpdateUnitTo(usunitfrom))
            else
                Completable.complete()
        else if (uspartfrom > 1)
            Completable.mergeArray(doUpdatePartFrom(uspartfrom - 1), doUpdateUnitPartTo())
        else if (usunitfrom > 1)
            Completable.mergeArray(doUpdateUnitFrom(usunitfrom - 1), doUpdatePartFrom(partCount), doUpdateUnitPartTo())
        else
            Completable.complete()

    fun nextUnitPart(): Completable =
        if (toType == UnitPartToType.Unit)
            if (usunitfrom < unitCount)
                Completable.mergeArray(doUpdateUnitFrom(usunitfrom + 1), doUpdateUnitTo(usunitfrom))
            else
                Completable.complete()
        else if (uspartfrom < partCount)
            Completable.mergeArray(doUpdatePartFrom(uspartfrom + 1), doUpdateUnitPartTo())
        else if (usunitfrom < unitCount)
            Completable.mergeArray(doUpdateUnitFrom(usunitfrom + 1), doUpdatePartFrom(1), doUpdateUnitPartTo())
        else
            Completable.complete()

    private fun doUpdateUnitPartFrom(): Completable =
        Completable.mergeArray(doUpdateUnitFrom(usunitto), doUpdatePartFrom(uspartto))

    private fun doUpdateUnitPartTo(): Completable =
        Completable.mergeArray(doUpdateUnitTo(usunitfrom), doUpdatePartTo(uspartfrom))

    private fun doUpdateSingleUnit(): Completable =
        Completable.mergeArray(doUpdateUnitTo(usunitfrom), doUpdatePartFrom(1), doUpdatePartTo(partCount))

    private fun doUpdateUnitFrom(v: Int): Completable {
        val dirty = usunitfrom != v
        usunitfrom = v
        return if (!dirty) Completable.complete() else userSettingService.update(INFO_USUNITFROM, usunitfrom).applyIO()
    }

    private fun doUpdatePartFrom(v: Int): Completable {
        val dirty = uspartfrom != v
        uspartfrom = v
        return if (!dirty) Completable.complete() else userSettingService.update(INFO_USPARTFROM, uspartfrom).applyIO()
    }

    private fun doUpdateUnitTo(v: Int): Completable {
        val dirty = usunitto != v
        usunitto = v
        return if (!dirty) Completable.complete() else userSettingService.update(INFO_USUNITTO, usunitto).applyIO()
    }

    private fun doUpdatePartTo(v: Int): Completable {
        val dirty = uspartto != v
        uspartto = v
        return if (!dirty) Completable.complete() else userSettingService.update(INFO_USPARTTO, uspartto).applyIO()
    }

    fun getHtml(url: String): Single<String> =
        htmlService.getHtml(url)

    fun getNote(word: String): Single<String> {
        val dictNote = selectedDictNote ?: return Single.just("")
        val url = dictNote.urlString(word, lstAutoCorrect)
        return getHtml(url).map {
            println(it)
            extractTextFrom(it, dictNote.transform, "") { text, _ -> text }
        }
    }

    fun getNotes(wordCount: Int, isNoteEmpty: (Int) -> Boolean, getOne: (Int) -> Unit, allComplete: () -> Unit) {
        val dictNote = selectedDictNote ?: return
        var i = 0
        var subscription: Disposable? = null
        subscription = Observable.interval(dictNote.wait.toLong(), TimeUnit.MILLISECONDS).applyIO().subscribe {
            while (i < wordCount && !isNoteEmpty(i))
                i++
            if (i > wordCount) {
                allComplete()
                subscription?.dispose()
            } else {
                if (i < wordCount)
                    getOne(i)
                i++
            }
        }
        compositeDisposable.add(subscription)
    }

    fun clearNotes(wordCount: Int, isNoteEmpty: (Int) -> Boolean, getOne: (Int) -> Unit, allComplete: () -> Unit) {
        val dictNote = selectedDictNote ?: return
        var i = 0
        while (i < wordCount) {
            while (i < wordCount && !isNoteEmpty(i))
                i++
            if (i < wordCount)
                getOne(i)
            i++
        }
        allComplete()
    }
}
