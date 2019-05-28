package com.alessandrocosma.picopiimx7dtemperature.myviewmodel.mylivedata;

import android.arch.lifecycle.LiveData;
import android.util.Log;
import android.os.Handler;
import android.os.Looper;

import com.google.android.things.contrib.driver.bmx280.Bmx280;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.math.BigDecimal;

public class TemperatureLiveData extends LiveData<Float>{

    private static final String TAG = TemperatureLiveData.class.getSimpleName();
    private Bmx280 tempSensor;
    private Handler handler;

    private final Runnable reportTemperature = new Runnable() {
        float temperature;
        @Override
        public void run() {
            try {
                temperature = tempSensor.readTemperature();
                BigDecimal tempBG = new BigDecimal(temperature);
                tempBG = tempBG.setScale(2, BigDecimal.ROUND_HALF_UP);
                temperature = (tempBG.floatValue());
                //notifico il valore all'observer
                setValue(temperature);
            }
            catch (IOException | IllegalStateException e){
                Log.e(TAG, "Unable to read temperature");
                temperature = Float.valueOf(null);
            }

            handler.postDelayed(reportTemperature, TimeUnit.SECONDS.toMillis(2));
        }
    };


    @Override
    protected void onActive() {
        super.onActive();
        Log.d(TAG, "onActive");

        try {
            tempSensor = RainbowHat.openSensor();
            tempSensor.setTemperatureOversampling(Bmx280.OVERSAMPLING_1X);
            tempSensor.setMode(Bmx280.MODE_NORMAL);
        }
        catch (IOException e){
            Log.e(TAG, "Unable to open temperature sensor");
        }

        // inizializzo l'handler
        handler = new Handler(Looper.getMainLooper());

        //avvio letture della temperatura
        handler.post(reportTemperature);
    }

    @Override
    protected void onInactive() {

        handler.removeCallbacks(reportTemperature);

        try {
            tempSensor.close();
        }
        catch (IOException e) {
            Log.d(TAG, " " + e);
        }

        super.onInactive();
        Log.d(TAG, "onInactive");
    }
}
