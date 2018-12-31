package com.system.moneycontrol.model.business

import com.system.moneycontrol.data.repositories.TagGroupRepository
import com.system.moneycontrol.data.repositories.TagRepository
import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.entities.TagGroup

class TagBusiness constructor(private val repository: TagRepository, private val groupRepository: TagGroupRepository) {

    suspend fun findAll(): List<Tag> {

        val tags = repository.findAll("name")
        val groups = groupRepository.findAll()

        tags.forEach { tag -> tag.group = groups.filter { tag.group.key == it.key }[0] }

        return tags
    }

    suspend fun delete(key: String) = repository.delete(key)

    suspend fun save(model: Tag) = if (model.key.isNullOrBlank()) {
        repository.save(model)
    } else {
        repository.update(model.key!!, model)
    }

    suspend fun getByName(name: String) = repository.getBy("name", name)

    suspend fun getGroups(): List<TagGroup> = groupRepository.findAll()
}