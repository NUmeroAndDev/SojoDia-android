package com.numero.sojodia.ui.settings.item

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.numero.sojodia.R
import kotlinx.android.synthetic.main.view_settings_item.view.*

class SettingsItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
    init {
        View.inflate(context, R.layout.view_settings_item, this)
        context.withStyledAttributes(attrs, R.styleable.SettingsItemView) {
            val title = getString(R.styleable.SettingsItemView_title)
            setTitle(title)
            val summary = getString(R.styleable.SettingsItemView_summary)
            setSummary(summary)
            val icon = getDrawable(R.styleable.SettingsItemView_icon)
            val iconTintColor = getColorStateList(R.styleable.SettingsItemView_iconTint)
            iconImageView.setImageDrawable(icon)
            iconImageView.imageTintList = iconTintColor
        }
    }

    fun setTitle(title: String?) {
        titleTextView.text = title
        titleTextView.isVisible = title.isNullOrEmpty().not()
    }

    fun setSummary(summary: String?) {
        summaryTextView.text = summary
        summaryTextView.isVisible = summary.isNullOrEmpty().not()
    }

    fun setVisibleIcon(isVisible: Boolean) {
        iconImageView.isVisible = isVisible
    }

    override fun setOnClickListener(l: OnClickListener?) {
        itemView.setOnClickListener(l)
    }
}