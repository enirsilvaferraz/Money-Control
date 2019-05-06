package com.system.moneycontrol.view.transaction

import com.system.moneycontrol.business.AccountBusiness
import com.system.moneycontrol.business.TagBusiness
import com.system.moneycontrol.business.TransactionBusiness
import com.system.moneycontrol.data.Account
import com.system.moneycontrol.data.Tag
import com.system.moneycontrol.data.Transaction
import com.system.moneycontrol.data.TransactionType
import com.system.moneycontrol.infrastructure.functions.AppFunctions.getDate

class TransactionManagerPresenter(

        private val view: TransactionManagerContract.View,
        private val transacBusiness: TransactionBusiness,
        private val tagBusiness: TagBusiness,
        private val accountBusiness: AccountBusiness

) : TransactionManagerContract.Presenter {

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun onSelectTagClicked() {

        try {
            view.showListPicker(tagBusiness.findAll())
        } catch (e: Exception) {
            view.showFailure()
        }
    }

    override suspend fun onSelectAccountClicked() {

        try {
            view.showListPicker(accountBusiness.findAll())
        } catch (e: Exception) {
            view.showFailure()
        }
    }

    override fun onTagSelected(tag: Tag) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAccountSelected(account: Account) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun onSaveClicked(key: String?, value: String?, date: String?, description: String?, tag: String?, account: String?, type: String) {

        if (value.isNullOrBlank()) {
            view.showRequiredMessageValue()
        }

        if (date.isNullOrBlank()) {
            view.showRequiredMessageDate()
        }

        if (tag.isNullOrBlank()) {
            view.showRequiredMessageTag()
        }

        if (account.isNullOrBlank()) {
            view.showRequiredMessageAccount()
        }

        if (value != null && date != null && !tag.isNullOrBlank() && !account.isNullOrBlank()) {

            // Temporario
            val enum = TransactionType.values().filter { it.key == type }[0]
            save(Transaction(key, value.toDouble(), getDate(date), description, Tag(name = tag), Account(name = account), enum))
        }
    }

    suspend fun save(model: Transaction) {

        try {

            view.showLoading()

            model.tag = tagBusiness.getByName(model.tag.name)
            if (model.tag.key.isNullOrBlank()) {
                model.tag = tagBusiness.save(model.tag)
            }

            model.account = accountBusiness.getByName(model.account.name)
            if (model.account.key.isNullOrBlank()) {
                model.account = accountBusiness.save(model.account)
            }

            transacBusiness.save(model)
            view.showSuccess()

        } catch (e: Exception) {
            view.showFailure()

        } finally {
            view.hideLoading()
        }
    }
}