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
    
    public SegmentacionBrain() {
        
    }
    
    public SegmentacionBrain(Imagen objImagen) {
        
    }
    
    public SegmentacionBrain(DcmImg objDcmImg) {
        this.objDcmImg = objDcmImg;
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
                //System.out.println(""+x);
                if(x < 700) {
                    wrtRaster.setSample(j, i, 0, 0);
                } else {
                    wrtRaster.setSample(j, i, 0, x);
                }
                
            }
        }
        
        objDcmImg.setRasterDicom(wrtRaster);
    }
    
    public DcmImg getDcmImg() {
        return objDcmImg;
    }
}
