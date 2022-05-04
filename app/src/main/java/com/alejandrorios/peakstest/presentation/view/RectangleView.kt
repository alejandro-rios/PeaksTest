package com.alejandrorios.peakstest.presentation.view

import android.content.ClipData
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.util.AttributeSet
import android.view.View
import android.view.View.MeasureSpec.AT_MOST
import android.view.View.MeasureSpec.EXACTLY
import androidx.constraintlayout.widget.ConstraintLayout
import com.alejandrorios.peakstest.domain.model.RectangleWithPos
import com.alejandrorios.peakstest.utils.PeaksDragShadowBuilder
import com.alejandrorios.peakstest.utils.beginDrag
import com.alejandrorios.peakstest.utils.getRandomColor
import kotlin.math.min

class RectangleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var rectColor = 0
    private var rectWidth: Int = 0
    private var rectHeight: Int = 0
    private var rectCenterX: Int = 0
    private var rectCenterY: Int = 0
    private val rectangle: ShapeDrawable = ShapeDrawable(RectShape())
    lateinit var rectangleData: RectangleWithPos

    init {
        rectColor = getRandomColor()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawRect(canvas)
    }

    private fun drawRect(canvas: Canvas) {
        rectangle.apply {
            rectCenterX = width / 2
            rectCenterY = height / 2
            val topRectX = (rectCenterX - (rectWidth / 2))
            val topRectY = (rectCenterY - (rectHeight / 2))

            setBounds(topRectX, topRectY, topRectX + rectWidth, topRectY + rectHeight)
            paint.color = rectColor
            draw(canvas)
        }
    }

    fun buildView(rectangleData: RectangleWithPos, viewParent: ConstraintLayout) {
        this.rectangleData = rectangleData
        locateInParent(viewParent)
        addOnLongClickListener(rectangleData.id)
    }

    private fun locateInParent(viewParent: ConstraintLayout) {
        post {
            val centerX = viewParent.width * (rectangleData.lastPosX ?: rectangleData.rectangle.x)
            val centerY = viewParent.height * (rectangleData.lastPosY ?: rectangleData.rectangle.y)

            animate().x((centerX - (rectWidth / 2)).toFloat()).y((centerY - (rectHeight / 2)).toFloat())
        }
    }

    private fun addOnLongClickListener(index: Int) {
        setOnLongClickListener {
            val item = ClipData.Item(index.toString())
            val dataToDrag = ClipData(index.toString(), arrayOf(MIMETYPE_TEXT_PLAIN), item)
            val shadow = PeaksDragShadowBuilder(this)

            beginDrag(dataToDrag, shadow)

            visibility = INVISIBLE
            true
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        rectWidth = (widthSize * rectangleData.rectangle.size).toInt()
        rectHeight = (heightSize * rectangleData.rectangle.size).toInt()

        // Measure Width
        val width: Int = when (widthMode) {
            EXACTLY -> widthSize
            AT_MOST -> min(rectWidth, widthSize)
            else -> rectWidth
        }

        // Measure Height
        val height: Int = when (heightMode) {
            EXACTLY -> heightSize
            AT_MOST -> min(rectHeight, heightSize)
            else -> rectHeight
        }

        // MUST CALL THIS in order to show changes
        setMeasuredDimension(width, height)
    }
}
