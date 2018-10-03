package com.alessandrocosma.picopiimx7dtemperature.myviewmodel.mylivedata;

import com.alessandrocosma.picopiimx7dtemperature.MainActivity;
import com.alessandrocosma.picopiimx7dtemperature.myviewmodel.MainActivityViewModel;
import com.google.android.things.contrib.driver.button.Button;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;

import java.io.IOException;


public class ButtonLiveData extends LiveData<Button>{

     private static final String TAG = ButtonLiveData.class.getSimpleName();

     private Button buttonC;


     @Override
     protected void onActive() {

          super.onActive();
          Log.d(TAG, "onActive");

          try {
               buttonC = RainbowHat.openButtonC();
               buttonC.setOnButtonEventListener(new Button.OnButtonEventListener() {
                    @Override
                    public void onButtonEvent(Button button, boolean pressed) {
                         setValue(buttonC);
                         Log.d(TAG, "button C pressed");
                    }
               });
          }
          catch (IOException e){
               Log.e(TAG, "Unable to open Button C");
          }

     }

     @Override
     protected void onInactive() {
          super.onInactive();
          Log.d(TAG, "onInactive");

          try {
               buttonC.close();

          }
          catch (IOException e){
               Log.e(TAG, "OnInactive: "+e);
          }

          setValue(null);
     }

}
