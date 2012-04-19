/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagen;

/**
 * 
 * @author juansrs
 */


public class Ecualizacion {
    
    // Atributos de clase
    Imagen objImagen;
    
    public Ecualizacion(Imagen objImagen) {
        this.objImagen = objImagen;
    }
    
    public Imagen ecualizar() {
        if(objImagen.getFormato().equals("P2")) {
            return ecualizarGris();
        }
        if(objImagen.getFormato().equals("P3")) {
            return ecualizarRGB();
        }
        return null;
    }
    
    private Imagen ecualizarGris() {
        Imagen nuevaImagen = new Imagen();
        Histograma histograma = new Histograma(objImagen);
        //histograma de la imagen
        int histogramaGris[] = histograma.getHistogramaGris();
        int ancho = objImagen.getM();
        int alto = objImagen.getN();
        int L = objImagen.getNivelIntensidad();
        //histograma normalizado
        int histogramaNormal[] = new int[L+1];
        //calcula el nuevo histograma
        for(int i = 1; i < L+1; i++) {
            histogramaNormal[i] = histogramaNormal[i-1]+histogramaGris[i];
        }
        //crea vector LUT
        int LUT[] = new int[L+1];
        for(int i = 0; i < L; i++) {
            LUT[i] = (int)Math.floor(((L-1)*histogramaNormal[i])/(ancho*alto));
        }
        //crea la nueva imagen
        nuevaImagen.setN(alto);
        nuevaImagen.setM(ancho);
        nuevaImagen.setNivelIntensidad(L);
        nuevaImagen.setFormato(objImagen.getFormato());
        //matriz escala de grises
        //short matrizGris[][] = objImagen.getMatrizGris();
        short matrizGris[][] = new short[alto][ancho];
        for(int i = 0; i < alto; i++) {
            for(int j = 0; j < ancho; j++) {
                matrizGris[i][j] = (short)LUT[objImagen.getMatrizGris()[i][j]];
            }
        }
        //asigna la nueva matriz
        nuevaImagen.setMatrizGris(matrizGris);
        return nuevaImagen;
    }
    
    private Imagen ecualizarRGB() {
        Imagen nuevaImagen = new Imagen();
        Histograma histograma = new Histograma(objImagen);
        //histograma de la imagen por cada canal
        int histogramaR[] = histograma.getHistogramaR();
        int histogramaG[] = histograma.getHistogramaG();
        int histogramaB[] = histograma.getHistogramaB();
        int ancho = objImagen.getM();
        int alto = objImagen.getN();
        int L = objImagen.getNivelIntensidad();
        //histograma normalizado por cada canal
        int histogramaNormalR[] = new int[L+1];
        int histogramaNormalG[] = new int[L+1];
        int histogramaNormalB[] = new int[L+1];
        //calcula el nuevo histograma por cada canal
        for(int i = 1; i < L+1; i++) {
            histogramaNormalR[i] = histogramaNormalR[i-1]+histogramaR[i];
            histogramaNormalG[i] = histogramaNormalG[i-1]+histogramaG[i];
            histogramaNormalB[i] = histogramaNormalB[i-1]+histogramaB[i];
        }
        //crea vector LUT por cada canal
        int LUTR[] = new int[L+1];
        int LUTG[] = new int[L+1];
        int LUTB[] = new int[L+1];
        for(int i = 0; i < L; i++) {
            LUTR[i] = (int)Math.floor(((L-1)*histogramaNormalR[i])/(ancho*alto));
            LUTG[i] = (int)Math.floor(((L-1)*histogramaNormalG[i])/(ancho*alto));
            LUTB[i] = (int)Math.floor(((L-1)*histogramaNormalB[i])/(ancho*alto));
        }
        //crea la nueva imagen
        nuevaImagen.setN(alto);
        nuevaImagen.setM(ancho);
        nuevaImagen.setNivelIntensidad(L);
        nuevaImagen.setFormato(objImagen.getFormato());
        //matriz RGB
        short matrizR[][] = new short[alto][ancho];
        short matrizG[][] = new short[alto][ancho];
        short matrizB[][] = new short[alto][ancho];
        for(int i = 0; i < alto; i++) {
            for(int j = 0; j < ancho; j++) {
                matrizR[i][j] = (short)LUTR[objImagen.getMatrizR()[i][j]];
                matrizG[i][j] = (short)LUTG[objImagen.getMatrizG()[i][j]];
                matrizB[i][j] = (short)LUTB[objImagen.getMatrizB()[i][j]];
            }
        }
        //asigna la nueva matriz RGB
        nuevaImagen.setMatrizR(matrizR);
        nuevaImagen.setMatrizG(matrizG);
        nuevaImagen.setMatrizB(matrizB);
        return nuevaImagen;
    }
}
