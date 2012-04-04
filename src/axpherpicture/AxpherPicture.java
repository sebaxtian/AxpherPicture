/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package axpherpicture;

import imagen.Histograma;
import imagen.Imagen;
import imagen.Umbralizacion;

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
        //calcula el umbral
        Umbralizacion umbralizacionPGM = new Umbralizacion(histogramaImgPGM, 0);
        int umbralGris = umbralizacionPGM.getUmbralGris();
        System.out.println("UmbralGris: "+umbralGris);
        //construye una imagen binaria
        Imagen imgBinariaPGM = new Imagen();
        imgBinariaPGM.setFormato("P2");
        imgBinariaPGM.setNivelIntensidad(1);
        imgBinariaPGM.setN(imgPGM.getN());
        imgBinariaPGM.setM(imgPGM.getM());
        short matrizGris[][] = new short[imgBinariaPGM.getN()][imgBinariaPGM.getM()];
        for(int i = 0; i < imgBinariaPGM.getN(); i++) {
            for(int j = 0; j < imgBinariaPGM.getM(); j++) {
                if(imgPGM.getMatrizGris()[i][j] < umbralGris) {
                    matrizGris[i][j] = 0;
                } else {
                    matrizGris[i][j] = 1;
                }
            }
        }
        imgBinariaPGM.setMatrizGris(matrizGris);
        imgBinariaPGM.guardarImagen("ImgProcesado/binariaLena.pgm");
        
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
        //calcula el umbral
        Umbralizacion umbralizacionPPM = new Umbralizacion(histogramaImgPPM, 0);
        int umbralR = umbralizacionPPM.getUmbralR();
        int umbralG = umbralizacionPPM.getUmbralG();
        int umbralB = umbralizacionPPM.getUmbralB();
        System.out.println("UmbralR: "+umbralR);
        System.out.println("UmbralG: "+umbralG);
        System.out.println("UmbralB: "+umbralB);
        //construye una imagen binaria
        Imagen imgBinariaPPM = new Imagen();
        imgBinariaPPM.setFormato("P3");
        imgBinariaPPM.setNivelIntensidad(1);
        imgBinariaPPM.setN(imgPPM.getN());
        imgBinariaPPM.setM(imgPPM.getM());
        short matrizR[][] = new short[imgBinariaPPM.getN()][imgBinariaPPM.getM()];
        short matrizG[][] = new short[imgBinariaPPM.getN()][imgBinariaPPM.getM()];
        short matrizB[][] = new short[imgBinariaPPM.getN()][imgBinariaPPM.getM()];
        for(int i = 0; i < imgBinariaPPM.getN(); i++) {
            for(int j = 0; j < imgBinariaPPM.getM(); j++) {
                //canal R
                if(imgPPM.getMatrizR()[i][j] < umbralR) {
                    matrizR[i][j] = 0;
                } else {
                    matrizR[i][j] = 1;
                }
                //canal G
                if(imgPPM.getMatrizG()[i][j] < umbralG) {
                    matrizG[i][j] = 0;
                } else {
                    matrizG[i][j] = 1;
                }
                //canal B
                if(imgPPM.getMatrizB()[i][j] < umbralB) {
                    matrizB[i][j] = 0;
                } else {
                    matrizB[i][j] = 1;
                }
            }
        }
        imgBinariaPPM.setMatrizR(matrizR);
        imgBinariaPPM.setMatrizG(matrizG);
        imgBinariaPPM.setMatrizB(matrizB);
        imgBinariaPPM.guardarImagen("ImgProcesado/binariaLena.ppm");
    }
}
