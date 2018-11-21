**PicoPiImx7dTemperature**

**versione v2: Android Architecture Components**

PicoPiImx7dTemperature is an AndroidThings app developed for the PICO-PI-IMX7-STARTKIT-RAINBOW-HAT platform that detects the temperature through the Bmx280 sensor embedded in the RainbowHat.

Temperature value is showing to the user through the alphanumeric display HT16K33 and depending on its value, blue, green or red LEDs are triggered, depending on whether the measured temperature is lower than NORMAL_TEMPERATURE, between NORMAL_TEMPERATURE and MAX_TEMPERATURE or greater than MAX_TEMPERATURE.

Only in the event that the MAX_TEMPERATURE threshold value is exceeded, an audible alarm is triggered via the Piezo Buzzer pwm buzzer.

    Default values: 

    MAX_TEMPERATURE = 28°C

    NORMAL_TEMPERATURE = 24°C
                   



