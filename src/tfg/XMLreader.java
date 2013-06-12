package tfg;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.NodeList;

// aquesta classe ens servira per llegir les dades del document XML i imprimir les assignacions extretes.
public class XMLreader {

    private LinkedList<Assignacions> assignacions;
    private LinkedList<AssignacionsTest> assignacionsTest;
    private String nom_gest;
    private String nom_etipro;
    private ArrayList<String> llista_etipro;
    private Float temps;
    private Float nombre_etipro;
    private Assignacions a;
    private AssignacionsTest b;
    private int numAssing;

    void XMLreader() throws SAXException, IOException {

        assignacions = new LinkedList<>();
        assignacionsTest = new LinkedList<>();
        llista_etipro = new ArrayList<>();


//Dades d'entrenament
        try {

            //parseja el documen xml
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = docBuilder.parse(new File("src/xml/tags.xml"));

            NodeList listTags = doc.getElementsByTagName("tag"); //llista de etiquetes "tag"
            int totalDevices = listTags.getLength();
            System.out.println("Número total de TAGS : " + totalDevices + "\n"); //imprimeix el nombre de tags que disposem

            org.w3c.dom.Element device; //crea un device
            for (int i = 0; i < listTags.getLength(); i++) { //per a cada tag que disposem:
                device = (org.w3c.dom.Element) listTags.item(i); //agafa cada un per separat
                if (device.getNodeType() == Node.ELEMENT_NODE) {

//                    System.out.println("**********************");
//                    System.out.println("TAG " + (i + 1)); //imprimeix quin tag estem utilitzant

                    NodeList listEtipro = device.getElementsByTagName("etipro"); //agafa cada etipro que hi ha al tag
                    int TotalEtipro = listEtipro.getLength();

                    org.w3c.dom.Element eDevice = device;
                    nom_gest = getTagValue("gest", eDevice);
//                    System.out.println("Gest : " + getTagValue("gest", eDevice)); //imprimeix el gest

                    String t = getTagValue("time", eDevice);
                    temps = ConvertStringToFloat(t);
//                    System.out.println("Temps : " + getTagValue("time", eDevice)); //imprimeix el temps

                    int ep = TotalEtipro;
                    nombre_etipro = ConvertIntToFloat(ep);
//                    System.out.println("Nombre d'etiquetes prosodiques: " + TotalEtipro); //imprimeix el nombre d'etiquetes prosodiques que porta aquest tag 


                    for (int j = 0; j < TotalEtipro; j++) { //imprimeix cada etiqueta prosòdica per separat

                        nom_etipro = getTagValue("etipro", eDevice, j);
//                         System.out.println("Etiqueta prosodica : " + getTagValue("etipro", eDevice, j));

                        a = new Assignacions(nom_gest, nom_etipro, temps, nombre_etipro);
                        assignacions.add(a);

                        numAssing = numAssing + 1;

                        //imprimir cada assignacio
//                        a.Imprimir_assignació(numAssing);



                    }


//                    System.out.println("\n");


                }
            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLreader.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("NOMBRE D'ASSIGNACIONS: " + assignacions.size() + "\n");

        //Dades de Test 
        try {

            //parseja el documen xml
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = docBuilder.parse(new File("src/xml/tags_audios_input.xml"));

            System.out.println("\n DADES DE TEST: \n");
            NodeList listTags = doc.getElementsByTagName("tag"); //llista de etiquetes "tag"
            int totalDevices = listTags.getLength();
            System.out.println("Número total de TAGS : " + totalDevices + "\n"); //imprimeix el nombre de tags que disposem

            org.w3c.dom.Element device; //crea un device
            for (int i = 0; i < listTags.getLength(); i++) { //per a cada tag que disposem:
                device = (org.w3c.dom.Element) listTags.item(i); //agafa cada un per separat
                if (device.getNodeType() == Node.ELEMENT_NODE) {

//                    System.out.println("**********************");
//                    System.out.println("TAG " + (i + 1)); //imprimeix quin tag estem utilitzant

                    NodeList listEtipro = device.getElementsByTagName("etipro"); //agafa cada etipro que hi ha al tag
                    int TotalEtipro = listEtipro.getLength();

                    org.w3c.dom.Element eDevice = device;


                    int ep = TotalEtipro;
                    nombre_etipro = ConvertIntToFloat(ep);
//                    System.out.println("Nombre d'etiquetes prosodiques: " + TotalEtipro); //imprimeix el nombre d'etiquetes prosodiques que porta aquest tag 

                    llista_etipro.clear();

                    for (int j = 0; j < TotalEtipro; j++) { //imprimeix cada etiqueta prosòdica per separat

                        nom_etipro = getTagValue("etipro", eDevice, j);
//                         System.out.println("Etiqueta prosodica : " + getTagValue("etipro", eDevice, j));


                        llista_etipro.add(nom_etipro);


                        numAssing = numAssing + 1;

                        //imprimir cada assignacio
//                        a.Imprimir_assignació(numAssing);

                    }
                    b = new AssignacionsTest(llista_etipro);
                    assignacionsTest.add(b);

//                    System.out.println("\n");

                }

            }

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLreader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
// FUNCIONS PER A DADES D'ENTRENAMENT       
//        ImprimirAssignaciosDepurades();
//        ImprimirAsignacionsDepuradaMajorVote(); //necesari per "resultats" de la memoria.

        //**********
//        Imprimir_AssignacionsAleatories();

//        ImprimirAssignacionsKNN();

//        ImprimirMajorVote();


// FUNCIONS PER A DADES DE TEST

//Assignar gestos a cada grup d'etiquetes de les dades de test

        //Aleatoriament
        Imprimir_AssignacionsAleatoriesTEST();

        //per MajorVote
//    ImprimirMajorVoteTest();

        //per KNN
//    ImprimirKNNTest();


    }

    private void ImprimirAssignacionsKNN() {

        LinkedList<Assignacions> assignacions_dep;
        LinkedList<Assignacions> assignacions_KNN;

        assignacions_dep = a.AsignacionsDepurada(assignacions);
        assignacions_KNN = a.AsignacionsKNN(assignacions_dep);
        System.out.println("\n");
        a.OrderByDist(assignacions_KNN);
        a.Imprimir_assignacioKNN(assignacions_KNN);
    }

    private void ImprimirMajorVote() {

        LinkedList<Assignacions> assignacions_dep;
        LinkedList<Assignacions> assignacions_MajorVote;

        assignacions_dep = a.AsignacionsDepuradaMajorVote(assignacions);
        assignacions_MajorVote = a.AsignacionsMajorVote(assignacions_dep);
        System.out.println("\n");
        a.OrderByVote(assignacions_MajorVote);
        a.Imprimir_MajorVote(assignacions_MajorVote);
    }

    //l'utilitzem per mostrar els resultats de les assignacions en l'etiquetatge manual.
    private void ImprimirAsignacionsDepuradaMajorVote() {

        LinkedList<Assignacions> assignacions_MajorVote;

        assignacions_MajorVote = a.AsignacionsDepuradaMajorVote(assignacions);
        System.out.println("\n");
        a.AssignarPercentatges(assignacions_MajorVote);
        a.OrderByEtiPro(assignacions_MajorVote);
        a.Imprimir_MajorVotePercentatges(assignacions_MajorVote);
    }

    // imprimir asignaciones aleatorias a partir de los gestos y etiquetas disponibles
    public void Imprimir_AssignacionsAleatories() {

        LinkedList<Assignacions> assignacions_Aleatories;

        List<String> listaGestos, listaEti;
        listaEti = a.ObtenirEtiquetes(assignacions);
//        a.Imprimir_Etiquetes(listaEti);

        listaGestos = a.ObtenirGestos(assignacions);
//        a.Imprimir_Gestos(listaGestos);

        assignacions_Aleatories = a.AsignacionsAleatories(listaEti, listaGestos);
        a.ImprimirAssignacioAleatoria(assignacions_Aleatories);

    }

    public void Imprimir_AssignacionsAleatoriesTEST() {

        LinkedList<AssignacionsTest> assignacions_AleatoriesTest;

        List<String> listaGestos;

        listaGestos = a.ObtenirGestos(assignacions);
//        a.Imprimir_Gestos(listaGestos);

        assignacions_AleatoriesTest = b.AsignacionsAleatoriesTest(assignacionsTest, listaGestos);
        b.ImprimirAssignacioAleatoriaTest(assignacions_AleatoriesTest);

    }

    public void ImprimirMajorVoteTest() {

        LinkedList<Assignacions> assignacions_dep;
        LinkedList<Assignacions> assignacions_MajorVote;

        assignacions_dep = a.AsignacionsDepuradaMajorVote(assignacions);
        assignacions_MajorVote = a.AsignacionsMajorVote(assignacions_dep);
        a.OrderByVote(assignacions_MajorVote);
        System.out.println("\n");

        LinkedList<AssignacionsTest> assignacions_MajorVoteTest;

        assignacions_MajorVoteTest = b.AssignacionsMajorVoteTest(assignacionsTest, assignacions_MajorVote);

        b.ImprimirAssignacioMajorVoteTest(assignacions_MajorVoteTest);

    }

    public void ImprimirKNNTest() {

        LinkedList<Assignacions> assignacions_dep;
        LinkedList<Assignacions> assignacions_KNN;

        assignacions_dep = a.AsignacionsDepurada(assignacions);
        assignacions_KNN = a.AsignacionsKNN(assignacions_dep);
        a.OrderByDist(assignacions_KNN);
        System.out.println("\n");

        LinkedList<AssignacionsTest> assignacions_KNNTest;

        assignacions_KNNTest = b.AssignacionsKNNTest(assignacionsTest, assignacions_KNN);

        b.ImprimirAssignacioKNNTest(assignacions_KNNTest);

    }

    //depurar i imprimir assignacions depurades
    public void ImprimirAssignaciosDepurades() {

        LinkedList<Assignacions> assignacions_dep;
        assignacions_dep = a.AsignacionsDepurada(assignacions);
//        assignacions_dep = a.AsignacionsDepuradaMajorVote(assignacions);
        System.out.println("\n");
        a.Imprimir_assignacioDepurada(assignacions_dep);

    }

    // obtenim el valor que conté una etiqueta xml
    public String getTagValue(String tag, org.w3c.dom.Element elemento) {
        NodeList lista = elemento.getElementsByTagName(tag).item(0).getChildNodes();
        Node valor = (Node) lista.item(0);
        return valor.getNodeValue();

    }

    //obtenim el valor que conté una etiqueta xml segons l'index que li indiquem (per quan volem imprimir diversos valors consecutius)
    public String getTagValue(String tag, org.w3c.dom.Element elemento, int index) {
        NodeList lista = elemento.getElementsByTagName(tag).item(index).getChildNodes();
        Node valor = (Node) lista.item(0);
        return valor.getNodeValue();

    }

    public Float ConvertStringToFloat(String s) {

        Float f = new Float(s);
        return f;
    }

    public Float ConvertIntToFloat(int i) {

        float p;
        p = (float) i;
        return p;

    }
}
