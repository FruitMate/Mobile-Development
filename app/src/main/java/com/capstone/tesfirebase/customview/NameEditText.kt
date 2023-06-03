package com.capstone.tesfirebase.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.capstone.tesfirebase.R

class NameEditText : AppCompatEditText {
    private var customError: String? = null
    private lateinit var personIcon: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "Nama"
        //error = customError
    }

    private fun init() {
        personIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_person_24) as Drawable
        setDrawables(startOfTheText = personIcon)
        compoundDrawablePadding = 24
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val (message, isValid) = isNameValid(context, s.toString())
                customError = if (!isValid) message else null
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    fun isNameValid(context: Context, name: String): Pair<String, Boolean> {
        return when {
            name.isEmpty() -> context.getString(R.string.name_required_error) to false
            else -> name to true
        }
    }

    private fun setDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText:Drawable? = null,
        endOfTheText:Drawable? = null,
        bottomOfTheText: Drawable? = null
    ){
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }
}