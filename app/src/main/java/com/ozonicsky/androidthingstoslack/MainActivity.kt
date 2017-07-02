package com.ozonicsky.androidthingstoslack

import android.app.Activity
import android.os.Bundle
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.GpioCallback
import com.google.android.things.pio.PeripheralManagerService


/**
 * Skeleton of an Android Things activity.
 *
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * val service = PeripheralManagerService()
 * val mLedGpio = service.openGpio("BCM6")
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
 * mLedGpio.value = true
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 *
 */
class MainActivity : Activity() {

    val ledPin: String = "BCM6"
    val buttonPin: String = "BCM16"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = PeripheralManagerService()
        val led = service.openGpio(ledPin)
        led.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
        val button = service.openGpio(buttonPin)
        button.setDirection(Gpio.DIRECTION_IN)
        button.setActiveType(Gpio.ACTIVE_HIGH)
        button.setEdgeTriggerType(Gpio.EDGE_BOTH)
        button.registerGpioCallback(object : GpioCallback() {
            override fun onGpioEdge(gpio: Gpio): Boolean {
                led.value = gpio.value != true
                return true
            }
        })
    }

}
