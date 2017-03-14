/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Rojo5
 */
public class Aeronave  extends Rectangle2D.Double{
    int alturaVentana = Ventana.HEIGHT;
    Color colorNave;
    int yVelocidad = -2;
    
    public Aeronave(int _ancho, int _alto, Color _color){
        super(100, 150, _ancho, _alto);
        colorNave = _color;
    }
    
    public void vuela(Graphics2D g2){
        this.y = this.y - yVelocidad;
        //pongo un tope para que no se salga por el techo
        if (this.y < 0) {
            this.y = 0;
            yVelocidad = -2;
        }
        
       yVelocidad -= 1;
        if (yVelocidad < -3){
            yVelocidad = -2;
        }
        g2.setColor(colorNave);
        g2.fill(this);
        
        
    }
}
