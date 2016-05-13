/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.iutvalence.recherchefourmis.environment;

import java.util.ArrayList;
import java.util.HashMap;
/**
 *
 * @author antony
 */
public class Environment implements InterfaceEnvironment{
    private HashMap<String, Arc> matrice;
    private ArrayList<String> noeuds;

    public Environment(ArrayList<String> noeuds) {
        this.noeuds = noeuds;
        this.initMatrice();
    }

    
    
    private void initMatrice(){
        this.matrice = new HashMap<>();
        for(int i = 0; i < noeuds.size(); i++){
            for(int j = i+1; j<noeuds.size(); j++){
                this.matrice.put(noeuds.get(i) + "," + noeuds.get(j), new Arc());
            }
        }
    }
    
    

    @Override
    public Arc getArc(String debut, String fin) {
        Arc arc = this.matrice.get(debut + "," + fin);
        if(arc == null){
            arc = this.matrice.get(fin + "," + debut);
        }
        return arc;
    }
    
}
