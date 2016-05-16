/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.iutvalence.recherchefourmis.entite;

import fr.iutvalence.recherchefourmis.environment.Arc;
import fr.iutvalence.recherchefourmis.environment.Environment;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jerem
 */
public class Fourmi implements Runnable{
    /**
     * Utile plus tard quand on devra gérer les differentes fourmis.
     */
    private Environment environment;
    private EnumModeFourmis mode;

    public String noeudActuel = "N";
    /**
     * Permettra quand on sera arrivé à destination de rebrousser chemin.
     */
    public List<String> routeParcourus = new ArrayList<>();

    public void Fourmi(Environment environment) {
        this.environment = environment;
    }
    
    /**
     * Fait avancer la fourmis suivant un algor
     */
    public void avancer()
    {
        Arc[] cheminPossible = environment.getArcsPossible(noeudActuel);
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
            
}
