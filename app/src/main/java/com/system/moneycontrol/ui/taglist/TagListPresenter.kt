package com.system.moneycontrol.ui.taglist

import com.system.moneycontrol.model.business.TagManagerBusiness
import javax.inject.Inject

class TagListPresenter @Inject constructor (
        private val view: TagListContract.View,
        private val business: TagManagerBusiness) : TagListContract.Presenter {


}