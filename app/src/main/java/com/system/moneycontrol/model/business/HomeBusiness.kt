package com.system.moneycontrol.model.business

import com.system.moneycontrol.data.repositories.TagRepository
import com.system.moneycontrol.data.repositories.TransactionRepository
import com.system.moneycontrol.model.entities.PaymentType
import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.ui.itemView.ItemRecyclerView
import com.system.moneycontrol.ui.itemView.TitleItemVIew


class HomeBusiness(val repTransaction: TransactionRepository, val repTag: TagRepository, val repType: TypeBusiness) {

    fun getTransactions(year: String, month: String, viewValues: Boolean, onSuccess: ((List<ItemRecyclerView>) -> Unit)?, onFailure: ((Exception) -> Unit)?) {
        repTransaction.getList(year, month)
                .addSuccessList { transactions -> getTag(viewValues, onSuccess, transactions, onFailure) }
                .addFailure(onFailure)
                .execute()
    }

    private fun getTag(viewValues: Boolean, onSuccess: ((List<ItemRecyclerView>) -> Unit)?, transactions: List<Transaction>, onFailure: ((Exception) -> Unit)?) {
        repTag.getList()
                .addSuccessList { tags -> getTypes(viewValues, onSuccess, transactions, tags, onFailure) }
                .addFailure(onFailure)
                .execute()
    }

    private fun getTypes(viewValues: Boolean, onSuccess: ((List<ItemRecyclerView>) -> Unit)?, transactions: List<Transaction>, tags: List<Tag>, onFailure: ((Exception) -> Unit)?) {
        repType.getAll()
                .addSuccessList { types -> onSuccess?.invoke(formatResultTransactions(transactions, tags, types, viewValues)) }
                .addFailure(onFailure)
                .execute()
    }

    private fun formatResultTransactions(transactions: List<Transaction>, tags: List<Tag>, types: List<PaymentType>, viewValues: Boolean): List<ItemRecyclerView> {

        transactions.forEach { transaction ->
            transaction.tag = tags.filter { it.key == transaction.tag.key }[0]
            transaction.paymentType = types.filter { it.key == transaction.paymentType.key }[0]
        }

        return if (transactions.isNotEmpty()) {
            configureGroup(transactions, arrayOf("Nubank", "Credicard"), viewValues)
        } else {
            listOf()
        }
    }

    private fun configureGroup(fetchedList: List<Transaction>, typesName: Array<String>, viewValues: Boolean): ArrayList<ItemRecyclerView> {

        val orderedList = arrayListOf<Transaction>()
        orderedList.addAll(fetchedList)

        val finalList = arrayListOf<ItemRecyclerView>()
        val excluded = arrayListOf<ItemRecyclerView>()

        for (typeName in typesName) {

            val groupItens = orderedList.filter { it.paymentType.name == typeName }

            if (groupItens.isNotEmpty()) {

                orderedList.add(getGroupedTransaction(groupItens))
                orderedList.removeAll(groupItens)

                excluded.add(TitleItemVIew(typeName))
                excluded.addAll(groupItens.map { it.toItemView(viewValues) })
            }
        }

        orderedList.sortedWith(compareBy({ it.paymentDate }, { it.paymentType.name }, { it.tag.name }))

        // finalList.add(TitleItemVIew("Transações Correntes"))
        finalList.addAll(orderedList.map { it.toItemView(viewValues) })
        finalList.addAll(excluded)

        return finalList
    }

    private fun getGroupedTransaction(groupItens: List<Transaction>): Transaction {

        val transactionNubank = Transaction()
        with(transactionNubank) {
            tag.name = "Pagamentos Agrupados"
            paymentDate = groupItens[0].paymentDate
            paymentType = groupItens[0].paymentType
            moneySpent = groupItens.sumByDouble { it.moneySpent }
            refund = groupItens.sumByDouble { it.refund }
        }
        return transactionNubank
    }
}