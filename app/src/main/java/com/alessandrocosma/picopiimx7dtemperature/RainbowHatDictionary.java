package com.alessandrocosma.picopiimx7dtemperature;

import java.util.HashMap;

/**
 * Classe che mi rappresenta un dizionario implementato con HashMap<String, String>
 * @author Alessandro Cosma
 */
public final class RainbowHatDictionary {

    private HashMap<String, String> dict;

    /**
     * Costruttore che crea un dizionario vuoto
     */
    public RainbowHatDictionary(){
        this.dict = new HashMap<String, String>();
    }

    /**
     * Costruttore che crea un dizionario con una certa capacità iniziale
     * @param initialiCapacity la capacità iniziale con cui viene creato il dizionario
     */
    public RainbowHatDictionary(int initialiCapacity){
        this.dict = new HashMap<String, String>(initialiCapacity);
    }

    /**
     * Metodo per inserire un elemento nel dizionario
     * @param key la chiave che identifica l'elemento
     * @param name il valore da attribuire all'elemento
     */
    public void put(String key, String name){
        dict.put(key, name);
    }

    /**
     * Metodo per ottenere un valore data la chiave
     * @param key la chiave dell'elemento da cercare
     * @return il valore "name" dell'elemento avente chiave key
     */
    public String get(String key){
            return dict.get(key);
    }
}
