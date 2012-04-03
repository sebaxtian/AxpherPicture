/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagen;

import javax.swing.JOptionPane;

    /**
 * Definicion de la clase Scalar
 * Permite la reducion o ampliacion
 * por factor (%) para una imagen
 * Los valores del tamaño de la imagen
 * por pixel son almacenados en una matriz.
 * 
 * @author Jhon Javier Cardona Muñoz
 * @Fecha lun abr  2 22:31:13 COT 2012
 * @version 0.1
 */

public class Scalar {

    private int [][] imagenEscalada;
    private int [][] imagenOriginal;
    private double factor = 1.0;
 
    /**
     * Metodo constructor por defecto 1,
     * Asigina el tamaño de los atributos
     * de la nueva imagen escalada y la 
     * imagen original, a la matriz de la
     * imagen escalada se llena con valores 0
     * @param imagen matriz de la imagen (tipo entero) que se quiere redimencionar
     * @param escalar factor de porcentaje (tipo double) que se desea escalar (0.0 - 1.0 --> 0 % - 100%, > 1.0 porcentaje de ampliacion)
     */
    
    public Scalar(int [][] imagen, double escalar){
        if(escalar<=0)
            JOptionPane.showMessageDialog(null, "El escalar debe ser mayor a cero");
        else{            
            imagenEscalada = new int [(int)Math.floor(escalar*imagen.length)][(int)Math.floor(escalar*imagen[0].length)];
            for (int i=0; i<imagenEscalada.length; i++){
                for(int j=0; j<imagenEscalada[0].length;j++)
                    imagenEscalada[i][j]=0;
            }
            this.imagenOriginal = imagen;
            this.factor = escalar;
        }
    }
    
        
     /**
     * Metodo constructor por defecto 2,
     * Asigina el tamaño de los atributos
     * de la nueva imagen escalada y la 
     * imagen original, a la matriz de la
     * imagen escalada se llena con valores 0
     * @param imagen matriz de la imagen original 
     * (tipo entero) que se quiere redimencionar
     * @param pixeles tamaño que tendra en pixeles
     * la nueva imagen escalada (tipo entero)
     * siendo pixeles el tamaño de cada lado (tamaño: pixeles*pixeles)
     */
    
    public Scalar(int [][] imagen, int pixeles){
        if(pixeles<=0)
            JOptionPane.showMessageDialog(null, "los pixeles deben ser mayores a cero");
        else{
            double escalar;     
            //se asume que la imagen original es de n x n
            if(imagen.length==imagen[0].length)
                escalar = imagen.length/pixeles;
            //en caso contrario se toma la fila o columna que sea contenga mas pixeles
            else{
                int mayorPixeles;
                if(imagen.length > imagen[0].length)
                    mayorPixeles = imagen.length;
                else
                    mayorPixeles = imagen[0].length;
                escalar = mayorPixeles/pixeles;
            }
                
            imagenEscalada = new int [(int)Math.floor(escalar*imagen.length)][(int)Math.floor(escalar*imagen[0].length)];
            for (int i=0; i<imagenEscalada.length; i++){
                for(int j=0; j<imagenEscalada[0].length;j++)
                    imagenEscalada[i][j]=0;
            }
            this.imagenOriginal = imagen;
            this.factor = escalar;
        }
    }
    
    public static void escalarM(int [][] entrada, double escalar){
        //el escalar debe ser mayor que 0
        int [][] salida = null;
        if(escalar>0){
            salida = new int [(int)Math.floor(escalar*entrada.length)][(int)Math.floor(escalar*entrada[0].length)];
            for (int i=0; i<salida.length; i++){
                for(int j=0; j<salida[0].length;j++)
                    salida[i][j]=0;
            }
            
            for (int i=0; i<entrada.length; i++){
                for(int j=0; j<entrada[0].length;j++)
                    salida[(int)Math.floor(i*escalar)][(int)Math.floor(j*escalar)]=entrada[i][j];
            }

            imprimirM(salida);
            System.out.println();
            for (int i=0; i<salida.length; i++){
                for(int j=0; j<salida[0].length;j++)
                    if(salida[i][j]==0){
                        //JOptionPane.showConfirmDialog(null, "Revisar " +i+" "+j);
                        interpolar(salida,i,j);
                    }
            }

        }else{
            JOptionPane.showMessageDialog(null, "El escalar es menor a cero");
        }

        imprimirM(salida);
        System.out.println();
        
        for (int i = 0; i < salida.length; i++) {
            for (int j = 0; j < salida[0].length; j++) {
                if (i>0&&i<salida.length-1 && j>0 && j<salida[0].length-1 && salida[i][j]==0){
                    //JOptionPane.showConfirmDialog(null, "Revisar " +i+" "+j);
                   interpolacionInterna(salida, i, j);
                }
            }
        }


        imprimirM(salida);
        System.out.println();


        for (int i = 0; i < salida.length; i++) {
            for (int j = 0; j < salida[0].length; j++) {
                if (i>0&&i<salida.length-1 && j>0 && j<salida[0].length-1 && salida[i][j]==0){
                    //JOptionPane.showConfirmDialog(null, "Revisar " +i+" "+j);
                   interpolacionInterna(salida, i, j);
                }
            }
        }


        imprimirM(salida);
        System.out.println();

        
    }

    public static int interpolacionInterna(int [][] entrada, int y, int x){
        int suma = (entrada[y][x-1] + entrada[y-1][x-1] + entrada[y-1][x] +entrada[y-1][x+1] + entrada[y][x+1] + entrada[y][x+1] + entrada[y][x+1] + entrada[y][x+1])/8;    
        entrada[y][x] = (int)suma;  
        return 0;
    }
    
    public static int interpolar(int [][] entrada, int y, int x){
        if(x==0){
            if(y==0){
                int suma = (entrada[y+1][x] + entrada[y+1][x+1] + entrada[y][x+1])/3;
                entrada[y][x] = (int)suma;
            }else if(y==entrada.length-1){
                int suma = (entrada[y-1][x] + entrada[y-1][x+1] + entrada[y][x+1])/3;
                entrada[y][x] = (int)suma;
            }else {
                int suma = (entrada[y-1][x] + entrada[y-1][x+1] + entrada[y][x+1] +entrada[y+1][x+1] + entrada[y+1][x])/5;
                entrada[y][x] = (int)suma;
            }
        }
        else if(x == entrada[0].length - 1){
            if(y==0){
                int suma = (entrada[y][x-1] + entrada[y+1][x-1] + entrada[y+1][x])/3;
                entrada[y][x] = (int)suma;
            }else if(y==entrada.length-1){
                int suma = (entrada[y][x-1] + entrada[y-1][x-1] + entrada[y-1][x])/3;
                entrada[y][x] = (int)suma;
            }else{
                int suma = (entrada[y-1][x] + entrada[y-1][x-1] + entrada[y][x-1] +entrada[y+1][x-1] + entrada[y+1][x])/5;
                entrada[y][x] = (int)suma;
            }
        }
        else if(y == 0){
            if(x==0){
                int suma = (entrada[y+1][x] + entrada[y+1][x+1] + entrada[y][x+1])/3;
                entrada[y][x] = (int)suma;
            }
            else if(x == entrada[0].length - 1){
                int suma = (entrada[y][x-1] + entrada[y+1][x-1] + entrada[y+1][x])/3;
                entrada[y][x] = (int)suma;
            }else{
                int suma = (entrada[y][x-1] + entrada[y+1][x-1] + entrada[y][x+1] +entrada[y+1][x+1] + entrada[y][x+1])/5;
                entrada[y][x] = (int)suma;    
            }
        }
        else if(y == entrada.length - 1){
            if(x==0){
                int suma = (entrada[y-1][x] + entrada[y-1][x+1] + entrada[y][x+1])/3;
                entrada[y][x] = (int)suma;
            }
            else if(x == entrada[0].length - 1){
                 int suma = (entrada[y][x-1] + entrada[y-1][x-1] + entrada[y-1][x])/3;
                entrada[y][x] = (int)suma;
            }else{
                int suma = (entrada[y][x-1] + entrada[y-1][x-1] + entrada[y-1][x] +entrada[y-1][x+1] + entrada[y][x+1])/5;
                entrada[y][x] = (int)suma;   
            }
        }
        else{
//            int suma = (entrada[y][x-1] + entrada[y-1][x-1] + entrada[y-1][x] +entrada[y-1][x+1] + entrada[y][x+1] + entrada[y][x+1] + entrada[y][x+1] + entrada[y][x+1])/8;    
//            entrada[y][x] = (int)suma;  
        }
        return 0;
    }


    public static void imprimirM(int [][] entrada){
        for (int i=0; i<entrada.length; i++){
            for(int j=0; j<entrada[0].length;j++)
                System.out.print(entrada[i][j]+" ");
        System.out.println("");
        }
    }

    public static void imprimirA(int [] entrada){
        for(int i=0; i<entrada.length; i++)
            System.out.println(entrada[i] +" ");
    }


 

    /**
     * @return the imagenEscalada
     */
    public int[][] getImagenEscalada() {
        return imagenEscalada;
    }

    /**
     * @param imagenEscalada the imagenEscalada to set
     */
    public void setImagenEscalada(int[][] imagenEscalada) {
        this.imagenEscalada = imagenEscalada;
    }

    /**
     * @return the imagenOriginal
     */
    public int[][] getImagenOriginal() {
        return imagenOriginal;
    }



}
