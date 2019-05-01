package com.system.moneycontrol.v3.repositories

import com.system.moneycontrol.v3.data.Tag

interface TagRepository {

    suspend fun findAll(order: String): List<Tag>

    suspend fun getBy(field: String, value: String): List<Tag>

    suspend fun save(model: Tag): Tag

    suspend fun delete(key: String, model: Tag): Tag

    suspend fun update(key: String, model: Tag): Tag
}
