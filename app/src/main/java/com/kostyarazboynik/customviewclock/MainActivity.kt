package com.kostyarazboynik.customviewclock

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale
import java.util.TimeZone

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var count = 1
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<ClockView>(R.id.clockView).apply {
            secondHandIsVisible = false
            minuteHandIsVisible = false
            hourHandIsVisible = false
            setOnClickListener {
//                secondHandColor = if (count % 2 == 0) Color.BLACK else Color.RED
//                numbersIsVisible = count % 2 == 0
//                dialFrameIsVisible = count % 2 != 0

                dividersType = when (count % 6) {
                    1 -> {
                        secondHandIsVisible = false
                        hourHandIsVisible = false
                        minuteHandIsVisible = true
                        DividersType.HOUR_DIVIDERS
                    }
                    2 -> {
                        minuteHandIsVisible = false
                        hourHandIsVisible = false
                        secondHandIsVisible = true
                        DividersType.THIRTY_MINUTES
                    }
                    3 -> {
                        secondHandIsVisible = false
                        minuteHandIsVisible = false
                        hourHandIsVisible = true
                        DividersType.TWENTY_MINUTES
                    }
                    4 -> {
                        secondHandIsVisible = true
                        minuteHandIsVisible = false
                        hourHandIsVisible = true
                        DividersType.FIFTEEN_MINUTES
                    }
                    5 -> {
                        secondHandIsVisible = false
                        minuteHandIsVisible = true
                        hourHandIsVisible = true
                        DividersType.TWELVE_MINUTES
                    }
                    else -> {
                        secondHandIsVisible = true
                        minuteHandIsVisible = true
                        hourHandIsVisible = true
                        DividersType.NO_DIVIDERS
                    }
                }
                count++
            }
        }

//        clockView.hourHandColor  // часовая стрелка
//        clockView.minuteHandColor              // минутная
//        clockView.secondHandColor // секундная
//
//        clockView.dialColor = Color.YELLOW     // фон
//        clockView.dialFrameColor = Color.RED       // контур
//
//        clockView.dividersColor = Color.GREEN      // точки
//        clockView.numbersColor = Color.WHITE      // текст
//

    }

}