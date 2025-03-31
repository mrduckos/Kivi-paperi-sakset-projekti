package org.example.projektikps;

import java.io.Serializable;
import java.util.Random;

/**
 * Luokka, joka perii pelaaja luokan
 * Luokka, joka edustaa AI-vastustajaa
 */
public class AI extends Pelaaja implements Serializable {
    /**
     * Kaikki mahdolliset valinnat AI:lle merkkijono talukkona
     */
    String[] vaihtoehdot = {"kivi", "paperi", "sakset"};

    /**
     * Random number generaattori valinnan tekemiseksi
     */
    Random randomi;

    /**
     * Alustaja AI-luokalle
     * Luo uuden AI-vastustajan ja asettaa sen nimeksi "AI temusta"
     */
    public AI (){
        super("AI temusta");
        this.randomi = new Random();
    }

    /**
     * Palauttaa AI-vastustajan randomisti valitun elementin (Kivi tai Paperi tai Sakset)
     *
     * @return AI-vastustajan valinta
     */
    @Override
    public String getValinta() {
        int valinnanIndex = randomi.nextInt(vaihtoehdot.length);
        String valinta = vaihtoehdot[valinnanIndex];
        return valinta;
    }

}