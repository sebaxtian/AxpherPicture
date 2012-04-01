/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagen;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private short n;
    private short m;
    private short nivelIntensidad;
    private short matrizGris[][];
    private short matrizR[][];
    private short matrizG[][];
    private short matrizB[][];
    //factores de conversion a escala de grises
    private final float Alfa = 0.299f;
    private final float Beta = 0.587f;
    private final float Gama = 0.114f;
    
    
    /**
     * Metodo constructor por defecto,
     * crea una imagen con todos sus
     * atributos a valores nulos
     */
    public Imagen() {
        
    }
    
    /**
     * Metodo constructor que tiene
     * como argumentno la ruta hacia
     * el archivo de imagen que desea
     * cargar
     * 
     * @param rutaImagen 
     */
    public Imagen(String rutaImagen) {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            //abre el archivo para realizar la lectura
            archivoImagen = new File(rutaImagen);
            fr = new FileReader(archivoImagen);
            br = new BufferedReader(fr);
            //realiza la lectura del archivo
            formato = br.readLine();
            String data = "";
            while((data = br.readLine()) != null) {
                if(data.length() > 0 && data.charAt(0) != '#')
                    System.out.println(""+data);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Imagen::Error: El archivo de imagen no existe o no pudo ser abierto");
        } catch (IOException ex) {
            System.err.println("Imagen::Error: Error durante la lectura del archivo de imagen");
        } finally {
            //cierra la lectura del archivo ocurra o no una excepcion
            if(fr != null) {
                try {
                    fr.close();
                } catch (IOException ex) {
                    System.err.println("Imagen::Error: No fue posible cerrar el archivo de imagen");
                }
            }
        }
    }
}
