package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.example.myapplication.databinding.CustomRegistrationButtonBinding

class CustomRegistrationButtonView(
    context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: CustomRegistrationButtonBinding

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context, attrs, defStyleAttr, 0
    )
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.custom_registration_button, this, true)
        binding = CustomRegistrationButtonBinding.bind(this)
        initializeAttributes(attrs, defStyleAttr, defStyleRes)
    }

    @SuppressLint("ResourceType")
    private fun initializeAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.CustomRegistrationButtonView, defStyleAttr, defStyleRes
        )

        with(binding) {

            val registrationBtnBackground = typedArray.getResourceId(
                R.styleable.CustomRegistrationButtonView_registrationBtnBackground,
                R.drawable.google_registration_background
            )
            setBackgroundResource(registrationBtnBackground)

            /****************************************************************************/
            val logoNameText =
                typedArray.getString(R.styleable.CustomRegistrationButtonView_logoNameText)
            setLogoNameText(logoNameText)

            /****************************************************************************/
            val logoNameTextSize = typedArray.getDimensionPixelSize(
                R.styleable.CustomRegistrationButtonView_logoNameTextSize, 16
            )
            setLogoNameTextSize(logoNameTextSize.toFloat())

            /****************************************************************************/
            val logoImage = typedArray.getResourceId(
                R.styleable.CustomRegistrationButtonView_logoImage, R.drawable.icon
            )
            logoImageView.setBackgroundResource(logoImage)

            /****************************************************************************/
            val logoImageWidth = typedArray.getDimensionPixelSize(
                R.styleable.CustomRegistrationButtonView_logoImageWidth, 48
            )
            val logoImageHeight = typedArray.getDimensionPixelSize(
                R.styleable.CustomRegistrationButtonView_logoImageHeight, 48
            )
            var layoutParams = logoImageView.layoutParams
            layoutParams.width = logoImageWidth
            layoutParams.height = logoImageHeight
            logoImageView.layoutParams = layoutParams

            /****************************************************************************/
            val distanceBetweenElements = typedArray.getDimensionPixelSize(
                R.styleable.CustomRegistrationButtonView_distanceBetweenElements, 16
            )

            layoutParams = logoImageView.layoutParams as MarginLayoutParams
            layoutParams.marginEnd = distanceBetweenElements
            logoImageView.layoutParams = layoutParams

            /****************************************************************************/
            val logoNameTextColor = typedArray.getColor(
                R.styleable.CustomRegistrationButtonView_logoNameTextColor, Color.BLACK
            )
            logoTextView.setTextColor(logoNameTextColor)

            /****************************************************************************/
            val logoNameTextFontResourceId = typedArray.getResourceId(
                R.styleable.CustomRegistrationButtonView_logoNameTextFont, R.font.open_sans
            )
            val logoNameTextFont = ResourcesCompat.getFont(context, logoNameTextFontResourceId)
            logoTextView.setTypeface(logoNameTextFont)
        }
        typedArray.recycle()
    }

    fun setLogoNameText(text: String?) {
        binding.logoTextView.text = text ?: "GOOGLE"
    }

    fun setLogoNameTextSize(textSize: Float) {
        binding.logoTextView.textSize = textSize
    }

}