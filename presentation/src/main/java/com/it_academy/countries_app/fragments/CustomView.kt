package com.it_academy.countries_app.fragments

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.it_academy.countries_app.R

class CustomView : ConstraintLayout {

    private var tvTextComments: TextView? = null

    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        inflate(context, R.layout.custom_view, this)

        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        layoutParams.height = context.resources.getDimension(R.dimen.height_50dp).toInt()
        (layoutParams as LayoutParams).marginStart =
            context.resources.getDimension(R.dimen.margin_4dp).toInt()

        (layoutParams as LayoutParams).marginEnd =
            context.resources.getDimension(R.dimen.margin_4dp).toInt()

        tvTextComments = findViewById(R.id.text_comments)

        attrs?.let {
            tvTextComments?.text = context.getString(R.string.comments)
        }
    }
}