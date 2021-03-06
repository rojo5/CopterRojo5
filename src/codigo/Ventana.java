/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 *
 * @author rojo5
 */
public class Ventana extends javax.swing.JFrame {
    
    //Declaracion de variables y objetos
    static int ANCHOPANTALLA = 811;
    static int ALTOPANTALLA= 420;
    static int ANCHOCOLUMNA= 30;
    static int SEPARACIONENEMIGO = 1500 ;
    long principio;
    long acaba;
    int distancia;
    int distanciaMax;
    Boolean gatillo = false, bala = false, auxiliar = false;
    Image fondo;
    //OBJETOS
    Aeronave miAeronave = new Aeronave(60, 60, Color.red);
    Disparo laser;
    Enemigo arrayEnemigo[]= new Enemigo[3];
    
    Pasillo  arrayPasillo[]= new Pasillo[29];
    
    BufferedImage buffer = null;
    Graphics2D lienzoGraphics, bufferGraphics = null;
    
    //Declaracion del temporizador
    Timer temporizador  = new Timer(10, new ActionListener() { //Por defecto estaba a 10
        @Override
        public void actionPerformed(ActionEvent e) {
            bucleDelJuego();
        }
    });
    
    /**
     * Creates new form Ventana
     */
    public Ventana() {
        initComponents();
        inicializaBuffers();
        jDialog2.setSize(824,411);
        jDialog2.setVisible(true);
        creaColumnas();
        cargar(new File("Puntuacion.txt"));
        jDialog1.setSize(812, 420);        
        creaEnemigos();
    }
    
    //Metodo para crear a los enemigos
    private void creaEnemigos(){
        for(int u=0;u < arrayEnemigo.length;u++){
            arrayEnemigo[u] = new Enemigo(ANCHOPANTALLA+ u*SEPARACIONENEMIGO, ANCHOPANTALLA);
        }
        
         
        
    }
    
    //Creo este metodo para crear un array del objeto pasillo  añadir todos los parametros
    //que tendra cada objeto pasillo
    private void creaColumnas(){
        int posicion =-30;
       for(int i=0; i<arrayPasillo.length;i++){
         arrayPasillo[i]= new Pasillo(ANCHOCOLUMNA+posicion, ANCHOPANTALLA, this);
         posicion+=ANCHOCOLUMNA;
        }
       principio= System.currentTimeMillis();
    }
    //Metodo para cargar las imagenes de la ventana
    private Image cargaImagen(String nombreImagen, double altoImagen){
        return(new ImageIcon(new ImageIcon(getClass().getResource(nombreImagen))
                .getImage().getScaledInstance(ANCHOPANTALLA, (int) ALTOPANTALLA, Image.SCALE_DEFAULT))).getImage();
    }
    
    //Inicializamos los buffers para poder crear un lienzo donde poder representar os objetos
    private void inicializaBuffers(){
        lienzoGraphics = (Graphics2D) jPanel1.getGraphics();
        buffer = (BufferedImage) jPanel1.createImage(ANCHOPANTALLA, ALTOPANTALLA);
        bufferGraphics = buffer.createGraphics();
        
        bufferGraphics.setColor(Color.BLACK);
        bufferGraphics.fillRect(0, 0, ANCHOPANTALLA, ALTOPANTALLA);
        
        //Ponemos el fondo
        fondo= cargaImagen("/imagenes/fondo.png", ALTOPANTALLA/2);
    }
    
    //En este metodo se encuentran los objetos que forman parte del juego con sus parametros
    //Aqui se pintan en el buffer el los objetos
    private void bucleDelJuego(){
       
        
        bufferGraphics.setColor(Color.BLACK);
        bufferGraphics.fillRect(0, 0, ANCHOPANTALLA, ALTOPANTALLA); 
        //FONDO
        bufferGraphics.drawImage(fondo, 0,0, null);
        
        if(distancia >25){
              for (Enemigo arrayEnemigo1 : arrayEnemigo) {
                    arrayEnemigo1.mueve(bufferGraphics);   
              }
         }
        
        //Pinto las columnas( clase Pasillo)
        for (Pasillo arrayPasillo1 : arrayPasillo) {
            arrayPasillo1.pintaColumna(bufferGraphics);        
        }
        miAeronave.vuela(bufferGraphics);
        
        //Generacion del disparo
        if(gatillo==true){
            bala = true;
            auxiliar = true;
            if(laser.disparo.getX() > ANCHOPANTALLA){
                gatillo= false;
                bala=false;   
            }
        }
        if (auxiliar ==true){
            laser.mueve(bufferGraphics);
        }
        
        
        //Pinto Marcador
        acaba= System.currentTimeMillis();
        distancia = (int) ((acaba-principio)/60);
        bufferGraphics.setColor(Color.yellow);
        bufferGraphics.setFont(new Font("Courier New", Font.BOLD, 16));
        bufferGraphics.drawString("Distancia: "+ distancia, ANCHOPANTALLA/2+50, 400);
        if(distancia > distanciaMax){
            distanciaMax= distancia;
        }
        bufferGraphics.drawString("Record: "+ distanciaMax, 50, 400);
        
        lienzoGraphics.drawImage(buffer, 0,0, null);
        
        //Colisiones con los muros
        for (Pasillo arrayPasillo1 : arrayPasillo) {
            if (miAeronave.chequeaColision(arrayPasillo1 ) == true) {
                guardar();
                temporizador.stop();
                jDialog1.setVisible(true);
            }
        }  
        //Colisiones con las naves enemigas
        for (Enemigo arrayEnemigo1 : arrayEnemigo) {
            if (miAeronave.chequeaEnemigo(arrayEnemigo1 ) == true) {
                guardar();
                temporizador.stop();
                jDialog1.setVisible(true);
            }
        }  
        
        //Colisiones de los disparos con las naves
        if(auxiliar == true){
        for (Enemigo arrayEnemigo1 : arrayEnemigo) {
            if (laser.chequeaEnemigo(arrayEnemigo1 ) == true) {
                //Saca de la pantalla al caza y el disparo
                arrayEnemigo1.cazaTie.setFrame(0- arrayEnemigo1.cazaTie.getWidth(), arrayEnemigo1.cazaTie.getY(),arrayEnemigo1.cazaTie.getWidth(), arrayEnemigo1.cazaTie.getHeight());
                laser.disparo.setFrame(ANCHOPANTALLA+2, laser.disparo.getY(),laser.disparo.getWidth(), laser.disparo.getHeight());
                try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream( getClass().getResource("/sonidos/Explosion.wav") ));
            clip.loop(0);
        } catch (Exception e) {      
        }
            }
        }  
        }
        
        
        
    }
    
       //Metodo para guardar la maxima puntuacion en un archivo 
    public void guardar(){
        FileWriter out;
        
        try{
            out = new FileWriter("Puntuacion.txt");
            out.write(""+distanciaMax);
            out.close();
        }
        catch(IOException i){
             System.out.println("Error: "+i.getMessage());
        }
    }
    //Metodo para cargar la puntuacion almacenada en el fichero
    public void cargar(File fichero){
        try{
            Scanner reader = new Scanner(fichero);
            while(reader.hasNext()){
                int valor = reader.nextInt();
                if(valor > distanciaMax){
                    distanciaMax= valor;
                }
            }
        }
        catch(IOException i){
            System.out.println("Error. "+i);
        }
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jDialog2 = new javax.swing.JDialog();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();

        jTextField1.setFont(new java.awt.Font("Comic Sans MS", 0, 36)); // NOI18N
        jTextField1.setText("HAS PERDIDO");
        jTextField1.setEnabled(false);

        jButton1.setText("Reiniciar");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addGap(256, 256, 256)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(174, Short.MAX_VALUE))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/aurebesh.png"))); // NOI18N
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton2MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addGap(230, 230, 230)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(251, Short.MAX_VALUE))
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(135, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 812, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    //Dectecta si se pulsa la tecla del espacio y al pulsar reduce la coordenada Y
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_SPACE){
            miAeronave.yVelocidad+=9;
        }
        //Disparar
        if(evt.getKeyCode() == KeyEvent.VK_M){
            laser = new Disparo(miAeronave, ANCHOPANTALLA);
            if(bala == false){
            gatillo = true;
            }
            try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream( getClass().getResource("/sonidos/laserSound.wav") ));
            clip.loop(0);
        } catch (Exception e) {      
        }
            
        }
        
    }//GEN-LAST:event_formKeyPressed
    
    //Reiniciar juego
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        jDialog1.setVisible(false);
       
        distancia=distancia-distancia*2;
        miAeronave.x=50 ;
        miAeronave.y= ALTOPANTALLA/2-30;
        laser.disparo.setFrame(ANCHOPANTALLA+2, laser.disparo.getY(),laser.disparo.getWidth(), laser.disparo.getHeight());
        creaColumnas();
        creaEnemigos();
      
            temporizador.restart();
        
        
        
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MousePressed
        temporizador.start();
        jDialog2.setVisible(false);
    }//GEN-LAST:event_jButton2MousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
