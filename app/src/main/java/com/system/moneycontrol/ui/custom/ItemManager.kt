package com.system.moneycontrol.ui.custom

import android.content.Context
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import android.widget.FrameLayout
import com.system.moneycontrol.R
import kotlinx.android.synthetic.main.item_manager.view.*

class ItemManager(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    init {

        inflate(context, R.layout.item_manager, this)

        context.theme.obtainStyledAttributes(attrs, R.styleable.ItemManager, 0, 0).apply {

            try {

                label.text = getString(R.styleable.ItemManager_label)

                value.setText(getString(R.styleable.ItemManager_value))
                value.isEnabled = getBoolean(R.styleable.ItemManager_enabled, true)
                value.inputType = if (getBoolean(R.styleable.ItemManager_onlyDigits, false))
                    InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                else
                    InputType.TYPE_CLASS_TEXT

                icon.setBackgroundResource(getResourceId(R.styleable.ItemManager_icon, R.drawable.ic_content_paste_black_24dp))

            } finally {
                recycle()
            }
        }
    }

    fun setValue(value: String) {
        this.value.setText(if (value.isNotBlank()) value else "--")
    }

    fun setWatcher(watcher: TextWatcher) {
        this.value.addTextChangedListener(watcher)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        this.container.setOnClickListener(l)
    }

    fun getEditText(): EditText {
        return value
    }
}