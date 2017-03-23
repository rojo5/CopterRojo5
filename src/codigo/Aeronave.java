/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;

/**
 *
 * @author Rojo5
 */
public class Aeronave  extends Rectangle2D.Double{
    
    //Declaracion de variables 
    int alturaVentana = Ventana.HEIGHT;
    Color colorNave;
    Image textura;
    double yVelocidad = -2;
    
    public Aeronave(int _ancho, int _alto, Color _color){
        super(100, 150, _ancho, _alto);
        colorNave = _color;
        textura = (new ImageIcon(new ImageIcon(getClass().getResource("/imagenes/X-Wing.png"))
                .getImage().getScaledInstance(60, 65, Image.SCALE_DEFAULT))).getImage();
    }
    
    public void vuela(Graphics2D g2){
        AffineTransform trans = new AffineTransform();
         trans.translate(x, y);  //mueve la imagen a la posición en que tiene que ser dibujada
        this.y = this.y - yVelocidad;
        //pongo un tope para que no se salga por el techo
        if (this.y < 0) {
            this.y = 0;
            yVelocidad = -1;
        }
         g2.drawImage(textura, trans,null);
        g2.setColor(colorNave);
//        g2.draw(this);
        yVelocidad -= 1;
        if (yVelocidad < -3){
            yVelocidad = -1;
        }
    }
    
    public boolean chequeaColision(Pasillo p){
           //Calcula si la nave se sale de los rectangulos

            
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
    
    public boolean  chequeaEnemigo(Enemigo e){
        Area  areaNave = new Area(this);
        Area  areaEnemigo = new Area(e.cazaTie);
        boolean colision = true;
        
        areaNave.intersect(areaEnemigo);
            
            if(areaNave.isEmpty()){
                colision=false;
            }
        return this.intersects(e.cazaTie) || colision;
    }
}
