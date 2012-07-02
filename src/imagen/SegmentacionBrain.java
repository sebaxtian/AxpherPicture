/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagen;

import java.awt.image.Raster;
import java.awt.image.WritableRaster;


/**
 *
 * @author juansrs
 */



public class SegmentacionBrain {
    
    // Atributos de clase
    private DcmImg objDcmImg;
    private Imagen imagenDcm;
    
    public SegmentacionBrain() {
        
    }
    
    public SegmentacionBrain(Imagen objImagen) {
        
    }
    
    public SegmentacionBrain(DcmImg objDcmImg) {
        this.objDcmImg = objDcmImg;
        this.imagenDcm = objDcmImg.getImagenMR(0, 0);
    }
    
    public void guardaImagen(String ruta) {
        this.imagenDcm.guardarImagen(ruta);
    }
    
    public void segmentarMateriaBlanca() {
        int alto = objDcmImg.getRasterDicom().getHeight();
        int ancho = objDcmImg.getRasterDicom().getWidth();
        int minX = objDcmImg.getRasterDicom().getMinX();
        int minY = objDcmImg.getRasterDicom().getMinY();
        
        Raster objRaster = objDcmImg.getRasterDicom();
        WritableRaster wrtRaster = objRaster.createCompatibleWritableRaster();
        
        // busca la materia blanca
        for (int i = minY; i < alto; i++) {
            for (int j = minX; j < ancho; j++) {
                int x = objDcmImg.getRasterDicom().getSample(j, i, 0);
                
            }
        }
        
        objDcmImg.setRasterDicom(wrtRaster);
    }
    
    public DcmImg getDcmImg() {
        return objDcmImg;
    }
}
