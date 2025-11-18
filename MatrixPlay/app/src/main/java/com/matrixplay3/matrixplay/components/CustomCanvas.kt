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
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.clients

class CustomCanvas @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val STROKE_CANVAS_WIDTH : Int = 2
    private var padWidth : Float = 0.01f
    private var padHeight : Float = 0.2f
    private var ballRadius : Float = 0.1f
    private val font = ResourcesCompat.getFont(context, R.font.protonerdfontmonoregular)

    private var positionsX = arrayOf(0f, 0f, 0f)
    private var positionsY = arrayOf(0f, 0f, 0f)
    private var scores = arrayOf(0, 0)

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

    private val padPlayer1Paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.p1_color)
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val padPlayer2Paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.p2_color)
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val ballPaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.yellow)
        style = Paint.Style.FILL
        isAntiAlias = true
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
        drawPads(canvas)
        drawBall(canvas)
    }

    fun updatePlayer1PadPosition(x : Float, y : Float) {
        positionsX.set(0, x)
        positionsY.set(0, y)
        invalidate()
    }

    fun updatePlayer2PadPosition(x : Float, y : Float) {
        positionsX.set(1, x)
        positionsY.set(1, y)
        invalidate()
    }

    fun updatePlayer1PadPosition(y : Float) {
        positionsY.set(0, y)
        invalidate()
    }

    fun updatePlayer2PadPosition(y : Float) {
        positionsY.set(1, y)
        invalidate()
    }

    fun updateBallPosition(x : Float, y : Float) {
        positionsX.set(2, x)
        positionsY.set(2, y)
        invalidate()
    }

    fun setPadDimensions(w : Float, h : Float) {
        padWidth = w
        padHeight = h
    }

    fun setBallRadius(r : Float) {
        ballRadius = r
    }

    fun updateScore(pos : Int) {
        var score = scores.get(pos)
        scores.set(pos, score + 1)
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
        canvas.drawText("Alex", x, y, namePlayer1Paint)
        //canvas.drawText(clients.get(0).name, x, y, namePlayer1Paint)

        x = width * 0.3f
        y = height * 0.9f
        canvas.drawText(scores.get(0).toString(), x, y, scorePlayer1Paint)
    }

    private fun drawPlayer2Score(canvas : Canvas) {
        var x : Float = width * 0.55f
        var y : Float = height * 0.1f
        //canvas.drawText(clients.get(1).name, x, y, namePlayer2Paint)
        canvas.drawText("Erick", x, y, namePlayer2Paint)

        x = width * 0.55f
        y = height * 0.9f
        canvas.drawText(scores.get(1).toString(), x, y, scorePlayer2Paint)
    }

    private fun drawPads(canvas : Canvas) {
        var x : Float = width * positionsX.get(0)
        var y : Float = height * positionsY.get(0)

        canvas.drawRect(
            x,
            y,
            x + (padWidth * width),
            y + (padHeight * height),
            padPlayer1Paint
        )

        x = width * positionsX.get(1)
        y = height * positionsY.get(1)
        canvas.drawRect(
            x,
            y,
            x + (padWidth * width),
            y + (padHeight * height),
            padPlayer2Paint
        )
    }

    private fun drawBall(canvas : Canvas) {
        var x : Float = width * positionsX.get(2)
        var y : Float = height * positionsY.get(2)

        canvas.drawCircle(
            x,
            y,
            ballRadius * width,
            ballPaint
        )
    }
}