/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.iutvalence.recherchefourmis.entite;

/**
 *
 * @author antony
 */
public enum EnumModeFourmis {
    
    
    /**
     * 
     */
    EXPLORATRICE(1, 5),
    /**
     * 
     */
    SUIVEUSE(5, 2);

    private int pourcentagePheromones;
    private int pourcentageMetrique;

    private EnumModeFourmis(int pourcentagePheromones, int pourcentageMetrique) {
        this.pourcentagePheromones = pourcentagePheromones;
        this.pourcentageMetrique = pourcentageMetrique;
    }

    public int getPourcentagePheromones() {
        return pourcentagePheromones;
    }

    public void setPourcentagePheromones(int pourcentagePheromones) {
        this.pourcentagePheromones = pourcentagePheromones;
    }

    public int getPourcentageMetrique() {
        return pourcentageMetrique;
    }

    public void setPourcentageMetrique(int pourcentageMetrique) {
        this.pourcentageMetrique = pourcentageMetrique;
    }
    
    
    
}
