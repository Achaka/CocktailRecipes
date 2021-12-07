package com.achaka.cocktailrecipes.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.EditText
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import com.achaka.cocktailrecipes.R

class CommentaryEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : androidx.appcompat.widget.AppCompatEditText(context, attrs) {

    val drawable: Drawable? =
        AppCompatResources.getDrawable(context, R.drawable.ic_baseline_check_circle_outline_24)


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, drawable)
    }
}