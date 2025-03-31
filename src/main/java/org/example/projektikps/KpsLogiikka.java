package org.example.projektikps;

import java.io.Serializable;

/**
 * Luokka Kivi-Paperi-Sakset pelin logiikalle
 * Sisältää pelin säännöt
 */
public class KpsLogiikka implements Serializable {

    /**
     * Ihmispelaaja pelaaja-oliona
     */
    private Pelaaja pelaaja;

    /**
     * Tekoäly vastustaja AI-oliona
     */
    private AI ai;

    /**
     * AI:n viimeisin valinta merkkijonona
     */
    private String aiViimeisinValinta;

    /**
     * Boolean arvo jolla tarkistetaan onko pelaajalle annettu nimi booleanina
     */
    private boolean onkoPelaajallaNimi;

    /**
     * Parametrillinen alustaja pelaajalle ja AI-vastustajalle
     *
     * @param pelaaja1 ihmispelaaja
     */
    public KpsLogiikka(Pelaaja pelaaja1) {
        this.pelaaja = pelaaja1;
        this.ai = new AI();
        this.onkoPelaajallaNimi = false;
    }

    /**
     * Suorittaa yhden pelikierroksen
     * Määrittää voittajan ja päivittää pistetilanteen
     *
     * @param pelaajanValinta pelaajan ja AI-vastustajan valitsema elementti
     * @return kierroksen voittaja
     */
    public String pelaa(String pelaajanValinta) {
        aiViimeisinValinta = ai.getValinta();
        pelaaja.valinta = pelaajanValinta;

        // Voitto logiikka
        // palauttaa voittajan riippuen molempien valinnoista
        if (pelaajanValinta.equals(aiViimeisinValinta)){
            return "Tasapeli! Molemmat valitsivat: " + pelaajanValinta;
        } else if ((pelaajanValinta.equals("kivi") && aiViimeisinValinta.equals("sakset")) ||
                (pelaajanValinta.equals("paperi") && aiViimeisinValinta.equals("kivi")) ||
                (pelaajanValinta.equals("sakset") && aiViimeisinValinta.equals("paperi"))) {
            pelaaja.pojonLisays();
            return pelaaja.getNimi() + " Voitti! "+ ai.getNimi() + " valitsi: " + aiViimeisinValinta;
        } else {
            ai.pojonLisays();
            return "Ai voitti! " + ai.getNimi() + " valitsi: " + aiViimeisinValinta;
        }

    }

    /**
     * Palauttaa pelaajan voittojen määrän
     *
     * @return pelaajan voitot
     */
    public int getPelaajaVoitot() {
        return pelaaja.getPisteet();
    }

    /**
     * Palauttaa AI-vastustajan voittojen määrän
     *
     * @return AI-vastustajan voitot
     */
    public int getAiVoitot() {
        return ai.getPisteet();
    }

    /**
     * Palauttaa AI:n viimeisimmän valinnan
     *
     * @return AI:n viimeisin valinta
     */
    public String getAiViimeisinValinta() {
        return aiViimeisinValinta;
    }

    /**
     * Palauttaa pelaajan valinnan
     *
     * @return Pelaajan valinta
     */
    public String getPelaajaValinta() {
        String pelaajaValinta = pelaaja.getValinta();
        return pelaajaValinta;
    }

    /**
     * Laskee monta kertaa pelaaja on pelannut minkäkin elementin
     */
    public void pelaajaElementtiLaskuri() {
        if (getPelaajaValinta().equals("kivi")) {
            pelaaja.kiviLaskuri++;
        } else if (getPelaajaValinta().equals("paperi")) {
            pelaaja.paperiLaskuri++;
        } else {
            pelaaja.saksiLaskuri++;
        }
    }

    /**
     * Palauttaa boolean arvon riippuen onko pelaajalla nimi
     *
     * @return true, jos pelaajalla on nimi, muutoin false
     */
    public boolean getOnkoPelaajallaNimi() {
        return onkoPelaajallaNimi;
    }

    /**
     * Asettaa boolean arvon kentälle "onkoPelaajallaNimi"
     *
     * @param onkoPelaajallaNimi true, jos pelaajalla on nimi, muutoin false
     */
    public void setOnkoPelaajallaNimi(boolean onkoPelaajallaNimi) {
        this.onkoPelaajallaNimi = onkoPelaajallaNimi;
    }

    /**
     * Palauttaa tekoälyn tiedot
     *
     * @return tekoäly
     */
    public AI getAi() {
        return ai;
    }

    /**
     * Palauttaa pelaajan tiedot
     *
     * @return ihmispelaaja
     */
    public Pelaaja getPelaaja() {
        return pelaaja;
    }
}