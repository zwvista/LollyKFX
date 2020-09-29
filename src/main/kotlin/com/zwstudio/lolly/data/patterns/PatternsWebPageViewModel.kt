package com.zwstudio.lolly.data.patterns

import com.zwstudio.lolly.domain.wpp.MPatternWebPage
import tornadofx.*

class PatternsWebPageViewModel(val vm: PatternsViewModel, item: MPatternWebPage) : ItemViewModel<MPatternWebPage>(item) {
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
        if (item.webpageid == 0)
            vm.createWebPage(item).subscribe()
        else
            vm.updateWebPage(item).subscribe()
        if (item.id == 0)
            vm.createPatternWebPage(item).subscribe()
        else
            vm.updatePatternWebPage(item).subscribe()
    }
}
