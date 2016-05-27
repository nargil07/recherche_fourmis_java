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
    private String syntheseFourmis;
    /**
     * Permettra quand on sera arrivé à destination de rebrousser chemin.
     */
    public List<Arc> routeParcourus = new ArrayList<>();
    public List<String> noeudsParcourus = new ArrayList<>();

    public Fourmi(Environment environment, EnumModeFourmis mode, String name) {
        this.environment = environment;
        this.mode = mode;
        this.noeudActuel = "N";
        this.noeudsParcourus.add(noeudActuel);
        this.name = name;
        this.syntheseFourmis = this.noeudActuel;
    }

    /**
     * Fait avancer la fourmis suivant un algo
     */
    public void avancer() {
        Arc arc = choisir();
        String noeudOuAller = null;
        try {
            if (arc != null) {
                if (arc.getNoeudFin().equals(noeudActuel)) {
                    noeudOuAller = arc.getNoeudDep();
                } else {
                    noeudOuAller = arc.getNoeudFin();
                }
                if (!isNoeudParcourus(noeudOuAller)) {
                    Thread.sleep(arc.metrique * 100);
                    routeParcourus.add(arc);
                    noeudsParcourus.add(noeudOuAller);
                    noeudActuel = noeudOuAller;
                    this.syntheseFourmis = syntheseFourmis + " - " + noeudActuel;
                    System.out.println(this.name + " : Arrivée au noeud " + noeudActuel);
                } else {
                    reculer();
                }
            } else {
                reculer();
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(Fourmi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void reculer() {
        try {
            Arc arc = routeParcourus.remove(routeParcourus.size() - 1);
            noeudsParcourus.remove(noeudsParcourus.size() - 1);
            Thread.sleep(arc.metrique * 100);
            if (arc.getNoeudFin().equals(noeudActuel)) {
                noeudActuel = arc.getNoeudDep();
            } else {
                noeudActuel = arc.getNoeudFin();
            }
            this.syntheseFourmis = this.syntheseFourmis + " - " + this.noeudActuel;
            System.out.println(this.name + " : Arrivée au noeud " + noeudActuel);
        } catch (InterruptedException ex) {
            Logger.getLogger(Fourmi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean isCheminParcourus(Arc arc) {
        int i = 0;
        boolean result = false;
        do {
            result = (arc == routeParcourus.get(i));
        } while (!result && routeParcourus.size() < i);
        return result;
    }

    private boolean isNoeudParcourus(String noeuds) {
        for (String noeud : noeudsParcourus) {
            if (noeuds.equals(noeud)) {
                return true;
            }
        }
        return false;
    }

    public Arc choisir() {
        Arc result = null;

        float total = 0;

        Arc[] cheminPossible = environment.getArcsPossible(noeudActuel, routeParcourus);
        float[] tabPourcentage = new float[cheminPossible.length];
        for (int i = 0; i < cheminPossible.length; i++) {
            if (i < 1) {
                Arc arc = cheminPossible[i];
                tabPourcentage[i] = (float) ((1 / Math.pow(arc.metrique, this.mode.getPourcentageMetrique())) * 1 + Math.pow(arc.pheromones, this.mode.getPourcentagePheromones()));
                total += tabPourcentage[i];
            } else {
                Arc arc = cheminPossible[i];
                tabPourcentage[i] = (float) (((1 / Math.pow(arc.metrique, this.mode.getPourcentageMetrique())) * 1 + Math.pow(arc.pheromones, this.mode.getPourcentagePheromones())));
                total += tabPourcentage[i];
                tabPourcentage[i] = tabPourcentage[i] + tabPourcentage[i - 1];
            }

        }

        if (cheminPossible.length > 0) {
            double rand = Math.random();
            int i = 0;
            while (rand >= (tabPourcentage[i] / total)) {
                i++;
            }
            result = cheminPossible[i];
        }
        return result;

    }

    @Override
    public void run() {
        while (!noeudActuel.equals(noeudArrive)) {
            avancer();
        }
        while (routeParcourus.size() > 0) {
           reculer();
        }
        System.out.println(this.syntheseFourmis);

    }
}
