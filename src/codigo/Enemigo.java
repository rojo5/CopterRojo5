/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 *
 * @author rojo5
 */
public class Enemigo {
    int alturaEnemigo=60;
    int anchoEnemigo =60;
    int posicionY;
    Rectangle2D cazaTie;
    
    public Enemigo (int ancho,int _anchoPantalla){
        
    }
    
    private void posicionInicial(int ancho){
        posicionY= (int) (Math.random()*60);
        
        cazaTie = new Rectangle2D.Double(ancho,200+ posicionY, anchoEnemigo, alturaEnemigo);
    }
    
    private void cargaTextura(){
        
    }
    
//    public boolean mueve(Graphics2D g2){
//        g2.setColor(Color.red);
//        
////        g2.drawImage(img, xform, obs)
//        
//        g2.fill(cazaTie);
//    }
}
