package org.example.projektikps;


import java.io.*;

/**
 * Luokka pelin tallentamiseen ja lataamiseen tiedostosta
 */
public class TiedostoTallennus {
    private static final String PELI_TIEDOSTO = "peli.dat";

    /**
     * Tallentaa pelin tiedostoon
     *
     * @param peli Pelin tiedot
     */
    public static void tallennaPeli(KpsLogiikka peli) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PELI_TIEDOSTO))) {
            oos.writeObject(peli);
        } catch (IOException e) {
            System.out.println("Virhe pelin tallennuksessa: " + e.getMessage());
        }
    }

    /**
     * Lataa pelin tiedostosta
     *
     * @return Pelin tiedot
     */
    public static KpsLogiikka lataaPeli() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PELI_TIEDOSTO))) {
            return (KpsLogiikka) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Virhe pelin latauksessa: " + e.getMessage());
            return null;
        }
    }

    /**
     * Tarkistaa, onko tiedostoa olemassa
     *
     * @return true, jos tallennus olemassa, muutoin false
     */
    public static boolean onkoPeliTallennettu() {
        File tiedosto = new File(PELI_TIEDOSTO);
        return tiedosto.exists();
    }

    /**
     * Poistaa pelin tiedostot
     */
    public static void poistaPeliTallennus() {
        File tiedosto = new File(PELI_TIEDOSTO);
        if (tiedosto.exists()) {
            tiedosto.delete();
        }
    }
}