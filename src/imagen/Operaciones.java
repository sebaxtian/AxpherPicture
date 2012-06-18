/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagen;

import javax.swing.JOptionPane;

/**
 * Clase encargada de realizar operaciones con imagenes
 * Operaciones en una imagen or entre un conjunto de imágenes:
 * Booleanas: and, or, xor, not
 * Aritméticas: suma, resta, producto/división, media
 * Relacionales: máximo, mínimo
 * @author Jhon
 */
public class Operaciones {
    
    //private Imagen objImagen;
    
    public Operaciones(){
    
    }
    
    /**
     * 
     * @param imagenOriginal
     * @param imagenOperando
     * @param operando
     * @return 
     */
    
    public Imagen OrAndXor (Imagen imagenOriginal, Imagen imagenOperando, String operando){
        Imagen imagenResultado = new Imagen(); //Ojo aqui EROOR
        imagenResultado = imagenOriginal.clone();
        
        if(imagenOriginal.getFormato().equals(imagenOperando.getFormato()) && imagenOriginal.getNivelIntensidad()==imagenOperando.getNivelIntensidad() && (imagenOriginal.getM()== imagenOperando.getM()) && (imagenOriginal.getN()== imagenOperando.getN())){
            if(imagenOriginal.getFormato().equals("P2")){
                //imagenResultado.setFormato(imagenOriginal.getFormato());
                //imagenResultado.setMatrizGris(imagenOriginal.getMatrizGris());
                //imagenResultado.setNivelIntensidad(nivelIntensidad);
                short [][] matrizResultado = new short[imagenResultado.getMatrizGris().length][imagenResultado.getMatrizGris()[0].length];
                
                for(int i=0; i < matrizResultado.length; i++){
                    for(int j=0; j< matrizResultado[0].length; j++){
                        
                        String binOriginal = Integer.toBinaryString(imagenOriginal.getMatrizGris()[i][j]);
                        String binOperando = Integer.toBinaryString(imagenOperando.getMatrizGris()[i][j]);

                        char[] binOrigen = new char[Integer.toBinaryString(imagenOriginal.getNivelIntensidad()).length()];
                        char[] binOperand = new char[Integer.toBinaryString(imagenOperando.getNivelIntensidad()).length()];

                        for (int x = 0; x < binOrigen.length; x++) {
                            binOrigen[x] = '0';
                        }

                        for (int x = 0; x < binOperand.length; x++) {
                            binOperand[x] = '0';
                        }
           
                        for (int x = 0; x < binOriginal.length(); x++) {
                            binOrigen[binOrigen.length - 1 - x] = binOriginal.charAt(x);
                        }

                        for (int x = 0; x < binOperando.length(); x++) {
                            binOperand[binOperand.length - 1 - x] = binOperando.charAt(x);
                        }
                        
                        short resultado=0;
                        
                        if(operando.equals("or")){
                            resultado = this.toEntero(this.oR(binOrigen, binOperand));                     
                        }else if(operando.equals("and")){
                            resultado = this.toEntero(this.aNd(binOrigen, binOperand));                     
                        }else if(operando.equals("xor")){
                            resultado = this.toEntero(this.xOr(binOrigen, binOperand));                     
                        }
                        
                        matrizResultado[i][j] = resultado;
                    }
                
                }
                
                imagenResultado.setMatrizGris(matrizResultado);
                
            }else{
            }
            
            
        }else
            JOptionPane.showMessageDialog(null, "Las Imagenes no son Compatibles");
        
        return imagenResultado;
    }
    
    public Imagen suma (Imagen imagenOriginal, Imagen imagenOperando){
        Imagen imagenResultado = new Imagen(); //Ojo aqui EROOR
        imagenResultado = imagenOriginal.clone();
        if(imagenOriginal.getFormato().equals(imagenOperando.getFormato()) && imagenOriginal.getNivelIntensidad()==imagenOperando.getNivelIntensidad() && (imagenOriginal.getM()== imagenOperando.getM()) && (imagenOriginal.getN()== imagenOperando.getN())){
           short [][] matrizResultado = new short[imagenResultado.getMatrizGris().length][imagenResultado.getMatrizGris()[0].length];
           for (int i = 0; i < matrizResultado.length; i++) {
               for (int j = 0; j < matrizResultado[0].length; j++) {
                   short suma = (short) (imagenOriginal.getMatrizGris()[i][j] + imagenOperando.getMatrizGris()[i][j]);
                   if(suma > imagenOriginal.getNivelIntensidad()) {
                       matrizResultado[i][j] = (short)imagenOriginal.getNivelIntensidad();
                   } else {
                       matrizResultado[i][j] = suma;
                   }
               }
           }
           imagenResultado.setMatrizGris(matrizResultado);  
        }
        
        return imagenResultado;
    }
    
    public Imagen suma (Imagen imagenOriginal, int cantidad){
        Imagen imagenResultado = new Imagen(); //Ojo aqui EROOR
        imagenResultado = imagenOriginal.clone();
        
        
        if(imagenOriginal.getFormato().equals("P2") ){
           short [][] matrizResultado = new short[imagenResultado.getMatrizGris().length][imagenResultado.getMatrizGris()[0].length];
           for (int i = 0; i < matrizResultado.length; i++) {
               for (int j = 0; j < matrizResultado[0].length; j++) {
                   short suma = (short)(imagenOriginal.getMatrizGris()[i][j] + cantidad);
                   if(suma > imagenOriginal.getNivelIntensidad()) {
                       matrizResultado[i][j] = (short)imagenOriginal.getNivelIntensidad();
                   } else {
                       matrizResultado[i][j] = suma;
                   }
               }
           }
           imagenResultado.setMatrizGris(matrizResultado);  
        }
        
        return imagenResultado;
    }
    
    
    /**
     * 
     * @param Origen
     * @param Operando 
     * @return 
     */ 
    private  char [] oR (char [] Origen, char [] Operando){
    
        char [] salida = new char[Origen.length];
        for(int i = 0 ; i < salida.length ; i++){
            if(Origen[i]=='1'||Operando[i]=='1'){
                salida[i]='1';
            }else
                salida[i]='0';
        }
//        for(int i=0; i<salida.length; i++){
//            System.out.print(salida[i]+" ");
//        } 
        return salida;
    }
    
    
    /**
     * 
     * @param Origen
     * @param Operando
     * @return 
     */
    private char [] aNd (char [] Origen, char [] Operando){
    
        char [] salida = new char[Origen.length];
        for(int i = 0 ; i < salida.length ; i++){
            if(Origen[i]=='0'||Operando[i]=='0'){
                salida[i]='0';
            }else
                salida[i]='1';
        }

        /*System.out.println("Origen");
        for(int i=0; i < Origen.length; i++){
            System.out.print(Origen[i]+" ");
        }
        
        System.out.println();
        System.out.println("Operando");
        for(int i=0; i<Operando.length; i++){
            System.out.print(Operando[i]+" ");
        }

                
        System.out.println();
        System.out.println("Salida");
        for(int i=0; i<salida.length; i++){
            System.out.print(salida[i]+" ");
        }
        System.out.println();*/
        return salida;
    }
    
    
    /**
     * 
     * @param Origen
     * @param Operando
     * @return 
     */
    private char [] xOr (char [] Origen, char [] Operando){
    
        char [] salida = new char[Origen.length];
        for(int i = 0 ; i < salida.length ; i++){
            if((Origen[i]=='0'&& Operando[i]=='0')||(Origen[i]=='1'&& Operando[i]=='1')){
                salida[i]='0';
            }else
                salida[i]='1';
        }        
//        for(int i=0; i<salida.length; i++){
//            System.out.print(salida[i]+" ");
//        }     
        return salida;
    }    
    
    
    /**
     * 
     * @param Origen
     * @return 
     */
    private char [] nOt (char [] Origen){
        char [] salida = new char[Origen.length];
        for(int i = 0 ; i < salida.length ; i++){
            if((Origen[i]=='0')){
                salida[i]='1';
            }else
                salida[i]='0';
        }
//        for(int i=0; i<salida.length; i++){
//            System.out.print(salida[i]+" ");
//        }
        return salida;
    }
    
    /**
     * Metodo : encargado de convertir un numero binario almacenado en un arreglo en un numero decimal tipo entero
     * @param entrada arreglo tipo char que representa un numero binario, el 
     * tamaño del arreglo representa la cantida de bits correspondiente al numero binario
     * @return retorna un short (la conversion de un numero binario a decimal). 
     */
    
    private short toEntero(char [] entrada){
        short resultado=0;
        int j=entrada.length-1;
        for(int i = 0; i < entrada.length; i++){
            if(entrada[i]=='1')
                resultado += (short) Math.pow(2, j);
            j--;
        }    
        return resultado;
    }
    
    
    public Imagen resta(Imagen imgFuente, int cantidad) {
        Imagen imgResultado = new Imagen();
        imgResultado = imgFuente.clone();
        
        short[][] matrizFuente = imgFuente.getMatrizGris();
        short[][] matrizResultado = new short[imgFuente.getN()][imgFuente.getM()];
        
        if(imgFuente.getFormato().equals("P2")) {
            for(int i = 0; i < matrizResultado.length; i++) {
                for(int j = 0; j < matrizResultado[0].length; j++) {
                    short pixelResultado = (short)(matrizFuente[i][j] - cantidad);
                    if(pixelResultado < 0) {
                        matrizResultado[i][j] = 0;
                    } else {
                        matrizResultado[i][j] = pixelResultado;
                    }
                }
            }
        }
        
        imgResultado.setMatrizGris(matrizResultado);
        
        return imgResultado;
    }
    
    public Imagen resta(Imagen imgFuente, Imagen imgOperando) {
        Imagen imgResultado = new Imagen();
        imgResultado = imgFuente.clone();
        
        short[][] matrizFuente = imgFuente.getMatrizGris();
        short[][] matrizOperando = imgOperando.getMatrizGris();
        short[][] matrizResultado = new short[imgFuente.getN()][imgFuente.getM()];
        
        if(imgFuente.getFormato().equals("P2")) {
            for(int i = 0; i < matrizResultado.length; i++) {
                for(int j = 0; j < matrizResultado[0].length; j++) {
                    short pixelResultado = (short)(matrizFuente[i][j] - matrizOperando[i][j]);
                    if(pixelResultado < 0) {
                        matrizResultado[i][j] = 0;
                    } else {
                        matrizResultado[i][j] = pixelResultado;
                    }
                }
            }
        }
        
        imgResultado.setMatrizGris(matrizResultado);
        
        return imgResultado;
    }
    
    public Imagen producto(Imagen imgFuente, int cantidad) {
        Imagen imgResultado = new Imagen();
        imgResultado = imgFuente.clone();
        
        short[][] matrizFuente = imgFuente.getMatrizGris();
        short[][] matrizResultado = new short[imgFuente.getN()][imgFuente.getM()];
        
        if(imgFuente.getFormato().equals("P2")) {
            for(int i = 0; i < matrizResultado.length; i++) {
                for(int j = 0; j < matrizResultado[0].length; j++) {
                    short pixelResultado = (short)(matrizFuente[i][j] * cantidad);
                    if(pixelResultado > 255) {
                        matrizResultado[i][j] = 255;
                    } else {
                        matrizResultado[i][j] = pixelResultado;
                    }
                }
            }
        }
        
        imgResultado.setMatrizGris(matrizResultado);
        
        return imgResultado;
    }
    
    public Imagen producto(Imagen imgFuente, Imagen imgOperando) {
        Imagen imgResultado = new Imagen();
        imgResultado = imgFuente.clone();
        
        short[][] matrizFuente = imgFuente.getMatrizGris();
        short[][] matrizOperando = imgOperando.getMatrizGris();
        short[][] matrizResultado = new short[imgFuente.getN()][imgFuente.getM()];
        
        if(imgFuente.getFormato().equals("P2")) {
            for(int i = 0; i < matrizResultado.length; i++) {
                for(int j = 0; j < matrizResultado[0].length; j++) {
                    short pixelResultado = (short)(matrizFuente[i][j] * matrizOperando[i][j]);
                    if(pixelResultado > 255 || pixelResultado < 0) {
                        matrizResultado[i][j] = 255;
                    } else {
                        matrizResultado[i][j] = pixelResultado;
                    }
                }
            }
        }
        
        imgResultado.setMatrizGris(matrizResultado);
        
        return imgResultado;
    }
    
    
    public Imagen traslacion(Imagen imgFuente, double factX, double factY) {
        Imagen imgResultado = new Imagen();
        imgResultado = imgFuente.clone();
        
        short[][] matrizResultado = new short[imgFuente.getN()][imgFuente.getM()];
        short[][] matrizFuente = imgFuente.getMatrizGris();
        
        if(imgFuente.getFormato().equals("P2")) {
            for(int i = 0; i < matrizFuente.length; i++) {
                for(int j = 0; j < matrizFuente[0].length; j++) {
                    int iPrima = (int)(i + factX);
                    int jPrima = (int)(j + factY);
                    short pixelFuente = matrizFuente[i][j];
                    if(iPrima < 0) {
                        iPrima = 0;
                    }
                    if(jPrima < 0) {
                        jPrima = 0;
                    }
                    if(iPrima < matrizFuente.length && jPrima < matrizFuente[0].length) {
                        matrizResultado[iPrima][jPrima] = pixelFuente;
                    }
                }
            }
        }
        
        imgResultado.setMatrizGris(matrizResultado);
        
        return imgResultado;
    }
    
    
    public Imagen reflexionX(Imagen imgFuente) {
        Imagen imgResultado = new Imagen();
        imgResultado = imgFuente.clone();
        
        short[][] matrizResultado = new short[imgFuente.getN()][imgFuente.getM()];
        short[][] matrizFuente = imgFuente.getMatrizGris();
        
        if(imgFuente.getFormato().equals("P2")) {
            for(int i = 0; i < matrizFuente.length; i++) {
                for(int j = 0; j < matrizFuente[0].length; j++) {
                    short pixelFuente = matrizFuente[i][j];
                    int jPrima = (matrizFuente[0].length - 1) - j;
                    matrizResultado[i][jPrima] = pixelFuente;
                }
            }
        }
        
        imgResultado.setMatrizGris(matrizResultado);
        
        return imgResultado;
    }
    
    
    public Imagen reflexionY(Imagen imgFuente) {
        Imagen imgResultado = new Imagen();
        imgResultado = imgFuente.clone();
        
        short[][] matrizResultado = new short[imgFuente.getN()][imgFuente.getM()];
        short[][] matrizFuente = imgFuente.getMatrizGris();
        
        if(imgFuente.getFormato().equals("P2")) {
            for(int i = 0; i < matrizFuente.length; i++) {
                for(int j = 0; j < matrizFuente[0].length; j++) {
                    short pixelFuente = matrizFuente[i][j];
                    int iPrima = (matrizFuente.length - 1) - i;
                    matrizResultado[iPrima][j] = pixelFuente;
                }
            }
        }
        
        imgResultado.setMatrizGris(matrizResultado);
        
        return imgResultado;
    }
    
    
    public Imagen media(Imagen imgFuente, int cantidad) {
        Imagen imgResultado = new Imagen();
        imgResultado = imgFuente.clone();
        
        short[][] matrizResultado = new short[imgFuente.getN()][imgFuente.getM()];
        short[][] matrizFuente = imgFuente.getMatrizGris();
        
        if(imgFuente.getFormato().equals("P2")) {
            for(int i = 0; i < matrizFuente.length; i++) {
                for(int j = 0; j < matrizFuente[0].length; j++) {
                    short pixelFuente = matrizFuente[i][j];
                    matrizResultado[i][j] = (short)((pixelFuente + cantidad) / 2);
                }
            }
        }
        
        imgResultado.setMatrizGris(matrizResultado);
        
        return imgResultado;
    }
    
    public Imagen media(Imagen imgFuente, Imagen imgOperando) {
        Imagen imgResultado = new Imagen();
        imgResultado = imgFuente.clone();
        
        short[][] matrizFuente = imgFuente.getMatrizGris();
        short[][] matrizOperando = imgOperando.getMatrizGris();
        short[][] matrizResultado = new short[imgFuente.getN()][imgFuente.getM()];
        
        if(imgFuente.getFormato().equals("P2")) {
            for(int i = 0; i < matrizFuente.length; i++) {
                for(int j = 0; j < matrizFuente[0].length; j++) {
                    short pixelFuente = matrizFuente[i][j];
                    short pixelOperando = matrizOperando[i][j];
                    matrizResultado[i][j] = (short)((pixelFuente + pixelOperando) / 2);
                }
            }
        }
        
        imgResultado.setMatrizGris(matrizResultado);
        
        return imgResultado;
    }
    
    public Imagen maximo(Imagen imgFuente, int cantidad) {
        Imagen imgResultado = new Imagen();
        imgResultado = imgFuente.clone();
        
        short[][] matrizResultado = new short[imgFuente.getN()][imgFuente.getM()];
        short[][] matrizFuente = imgFuente.getMatrizGris();
        
        if(imgFuente.getFormato().equals("P2")) {
            for(int i = 0; i < matrizFuente.length; i++) {
                for(int j = 0; j < matrizFuente[0].length; j++) {
                    short pixelFuente = matrizFuente[i][j];
                    if(pixelFuente < cantidad) {
                        matrizResultado[i][j] = (short)cantidad;
                    } else {
                        matrizResultado[i][j] = pixelFuente;
                    }
                }
            }
        }
        
        imgResultado.setMatrizGris(matrizResultado);
        
        return imgResultado;
    }
    
    public Imagen maximo(Imagen imgFuente, Imagen imgOperando) {
        Imagen imgResultado = new Imagen();
        imgResultado = imgFuente.clone();
        
        short[][] matrizFuente = imgFuente.getMatrizGris();
        short[][] matrizOperando = imgOperando.getMatrizGris();
        short[][] matrizResultado = new short[imgFuente.getN()][imgFuente.getM()];
        
        if(imgFuente.getFormato().equals("P2")) {
            for(int i = 0; i < matrizFuente.length; i++) {
                for(int j = 0; j < matrizFuente[0].length; j++) {
                    short pixelFuente = matrizFuente[i][j];
                    short pixelOperando = matrizOperando[i][j];
                    if(pixelFuente < pixelOperando) {
                        matrizResultado[i][j] = pixelOperando;
                    } else {
                        matrizResultado[i][j] = pixelFuente;
                    }
                }
            }
        }
        
        imgResultado.setMatrizGris(matrizResultado);
        
        return imgResultado;
    }
    
    
    public Imagen minimo(Imagen imgFuente, int cantidad) {
        Imagen imgResultado = new Imagen();
        imgResultado = imgFuente.clone();
        
        short[][] matrizResultado = new short[imgFuente.getN()][imgFuente.getM()];
        short[][] matrizFuente = imgFuente.getMatrizGris();
        
        if(imgFuente.getFormato().equals("P2")) {
            for(int i = 0; i < matrizFuente.length; i++) {
                for(int j = 0; j < matrizFuente[0].length; j++) {
                    short pixelFuente = matrizFuente[i][j];
                    if(pixelFuente < cantidad) {
                        matrizResultado[i][j] = pixelFuente;
                    } else {
                        matrizResultado[i][j] = (short)cantidad;
                    }
                }
            }
        }
        
        imgResultado.setMatrizGris(matrizResultado);
        
        return imgResultado;
    }
    
    
    public Imagen minimo(Imagen imgFuente, Imagen imgOperando) {
        Imagen imgResultado = new Imagen();
        imgResultado = imgFuente.clone();
        
        short[][] matrizFuente = imgFuente.getMatrizGris();
        short[][] matrizOperando = imgOperando.getMatrizGris();
        short[][] matrizResultado = new short[imgFuente.getN()][imgFuente.getM()];
        
        if(imgFuente.getFormato().equals("P2")) {
            for(int i = 0; i < matrizFuente.length; i++) {
                for(int j = 0; j < matrizFuente[0].length; j++) {
                    short pixelFuente = matrizFuente[i][j];
                    short pixelOperando = matrizOperando[i][j];
                    if(pixelFuente < pixelOperando) {
                        matrizResultado[i][j] = pixelFuente;
                    } else {
                        matrizResultado[i][j] = pixelOperando;
                    }
                }
            }
        }
        
        imgResultado.setMatrizGris(matrizResultado);
        
        return imgResultado;
    }
    
}
