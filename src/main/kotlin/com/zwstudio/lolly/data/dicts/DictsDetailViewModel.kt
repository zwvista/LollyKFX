package com.zwstudio.lolly.data.dicts

import com.zwstudio.lolly.domain.misc.MCode
import com.zwstudio.lolly.domain.misc.MDictionary
import com.zwstudio.lolly.domain.misc.MLanguage
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*

class DictsDetailViewModel(val vm: DictsViewModel, item: MDictionary) : ItemViewModel<MDictionary>(item) {
    val id = bind(MDictionary::id)
    val langnamefrom = bind(MDictionary::langnamefrom)
    val langtoitem: SimpleObjectProperty<MLanguage>
    val seqnum = bind(MDictionary::seqnum)
    val dicttypeitem: SimpleObjectProperty<MCode>
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
        item.langtoitem = vm.vmSettings.lstLanguagesAll.first { it.id == item.langidto }
        item.dicttypeitem = vm.vmSettings.lstDictTypeCodes.first { it.code == item.dicttypecode }
        langtoitem = bind(MDictionary::langtoitem)
        dicttypeitem = bind(MDictionary::dicttypeitem)
    }

    override fun onCommit() {
        super.onCommit()
        item.langidto = item.langtoitem!!.id
        item.dicttypecode = item.dicttypeitem!!.code
        if (item.id == 0)
            vm.createDict(item).subscribe()
        else
            vm.updateDict(item).subscribe()
        if (item.id != 0)
            vm.updateSite(item).subscribe()
        else if (item.transform.isEmpty())
            vm.createSite(item).subscribe()
    }
}