/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import imagen.Imagen;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Esta clase canvas permite
 * pintar la imagen de un
 * histograma.
 * 
 * @author Juan Sebastian Rios Sabogal
 */


public class CanvasHistograma extends Canvas implements MouseMotionListener {
    
    private Image imagen;
    private String formato;
    
    public CanvasHistograma() {
        setBackground(Color.WHITE);
        addMouseMotionListener(this);
    }
    
    public void pintarHistograma(Imagen imgHistograma) {
        if(imgHistograma.getFormato().equals("P2")) {
            System.out.println("pinta histograma P2");
            formato = imgHistograma.getFormato();
            this.setSize(imgHistograma.getM(), imgHistograma.getN());
            pintarHistogramaGris(imgHistograma.getMatrizGris());
        } else {
            System.out.println("pinta histograma P3");
            formato = imgHistograma.getFormato();
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

    @Override
    public void mouseDragged(MouseEvent e) {
        //System.out.println("Arrastra El Mouse Sobre El Canvas "+Math.random());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Graphics g = getGraphics();
        g.clearRect(0, 0, imagen.getWidth(this), imagen.getHeight(this));
        g.drawImage(imagen, 0, 0, this);
        
        if(formato.equals("P2")) {
            g.setColor(Color.red);
            if(e.getX() < 128) {
                g.drawString(""+e.getX(), e.getX()+4, e.getY());
            } else {
                g.drawString(""+e.getX(), e.getX()-25, e.getY());
            }
            g.setColor(Color.cyan);
            g.drawLine(e.getX(), 0, e.getX(), imagen.getHeight(this));
        }
        if(formato.equals("P3")) {
            g.setColor(Color.black);
            int nivel = 0;
            if(e.getX() <= 255) {
                nivel = e.getX();
            }
            if(e.getX() > 255 && e.getX() <= 510) {
                nivel = e.getX()-255;
            }
            if(e.getX() > 510 && e.getX() <= 765) {
                nivel = e.getX()-510;
            }
            if(e.getX() < 128) {
                g.drawString(""+nivel, e.getX()+4, e.getY());
            }
            if(e.getX() <= 255 && e.getX() >= 128) {
                g.drawString(""+nivel, e.getX()-25, e.getY());
            }
            if(e.getX() > 255 && e.getX() < 383) {
                g.drawString(""+nivel, e.getX()+4, e.getY());
            }
            if(e.getX() <= 510 && e.getX() >= 383) {
                g.drawString(""+nivel, e.getX()-25, e.getY());
            }
            if(e.getX() > 510 && e.getX() < 638) {
                g.drawString(""+nivel, e.getX()+4, e.getY());
            }
            if(e.getX() <= 765 && e.getX() >= 638) {
                g.drawString(""+nivel, e.getX()-25, e.getY());
            }
            g.setColor(Color.yellow);
            g.drawLine(e.getX(), 0, e.getX(), imagen.getHeight(this));
        }
    }
}
