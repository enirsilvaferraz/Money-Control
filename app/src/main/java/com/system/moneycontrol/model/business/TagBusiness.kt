package com.system.moneycontrol.model.business

import com.system.moneycontrol.data.repositories.TagRepository
import com.system.moneycontrol.model.entities.Tag
import javax.inject.Inject

class TagBusiness @Inject constructor(private val repository: TagRepository) {

    fun getAll() = repository.getList()

    fun delete(model: Tag) = repository.delete(model)

    fun save(model: Tag) = if (model.key.isNullOrBlank()) {
        repository.save(model)
    } else {
        repository.update(model)
    }
}