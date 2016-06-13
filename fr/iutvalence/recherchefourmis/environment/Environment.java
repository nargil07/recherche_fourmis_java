package fr.iutvalence.recherchefourmis.environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author antony
 */
public class Environment implements InterfaceEnvironment {

    private HashMap<String, Integer> noeuds;
    private Arc[][] arcs;

    /**
     * Constructeur. Initialise une matrice d'arc en fonction d'une liste de
     * String.
     *
     * @param noeuds la liste de noeuds
     */
    public Environment(ArrayList<String> noeuds) {
        this.noeuds = new HashMap<>();
        for (int i = 0; i < noeuds.size(); i++) {
            this.noeuds.put(noeuds.get(i), i);
        }
        this.initMatrice();
    }

    /**
     * Initialise la matrice. Crée des arcs dans la matrice en laissant à null
     * ceux dont ce n'est pas nécessaire.
     * <p>
     * Par exemple pour une matrice de 3 sur 3 </p>
     * <p>
     * 0 1 1</p>
     * <p>
     * 0 0 1</p>
     * <p>
     * 0 0 0</p>
     */
    private void initMatrice() {
        arcs = new Arc[noeuds.size()][noeuds.size()];
        for (int i = 0; i < noeuds.size(); i++) {
            for (int j = i + 1; j < noeuds.size(); j++) {
                arcs[i][j] = new Arc(findNoeud(i), findNoeud(j));
            }
        }
    }

    /**
     * Permet de récuperer l'index d'un noeud.
     *
     * @param noeud le nom du noeud
     * @return int renvoi l'index ou -1 si le noeud n'existe pas.
     */
    public int findIndex(String noeud) {
        Integer index = this.noeuds.get(noeud);
        if (index == null) {
            index = -1;
        }
        return index;
    }

    /**
     * Renvoie le nom du noeud en fonction de son index.
     *
     * @param i l'index
     * @return renvoie le nom du noeuds ou null si l'index est mauvais.
     */
    public String findNoeud(int i) {
        for (Map.Entry<String, Integer> entry : noeuds.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (value == i) {
                return key;
            }
        }
        return null;
    }

    /**
     * Permet de donner une metrique à un Arc. le debut et la fin n'est pas à
     * prendre en compte car le graphe est symetrique.
     * Ne fait rien si l'arc n'existe pas.
     * @param debut le nom d'un noeud
     * @param fin le nom de l'autre noeud
     * @param metrique la metrique à mettre à ce chemin
     */
    public void setArcMetrique(String debut, String fin, int metrique) {
        Arc arc = getArc(debut, fin);
        synchronized (arc) {
            if (arc != null) {
                arc.metrique = metrique;
            }
        }
    }

    /**
     * Renvoie un Arc avec le nom du debut et de la fin de l'arc. Verifie dans
     * les deux sens.
     *
     * @param debut Premier noeud
     * @param fin Deuxieme noeud
     * @return
     */
    @Override
    public Arc getArc(String debut, String fin) {
        int intDebut = 0, intFin = 0;
        intDebut = findIndex(debut);
        intFin = findIndex(fin);
        Arc arc = this.arcs[intDebut][intFin];
        if (arc == null) {
            //verifie si l'arc n'existe pas dans l'autre sens.
            arc = this.arcs[intFin][intDebut];
        }
        return arc;
    }

    /**
     * Methode permettant de donner une liste d'arc parcourable en fonction du
     * noeuds et d'une liste d'arc à ne pas prendre en compte.
     *
     * @param noeudDepart 
     * @param dejaParcourus Liste des arcs à ne pas prendre en compte, peut être null.
     * @return
     */
    public Arc[] getArcsPossible(String noeudDepart, List<Arc> dejaParcourus) {
        if (dejaParcourus == null) {
            dejaParcourus = new ArrayList<>(); // si null init la liste à zero.
        }
        Set<Arc> results = new HashSet<>();//utilisation d'un Set pour éviter les doublons.
        int intDepart = findIndex(noeudDepart);
        for (Arc arc : this.arcs[intDepart]) {//verifie la matrice dans un sens
            boolean parcourus = false;
            for (Arc arcParcourus : dejaParcourus) {
                if (arcParcourus == arc) {
                    parcourus = true;
                    break;
                }
            }
            if (arc != null && arc.metrique != 0 && !parcourus) {
                //si l'arc est à null ou à une metrique à zero
                //ou à déjà était parcourus alors il ne le met pas
                results.add(arc);
            }
        }
        for (int i = 0; i < this.arcs.length; i++) { //verifie la matrice dans l'autre sens
            Arc arc = this.arcs[i][intDepart];
            boolean parcourus = false;
            for (Arc arcParcourus : dejaParcourus) {
                if (arcParcourus == arc) {
                    parcourus = true;
                    break;
                }
            }
            if (arc != null && arc.metrique != 0 && !parcourus) {
                results.add(this.arcs[i][intDepart]);
            }

        }
        return results.toArray(new Arc[results.size()]);
    }
}
