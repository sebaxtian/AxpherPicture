/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagen;

/**
 * Definicion de la clase Histograma
 * Permite el calculo de niveles de
 * intensidad por pixel para una imagen
 * Los valores de niveles de intensidad
 * por pixel son almacenados en un vector.
 * 
 * @author Juan Sebastian Rios Sabogal
 * @Fecha lun abr  2 12:31:13 COT 2012
 * @version 0.1
 */


public class Histograma {
    
    //Atributos de clase
    private Imagen imagen;
    private String formato;
    private int maxIntensidad;
    private int histogramaGris[];
    private int histogramaR[];
    private int histogramaG[];
    private int histogramaB[];
    private int maxNumPixelesGris;
    private int nivelDominanteGris;
    private int maxNumPixelesR;
    private int nivelDominanteR;
    private int maxNumPixelesG;
    private int nivelDominanteG;
    private int maxNumPixelesB;
    private int nivelDominanteB;
    
    
    public Histograma(Imagen imagen) {
        this.imagen = imagen;
        formato = imagen.getFormato();
        maxIntensidad = imagen.getNivelIntensidad();
        //calcula histograma segun la imagen
        if(formato.equals("P2")) {
            calcularHistogramaGris();
        }
        if(formato.equals("P3")) {
            calcularHistogramaRGB();
        }
    }
    
    /**
     * Metodo que se encarga de contruir el
     * arreglo de histograma para la imagen
     * en el caso de que esta sea a escala
     * de grises.
     */
    private void calcularHistogramaGris() {
        histogramaGris = new int[imagen.getNivelIntensidad()+1];
        //realiza el conteo de numero de pixeles por nivel de intensidad
        for(int i = 0; i < imagen.getN(); i++) {
            for(int j = 0; j < imagen.getM(); j++) {
                int nivelGris = imagen.getMatrizGris()[i][j];
                if(nivelGris < 256)
                    histogramaGris[nivelGris]++;
            }
        }
    }
    
    /**
     * Metodo que se encarga de contruir el
     * arreglo de histograma para la imagen
     * en el caso de que esta sea RGB
     * construye un arreglo por cada canal.
     */
    private void calcularHistogramaRGB() {
        histogramaR = new int[imagen.getNivelIntensidad()+1];
        histogramaG = new int[imagen.getNivelIntensidad()+1];
        histogramaB = new int[imagen.getNivelIntensidad()+1];
        //realiza el conteo de numero de pixeles por nivel de intensidad para cada canal RGB
        for(int i = 0; i < imagen.getN(); i++) {
            for(int j = 0; j < imagen.getM(); j++) {
                //canal R
                int nivelR = imagen.getMatrizR()[i][j];
                if(nivelR < 256)
                    histogramaR[nivelR]++;
                //canal G
                int nivelG = imagen.getMatrizG()[i][j];
                if(nivelG < 256)
                    histogramaG[nivelG]++;
                //canal B
                int nivelB = imagen.getMatrizB()[i][j];
                if(nivelB < 256)
                    histogramaB[nivelB]++;
            }
        }
    }
    
    /**
     * Construye una imagen con la representacion
     * del histograma para el histograma a escala
     * de grises.
     * 
     * @return imagen
     */
    private Imagen getImagenHistogramaGris() {
        Imagen imagenHistograma = new Imagen();
        imagenHistograma.setFormato("P2");
        imagenHistograma.setM((short)256);
        imagenHistograma.setN((short)270);
        imagenHistograma.setNivelIntensidad((short)255);
        //busca el maximo numero de pixeles para el nivel de intensidad dominante
        nivelDominanteGris = 0;
        maxNumPixelesGris = 0;
        for(int i = 0; i < histogramaGris.length; i++) {
            if(histogramaGris[i] > getMaxNumPixelesGris()) {
                maxNumPixelesGris = histogramaGris[i];
                nivelDominanteGris = i;
            }
        }
        //matriz para dibujar el histograma
        short matrizGris[][] = new short[imagenHistograma.getN()][imagenHistograma.getM()];
        //todos los elementos de la matriz estan en blanco
        for(int i = 0; i < imagenHistograma.getN(); i++) {
            for(int j = 0; j < imagenHistograma.getM(); j++) {
                matrizGris[i][j] = 255;
            }
        }
        //crea la matriz con los datos del histograma
        for(int j = 0; j < histogramaGris.length; j++) {
            int numPixeles = histogramaGris[j];
            numPixeles = (255 * numPixeles) / getMaxNumPixelesGris();
            for(int n = 0; n < numPixeles; n++) {
                int i = 255 - n;
                matrizGris[i][j] = 0;
            }
        }
        for(int i = 260; i < 270; i++) {
            for(int j = 0; j < imagenHistograma.getM(); j++) {
                matrizGris[i][j] = (short)j;
            }
        }
        //asigna la matriz del histograma a la imagen del histograma
        imagenHistograma.setMatrizGris(matrizGris);
        System.out.println("Histograma::Formato: "+imagenHistograma.getFormato()+" N: "+imagenHistograma.getN()+" M: "+imagenHistograma.getM()+" MaxNumPixeles: "+getMaxNumPixelesGris()+" NivelDominante: "+getNivelDominanteGris());
        return imagenHistograma;
    }
    
    /**
     * Construye una imagen con la representacion
     * del histograma para el histograma RGB
     * 
     * @return imagen
     */
    private Imagen getImagenHistogramaRGB() {
        Imagen imagenHistograma = new Imagen();
        imagenHistograma.setFormato("P3");
        imagenHistograma.setM((short)768);
        imagenHistograma.setN((short)270);
        imagenHistograma.setNivelIntensidad((short)255);
        //busca el maximo numero de pixeles para el nivel de intensidad dominante por cada canal
        nivelDominanteR = 0;
        maxNumPixelesR = 0;
        nivelDominanteG = 0;
        maxNumPixelesG = 0;
        nivelDominanteB = 0;
        maxNumPixelesB = 0;
        for(int i = 0; i < histogramaR.length; i++) {//los histogramas RGB tienen la misma dimension
            //canal R
            if(histogramaR[i] > getMaxNumPixelesR()) {
                maxNumPixelesR = histogramaR[i];
                nivelDominanteR = i;
            }
            //canal G
            if(histogramaG[i] > getMaxNumPixelesG()) {
                maxNumPixelesG = histogramaG[i];
                nivelDominanteG = i;
            }
            //canal B
            if(histogramaB[i] > getMaxNumPixelesB()) {
                maxNumPixelesB = histogramaB[i];
                nivelDominanteB = i;
            }
        }
        //matriz para dibujar el histograma por cada canal RGB
        short matrizR[][] = new short[imagenHistograma.getN()][imagenHistograma.getM()];
        short matrizG[][] = new short[imagenHistograma.getN()][imagenHistograma.getM()];
        short matrizB[][] = new short[imagenHistograma.getN()][imagenHistograma.getM()];
        //todos los elementos de la matriz RGB estan en blanco
        for(int i = 0; i < imagenHistograma.getN(); i++) {
            for(int j = 0; j < imagenHistograma.getM(); j++) {
                matrizR[i][j] = 255;
                matrizG[i][j] = 255;
                matrizB[i][j] = 255;
            }
        }
        //crea la matriz RGB con los datos del histograma
        //canal R
        for(int j = 0; j < histogramaR.length; j++) {
            int numPixeles = histogramaR[j];
            numPixeles = (255 * numPixeles) / getMaxNumPixelesR();
            for(int n = 0; n < numPixeles; n++) {
                int i = 255 - n;
                matrizG[i][j] = 0;
                matrizB[i][j] = 0;
            }
        }
        for(int i = 260; i < 270; i++) {
            for(int j = 0; j < 255; j++) {
                matrizR[i][j] = (short)j;
                matrizG[i][j] = 0;
                matrizB[i][j] = 0;
            }
        }
        //cana G
        for(int n = 0; n < histogramaG.length; n++) {
            int numPixeles = histogramaG[n];
            numPixeles = (255 * numPixeles) / getMaxNumPixelesG();
            for(int i = 0; i < numPixeles; i++) {
                for(int j = 256; j < 510; j++) {
                    int k = 255 - i;
                    matrizR[k][n+256] = 0;
                    matrizB[k][n+256] = 0;
                }
            }
        }
        for(int i = 260; i < 270; i++) {
            int nivel = 0;
            for(int j = 256; j < 510; j++) {
                matrizG[i][j] = (short)nivel;
                matrizR[i][j] = 0;
                matrizB[i][j] = 0;
                nivel++;
            }
        }
        //canal B
        for(int n = 0; n < histogramaB.length; n++) {
            int numPixeles = histogramaB[n];
            numPixeles = (255 * numPixeles) / getMaxNumPixelesB();
            for(int i = 0; i < numPixeles; i++) {
                for(int j = 256; j < 510; j++) {
                    int k = 255 - i;
                    matrizR[k][n+511] = 0;
                    matrizG[k][n+511] = 0;
                }
            }
        }
        for(int i = 260; i < 270; i++) {
            int nivel = 0;
            for(int j = 511; j < 768; j++) {
                matrizB[i][j] = (short)nivel;
                matrizR[i][j] = 0;
                matrizG[i][j] = 0;
                nivel++;
            }
        }
        //asigna la matriz RGB del histograma a la imagen del histograma
        imagenHistograma.setMatrizR(matrizR);
        imagenHistograma.setMatrizG(matrizG);
        imagenHistograma.setMatrizB(matrizB);
        System.out.println("Histograma::Formato: "+imagenHistograma.getFormato()+" N: "+imagenHistograma.getN()+" M: "+imagenHistograma.getM()+" MaxNumPixelesR: "+getMaxNumPixelesR()+" NivelDominanteR: "+getNivelDominanteR()
                +" MaxNumPixelesG: "+getMaxNumPixelesG()+" NivelDominanteG: "+getNivelDominanteG()+" MaxNumPixelesB: "+getMaxNumPixelesB()+" NivelDominanteB: "+getNivelDominanteB());
        return imagenHistograma;
    }
    
    /**
     * Metodo que permite obtener una imagen
     * del histograma calculado.
     * 
     * @return imagen
     */
    public Imagen getImagenHistograma() {
        //retorna una imagen del histograma segun el formato
        if(imagen.getFormato().equals("P2")) {
            return getImagenHistogramaGris();
        }
        if(imagen.getFormato().equals("P3")) {
            return getImagenHistogramaRGB();
        }
        return null;
    }
    
    /**
     * @return the histogramaGris
     */
    public int[] getHistogramaGris() {
        return histogramaGris;
    }
    
    /**
     * @return the histogramaR
     */
    public int[] getHistogramaR() {
        return histogramaR;
    }
    
    /**
     * @return the histogramaG
     */
    public int[] getHistogramaG() {
        return histogramaG;
    }
    
    /**
     * @return the histogramaB
     */
    public int[] getHistogramaB() {
        return histogramaB;
    }

    /**
     * @return the maxNumPixelesGris
     */
    public int getMaxNumPixelesGris() {
        return maxNumPixelesGris;
    }

    /**
     * @return the nivelDominanteGris
     */
    public int getNivelDominanteGris() {
        return nivelDominanteGris;
    }

    /**
     * @return the maxNumPixelesR
     */
    public int getMaxNumPixelesR() {
        return maxNumPixelesR;
    }

    /**
     * @return the nivelDominanteR
     */
    public int getNivelDominanteR() {
        return nivelDominanteR;
    }

    /**
     * @return the maxNumPixelesG
     */
    public int getMaxNumPixelesG() {
        return maxNumPixelesG;
    }

    /**
     * @return the nivelDominanteG
     */
    public int getNivelDominanteG() {
        return nivelDominanteG;
    }

    /**
     * @return the maxNumPixelesB
     */
    public int getMaxNumPixelesB() {
        return maxNumPixelesB;
    }

    /**
     * @return the nivelDominanteB
     */
    public int getNivelDominanteB() {
        return nivelDominanteB;
    }
    
    /**
     * @return the formato
     */
    public String getFormato() {
        return formato;
    }
    
    /**
     * @return the maxIntensidad
     */
    public int getMaxIntensidad() {
        return maxIntensidad;
    }
}
