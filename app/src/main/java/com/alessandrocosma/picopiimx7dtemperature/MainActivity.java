package com.alessandrocosma.picopiimx7dtemperature;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.lifecycle.ViewModelProvider;
import android.util.Log;

import com.alessandrocosma.picopiimx7dtemperature.myviewmodel.MainActivityViewModel;
import com.google.android.things.contrib.driver.button.Button;


import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;
import com.google.android.things.pio.I2cDevice;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;

import java.util.List;
import java.io.IOException;


/**
 * A simple application for AndroidThings platform - PICO-PI-IMX7 with RainbowHat.
 * The RainbowHat BMP280 sensor reports the current temperature every 2 seconds
 * and displays it in the segment display.
 * If temperature >= MAX_TEMPERATURE the red led is turned on and the device plays an alarm.
 * If NORMAL_TEMPERATURE <= temperature < MAX_TEMPERATURE the green led is turned on.
 * Otherwise (temperature < MAX_TEMPERATURE) blue led is turned on.
 * N.B. Temperature readings are affected by heat radiated from your Pi’s CPU and the onboard LEDs;
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    //costanti che mi definiscono le soglie di temperatura per l'accensione dei vari led colorati
    private static final float MAX_TEMPERATURE = 28.0f;
    private static final float NORMAL_TEMPERATURE = 22.0f;

    //costante che mi definisce il bus I2C del RainbowHat
    private static final String DEFAULT_I2C_BUS = "I2C1";

    private final PeripheralManager mPeripheralManager = PeripheralManager.getInstance();


    private MainActivityViewModel mainActivityViewModel;

    private final Observer<Boolean> exitButtonLiveDataObserver = new Observer<Boolean>(){
        @Override
        public void onChanged(@Nullable Boolean pressed){

            if (pressed){
                Log.d(TAG, "onChanged() in exitButtonLiveDataObserver: value = "+pressed);
                MainActivity.this.finish();
            }
        }
    };

    private final Observer<Float> temperatureLiveDataObserver = new Observer<Float>() {
        @Override
        public void onChanged(@Nullable Float temperature) {

            Log.d(TAG, "onChanged() in temperatureLiveDataObserver: temperature = "+temperature);
            mainActivityViewModel.display(temperature);

            if(temperature < NORMAL_TEMPERATURE){
                mainActivityViewModel.setLedLight('R',false);
                mainActivityViewModel.setLedLight('G',false);
                mainActivityViewModel.setLedLight('B',true);
            }

            else if(temperature >= NORMAL_TEMPERATURE && temperature < MAX_TEMPERATURE){
                mainActivityViewModel.setLedLight('R',false);
                mainActivityViewModel.setLedLight('G',true);
                mainActivityViewModel.setLedLight('B',false);
            }
            else {
                mainActivityViewModel.setLedLight('R',true);
                mainActivityViewModel.setLedLight('G',false);
                mainActivityViewModel.setLedLight('B',false);

                mainActivityViewModel.playSound();
            }
        }
    };

    /**
     * Metodo per la scansione di device I2C del RainbowHat
     */
    private void executei2cScan(){
        String hexAddress;
        String name;
        for (int address = 0; address <= 127; address++) {
            //try-with-resources: auto-close the devices
            try (final I2cDevice device = mPeripheralManager.openI2cDevice(DEFAULT_I2C_BUS, address)) {
                try {
                    hexAddress = Integer.toHexString(address);
                    device.readRegByte(0x0);
                    name = RainbowHatDictManager.getDictionaryI2C().get(hexAddress);
                    if (name != null)
                        Log.i("i2cScanner", "Trying: "+hexAddress+" - SUCCESS -> device name = "+name);
                } catch (final IOException e) {
                    Log.i("i2cScanner", "Trying: "+address+" - FAIL");
                }

            } catch (final IOException e) {
                //in case address not exists, openI2cDevice() generates an exception
                Log.e(TAG, "address "+address +" doesn't exist!");
            }
        }
    }

    /**
     * Metodo per la scansione di device GPIO del RainbowHat
     */
    private void executeGPIOScan(){

        List<String> mGpioList;
        Gpio mGpio;
        String name;

        //obtain the gpio port list
        mGpioList = mPeripheralManager.getGpioList();

        for (String gpioDeviceName: mGpioList){
            //Log.i("gpioScanner",gpioDeviceName);
            name = RainbowHatDictManager.getDictionaryGPIO().get(gpioDeviceName);
            if (name != null)
                 Log.i("gpioScanner", "Trying: "+gpioDeviceName+" - SUCCESS -> device name = "+name);
        }
    }

    /**
     * Metodo per la scansione di device PWM del RainbowHat
     */
    private void executePWMScan(){
        List<String> mPWMList;
        mPWMList = mPeripheralManager.getPwmList();

        for (String pwmDeviceName: mPWMList)
            Log.i("pwmScanner",pwmDeviceName);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        // scansione device I2C
        //executei2cScan();

        // scansione device GPIO
        //executeGPIOScan();

        // scansione device PWM
        //executePWMScan();


        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        // Start observing ButtonLiveData
        mainActivityViewModel.getButtonLiveData().observe(MainActivity.this, exitButtonLiveDataObserver);

        // Start observingTemperatureLiveData
        mainActivityViewModel.getTemperatureLiveData().observe(MainActivity.this, temperatureLiveDataObserver);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
