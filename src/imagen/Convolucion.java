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
   //private short [][] matrizMagnitud;  
   //private short [][] matrizDireccion;
    
   public Convolucion() {
        
    }
    
    /**
     * 
     * @param imagen objeto tipo Imagen la cual contiene la imagen original que se desea procesar
     * @param kernel matriz parametro de entreda de tipo short el cual contiene la mascara con la
     * cual se realiza la convolucion sobre cada pixel de entrada
     * @return un objeto tipo Imagen al cual fue recorrido por todos sus pixeles y se aplica sobre estos
     * la convolucion con el kernel que recibe como parametro de entrada
     */
    public Imagen calculoConvolucion(Imagen imagen, short [][] kernel){
        this.objImagen = imagen;
        //this.matrizDireccion = imagen.getMatrizGris();
        //this.matrizMagnitud = imagen.getMatrizGris();
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
