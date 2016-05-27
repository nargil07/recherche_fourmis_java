/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.iutvalence.recherchefourmis;

import fr.iutvalence.recherchefourmis.entite.EnumModeFourmis;
import fr.iutvalence.recherchefourmis.entite.Fourmi;
import fr.iutvalence.recherchefourmis.environment.Environment;
import java.util.ArrayList;

/**
 *
 * @author antony
 */
public class Launcher {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.add("E");
        list.add("F");
        list.add("N");
        Environment environment = new Environment(list);
        environment.getArc("N", "B").metrique = 5;
        environment.getArc("N", "B").pheromones = 5;
        environment.getArc("N", "A").metrique = 1;
        environment.getArc("N", "C").metrique = 0;
        environment.getArc("N", "D").metrique = 0;
        environment.getArc("N", "E").metrique = 0;
        environment.getArc("N", "F").metrique = 0;
        environment.getArc("B", "A").metrique = 0;
        environment.getArc("B", "C").metrique = 1;
        environment.getArc("B", "D").metrique = 0;
        environment.getArc("B", "E").metrique = 0;
        environment.getArc("B", "F").metrique = 0;
        environment.getArc("A", "C").metrique = 5;
        environment.getArc("A", "D").metrique = 0;
        environment.getArc("A", "E").metrique = 0;
        environment.getArc("A", "F").metrique = 0;
        environment.getArc("C", "D").metrique = 4;
        environment.getArc("C", "E").metrique = 1;
        environment.getArc("C", "F").metrique = 0;
        environment.getArc("D", "E").metrique = 0;
        environment.getArc("D", "F").metrique = 3;
        environment.getArc("E", "F").metrique = 1;
        
        for(int i = 0; i < 12; i++){
            Thread t = new Thread(new Fourmi(environment, EnumModeFourmis.SUIVEUSE, "Foumis-" + String.valueOf(i)));
            t.start();
            Thread.sleep(1000);
        }
    }
}
