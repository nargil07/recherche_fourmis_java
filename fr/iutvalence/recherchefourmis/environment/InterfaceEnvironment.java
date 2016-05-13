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
public interface InterfaceEnvironment {
    /**
     * Permet de récupérer l'arc entre les deux points.
     * @param debut
     * @param fin
     * @return Arc, l'objet arc entre les deux points. NULL si pas d'arc entre les deux.
     */
    public Arc getArc(String debut, String fin);
    
}
