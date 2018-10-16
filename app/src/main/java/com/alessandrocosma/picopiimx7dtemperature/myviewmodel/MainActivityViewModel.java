package com.alessandrocosma.picopiimx7dtemperature.myviewmodel;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.util.Log;
import android.os.Handler;
import android.os.Looper;

import com.alessandrocosma.picopiimx7dtemperature.myviewmodel.mylivedata.ButtonLiveData;
import com.alessandrocosma.picopiimx7dtemperature.myviewmodel.mylivedata.TemperatureLiveData;
import com.google.android.things.contrib.driver.button.Button;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;
import com.google.android.things.contrib.driver.ht16k33.Ht16k33;
import com.google.android.things.contrib.driver.pwmspeaker.Speaker;
import com.google.android.things.pio.Gpio;

import java.io.IOException;


public class MainActivityViewModel extends ViewModel {

    private static final String TAG = MainActivityViewModel.class.getSimpleName();

    private ButtonLiveData mButtonLiveData;
    private TemperatureLiveData mTemperatureLiveData;
    private AlphanumericDisplay alphanumericDisplay;
    private Speaker mSpeaker;

    public LiveData<Button> getButtonLiveData() {
        if (mButtonLiveData == null)
            mButtonLiveData = new ButtonLiveData();

        return mButtonLiveData;
    }

    public LiveData<Float> getTemperatureLiveData(){
        if(mTemperatureLiveData == null)
            mTemperatureLiveData = new TemperatureLiveData();

        return mTemperatureLiveData;
    }


    public void openSpeaker(){
        if(mSpeaker == null){
            try {
                mSpeaker = RainbowHat.openPiezo();
            }
            catch (IOException e){
                Log.e(TAG, "Unable to open the Speaker");
            }
        }
    }


    public void closeSpeaker(){
        if(mSpeaker == null)
            return;

        try {
            mSpeaker.close();
        }
        catch (IOException e){
            Log.e(TAG, "Unable to close the Speaker");
        }
    }


    public void playSound(){
        if(mSpeaker == null){
            this.openSpeaker();
        }

        /** An Handler for buzzer sound.
         *  It is an asynchronous thread but still on the main thread
         */
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
            } catch (IOException | InterruptedException e) {
                Log.e(TAG,"Unable to play buzzer sound");
            }
        }
    };


    public void setLedLight(char led, boolean value){

        Gpio ledR;
        Gpio ledG;
        Gpio ledB;

        switch (led){
            case 'R':
                try {
                    ledR = RainbowHat.openLedRed();
                    ledR.setValue(value);
                    ledR.close();
                    break;
                }
                catch (IOException e){
                    Log.e(TAG, "Unable to manage the led"+String.valueOf(led));
                }


            case 'B':
                try {
                    ledB = RainbowHat.openLedBlue();
                    ledB.setValue(value);
                    ledB.close();
                    break;
                }
                catch (IOException e){
                    Log.e(TAG, "Unable to manage the led"+String.valueOf(led));
                }


            case 'G':
                try {
                    ledG = RainbowHat.openLedGreen();
                    ledG.setValue(value);
                    ledG.close();
                    break;
                }
                catch (IOException e){
                    Log.e(TAG, "Unable to manage the led"+String.valueOf(led));
                }


                default:
                    Log.e(TAG, "identificatore led non corrispondente. Valori ammessi: R,G,B");

        }
    }

    public void display(Float value) {
        if (alphanumericDisplay == null) {
            try {
                alphanumericDisplay = RainbowHat.openDisplay();
                alphanumericDisplay.setBrightness(Ht16k33.HT16K33_BRIGHTNESS_MAX);
                alphanumericDisplay.clear();
                alphanumericDisplay.setEnabled(true);
            }
            catch (IOException e) {
                Log.d(TAG, "display: " + e);
                alphanumericDisplay = null;
                return;
            }
        }

        try {
            alphanumericDisplay.display(value);
        }
        catch (IOException e) {
            Log.d(TAG, "display: " + e);
        }
    }

    public void cleanDisplay(){
        if (alphanumericDisplay == null) {
            try {
                alphanumericDisplay = RainbowHat.openDisplay();
            } catch (IOException e) {
                alphanumericDisplay = null;
                return;
            }
        }

        try {
            alphanumericDisplay.clear();
            alphanumericDisplay.setEnabled(true);
            alphanumericDisplay.close();
        }
        catch (IOException e) {}

    }

}
