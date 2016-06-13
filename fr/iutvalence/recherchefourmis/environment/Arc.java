/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.iutvalence.recherchefourmis.environment;

/**
 *
 * @author antony
 */
public class Arc {

    public Integer pheromones = 1;
    public int metrique = 1;
    public String noeudDep;
    public String noeudFin;

    private final int maxPheromones = 10;

    /**
     * Constructeur. L'arc represente un chemin entre deux noeuds. On peut
     * parcourires l'arc dans les deux sens.
     *
     * @param noeudFin
     * @param noeudDep
     */
    public Arc(String noeudFin, String noeudDep) {
        this.noeudDep = noeudDep;
        this.noeudFin = noeudFin;
    }

    public String getNoeudDep() {
        return noeudDep;
    }

    public void setNoeudDep(String noeudDep) {
        this.noeudDep = noeudDep;
    }

    public String getNoeudFin() {
        return noeudFin;
    }

    public void setNoeudFin(String noeudFin) {
        this.noeudFin = noeudFin;
    }

    /**
     * Ajoute le nombre {@code i} au pheromones et verifie qu'il ne soit pas au
     * dessus du max.
     *
     * @param i le nombre de pheromones à ajouter.
     */
    public void addPheromones(int i) {
        synchronized (pheromones) {
            pheromones += i;
            if (pheromones > maxPheromones) {
                pheromones = maxPheromones;
            }
        }
    }

}
