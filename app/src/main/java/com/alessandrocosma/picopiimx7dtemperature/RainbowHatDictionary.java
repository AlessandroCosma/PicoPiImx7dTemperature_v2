package com.alessandrocosma.picopiimx7dtemperature;

import java.util.HashMap;

/**
 * Classe che mi rappresenta un dizionario implementato con HashMap<String, String>
 * String n°1 = chiave che rappresenta l'indirizzo del device (nel caso I2C)
 *              oppure id alfanumerico (caso GPIO, PWM)
 * String °2 = nome che assegno al device connesso
 */
public final class RainbowHatDictionary {

    private HashMap<String, String> dict;

    public RainbowHatDictionary(){
        this.dict = new HashMap<String, String>();
    }

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
