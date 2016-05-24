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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jerem
 */
public class Fourmi implements Runnable {

    /**
     * Utile plus tard quand on devra gérer les differentes fourmis.
     */
    private Environment environment;
    private EnumModeFourmis mode;

    public String noeudActuel = "N";
    private String noeudArrive = "F";
    private String name;
    /**
     * Permettra quand on sera arrivé à destination de rebrousser chemin.
     */
    public List<Arc> routeParcourus = new ArrayList<>();

    public Fourmi(Environment environment, EnumModeFourmis mode) {
        this.environment = environment;
        this.mode = mode;
        this.noeudActuel = "N";
        this.name = "four" + String.valueOf(Math.random());
    }

    /**
     * Fait avancer la fourmis suivant un algo
     */
    public void avancer() {
        Arc arc = choisir();
        try {
            Thread.sleep(arc.metrique * 1000);
            routeParcourus.add(arc);
            if(arc.getNoeudFin().equals(noeudActuel)){
                noeudActuel = arc.getNoeudDep();
            }else{
                noeudActuel = arc.getNoeudFin();
            }
            
            System.out.println(this.name + " : Arrivée au noeud " + noeudActuel);
        } catch (InterruptedException ex) {
            Logger.getLogger(Fourmi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean isCheminParcourus(Arc arc){
        int i = 0;
        boolean result = false;
        do{
            result = (arc == routeParcourus.get(i));
        }while(!result && routeParcourus.size() < i);
        return result;
    }
    
    public Arc choisir(){
        float total = 0;

        Arc[] cheminPossible = environment.getArcsPossible(noeudActuel);
        float[] tabPourcentage = new float[cheminPossible.length];
        for (int i = 0; i < cheminPossible.length; i++) {
            if (i < 1) {
                Arc arc = cheminPossible[i];
                tabPourcentage[i] = (float) ((1 / Math.pow(arc.metrique, this.mode.getPourcentageMetrique())) * 1+Math.pow(arc.pheromones, this.mode.getPourcentagePheromones()));
                total += tabPourcentage[i];
            }else{
                Arc arc = cheminPossible[i];
                tabPourcentage[i] = (float) (((1 / Math.pow(arc.metrique, this.mode.getPourcentageMetrique())) * 1+Math.pow(arc.pheromones, this.mode.getPourcentagePheromones())));
                total += tabPourcentage[i];
                tabPourcentage[i] = tabPourcentage[i] + tabPourcentage[i-1]; 
            }

        }

        double rand = Math.random();
        int i = 0;
        while(rand >= (tabPourcentage[i]/total)){
            i++;
        }
        return cheminPossible[i];
        
    }

    @Override
    public void run() {
        while(true){
            avancer();
        }
    }
}
