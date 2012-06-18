/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagen;

import java.awt.Point;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Estudiante
 */
public class Canny {
    private Imagen imagen;
    
    public Canny(){
    }
    
    public void calculoCanny(Imagen imagen, int tamañoKernel, int umbral1, int umbral2){
        this.imagen = imagen;
        if(umbral1>umbral2){
            JOptionPane.showMessageDialog(null, "El umbral1 debe ser menor que el umbral2... el sistema revalua el umbral2 por umbral1 y el umbral1 por umbral2");
            int aux = umbral1;
            umbral1=umbral2;
            umbral2=aux;
        }
        
        FiltroNoise filterNoise = new FiltroNoise(imagen);
        filterNoise.filtroGausiano(tamañoKernel);
        //Ojo aqui implrimir la matriz despues del gausiano
        //filterNoise.getImagen().guardarImagen("ImgProcesado/gausianoLena.pgm");
        
        filterNoise.filtroSobel(tamañoKernel);
        //Ojo aqui implrimir la matriz despues de sobel
        //filterNoise.getImagen().guardarImagen("ImgProcesado/SobelLena.pgm");
        
        Imagen objimagen = new Imagen();
        objimagen = supresionNoMaxima(filterNoise.getImagen(),filterNoise.getMatrizDireccion());
        //objimagen.guardarImagen("ImgProcesado/noMaximoLena.pgm");
        
        objimagen = histeresis(objimagen, filterNoise.getMatrizDireccion(), umbral1, umbral2);
        //objimagen.guardarImagen("ImgProcesado/histeresisLena1.pgm");
        this.setImagen(objimagen);
    }

    
    
    public void calculoCanny(Imagen imagen, int tamañoKernel){
        this.imagen = imagen;
        //**********Calculo de Umbrales Automaticos *****************************
        Histograma histograma = new Histograma(imagen);
        
        //Umbralizacion umbral = new Umbralizacion(histograma, tamañoKernel);
        
        int umbral2 = 0;
        
        Umbralizacion umb = new Umbralizacion(histograma, tamañoKernel);
        umb.metodoOtsu();
        umbral2 = umb.getUmbralGris();
         
//        double var =0;
//        for(int i=0; i<histograma.getHistogramaGris().length;i++){
//            var = varianza2(histograma.getHistogramaGris(), i);
//            System.out.println("i="+i+" - var"+var);
//            JOptionPane.showMessageDialog(null,"i="+i+" - var"+var);
//            if(var > umbral2){
//                umbral2 = (int) var;
//                 System.out.println("i="+i+" - umbral2 = "+umbral2);
//            }
//        }
        
        //int umbral1 = (int)(0.5*var); 
        int umbral1 = (int)(0.5*umbral2); 
        //***************************************************************
         //JOptionPane.showMessageDialog(null, "El umbral1 ="+ umbral1 +" ... umbral2="+umbral2);
         System.out.println("El umbral1 ="+ umbral1 +" ... umbral2="+umbral2);
           
        if(umbral1>umbral2){
            JOptionPane.showMessageDialog(null, "El umbral1 debe ser menor que el umbral2... el sistema revalua el umbral2 por umbral1 y el umbral1 por umbral2");
            int aux = umbral1;
            umbral1=umbral2;
            umbral2=aux;
        }
        
        FiltroNoise filterNoise = new FiltroNoise(imagen);
        filterNoise.filtroGausiano(tamañoKernel);
        //Ojo aqui implrimir la matriz despues del gausiano
        //filterNoise.getImagen().guardarImagen("ImgProcesado/gausianoMama.pgm");
        
        filterNoise.filtroSobel(tamañoKernel);
        //Ojo aqui implrimir la matriz despues de sobel
        //filterNoise.getImagen().guardarImagen("ImgProcesado/SobelMama.pgm");
        
        Imagen objimagen = new Imagen();
        objimagen = supresionNoMaxima(filterNoise.getImagen(),filterNoise.getMatrizDireccion());
        //objimagen.guardarImagen("ImgProcesado/noMaximoMama.pgm");
        
        objimagen = histeresis(objimagen, filterNoise.getMatrizDireccion(), umbral1, umbral2);
        //objimagen.guardarImagen("ImgProcesado/histeresisMama.pgm");
        this.setImagen(objimagen);
    
    }
    
    
    
    /**
     * @return the imagen
     */
    public Imagen getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }
    
    /**
     * 
     * @param imagen
     * @param direccion
     * @param umbral1
     * @param umbral2
     * @return 
     */
    private Imagen histeresis(Imagen imagen, double [][] direccion, int umbral1, int umbral2){
       boolean [][] explorado = new boolean[direccion.length][direccion[0].length];
       boolean [][] borde = new boolean[direccion.length][direccion[0].length];
       //boolean [][] B = new boolean[direccion.length][direccion[0].length];
       this.imagen = imagen;
       
       for(int i = 0; i < explorado.length; i++){
            for (int j = 0; j < explorado[0].length; j++){
                explorado[i][j]=false;
                borde[i][j]=false;
                //B[i][j]=false;
            }   
       }
        
       
       int fila=0;
       int columna=0;
       boolean estado = false;
       short mayor =0;//esta variable se va utilizar en el caso de que no exista un dato mayor a umbral2 
       for(int i = 0; i < explorado.length; i++){
           if(estado){// esto se hace para que deje de iterar
               i=explorado.length-1;
           }
           
           for (int j = 0; j < explorado[0].length; j++){
               if(estado){// esto se hace para que deje de iterar
                   j=explorado[0].length-1;
               }
               
               if(imagen.getMatrizGris()[i][j] > (short)umbral2 &&!estado){
                    fila = i;
                    columna =j;
                    estado=true;
                }else{
                   if(mayor < imagen.getMatrizGris()[i][j]){
                        mayor = imagen.getMatrizGris()[i][j];
                        fila = i;
                        columna =j;
                   }
               }
            }   
       }
       
       if(!estado){
           //JOptionPane.showMessageDialog(null, "Revisar el umbral2 ="+umbral2);
           umbral2=mayor-1;
           if(umbral1>umbral2)
               umbral1=mayor-50;
       }
       
              
       //Algoritmo segun pdf detector de canny
       for(int i=1;i<imagen.getMatrizGris().length-1;i++){
           for(int j=1; j<imagen.getMatrizGris()[0].length-1;j++){
               //JOptionPane.showMessageDialog(null, "nuevo umbral2 ="+umbral2 +" fila="+i+ " columna="+j +"  matriz="+imagen.getMatrizGris()[i][j]);
               if(!explorado[i][j]&&(imagen.getMatrizGris()[i][j]> umbral2) ){
                   //JOptionPane.showMessageDialog(null, "nuevo umbral2 ="+umbral2 +" fila="+i+ " columna="+j +"  matriz="+imagen.getMatrizGris()[i][j]);
                   int k=i;
                   int l=j;
                   do{
                       explorado[k][l]=true;
                       borde[k][l]=true;
                       Point coordenada = polarToRectangula(direccion[k][l], 90, k, l);
                       //System.out.println("coor original ("+(k+1)+","+(l+1)+") - direccion "+direccion[k][l]); 
                       k=(int)coordenada.getX();
                       l=(int)coordenada.getY();
                       //System.out.println("coor nueva ("+(k+1)+","+(l+1)+") - imagen[k][l]="+imagen.getMatrizGris()[k][l]);
                   }while(imagen.getMatrizGris()[k][l]> umbral1 && !explorado[k][l]);
               }
           }
       }
       
              
       for(int i = 0; i < borde.length; i++){
            for (int j = 0; j < borde[0].length; j++){
                if(!borde[i][j]){
                  //System.out.print("("+(i+1)+","+(j+1)+") - ");
                  imagen.getMatrizGris()[i][j]=0;
                //borde[i][j];
                }
                
            }   
            //System.out.println();
       }  
        return imagen;
    }
    
    /**
     * 
     * @param angulo
     * @param compensacion
     * @param i
     * @param j
     * @return 
     */
    private Point polarToRectangula(double angulo, double compensacion, int i, int j){
        Point punto = new Point();
        double direccion = 0;
        

        if (angulo > 360 || angulo < 0) {
            //JOptionPane.showMessageDialog(null, "Ojo revisar porque se pasa del limite de los grados  i="+i +" j="+j+ " direcion="+dir);
            if (angulo < 0 && angulo >= -360) {
                angulo = 360 + angulo;
            } else if (angulo > 360) {
                JOptionPane.showMessageDialog(null, "Ojo revisar porque se pasa del limite (mayor 360) de los grados  i=" + i + " j=" + j + " direcion=" + direccion);
            } else {
                JOptionPane.showMessageDialog(null, "Ojo revisar porque se pasa del limite  (menor -360) de los grados  i=" + i + " j=" + j + " direcion=" + direccion);
            }
        }
        direccion = (angulo+compensacion);
        if(angulo+compensacion>360)
            direccion = (angulo+compensacion)-360;
        
        if ((direccion > 337.5 && direccion <= 360) || (direccion >= 0 && direccion <= 22.5)) {
            if (j < this.getImagen().getMatrizGris()[0].length-1) {
                j++;
            }
        } else if (direccion > 22.5 && direccion <= 67.5) {
            if (i > 0 && j < this.getImagen().getMatrizGris()[0].length-1) {
                i --;
                j ++;
            }
        } else if (direccion > 67.5 && direccion <= 112.5) {
            if(i>0)
                i--;
            
        } else if (direccion > 112.5 && direccion <= 157.5) {
            if(i>0 && j>0){
                j--;
                i--;
            }
            
        }else if (direccion > 157.5 && direccion <= 202.5) {
            if(j>0){
                j--;
            }
            
        } else if (direccion > 202.5 && direccion <= 247.5) {
            if(i<this.getImagen().getMatrizGris().length-1 && j>0){
                j--;
                i++;
            }
            
        } else if (direccion > 247.5 && direccion <= 292.5) {
            if(i<this.getImagen().getMatrizGris().length-1 ){
                i++;
            }
            
        } else if (direccion > 292.5 && direccion <= 337.5) {
            if(i<this.getImagen().getMatrizGris().length-1 && j<this.getImagen().getMatrizGris()[0].length-1){
                j++;
                i++;
            }
            
        } 
        punto.setLocation(i, j); // i=x, j=y
        return punto;
    }
  
    
    /**
     * 
     * @param magnitud
     * @param direccion
     * @return 
     */
    private Imagen supresionNoMaxima(Imagen magnitud, double [][] direccion){
        
        short [][] pixeles = magnitud.getMatrizGris();
        
        //Ojo tener en cuenta que aqui se esta perdiendo un pixel en los bordes
        for(int i = 1; i < pixeles.length-1; i++){
            for (int j = 1; j < pixeles[0].length-1; j++){
                
                double dir = direccion[i][j];
                
                if(dir>180 && dir <=360){
                    dir /=2; 
                }else if(dir > 360 || dir < 0){
                    //JOptionPane.showMessageDialog(null, "Ojo revisar porque se pasa del limite de los grados  i="+i +" j="+j+ " direcion="+dir);
                    if(dir<0 && dir>=-360){
                        dir=360+dir;
                    }
                    else if(dir>360){
                        JOptionPane.showMessageDialog(null, "Ojo revisar porque se pasa del limite (mayor 360) de los grados  i="+i +" j="+j+ " direcion="+dir);
                    }else{
                            JOptionPane.showMessageDialog(null, "Ojo revisar porque se pasa del limite  (menor -360) de los grados  i="+i +" j="+j+ " direcion="+dir);
                    }
                }
                
                if((dir >=0 && dir<=22.5)||(dir >=157.5 && dir<=180)){
                    if( (magnitud.getMatrizGris()[i][j] < magnitud.getMatrizGris()[i][j+1]) || (magnitud.getMatrizGris()[i][j] < magnitud.getMatrizGris()[i][j-1]))
                        magnitud.getMatrizGris()[i][j]=0;
                    
                }else if(dir >22.5 && dir<=67.5){
                    if( (magnitud.getMatrizGris()[i][j] < magnitud.getMatrizGris()[i+1][j+1]) || (magnitud.getMatrizGris()[i][j] < magnitud.getMatrizGris()[i-1][j-1]))
                        magnitud.getMatrizGris()[i][j]=0;
                
                }else if(dir >67.5 && dir<=112.5){
                    if( (magnitud.getMatrizGris()[i][j] < magnitud.getMatrizGris()[i+1][j]) || (magnitud.getMatrizGris()[i][j] < magnitud.getMatrizGris()[i-1][j]))
                        magnitud.getMatrizGris()[i][j]=0;
                    
                }else if(dir >112.5 && dir<157.5){
                    if( (magnitud.getMatrizGris()[i][j] < magnitud.getMatrizGris()[i+1][j-1]) || (magnitud.getMatrizGris()[i][j] < magnitud.getMatrizGris()[i-1][j+1]))
                        magnitud.getMatrizGris()[i][j]=0;   
                }   
            }   
        } 
        return magnitud;
    }
    
    
     
    //***************Funciones para calculo automatico de Otsu************************
    //An Adaptive Threshold for the Canny Operator of Edge Detection
    /**
     * 
     * @param pi histograma Pi (i=1,2,… T,)
     * @param t (1<t<T)
     * @return where the w0(t) and w1(t) are the two classes’ probability respectively
     */
    private int w0(int[] pi, int t){
        //t--;//ojo tener en cuenta esta linea 
        int w0=0;
        for(int i=0; i<t+1; i++){
            w0+= pi[i];
            System.out.println("pi"+i+"="+pi[i]);
        }
        System.out.println("t="+t+"  w0="+w0);
        return w0;
    }
    
    
    /**
     * 
     * @param pi histograma Pi (i=1,2,… T,)
     * @param t (1<t<T)
     * @return where the w0(t) and w1(t) are the two classes’ probability respectively
     */
    private int w1(int[] pi, int t){
        //t--;//ojo tener en cuenta esta linea
        int w1=0;
        for(int i=t+1; i<pi.length; i++){    
            w1+= pi[i];
            System.out.println("pi"+i+"="+pi[i]);
        }
        System.out.println("t="+t+"  w1="+w1);
        return w1;
    }
    
     
    /**
     * 
     * @param pi histograma Pi (i=1,2,… T,)
     * @param t (1<t<T)
     * @return where the w0(t) and w1(t) are the two classes’ probability respectively
     */
    private double u0(int[] pi, int t){
        //t--;//ojo tener en cuenta esta linea
        double u0 = 0;
        for(int i=0; i<t+1; i++){
            System.out.println("n0="+n0(pi, t)+"  w0="+w0(pi, t)+"  pi"+i+"="+pi[i]);
            if(w0(pi, t)!=0 && n0(pi, t)!=0)            
                u0 += ((i+1) * (pi[i]/n0(pi, t)))/w0(pi, t);
        }
        System.out.println("t="+t+"  u0="+u0);
        return u0;
    } 
    
    
    /**
     * 
     * @param pi histograma Pi (i=1,2,… T,)
     * @param t (1<t<T)
     * @return where the w0(t) and w1(t) are the two classes’ probability respectively
     */
    private double u1(int[] pi, int t){
        //t--;//ojo tener en cuenta esta linea
        double u1 =0;
        for(int i=t+1; i<pi.length; i++){
            System.out.println("n1="+n1(pi, t)+"  w1="+w1(pi, t)+"  pi"+i+"="+pi[i]);
            if(w1(pi, t)!=0)
                u1 += (i* (pi[i]/n1(pi, t)))/w1(pi, t);
        }
        System.out.println("t="+t+"  u1="+u1);
        return u1;
    } 
    
    /**
     * 
     * @param pi histograma Pi (i=1,2,… T,)
     * @return 
     */
    private double ut(int[] pi){
        //t--;//ojo tener en cuenta esta linea
        double ut = 0;
        
        for(int i=0; i<pi.length; i++){
            System.out.println("i="+ i +"  pi"+i+"="+pi[i]);
            ut += ((i+1)*pi[i]);
        }
        System.out.println("ut="+ut);
        return ut;
    } 
    
     /**
     * 
     * @param pi histograma Pi (i=1,2,… T,)
     * @return 
     */
    private double uc(int[] pi, int t){
        //t--;//ojo tener en cuenta esta linea
        double uc = (w0(pi, t) * u0(pi, t)) + (w1(pi, t) * u1(pi, t));
        System.out.println("t="+t+"  uc="+uc);
        return uc;
    } 
    
    /**
     * 
     * @param pi
     * @param t
     * @return 
     */
    private int n0(int[] pi, int t){
        int n0=0;
        for(int i=0; i<t; i++){
            n0 += pi[i];
        }
        return n0;
    }
    
        /**
     * 
     * @param pi
     * @param t
     * @return 
     */
    private int n1(int[] pi, int t){
        int n1=0;
        for(int i=t; i<pi.length; i++){
            n1 += pi[i];
        }
        return n1;
    }
    
    
    /**
     * 
     * @param pi
     * @param t
     * @return The criterion function is defined as variance between the two classes.
     */
    private double varianza2(int[] pi, int t){
        //double varianza2 = (w0(pi, t) * w1(pi, t)) * Math.pow(u0(pi, t)-u1(pi, t),2);
        double varianza2 = (w0(pi, t) * Math.pow(u0(pi, t)- uc(pi, t),2)) + ( w1(pi, t) * Math.pow(u1(pi, t)-uc(pi, t),2));
        return varianza2;
    }
   
    
    
    
    
    public static void main(String []arg){
            
        //String rutaImgPGM = "ImgFuente/PGM.pgm";
        String rutaImgPGM = "ImgFuente/Familia.pgm";
        Imagen imgPGM = new Imagen(rutaImgPGM);
        imgPGM.guardarImagen("ImgProcesado/familia.pgm");
        Canny cn = new Canny();
        cn.calculoCanny(imgPGM,3);
        //cn.calculoCanny(imgPGM,3,98,115);
        
        cn.getImagen().guardarImagen("ImgProcesado/cannyFamilia.pgm");
        
    }
    
    
    
}
