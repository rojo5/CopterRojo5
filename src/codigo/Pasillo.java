package codigo;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Usuario
 */
public class Pasillo extends Rectangle2D.Double{
    
    int altoColumna= 300;
    int anchoColumna= 0;
    
    public Pasillo(int _ancho, int _anchoPantalla){
        anchoColumna= _ancho;
    }
    
    public void pintaColumna(Graphics2D g2){
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 50, this.anchoColumna , this.altoColumna);
        
    }
}
