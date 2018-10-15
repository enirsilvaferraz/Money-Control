package com.system.moneycontrol.ui.presentation.home

import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.business.HomeBusiness
import com.system.moneycontrol.model.business.TransactionBusiness
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.ui.itemView.ItemRecyclerView

class HomePresenter(

        val view: HomeContract.View,
        val business: HomeBusiness,
        val transactionBusiness: TransactionBusiness,
        val utils: MyUtils

) : HomeContract.Presenter {

    override fun init() {
        requestLoad()
    }

    override fun requestLoad() {

        val year = utils.getDate("yyyy")
        val month = utils.getDate("MM")

        view.setTitle(utils.getDate("MMM 'de' yyyy"))

        view.setProgress(10)

        business.getTransactions(year, month, {
            if (it.isNotEmpty()) {
                view.setProgress(100)
                view.configureList(configureListView(it))
            } else {
                view.setProgress(100)
                view.showEmptyState()
            }
        }, {
            view.showError(it.message!!)
        })
    }

    fun configureListView(transactions: List<Transaction>): List<ItemRecyclerView> {
        val itemList = arrayListOf<ItemRecyclerView>()
        transactions.forEach { itemList.add(it.toItemView()) }
        return itemList
    }

    override fun onItemSelected(it: Transaction) {

        view.showTransactionManager(it)

//        transactionBusiness.delete(it)
//                .addSuccessItem {
//                    view.showError("Transaction deleted!")
//                    requestLoad()
//                }
//                .addFailure {
//                    view.showError(it.message!!)
//                }
//                .execute()
    }
}