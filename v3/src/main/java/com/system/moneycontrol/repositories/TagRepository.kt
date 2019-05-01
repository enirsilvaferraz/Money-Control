package com.system.moneycontrol.repositories

import com.system.moneycontrol.data.Tag

interface TagRepository {

    suspend fun findAll(order: String): List<Tag>

    suspend fun getBy(field: String, value: String): List<Tag>

    suspend fun save(model: Tag): Tag

    suspend fun delete(key: String, model: Tag): Tag

    suspend fun update(key: String, model: Tag): Tag
}
