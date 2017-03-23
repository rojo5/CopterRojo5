/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;

/**
 *
 * @author Usuario
 */
public class Disparo {
    //Declaracion de las variables
    int alturaDisparo = 2;
    int anchoDisparo=10;
    private int anchoPantalla;
    Rectangle2D disparo;
    Image texturaLaser;
    
    public Disparo(Aeronave jugador, int _anchoPantalla){
        anchoPantalla= _anchoPantalla;
        posicionIncial(jugador);
        cargaTextura();
        
    }
    //Genera el disparo en la posicion de la nave del jugador justo en el centro
    private void posicionIncial(Aeronave jugador){
        
        disparo = new Rectangle2D.Double(jugador.getX()+jugador.width, jugador.getCenterY(), anchoDisparo, alturaDisparo);
    }
    
    private void cargaTextura(){
        texturaLaser = (new ImageIcon(new ImageIcon(
                getClass().getResource("/imagenes/laser.png"))
                .getImage().getScaledInstance(10, 2, Image.SCALE_DEFAULT)))
                .getImage();
    }
    
    public void mueve(Graphics2D g2){
        animacion();
        g2.setColor(Color.red);
        

//            g2.drawImage(texturaTie, (int)cazaTie.getX(), (int)cazaTie.getY(), null);
        g2.fill(disparo);
        g2.draw(disparo);

    }
    
    private void animacion(){
        disparo.setFrame(disparo.getX()+2, disparo.getY(),disparo.getWidth(), disparo.getHeight());
    }
    
    //Comprueba si ha dado a un enemigo
     public boolean  chequeaEnemigo(Enemigo e){
        Area  areaDisparo = new Area(disparo);
        Area  areaEnemigo = new Area(e.cazaTie);
        boolean colision = true;
        
        areaDisparo.intersect(areaEnemigo);
            
            if(areaDisparo.isEmpty()){
                colision=false;
            }
        return areaDisparo.intersects(e.cazaTie) || colision;
    }
}


//Tienes que ver como hacer que se vea el objeto al pulsar una tecla