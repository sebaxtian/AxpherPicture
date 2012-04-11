/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagen;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author jjcarmu
 */
public class FiltroNoise {

    //private String formato;
    private Imagen imagen;
    private short[][] matrizGris = null;
    //para imagenes tipo P3
    private short matrizR[][] = null;
    private short matrizG[][] = null;
    private short matrizB[][] = null;
    //private short matrizRoriginal[][] = null;
    //private short matrizGoriginal[][] = null;
    //private short matrizBoriginal[][] = null;
    
    /**
     * constructor de la clase Filtro que resibe como argumento una imagen
     * @param imagen : elemento tipo imagen (formato P2 o P3)
     */
    public FiltroNoise(Imagen imagen){
        this.imagen = imagen;  
    }
    
    /**
     * Metodo que se encarga de filtrar la imagen mediante el metodo de la mediana
     * @param tamanoMascara : parametro que determina el tamaño de la mascara, 
     * este debe ser impar y mayor a 1 ejem: 1,3,5.. 
     * (representa el tamaño de las filas y las columnas de la matriz).
     */
    public void filtroMediana(int tamanoMascara){
        
        
        if(tamanoMascara%2 == 0 || tamanoMascara==1){
            JOptionPane.showMessageDialog(null,"Favor ingresar un numero IMPAR o mayor a 1");
        } else {

            //mascara seleccionada para realizar el filtro
            short[][] mascara = new short[tamanoMascara][tamanoMascara];
            //arreglo con los pixeles tomados de la mascara ordenados
            short[] mascaraOrdena = new short[tamanoMascara * tamanoMascara];
//            ArrayList mascaraOrdena = new ArrayList();
            //mascaraOrdena;
              
//            int numerosEnteros[] = { 2, 1, 6, 4, 5, 3 };
//            
//            JOptionPane.showMessageDialog(null, "JHON");
//            for(int i = 0; i < numerosEnteros.length; i++)
//                System.out.print(numerosEnteros[i]+" ");
//            
//            Arrays.sort(numerosEnteros);
//            System.out.println();
//            for(int i = 0; i < numerosEnteros.length; i++)
//                System.out.println(numerosEnteros[i]+" ");
            
            
            if (this.imagen.getFormato().equals("P2")) {
                this.matrizGris = this.imagen.getMatrizGris();
                int tope = tamanoMascara/2; //variable que sirve de control para evitar que se desborde la mascara de la matriz
                //JOptionPane.showMessageDialog(null, "tope: "+tope);
                
                for (int i = tope; i < this.imagen.getMatrizGris().length-tope; i++) {
                    for (int j = tope; j < this.imagen.getMatrizGris()[0].length-tope; j++) {
                        //JOptionPane.showMessageDialog(null, "iteracion: i="+i+ " j="+j);
                        //llenado de la mascara
                        for(int y = 0; y < mascara.length; y++){
                            for(int x = 0; x < mascara[0].length; x++){
                                mascara[y][x] = this.imagen.getMatrizGris()[i-tope+y][j-tope+x];
                            }
                        }
                        
                        //llenado del arreglo mascaraOrdenada apartir de la mascara
                        int posicion=0;
                        for(int y = 0; y < mascara.length; y++){
                            for(int x = 0; x < mascara[0].length; x++){
                                System.out.print(mascara[y][x]+" ");
                                mascaraOrdena[posicion] = mascara[y][x];
                                posicion++;
                            }
                            System.out.println();
                        }
                        
                        System.out.println("********************************");
                        
                        for(int k=0; k<mascaraOrdena.length;k++){
                            System.out.print(mascaraOrdena[k]+" ");
                        }
                        
                        System.out.println();
                        Arrays.sort(mascaraOrdena);
                        for(int k=0; k<mascaraOrdena.length;k++){
                            System.out.println(mascaraOrdena[k]);
                        }
                        
                        this.matrizGris[i][j] = mascaraOrdena[(int)Math.ceil(mascaraOrdena.length/2)];
                        //***********************************************             
                    }

                }

            } else if (this.imagen.getFormato().equals("P3")) {
                
                
                
            } else {
                System.out.println("(clase Filtro) Que formato sera " + this.imagen.getFormato());
            }


        }
    
    }
    
    public static void main(String [] arg){
        
        short [][] gris ={{1,2,3,4,5,6,7,8,9,10},
            {11,12,13,14,15,16,17,18,19,20},
            {21,22,23,24,25,26,27,28,29,30},
            {31,32,33,34,35,36,37,38,39,40},
            {41,42,43,44,45,46,47,48,49,50},
            {11,12,13,14,15,16,17,18,19,20},
            {31,32,33,34,35,36,37,38,39,40}}; 
        
        Imagen im = new Imagen();
        im.setMatrizGris(gris);
        im.setFormato("P2");
        
        FiltroNoise fl = new FiltroNoise(im);
        fl.filtroMediana(3);
        
        for(int i=0; i<fl.matrizGris.length;i++){
            for(int j=0; j<fl.matrizGris[0].length;j++){
                System.out.print(fl.matrizGris[i][j]+" ");
            }  
            System.out.println();
        }
    }
    
    
}
