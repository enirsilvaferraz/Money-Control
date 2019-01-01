package com.system.moneycontrol.model.business

import com.system.moneycontrol.data.repositories.TagGroupRepository
import com.system.moneycontrol.data.repositories.TransactionRepository
import com.system.moneycontrol.model.entities.ReportType
import com.system.moneycontrol.ui.itemView.ItemRecyclerView


class HomeBusiness(private val repTransaction: TransactionRepository,
                   private val tagBusiness: TagBusiness,
                   private val typeBusiness: TypeBusiness,
                   private val repTagGroup: TagGroupRepository) {

    suspend fun getViewTransactions(year: String, month: String, viewValues: Boolean, reportType: ReportType): List<ItemRecyclerView> {

        val transactions = repTransaction.findAll(year, month)

        if (transactions.isNotEmpty()) {

            val tags = tagBusiness.findAll()
            val tagGroups = repTagGroup.findAll()

            tags.forEach { tag -> tag.group = tagGroups.filter { tag.group.key == it.key }[0] }

            val types = typeBusiness.findAll()

            transactions.forEach { transaction ->
                transaction.moneySpent = if (viewValues) transaction.moneySpent else 0.0
                transaction.refund = if (viewValues) transaction.refund else 0.0
                transaction.tag = tags.filter { it.key == transaction.tag.key }[0]
                transaction.paymentType = types.filter { it.key == transaction.paymentType.key }[0]
            }

            return arrayListOf<ItemRecyclerView>().apply {
                addAll(HomeReportBusiness.TagReport().getReport(transactions, tags, tagGroups))
                addAll(HomeReportBusiness.TransactionReport().getReport(transactions))
            }
        }

        return arrayListOf()
    }
}