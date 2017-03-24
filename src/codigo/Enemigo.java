/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author rojo5
 */
public class Enemigo {
    
    //Declaracion de variables
    int alturaEnemigo=60;
    int anchoEnemigo =60;
    private int anchoPantalla;
    int posicionY =100;
    int posicionX;
    Image texturaTie;
    Rectangle2D cazaTie;
    
    public Enemigo (int ancho,int _anchoPantalla){
        posicionInicial(ancho);
        anchoPantalla=_anchoPantalla;
        cargaTextura();
    }
    
    //Metodo encargado de pintar al enemigo
    private void posicionInicial(int ancho){
        posicionX = ancho;
       Random aleatroio = new Random();
        int desplazamiento = aleatroio.nextInt(150)+100;
        
        
        cazaTie = new Rectangle2D.Double(posicionX, desplazamiento, anchoEnemigo, alturaEnemigo);
    }
    
    private void cargaTextura(){
        texturaTie = (new ImageIcon(new ImageIcon(
                getClass().getResource("/imagenes/TIE_Fighter.png"))
                .getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT)))
                .getImage();
    }
    
    public void mueve(Graphics2D g2){
        animacionEnemigo();
        g2.setColor(Color.red);
        
//        g2.drawImage(img, xform, obs)
            g2.drawImage(texturaTie, (int)cazaTie.getX(), (int)cazaTie.getY(), null);
//        g2.draw(cazaTie);
//        g2.fill(cazaTie);
    }
    
    private void animacionEnemigo(){
        if(cazaTie.getX()+ anchoEnemigo < 0){
            
            posicionInicial(anchoPantalla);
        }
        else{
            cazaTie.setFrame(cazaTie.getX()-3, cazaTie.getY(),cazaTie.getWidth(), cazaTie.getHeight());
        }
    }
}
