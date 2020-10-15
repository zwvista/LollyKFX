package com.zwstudio.lolly.data.misc

import com.zwstudio.lolly.domain.misc.MDictionary
import tornadofx.*

class DictsDetailViewModel(val vm: DictsViewModel, item: MDictionary) : ItemViewModel<MDictionary>(item) {
    val id = bind(MDictionary::id)
    val langnamefrom = bind(MDictionary::langnamefrom)
    val langtoitem = bind(MDictionary::langtoitem)
    val seqnum = bind(MDictionary::seqnum)
    val dicttypeitem = bind(MDictionary::dicttypeitem)
    val dictname = bind(MDictionary::dictname)
    val url = bind(MDictionary::url)
    val chconv = bind(MDictionary::chconv)
    val wait = bind(MDictionary::wait)
    val siteid = bind(MDictionary::siteid)
    val transform = bind(MDictionary::transform)
    val automation = bind(MDictionary::automation)
    val template = bind(MDictionary::template)
    val template2 = bind(MDictionary::template2)

    init {
    }

    override fun onCommit() {
        super.onCommit()
        if (item.id == 0)
            vm.createDict(item).subscribe()
        else
            vm.updateDict(item).subscribe()
        if (item.id != 0)
            vm.updateSite(item).subscribe()
        else if (item.transform.isNullOrEmpty())
            vm.createSite(item).subscribe()
    }
}
