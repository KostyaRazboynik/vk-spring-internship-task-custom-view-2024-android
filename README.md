# Clock custom view
Custom clock view for Android

It is very easy to use and customize for yourself

## Installation
#### Gradle
```groovy
// Add feature_clock module dependency
dependencies {
    implementation(project(":feature_clock"))
}
```


## Methods and Properties
- setDefaultTheme(): Unit - to reset all customization settings
- dialColor: Int - color of dial, default is light gray
- dialFrameColor: Int - color of dial frame, default is black
- numbersColor: Int - color of numbers, default is black
- dividersColor: Int - color of dividers, default is black
- hourHandColor: Int - color of hour hand, default black
- minuteHandColor: Int - color of minute hand, default is black
- secondHandColor: Int - color of second hand, default is black
- dialFrameIsVisible: Boolean - visibility of clock dial frame
- numbersIsVisible: Boolean - visibility numbers in clock dial
- hourHandIsVisible: Boolean - visibility of hour hand
- minuteHandIsVisible: Boolean - visibility of minute hand
- secondHandIsVisible: Boolean - visibility of second hand
- timeZone: TimeZone? - holds the clock TimeZone, default is local timezone
- dividersType: DividersType - holds type of clock dividers, default is DividersType.TWELVE_MINUTES


## Usage
### XML
```xml
<com.kostyarazboynik.customviewclock.ClockView
    android:id="@+id/clockView"
    android:layout_height="wrap_content"
    android:layout_width="wrap_content"
    app:timeZone="GMT+3"
    app:dividersType="NO_DIVIDERS"
    app:dialFrameIsVisible="false"
    app:secondHandColor="@color/red" />
```

### Kotlin
```kotlin
findViewById<ClockView>(R.id.clockView).apply {
    setDefaultTheme()
    timeZone = TimeZone.getTimeZone("UTC")
    dividersType = DividersType.HOUR_DIVIDERS
    secondHandIsVisible = false
    hourHandColor = Color.BLACK
}
```



