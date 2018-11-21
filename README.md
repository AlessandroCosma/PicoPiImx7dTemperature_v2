**PicoPiImx7dTemperature**

**versione v2: Android Architecture Components**

PicoPiImx7dTemperature is an AndroidThings app developed for the PICO-PI-IMX7-STARTKIT-RAINBOW-HAT platform
[(official site)](https://shop.technexion.com/pico-pi-imx7-startkit-rainbow-hat.html) that detects the temperature through the Bmx280 sensor
embedded in the RainbowHat, showing it to the user through the alphanumeric display HT16K33 (also presents in the RainbowHat) and activates the blue, green or red LEDs, depending on whether the measured temperature is lower than NORMAL_TEMPERATURE, between NORMAL_TEMPERATURE and MAX_TEMPERATURE or greater than MAX_TEMPERATURE.
Only in the event that the MAX_TEMPERATURE threshold value is exceeded, an audible alarm is triggered via the Piezo Buzzer pwm buzzer.

    Default values: 

    MAX_TEMPERATURE = 28°C

    NORMAL_TEMPERATURE = 24°C
                   



