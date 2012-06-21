/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagen;

/**
 * Clase que permite la correccion del contraste
 * de una imagen.
 * 
 * @author sebaxtian
 */


public class Contraste {
    
    
    // Atributos de clase
    private double contraste;
    private int nivelMin;
    private int nivelMax;
    
    public Contraste() {
        contraste = 0;
        nivelMin = 0;
        nivelMax = 0;
    }
    
    public double getContraste(Imagen objImagen) {
        Histograma histograma = new Histograma(objImagen);
        int[] niveles = histograma.getHistogramaGris();
        // nivelMax y nivelMin
        nivelMin = 0;
        nivelMax = niveles.length - 1;
        
        // busca el primer valor diferente a cero de izquierda a derecha
        while(niveles[nivelMin] != 0) {
            nivelMin++;
        }
        
        // busca el primer valor diferente a cero de derecha a izquierda
        while(niveles[nivelMax] != 0) {
            nivelMax--;
        }
        
        // calcula el contraste
        contraste = (nivelMax - nivelMin) / (nivelMax + nivelMin);
        
        return contraste;
    }
    
    
    public Imagen correccionGamma(Imagen objImagen, double r) {
        Imagen objSalida = new Imagen();
        
        short[][] matrizSalida = new short[objImagen.getN()][objImagen.getM()];
        short[][] matrizEntrada = objImagen.getMatrizGris();
        
        if(r == 1) {
            return objImagen;
        }
        
        objSalida.setFormato("P2");
        objSalida.setN(objImagen.getN());
        objSalida.setM(objImagen.getM());
        objSalida.setNivelIntensidad(objImagen.getNivelIntensidad());
        
        for(int i = 0; i < matrizEntrada.length; i++) {
            for(int j = 0; j < matrizEntrada[0].length; j++) {
                matrizSalida[i][j] = (short)Math.pow(matrizEntrada[i][j], r);
            }
        }
        
        objSalida.setMatrizGris(matrizSalida);
        
        return objSalida;
    }
    
    public Imagen contrastStretching(Imagen objImagen) {
        Imagen objSalida = new Imagen();
        
        short[][] matrizSalida = new short[objImagen.getN()][objImagen.getM()];
        short[][] matrizEntrada = objImagen.getMatrizGris();
        
        contraste = getContraste(objImagen);
        
        objSalida.setFormato("P2");
        objSalida.setN(objImagen.getN());
        objSalida.setM(objImagen.getM());
        objSalida.setNivelIntensidad(objImagen.getNivelIntensidad());
        
        int nivelIntensidad = objImagen.getNivelIntensidad();
        
        for(int i = 0; i < matrizEntrada.length; i++) {
            for(int j = 0; j < matrizEntrada[0].length; j++) {
                matrizSalida[i][j] = (short)((matrizEntrada[i][j] - nivelMin) * nivelIntensidad / (nivelMax - nivelMin));
            }
        }
        
        objSalida.setMatrizGris(matrizSalida);
        
        return objSalida;
    }
}
