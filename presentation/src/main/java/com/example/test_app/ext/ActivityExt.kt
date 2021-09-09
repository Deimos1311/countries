package com.example.test_app.ext

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.StringRes
import com.example.domain.DIALOG_WIDTH_DELTA_7
import com.example.test_app.R

fun createDialog(activity: Activity): Dialog {
    return Dialog(activity)
}

fun Activity.initBaseOneButtonContent(
    title: String?,
    description: String?
): Pair<Dialog, View> {

    val dialog = createDialog(this)
    dialog.setCanceledOnTouchOutside(false)
    val contentView = LayoutInflater.from(this)
        .inflate(R.layout.dialog_with_one_button, null)

    val tvTitle: TextView = contentView.findViewById(R.id.title_dialog_with_one_button)
    title?.let {
        tvTitle.text = it
        tvTitle.visibility = View.VISIBLE
    }

    val tvDescription: TextView =
        contentView.findViewById(R.id.description_dialog_with_one_button)
    description?.let {
        tvDescription.text = it
        tvDescription.visibility = View.VISIBLE
    }

    return Pair(dialog, contentView)
}

private fun setContentView(dialog: Dialog, contentView: View) {
    dialog.setContentView(contentView)
    val window = dialog.window
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    val resources = dialog.context.resources

    val params = contentView.layoutParams as FrameLayout.LayoutParams
    params.width = ((resources.displayMetrics.widthPixels * DIALOG_WIDTH_DELTA_7).toInt())
    contentView.layoutParams = params
}

fun Activity.showDialogWithOneButton(
    title: String?,
    description: String?,
    @StringRes buttonTextId: Int,
    buttonClickListener: View.OnClickListener?
): Dialog {
    val (dialog, contentView) = initBaseOneButtonContent(title, description)

    val button: Button = contentView.findViewById(R.id.button_dialog_with_one_button)
    button.setText(buttonTextId)
    button.setOnClickListener {
        dialog.dismiss()
        buttonClickListener?.onClick(it)
    }

    setContentView(dialog, contentView)

    if (!this.isFinishing) {
        dialog.show()
    }
    return dialog
}