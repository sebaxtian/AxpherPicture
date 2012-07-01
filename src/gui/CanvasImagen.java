/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import imagen.DcmImg;
import imagen.Imagen;
import imagen.Scalar;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

/**
 * Esta clase CanvasImagen permite
 * pintar un objeto imagen sobre
 * el mismo.
 * 
 * @author Juan Sebastian Rios Sabogal
 * 
 */


public class CanvasImagen extends Canvas implements MouseListener, MouseMotionListener {
    
    private Image imagen;
    private Point puntoInicial;
    private Point puntoFinal;
    private double factEscala;
    private BufferedImage bfimg;
    
    public CanvasImagen() {
        setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    public void pintarImagen(Imagen objImagen) {
        Scalar scalarImagen = new Scalar(objImagen, 358);
        scalarImagen.escalacionBicubica();
        factEscala = scalarImagen.factor;
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
        this.getGraphics().drawImage(bfimg, 0, 0, this);
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
        g = bfimg.getGraphics();
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
        g = bfimg.getGraphics();
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
        g = bfimg.getGraphics();
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
        g = bfimg.getGraphics();
        g.drawImage(imagen, 0, 0, this);
    }
    
    private double distancia(Point puntoInicial, Point puntoFinal) {
        double distancia = 0;
        
        int x1 = (int)Math.floor(puntoInicial.x / factEscala);
        int x2 = (int)Math.floor(puntoFinal.x / factEscala);
        int y1 = (int)Math.floor(puntoInicial.y / factEscala);
        int y2 = (int)Math.floor(puntoFinal.y / factEscala);
        
        distancia = Math.sqrt( Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2) );
        
        return distancia;
    }
    
    
    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        /*super.paint(g);
        g.drawImage(imagen, 0, 0, this);*/
        bfimg = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        //bfimg.getGraphics().drawImage(imagen, 0, 0, this);
        
        g.drawImage(bfimg, 0, 0, this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        puntoInicial = e.getPoint();
        System.out.println("Click en el Canvas ["+puntoInicial.x+" , "+puntoInicial.y+"]");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        puntoFinal = e.getPoint();
        if(puntoInicial != null) {
            Graphics g = bfimg.getGraphics();
            
            g.setColor(Color.red);
            g.drawImage(imagen, 0, 0, this);
            g.drawLine(puntoInicial.x, puntoInicial.y, puntoFinal.x, puntoFinal.y);
            
            // Fuente
            Font sanSerifFont = new Font("SanSerif", Font.PLAIN, 9);
            g.setFont(sanSerifFont);
            g.setColor(Color.YELLOW);
            
            double d = distancia(puntoInicial, puntoFinal);
            
            double pixelSpacing = DcmImg.pixelSpacing;
            
            int xd = (int)(puntoFinal.x+20);
            int yd = (int)(puntoFinal.y+20);
            
            g.drawString("["+d*pixelSpacing+"] mm", xd, yd);
            
            this.getGraphics().drawImage(bfimg, 0, 0, this);
            
            System.out.println("Mueve el mouse en el Canvas ["+puntoFinal.x+" , "+puntoFinal.y+"]");
        }
    }
}
