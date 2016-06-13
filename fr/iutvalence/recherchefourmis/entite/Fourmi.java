package fr.iutvalence.recherchefourmis.entite;

import fr.iutvalence.recherchefourmis.environment.Arc;
import fr.iutvalence.recherchefourmis.environment.Environment;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Une fourmis qui est une entité autonomes.
 * Il suffit de le lancer dans un thread.
 * @author antony
 */
public class Fourmi implements Runnable {

    private final Environment environment;
    private final EnumModeFourmis mode;
    private boolean modeAvancer = true;

    public String noeudActuel = "N";
    private final String noeudArrive = "F";
    private final String name;
    private String syntheseFourmis;
    /**
     * Les différents arc parcourus.
     */
    public List<Arc> routeParcourus = new ArrayList<>();
    /**
     * Les différents arc qui mennent à un cul de sac.
     */
    public List<Arc> routeBannis = new ArrayList<>();
    /**
     * les differents noeuds parcourus.
     */
    public List<String> noeudsParcourus = new ArrayList<>();

    /**
     * Constructeur. Crée une fourmi en lui donnant son environment et son mode
     * de fonctionnement.
     *
     * @param environment
     * @param mode
     * @param name le nom que l'on veut donner a la fourmis pour les logs.
     */
    public Fourmi(Environment environment, EnumModeFourmis mode, String name) {
        this.environment = environment;
        this.mode = mode;
        this.noeudActuel = "N";
        this.noeudsParcourus.add(noeudActuel);
        this.name = name;
        this.syntheseFourmis = this.noeudActuel;
    }

    /**
     * La fourmi avance suivant un algorithme.
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
                if (!isNoeudParcourus(noeudOuAller)) {//verifie si le noeud a était parcourus
                    System.out.println(String.format("%s : %s -> %s", this.name, this.noeudActuel, noeudOuAller));
                    Thread.sleep(arc.metrique * 1000);
                    routeParcourus.add(arc);
                    noeudsParcourus.add(noeudOuAller);
                    noeudActuel = noeudOuAller;
                    this.syntheseFourmis = syntheseFourmis + " - " + noeudActuel;
                    System.out.println(String.format("%s : arrivé à %s", this.name, this.noeudActuel));
                    // System.out.println(this.name + " : Arrivée au noeud " + noeudActuel);
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

    /**
     * Fais reculer la fourmis. Il verifie si on est en mode avancer ou non. Si
     * on est en mode avancer quand il recule il met le chemin comme route
     * bannis.
     */
    private void reculer() {
        Arc arc = null;
        String noeudOuAller = null;
        try {
            arc = routeParcourus.remove(routeParcourus.size() - 1);
            if (!modeAvancer) {//si en mode reculer
                noeudsParcourus.remove(noeudsParcourus.size() - 1);
                arc.addPheromones(1);
            } else {
                routeBannis.add(arc);
            }
            if (arc.getNoeudFin().equals(noeudActuel)) {
                noeudOuAller = arc.getNoeudDep();
            } else {
                noeudOuAller = arc.getNoeudFin();
            }
            System.out.println(String.format("%s : %s -> %s", this.name, this.noeudActuel, noeudOuAller));
            Thread.sleep(arc.metrique * 1000);
            System.out.println(String.format("%s : arrivé à %s", this.name, this.noeudActuel));
            this.noeudActuel = noeudOuAller;
            this.syntheseFourmis = this.syntheseFourmis + " - " + this.noeudActuel;
            //System.out.println(this.name + " : Arrivée au noeud " + noeudActuel);
        } catch (InterruptedException ex) {
            Logger.getLogger(Fourmi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Verifie si le chemin à était parcourus.
     *
     * @param arc
     * @return
     */
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

    /**
     * Choisir le chemin, en verifiant les chemin parcourus.
     *
     * @return
     */
    public Arc choisir() {
        Arc result = null;
        //Le total qui aura l'addition de tous les pourcentages
        float total = 0;
        //On remplis une liste de tous les chemins a ne pas parcourires
        List<Arc> cheminImpossible = new ArrayList<>(routeBannis);
        cheminImpossible.addAll(routeParcourus);
        //récupere les chemins possible de l'environnement
        Arc[] cheminPossible = environment.getArcsPossible(noeudActuel, cheminImpossible);
        //crée un tableau de float qui contiendras les pourcentages de prendre 
        //tel ou tel chemin.
        float[] tabPourcentage = new float[cheminPossible.length];
        for (int i = 0; i < cheminPossible.length; i++) {
            if (i < 1) {
                /**
                 * Si c'est le premier chemin que l'on parcours ou calcule le
                 * pourcentage graçe au calcule
                 */
                Arc arc = cheminPossible[i];
                tabPourcentage[i] = (float) ((1 / Math.pow(arc.metrique, this.mode.getPourcentageMetrique())) * Math.pow(arc.pheromones, this.mode.getPourcentagePheromones()));
                total += tabPourcentage[i];
            } else {
                /**
                 * Sinon on calcule le pourcentage et on ajoute celui d'avant.
                 */
                Arc arc = cheminPossible[i];
                tabPourcentage[i] = (float) (((1 / Math.pow(arc.metrique, this.mode.getPourcentageMetrique())) * Math.pow(arc.pheromones, this.mode.getPourcentagePheromones())));
                total += tabPourcentage[i];
                tabPourcentage[i] = tabPourcentage[i] + tabPourcentage[i - 1];
            }

        }
        //si il y a des chemins possible.
        if (cheminPossible.length > 0) {
            //un double contenant un chiffre aléatoire entre 0 et 1.
            double rand = Math.random();
            
            int i = 0;
            //tant que le rand et superieur au calcule du pourcentage divisé par
            //le total on parcours le tableau de pourcentage
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
        modeAvancer = false;
        while (routeParcourus.size() > 0) {
            reculer();
        }
        System.out.println(this.syntheseFourmis);

    }
}
