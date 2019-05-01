package com.system.moneycontrol.business

import com.system.moneycontrol.data.Tag
import com.system.moneycontrol.repositories.TagRepository

class TagBusiness(private val repository: TagRepository) {

    suspend fun save(model: Tag): Tag = if (model.key.isNotBlank()) {
        repository.update(model.key, model)
    } else {
        repository.save(model)
    }

    suspend fun delete(model: Tag): Tag = repository.delete(model.key, model)

    suspend fun findAll() = repository.findAll(Tag::name.name)
}