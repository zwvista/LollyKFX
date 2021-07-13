package com.zwstudio.lolly.viewmodels.patterns

import com.zwstudio.lolly.models.wpp.MPatternWebPage
import tornadofx.*

class PatternsWebPagesDetailViewModel(val vm: PatternsWebPagesViewModel, item: MPatternWebPage) : ItemViewModel<MPatternWebPage>(item) {
    val id = bind(MPatternWebPage::id)
    val patternid = bind(MPatternWebPage::patternid)
    val pattern = bind(MPatternWebPage::pattern)
    val seqnum = bind(MPatternWebPage::seqnum)
    val webpageid = bind(MPatternWebPage::webpageid)
    val title = bind(MPatternWebPage::title)
    val url = bind(MPatternWebPage::url)

    init {
    }

    override fun onCommit() {
        super.onCommit()
        (if (item.webpageid == 0)
            vm.createWebPage(item)
        else
            vm.updateWebPage(item)).mergeWith {
            (if (item.id == 0)
                vm.createPatternWebPage(item)
            else
                vm.updatePatternWebPage(item))
        }.subscribe()
    }
}
