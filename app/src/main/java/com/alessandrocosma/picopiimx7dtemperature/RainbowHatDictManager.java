package com.alessandrocosma.picopiimx7dtemperature;

import java.util.HashMap;


/**
 * Classe per gestire i dizionari contenenti le associazioni indirizzo-nome di
 * device connessi al RainbowHat.
 */
public final class RainbowHatDictManager {

    /**
     * Classe innestata che mi rappresenta un dizionario implementato con HashMap<String, String>
     * String#1 = chiave che rappresenta l'indirizzo deldevice (nel caso I2C) oppure id alfanumerico (caso GPIO, PWM)
     */
    public static class RainbowHatDictionary{

        private HashMap<String, String> dict;

        public RainbowHatDictionary(int initialiCapacity){
            this.dict = new HashMap<String, String>(initialiCapacity);
        }

        public void put(String key, String name){
            dict.put(key, name);
        }

        public String get(Object key){
            if(key instanceof String)
                return dict.get(key);
            else if(key instanceof Integer){
                return dict.get(key.toString());
            }
            else return null;
        }
    }


    /** Dizionario device I2C
     *  numero default di device: 2
     */
    private static RainbowHatDictionary dictionaryI2C = new RainbowHatDictionary(2){
        {
            put(Integer.toHexString(0x77), "BPM280");
            put(Integer.toHexString(0x70), "HT16K33");
        }
    };

    public final static RainbowHatDictionary getDictionaryI2C() {
        return dictionaryI2C;
    }

}


