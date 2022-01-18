package com.nissanconnect.animateimage

import android.content.Context
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.AppCompatImageView

class AnimateImage : AppCompatImageView {
    private var defaultDuration: Int = 1000
    private var customAnimation: AnimateSlide =
        AnimateSlide.NO_ACTION

    constructor(context: Context?) : super(context!!) {
        slideAnimation(customAnimation)
    }

    constructor(context: Context?, attrs: AttributeSet) : super(context!!, attrs) {
        changeAttributes(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet, defStyle: Int) : super(
        context!!,
        attrs,
        defStyle
    ) {
        changeAttributes(attrs)
    }

    private fun changeAttributes(attrs: AttributeSet) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CustomImageAnimation, 0, 0)
        customAnimation = try {
            AnimateSlide.values()[(ta.getInt(
                R.styleable.CustomImageAnimation_animationDirection,
                0
            ))]
        } finally {
            defaultDuration =
                ta.getInt(R.styleable.CustomImageAnimation_animationDuration, defaultDuration)
            ta.recycle()
        }
        slideAnimation(customAnimation)
    }

    fun slideAnimation(slideType: AnimateSlide, duration: Int) {
        setDuration(duration)
        slideAnimation(slideType)
    }

    private fun setDuration(duration: Int) {
        this.defaultDuration = if (duration > 0) duration else defaultDuration
    }

    fun slideAnimation(slideType: AnimateSlide) {
        val animFile: Int
        when (slideType) {
            AnimateSlide.LEFT -> {
                animFile = R.anim.slide_from_left
            }
            AnimateSlide.TOP -> {
                animFile = R.anim.slide_from_top
            }
            AnimateSlide.RIGHT -> {
                animFile = R.anim.slide_from_right
            }
            AnimateSlide.BOTTOM -> {
                animFile = R.anim.slide_from_bottom
            }
            AnimateSlide.NO_ACTION -> {
                animFile = R.anim.slide_no_animation
            }
        }
        val animSlide = AnimationUtils.loadAnimation(
            context,
            animFile
        )
        animSlide.duration = defaultDuration.toLong()
        this.startAnimation(animSlide)
    }
}
