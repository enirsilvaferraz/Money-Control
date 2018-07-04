package com.system.moneycontrol.model.business

import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.repositories.TagRepository
import javax.inject.Inject

class TagBusiness @Inject constructor(val repository: TagRepository) {

    fun createNewKey(model: Tag) = "KEY_${model.name.replace(" ", "_").toLowerCase()}"

    fun getAll(onSuccess: ((List<Tag>) -> Unit)?, onFailure: ((Exception) -> Unit)?) {

    }

    fun save(model: Tag, onSuccess: ((Tag) -> Unit)?, onFailure: ((Exception) -> Unit)?) {
    }

    fun delete(model: Tag, onSuccess: ((Tag) -> Unit)?, onFailure: ((Exception) -> Unit)?) {
    }


}