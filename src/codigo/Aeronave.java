/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Rojo5
 */
public class Aeronave  extends Rectangle2D.Double{
    
    //Declaracion de variables 
    int alturaVentana = Ventana.HEIGHT;
    Color colorNave;
    double yVelocidad = -2;
    
    public Aeronave(int _ancho, int _alto, Color _color){
        super(100, 150, _ancho, _alto);
        colorNave = _color;
    }
    
    public void vuela(Graphics2D g2){
        this.y = this.y - yVelocidad;
        //pongo un tope para que no se salga por el techo
        if (this.y < 0) {
            this.y = 0;
            yVelocidad = -1;
        }
        g2.setColor(colorNave);
        g2.fill(this);
        yVelocidad -= 1;
        if (yVelocidad < -3){
            yVelocidad = -1;
        }
    }
    
    public boolean chequeaColision(Pasillo p){
           //Calcula si la nave se sale de los rectangulos
//        if((this.y  < p.columna.getY() && this.x >= p.columna.getX() && this.x <= p.columna.getX() + p.columna.getWidth()) || 
//           (this.y+ this.height > p.columna.getY() + p.columna.getHeight() && this.x <= p.columna.getX() + p.columna.getWidth())){
//            return true;
//        }
//            return false;
            
            Area  areaNave = new Area(this);
            Area  areaTecho = new Area(p.techo);
            Area  areaSuelo = new Area(p.suelo);
            boolean colision = true, colision2 = true;
            
            areaNave.intersect(areaTecho);
            
            if(areaNave.isEmpty()){
                colision=false;
            }
            
            areaNave.intersect(areaSuelo);
            
            if(areaNave.isEmpty()){
                colision2=false;
            }
            
            return (this.intersects(p.techo) || this.intersects(p.suelo) || colision || colision2);
        
    }
}
