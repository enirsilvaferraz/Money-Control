package com.system.moneycontrol.model.business

import com.system.moneycontrol.data.repositories.TagRepository
import com.system.moneycontrol.model.entities.Tag

class TagBusiness constructor(private val repository: TagRepository) {

    suspend fun getAll() = repository.getList()

    suspend fun delete(model: Tag) = repository.delete(model)

    suspend fun save(model: Tag) = if (model.key.isNullOrBlank()) {
        repository.save(model)
    } else {
        repository.update(model)
    }

    suspend fun getByName(name: String) = repository.getByName(name)
}