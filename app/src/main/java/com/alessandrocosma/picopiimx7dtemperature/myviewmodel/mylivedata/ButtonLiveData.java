package com.alessandrocosma.picopiimx7dtemperature.myviewmodel.mylivedata;

import com.google.android.things.contrib.driver.button.Button;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.io.IOException;


public class ButtonLiveData extends LiveData<Button>{

     private static final String TAG = ButtonLiveData.class.getSimpleName();

     private Button butttonA;


     @Override
     protected void onActive() {

          super.onActive();
          Log.d(TAG, "onActive");

          try {
               butttonA = RainbowHat.openButtonA();
               butttonA.setOnButtonEventListener(new Button.OnButtonEventListener() {
                    @Override
                    public void onButtonEvent(Button button, boolean pressed) {
                         setValue(butttonA);
                         Log.d(TAG, "button A pressed");
                    }
               });
          }
          catch (IOException e){
               Log.e(TAG, "Unable to open Button A");
          }

     }

     @Override
     protected void onInactive() {

        try {
            buttonA.close();

        }
        catch (IOException e){
            Log.e(TAG, "OnInactive: "+e);
        }

        setValue(null);
        
        super.onInactive();
        Log.d(TAG, "onInactive");
     }

}
