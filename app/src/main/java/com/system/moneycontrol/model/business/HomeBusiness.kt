package com.system.moneycontrol.model.business

import com.system.moneycontrol.data.repositories.TagGroupRepository
import com.system.moneycontrol.data.repositories.TagRepository
import com.system.moneycontrol.data.repositories.TransactionRepository
import com.system.moneycontrol.data.repositories.TypeRepository
import com.system.moneycontrol.model.entities.ReportType
import com.system.moneycontrol.ui.itemView.ItemRecyclerView


class HomeBusiness(private val repTransaction: TransactionRepository,
                   private val repTag: TagRepository,
                   private val repType: TypeRepository,
                   private val repTagGroup: TagGroupRepository) {

    suspend fun getViewTransactions(year: String, month: String, viewValues: Boolean, reportType: ReportType): List<ItemRecyclerView> {

        val transactions = repTransaction.getList(year, month)

        if (transactions.isNotEmpty()) {

            val tags = repTag.getList()
            val tagGroups = repTagGroup.getList()

            tags.forEach { tag -> tag.group = tagGroups.filter { tag.group.key == it.key }[0] }

            val types = repType.getList()

            transactions.forEach { transaction ->
                transaction.moneySpent = if (viewValues) transaction.moneySpent else 0.0
                transaction.refund = if (viewValues) transaction.refund else 0.0
                transaction.tag = tags.filter { it.key == transaction.tag.key }[0]
                transaction.paymentType = types.filter { it.key == transaction.paymentType.key }[0]
            }


            val list = arrayListOf<ItemRecyclerView>()

            list.addAll(HomeReportBusiness.TagReport().getReport(transactions, tags, tagGroups))
            list.addAll(HomeReportBusiness.TransactionReport().getReport(transactions, tags))

            // HomeReportBusiness.TypeReport().getReport(transactions, types)

            return list
        }

        return arrayListOf()
    }
}