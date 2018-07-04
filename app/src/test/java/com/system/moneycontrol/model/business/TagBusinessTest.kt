package com.system.moneycontrol.model.business

import com.system.moneycontrol.BaseTest
import com.system.moneycontrol.infrastructure.ConstantsTest
import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.repositories.TagRepository
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Spy

class TagBusinessTest : BaseTest() {

    @Mock
    lateinit var repository: TagRepository

    @Spy
    @InjectMocks
    lateinit var business: TagBusiness

    val mockValid = Tag(ConstantsTest.VALID_KEY, ConstantsTest.VALID_STRING)

    @Test
    fun save_savingNewValue_success() {
        business.save(mockValid, assertTrue(mockValid), assertFalse())
    }

    @Test
    fun save_savingUpdate_success() {
    }

    @Test
    fun save_savingFailure_failure() {
    }

    @Test
    fun getAll_noData_emptyList() {
    }

    @Test
    fun getAll_multiData_list() {
    }

    @Test
    fun getAll_gettingWithError_failure() {
    }

    @Test
    fun delete_deletingData_success() {
    }

    @Test
    fun delete_deletingData_failure() {
    }
}