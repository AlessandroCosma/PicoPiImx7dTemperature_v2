package com.alessandrocosma.picopiimx7dtemperature.myviewmodel;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;
import android.os.Handler;
import android.os.Looper;

import com.alessandrocosma.picopiimx7dtemperature.myviewmodel.mylivedata.ButtonLiveData;
import com.alessandrocosma.picopiimx7dtemperature.myviewmodel.mylivedata.TemperatureLiveData;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;
import com.google.android.things.contrib.driver.ht16k33.Ht16k33;
import com.google.android.things.contrib.driver.pwmspeaker.Speaker;
import com.google.android.things.pio.Gpio;

import java.io.IOException;


public class MainActivityViewModel extends ViewModel {

    private static final String TAG = MainActivityViewModel.class.getSimpleName();

    private final ButtonLiveData mButtonLiveData;
    private final TemperatureLiveData mTemperatureLiveData;
    private final AlphanumericDisplay alphanumericDisplay;
    private final Speaker mSpeaker;

    private final Gpio ledR;
    private final Gpio ledG;
    private final Gpio ledB;


    public MainActivityViewModel(){

        Log.d(TAG, "MainActivityViewModel instance created");

        // Create LiveData
        this.mButtonLiveData = new ButtonLiveData();
        this.mTemperatureLiveData = new TemperatureLiveData();

        // Open a connection to the leds
        openLeds();
        // Turn off leds light when init the ViewModel
        setLedLight('R',false);
        setLedLight('G',false);
        setLedLight('B',false);

        // Open a connection to the display
        openDisplay();

        // Open a connection to the speaker
        openSpeaker();
    }


    public LiveData<Boolean> getButtonLiveData() {
        return mButtonLiveData;
    }

    public LiveData<Float> getTemperatureLiveData(){
        return mTemperatureLiveData;
    }

    private void openLeds(){
        try{
            ledR = RainbowHat.openLedRed();
            ledB = RainbowHat.openLedBlue();
            ledG = RainbowHat.openLedGreen();
            Log.d(TAG, "Open LEDs connection");
        }
        catch (IOException e) {
            Log.e(TAG, "Unable to open leds connection");
        }
    }


    public void setLedLight(char led, boolean value){

        switch (led){
            case 'R':
                try {
                    ledR.setValue(value);
                    break;
                }
                catch (IOException e){
                    Log.e(TAG, "Unable to manage the led"+String.valueOf(led));
                }

            case 'B':
                try {
                    ledB.setValue(value);
                    break;
                }
                catch (IOException e){
                    Log.e(TAG, "Unable to manage the led"+String.valueOf(led));
                }

            case 'G':
                try {
                    ledG.setValue(value);
                    break;
                }
                catch (IOException e){
                    Log.e(TAG, "Unable to manage the led"+String.valueOf(led));
                }

            default:
                Log.e(TAG, "Invalid led identifier. Allowed values: R,G,B");

        }
    }

    private void closeLeds(){

        setLedLight('R',false);
        setLedLight('G',false);
        setLedLight('B',false);

        try {
            ledR.close();
            ledG.close();
            ledB.close();
            Log.d(TAG, "Close LEDs connection");

        }
        catch (IOException e) {
            Log.e(TAG, "Unable to close leds connection");
        }
    }


    private void openSpeaker(){
        if(mSpeaker == null){
            try {
                mSpeaker = RainbowHat.openPiezo();
                Log.d(TAG, "Open speaker connection");

            }
            catch (IOException e){
                Log.e(TAG, "Unable to open the Speaker");
            }
        }
    }


    private void closeSpeaker(){
        if(mSpeaker == null)
            return;

        try {
            mSpeaker.close();
            Log.d(TAG, "Close speaker connection");

        }
        catch (IOException e){
            Log.e(TAG, "Unable to close the Speaker");
        }
    }


    public void playSound(){
        if(mSpeaker == null){
            this.openSpeaker();
        }

        // An Handler for buzzer sound.
        // It is an asynchronous thread but still on the main thread
        Handler buzzerSoundHandler = new Handler(Looper.getMainLooper());
        buzzerSoundHandler.post(playSound);
    }


    private final Runnable playSound = new Runnable() {
        @Override
        public void run() {
            try {
                mSpeaker.play(2000);
                Thread.sleep(1500);
                mSpeaker.stop();
            } catch (IOException | InterruptedException | IllegalStateException e) {
                Log.e(TAG,"Unable to play buzzer sound: "+e.toString());
            }
        }
    };

    private void openDisplay(){
        if (alphanumericDisplay == null) {
            try {
                alphanumericDisplay = RainbowHat.openDisplay();
                alphanumericDisplay.setBrightness(Ht16k33.HT16K33_BRIGHTNESS_MAX);
                alphanumericDisplay.clear();
                alphanumericDisplay.setEnabled(true);
                Log.d(TAG, "Open display connection");
            }
            catch (IOException e) {
                Log.d(TAG, "display: " + e);
                alphanumericDisplay = null;
                return;
            }
        }
        else{
            Log.d(TAG, "The display is already opened");
        }
    }

    public void display(Float value) {

        try {
            alphanumericDisplay.display(value);
        }
        catch (IOException e) {
            Log.d(TAG, "display: " + e);
        }
    }

    private void closeDisplay(){
        if (alphanumericDisplay != null) {
            try {
                alphanumericDisplay.clear();
                alphanumericDisplay.setEnabled(true);
                alphanumericDisplay.close();
                Log.d(TAG, "Close display connection");
            } catch (IOException e) {
            }
        }

    }

    @Override
    protected void onCleared() {
        // Close LEDs light and the connections
        closeLeds();
        // Close speaker connection
        closeSpeaker();
        // Clean the display and close the connection
        closeDisplay();
    }
}
