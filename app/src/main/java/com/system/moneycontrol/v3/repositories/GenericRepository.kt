package com.system.moneycontrol.v3.repositories

interface GenericRepository<ReturnType> {

    suspend fun findAll(order: String): List<ReturnType>

    suspend fun getBy(field: String, value: String): List<ReturnType>

    suspend fun save(model: ReturnType): ReturnType

    suspend fun delete(key: String, model: ReturnType): ReturnType

    suspend fun update(key: String, model: ReturnType): ReturnType
}