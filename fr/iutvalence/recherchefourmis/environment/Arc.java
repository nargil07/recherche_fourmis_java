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
    public int pheromones = 1;
    public int metrique = 1;
    public String noeudDep;
    public String noeudFin;

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
    
    
    
}
