/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import imagen.Imagen;
import imagen.Scalar;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/**
 * Esta clase CanvasImagen permite
 * pintar un objeto imagen sobre
 * el mismo.
 * 
 * @author Juan Sebastian Rios Sabogal
 * 
 */


public class CanvasImagen extends Canvas {
    
    private Image imagen;
    
    public CanvasImagen() {
        setBackground(Color.WHITE);
    }
    
    public void pintarImagen(Imagen objImagen) {
        Scalar scalarImagen = new Scalar(objImagen, 358);
        scalarImagen.escalacionBicubica();
        if(objImagen.getFormato().equals("P2")) {
            if(objImagen.getNivelIntensidad() == 1) {
                pintarImgBinariaGris(scalarImagen.getImagenEscalada());
            } else {
                pintarImgGris(scalarImagen.getImagenEscalada());
            }
        } else {
            if(objImagen.getNivelIntensidad() == 1) {
                pintarImgBinariaRGB(scalarImagen.getMatrizR(), scalarImagen.getMatrizG(), scalarImagen.getMatrizB());
            } else {
                pintarImgRGB(scalarImagen.getMatrizR(), scalarImagen.getMatrizG(), scalarImagen.getMatrizB());
            }
        }
    }
    
    private void pintarImgGris(short matrizGris[][]) {
        System.out.println("Pinta Imagen Gris");
        Graphics g;
        Color color;
        int centroJ = (114/2);
        int centroI = (123/2);
        imagen = createImage(getWidth(), getHeight());
        g = imagen.getGraphics();
        for(int i = 0; i < matrizGris.length; i++) {
            for(int j = 0; j < matrizGris[0].length; j++) {
                int pixel = matrizGris[i][j];
                color = new Color(pixel, pixel, pixel);
                g.setColor(color);
                g.fillOval(j+centroJ, i+centroI, 2, 2);
            }
        }
        g = getGraphics();
        g.drawImage(imagen, 0, 0, this);
    }
    
    private void pintarImgRGB(short matrizR[][],short matrizG[][],short matrizB[][]) {
        System.out.println("Pinta Imagen RGB");
        Graphics g;
        Color color;
        int centroJ = (114/2);
        int centroI = (123/2);
        imagen = createImage(getWidth(), getHeight());
        g = imagen.getGraphics();
        for(int i = 0; i < matrizR.length; i++) {
            for(int j = 0; j < matrizR[0].length; j++) {
                int pixelR = matrizR[i][j];
                int pixelG = matrizG[i][j];
                int pixelB = matrizB[i][j];
                color = new Color(pixelR, pixelG, pixelB);
                g.setColor(color);
                g.fillOval(j+centroJ, i+centroI, 2, 2);
            }
        }
        g = getGraphics();
        g.drawImage(imagen, 0, 0, this);
    }
    
    private void pintarImgBinariaGris(short matrizGris[][]) {
        System.out.println("Pinta Imagen Binaria");
        Graphics g;
        Color color;
        int centroJ = (114/2);
        int centroI = (123/2);
        imagen = createImage(getWidth(), getHeight());
        g = imagen.getGraphics();
        for(int i = 0; i < matrizGris.length; i++) {
            for(int j = 0; j < matrizGris[0].length; j++) {
                int pixel = matrizGris[i][j];
                if(pixel == 1)
                    pixel = 255;
                color = new Color(pixel, pixel, pixel);
                g.setColor(color);
                g.fillOval(j+centroJ, i+centroI, 2, 2);
            }
        }
        g = getGraphics();
        g.drawImage(imagen, 0, 0, this);
    }
    
    private void pintarImgBinariaRGB(short matrizR[][],short matrizG[][],short matrizB[][]) {
        System.out.println("Pinta Imagen Binaria");
        Graphics g;
        Color color;
        int centroJ = (114/2);
        int centroI = (123/2);
        imagen = createImage(getWidth(), getHeight());
        g = imagen.getGraphics();
        for(int i = 0; i < matrizR.length; i++) {
            for(int j = 0; j < matrizR[0].length; j++) {
                int pixelR = matrizR[i][j];
                int pixelG = matrizG[i][j];
                int pixelB = matrizB[i][j];
                if(pixelR == 1)
                    pixelR = 255;
                if(pixelG == 1)
                    pixelG = 255;
                if(pixelB == 1)
                    pixelB = 255;
                color = new Color(pixelR, pixelG, pixelB);
                g.setColor(color);
                g.fillOval(j+centroJ, i+centroI, 2, 2);
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
