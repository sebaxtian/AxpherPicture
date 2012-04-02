/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package axpherpicture;

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
        String rutaImagen = "ImgFuente/lena.ppm";
        Imagen objImagen = new Imagen(rutaImagen);
        objImagen.guardarImagen("ImgProcesado/lenaCopia.ppm");
        Imagen objImagenGrises = objImagen.getEscalaGrises();
        objImagenGrises.guardarImagen("ImgProcesado/lenaGrises.pgm");
    }
}
