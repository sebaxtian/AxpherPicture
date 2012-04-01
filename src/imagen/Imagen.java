/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagen;

import java.io.File;

/**
 * Esta es la deficion de la clase Imagen,
 * la clase es una abstraccion de una imagen
 * que puede ser una imagen tipo PPM o PGM,
 * Una imagen esta compuesta por un formato,
 * alto, ancho y nivel de intensidad, ademas
 * de una matriz,
 * Para imagenes a escala de grises consta
 * de una matriz para el canal gris y para
 * imagenes a color consta de tres matrices,
 * una por cada canal de color RGB.
 * 
 * @author sebaxtian
 * @Fecha sab mar 31 21:10:21 COT 2012
 * @version 0.1
 */


public class Imagen {
    
    //Atributos de clase
    private File archivoImagen;
    private String formato;
    private int n;
    private int m;
    private int nivelIntensidad;
}
