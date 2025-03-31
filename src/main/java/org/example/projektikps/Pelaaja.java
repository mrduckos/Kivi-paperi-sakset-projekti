package org.example.projektikps;
import java.io.Serializable;

/**
 * Abstrakti luokka, joka edustaa pelaajaa
 */

public abstract class Pelaaja implements Valinta, Serializable {
    /**
     * pelaajan nimi merkkijonona
     */
    String nimi;

    /**
     * Pelaaja voitot kokonaislukuna
     */
    int pisteet;

    /**
     * Pelaajan valituin elementti (kivi tai paperi tai sakset) merkkijonona
     */
    String valituinElementti;

    /**
     * Laskurit jokaiselle elementille jotta saadaan valituin elementti kokonaislukuina
     */
    int kiviLaskuri;
    int paperiLaskuri;
    int saksiLaskuri;

    /**
     * Pelaajan valitsema elementti merkkijonona
     */
    String valinta;

    /**
     * Alustaja joka luo uuden pelaaja-olion annetun nimen perusteella
     * Pelaajan voitot asetetaan aluksi nollaan
     * Valintaa ei ole vielä tehty joten se on tyhjä
     *
     * @param nimi pelaajan nimi
     */
    public Pelaaja (String nimi) {
        this.nimi = nimi;
        this.pisteet = 0;
        this.valituinElementti = "";
        this.valinta = "";
        this.kiviLaskuri = 0;
        this.paperiLaskuri = 0;
        this.saksiLaskuri = 0;
    }


    /**
     * Palauttaa pelaajan nimen
     *
     * @return pelaajan nimi
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * Asettaa pelaajalle nimen
     *
     * @param nimi pelaajan nimi
     */
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    /**
     * Palauttaa pelaajan valituimman elementin (kivi-paperi-sakset)
     *
     * @return pelaajan valituin elementti
     */
    public String getValituinElementti() {
        if (kiviLaskuri > paperiLaskuri && kiviLaskuri > saksiLaskuri) {
            return "kivi";
        }
        if (paperiLaskuri > kiviLaskuri && paperiLaskuri > saksiLaskuri) {
            return "paperi";
        } else {
            return "sakset";
        }
    }



    /**
     * Palauttaa pelaajan voitot
     *
     * @return pelaajan voitot
     */
    public int getPisteet() {
        return pisteet;
    }

    /**
     * lisää pelaajalle pisteen (voiton)
     */
    public void pojonLisays() {
        this.pisteet += 1;
    }

    /**
     * Palauttaa pelaajan valinnan (kivi-paperi-sakset)
     *
     * @return pelaajan valinta
     */
    @Override
    public String getValinta() {
        return valinta;
    }
}