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
 * @author Rojo5
 */
public class Pasillo extends Rectangle2D.Double{
    
    //Declaracion de variables
    int altoColumna= 275;
    int anchoColumna= 0;
    int posicionX= 0;
    int posicionY = 50;
    
    public Pasillo(int _ancho, int _anchoPantalla, int _posicion){
        anchoColumna= _ancho;
        posicionX = _posicion;
    }
    
    //Este metodo se encarga de la representacion de la columna en el juego
    public void pintaColumna(Graphics2D g2){
        g2.setColor(Color.BLACK);
        g2.fillRect(posicionX, posicionY, this.anchoColumna , this.altoColumna);
        
    }
}
