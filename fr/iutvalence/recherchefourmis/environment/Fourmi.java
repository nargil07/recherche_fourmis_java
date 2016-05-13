/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.iutvalence.recherchefourmis.environment;

/**
 *
 * @author jerem
 */
public class Fourmi {

    public String noeudActuel;
    public String [] noeudsVoisin;

    public void Fourmi(String noeudActuel, String[] noeudsVoisin) {
        this.noeudActuel = noeudActuel;
        this.noeudsVoisin = noeudsVoisin;
    }
    
    public void avancer()
    {
        
    }
            
}
