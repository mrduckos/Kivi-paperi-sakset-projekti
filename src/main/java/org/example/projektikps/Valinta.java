package org.example.projektikps;

/**
 * Rajapinta, joka määrittelee metodin pelivalinnan hakemiselle
 */
public interface Valinta {
    /**
     * Palauttaa pelaajan tai tekoälyn valitseman elementin (Kivi tai Paperi tai Sakset)
     *
     * @return valittu elementti
     */
    String getValinta();
}