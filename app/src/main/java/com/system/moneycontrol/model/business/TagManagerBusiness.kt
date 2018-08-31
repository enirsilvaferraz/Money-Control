package com.system.moneycontrol.model.business

import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.data.repositories.TagRepository
import javax.inject.Inject

class TagManagerBusiness @Inject constructor(private val repository: TagRepository) {

    fun getAll() = repository.getList()


    fun save(model: Tag, onSuccess: ((Tag) -> Unit)?, onFailure: ((Exception) -> Unit)?) {
        if (model.key.isBlank()) {
            repository.save(model, onSuccess, onFailure)
        } else {
            repository.update(model, onSuccess, onFailure)
        }
    }

    fun delete(model: Tag, onSuccess: ((Tag) -> Unit)?, onFailure: ((Exception) -> Unit)?) {
        repository.delete(model, onSuccess, onFailure)
    }
}