/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import imagen.Imagen;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/**
 * Esta clase canvas permite
 * pintar la imagen de un
 * histograma.
 * 
 * @author Juan Sebastian Rios Sabogal
 */


public class CanvasHistograma extends Canvas {
    
    private Image imagen;
    
    public CanvasHistograma() {
        setBackground(Color.WHITE);
    }
    
    public void pintarHistograma(Imagen imgHistograma) {
        if(imgHistograma.getFormato().equals("P2")) {
            System.out.println("pinta histograma P2");
            this.setSize(imgHistograma.getM(), imgHistograma.getN());
            pintarHistogramaGris(imgHistograma.getMatrizGris());
        } else {
            this.setSize(imgHistograma.getM(), imgHistograma.getN());
            pintarHistogramaRGB(imgHistograma.getMatrizR(), imgHistograma.getMatrizG(), imgHistograma.getMatrizB());
        }
    }
    
    private void pintarHistogramaGris(short matrizGris[][]) {
        Graphics g;
        Color color;
        imagen = createImage(getWidth(), getHeight());
        g = imagen.getGraphics();
        for(int i = 0; i < matrizGris.length; i++) {
            for(int j = 0; j < matrizGris[0].length; j++) {
                int pixel = matrizGris[i][j];
                if(pixel == 256)
                    System.out.println(""+pixel);
                color = new Color(pixel, pixel, pixel);
                g.setColor(color);
                g.fillOval(j, i, 2, 2);
            }
        }
        g = getGraphics();
        g.drawImage(imagen, 0, 0, this);
    }
    
    private void pintarHistogramaRGB(short matrizR[][],short matrizG[][],short matrizB[][]) {
        Graphics g;
        Color color;
        imagen = createImage(getWidth(), getHeight());
        g = imagen.getGraphics();
        for(int i = 0; i < matrizR.length; i++) {
            for(int j = 0; j < matrizR[0].length; j++) {
                int pixelR = matrizR[i][j];
                int pixelG = matrizG[i][j];
                int pixelB = matrizB[i][j];
                if(pixelR == 256)
                    pixelR = 255;
                if(pixelG == 256)
                    pixelG = 255;
                if(pixelB == 256)
                    pixelB = 255;
                color = new Color(pixelR, pixelG, pixelB);
                g.setColor(color);
                g.fillOval(j, i, 2, 2);
            }
        }
        g = getGraphics();
        g.drawImage(imagen, 0, 0, this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(imagen, 0, 0, this);
    }
}
