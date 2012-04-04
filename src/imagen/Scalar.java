
package imagen;

import javax.swing.JOptionPane;

/**
 * Definicion de la clase Scalar Permite la reducion o ampliacion por factor (%)
 * para una imagen Los valores del tamaño de la imagen por pixel son almacenados
 * en una matriz.
 *
 * @author Jhon Javier Cardona Muñoz @Fecha lun abr 2 22:31:13 COT 2012
 * @version 0.1
 */
public class Scalar {

    // para imagenes tipo P2
    private short[][] matrizEscalada = null;
    private short[][] matrizOriginal = null;
    //para imagenes tipo P3
    private short matrizR[][] = null;
    private short matrizG[][] = null;
    private short matrizB[][] = null;
    private short matrizRoriginal[][] = null;
    private short matrizGoriginal[][] = null;
    private short matrizBoriginal[][] = null;
    //*************************
    private double factor = 1.0;
    //private Imagen imagenEscalada;
    private char constructor = ' ';

    /**
     * Metodo constructor por defecto 1, Asigina el tamaño de los atributos de
     * la nueva imagen escalada y la imagen original, a la matriz de la imagen
     * escalada se llena con valores 0
     *
     * @param imagen matriz de la imagen (tipo short) que se quiere
     * redimencionar
     * @param escalar factor de porcentaje (tipo double) que se desea escalar
     * (0.0 - 1.0 --> 0 % - 100%, > 1.0 porcentaje de ampliacion)
     */
    public Scalar(short[][] imagen, double escalar) {
        if (escalar <= 0) {
            JOptionPane.showMessageDialog(null, "El escalar debe ser mayor a cero");
        } else {
            this.matrizEscalada = new short[(int) Math.floor(escalar * imagen.length)][(int) Math.floor(escalar * imagen[0].length)];
            for (int i = 0; i < this.matrizEscalada.length; i++) {
                for (int j = 0; j < this.matrizEscalada[0].length; j++) {
                    this.matrizEscalada[i][j] = 0;
                }
            }
            this.matrizOriginal = imagen;
            this.factor = escalar;
            this.constructor = '1';
        }
    }

    /**
     * Metodo constructor por defecto 2, Asigina el tamaño de los atributos de
     * la nueva imagen escalada y la imagen original, a la matriz de la imagen
     * escalada se llena con valores 0
     *
     * @param imagen matriz de la imagen original (tipo entero) que se quiere
     * redimencionar
     * @param pixeles tamaño que tendra en pixeles la nueva imagen escalada
     * (tipo short) siendo pixeles el tamaño de cada lado (tamaño:
     * pixeles*pixeles)
     */
    public Scalar(short[][] imagen, int pixeles) {
        if (pixeles <= 0) {
            JOptionPane.showMessageDialog(null, "los pixeles deben ser mayores a cero");
        } else {
            double escalar;
            //se asume que la imagen original es de n x n
            if (imagen.length == imagen[0].length) {
                escalar = (double) pixeles / (double) imagen.length;
            } //en caso contrario se toma la fila o columna que sea contenga mas pixeles
            else {
                int mayorPixeles;
                if (imagen.length > imagen[0].length) {
                    mayorPixeles = imagen.length;
                } else {
                    mayorPixeles = imagen[0].length;
                }
                escalar = pixeles / mayorPixeles;
            }

            this.matrizEscalada = new short[(int) Math.floor(escalar * imagen.length)][(int) Math.floor(escalar * imagen[0].length)];
            for (int i = 0; i < this.matrizEscalada.length; i++) {
                for (int j = 0; j < this.matrizEscalada[0].length; j++) {
                    this.matrizEscalada[i][j] = 0;
                }
            }
            this.matrizOriginal = imagen;
            //JOptionPane.showMessageDialog(null, "escalar = "+escalar+ " factor ="+factor );
            this.factor = escalar;
            this.constructor = '1';
        }
    }

    /**
     * Metodo constructor por defecto 3, Asigina el tamaño de los atributos de
     * la nueva imagen escalada y la imagen original, a la matriz o matrices de
     * la imagen escalada se llena con valores 0
     *
     * @param imagen imagen (tipo Imagen) que se quiere redimencionar
     * @param escalar factor de porcentaje (tipo double) que se desea escalar
     * (0.0 - 1.0 --> 0 % - 100%, > 1.0 porcentaje de ampliacion)
     */
    public Scalar(Imagen imagen, double escalar) {
        if (escalar <= 0) {
            JOptionPane.showMessageDialog(null, "El escalar debe ser mayor a cero");
        } else {
            //si el formato de la imagen es PGM
            if (imagen.getFormato().equals("P2")) {
                this.matrizEscalada = new short[(int) Math.floor(escalar * imagen.getMatrizGris().length)][(int) Math.floor(escalar * imagen.getMatrizGris()[0].length)];
                for (int i = 0; i < this.matrizEscalada.length; i++) {
                    for (int j = 0; j < this.matrizEscalada[0].length; j++) {
                        this.matrizEscalada[i][j] = 0;
                    }
                }

                this.matrizOriginal = imagen.getMatrizGris();
                this.factor = escalar;
                this.constructor = '1';
            } //si el formato de la imagen es PPM
            else if (imagen.getFormato().equals("P3")) {
                this.matrizR = new short[(int) Math.floor(escalar * imagen.getMatrizR().length)][(int) Math.floor(escalar * imagen.getMatrizR()[0].length)];
                for (int i = 0; i < this.matrizR.length; i++) {
                    for (int j = 0; j < this.matrizR[0].length; j++) {
                        this.matrizR[i][j] = 0;
                    }
                }

                this.matrizG = new short[(int) Math.floor(escalar * imagen.getMatrizG().length)][(int) Math.floor(escalar * imagen.getMatrizG()[0].length)];
                for (int i = 0; i < this.matrizG.length; i++) {
                    for (int j = 0; j < this.matrizG[0].length; j++) {
                        this.matrizG[i][j] = 0;
                    }
                }

                this.matrizB = new short[(int) Math.floor(escalar * imagen.getMatrizB().length)][(int) Math.floor(escalar * imagen.getMatrizB()[0].length)];
                for (int i = 0; i < this.matrizB.length; i++) {
                    for (int j = 0; j < this.matrizB[0].length; j++) {
                        this.matrizB[i][j] = 0;
                    }
                }
                this.matrizBoriginal = imagen.getMatrizB();
                this.matrizGoriginal = imagen.getMatrizG();
                this.matrizRoriginal = imagen.getMatrizR();
                this.factor = escalar;
                this.constructor = '2';
            } else {
                System.out.println("Error formato traza: Scalar linea 154");
            }
            //JOptionPane.showMessageDialog(null, "Error formato");

        }
    }

    /**
     * Metodo constructor por defecto 3, Asigina el tamaño de los atributos de
     * la nueva imagen escalada y la imagen original, a la matriz o matrices de
     * la imagen escalada se llena con valores 0
     *
     * @param imagen imagen (tipo Imagen) que se quiere redimencionar
     * @param escalar factor de porcentaje (tipo double) que se desea escalar
     * (0.0 - 1.0 --> 0 % - 100%, > 1.0 porcentaje de ampliacion)
     */
    public Scalar(Imagen imagen, int pixeles) {
        if (pixeles <= 0) {
            JOptionPane.showMessageDialog(null, "Los pixeles deben ser mayores a cero");
        } else {
            double escalar = 1.0;
            //se asume que la imagen original es de n x n
            if (imagen.getM() == imagen.getN()) {
                escalar = (double) pixeles / (double) imagen.getM();
            } //en caso contrario se toma la fila o columna que sea contenga mas pixeles
            else {
                int mayorPixeles;
                if (imagen.getN() > imagen.getM()) {
                    mayorPixeles = imagen.getN();
                } else {
                    mayorPixeles = imagen.getM();
                }

                escalar = pixeles / mayorPixeles;
            }

            //si el formato de la imagen es PGM
            if (imagen.getFormato().equals("P2")) {
                this.matrizEscalada = new short[(int) Math.floor(escalar * imagen.getMatrizGris().length)][(int) Math.floor(escalar * imagen.getMatrizGris()[0].length)];
                for (int i = 0; i < this.matrizEscalada.length; i++) {
                    for (int j = 0; j < this.matrizEscalada[0].length; j++) {
                        this.matrizEscalada[i][j] = 0;
                    }
                }

                this.matrizOriginal = imagen.getMatrizGris();
                this.factor = escalar;
                this.constructor = '1';
            } //si el formato de la imagen es PPM
            else if (imagen.getFormato().equals("P3")) {
                this.matrizR = new short[(int) Math.floor(escalar * imagen.getMatrizR().length)][(int) Math.floor(escalar * imagen.getMatrizR()[0].length)];
                for (int i = 0; i < this.matrizR.length; i++) {
                    for (int j = 0; j < this.matrizR[0].length; j++) {
                        this.matrizR[i][j] = 0;
                    }
                }

                this.matrizG = new short[(int) Math.floor(escalar * imagen.getMatrizG().length)][(int) Math.floor(escalar * imagen.getMatrizG()[0].length)];
                for (int i = 0; i < this.matrizG.length; i++) {
                    for (int j = 0; j < this.matrizG[0].length; j++) {
                        this.matrizG[i][j] = 0;
                    }
                }

                this.matrizB = new short[(int) Math.floor(escalar * imagen.getMatrizB().length)][(int) Math.floor(escalar * imagen.getMatrizB()[0].length)];
                for (int i = 0; i < this.matrizB.length; i++) {
                    for (int j = 0; j < this.matrizB[0].length; j++) {
                        this.matrizB[i][j] = 0;
                    }
                }

                this.matrizBoriginal = imagen.getMatrizB();
                this.matrizGoriginal = imagen.getMatrizG();
                this.matrizRoriginal = imagen.getMatrizR();
                this.factor = escalar;
                this.constructor = '2';
            } else {
                JOptionPane.showMessageDialog(null, "Error formato");
            }
        }
    }

    /*
     * Metodo encargado de realizar la escalacion de una imagen utilizando
     * interpolacion bicubica y lineal
     */
    public void escalacionBicubica() {
        //switch(this.constructor){
        //este es el caso de que la imagen sea de un formato P2 (escala de grises)
        if (this.constructor == '1') {
            for (int i = 0; i < this.matrizEscalada.length; i++) {
                for (int j = 0; j < this.matrizEscalada[0].length; j++) {
                    //System.out.println("i = "+i+ " j="+j);
                    this.matrizEscalada[i][j] = this.matrizOriginal[(int) Math.floor(i / this.factor)][(int) Math.floor(j / this.factor)];
                    //this.matrizEscalada[(int) Math.floor(i * this.factor)][(int) Math.floor(j * this.factor)] = this.matrizOriginal[i][j];
                }
            }
            // Ojo esto sirve para mejorar la imagen por medio de interpolacion
//                if(this.factor>1){
//                    for (int i = 0; i < this.matrizEscalada.length; i++) {
//                        for (int j = 0; j < this.matrizEscalada[0].length; j++) {
//                            if (this.matrizEscalada[i][j] == 0) {
//                                //JOptionPane.showConfirmDialog(null, "Revisar " +i+" "+j);
//                                matrizEscalada[i][j] = interpolacionExterna(matrizEscalada,i, j);
//                            }
//                        }
//                        for (int j = 0; j < this.matrizEscalada[0].length; j++) {
//                            if (this.matrizEscalada[i][j] == 0) {
//                                //JOptionPane.showConfirmDialog(null, "Revisar " +i+" "+j);
//                                matrizEscalada[i][j] = interpolacionInterna(matrizEscalada,i, j);
//                            }
//                        }
//                    }
//                }else {
//                
//                }              

        } //Este es el caso  de que la imagen sea de formato P3 (RGB)
        else if (this.constructor == '2') {

            for (int i = 0; i < this.matrizR.length; i++) {
                for (int j = 0; j < this.matrizR[0].length; j++) {
                    //this.matrizR[(int) Math.floor(i * this.factor)][(int) Math.floor(j * this.factor)] = this.matrizOriginal[i][j];
                    this.matrizR[i][j] = this.matrizRoriginal[(int) Math.floor(i / this.factor)][(int) Math.floor(j / this.factor)];
                }
            }

            for (int i = 0; i < this.matrizG.length; i++) {
                for (int j = 0; j < this.matrizG[0].length; j++) {
                    this.matrizG[i][j] = this.matrizGoriginal[(int) Math.floor(i / this.factor)][(int) Math.floor(j / this.factor)];
                }
            }

            for (int i = 0; i < this.matrizB.length; i++) {
                for (int j = 0; j < this.matrizB[0].length; j++) {
                    this.matrizB[i][j] = this.matrizBoriginal[(int) Math.floor(i / this.factor)][(int) Math.floor(j / this.factor)];
                }
            }

            // Ojo esto puede servir para mejorar la imagen por interpolacion
//                if(this.factor>1){
//                    //interpolacion de la matriz con el componente RED
//
//                    for (int i = 0; i < this.matrizR.length; i++) {
//                        for (int j = 0; j < this.matrizR[0].length; j++) {
//                            if (this.matrizR[i][j] == 0) {
//                                //JOptionPane.showConfirmDialog(null, "Revisar " +i+" "+j);
//                                this.matrizR[i][j] = interpolacionExterna(matrizR, i, j);
//                            }
//                        }
//                        
//                        for (int j = 0; j < this.matrizR[0].length; j++) {
//                            if (this.matrizR[i][j] == 0) {
//                                //JOptionPane.showConfirmDialog(null, "Revisar " +i+" "+j);
//                                this.matrizR[i][j] = interpolacionInterna(this.matrizR, i, j);
//                            }
//                        }
//                    }
//                    
//                    //interpolacion de la matriz con el componente GREN
//                    for (int i = 0; i < this.matrizG.length; i++) {
//                        for (int j = 0; j < this.matrizG[0].length; j++) {
//                            if (this.matrizG[i][j] == 0) {
//                                //JOptionPane.showConfirmDialog(null, "Revisar " +i+" "+j);
//                                matrizG[i][j] = interpolacionExterna(matrizG, i, j);
//                            }
//                        }
//                        for (int j = 0; j < this.matrizG[0].length; j++) {
//                            if (this.matrizG[i][j] == 0) {
//                                //JOptionPane.showConfirmDialog(null, "Revisar " +i+" "+j);
//                                matrizG[i][j] = interpolacionInterna(matrizG, i, j);
//                            }
//                        }
//                    }
//                    
//                    //interpolacion de la componente BLUE
//                    for (int i = 0; i < this.matrizB.length; i++) {
//                        for (int j = 0; j < this.matrizB[0].length; j++) {
//                            if (this.matrizB[i][j] == 0) {
//                                //JOptionPane.showConfirmDialog(null, "Revisar " +i+" "+j);
//                                matrizB[i][j] = interpolacionExterna(matrizB, i, j);
//                            }
//                        }
//                        for (int j = 0; j < this.matrizB[0].length; j++) {
//                            if (this.matrizB[i][j] == 0) {
//                                //JOptionPane.showConfirmDialog(null, "Revisar " +i+" "+j);
//                                matrizB[i][j] = interpolacionInterna(matrizB, i, j);
//                            }
//                        }
//                    }
//                    
//                    
//                }else {
//                
//                }

        } else {
            System.out.println("Verficar el tipo de formato " + this.constructor + "  traza: Scalar linea 349");
        }
        //JOptionPane.showMessageDialog(null, "Verficar el tipo de formato "+this.constructor);        
        //}
    }

    /**
     *
     * metodo que realiza interpolacion lineal o bicubica para la parte interna
     * de una matriz
     *
     * @param matrizEscalada matriz a la cual se le realiza la interpolacion
     * @param y coordena que hace referencia a la fila del parametro
     * matrizEscalada
     * @param x coordena que hace referencia a la columna del parametro
     * matrizEscalada
     * @return short valor resultante que arroja la interpolacion de la
     * matrizEscalada en las coordenas (y,x)
     */
    private short interpolacionInterna(short[][] matrizEscalada, int y, int x) {
        int suma = matrizEscalada[y][x];
        if (y > 0 && y < matrizEscalada.length - 1 && x > 0 && x < matrizEscalada[0].length - 1 && matrizEscalada[y][x] == 0) {
            suma = (matrizEscalada[y][x - 1] + matrizEscalada[y - 1][x - 1] + matrizEscalada[y - 1][x] + matrizEscalada[y - 1][x + 1] + matrizEscalada[y][x + 1] + matrizEscalada[y][x + 1] + matrizEscalada[y][x + 1] + matrizEscalada[y][x + 1]) / 8;
        }
        //matrizEscalada[y][x] = (short)suma;  
        return (short) suma;
    }

    /**
     *
     * metodo que realiza interpolacion lineal o bicubica para bordes de una
     * matriz
     *
     * @param matrizEscalada matriz a la cual se le realiza la interpolacion
     * @param y coordena que hace referencia a la fila del parametro
     * matrizEscalada
     * @param x coordena que hace referencia a la columna del parametro
     * matrizEscalada
     * @return short valor resultante que arroja la interpolacion de la
     * matrizEscalada en las coordenas (y,x)
     */
    private short interpolacionExterna(short[][] matrizEscalada, int y, int x) {
        int suma = 0;
        if (x == 0) {
            if (y == 0) {
                suma = (matrizEscalada[y + 1][x] + matrizEscalada[y + 1][x + 1] + matrizEscalada[y][x + 1]) / 3;
            } else if (y == matrizEscalada.length - 1) {
                suma = (matrizEscalada[y - 1][x] + matrizEscalada[y - 1][x + 1] + matrizEscalada[y][x + 1]) / 3;
            } else {
                suma = (matrizEscalada[y - 1][x] + matrizEscalada[y - 1][x + 1] + matrizEscalada[y][x + 1] + matrizEscalada[y + 1][x + 1] + matrizEscalada[y + 1][x]) / 5;
            }
        } else if (x == matrizEscalada[0].length - 1) {
            if (y == 0) {
                suma = (matrizEscalada[y][x - 1] + matrizEscalada[y + 1][x - 1] + matrizEscalada[y + 1][x]) / 3;
            } else if (y == matrizEscalada.length - 1) {
                suma = (matrizEscalada[y][x - 1] + matrizEscalada[y - 1][x - 1] + matrizEscalada[y - 1][x]) / 3;
            } else {
                suma = (matrizEscalada[y - 1][x] + matrizEscalada[y - 1][x - 1] + matrizEscalada[y][x - 1] + matrizEscalada[y + 1][x - 1] + matrizEscalada[y + 1][x]) / 5;
            }
        } else if (y == 0) {
            if (x == 0) {
                suma = (matrizEscalada[y + 1][x] + matrizEscalada[y + 1][x + 1] + matrizEscalada[y][x + 1]) / 3;
            } else if (x == matrizEscalada[0].length - 1) {
                suma = (matrizEscalada[y][x - 1] + matrizEscalada[y + 1][x - 1] + matrizEscalada[y + 1][x]) / 3;
            } else {
                suma = (matrizEscalada[y][x - 1] + matrizEscalada[y + 1][x - 1] + matrizEscalada[y][x + 1] + matrizEscalada[y + 1][x + 1] + matrizEscalada[y][x + 1]) / 5;
            }
        } else if (y == matrizEscalada.length - 1) {
            if (x == 0) {
                suma = (matrizEscalada[y - 1][x] + matrizEscalada[y - 1][x + 1] + matrizEscalada[y][x + 1]) / 3;
            } else if (x == matrizEscalada[0].length - 1) {
                suma = (matrizEscalada[y][x - 1] + matrizEscalada[y - 1][x - 1] + matrizEscalada[y - 1][x]) / 3;
            } else {
                suma = (matrizEscalada[y][x - 1] + matrizEscalada[y - 1][x - 1] + matrizEscalada[y - 1][x] + matrizEscalada[y - 1][x + 1] + matrizEscalada[y][x + 1]) / 5;
            }
        } else {
//            int suma = (entrada[y][x-1] + entrada[y-1][x-1] + entrada[y-1][x] +entrada[y-1][x+1] + entrada[y][x+1] + entrada[y][x+1] + entrada[y][x+1] + entrada[y][x+1])/8;    
//            entrada[y][x] = (int)suma;  
        }
        return (short) suma;
    }

    public static void imprimirM(short[][] entrada) {
        for (int i = 0; i < entrada.length; i++) {
            for (int j = 0; j < entrada[0].length; j++) {
                System.out.print(entrada[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public static void imprimirA(short[] entrada) {
        for (int i = 0; i < entrada.length; i++) {
            System.out.println(entrada[i] + " ");
        }
    }

    /**
     * @return the imagenEscalada
     */
    public short[][] getImagenEscalada() {
        return matrizEscalada;
    }

    /**
     * @param imagenEscalada the imagenEscalada to set
     */
    public void setImagenEscalada(short[][] imagenEscalada) {
        this.matrizEscalada = imagenEscalada;
    }

    /**
     * @return the imagenOriginal
     */
    public short[][] getImagenOriginal() {
        return matrizOriginal;
    }

    /**
     * @return the matrizR
     */
    public short[][] getMatrizR() {
        return matrizR;
    }

    /**
     * @return the matrizG
     */
    public short[][] getMatrizG() {
        return matrizG;
    }

    /**
     * @return the matrizB
     */
    public short[][] getMatrizB() {
        return matrizB;
    }
}