/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import imagen.Imagen;
import imagen.Scalar;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Esta clase se encarga de pintar la
 * representacion de los valores de nivel
 * de intensidad para una fila de una imagen
 * en un plano en forma de senial.
 * 
 * @author sebaxtian
 */


public class CanvasSignal extends Canvas {
    
    // Atributos de clase
    private Imagen objImagen;
    private int fila;
    
    public CanvasSignal() {
        setBackground(Color.WHITE);
    }
    
    private void pintarPlano(Graphics g) {
        // dibuja los ejes
        g.drawLine(30, 20, 30, 320);
        g.drawLine(30, 320, 590, 320);
        
        // Fuente
        Font sanSerifFont = new Font("SanSerif", Font.PLAIN, 9);
        g.setFont(sanSerifFont);
        
        // dibuja los labels
        g.drawString("Niveles", 20, 10);
        g.drawString("Pixeles", 560, 340);
        
        int nivel = 0;
        // ---> Eje en Y
        nivel = (int)Math.pow(2, 5);
        g.drawString(""+nivel, 10, 320-nivel);
        g.drawLine(30, 320-nivel, 35, 320-nivel);
        
        nivel = (int)Math.pow(2, 6);
        g.drawString(""+nivel, 10, 320-nivel-20);
        g.drawLine(30, 320-nivel-20, 35, 320-nivel-20);
        
        nivel = (int)Math.pow(2, 7);
        g.drawString(""+nivel, 10, 320-nivel-20);
        g.drawLine(30, 320-nivel-20, 35, 320-nivel-20);
        
        nivel = (int)Math.pow(2, 8);
        g.drawString(""+nivel, 10, 320-nivel);
        g.drawLine(30, 320-nivel, 35, 320-nivel);
        
        // ---> Eje en X
        nivel = (int)Math.pow(2, 5);
        g.drawString(""+nivel, 25+nivel, 335);
        g.drawLine(30+nivel, 320, 30+nivel, 315);
        
        nivel = (int)Math.pow(2, 6);
        g.drawString(""+nivel, 25+nivel+20, 335);
        g.drawLine(30+nivel+20, 320, 30+nivel+20, 315);
        
        nivel = (int)Math.pow(2, 7);
        g.drawString(""+nivel, 25+nivel+20, 335);
        g.drawLine(30+nivel+20, 320, 30+nivel+20, 315);
        
        nivel = (int)Math.pow(2, 8);
        g.drawString(""+nivel, 25+nivel+20, 335);
        g.drawLine(30+nivel+20, 320, 30+nivel+20, 315);
        
        nivel = (int)Math.pow(2, 9);
        g.drawString(""+nivel, 25+nivel, 335);
        g.drawLine(30+nivel, 320, 30+nivel, 315);
    }
    
    public void pintarSignal(Imagen objImagen, int fila) {
        Scalar scalarImagen = new Scalar(objImagen, 512, 512);
        scalarImagen.escalacionBicubica();
        objImagen.setMatrizGris(scalarImagen.getImagenEscalada());
        objImagen.setN(objImagen.getMatrizGris().length);
        objImagen.setM(objImagen.getMatrizGris()[0].length);
        this.objImagen = objImagen;
        this.fila = fila;
        System.out.println("Alto: "+objImagen.getN()+" Ancho: "+objImagen.getM());
    }
    
    private void signal(Graphics g) {
        short[] signalFila = objImagen.getMatrizGris()[fila];
        for(int i = 0; i < signalFila.length; i++) {
            g.fillOval(i+30, 320-signalFila[i], 2, 2);
            g.drawString("Fila: "+fila, 256, 20);
        }
    }
    
    public void setFila(int fila) {
        this.fila = fila;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        pintarPlano(g);
        signal(g);
    }
}

