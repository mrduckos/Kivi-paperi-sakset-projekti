package org.example.projektikps;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Luokka graafiselle käyttöliittymälle kivi-paperi-sakset pelille
 */
public class GraafinenKPS extends Application {
    private KpsLogiikka peli;
    private Label tulos;
    private Label pelaajaVoitot;
    private Label aiVoitot;
    private Label valituinElementti;
    private String nimi;
    private Pelaaja pelaaja;
    private AI ai;
    private ImageView pelaajaValintaKuva;
    private ImageView aiValintaKuva;

    /**
     * Käynnistää sovelluksen ja tarkistaa onko tallennettua peliä olemassa
     * Valitsee sopivan näkymän riippuen onko tallennus olemassa vai ei
     *
     * @param primaryStage "näyttämö"
     */
    @Override
    public void start(Stage primaryStage) {

        // tarkistetaan onko tiedostoja pelille jo olemassa
        // Jos on, toteutetaan metodi peliKeske, muutoin toteutetaan metodi "reseti"
        if (TiedostoTallennus.onkoPeliTallennettu()) {
            peliKesken(primaryStage);
        } else {
            resetti(primaryStage);
        }
    }

    /**
     * Näyttää ruudun "peli kesken" jos peli on jäänyt kesken
     * Pelaaja voi jatkaa tai aloittaa uuden pelin
     *
     * @param primaryStage "näyttämö"
     */
    public void peliKesken(Stage primaryStage) {
        // VBox kysymykselle ja napeille jatkamiselle tai uudelle pelille
        VBox vBox = new VBox(20);

        Label kysymys = new Label("Sinulla on peli kesken. \nJatketaanko vai aloitetaanko uusi peli?");
        Button bUusi = new Button("Uusi");
        Button bJatka = new Button("Jatka");
        vBox.getChildren().addAll(kysymys, bUusi, bJatka);
        vBox.setAlignment(Pos.CENTER);

        // "Uusi" nappi poistaa tallennukset ja toteuttaa "resetti" metodin
        bUusi.setOnAction(e -> {
            TiedostoTallennus.poistaPeliTallennus();
            resetti(primaryStage);
        });

        // "Jatka" nappi lataa omista tiedostoista pelaajan ja AI:n
        bJatka.setOnAction(e -> {
            KpsLogiikka peliLataus = TiedostoTallennus.lataaPeli();

            if (peliLataus != null){
                this.peli = peliLataus;
                this.ai = peli.getAi();
                this.pelaaja = peli.getPelaaja();
                aloitus(primaryStage);
                System.out.println("onko pelaajalla nimi: " + peli.getOnkoPelaajallaNimi());
                System.out.println("Pelaajan nimi: " + pelaaja.getNimi());
            }
        });

        // pohja, johon sijoitetaan VBox keskelle
        BorderPane pohja = new BorderPane();
        pohja.setCenter(vBox);


        Scene scene = new Scene(pohja, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Jatketaanko peliä?");
        primaryStage.show();
    }

    /**
     * Resetoi pelaajan ja AI:n tiedot
     * Laittaa pelaajan ja AI:n tiedot nollaan
     *
     * @param primaryStage "näyttämö"
     */
    public void resetti(Stage primaryStage) {
        this.pelaaja = new IhmisPelaaja("Pelaaja");
        this.ai = new AI();

        aloitus(primaryStage);
    }

    /**
     * Luo graafisen käyttöliittymän itse pelille
     * Asettaa pelin "alkutilaan"
     *
     * @param primaryStage "näyttämö"
     */
    public void aloitus (Stage primaryStage) {
        // Jos peli jostakin syystä null, luodaan uusi peli
        if (this.peli == null) {
            peli = new KpsLogiikka(pelaaja);
        }

        // kuvat kivelle, paperille ja saksille
        Image kivi = new Image("/KIVI.png");
        Image paperi = new Image("/PAPERI.png");
        Image sakset = new Image("/SAKSET.png");

        // nimen valitsemis paneeli jossa textfield
        StackPane nimiPaneeli = new StackPane();
        TextField tfNimi = new TextField();
        tfNimi.setMaxWidth(100);
        nimiPaneeli.getChildren().addAll(tfNimi);
        tfNimi.setPromptText("Valitse nimi...");

        // peli alkaa kunnolla vasta kun pelaaja on antanut nimen
        // tarkistetaan, onko pelaajalla jo olemassa nimi tallennusta varten
        // jos pelaajalla on nimi, skipataan nimen kirjoitus vaihe
        if (!peli.getOnkoPelaajallaNimi()) {
            tfNimi.setOnAction(e -> {
                nimi = tfNimi.getText();
                pelaaja = new IhmisPelaaja(nimi);
                ai = new AI();
                peli.setOnkoPelaajallaNimi(true);

                pelaajaVoitot.setText(pelaaja.getNimi() + " voitot: " + pelaaja.getPisteet());
                aiVoitot.setText(ai.getNimi() + " voitot: " + ai.getPisteet());
                valituinElementti.setText(pelaaja.getNimi() + "n valituin elementti: " + pelaaja.getValituinElementti());
                nimiPaneeli.setVisible(false);
            });
        } else {
            nimiPaneeli.setVisible(false);
            tfNimi.setVisible(false);
            nimi = pelaaja.getNimi();
        }

        // tekstit/Labelit jotka sijoitettu graafisessa käyttöliittymässä vasemmalle
        // jotta näkee pelaajan voitot yhteensä, AI:n voitot yhteensä ja
        // pelaajan valituimman elementin
        pelaajaVoitot = new Label("Voitot: ");
        aiVoitot = new Label("AI voitot: ");
        valituinElementti = new Label("valituin elementti: ");

        // VBox voitoille, sijoitettu vasempaan reunaan
        VBox voitot = new VBox(20);
        voitot.getChildren().addAll(pelaajaVoitot, aiVoitot, valituinElementti);
        voitot.setAlignment(Pos.TOP_LEFT);
        voitot.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        voitot.setPadding(new Insets(20));

        // valinta napit
        // määrittää minkä elementin pelaaja valitsee
        HBox napit = new HBox(20);
        napit.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        napit.setPadding(new Insets(10));
        napit.setMaxWidth(200);
        napit.setTranslateX(300);
        Button bkivi = new Button("kivi");
        Button bpaperi = new Button("paperi");
        Button bsakset = new Button("sakset");

        // label johon tulee näkyviin jokaisen kierroksen tulos/voittaja
        tulos = new Label("Valitse: ");
        tulos.setAlignment(Pos.CENTER);
        tulos.setTranslateX(300);
        tulos.setStyle("-fx-font-size: 20px");

        // mitä napit tekevät
        bkivi.setOnAction(e -> pelaa("kivi"));
        bpaperi.setOnAction(e -> pelaa("paperi"));
        bsakset.setOnAction(e -> pelaa("sakset"));

        napit.getChildren().addAll(bkivi, bpaperi, bsakset);
        napit.setAlignment(Pos.CENTER);
        napit.setTranslateY(-20);

        // imageview kuville
        // näiden avulla voidaan näyttää kuvana mitä pelaaja valitsi ja mitä AI valitsi
        pelaajaValintaKuva = new ImageView();
        pelaajaValintaKuva.setFitHeight(120);
        pelaajaValintaKuva.setFitWidth(120);
        pelaajaValintaKuva.setTranslateX(-100);

        aiValintaKuva = new ImageView();
        aiValintaKuva.setFitHeight(120);
        aiValintaKuva.setFitWidth(120);
        aiValintaKuva.setTranslateX(100);

        // Labelit kuville
        // Selkeyttää kumpi valitsi ja mitä valitsi
        // Selventää myös, kumpi kuva kuvastaa kumman valintaa
        Label pelaajanKuva = new Label( pelaaja.getNimi() + "valitsi: ");
        pelaajanKuva.setTranslateY(-75);
        pelaajanKuva.setTranslateX(-100);

        Label ainKuva = new Label(ai.getNimi() + "Valitsi: ");
        ainKuva.setTranslateY(-75);
        ainKuva.setTranslateX(100);

        // Selkeyttää uusille pelaajille, miten peli toimii
        Label valitseElementti = new Label("Valitse elementti painamalla nappia: ");
        valitseElementti.setTranslateY(125);

        // HBox kuville
        StackPane valinnat = new StackPane();
        valinnat.getChildren().addAll(pelaajaValintaKuva, aiValintaKuva, pelaajanKuva, ainKuva, valitseElementti);
        valinnat.setAlignment(Pos.CENTER);

        // borderpane pohja johon asetellaan kaikki paikoilleen
        BorderPane pohja = new BorderPane();
        pohja.setBottom(napit);
        pohja.setRight(nimiPaneeli);
        pohja.setLeft(voitot);
        pohja.setCenter(valinnat);
        pohja.setTop(tulos);

        // tallennus tapahtuu, kun ruutu suljetaan
        primaryStage.setOnCloseRequest(e -> {
            TiedostoTallennus.tallennaPeli(peli);
            System.out.println("onko pelaajalla nimi? " + peli.getOnkoPelaajallaNimi());
            System.out.println("Tallennus onnistui!");

        });
        Scene kehys = new Scene(pohja, 750, 400);
        primaryStage.setScene(kehys);
        primaryStage.setTitle("KPS");
        primaryStage.show();
    }

    /**
     * Suorittaa yhden pelikierroksen
     * Tarkistaa voittajan
     * Päivittää kuvat
     *
     * @param pelaajanValinta pelaajan valitsema elementti
     */
    private void pelaa(String pelaajanValinta) {
        peli.pelaajaElementtiLaskuri();
        String valinta = peli.pelaa(pelaajanValinta);
        tulos.setText(valinta);

        pelaajaVoitot.setText(pelaaja.getNimi() + " voitot: " + peli.getPelaajaVoitot());
        aiVoitot.setText(ai.getNimi() + " voitot: " + peli.getAiVoitot());
        valituinElementti.setText(pelaaja.getNimi() + " valituin elementti: " + pelaaja.getValituinElementti());

        paivitaKuvat(pelaajanValinta, peli.getAiViimeisinValinta());
    }

    /**
     * Päivittää kuvat valintojen perusteella
     *
     * @param pelaajanValinta peelaajan valinta
     * @param aiValinta AI:n valinta
     */
    private void paivitaKuvat(String pelaajanValinta, String aiValinta) {
        pelaajaValintaKuva.setImage(new Image("/" + pelaajanValinta.toUpperCase() + ".png"));
        aiValintaKuva.setImage(new Image("/" + aiValinta.toUpperCase() + ".png"));
    }

    /**
     * main metodi
     *
     * @param args argumnetit
     */
    public static void main(String[] args) {
        launch(args);
    }

}