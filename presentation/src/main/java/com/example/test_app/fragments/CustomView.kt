package com.example.test_app.fragments

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import com.example.test_app.R
import com.example.test_app.fragments.list_of_countries.ListOfCountriesAdapter

class CustomView : ConstraintLayout {

    private var tvTextComments: TextView? = null

    //private var ivImageUp: ImageView? = null
    //private var ivImageDown: ImageView? = null

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

        //ivImageUp = findViewById(R.id.image_up)
        //ivImageDown = findViewById(R.id.image_down)

        attrs?.let {
            tvTextComments?.text = context.getString(R.string.comments)
        }

        /*onClickImageUp {
        }
        onClickImageDown {
        }*/
    }

    /*private fun onClickImageUp(click: () -> Unit) {
        //ivImageUp?.setOnClickListener {
            click.invoke()
        }
    }

    private fun onClickImageDown(click: () -> Unit) {
        //ivImageDown?.setOnClickListener {
            click.invoke()
        }
    }*/
}