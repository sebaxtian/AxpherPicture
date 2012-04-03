/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package axpherpicture;

import imagen.Histograma;
import imagen.Imagen;

/**
 * Clase Main del proyecto AxpherPicture
 * 
 * @author Juan Sebastian Rios Sabogal
 * @Fecha sab mar 31 21:01:59 COT 2012
 * @version 0.1
 */


public class AxpherPicture {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        /**
         * Imagen en formato PGM
         */
        String rutaImgPGM = "ImgFuente/lena.pgm";
        Imagen imgPGM = new Imagen(rutaImgPGM);
        imgPGM.guardarImagen("ImgProcesado/lenaCopia.pgm");
        //calcula el histograma
        Histograma histogramaImgPGM = new Histograma(imgPGM);
        Imagen imgHistogramaPGM = histogramaImgPGM.getImagenHistograma();
        imgHistogramaPGM.guardarImagen("ImgProcesado/histogramaLena.pgm");
        
        /**
         * Imagen en formato PPM
         */
        String rutaImgPPM = "ImgFuente/lena.ppm";
        Imagen imgPPM = new Imagen(rutaImgPPM);
        imgPPM.guardarImagen("ImgProcesado/lenaCopia.ppm");
        //calcula escala de grises
        Imagen imgGrises = imgPPM.getEscalaGrises();
        imgGrises.guardarImagen("ImgProcesado/lenaGrises.pgm");
        //calcula el histograma
        Histograma histogramaImgPPM = new Histograma(imgPPM);
        Imagen imgHistogramaPPM = histogramaImgPPM.getImagenHistograma();
        imgHistogramaPPM.guardarImagen("ImgProcesado/histogramaLena.ppm");
    }
}
