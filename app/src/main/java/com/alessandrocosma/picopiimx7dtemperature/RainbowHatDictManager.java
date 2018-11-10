package com.alessandrocosma.picopiimx7dtemperature;


/**
 * Classe per gestire i dizionari contenenti le associazioni indirizzo-nome di
 * device connessi al RainbowHat.
 */
public final class RainbowHatDictManager {

    /** Dizionario device I2C
     *  numero default di device: 2
     */
    private static RainbowHatDictionary dictionaryI2C = new RainbowHatDictionary(2){
        {
            put(Integer.toHexString(0x77), "BPM280");
            put(Integer.toHexString(0x70), "HT16K33");
        }
    };

    /** Dizionario device GPIO
     *  numero default di device: 6
     */
    private static RainbowHatDictionary dictionaryGPIO = new RainbowHatDictionary(6){
        {
            put("GPIO2_IO00", "GREEN_LED");
            put("GPIO2_IO02", "RED_LED");
            put("GPIO2_IO05", "BLUE_LED");
            put("GPIO2_IO07", "BUTTON_C");
            put("GPIO6_IO14", "BUTTON_A");
            put("GPIO6_IO15", "BUTTON_B");

            //device gpio della board, non del RainbowHat
            //put("GPIO6_IO12", "CLOCK_PORT");
            //put("GPIO6_IO13", "DATA_PORT");
        }
    };

    /** Dizionario device PWM
     *  numero default di device: 2
     */
    private static RainbowHatDictionary dictionaryPWM = new RainbowHatDictionary(2){
        {
            put("PWM2","BUZZER");
            put("PWM1", "HEADER");
        }
    }

    public final static RainbowHatDictionary getDictionaryI2C() {
        return dictionaryI2C;
    }

    public final static RainbowHatDictionary getDictionaryGPIO(){
        return dictionaryGPIO;
    }

    public final static RainbowHatDictionary getDictionaryPWM(){
        return dictionaryPWM;
    }

}


