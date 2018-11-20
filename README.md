**PicoPiImx7dTemperature**

**versione v2: Android Architecture Components**

PicoPiImx7dTemperature is an AndroidThings app developed for the PICO-PI-IMX7-STARTKIT-RAINBOW-HAT platform
(https://shop.technexion.com/pico-pi-imx7-startkit-rainbow-hat.html) that detects the temperature through the Bmx280 sensor
embedded in the RainbowHat, showing it to the user through the alphanumeric display HT16K33 (also presents in the RainbowHat) and activates the blue, green or red LEDs, depending on whether the measured temperature is lower than NORMAL_TEMPERATURE, between NORMAL_TEMPERATURE and MAX_TEMPERATURE or greater than MAX_TEMPERATURE.
Only in the event that the MAX_TEMPERATURE threshold value is exceeded, an audible alarm is triggered via the Piezo Buzzer pwm buzzer.

    Default values: 

    MAX_TEMPERATURE = 28°C

    NORMAL_TEMPERATURE = 24°C
                   



To import the following project into AndroidStudio:
1) clone the repo locally
2) rename the project directory from 'PicoPiTemperature_v2' to 'PicoPiImx7dTemperature'
3) import into AndoridStudio using the appropriate procedure
