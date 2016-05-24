/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.iutvalence.recherchefourmis.environment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author antony
 */
public class Environment implements InterfaceEnvironment {

    private HashMap<String, Integer> noeuds;
    private Arc[][] arcs;

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
     * @param noeud
     * @return int renvoi l'index ou -1 si le noeud n'existe pas.
     */
    public int findIndex(String noeud) {
        Integer index = this.noeuds.get(noeud);
        if (index == null) {
            index = -1;
        }
        return index;
    }
    
    public String findNoeud(int i){
        for (Map.Entry<String, Integer> entry : noeuds.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if(value == i){
                return key;
            }
        }
        return null;
    }

    public void setArcMetrique(String debut, String fin, int metrique) {
        Arc arc = getArc(debut, fin);
        synchronized (arc) {
            if (arc != null) {
                arc.metrique = metrique;
            }
        }
    }

    @Override
    public Arc getArc(String debut, String fin) {
        int intDebut = 0, intFin = 0;
        intDebut = findIndex(debut);
        intFin = findIndex(fin);
        Arc arc = this.arcs[intDebut][intFin];
        if (arc == null) {
            arc = this.arcs[intFin][intDebut];
        }
        return arc;
    }

    public Arc[] getArcsPossible(String noeudDepart) {

        Set<Arc> results = new HashSet<>();
        int intDepart = findIndex(noeudDepart);
        for (Arc arc : this.arcs[intDepart]) {
            if (arc != null && arc.metrique != 0) {
                results.add(arc);
            }
        }
        for (int i = 0; i < this.arcs.length; i++) {
            Arc arc = this.arcs[i][intDepart];
            if (arc != null && arc.metrique != 0) {
                results.add(this.arcs[i][intDepart]);
            }

        }
        return results.toArray(new Arc[results.size()]);
    }
}
