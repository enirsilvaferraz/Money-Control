package com.system.moneycontrol.model.business

import com.system.moneycontrol.model.entities.PaymentType
import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.entities.TagGroup
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.ui.itemView.*

interface HomeReportBusiness {

    class TransactionReport {

        fun getReport(transactions: List<Transaction>): ArrayList<ItemRecyclerView> {

            val orderedList = arrayListOf<Transaction>()
            orderedList.addAll(transactions)

            val finalList = arrayListOf<ItemRecyclerView>()
            val excluded = arrayListOf<ItemRecyclerView>()

            for (typeName in arrayOf("Nubank", "Credicard")) {

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
    }

    class TagReport {

        fun getReport(transactions: List<Transaction>, tags: List<Tag>, tagGroups: List<TagGroup>): List<ItemRecyclerView> {

            val endList = arrayListOf<ItemRecyclerView>()

            endList.add(TitleItemVIew("Tags"))

            tagGroups.forEach { group ->

                val tagFilter = tags.filter { tag -> tag.group.key == group.key }

                tagFilter.forEach { tag ->
                    val transactionFilter = transactions.filter { it.tag.key == tag.key }
                    tag.sumPrice = transactionFilter.sumByDouble { it.moneySpent }
                    tag.sumRefound = transactionFilter.sumByDouble { it.refund }
                }

                endList.add(GroupTagItemView(group, tagFilter.sumByDouble { it.sumPrice }, tagFilter.sumByDouble { it.sumRefound }))

                endList.addAll(tagFilter.map { tag -> TagItemView(tag) })
            }

            return endList
        }
    }

    class TypeReport {

        fun getReport(transactions: List<Transaction>, payments: List<PaymentType>): List<ItemRecyclerView> {
            return payments.map { PaymentTypeItemView(it) }
        }
    }
}