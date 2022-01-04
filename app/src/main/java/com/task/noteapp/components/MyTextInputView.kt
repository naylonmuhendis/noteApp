package com.task.noteapp.components

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.task.noteapp.R
import com.task.noteapp.databinding.ViewTextInputBinding

class MyTextInputView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: ViewTextInputBinding =
        ViewTextInputBinding.inflate(LayoutInflater.from(context), this, true)

    var errorEnabled: Boolean = false
        set(value) {
            field = value
            binding.isError = value
            if (value) {
                binding.editTextInput.setBackgroundResource(R.drawable.radius_error_11dp)
            } else {
                binding.editTextInput.setBackgroundResource(R.drawable.radius_11dp)
            }
            binding.executePendingBindings()
        }

    var error: String? = ""
        set(value) {
            field = value
            errorEnabled = !value.isNullOrEmpty()
            binding.error = value
            binding.isError = errorEnabled
            binding.executePendingBindings()
        }

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTextInputView)
            typedArray.run {
                val hint = typedArray.getString(R.styleable.MyTextInputView_android_hint)
                binding.hint = hint
                if (isInEditMode) {
                    binding.editTextInput.hint = hint
                }
                typedArray.getInt(R.styleable.MyTextInputView_android_maxLines, 1)
                    .takeIf { it != 0 }?.let { maxline ->
                        binding.editTextInput.maxLines = maxline
                    }

                typedArray.getString(R.styleable.MyTextInputView_android_text)?.let { text ->
                    binding.editTextInput.setText(text)
                }
                typedArray.getBoolean(R.styleable.MyTextInputView_errorEnabled, false)
                    .let { enable ->
                        errorEnabled = enable
                    }
                typedArray.getInt(R.styleable.MyTextInputView_android_minEms, 0)
                    .takeIf { it != 0 }?.let { minEms ->
                        binding.editTextInput.setEms(minEms)
                    }

                typedArray.getInt(R.styleable.MyTextInputView_android_inputType, 0)
                    .takeIf { it != 0 }?.let { inputType ->
                        binding.editTextInput.inputType = inputType
                    }
                typedArray.getInt(R.styleable.MyTextInputView_android_imeOptions, 0)
                    .takeIf { it != 0 }?.let { imeOptions ->
                        binding.editTextInput.imeOptions = imeOptions
                    }
                typedArray.getDimension(R.styleable.MyTextInputView_android_textSize, 0f)
                    .takeIf { it != 0f }?.let { textSize ->
                        binding.editTextInput.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
                    }
            }
            typedArray.recycle()
        }
        binding.editTextInput.addTextChangedListener {
            if (errorEnabled)
                error = ""
        }

        binding.editTextInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && errorEnabled)
                error = ""
        }
    }

    var text: String = ""
        get() = binding.editTextInput.text?.toString() ?: ""
        set(value) {
            field = value
            binding.editTextInput.setText(value)
            binding.executePendingBindings()
        }

    fun clearError() {
        text = ""
    }

}