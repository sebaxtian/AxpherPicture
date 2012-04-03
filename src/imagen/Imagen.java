/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagen;

import java.io.*;
import java.util.Date;


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
 * @author Juan Sebastian Rios Sabogal
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
        BufferedReader br;
        try {
            //abre el archivo para realizar la lectura
            archivoImagen = new File(rutaImagen);
            fr = new FileReader(archivoImagen);
            br = new BufferedReader(fr);
            //realiza la lectura del archivo
            //lee el formato de la imagen
            formato = br.readLine();
            String data = br.readLine();
            while(data.charAt(0) == '#') {
                data = br.readLine();
            }
            //lee el numero de columnas y filas
            m = Short.parseShort(data.split("\\s")[0]);
            n = Short.parseShort(data.split("\\s")[1]);
            //lee el nivel maximo de intensidad
            data = br.readLine();
            nivelIntensidad = Short.parseShort(data);
            System.out.println("Imagen::Formato = "+formato);
            System.out.println("Imagen::N = "+n);
            System.out.println("Imagen::M = "+m);
            System.out.println("Imagen::NivelIntensidad = "+nivelIntensidad);
            //carga la matriz o matrices del formato correspondiente
            System.out.println("Imagen::Inicia carga ...");
            //si el formato de la imagen es PGM
            if(formato.equals("P2")) {
                cargarImagenPGM(br);
            }
            //si el formato de la imagen es PPM
            if(formato.equals("P3")) {
                cargarImagenPPM(br);
            }
            System.out.println("Imagen::Termina carga");
        } catch (FileNotFoundException ex) {
            System.err.println("Imagen::Error: El archivo de imagen no existe o no pudo ser abierto");
        } catch (IOException ex) {
            System.err.println("Imagen::Error: Durante la lectura del archivo de imagen");
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
    
    /**
     * Metodo que realiza la lectura del contenido
     * de la matriz del archivo de imagen PGM
     * Construye una matriz que representa los
     * pixeles en niveles de gris
     * 
     * @param br
     * @throws IOException 
     */
    private void cargarImagenPGM(BufferedReader br) throws IOException {
        setMatrizGris(new short[getN()][getM()]);
        int fila = 0;
        int columna = 0;
        String data;
        while((data = br.readLine()) != null) {
            //almacena todo el conetido de la matriz
            String splitdata[] = data.split("\\s");
            for (String pixel : splitdata) {
                if(!pixel.equals("")){
                    getMatrizGris()[fila][columna] = Short.parseShort(pixel);
                    if(columna == getM()-1) {
                        columna = -1;
                        if(fila == getN()-1) {
                            fila = -1;
                        }
                        fila++;
                    }
                    columna++;
                }
            }
        }
    }
    
    /**
     * Metodo que realiza la lectura del contenido
     * de la matriz del archivo de imagen PPM
     * Construye una matriz por cada canal RGB
     * que representa los pixeles por nivel de color
     * 
     * @param br
     * @throws IOException 
     */
    private void cargarImagenPPM(BufferedReader br) throws IOException {
        setMatrizR(new short[getN()][getM()]);
        setMatrizG(new short[getN()][getM()]);
        setMatrizB(new short[getN()][getM()]);
        int key = 0;
        int fila = 0;
        int columna = 0;
        String data;
        String rgb[] = new String[3];
        while((data = br.readLine()) != null) {
            //almacena todo el conetido de la matriz por cada canal
            String splitdata[] = data.split("\\s");
            for (String pixel : splitdata) {
                if(!pixel.equals("")){
                    rgb[key] = pixel;
                    key++;
                    if(key == 3) {
                        getMatrizR()[fila][columna] = Short.parseShort(rgb[0]);
                        getMatrizG()[fila][columna] = Short.parseShort(rgb[1]);
                        getMatrizB()[fila][columna] = Short.parseShort(rgb[2]);
                        if(columna == getM()-1) {
                            columna = -1;
                            if(fila == getN()-1) {
                                fila = -1;
                            }
                            fila++;
                        }
                        columna++;
                        key = 0;
                    }
                }
            }
        }
    }
    
    /**
     * Metodo que permite guardar la imagen
     * en un archivo, como atributo recibe
     * la ruta hacia el nuevo archivo de imagen.
     * 
     * @param rutaArchivo 
     */
    public void guardarImagen(String rutaArchivo) {
        FileWriter fw = null;
        PrintWriter pw;
        try {
            fw = new FileWriter(rutaArchivo);
            pw = new PrintWriter(fw);
            String comentario = "# Imagen guardada desde programa AxpherPicture\n";
            comentario += "# Autor: Juan Sebastian Rios Sabogal\n";
            comentario += "# Email: sebaxtianrios@gmail.com\n";
            comentario += "# Fecha: " + new Date() + "\n";
            comentario += "# Version: 0.1\n";
            comentario += "# \n";
            //escribe los atributos de la imagen
            pw.write(getFormato()+"\n");
            pw.write(comentario);
            pw.write(getM()+" "+getN()+"\n");
            pw.write(getNivelIntensidad()+"\n");
            //escribe el contenido de la matriz o matrices en el archivo segun el formato
            System.out.println("Imagen::Inicia escritura ...");
            //si el formato de imagen es PGM
            if(getFormato().equals("P2")){
                for(int i = 0; i < getN(); i++) {
                    for(int j = 0; j < getM(); j++) {
                        pw.write(getMatrizGris()[i][j]+" ");
                    }
                    pw.write("\n");
                }
            }
            //si el formato de imagen es PPM
            if(getFormato().equals("P3")) {
                for(int i = 0; i < getN(); i++) {
                    for(int j = 0; j < getM(); j++) {
                        pw.write(getMatrizR()[i][j]+" ");
                        pw.write(getMatrizG()[i][j]+" ");
                        pw.write(getMatrizB()[i][j]+" ");
                    }
                    pw.write("\n");
                }
            }
            System.out.println("Imagen::Termina escritura");
        } catch (IOException ex) {
            System.err.println("Imagen::Error: Durante la escritura del archivo de imagen");
        } finally {
            //cierra la lectura del archivo ocurra o no una excepcion
            if(fw != null) {
                try {
                    fw.close();
                } catch (IOException ex) {
                    System.err.println("Imagen::Error: No fue posible cerrar el archivo de imagen");
                }
            }
        }
    }
    
    /**
     * Metodo que permite obtener una imagen
     * a escala de grises del objeto Imagen.
     * 
     * @return imagen
     */
    public Imagen getEscalaGrises() {
        Imagen imgGrises = new Imagen();
        if(formato.equals("P3")){
            imgGrises.setFormato("P2");
            imgGrises.setN(n);
            imgGrises.setM(m);
            imgGrises.setNivelIntensidad(nivelIntensidad);
            short matrizGrises[][] = new short[n][m];
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < m; j++) {
                    short pixelR = matrizR[i][j];
                    short pixelG = matrizG[i][j];
                    short pixelB = matrizB[i][j];
                    short pixelGris = (short)Math.ceil((Alfa * pixelR) + (Beta * pixelG) + (Gama * pixelB));
                    matrizGrises[i][j] = pixelGris;
                }
            }
            imgGrises.setMatrizGris(matrizGrises);
        } else {
            imgGrises = this;
        }
        return imgGrises;
    }

    /**
     * @return the formato
     */
    public String getFormato() {
        return formato;
    }

    /**
     * @param formato the formato to set
     */
    public void setFormato(String formato) {
        this.formato = formato;
    }

    /**
     * @return the n
     */
    public short getN() {
        return n;
    }

    /**
     * @param n the n to set
     */
    public void setN(short n) {
        this.n = n;
    }

    /**
     * @return the m
     */
    public short getM() {
        return m;
    }

    /**
     * @param m the m to set
     */
    public void setM(short m) {
        this.m = m;
    }

    /**
     * @return the nivelIntensidad
     */
    public short getNivelIntensidad() {
        return nivelIntensidad;
    }

    /**
     * @param nivelIntensidad the nivelIntensidad to set
     */
    public void setNivelIntensidad(short nivelIntensidad) {
        this.nivelIntensidad = nivelIntensidad;
    }

    /**
     * @return the matrizGris
     */
    public short[][] getMatrizGris() {
        return matrizGris;
    }

    /**
     * @param matrizGris the matrizGris to set
     */
    public void setMatrizGris(short[][] matrizGris) {
        this.matrizGris = matrizGris;
    }

    /**
     * @return the matrizR
     */
    public short[][] getMatrizR() {
        return matrizR;
    }

    /**
     * @param matrizR the matrizR to set
     */
    public void setMatrizR(short[][] matrizR) {
        this.matrizR = matrizR;
    }

    /**
     * @return the matrizG
     */
    public short[][] getMatrizG() {
        return matrizG;
    }

    /**
     * @param matrizG the matrizG to set
     */
    public void setMatrizG(short[][] matrizG) {
        this.matrizG = matrizG;
    }

    /**
     * @return the matrizB
     */
    public short[][] getMatrizB() {
        return matrizB;
    }

    /**
     * @param matrizB the matrizB to set
     */
    public void setMatrizB(short[][] matrizB) {
        this.matrizB = matrizB;
    }
}
