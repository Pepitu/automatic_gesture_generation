package tfg;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AssignacionsTest {

    private ArrayList<String> lista_etipro;
    private String nom_gest;

    public AssignacionsTest(ArrayList<String> lista_etipro) {

        this.lista_etipro = ( ArrayList<String> )lista_etipro.clone();

    }

    public AssignacionsTest(String nom_gest) {
        this.nom_gest = nom_gest;
    }

    public List<String> getLista_etipro() {
        return lista_etipro;
    }

    public String getNom_gest() {
        return nom_gest;
    }
    
    public void setLista_etipro(ArrayList<String> lista_etipro) {
        this.lista_etipro = lista_etipro;
    }

    public void setNom_gest(String nom_gest) {
        this.nom_gest = nom_gest;
    }
    
    public LinkedList<AssignacionsTest> AssignacionsKNNTest(LinkedList<AssignacionsTest> assignacionsTest, LinkedList<Assignacions> assignacions_KNN){
        
        LinkedList<AssignacionsTest> assignacionsKNNTest;
        assignacionsKNNTest = new LinkedList<>();
        
        AssignacionsTest tag;
        Assignacions knn;
        String nom_tag;
        Float distancia = null;
        Float distancia2;
        int bandera = 0;
        
        for (int i = 0; i < assignacionsTest.size(); i++) {

            tag = assignacionsTest.get(i);

            //si solo tiene una etiqueta, coger el gesto de knn de esa etiqueta
            if (tag.lista_etipro.size() == 1) {
                nom_tag = tag.lista_etipro.get(0);
                for (int k = 0; k < assignacions_KNN.size(); k++) {
                    knn = assignacions_KNN.get(k);
                    if (nom_tag.equals(knn.getNom_etipro())) {
                        
                        nom_gest = knn.getNom_gest();
                        tag.setNom_gest(nom_gest);
                        break;
                    }
                }
                        assignacionsKNNTest.add(tag);
             //si tiene mas de una etiqueta  
            } else {
                //primer cas.. agafem distancia del primer tag
                for (int j = 0; j < tag.lista_etipro.size(); j++) {
                    nom_tag = tag.lista_etipro.get(j);

                    for (int k = 0; k < assignacions_KNN.size(); k++) {
                        knn = assignacions_KNN.get(k);

                        if (nom_tag.equals(knn.getNom_etipro())) {

                            distancia = knn.getDistancia();
                            nom_gest = knn.getNom_gest();
                            tag.setNom_gest(nom_gest);
                            bandera = 1;
                        }

                        if (bandera > 0) {
                            break;
                        }

                    }
                    if (bandera > 0) {
                            bandera = 0;
                            break;
                        }
                }

                //segon cas: comparem la distancia amb l'anterior, agafem el gest de menor distancia.
                for (int j = 1; j < tag.lista_etipro.size(); j++) { //recorremos la lista de etiquetas prosodicas del objeto
                    nom_tag = tag.lista_etipro.get(j);

                    for (int k = 0; k < assignacions_KNN.size(); k++) {
                        knn = assignacions_KNN.get(k);

                        if (nom_tag.equals(knn.getNom_etipro())) {

                            distancia2 = knn.getDistancia();
                            
                            if(distancia > distancia2){
                              nom_gest = knn.getNom_gest();
                              tag.setNom_gest(nom_gest);
                              distancia=distancia2;
                                                                
                            }
                            bandera = 1;
                        }
                        
                        
                        
                        if (bandera > 0) {
                            bandera = 0;
                            break;
                        }

                    }
                    
                }
                assignacionsKNNTest.add(tag);
            }
        }
        
        return assignacionsKNNTest;
    }

    public LinkedList<AssignacionsTest> AssignacionsMajorVoteTest(LinkedList<AssignacionsTest> assignacionsTest, LinkedList<Assignacions> assignacions_MajorVote) {

        LinkedList<AssignacionsTest> assignacionsMajorVoteTest;
        assignacionsMajorVoteTest = new LinkedList<>();

        AssignacionsTest tag;
        Assignacions majorvote;
        String nom_tag;
        Float nombreVots = null;
        Float nombreVots2;
        int bandera = 0;


        for (int i = 0; i < assignacionsTest.size(); i++) {

            tag = assignacionsTest.get(i);

            //si solo tiene una etiqueta, coger el gesto de majorVot de esa etiqueta
            if (tag.lista_etipro.size() == 1) {
                nom_tag = tag.lista_etipro.get(0);
                for (int k = 0; k < assignacions_MajorVote.size(); k++) {
                    majorvote = assignacions_MajorVote.get(k);
                    if (nom_tag.equals(majorvote.getNom_etipro())) {
                        
                        nom_gest = majorvote.getNom_gest();
                        tag.setNom_gest(nom_gest);
                        break;
                    }
                }
                        assignacionsMajorVoteTest.add(tag);
             //si tiene mas de una etiqueta  
            } else {
                //primer cas.. agafem major vot del primer tag
                for (int j = 0; j < tag.lista_etipro.size(); j++) {
                    nom_tag = tag.lista_etipro.get(j);

                    for (int k = 0; k < assignacions_MajorVote.size(); k++) {
                        majorvote = assignacions_MajorVote.get(k);

                        if (nom_tag.equals(majorvote.getNom_etipro())) {

                            nombreVots = majorvote.getDistancia();
                            nom_gest = majorvote.getNom_gest();
                            tag.setNom_gest(nom_gest);
                            bandera = 1;
                        }

                        if (bandera > 0) {
                            break;
                        }

                    }
                    if (bandera > 0) {
                            bandera = 0;
                            break;
                        }
                }

                //segon cas: comparem el vot amb l'anterior, agafem el gest de major vot.
                for (int j = 1; j < tag.lista_etipro.size(); j++) { //recorremos la lista de etiquetas prosodicas del objeto
                    nom_tag = tag.lista_etipro.get(j);

                    for (int k = 0; k < assignacions_MajorVote.size(); k++) {
                        majorvote = assignacions_MajorVote.get(k);

                        if (nom_tag.equals(majorvote.getNom_etipro())) {

                            nombreVots2 = majorvote.getDistancia();
                            
                            if(nombreVots < nombreVots2){
                              nom_gest = majorvote.getNom_gest();
                              tag.setNom_gest(nom_gest);
                              nombreVots=nombreVots2;
                                                                
                            }
                            bandera = 1;
                        }
                        
                        
                        
                        if (bandera > 0) {
                            bandera = 0;
                            break;
                        }

                    }
                    
                }
                assignacionsMajorVoteTest.add(tag);
            }
        }

        return assignacionsMajorVoteTest;
    }

    public LinkedList<AssignacionsTest> AsignacionsAleatoriesTest(LinkedList<AssignacionsTest> asignTes, List<String> list_gest) {

        LinkedList<AssignacionsTest> assignacionsAleatories;
        assignacionsAleatories = new LinkedList<>();

        String s;
        int numeroAleatorio;

        for (int i = 0; i < asignTes.size(); i++) {

            numeroAleatorio = (int) Math.floor(Math.random() * (list_gest.size()));
            s = list_gest.get(numeroAleatorio);

            AssignacionsTest asign = new AssignacionsTest(s);
            assignacionsAleatories.add(asign);
        }


        return assignacionsAleatories;
    }

    public void ImprimirAssignacioAleatoriaTest(LinkedList<AssignacionsTest> assignacions_AleatoriesTest) {

        System.out.println("************************");
        System.out.println("ASSIGNACIONS AleatoriesTest: ");
        AssignacionsTest asign;
        for (int i = 0; i < assignacions_AleatoriesTest.size(); i++) {
            asign = assignacions_AleatoriesTest.get(i);
            System.out.println("\n");
            System.out.println("AUDIO " + (i + 1));
            System.out.println("Nom Gest: " + asign.nom_gest);
//            System.out.println("Etiquetes Prosodiques: " + asign.getLista_etipro());
        }
    }
    
    public void ImprimirAssignacioKNNTest(LinkedList<AssignacionsTest> a) {

        System.out.println("************************");
        System.out.println("ASSIGNACIONS KNN: ");
        AssignacionsTest asign;
        for (int i = 0; i < a.size(); i++) {
            asign = a.get(i);
            System.out.println("\n");
            System.out.println("AUDIO " + (i + 1));
            for (int j = 0; j < asign.lista_etipro.size(); j++) {
                System.out.println("Tag: " + asign.lista_etipro.get(j));
            }
            if(asign.nom_gest==null){
                asign.nom_gest = "Gesture_neutralTalk";
            }
            System.out.println("Nom Gest: " + asign.nom_gest);
        }
    }        
    
    public void ImprimirAssignacioMajorVoteTest(LinkedList<AssignacionsTest> a) {

        System.out.println("************************");
        System.out.println("ASSIGNACIONS MajorVoteTest: ");
        AssignacionsTest asign;
        for (int i = 0; i < a.size(); i++) {
            asign = a.get(i);
            System.out.println("\n");
            System.out.println("AUDIO " + (i + 1));
            for (int j = 0; j < asign.lista_etipro.size(); j++) {
                System.out.println("Tag: " + asign.lista_etipro.get(j));
            }
            if(asign.nom_gest==null){
                asign.nom_gest = "Gesture_neutralTalk";
            }
            System.out.println("Nom Gest: " + asign.nom_gest);
        }
    }
}
