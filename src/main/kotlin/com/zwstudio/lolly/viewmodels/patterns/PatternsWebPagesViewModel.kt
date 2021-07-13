package com.zwstudio.lolly.viewmodels.patterns

import com.zwstudio.lolly.models.wpp.MPatternWebPage
import com.zwstudio.lolly.services.wpp.PatternWebPageService
import com.zwstudio.lolly.services.wpp.WebPageService
import com.zwstudio.lolly.viewmodels.misc.BaseViewModel
import com.zwstudio.lolly.viewmodels.misc.applyIO
import io.reactivex.rxjava3.core.Completable
import tornadofx.*

class PatternsWebPagesViewModel : BaseViewModel() {

    var lstWebPages = mutableListOf<MPatternWebPage>().asObservable()

    private val patternWebPageService: PatternWebPageService by inject()
    private val webPageService: WebPageService by inject()

    init {
    }

    fun getWebPages(patternid: Int) =
        patternWebPageService.getDataByPattern(patternid)
            .applyIO()
            .doAfterSuccess { lstWebPages.setAll(it) }

    fun updatePatternWebPage(item: MPatternWebPage) =
        patternWebPageService.update(item)
    fun createPatternWebPage(item: MPatternWebPage): Completable =
        patternWebPageService.create(item)
            .applyIO()
            .doAfterSuccess {
                item.id = it
                lstWebPages.add(item)
            }.flatMapCompletable { Completable.complete() }
    fun deletePatternWebPage(id: Int) =
        patternWebPageService.delete(id)

    fun updateWebPage(item: MPatternWebPage) =
        webPageService.update(item)
    fun createWebPage(item: MPatternWebPage): Completable =
        webPageService.create(item)
            .applyIO()
            .doAfterSuccess { item.id = it }
            .flatMapCompletable { Completable.complete() }
    fun deleteWebPage(id: Int) =
        webPageService.delete(id)

    fun newPatternWebPage(patternid: Int, pattern: String) = MPatternWebPage().apply {
        this.patternid = patternid
        this.pattern = pattern
        seqnum = (lstWebPages.maxOfOrNull { it.seqnum } ?: 0) + 1
    }
}
