/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pruebas;

    /**
 * @entrada : arreglo con con la frecuencia de todos los pixeles (histograma)
 * Definicion de la clase Umbral
 * Calcula el umbral de una imagen apartir
 * De un histograma generado por la imagen
 * retornado un numero entero, el cual
 * hace referencia a la parte del histograma
 * seleccionado para aplicar el umbral
 * @author Jhon Javier Cardona Muñoz
 * @Fecha lun abr  2 22:31:13 COT 2012
 * @version 0.1
*/
public class umbral {
    
    private int posicionUmbral;

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
    
        public static void isoData(int [] entrada){
        int u=0; //variable que almacena la media del histograma
        //calculo de la media
        for(int i=0; i<entrada.length; i++){
            u+=entrada[i];
        }
        u/=entrada.length;
        System.out.println("u : " +u);
    }

public static void  main(String []arg){}

    /**
     * @return the posicionUmbral
     */
    public int getPosicionUmbral() {
        return posicionUmbral;
    }

    /**
     * @param posicionUmbral the posicionUmbral to set
     */
    public void setPosicionUmbral(int posicionUmbral) {
        this.posicionUmbral = posicionUmbral;
    }
}
