package com.zwstudio.lolly.viewmodels.misc

import com.zwstudio.lolly.models.misc.*
import com.zwstudio.lolly.services.misc.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.Observables
import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ListChangeListener
import tornadofx.*
import java.util.concurrent.TimeUnit

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
    private var INFO_USLEVELCOLORS = MUserSettingInfo()
    var uslevelcolors = mapOf<Int, List<String>>()
    private var INFO_USSCANINTERVAL = MUserSettingInfo()
    val usscaninterval: Int
        get() = getUSValue(INFO_USSCANINTERVAL)!!.toInt()
    private var INFO_USREVIEWINTERVAL = MUserSettingInfo()
    val usreviewinterval: Int
        get() = getUSValue(INFO_USREVIEWINTERVAL)!!.toInt()
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
    private var INFO_USANDROIDVOICE = MUserSettingInfo()
    var usvoice: Int
        get() = (getUSValue(INFO_USANDROIDVOICE) ?: "0").toInt()
        set(value) = setUSValue(INFO_USANDROIDVOICE, value.toString())
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
        val lstScopePatternFilters = listOf("Pattern", "Note", "Tags")
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
            INFO_USANDROIDVOICE = getUSInfo(MUSMapping.NAME_USANDROIDVOICE)
            Observables.zip(dictionaryService.getDictsReferenceByLang(uslang),
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
            }.flatMap {
                if (!dirty) Observable.just(Unit) else userSettingService.update(INFO_USLANG, uslang)
            }.applyIO().subscribe()
        }
        selectedVoiceProperty.addListener { _, _, newValue ->
            if (newValue == null) return@addListener
            val newVal = newValue.id
            val dirty = usvoice != newVal
            usvoice = newVal
            (if (!dirty) Observable.just(Unit) else userSettingService.update(INFO_USANDROIDVOICE, usvoice)).subscribe()
        }
        selectedDictsReference.addListener(ListChangeListener {
            if (it == null) return@ListChangeListener
            val newVal = selectedDictsReference.joinToString(",") { it.dictid.toString() }
            val dirty = usdictsreference != newVal
            usdictsreference = newVal
            (if (!dirty) Observable.just(Unit) else userSettingService.update(INFO_USDICTSREFERENCE, usdictsreference)).subscribe()
        })
        selectedDictNoteProperty.addListener { _, _, newValue ->
            if (newValue == null) return@addListener
            val newVal = newValue.dictid
            val dirty = usdictnote != newVal
            usdictnote = newVal
            (if (!dirty) Observable.just(Unit) else userSettingService.update(INFO_USDICTNOTE, usdictnote)).subscribe()
        }
        selectedDictTranslationProperty.addListener { _, _, newValue ->
            if (newValue == null) return@addListener
            val newVal = newValue.dictid
            val dirty = usdicttranslation != newVal
            usdicttranslation = newVal
            (if (!dirty) Observable.just(Unit) else userSettingService.update(INFO_USDICTTRANSLATION, usdicttranslation)).subscribe()
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
            (if (!dirty) Observable.just(Unit) else userSettingService.update(INFO_USTEXTBOOK, ustextbook)).subscribe()
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
            doUpdateUnitFrom(usunitfrom).flatMap {
                if (toType == UnitPartToType.Unit)
                    doUpdateSingleUnit()
                else if (toType == UnitPartToType.Part || isInvalidUnitPart)
                    doUpdateUnitPartTo()
                else
                    Observable.just(Unit)
            }.subscribe()
        }
        uspartfromItem.addListener { _, oldValue, newValue ->
            if (oldValue == null || newValue == null) return@addListener
            uspartfrom = newValue.value
            doUpdatePartFrom(uspartfrom).flatMap {
                if (toType == UnitPartToType.Part || isInvalidUnitPart)
                    doUpdateUnitPartTo()
                else
                    Observable.just(Unit)
            }.subscribe()
        }
        usunittoItem.addListener { _, oldValue, newValue ->
            if (oldValue == null || newValue == null) return@addListener
            usunitto = newValue.value
            doUpdateUnitTo(usunitto).flatMap {
                if (isInvalidUnitPart)
                    doUpdateUnitPartFrom()
                else
                    Observable.just(Unit)
            }.subscribe()
        }
        usparttoItem.addListener { _, oldValue, newValue ->
            if (oldValue == null || newValue == null) return@addListener
            uspartto = newValue.value
            doUpdatePartTo(uspartto).flatMap {
                if (isInvalidUnitPart)
                    doUpdateUnitPartFrom()
                else
                    Observable.just(Unit)
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

    fun getData(): Observable<Unit> =
        Observables.zip(languageService.getData(),
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
            INFO_USLEVELCOLORS = getUSInfo(MUSMapping.NAME_USLEVELCOLORS)
            INFO_USSCANINTERVAL = getUSInfo(MUSMapping.NAME_USSCANINTERVAL)
            INFO_USREVIEWINTERVAL = getUSInfo(MUSMapping.NAME_USREVIEWINTERVAL)
            val lst = getUSValue(INFO_USLEVELCOLORS)!!.split("\r\n").map { it.split(',') }
            // https://stackoverflow.com/questions/32935470/how-to-convert-list-to-map-in-kotlin
            uslevelcolors = lst.associateBy({ it[0].toInt() }, { listOf(it[1], it[2]) })
            selectedLang = lstLanguages.first { it.id == uslang }
        }

    fun autoCorrectInput(text: String): String =
        autoCorrect(text, lstAutoCorrect, { it.input }, { it.extended })

    fun toggleUnitPart(part: Int): Observable<Unit> =
        if (toType == UnitPartToType.Unit) {
            toType = UnitPartToType.Part
            Observables.zip(doUpdatePartFrom(part), doUpdateUnitPartTo()).map { Unit }
        } else if (toType == UnitPartToType.Part) {
            toType = UnitPartToType.Unit
            doUpdateSingleUnit()
        } else
            Observable.just(Unit)

    fun previousUnitPart(): Observable<Unit> =
        if (toType == UnitPartToType.Unit)
            if (usunitfrom > 1)
                Observables.zip(doUpdateUnitFrom(usunitfrom - 1), doUpdateUnitTo(usunitfrom)).map { Unit }
            else
                Observable.just(Unit)
        else if (uspartfrom > 1)
            Observables.zip(doUpdatePartFrom(uspartfrom - 1), doUpdateUnitPartTo()).map { Unit }
        else if (usunitfrom > 1)
            Observables.zip(doUpdateUnitFrom(usunitfrom - 1), doUpdatePartFrom(partCount), doUpdateUnitPartTo()).map { Unit }
        else
            Observable.just(Unit)

    fun nextUnitPart(): Observable<Unit> =
        if (toType == UnitPartToType.Unit)
            if (usunitfrom < unitCount)
                Observables.zip(doUpdateUnitFrom(usunitfrom + 1), doUpdateUnitTo(usunitfrom)).map { Unit }
            else
                Observable.just(Unit)
        else if (uspartfrom < partCount)
            Observables.zip(doUpdatePartFrom(uspartfrom + 1), doUpdateUnitPartTo()).map { Unit }
        else if (usunitfrom < unitCount)
            Observables.zip(doUpdateUnitFrom(usunitfrom + 1), doUpdatePartFrom(1), doUpdateUnitPartTo()).map { Unit }
        else
            Observable.just(Unit)

    private fun doUpdateUnitPartFrom(): Observable<Unit> =
        Observables.zip(doUpdateUnitFrom(usunitto), doUpdatePartFrom(uspartto)).map { Unit }

    private fun doUpdateUnitPartTo(): Observable<Unit> =
        Observables.zip(doUpdateUnitTo(usunitfrom), doUpdatePartTo(uspartfrom)).map { Unit }

    private fun doUpdateSingleUnit(): Observable<Unit> =
        Observables.zip(doUpdateUnitTo(usunitfrom), doUpdatePartFrom(1), doUpdatePartTo(partCount)).map { Unit }

    private fun doUpdateUnitFrom(v: Int): Observable<Unit> {
        val dirty = usunitfrom != v
        usunitfrom = v
        return if (!dirty) Observable.just(Unit) else userSettingService.update(INFO_USUNITFROM, usunitfrom).applyIO()
    }

    private fun doUpdatePartFrom(v: Int): Observable<Unit> {
        val dirty = uspartfrom != v
        uspartfrom = v
        return if (!dirty) Observable.just(Unit) else userSettingService.update(INFO_USPARTFROM, uspartfrom).applyIO()
    }

    private fun doUpdateUnitTo(v: Int): Observable<Unit> {
        val dirty = usunitto != v
        usunitto = v
        return if (!dirty) Observable.just(Unit) else userSettingService.update(INFO_USUNITTO, usunitto).applyIO()
    }

    private fun doUpdatePartTo(v: Int): Observable<Unit> {
        val dirty = uspartto != v
        uspartto = v
        return if (!dirty) Observable.just(Unit) else userSettingService.update(INFO_USPARTTO, uspartto).applyIO()
    }

    fun getHtml(url: String): Observable<String> =
        htmlService.getHtml(url)

    fun retrieveNote(word: String): Observable<String> {
        val dictNote = selectedDictNote ?: return Observable.empty()
        val url = dictNote.urlString(word, lstAutoCorrect)
        return getHtml(url).map {
            println(it)
            extractTextFrom(it, dictNote.transform, "") { text, _ -> text }
        }
    }

    fun retrieveNotes(wordCount: Int, isNoteEmpty: (Int) -> Boolean, getOne: (Int) -> Unit, allComplete: () -> Unit) {
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
