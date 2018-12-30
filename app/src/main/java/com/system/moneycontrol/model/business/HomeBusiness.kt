package com.system.moneycontrol.model.business

import com.system.moneycontrol.data.repositories.TagRepository
import com.system.moneycontrol.data.repositories.TransactionRepository
import com.system.moneycontrol.data.repositories.TypeRepository
import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.ui.itemView.ItemRecyclerView
import com.system.moneycontrol.ui.itemView.SummaryItemView
import com.system.moneycontrol.ui.itemView.TitleItemVIew


class HomeBusiness(private val repTransaction: TransactionRepository,
                   private val repTag: TagRepository,
                   private val repType: TypeRepository) {

    suspend fun getViewTransactions(year: String, month: String, viewValues: Boolean): ArrayList<ItemRecyclerView> {

        val viewList = arrayListOf<ItemRecyclerView>()

        val transactions = repTransaction.getList(year, month)

        if (transactions.isNotEmpty()) {

            val tags = repTag.getList()
            val types = repType.getList()

            transactions.forEach { transaction ->
                transaction.moneySpent = if (viewValues) transaction.moneySpent else 0.0
                transaction.refund = if (viewValues) transaction.refund else 0.0
                transaction.tag = tags.filter { it.key == transaction.tag.key }[0]
                transaction.paymentType = types.filter { it.key == transaction.paymentType.key }[0]
            }

            viewList.addAll(configureSummaryList(transactions, tags))
            viewList.addAll(configureTransactionList(transactions, arrayOf("Nubank", "Credicard")))
        }

        return viewList
    }

    private fun configureTransactionList(fetchedList: List<Transaction>, typesName: Array<String>): ArrayList<ItemRecyclerView> {

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
                excluded.addAll(groupItens.map { it.toItemView() })
            }
        }

        orderedList.sortedWith(compareBy({ it.paymentDate }, { it.paymentType.name }, { it.tag.name }))

        finalList.add(TitleItemVIew("Transações Correntes"))
        finalList.addAll(orderedList.map { it.toItemView() })
        finalList.addAll(excluded)

        return finalList
    }

    private fun getGroupedTransaction(groupItens: List<Transaction>): Transaction = with(Transaction()) {
        tag.name = "Pagamentos Agrupados"
        paymentDate = groupItens[0].paymentDate
        paymentType = groupItens[0].paymentType
        moneySpent = groupItens.sumByDouble { it.moneySpent }
        refund = groupItens.sumByDouble { it.refund }
        this
    }

    private fun configureSummaryList(transactions: List<Transaction>, tags: List<Tag>): ArrayList<ItemRecyclerView> {

        val finalList = arrayListOf<ItemRecyclerView>()
        finalList.add(TitleItemVIew("Resumo Geral"))

        tags.forEach { tag ->
            finalList.add(getSummaredTransaction(tag, transactions.filter { it.tag.equals(tag) }))
        }

        return finalList
    }

    private fun getSummaredTransaction(tag: Tag, filteredList: List<Transaction>): ItemRecyclerView = SummaryItemView(
            tag = tag.name,
            price = filteredList.sumByDouble { it.moneySpent },
            refund = filteredList.sumByDouble { it.refund }
    )
}