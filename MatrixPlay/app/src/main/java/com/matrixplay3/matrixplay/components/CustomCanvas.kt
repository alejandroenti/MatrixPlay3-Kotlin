package com.matrixplay3.matrixplay.components

import android.R.style.Theme
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.ColorUtils
import com.matrixplay3.matrixplay.R

class CustomCanvas @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val STROKE_CANVAS_WIDTH : Int = 2
    private val font = ResourcesCompat.getFont(context, R.font.protonerdfontmonoregular)
    private val borderPaint = Paint().apply {
        var colorBackground = ContextCompat.getColor(context, R.color.background_stroke_color)
        color = ColorUtils.setAlphaComponent(colorBackground, 128)
        style = Paint.Style.STROKE
        strokeWidth = STROKE_CANVAS_WIDTH.toFloat()
        isAntiAlias = true
    }

    private val namePlayer1Paint = Paint().apply {
        var colorBackground = ContextCompat.getColor(context, R.color.p1_color)
        color = ColorUtils.setAlphaComponent(colorBackground, 100)
        textSize = 56f
        isAntiAlias = true
        typeface = font
    }

    private val scorePlayer1Paint = Paint().apply {
        var colorBackground = ContextCompat.getColor(context, R.color.p1_color)
        color = ColorUtils.setAlphaComponent(colorBackground, 100)
        textSize = 112f
        isAntiAlias = true
        typeface = font
    }

    private val namePlayer2Paint = Paint().apply {
        var colorBackground = ContextCompat.getColor(context, R.color.p2_color)
        color = ColorUtils.setAlphaComponent(colorBackground, 100)
        textSize = 56f
        isAntiAlias = true
        typeface = font
    }

    private val scorePlayer2Paint = Paint().apply {
        var colorBackground = ContextCompat.getColor(context, R.color.p2_color)
        color = ColorUtils.setAlphaComponent(colorBackground, 100)
        textSize = 112f
        isAntiAlias = true
        typeface = font
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(w, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawBackground(canvas)
        drawPlayer1Score(canvas)
        drawPlayer2Score(canvas)
    }

    private fun drawBackground(canvas : Canvas) {
        canvas.drawColor(ContextCompat.getColor(context, R.color.background_color))
        canvas.drawRect(
            0f + STROKE_CANVAS_WIDTH / 2,
            0f + STROKE_CANVAS_WIDTH / 2,
            width.toFloat() - STROKE_CANVAS_WIDTH / 2,
            height.toFloat() - STROKE_CANVAS_WIDTH / 2,
            borderPaint
        )
        canvas.drawRect(
            (width.toFloat() / 2) + STROKE_CANVAS_WIDTH / 2,
            0f + STROKE_CANVAS_WIDTH / 2,
            (width.toFloat() / 2) - STROKE_CANVAS_WIDTH / 2,
            height.toFloat() - STROKE_CANVAS_WIDTH / 2,
            borderPaint
        )
    }

    private fun drawPlayer1Score(canvas : Canvas) {
        var x : Float = width * 0.15f
        var y : Float = height * 0.1f
        canvas.drawText("Username", x, y, namePlayer1Paint)

        x = width * 0.3f
        y = height * 0.9f
        canvas.drawText("0", x, y, scorePlayer1Paint)
    }

    private fun drawPlayer2Score(canvas : Canvas) {
        var x : Float = width * 0.55f
        var y : Float = height * 0.1f
        canvas.drawText("Username", x, y, namePlayer2Paint)

        x = width * 0.55f
        y = height * 0.9f
        canvas.drawText("0", x, y, scorePlayer2Paint)
    }
}