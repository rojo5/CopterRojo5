package codigo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rojo5
 */
public class Pasillo {

    //Declaracion de variables
    int hueco = 200;
    int alturaColumna =230;
    int anchoColumna =30;
    Rectangle2D suelo, techo;
    private int anchoPantalla;
    
    public Pasillo(int ancho, int _anchoPantalla){
        posicionInicial(ancho);
        anchoPantalla= _anchoPantalla;
    }
    
    private void posicionInicial(int ancho){
        Random aleatroio = new Random();
        int desplazamiento = aleatroio.nextInt(10)+100;
        
        techo = new Rectangle2D.Double(ancho, -desplazamiento - anchoColumna*2, anchoColumna, alturaColumna);
        
        suelo = new Rectangle2D.Double(ancho, alturaColumna + hueco - desplazamiento +anchoColumna/2, anchoColumna, alturaColumna);
    }
    
    public void pintaColumna(Graphics2D g2){
        animacionColumna();
        
        g2.setColor(Color.CYAN);
        
        g2.fill(techo);
        g2.fill(suelo);
        
//        g2.draw(techo);
//        g2.draw(suelo);
    }
    
    private void animacionColumna(){
        
        if(techo.getX() + anchoColumna < 0){
            posicionInicial(anchoPantalla);
        }
        else{
            techo.setFrame(techo.getX()-1, techo.getY(),techo.getWidth(), techo.getHeight());
            suelo.setFrame(suelo.getX()-1, suelo.getY(),suelo.getWidth(), suelo.getHeight());
        }
    }
}
