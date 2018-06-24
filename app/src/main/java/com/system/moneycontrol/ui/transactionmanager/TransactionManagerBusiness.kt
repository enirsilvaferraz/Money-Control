package com.system.moneycontrol.ui.transactionmanager

import com.system.moneycontrol.model.repositories.TransactionRepository
import javax.inject.Inject

class TransactionManagerBusiness @Inject constructor(val repository: TransactionRepository) : TransactionManagerContract.Business {
}