/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagen;

import javax.swing.JOptionPane;

/**
 *
 * @author jjcarmu
 */
public class Convolucion {
    
   private Imagen objImagen;
    
    public Convolucion() {
        
    }
    
    public Imagen calculoConvolucion(Imagen imagen, short [][] kernel){
        this.objImagen = imagen;
        short [][] matrizGris = imagen.getMatrizGris();
        //short [][] kernel = tamanoKernel][tamanoKernel];
            
        int tope = kernel.length/2;
        
        for(int i=tope; i < matrizGris.length-tope; i++){
            for(int j=tope; j< matrizGris[0].length-tope; j++){
               matrizGris[i][j] = convolucionar(imagen.getMatrizGris(), kernel, i, j);
               //System.out.print(matrizGris[i][j]+" ");
            }
            //System.out.println();
        }
          
        objImagen.setMatrizGris(matrizGris);
        return objImagen;
        
    }
    
    
    
    public short convolucionar(short [][] imagen, short [][] kernel, int fila, int columna){
    
        int tope = kernel.length/2; //variable que sirve de control para evitar que se desborde la mascara de la matriz
        short pixel =0;
        short factor =0;
        
        for(int i=0; i < kernel.length; i++){
            for(int j=0; j< kernel[0].length; j++){
                factor+= kernel[i][j];
                pixel +=  (short) (kernel[i][j]*imagen[fila-tope+i][columna-tope+j]);        
            }
        }
        
        if(factor>0){
            pixel /=factor;
        }
        
        return pixel;
    }
   
    

    /**
     * @return the objImagen
     */
    public Imagen getObjImagen() {
        return objImagen;
    }

    /**
     * @param objImagen the objImagen to set
     */
    public void setObjImagen(Imagen objImagen) {
        this.objImagen = objImagen;
    }


}
