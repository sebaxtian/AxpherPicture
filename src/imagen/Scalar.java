/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagen;

import javax.swing.JOptionPane;

/**
 *
 * @author Administrador
 */
public class Scalar {
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
    private int [][] imagenEscalada;
    private int [][] imagenOriginal;
    
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
 *
 * @author Jhon
 * @entrada : arreglo con con la frecuencia de todos los pixeles (histograma)
 */
    public static int calculoDosPicos(int[] entrada){
       //calculo de la posicion del histograma con el mayor dato
        int hk=0; //esta variable hace referencia al valor mas alto H(k)
        int k=0; //hace referencia al punto mas alto de los picos
        for(int i=0; i<entrada.length; i++)
            if(entrada[i]>k){
                k=i;
                hk=entrada[i];
            }
        System.out.println("h = " +k);

        //******** calculo la posicion(j) donde esta el 2 pico mas lejano
        int hi=0;//esta variable hace referencia al segundo valor mas alto H(j)
        int j=0; // hace referencia al segundo punto mas alto de los picos
        for(int i=0; i<entrada.length; i++){
            int aux = ((int)Math.pow(i-k,2))*(entrada[i]);
            if(aux>hi){
                j=i;
                hi=aux;
            }
        }
        System.out.println("j = " +j);


        //********calculo el minimo valor entre los 2 mayores picos

        int menor=k, mayor=j;
        if(k>j){
            mayor = k;
            menor = j;
        }

        int t=j; // hace referencia al punto mas bajo entre los 2 picos
        int ht = hk; //esta variable hace referencia al valor mas bajo(H(h)), entre  H(k) y H(j)
        for(int i = menor+1; i < mayor-1; i++)
            if(entrada[i]<ht){
                t=i;
                ht=entrada[i];
                //System.out.println(" OJO ht "+ht);
            }

        System.out.println("t = " +t);
        return t;
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
