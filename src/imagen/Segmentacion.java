/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagen;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.SortOrder;
import javax.swing.text.Position;

/**
 *
 * @author jhon
 */
public class Segmentacion {
    int [][] cluster;
    Imagen imagen;
    public Segmentacion(Imagen imagen){
        this.imagen = imagen.clone();
        this.cluster = new int[imagen.getMatrizGris().length][imagen.getMatrizGris()[0].length];
    }
    
    public void k_means(){
        // cantidad de grupos (cluster)
        int  k =  (int) Math.sqrt(imagen.getNivelIntensidad());
        //int distanciaCentroides = (int)(imagen.getNivelIntensidad()/k)/2; //espacio automatico entre cada centroide  
        int distanciaCentroides = (int)(imagen.getNivelIntensidad()/k); //espacio automatico entre cada centroide  
    
        boolean[] centroidesFijos =new boolean[k];
        int [] centroides =new int[k];
        
        for (int i = 0; i < centroides.length; i++) {
            centroides[i] = (distanciaCentroides * i)+(distanciaCentroides / 2); //OJO se suma 1 tener en cuenta si sucede volcado de memoria
            JOptionPane.showMessageDialog(null, centroides[i]);
            centroidesFijos[i]=false;
        }
        
        //distanciaCentroides*=2;
        for (int i = 0; i < this.imagen.getMatrizGris().length; i++) {
            for (int j = 0; j <  this.imagen.getMatrizGris()[0].length; j++) {
                int posicion = (imagen.getMatrizGris()[i][j])/distanciaCentroides;//OJO se debe quitar 1, tener en cuenta si sucede volcado de memoria
                Point puntoC = new Point(i, j);
                //JOptionPane.showMessageDialog(null, posicion+" ij="+i+"-"+j);
                centroidesFijos[posicion]=true;
            }
        }
        
        int kCoordenadas = 0;
        for (int i = 0; i < centroidesFijos.length; i++) {
            if(centroidesFijos[i]==true)
                kCoordenadas++;
        }
        
        Point [] centroidesCoord =new Point[kCoordenadas];
        
        kCoordenadas = 0;
        for (int i = 0; i < this.imagen.getMatrizGris().length; i++) {
            for (int j = 0; j <  this.imagen.getMatrizGris()[0].length; j++) {
                int posicion = imagen.getMatrizGris()[i][j]/distanciaCentroides;
                if(centroidesFijos[posicion]==true){
                    centroidesFijos[posicion]=false;
                    Point C = new Point(i, j);
                    centroidesCoord[kCoordenadas]=C;
                    kCoordenadas++;
                }
            }
        }
        
        imprimirCentroides(centroidesCoord);
        boolean estado =true;
        do{
            Point [] centroidesCoordAux =new Point[kCoordenadas]; 
            for (int i = 0; i < centroidesCoordAux.length; i++) {
                centroidesCoordAux[i]=centroidesCoord[i];
            } 
            
            System.out.println("******************************************************************************************");
            int umbral =distanciaCentroides/2;
            for (int i = 0; i < this.imagen.getMatrizGris().length; i++) {
                for (int j = 0; j < this.imagen.getMatrizGris()[0].length; j++) {
                    //int posicion = this.imagen.getMatrizGris()[i][j] / distanciaCentroides;
                    for (int z = 0; z < centroidesCoord.length; z++) { // bucle para comparar el centroide con la matriz
                        //JOptionPane.showMessageDialog(null, "centroide "+centroides[z]+" - umblral"+umbral+" - matriz "+this.imagen.getMatrizGris()[i][j]);
                        //if (Math.abs(this.imagen.getMatrizGris()[i][j] - this.imagen.getMatrizGris()[centroidesCoord[z].x][centroidesCoord[z].y]) < umbral) {
                        if (Math.abs(this.imagen.getMatrizGris()[i][j] - centroides[z]) <= umbral) {
                            this.cluster[i][j]=z;
                            this.cluster[centroidesCoord[z].x][centroidesCoord[z].y]=z;
                            z=centroidesCoord.length;
                            //System.out.print(this.cluster[i][j]+"  ");
                            //break;
                        } 
                    }
                    //System.out.print("i="+i+",j="+j+" "+this.imagen.getMatrizGris()[i][j]+"  ");
                    System.out.print(this.cluster[i][j]+"  ");
                    //System.out.print("i="+i+",j="+j+" "+this.cluster[i][j]+"  ");
                }
                System.out.println();
            }
            
            JOptionPane.showMessageDialog(null, "Revisar 104");
            
            //Recalculamos los centroides 
            for (int z = 0; z < centroidesCoord.length; z++) { // bucle para comparar el centroide con la matriz
                Vector<Point> cent = new Vector<Point>();
                //JOptionPane.showMessageDialog(null, cent.size());
                for (int i = 0; i < this.cluster.length; i++) {
                    for (int j = 0; j < this.cluster[0].length; j++) {
                        if(this.cluster[i][j]==z)
                            cent.add(new Point(i,j));
                    }
                }
                // Vamos a recorrer el vector de coordenadas con el fin de realizar un arreglo de coordenadas ordenado
                centroidesCoord[z] = reCalculoCentroide(cent);
            }
            
            JOptionPane.showMessageDialog(null, "Revisar 120");
            estado=igualCentroides(centroidesCoord,centroidesCoordAux);
        }while(!estado);
        
        
        
        
        
    }
    
    private Point reCalculoCentroide(Vector<Point> cent){
        //vamos a imprimir todos las coordenadas capturadas
        //for(int i=0; i<cent.size();i++){
        //    System.out.print("x="+cent.elementAt(i).x+"-y="+cent.elementAt(i).y+" , ");
        //}
        //System.out.println("**********************************************");
        //JOptionPane.showMessageDialog(null,"reviar 134");
        
        Point pt = new Point();
        short [] grupo = new short[cent.size()];
        
        for( int i=0; i<cent.size() ; i ++ ){ 
            grupo[i]= this.imagen.getMatrizGris()[cent.elementAt(i).x][cent.elementAt(i).y];
        }
        Arrays.sort(grupo);
        short valorMediano =  grupo[(int)Math.ceil(grupo.length/2)]; 
                
        //System.out.println("Valor mediano "+valorMediano);
        
        for( int i=0; i<cent.size() ; i ++ ){ 
            if(valorMediano==this.imagen.getMatrizGris()[cent.elementAt(i).x][cent.elementAt(i).y]){
                pt=cent.elementAt(i);
                break;      
            }
        }
        //System.out.println("punto x="+pt.x+" - y="+pt.y);
        return pt;
    }
    
    
    private boolean igualCentroides(Point [] centroidesCoord,Point [] centroidesCoordAux){
        boolean estado=true;    
        for (int i = 0; i < centroidesCoordAux.length; i++) {
            if(centroidesCoordAux[i].x==centroidesCoord[i].x  && centroidesCoordAux[i].y==centroidesCoord[i].y){
                
            }
            else{ 
               estado=false;
            } 
        }
        return estado;
    }
    
    private void imprimirCentroides(Point [] centroides){
        for (int i = 0; i < centroides.length; i++) {
            //JOptionPane.showMessageDialog(null, i);
            System.out.println("centroide "+i+":  x="+centroides[i].x+" ; y="+centroides[i].y);
        } 
    }
    
    public static void main(String[] arg){
        String rutaImgPGM = "ImgFuente/lena.pgm";
        Imagen imgPGM = new Imagen(rutaImgPGM);
        Segmentacion sg = new Segmentacion(imgPGM);
        sg.k_means();
    }
}
