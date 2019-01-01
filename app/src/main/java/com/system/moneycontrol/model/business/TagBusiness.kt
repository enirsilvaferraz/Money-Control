package com.system.moneycontrol.model.business

import com.system.moneycontrol.data.repositories.TagGroupRepository
import com.system.moneycontrol.data.repositories.TagRepository
import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.entities.TagGroup

class TagBusiness constructor(private val repository: TagRepository, private val groupRepository: TagGroupRepository) {

    suspend fun findAll() = repository.findAll()

    suspend fun delete(model: Tag) = repository.delete(model)

    suspend fun save(model: Tag) = if (model.key.isNullOrBlank()) {
        repository.save(model)
    } else {
        repository.update(model)
    }

    suspend fun getByName(name: String) = repository.getByName(name)

    suspend fun getGroups(): List<TagGroup> = groupRepository.findAll()

    suspend fun getByKey(key: String): Tag = repository.getByKey(key)
}