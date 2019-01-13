package com.system.moneycontrol.ui.custom

import android.content.Context
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.system.moneycontrol.R
import kotlinx.android.synthetic.main.item_manager.view.*

class ItemManager(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private var attrEnabled: Boolean = true

    init {

        inflate(context, R.layout.item_manager, this)

        context.theme.obtainStyledAttributes(attrs, R.styleable.ItemManager, 0, 0).apply {

            try {

                attrEnabled = getBoolean(R.styleable.ItemManager_enabled, true)

                val attrLabel = getString(R.styleable.ItemManager_label)
                val attrValue = getString(R.styleable.ItemManager_value)
                val attrOnlyDigits = getBoolean(R.styleable.ItemManager_onlyDigits, false)
                val attrIcon = getResourceId(R.styleable.ItemManager_icon, R.drawable.ic_content_paste_black_24dp)

                label.text = attrLabel

                if (attrEnabled) {

                    valueEnabled.hint = attrValue
                    valueEnabled.isEnabled = true
                    valueEnabled.inputType = if (attrOnlyDigits) InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL else InputType.TYPE_CLASS_TEXT

                    valueEnabled.visibility = View.VISIBLE
                    valueDisabled.visibility = View.INVISIBLE

                } else {

                    valueDisabled.hint = attrValue

                    valueEnabled.isEnabled = false
                    valueEnabled.visibility = View.INVISIBLE
                    valueDisabled.visibility = View.VISIBLE
                }

                icon.setBackgroundResource(attrIcon)

            } finally {
                recycle()
            }
        }
    }

    fun setValue(value: String) {
        if (attrEnabled) {
            this.valueEnabled.setText(value)
        } else {
            this.valueDisabled.text = value
        }
    }

    fun setWatcher(watcher: TextWatcher) {
        if (attrEnabled) {
            this.valueEnabled.addTextChangedListener(watcher)
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        this.container.setOnClickListener(l)
    }
}