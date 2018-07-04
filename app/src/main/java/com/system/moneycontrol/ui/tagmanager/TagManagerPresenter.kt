package com.system.moneycontrol.ui.tagmanager

import com.system.moneycontrol.model.business.TagBusiness
import javax.inject.Inject

class TagManagerPresenter @Inject constructor(val view: TagManagerFragment, val business: TagBusiness) : TagManagerContract.Presenter