/*daniel  1144031217
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
    private short[][] matrizGrisOriginal=null;
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
    public FiltroNoise(Imagen imagen) {
        this.imagen = imagen;
        this.matrizGrisOriginal = new short[imagen.getMatrizGris().length][imagen.getMatrizGris()[0].length];
        this.matrizGris = new short[imagen.getMatrizGris().length][imagen.getMatrizGris()[0].length];
        for (int i = 0; i < imagen.getMatrizGris().length; i++) 
            for (int j = 0; j < imagen.getMatrizGris()[0].length; j++) {
                this.matrizGrisOriginal[i][j] = imagen.getMatrizGris()[i][j];
            }
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
            
            if (this.imagen.getFormato().equals("P2")) {
                //inicializacion de los datos de la matriz gris
                for(int i=0; i<this.matrizGris.length;i++)
                    for(int j=0; j<this.matrizGris[0].length; j++)
                        this.matrizGris[i][j] = this.matrizGrisOriginal[i][j];
                
                int tope = tamanoMascara/2; //variable que sirve de control para evitar que se desborde la mascara de la matriz
                
                for (int i = tope; i < this.imagen.getMatrizGris().length-tope; i++) {
                    for (int j = tope; j < this.imagen.getMatrizGris()[0].length-tope; j++) {
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
//                                System.out.print(mascara[y][x]+" ");
                                mascaraOrdena[posicion] = mascara[y][x];
                                posicion++;
                            }
  //                          System.out.println();
                        }
      //                    System.out.println("********************************");
//                        System.out.println("mascara Desorden");
//                        for(int k=0; k<mascaraOrdena.length;k++){
//                            System.out.print(mascaraOrdena[k]+" ");
//                        }    
//                        System.out.println();
                        Arrays.sort(mascaraOrdena);
                        //System.out.println("mascara orden");
//                        for(int k=0; k<mascaraOrdena.length;k++){
//                            System.out.print(mascaraOrdena[k]+" ");
//                        }
//                        System.out.println();
                        //***********************************************
                        //Se le asigna a la posicion i,j la mediana de la mascara
                       this.matrizGris[i][j] = mascaraOrdena[(int)Math.ceil(mascaraOrdena.length/2)];   
                    }
                }  
                this.imagen.setMatrizGris(this.matrizGris);
            } else if (this.imagen.getFormato().equals("P3")) {
             
            } else {
                System.out.println("(clase Filtro) Que formato sera " + this.imagen.getFormato());
            }


        }
    
    }
    
    /**
     * Metodo sigma : filtro encargado filtra el ruido de una imagen
     * el cual consiste en toma una ventana de 5x5 y en donde el elemento que se encuentra
     * en el centro (3,3), es el pixel que deseamos hallar con base a la vecinda que se encuntra 
     * en la ventana seleccionada, recorriendo la ventana y restando de cada uno el dato del pixel
     * (3,3) los datos que sean menores al umbral se toman y luego se suma y se divide sobre la cantidad de datos
     *@param sigma parametro tipo short, el cual sirve como umbral de referencia para el metodo
     */
    public void filtroSigma(short sigma){
        
        int tamanoMascara = 5;//Si quiere toma el valor de la mascara que es constante
        //inicializacion de los datos de la matriz gris
        for (int i = 0; i < this.matrizGris.length; i++) {
            for (int j = 0; j < this.matrizGris[0].length; j++) {
                this.matrizGris[i][j] = this.matrizGrisOriginal[i][j];
            }
        }

        //mascara seleccionada para realizar el filtro
        short[][] mascara = new short[tamanoMascara][tamanoMascara];
                
        int tope = tamanoMascara / 2; //variable que sirve de control para evitar que se desborde la mascara de la matriz
        //JOptionPane.showMessageDialog(null, "tope: "+tope);   

        for (int i = tope; i < this.imagen.getMatrizGris().length - tope; i++) {
            for (int j = tope; j < this.imagen.getMatrizGris()[0].length - tope; j++) {

                //llenado de la mascara
                for (int y = 0; y < mascara.length; y++) {
                    for (int x = 0; x < mascara[0].length; x++) {
                        mascara[y][x] = this.imagen.getMatrizGris()[i - tope + y][j - tope + x];
                        //System.out.print(mascara[y][x]+" ");
                    }
                    //System.out.println();
                }
                //System.out.println();
                
                short cantidad = 0; // variable encargada de llevar la cantidad de pixeles que cumplen el umbral (sigma)
                short sumatoria = 0; // sumatoria de los pixeles que cumplen el umbral (sigma)

                 // Calculo de sumatoria y cantidad de pixles que cumplen el umbral
                for (int y = 0; y < mascara.length; y++) {
                    for (int x = 0; x < mascara[0].length; x++) {
                        //if(Math.abs(mascara[y][x]-mascara[2][2]) < sigma){
                        if((mascara[y][x]-mascara[2][2]) < sigma){
                            sumatoria+=mascara[y][x];
                            cantidad++;
                            //System.out.println("Cumple --> (y,x)="+mascara[y][x]+" sigma="+sigma +" Resultado="+Math.abs(mascara[y][x]-mascara[2][2])); 
                        }else;
                            //System.out.println("No Cumple --> (y,x)="+mascara[y][x]+" sigma="+sigma +" Resultado="+Math.abs(mascara[y][x]-mascara[2][2]));
                    }
                    //System.out.println();
                }
                //System.out.println("sumatoria="+sumatoria+ "  cantidad="+cantidad);
                
                //Se le asigna a la matriz imagen el nuevo valor para el pixel evaluado
                if(cantidad > 0){
                    this.matrizGris[i][j] = (short) (sumatoria/cantidad);
                    //System.out.println("matriz="+this.matrizGris[i][j]+" sum="+sumatoria+" cant="+cantidad);
                }
                
            }
        }
        this.imagen.setMatrizGris(this.matrizGris);
    }
    
    
    public void nagaoMatsuyama(){
        int tamanoMascara = 5 ;//Si quiere toma el valor de la mascara que es constante
        //inicializacion de los datos de la matriz gris
                for(int i=0; i<this.matrizGris.length;i++)
                    for(int j=0; j<this.matrizGris[0].length; j++)
                        this.matrizGris[i][j] = this.matrizGrisOriginal[i][j];
        
        //mascara seleccionada para realizar el filtro
        short[][] mascara = new short[tamanoMascara][tamanoMascara];
        //arreglo con los pixeles tomados de la mascara ordenados  
        short[] mascaraOrdena = new short[tamanoMascara * tamanoMascara];
        
        int tope = tamanoMascara/2; //variable que sirve de control para evitar que se desborde la mascara de la matriz
        //JOptionPane.showMessageDialog(null, "tope: "+tope);
                               
        for (int i = tope; i < this.imagen.getMatrizGris().length-tope; i++) {        
            for (int j = tope; j < this.imagen.getMatrizGris()[0].length-tope; j++) {        
                
                //JOptionPane.showMessageDialog(null, "iteracion: i="+i+ " j="+j);        
                
                //llenado de la mascara
                for (int y = 0; y < mascara.length; y++) {
                    for (int x = 0; x < mascara[0].length; x++) {
                        mascara[y][x] = this.imagen.getMatrizGris()[i - tope + y][j - tope + x];
                    }
                }
                
                  // ************Impresion de las subventanas********************
//                for (int y = 0; y < mascara.length; y++) {
//                    for (int x = 0; x < mascara[0].length; x++) {
//                        System.out.print(mascara[y][x] + " ");
//                    }
//                    System.out.println();
//                }
                
                //Declaracion de subVentanas, de las cuales se van a calcular la varianza para la que tenga menor tomar su mediana
                
                short [] central = new short[9]; //Central
                short [] norte = new short[7]; //Norte
                short [] este = new short[7]; //Este
                short [] sur = new short[7]; //Sur
                short [] oeste = new short[7]; //Oeste
                short [] norteOeste = new short[7]; //NorteOeste
                short [] norteEste = new short[7]; //NorteEste
                short [] surEste = new short[7]; //SurEste
                short [] SurOeste = new short[7]; //SurOeste
                
               
               int posicion=0; 
                //Llenado de las subVentana Central
               for (int y = 1; y < mascara.length-1; y++) {
                    for (int x = 1; x < mascara[0].length-1; x++) {
                        central[posicion] = mascara[y][x];
                        posicion++;
                    }
                }
                
               //****************************************************************
                //Llenado de las subVentana Norte
                posicion = 0;
                for (int y = 0; y < mascara.length-3; y++) {
                    for (int x = 1; x < mascara[0].length-1; x++) {
                        //System.out.print(mascara[y][x] + " ");
                        norte[posicion] = mascara[y][x];
                        posicion++;
                    }
                    //System.out.println();
                    if(y==1){
                        //System.out.print("Revisar posicion "+posicion + "  i="+i +" j ="+j);
                        norte[posicion] = this.imagen.getMatrizGris()[i][j];
                    }
                }
        
                 //****************************************************************
                //Llenado de las subVentana Este
                
                posicion = 0;
                for (int y = 1; y < mascara.length-1; y++) {
                    for (int x = 3; x < mascara[0].length; x++) {
                        //System.out.print(mascara[y][x] + " ");
                        este[posicion] = mascara[y][x];
                        posicion++;
                    }
                    //System.out.println();
                    if(y==3){
                        este[posicion] = this.imagen.getMatrizGris()[i][j];
                    }
                }
                
                 //****************************************************************
                //Llenado de las subVentana Sur
                posicion = 0;
                for (int y = 3; y < mascara.length; y++) {
                    for (int x = 1; x < mascara[0].length-1; x++) {
                        //System.out.print(mascara[y][x] + " ");
                        sur[posicion] = mascara[y][x];
                        posicion++;
                    }
                    //System.out.println();
                    if(y==4){
                        sur[posicion] = this.imagen.getMatrizGris()[i][j];
                    }
                } 
                
                //****************************************************************
                //Llenado de las subVentana Oeste
                posicion = 0;
                for (int y = 1; y < mascara.length-1; y++) {
                    for (int x = 0; x < mascara[0].length-3; x++) {
                        //System.out.print(mascara[y][x] + " ");
                        oeste[posicion] = mascara[y][x];
                        posicion++;
                    }
                    //System.out.println();
                    if(y==3){
                        oeste[posicion] = this.imagen.getMatrizGris()[i][j];
                    }
                } 
                
                //****************************************************************
                //Llenado de las subVentana NorteOeste
                posicion = 0;
                for (int y = 0; y < mascara.length-2; y++) {
                    for (int x = 0; x < mascara[0].length-2; x++) {
                        //System.out.println("y "+y + " , x "+x);
                        if((y==0 && x==2)||(y==2 && x==0)){ 
                           // System.out.println("no toma el dato y "+y + " , x "+x);
                        }else{
                            norteOeste[posicion] = mascara[y][x];
                            posicion++;
                        }
                    }
                    //System.out.println();  
                } 
                
                
                //****************************************************************
                //Llenado de las subVentana NorteEste
                posicion = 0;
                for (int y = 0; y < mascara.length-2; y++) {
                    for (int x = 2; x < mascara[0].length; x++) {
                        //System.out.print(mascara[y][x] + " ");
                        if((y==0 && x==2)||(y==2 && x==4)){
                           // System.out.println("no toma el dato y "+y + " , x "+x);
                        }else{
                            norteEste[posicion] = mascara[y][x];
                            posicion++;
                        }
                    }
                    //System.out.println();  
                } 
                
                
                //****************************************************************
                //Llenado de las subVentana SurEste
                posicion = 0;
                for (int y = 2; y < mascara.length; y++) {
                    for (int x = 2; x < mascara[0].length; x++) {
                        //System.out.print(mascara[y][x] + " ");
                        if((y==2 && x==4)||(y==4 && x==2)){
                           // System.out.println("no toma el dato y "+y + " , x "+x);
                        }else{
                            surEste[posicion] = mascara[y][x];
                            posicion++;
                        }
                    }
                    //System.out.println();  
                } 
                
                
                //****************************************************************
                //Llenado de las subVentana SurOeste
                posicion = 0;
                for (int y = 2; y < mascara.length; y++) {
                    for (int x = 0; x < mascara[0].length-2; x++) {
                        //System.out.print(mascara[y][x] + " ");
                        if((y==2 && x==0)||(y==4 && x==2)){
                          //  System.out.println("no toma el dato y "+y + " , x "+x);
                        }else{
                            SurOeste[posicion] = mascara[y][x];
                            posicion++;
                        }
                    }
                    //System.out.println();  
                }

                double [][] varianzaMedia = new double[9][2];
                
//                System.out.println("******Central*******");
//                for(int y=0; y<central.length;y++)
//                    System.out.print(central[y]+" ");
//                System.out.println();
                varianzaMedia[0][0]= variaza(central);
                varianzaMedia[0][1]= media(central);
                
//                System.out.println("******Norte*******");
//                for(int y=0; y<norte.length;y++)
//                    System.out.print(norte[y]+" ");
//                System.out.println();
                varianzaMedia[1][0]= variaza(norte);
                varianzaMedia[1][1]= media(norte);
                
//                System.out.println("******Este*******");
//                for(int y=0; y<este.length;y++)
//                    System.out.print(este[y]+" ");
//                System.out.println();
                varianzaMedia[2][0]= variaza(este);
                varianzaMedia[2][1]= media(este);
                
//                System.out.println("******Sur*******");
//                for(int y=0; y<sur.length;y++)
//                    System.out.print(sur[y]+" ");
//                System.out.println();
                varianzaMedia[3][0]= variaza(sur);
                varianzaMedia[3][1]= media(sur);
                
//                System.out.println("******Oeste*******");
//                for(int y=0; y<oeste.length;y++)
//                    System.out.print(oeste[y]+" ");
//                System.out.println();
                varianzaMedia[4][0]= variaza(oeste);
                varianzaMedia[4][1]= media(oeste);
                
//                System.out.println("******norteOeste*******");
//                for(int y=0; y<norteOeste.length;y++)
//                    System.out.print(norteOeste[y]+" ");
//                System.out.println();
                varianzaMedia[5][0]= variaza(norteOeste);
                varianzaMedia[5][1]= media(norteOeste);
                
//                System.out.println("******NorteEste*******");
//                for(int y=0; y<norteEste.length;y++)
//                    System.out.print(norteEste[y]+" ");
//                System.out.println();
                varianzaMedia[6][0]= variaza(norteEste);
                varianzaMedia[6][1]= media(norteEste);
                
//                System.out.println("******SurEste*******");
//                for(int y=0; y<surEste.length;y++)
//                    System.out.print(surEste[y]+" ");
//                System.out.println();
                varianzaMedia[7][0]= variaza(surEste);
                varianzaMedia[7][1]= media(surEste);
                
//                System.out.println("******SurOste*******");
//                for(int y=0; y<SurOeste.length;y++)
//                    System.out.print(SurOeste[y]+" ");
//                System.out.println();
                varianzaMedia[8][0]= variaza(SurOeste);
                varianzaMedia[8][1]= media(SurOeste);
                
//                System.out.println("******Impresion VarianzaMedia*******");
                double menorVar = Short.MAX_VALUE;
                int pixelSeleccion = 0;
                for(int y=0; y < varianzaMedia.length; y++){
                    if(varianzaMedia[y][0] < menorVar){
                        menorVar = varianzaMedia[y][0];
                        pixelSeleccion = y;
                    }
//                    for(int x=0; x<varianzaMedia[0].length;x++){
//                        System.out.print(varianzaMedia[y][x]+"  ");
//                    }
//                    System.out.println();
                }
//                System.out.println("menor varianza ="+menorVar +" en la posicion "+pixelSeleccion);    
                
                this.matrizGris[i][j] = (short) varianzaMedia[pixelSeleccion][1];
            //JOptionPane.showMessageDialog(null, "Revisar");
            }
        }
        
        this.imagen.setMatrizGris(this.matrizGris);
    }
    
    /* Filtro GAUSIANO */
    public void filtroGausiano(int tamanoMascara){
        
        if(tamanoMascara%2 == 0 || tamanoMascara==1){
            JOptionPane.showMessageDialog(null,"Favor ingresar un numero IMPAR o mayor a 1");
        } else {  
            
            Convolucion cv = new Convolucion();
            //inicializacion de los datos de la matriz gris
            for (int i = 0; i < this.matrizGris.length; i++) {
                for (int j = 0; j < this.matrizGris[0].length; j++) {
                    this.matrizGris[i][j] = this.matrizGrisOriginal[i][j];
                }
            }
            
            //mascara seleccionada para realizar el filtro
            short[][] mascara = multiplicaVector(trianguloPascal(tamanoMascara));            
            
            int tope = tamanoMascara / 2; //variable que sirve de control para evitar que se desborde la mascara de la matriz
            //JOptionPane.showMessageDialog(null, "tope: "+tope);   

            for (int i = tope; i < this.imagen.getMatrizGris().length - tope; i++) {
                for (int j = tope; j < this.imagen.getMatrizGris()[0].length - tope; j++) {
                    this.matrizGris[i][j] = cv.convolucionar(this.matrizGrisOriginal, mascara, i, j);             
                }
            }
        }
        this.imagen.setMatrizGris(this.matrizGris);
    }
    
    /* Filtro SOBEL */
     public void filtroSobel(){
     
         Convolucion cv = new Convolucion();
         //inicializacion de los datos de la matriz gris
            for (int i = 0; i < this.matrizGris.length; i++) {
                for (int j = 0; j < this.matrizGris[0].length; j++) {
                    this.matrizGris[i][j] = this.matrizGrisOriginal[i][j];
                }
            }
            
            //mascara h1 seleccionada para realizar el filtro
            short[][] mascaraH1 = {{1,2,1},{0,0,0},{-1,-2,-1}};    
            
             //mascara h2 seleccionada para realizar el filtro
            short[][] mascaraH2 = {{1,0,-1},{2,0,-2},{1,0,-1}};   
            
            int tope = 3 / 2; //variable que sirve de control para evitar que se desborde la mascara de la matriz
            //JOptionPane.showMessageDialog(null, "tope: "+tope);   

            for (int i = tope; i < this.imagen.getMatrizGris().length - tope; i++) {
                for (int j = tope; j < this.imagen.getMatrizGris()[0].length - tope; j++) {
                    int x = cv.convolucionar(this.matrizGrisOriginal, mascaraH1, i, j);
                    int y = cv.convolucionar(this.matrizGrisOriginal, mascaraH2, i, j);
                    this.matrizGris[i][j] = (short) Math.sqrt(Math.pow(y, 2) + Math.pow(x, 2));             
                }
            }
            this.imagen.setMatrizGris(this.matrizGris);
     }
    
    
    private short[][] multiplicaVector(short [] A){
        short [][] C = new short[A.length][A.length];
        
        for(int i=0; i<C.length; i++){
            for(int j=0; j<C[0].length;j++){
                C[i][j] = (short) (A[i] * A[j]);
                //System.out.print(C[i][j]+" ");
            }
            //System.out.println();
        }
        return C;
    }
    
    private short [] trianguloPascal(int nivel){
    
        short [] aux = new short[nivel];
        
        ArrayList resultado = new ArrayList();
        ArrayList base = new ArrayList();
        
        base.add(1);
        base.add(1);
        
        for (int i=2; i< nivel; i++){
            resultado.add(1);
            for(int j=1; j < base.size();j++){
                int dato = (Integer) base.get(j-1)+ (Integer)base.get(j);
                resultado.add(dato);
            }
            
            resultado.add(1);
            base.removeAll(base);
           
            for(int k=0; k<resultado.size(); k++){
                int dato = (Integer) resultado.get(k);
                base.add(dato);
            }
            //JOptionPane.showMessageDialog(null, "Tamano Base "+base.size());
            if(i < nivel-1)
                resultado.removeAll(resultado);
            //JOptionPane.showMessageDialog(null, "Tamano resultado "+resultado.size());
        } 
        
        //System.out.println("**********Pascal*********************");
        for(int k=0; k<resultado.size(); k++){
            int dato = (Integer)resultado.get(k);
            aux[k]= (short) dato;    
            //System.out.println(aux[k]); 
        } 
        return aux;
    }
    
    
    private double variaza(short []datos){
        double media = 0;
        double var=0;
//        for(int i=0; i<datos.length; i++){
//            media += datos[i];
//        }
//        media /= datos.length;
//        System.out.println("media ="+media + "  N=" + datos.length);
        media= media(datos);
//        System.out.println("media ="+media + "  N=" + datos.length);
        for(int i=0; i<datos.length; i++){
            var += Math.pow(datos[i]-media, 2);
        }
        var /= datos.length;
//        System.out.println("varianza ="+var );
        return var;
    }
    
    private double media(short []datos){
        double media = 0;
        for(int i=0; i<datos.length; i++){
            media += datos[i];
        }
        media /= datos.length;
        //System.out.println("media ="+media + "  N=" + datos.length);
        return media;
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
     * @return the matrizGris
     */
    public short[][] getMatrizGris() {
        return matrizGris;
    }
    
}
