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
public class Resolucion {
    
        // Atributos de clase
    private Imagen objImagen;
    
    public Resolucion(Imagen objImagen) {
        this.objImagen = objImagen;
    }
    
    /**
     * 
     * @param bits : corresponde la cantidad de bits(niveles en escala de grises)
     * que se desea en la imagen resultante estos debes ser segun los estandares 1,3,4,5,8,10,12,14 
     */
    public void cuantizar(int bits){
    
        boolean estado = false;
        switch(bits){
        
            case 1:
                estado = true;
                break;
            
            case 3:
                estado = true;
                break;
                
            case 4:
                estado = true;
                break;    
            
            case 5:
                estado = true;
                break;
                
            case 7:
                estado = true;
                break;
                
            case 8:
                estado = true;
                break;
           
            case 10:
                estado = true;
                break;
                
           case 12:
               estado = true;
                break;
               
           case 14:
               estado = true;
                break;
            default:
                JOptionPane.showMessageDialog(null,"la cantidas de bits " +bits+ " no corresponde al estandar ");
                break;
        }
        
        if (estado){
            int intensidadOriginal = this.getObjImagen().getNivelIntensidad();
            double intensidadNueva = Math.pow(2, bits);
            double factor = (intensidadOriginal + 1) / intensidadNueva; //ojo AQUI
            
            if(this.getObjImagen().getFormato().equals("P2")){ 
                short [][] matrizGris = this.getObjImagen().getMatrizGris();
                
                for (int i = 0; i < this.getObjImagen().getMatrizGris().length; i++) {
                    for (int j = 0; j < this.getObjImagen().getMatrizGris()[0].length; j++) {
                        matrizGris[i][j] = (short) Math.ceil(matrizGris[i][j] / factor); //OJO aqui
                    }
                }
                
                this.getObjImagen().setMatrizGris(matrizGris);
            }
        }
    
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


