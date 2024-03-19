package com.kostyarazboynik.customviewclock

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kostyarazboynik.customviewclock.databinding.FragmentMainLayoutBinding
import java.util.TimeZone

class MainFragment : Fragment() {

    private val binding: FragmentMainLayoutBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpDividersVariantButton()
        setUpHandsVariantButton()
        setUpNumbersVisibilityButton()
        setUpFrameVisibilityButton()
        setUpTimeZoneButton()
        setUpMakeDefaultButton()
        setUpThemeButton()
    }

    private fun setUpThemeButton() {
        binding.themeChange.setOnClickListener {
            themeClickCount++
            setDefaultTheme()
            when (themeClickCount % 3) {
                0 -> Unit
                1 -> setBrightTheme()
                2 -> setCalmTheme()
            }
        }
    }

    private fun setCalmTheme() {
        binding.baseClockView.apply {
            dialColor = Color.WHITE
            dialFrameColor = Color.BLACK
            dialFrameIsVisible = true
            secondHandIsVisible = false
            minuteHandIsVisible = false
            dividersType = DividersType.HOUR_DIVIDERS
            numbersIsVisible = false
            hourHandColor = Color.BLACK
        }
    }

    private fun setBrightTheme() {
        binding.baseClockView.apply {
            dialColor = Color.YELLOW
            dialFrameColor = Color.BLUE
            numbersColor = Color.CYAN
            dividersColor = Color.MAGENTA
            hourHandColor = Color.RED
            minuteHandColor = Color.GREEN
            secondHandColor = Color.DKGRAY
        }
    }

    private fun setDefaultTheme() {
        binding.baseClockView.makeDefault()
    }

    private fun setUpMakeDefaultButton() {
        binding.makeDefault.setOnClickListener {
            handsClickCount = 0
            dividersClickCount = 0
            numbersVisibility = 0
            frameVisibility = 0
            timeZoneClickCount = 0
            themeClickCount = 0

            setDefaultTheme()
        }
    }

    private fun setUpTimeZoneButton() {
        binding.apply {
            timeZoneChange.setOnClickListener {
                timeZoneClickCount++
                baseClockView.timeZone = when (timeZoneClickCount % 4) {
                    1 -> TimeZone.getTimeZone("UTC")
                    2 -> TimeZone.getTimeZone("GMT+4")
                    3 -> TimeZone.getTimeZone("GMT-5")
                    else -> TimeZone.getDefault()
                }
            }
        }
    }

    private fun setUpHandsVariantButton() {
        binding.apply {
            handsChange.setOnClickListener {
                baseClockView.apply {
                    when (handsClickCount++ % 8) {
                        1 -> {
                            hourHandIsVisible = false
                            minuteHandIsVisible = false
                            secondHandIsVisible = true
                        }

                        2 -> {
                            hourHandIsVisible = false
                            minuteHandIsVisible = true
                            secondHandIsVisible = false
                        }

                        3 -> {
                            hourHandIsVisible = true
                            minuteHandIsVisible = false
                            secondHandIsVisible = false
                        }

                        4 -> {
                            hourHandIsVisible = false
                            minuteHandIsVisible = true
                            secondHandIsVisible = true
                        }

                        5 -> {
                            hourHandIsVisible = true
                            minuteHandIsVisible = false
                            secondHandIsVisible = true
                        }

                        6 -> {
                            hourHandIsVisible = true
                            minuteHandIsVisible = true
                            secondHandIsVisible = false
                        }

                        7 -> {
                            hourHandIsVisible = true
                            minuteHandIsVisible = true
                            secondHandIsVisible = true
                        }

                        else -> {
                            hourHandIsVisible = false
                            minuteHandIsVisible = false
                            secondHandIsVisible = false
                        }
                    }
                }
            }
        }
    }

    private fun setUpDividersVariantButton() {
        binding.apply {
            dividersChange.setOnClickListener {
                baseClockView.dividersType = when (dividersClickCount % 6) {
                    1 -> DividersType.HOUR_DIVIDERS
                    2 -> DividersType.THIRTY_MINUTES
                    3 -> DividersType.TWENTY_MINUTES
                    4 -> DividersType.FIFTEEN_MINUTES
                    5 -> DividersType.TWELVE_MINUTES
                    else -> DividersType.NO_DIVIDERS
                }
                dividersClickCount++
            }
        }
    }

    private fun setUpNumbersVisibilityButton() {
        binding.apply {
            numbersChange.setOnClickListener {
                baseClockView.numbersIsVisible = numbersVisibility % 2 != 0
                numbersVisibility++
            }
        }
    }

    private fun setUpFrameVisibilityButton() {
        binding.apply {
            frameChange.setOnClickListener {
                baseClockView.dialFrameIsVisible = frameVisibility % 2 != 0
                frameVisibility++
            }
        }
    }

    companion object {
        private var handsClickCount = 0
        private var dividersClickCount = 0
        private var numbersVisibility = 0
        private var frameVisibility = 0
        private var timeZoneClickCount = 0
        private var themeClickCount = 0

        private const val TAG = "ProductListFragment"
        fun newInstance() = MainFragment()
    }
}
