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
    Rectangle2D columna;
    int altoColumna = 275;
    int anchoColumna = 30;
    int posicionX = 50;
    int posicionY = 50;
    private int anchoPantalla;

    public Pasillo(int ancho, int _anchoPantalla) {
        posicionInicial(ancho);
        anchoPantalla = _anchoPantalla;
    }

    private void posicionInicial(int ancho) {
        Random aleatorio = new Random();
        int desplazamiento = aleatorio.nextInt(5);

        columna = new Rectangle2D.Double(ancho, posicionY - desplazamiento, anchoColumna, altoColumna);

    }

    //Este metodo se encarga de la representacion de la columna en el juego
    public void pintaColumna(Graphics2D g2) {
        animacionColumna();

        g2.setColor(Color.BLACK);
        g2.fill(columna);
//        g2.draw(columna);

    }

    private void animacionColumna() {
        if (columna.getX() + columna.getWidth() < 0) {
            if (posicionY > 30) {
                posicionY-=13;
                posicionInicial(anchoPantalla);
            }else{
                posicionY =75;
                
            }

        } else {
            columna.setFrame(columna.getX() - 1, columna.getY(), columna.getWidth(), columna.getHeight());
        }
    }
}
