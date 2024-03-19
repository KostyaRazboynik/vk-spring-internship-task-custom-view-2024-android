package com.kostyarazboynik.customviewclock

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.kostyarazboynik.featureclock.R
import java.util.Calendar
import java.util.TimeZone
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * This view displays a clock which shows current time.
 * All available attributes can be set in the code and xml file:
 *
 *    <com.kostyarazboynik.customviewclock.ClockView
 *        android:id="@+id/clockView"
 *        android:layout_height="wrap_content"
 *        android:layout_width="wrap_content"
 *        app:timeZone="GMT+3"
 *        app:dividersType="NO_DIVIDERS"
 *        app:secondHandColor="@color/red" />;
 * or
 *
 *    findViewById<ClockView>(R.id.clockView).apply {
 *        timeZone = "UTC"
 *        dividersType = DividersType.TWELVE_MINUTES
 *        hourHandIsVisible = false
 *    }
 *
 * If you pass unsupported value of the time zone or dividersType ше will be set as default.
 *
 * @author KostyaRazboynik
 */
@SuppressWarnings("MagicNumber")
class ClockView @JvmOverloads constructor(
    private val context: Context,
    private val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private var clockRadius = 0.0f
    private var centerX = 0.0f
    private var centerY = 0.0f
    private val position: PointF = PointF(0.0f, 0.0f)

    /**
     * The color of dial, default is light gray.
     */
    var dialColor = 0

    /**
     * The color of dial frame, default is black.
     */
    var dialFrameColor = 0

    /**
     * The color of numbers, default is black.
     */
    var numbersColor = 0

    /**
     * The color of dividers, default is black.
     */
    var dividersColor = 0

    /**
     * The color of hour hand, default black.
     */
    var hourHandColor = 0

    /**
     * The color of minute hand, default is black.
     */
    var minuteHandColor = 0

    /**
     * The color of second hand, default is red.
     */
    var secondHandColor = 0

    /**
     * The visibility of clock dial frame.
     */
    var dialFrameIsVisible = true

    /**
     * The visibility of numbers in clock dial.
     */
    var numbersIsVisible = true

    /**
     * The visibility of hour hand.
     */
    var hourHandIsVisible = true

    /**
     * The visibility of minute hand.
     */
    var minuteHandIsVisible = true

    /**
     * The visibility of second hand.
     */
    var secondHandIsVisible = true

    /**
     * Holds the clock TimeZone, default is local timezone.
     * To set via xml use "UTC" or "GMT+3 string format
     */
    var timeZone: TimeZone? = null

    /**
     * Holds type of clock dividers.
     * To set via xml use one of:
     * [
     *     TWELVE_MINUTES,
     *     FIFTEEN_MINUTES,
     *     TWENTY_MINUTES,
     *     THIRTY_MINUTES,
     *     HOUR_DIVIDERS,
     *     NO_DIVIDERS,
     * ]
     */
    var dividersType = DividersType.TWELVE_MINUTES

    init {
        setUpDividersType()
        setUpTimeZone()
        setUpClockPartsVisibility()
        setUpColors()
    }

    fun setDefaultTheme() {
        dialColor = ContextCompat.getColor(context, R.color.light_gray)
        dialFrameColor = ContextCompat.getColor(context, R.color.black)
        numbersColor = ContextCompat.getColor(context, R.color.black)
        dividersColor = ContextCompat.getColor(context, R.color.black)
        hourHandColor = ContextCompat.getColor(context, R.color.black)
        minuteHandColor = ContextCompat.getColor(context, R.color.black)
        secondHandColor = ContextCompat.getColor(context, R.color.black)
        dialFrameIsVisible = true
        secondHandIsVisible = true
        minuteHandIsVisible = true
        hourHandIsVisible = true
        numbersIsVisible = true
        dividersType = DividersType.TWELVE_MINUTES
        timeZone = TimeZone.getDefault()
    }

    private fun setUpDividersType() {
        context.withStyledAttributes(attrs, R.styleable.ClockView) {
            dividersType = (getString(R.styleable.ClockView_dividersType) ?: "").toDividersType()
        }
    }

    private fun setUpTimeZone() {
        context.withStyledAttributes(attrs, R.styleable.ClockView) {
            timeZone = try {
                TimeZone.getTimeZone(getString(R.styleable.ClockView_timeZone))
            } catch (e: Exception) {
                TimeZone.getDefault()
            }
        }
    }

    private fun setUpClockPartsVisibility() {
        context.withStyledAttributes(attrs, R.styleable.ClockView) {
            dialFrameIsVisible = getBoolean(
                R.styleable.ClockView_dialFrameIsVisible,
                true
            )
            numbersIsVisible = getBoolean(
                R.styleable.ClockView_numbersIsVisible,
                true
            )
            hourHandIsVisible = getBoolean(
                R.styleable.ClockView_hourHandIsVisible,
                true
            )
            minuteHandIsVisible = getBoolean(
                R.styleable.ClockView_minuteHandIsVisible,
                true
            )
            secondHandIsVisible = getBoolean(
                R.styleable.ClockView_secondHandIsVisible,
                true
            )
        }
    }

    private fun setUpColors() {
        context.withStyledAttributes(attrs, R.styleable.ClockView) {
            dialColor = getColor(
                R.styleable.ClockView_dialColor,
                ContextCompat.getColor(context, R.color.light_gray)
            )
            dialFrameColor = getColor(
                R.styleable.ClockView_dialFrameColor,
                ContextCompat.getColor(context, R.color.black)
            )
            numbersColor = getColor(
                R.styleable.ClockView_numbersColor,
                ContextCompat.getColor(context, R.color.black)
            )
            dividersColor = getColor(
                R.styleable.ClockView_dividersColor,
                ContextCompat.getColor(context, R.color.black)
            )
            hourHandColor = getColor(
                R.styleable.ClockView_hourHandColor,
                ContextCompat.getColor(context, R.color.black)
            )
            minuteHandColor = getColor(
                R.styleable.ClockView_minuteHandColor,
                ContextCompat.getColor(context, R.color.black)
            )
            secondHandColor = getColor(
                R.styleable.ClockView_secondHandColor,
                ContextCompat.getColor(context, R.color.black)
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawDial(canvas)
        drawDialFrame(canvas)
        drawDividers(canvas)
        drawNumbers(canvas)
        drawClockHands(canvas)
        drawCircle(canvas)
        postInvalidateDelayed(REFRESH_PERIOD)
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        clockRadius = min(width, height) / 2f - (min(width, height) / 25)
        centerX = width / 2f
        centerY = height / 2f
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textScaleX = 0.9f
        letterSpacing = -0.1f
        typeface = Typeface.DEFAULT
        strokeCap = Paint.Cap.ROUND
    }

    private val paintDial = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textScaleX = 0.9f
        letterSpacing = -0.1f
        typeface = Typeface.DEFAULT
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.FILL
    }

    private val paintHourHand = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textScaleX = 0.9f
        letterSpacing = -0.15f
        typeface = Typeface.DEFAULT
        strokeCap = Paint.Cap.ROUND
    }

    private val paintMinuteHand = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textScaleX = 0.9f
        letterSpacing = -0.15f
        typeface = Typeface.DEFAULT
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.STROKE
    }

    private val paintSecondHand = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textScaleX = 0.9f
        letterSpacing = -0.15f
        typeface = Typeface.DEFAULT
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.STROKE
    }

    private val paintClockFrame = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textScaleX = 0.9f
        letterSpacing = -0.15f
        typeface = Typeface.DEFAULT
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.STROKE
    }

    private fun drawDial(canvas: Canvas) {
        paintDial.color = dialColor
        canvas.drawCircle(centerX, centerY, clockRadius, paintDial)
    }

    private fun drawDialFrame(canvas: Canvas) {
        if (dialFrameIsVisible) {
            paintClockFrame.color = dialFrameColor
            paintClockFrame.strokeWidth = clockRadius / 12
            val boundaryRadius = clockRadius - paintClockFrame.strokeWidth / 2
            val minOfHeightWidth = min(width, height)
            paintClockFrame.setShadowLayer(minOfHeightWidth / 2f / 20, 0.0f, 0.0f, Color.BLACK)
            canvas.drawCircle(centerX, centerY, boundaryRadius, paintClockFrame)
            paintClockFrame.strokeWidth = 0f
        }
    }

    private fun drawDividers(canvas: Canvas) {
        paint.color = dividersColor
        paint.style = Paint.Style.FILL
        val (dividers, bigDividers) = getDividersCount()

        for (i in 0 until dividers) {
            position.computePositionForDivider(i, clockRadius * 5 / 6, dividers / 2)
            val dividerRadius = if (i % bigDividers == 0) clockRadius / 96 else clockRadius / 132
            canvas.drawCircle(position.x, position.y, dividerRadius, paint)
        }
    }

    private fun getDividersCount(): Pair<Int, Int> =
        when (dividersType) {
            DividersType.NO_DIVIDERS -> Pair(0, 0)
            DividersType.HOUR_DIVIDERS -> Pair(12, 12)
            DividersType.THIRTY_MINUTES -> Pair(24, 2)
            DividersType.TWENTY_MINUTES -> Pair(36, 3)
            DividersType.FIFTEEN_MINUTES -> Pair(48, 4)
            DividersType.TWELVE_MINUTES -> Pair(60, 5)
        }

    private fun PointF.computePositionForDivider(dividerNum: Int, radius: Float, a: Int) {
        val angle = (dividerNum * (Math.PI / a)).toFloat()
        x = radius * cos(angle) + centerX
        y = radius * sin(angle) + centerY
    }

    private fun drawNumbers(canvas: Canvas) {
        if (numbersIsVisible) {
            paint.textSize = clockRadius * 2 / 7
            paint.strokeWidth = 0f
            paint.color = numbersColor
            for (i in 1..12) {
                position.computeXYForHourLabels(i, clockRadius * 11 / 16)
                canvas.drawText(i.toString(), position.x, position.y, paint)
            }
        }
    }

    private fun PointF.computeXYForHourLabels(hour: Int, radius: Float) {
        val angle = (START_ANGLE + hour * (Math.PI / 6)).toFloat()
        x = radius * cos(angle) + centerX
        val textBaselineToCenter = (paint.descent() + paint.ascent()) / 2
        y = radius * sin(angle) + centerY - textBaselineToCenter
    }

    private fun drawClockHands(canvas: Canvas) {
        val calendar: Calendar = Calendar.getInstance()
        if (timeZone != null) {
            calendar.timeZone = timeZone!!
        }
        var hour = calendar.get(Calendar.HOUR_OF_DAY)
        hour -= if (hour > 12) 12 else 0
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        if (hourHandIsVisible) drawHourHand(canvas, hour + minute / 60f)
        if (minuteHandIsVisible) drawMinuteHand(canvas, minute + second / 60f)
        if (secondHandIsVisible) drawSecondHand(canvas, second)
    }

    private fun drawHourHand(canvas: Canvas, hourWithMinutes: Float) {
        paintHourHand.color = hourHandColor
        paintHourHand.strokeWidth = clockRadius / 20
        val minOfHeightWidth = min(width, height)
        paintHourHand.setShadowLayer(
            minOfHeightWidth / 2f / 20,
            min(width, height) / 150.0f,
            min(width, height) / 150.0f,
            Color.BLACK
        )
        val angle = (Math.PI * hourWithMinutes / 6 + START_ANGLE).toFloat()
        canvas.drawLine(
            centerX - cos(angle) * clockRadius * 3 / 14,
            centerY - sin(angle) * clockRadius * 3 / 14,
            centerX + cos(angle) * clockRadius * 7 / 14,
            centerY + sin(angle) * clockRadius * 7 / 14,
            paintHourHand
        )
    }

    private fun drawMinuteHand(canvas: Canvas, minute: Float) {
        paintMinuteHand.color = minuteHandColor
        paintMinuteHand.strokeWidth = clockRadius / 40
        val minOfHeightWidth = min(width, height)
        paintMinuteHand.setShadowLayer(
            minOfHeightWidth / 2f / 20,
            min(width, height) / 80.0f,
            min(width, height) / 60.0f,
            Color.BLACK
        )
        val angle = (Math.PI * minute / 30 + START_ANGLE).toFloat()
        canvas.drawLine(
            centerX - cos(angle) * clockRadius * 2 / 7,
            centerY - sin(angle) * clockRadius * 2 / 7,
            centerX + cos(angle) * clockRadius * 5 / 7,
            centerY + sin(angle) * clockRadius * 5 / 7,
            paintMinuteHand
        )
    }

    private fun drawSecondHand(canvas: Canvas, second: Int) {
        paintSecondHand.color = secondHandColor
        val angle = (Math.PI * second / 30 + START_ANGLE).toFloat()
        val minOfHeightWidth = min(width, height)
        paintSecondHand.setShadowLayer(
            minOfHeightWidth / 2f / 25,
            min(width, height) / 25.0f,
            min(width, height) / 25.0f,
            Color.BLACK
        )
        paintSecondHand.strokeWidth = clockRadius / 80
        canvas.drawLine(
            centerX - cos(angle) * clockRadius * 1 / 14,
            centerY - sin(angle) * clockRadius * 1 / 14,
            centerX + cos(angle) * clockRadius * 5 / 7,
            centerY + sin(angle) * clockRadius * 5 / 7,
            paintSecondHand
        )
        paintSecondHand.strokeWidth = clockRadius / 50
        canvas.drawLine(
            centerX - cos(angle) * clockRadius * 2 / 7,
            centerY - sin(angle) * clockRadius * 2 / 7,
            centerX - cos(angle) * clockRadius * 1 / 14,
            centerY - sin(angle) * clockRadius * 1 / 14,
            paintSecondHand
        )
    }

    private fun drawCircle(canvas: Canvas) {
        paint.style = Paint.Style.FILL
        paint.color = if (secondHandIsVisible) {
            paintSecondHand.color
        } else if (minuteHandIsVisible) {
            paintMinuteHand.color
        } else if (hourHandIsVisible) {
            paintHourHand.color
        } else {
            paintDial.color
        }
        canvas.drawCircle(centerX, centerY, clockRadius / 36, paint)
    }

    private companion object {
        private const val START_ANGLE = -Math.PI / 2
        private const val REFRESH_PERIOD = 60L
    }
}
