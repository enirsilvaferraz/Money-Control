package com.system.moneycontrol.view.transaction

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.system.moneycontrol.R
import com.system.moneycontrol.data.Account
import com.system.moneycontrol.data.Tag
import com.system.moneycontrol.view.GenericAdapter
import kotlinx.android.synthetic.main.activity_transaction_cad.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class TransactionManagerActivity : AppCompatActivity(), TransactionManagerContract.View {

    val presenter: TransactionManagerContract.Presenter by inject { parametersOf(this) }

    lateinit var bottomSheet: BottomSheetDialog
    lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_cad)

        buttomSelectTag.setOnClickListener {
            GlobalScope.launch(Main) {
                presenter.onSelectTagClicked()
            }
        }

        buttomSelectAccount.setOnClickListener {
            GlobalScope.launch(Main) {
                presenter.onSelectAccountClicked()
            }
        }

        saveButton.setOnClickListener {
            GlobalScope.launch(Main) {
                presenter.onSaveClicked(
                        null,
                        valueField.text.toString(),
                        dateField.text.toString(),
                        descriptionField.text.toString(),
                        tagField.text.toString(),
                        accountField.text.toString(),
                        typeField.text.toString()
                )
            }
        }

        bottomSheet = BottomSheetDialog(this)
        bottomSheet.setContentView(layoutInflater.inflate(R.layout.bottomsheet_default_list, null))

        recycler = bottomSheet.findViewById(R.id.bottomsheet_recyclerview)!!
        recycler.layoutManager = LinearLayoutManager(bottomSheet.context)
        recycler.adapter = GenericAdapter {
            when (it) {
                is Tag -> tagField.setText(it.name)
                is Account -> tagField.setText(it.name)
            }
        }

        presenter.start()
    }

    override fun showSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showFailure() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showRequiredMessageValue() {
        valueError.text = getString(R.string.system_required_field)
    }

    override fun showRequiredMessageDate() {
        dateError.text = getString(R.string.system_required_field)
    }

    override fun showRequiredMessageTag() {
        tagError.text = getString(R.string.system_required_field)
    }

    override fun showRequiredMessageAccount() {
        accountError.text = getString(R.string.system_required_field)
    }

    override fun showListPicker(data: List<Any>) {
        (recycler.adapter as GenericAdapter).setData(data.toMutableList())
        bottomSheet.show()
    }
}
