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


public class CanvasImagenSegmen extends Canvas {
    
    private Image imagen;
    private Point puntoInicial;
    private Point puntoFinal;
    private double factEscala;
    private BufferedImage bfimg;
    
    public CanvasImagenSegmen() {
        setBackground(Color.WHITE);
    }
    
    public void pintarImagen(Imagen objImagen) {
        Scalar scalarImagen = new Scalar(objImagen, 358);
        scalarImagen.escalacionBicubica();
        factEscala = scalarImagen.factor;
        if(objImagen.getFormato().equals("P2")) {
            pintarImgGris(scalarImagen.getImagenEscalada());
        }
        this.getGraphics().drawImage(bfimg, 0, 0, this);
    }
    
    private void pintarImgGris(short matrizGris[][]) {
        System.out.println("Pinta Imagen Gris");
        Graphics g;
        Color color;
        int centroJ = (114/2);
        int centroI = (123/2);
        imagen = createImage(512, 512);
        g = imagen.getGraphics();
        for(int i = 0; i < matrizGris.length; i++) {
            for(int j = 0; j < matrizGris[0].length; j++) {
                int pixel = matrizGris[i][j];
                color = new Color(pixel, pixel, pixel);
                g.setColor(color);
                g.fillOval(j+centroJ, i+centroI, 2, 2);
            }
        }
        //g = bfimg.getGraphics();
        g.drawImage(imagen, 0, 0, this);
        repaint();
    }
    
    public void pintarCirculo() {
        Graphics g = getGraphics();
        g.drawOval(250, 250, 200, 200);
        repaint();
    }
    
    
    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
       super.paint(g);
        g.drawImage(imagen, 0, 0, this);
        /*bfimg = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        //bfimg.getGraphics().drawImage(imagen, 0, 0, this);
        
        g.drawImage(bfimg, 0, 0, this);*/
    }

}
