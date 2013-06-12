package tfg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Assignacions {

    private String nom_gest;
    private String nom_etipro;
    private Float temps;
    private Float distancia;
    private Float nombre_etipro;
    private Float porcentaje;

    public Assignacions(String nom_gest, String nom_etipro, Float temps, Float nombre_etipro) {

        this.nom_gest = nom_gest;
        this.nom_etipro = nom_etipro;
        this.temps = temps;
        this.nombre_etipro = nombre_etipro;
        distancia = set_distancia(temps, nombre_etipro);
        porcentaje = null;

    }

    public Assignacions(String nom_gest, String nom_etipro) {

        this.nom_gest = nom_gest;
        this.nom_etipro = nom_etipro;

    }
    
  
    public String getNom_etipro() {
        return nom_etipro;
    }

    public String getNom_gest() {
        return nom_gest;
    }

    //calculem el pes de l'assignació
    private Float set_distancia(Float temps, Float nombre_etipro) {

        Float d;
        d = (1 / (temps / nombre_etipro)); //la distancia es la inversa del "pes"
        return d;

    }

    //de les asignacions inicials, de les que tenen la mateixa etiqueta i el mateix gest agafa la de menor distancia
    public LinkedList<Assignacions> AsignacionsDepurada(LinkedList<Assignacions> a) {

        Assignacions comparando;
        Assignacions resultado;
        LinkedList<Assignacions> assignacions2;
        assignacions2 = new LinkedList<>();
        int check;
        int check2 = 0;

        /* Procediment: esta ja l'objecte a la llista?
         * - Si no esta, l'afegim
         * - Si esta mirem quin te menor distancia i guardem nomes aquell
         * */

        //recorremos la lista completa con asignaciones repetidas
        for (int i = 0; i < a.size(); i++) {

            comparando = a.get(i); //cogemos el objeto para compararlo

            if (assignacions2.isEmpty()) { //primer caso (lista vacia-> añado)
                assignacions2.add(comparando);
            } else {
                check2 = check2 + 1;  // necesitamos esta bandera para no volver a repetir este paso una segunda vez
            }

            check = 0; //usamos esta bandera para asegurarnos que el objeto que comparamos no existe en la lista resultante

            //recorremos la lista resultante
            for (int j = 0; j < assignacions2.size(); j++) {

                resultado = assignacions2.get(j); //cogemos el objeto de la lista resultante para compararlo

                //si la asignacion tiene el mismo nombre de etiqueta prosodica y de gesto
                if ((comparando.nom_etipro.equals(resultado.nom_etipro)) && (comparando.nom_gest.equals(resultado.nom_gest))) {

                    check = 1; //cambiamos la bandera para no añadirlo de nuevo a la lista.

                    if (check2 > 1) { // evita que en el primer loop modifiquemos el peso.

                        if (resultado.distancia > comparando.distancia) {
                            resultado.distancia = comparando.distancia; //cogemos la menor distancia
                        }
                    }

                }

            }
            if (check == 0) { //significa que no existe esta asignacion en nuestra lista resultante-> la añadimos.
                assignacions2.add(comparando);
            }

        }

        return assignacions2;

    }

    /* De cada asignació,utilitzant la variable "distancia" per indicar el nombre de cops que apareix,
     * indiquem el nombre de cops que apareix l'asignació de l'etiqueta amb aquest gest
     * sense repetir assignacions    */
    public LinkedList<Assignacions> AsignacionsDepuradaMajorVote(LinkedList<Assignacions> a) {

        Assignacions comparando;
        Assignacions resultado;
        LinkedList<Assignacions> assignacions2;
        assignacions2 = new LinkedList<>();
        int check;
        int check2 = 0;

        for (int i = 0; i < a.size(); i++) {

            comparando = a.get(i);

            if (assignacions2.isEmpty()) { //primer caso (lista vacia, añado)
                comparando.distancia = (float) 1; //utilitzem la variable distancia per posar el nombre de cops que apareix una asignació especifica gest-etiqueta, inicialitzant-la a 1.
                assignacions2.add(comparando);
            } else {
                check2 = check2 + 1;
            }

            check = 0;
            for (int j = 0; j < assignacions2.size(); j++) {


                resultado = assignacions2.get(j);
                if ((comparando.nom_etipro.equals(resultado.nom_etipro)) && (comparando.nom_gest.equals(resultado.nom_gest))) {

                    check = 1;
                    if (check2 > 1) {
                        resultado.distancia = resultado.distancia + (float) 1; //sumem +1 el nombre de vegades que apareix aquesta asignació
                    }

                }

            }
            if (check == 0) {
                comparando.distancia = (float) 1; //inicialitzem el nombre de vegades a 1.
                assignacions2.add(comparando);
            }

        }

        return assignacions2;

    }

    //de les asignacions on hem contabilitzat el nombre de cops que apareix la mateixa, agafem nomes la que te més asignacions
    public LinkedList<Assignacions> AsignacionsMajorVote(LinkedList<Assignacions> a) {

        Assignacions comparando;
        Assignacions resultado;
        LinkedList<Assignacions> assignacions2;
        assignacions2 = new LinkedList<>();
        int check;
        int check2 = 0;

        for (int i = 0; i < a.size(); i++) {

            comparando = a.get(i);

            if (assignacions2.isEmpty()) { //primer caso (lista vacia, añado)
                assignacions2.add(comparando);
            } else {
                check2 = check2 + 1;
            }

            check = 0;
            for (int j = 0; j < assignacions2.size(); j++) {

                resultado = assignacions2.get(j);

                if ((comparando.nom_etipro.equals(resultado.nom_etipro))) {

                    check = 1;
                    if (check2 > 1) {
                        if (resultado.distancia < comparando.distancia) { //nomes agafem el que té mes asignacions

                            resultado.nom_gest = comparando.nom_gest;
                            resultado.distancia = comparando.distancia;
                        }
                    }

                }

            }
            if (check == 0) {
                assignacions2.add(comparando);
            }

        }

        return assignacions2;
    }

    //Comparamos la distancias de las mismas asignaciones de la lista, y cogemos solo la asignacion de menor distancia
    public LinkedList<Assignacions> AsignacionsKNN(LinkedList<Assignacions> a) {

        Assignacions comparando;
        Assignacions resultado;
        LinkedList<Assignacions> assignacions2;
        assignacions2 = new LinkedList<>();
        int check;
        int check2 = 0;

        for (int i = 0; i < a.size(); i++) {

            comparando = a.get(i);

            if (assignacions2.isEmpty()) { //primer caso (lista vacia, añado)
                assignacions2.add(comparando);
            } else {
                check2 = check2 + 1;
            }

            check = 0;
            for (int j = 0; j < assignacions2.size(); j++) {

                resultado = assignacions2.get(j);

                if ((comparando.nom_etipro.equals(resultado.nom_etipro))) {

                    check = 1;
                    if (check2 > 1) {
                        if (resultado.distancia > comparando.distancia) { //nomes agafem la de menor distancia

                            resultado.nom_gest = comparando.nom_gest;
                            resultado.distancia = comparando.distancia;
                        }
                    }

                }

            }
            if (check == 0) {
                assignacions2.add(comparando);
            }

        }

        return assignacions2;
    }

    //aquesta funció ens retorna la llista de gestos que existeixen en una llista d'asignacions.
    public List<String> ObtenirGestos(LinkedList<Assignacions> a) {

        List<String> list = new ArrayList<>();

        String b;
        Assignacions asign;

        for (int i = 0; i < a.size(); i++) {
            asign = a.get(i);
            b = asign.nom_gest;

            if (list.contains(b)) {
            } else {
                list.add(b);
            }

        }

        return list;
    }

    //per imprimir la llista de gestos
    public void Imprimir_Gestos(List<String> list) {

        System.out.println("\n");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("Gest " + (i + 1) + ": " + list.get(i));
        }

    }

    //aquesta funció ens retorna la llista de etiquetes prosòdiques que existeixen en una llista d'asignacions.
    public List<String> ObtenirEtiquetes(LinkedList<Assignacions> a) {

        List<String> list = new ArrayList<>();

        String b;
        Assignacions asign;

        for (int i = 0; i < a.size(); i++) {
            asign = a.get(i);
            b = asign.nom_etipro;

            if (list.contains(b)) {
            } else {
                list.add(b);
            }

        }

        return list;
    }

    //per imprimir la llista d'etiquetes prosòdiques
    public void Imprimir_Etiquetes(List<String> list) {

        System.out.println("\n");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("Etiqueta prosòdica " + (i + 1) + ": " + list.get(i));
        }

    }

    //assignar i calcular percentatges a cada assignació de la llista
    public void AssignarPercentatges(LinkedList<Assignacions> a) {

        List<String> llistaEtipro;
        llistaEtipro = ObtenirEtiquetes(a);
        String nomEti;
        Assignacions assign;
        Float nombreTotalAssign = (float) 0;

        for (int i = 0; i < llistaEtipro.size(); i++) {
            nomEti = llistaEtipro.get(i);
            for (int j = 0; j < a.size(); j++) {
                assign = a.get(j);
                if (assign.getNom_etipro().equals(nomEti)) {
                    nombreTotalAssign = nombreTotalAssign + assign.getDistancia();
                }
            }
            for (int k = 0; k < a.size(); k++) {
                assign = a.get(k);
                if (assign.getNom_etipro().equals(nomEti)) {
                    assign.porcentaje = (assign.getDistancia() / nombreTotalAssign) * (float) 100;
                }
            }
            
            nombreTotalAssign = (float) 0;

        }

    }

    /*Crea una llista d'assignacions aleatories gest-etiqueta prosòdica
     a partir de les etiquetes que apareixen en el conjunt de dades d'entrenament que utilitzem
     asignant un gest aleatoriament a cada una de les etiquetes prosòdiques */
    public LinkedList<Assignacions> AsignacionsAleatories(List<String> list_eti, List<String> list_gest) {

        LinkedList<Assignacions> assignacions3;
        assignacions3 = new LinkedList<>();
        String s;
        int numeroAleatorio;

        for (int i = 0; i < list_eti.size(); i++) {

            numeroAleatorio = (int) Math.floor(Math.random() * (list_gest.size()));
            s = list_gest.get(numeroAleatorio);

            Assignacions asign = new Assignacions(s, list_eti.get(i));
            assignacions3.add(asign);
        }


        return assignacions3;

    }
    
    
    // Aquesta funció l'utilitzem per a comparar els pesos de les asignacions d'una llista, i ordenar la llista per pes.
    public LinkedList<Assignacions> OrderByDist(LinkedList<Assignacions> a) {

//                LinkedList<Assignacions> LlistaOrdenada;
//                LlistaOrdenada = new LinkedList<>();

        Collections.sort(a, new Comparator<Assignacions>() {
            @Override
            public boolean equals(Object foo) {
                return false; // doesn't matter, but false is better
            }

            @Override
            public int compare(Assignacions o1, Assignacions o2) {
                float x = o1.getDistancia();
                float y = o2.getDistancia();
                if (x < y) {
                    return -1;
                } else if (x == y) {
                    return 0;
                } else {
                    return 1;
                }

            }
        });


        return a;
    }

    public LinkedList<Assignacions> OrderByEtiPro(LinkedList<Assignacions> a) {

//                LinkedList<Assignacions> LlistaOrdenada;
//                LlistaOrdenada = new LinkedList<>();

        Collections.sort(a, new Comparator<Assignacions>() {
            @Override
            public boolean equals(Object foo) {
                return false; // doesn't matter, but false is better
            }

            @Override
            public int compare(Assignacions o1, Assignacions o2) {
                String x = o1.getNom_etipro();
                String y = o2.getNom_etipro();
                int ret;
                ret = x.compareTo(y);
                return ret;

            }
        });


        return a;
    }

    // Aquesta funció l'utilitzem per a comparar el nombre d'asignacions iguals d'una llista, i ordenarla per aquest nombre.
    public LinkedList<Assignacions> OrderByVote(LinkedList<Assignacions> a) {

//                LinkedList<Assignacions> LlistaOrdenada;
//                LlistaOrdenada = new LinkedList<>();

        Collections.sort(a, new Comparator<Assignacions>() {
            @Override
            public boolean equals(Object foo) {
                return false; // doesn't matter, but false is better
            }

            @Override
            public int compare(Assignacions o1, Assignacions o2) {
                float x = o1.getDistancia();
                float y = o2.getDistancia();
                if (x > y) {
                    return -1;
                } else if (x == y) {
                    return 0;
                } else {
                    return 1;
                }

            }
        });


        return a;
    }

    public Float getDistancia() {
        return distancia;
    }

    //per imprimir les assignacions sense modificacions
    public void Imprimir_assignació(int numAssign) {

        System.out.println("\n ASSIGNACIÓ " + numAssign);
        System.out.println("Nom Gest: " + nom_gest);
        System.out.println("Nom Etiqueta Prosodica: " + nom_etipro);
//        System.out.println("Temps general: " +temps);
        System.out.println("Distancia assignació: " + distancia);
//        System.out.println("Nombre etipros: " +nombre_etipro);

    }

    //imprimeix les assignacions depurades
    public void Imprimir_assignacioDepurada(LinkedList<Assignacions> a) {

        System.out.println("************************");
        System.out.println("ASSIGNACIONS DEPURADES: ");
        Assignacions asign;
        for (int i = 0; i < a.size(); i++) {

            asign = a.get(i);
            System.out.println("\n");
            System.out.println("ASSIGNACIÓ " + (i + 1));
            System.out.println("Nom Gest: " + asign.nom_gest);
            System.out.println("Nom Etiqueta Prosodica: " + asign.nom_etipro);
            System.out.println("Distancia assignació: " + asign.distancia);

        }

    }

    //imprimeix les assignacions KNN
    public void Imprimir_assignacioKNN(LinkedList<Assignacions> a) {

        System.out.println("************************");
        System.out.println("ASSIGNACIONS KNN: ");
        Assignacions asign;
        for (int i = 0; i < a.size(); i++) {

            asign = a.get(i);
            System.out.println("\n");
            System.out.println("ASSIGNACIÓ " + (i + 1));
            System.out.println("Nom Gest: " + asign.nom_gest);
            System.out.println("Nom Etiqueta Prosodica: " + asign.nom_etipro);
            System.out.println("Distancia assignació: " + asign.distancia);

        }

    }

    //imprimeix les assignacions per Major Vote
    public void Imprimir_MajorVote(LinkedList<Assignacions> a) {

        System.out.println("************************");
        System.out.println("MAJOR VOTE: ");
        Assignacions asign;
        for (int i = 0; i < a.size(); i++) {

            asign = a.get(i);
            System.out.println("\n");
            System.out.println("ASSIGNACIÓ " + (i + 1));
            System.out.println("Nom Gest: " + asign.nom_gest);
            System.out.println("Nom Etiqueta Prosodica: " + asign.nom_etipro);
            System.out.println("Nombre d'assignacions: " + asign.distancia);

        }

    }
    
    //imprimir amb percentatges
    public void Imprimir_MajorVotePercentatges(LinkedList<Assignacions> a) {

        System.out.println("************************");
        System.out.println("MAJOR VOTE (with%): ");
        Assignacions asign;
        for (int i = 0; i < a.size(); i++) {

            asign = a.get(i);
            System.out.println("\n");
            System.out.println("ASSIGNACIÓ " + (i + 1));
            System.out.println("Nom Gest: " + asign.nom_gest);
            System.out.println("Nom Etiqueta Prosodica: " + asign.nom_etipro);
            System.out.println("Nombre d'assignacions: " + asign.distancia);
            System.out.println("Percentatge: " + asign.porcentaje);

        }

    }

    //imprimeix les assignacions Aleatories
    public void ImprimirAssignacioAleatoria(LinkedList<Assignacions> a) {

        System.out.println("************************");
        System.out.println("ASSIGNACIONS Aleatories: ");
        Assignacions asign;
        for (int i = 0; i < a.size(); i++) {

            asign = a.get(i);
            System.out.println("\n");
            System.out.println("ASSIGNACIÓ " + (i + 1));
            System.out.println("Nom Gest: " + asign.nom_gest);
            System.out.println("Nom Etiqueta Prosodica: " + asign.nom_etipro);

        }



    }
}
