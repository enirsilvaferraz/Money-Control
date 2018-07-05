package com.system.moneycontrol.model.business

import com.nhaarman.mockitokotlin2.*
import com.system.moneycontrol.BaseTest
import com.system.moneycontrol.infrastruture.ConstantsTest
import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.repositories.TagRepository
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Spy

class TagManagerBusinessTest : BaseTest() {

    @Mock
    lateinit var repository: TagRepository

    @Spy
    @InjectMocks
    lateinit var business: TagManagerBusiness

    val mockSaveValid = Tag("", ConstantsTest.VALID_STRING)
    val mockUpdateValid = Tag(ConstantsTest.VALID_KEY, ConstantsTest.VALID_STRING)

    @Test
    fun save_savingNewValue_success() {

        business.save(mockSaveValid, mock(), mock())

        verify(repository, times(1)).save(any(), any(), any())
        verify(repository, never()).update(any(), any(), any())
    }

    @Test
    fun save_savingUpdate_success() {

        business.save(mockUpdateValid, mock(), mock())

        verify(repository, never()).save(any(), any(), any())
        verify(repository, times(1)).update(any(), any(), any())
    }

    @Test
    fun getAll_multiData_list() {

        business.getAll(mock(), mock())

        verify(repository, times(1)).getList(any(), any())
    }

    @Test
    fun delete_deletingData_success() {

        business.delete(mockUpdateValid, mock(), mock())

        verify(repository, times(1)).delete(any(), any(), any())
    }
}