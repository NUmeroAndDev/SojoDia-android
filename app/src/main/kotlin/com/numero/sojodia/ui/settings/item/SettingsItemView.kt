package com.numero.sojodia.ui.settings.item

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.numero.sojodia.R
import com.numero.sojodia.databinding.ViewSettingsItemBinding

class SettingsItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ViewSettingsItemBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.withStyledAttributes(attrs, R.styleable.SettingsItemView) {
            val title = getString(R.styleable.SettingsItemView_title)
            setTitle(title)
            val summary = getString(R.styleable.SettingsItemView_summary)
            setSummary(summary)
            val icon = getDrawable(R.styleable.SettingsItemView_icon)
            val iconTintColor = getColorStateList(R.styleable.SettingsItemView_iconTint)
            binding.iconImageView.setImageDrawable(icon)
            binding.iconImageView.imageTintList = iconTintColor
        }
    }

    fun setTitle(title: String?) {
        binding.titleTextView.text = title
        binding.titleTextView.isVisible = title.isNullOrEmpty().not()
    }

    fun setSummary(summary: String?) {
        binding.summaryTextView.text = summary
        binding.summaryTextView.isVisible = summary.isNullOrEmpty().not()
    }

    fun setVisibleIcon(isVisible: Boolean) {
        binding.iconImageView.isVisible = isVisible
    }

    override fun setOnClickListener(l: OnClickListener?) {
        binding.root.setOnClickListener(l)
    }
}