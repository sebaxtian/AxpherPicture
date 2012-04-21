/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagen;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.media.imageioimpl.plugins.pnm.PNMImageReader;
import java.awt.image.Raster;
import java.io.*;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.imageio.plugins.dcm.DicomImageReadParam;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.tool.dcm2jpg.Dcm2Jpg;
import org.dcm4che2.util.TagUtils;

/**
 *
 * @author juansrs
 */


public class DicomImg {
    
    //Atributos de clase
    private DicomObject objDicom;
    private File archivoDicom;
    private Imagen objImagen;
    private Raster rasterDicom;
    
    public DicomImg(String rutaArchivo) {
        DicomInputStream din = null;
        try {
            archivoDicom = new File(rutaArchivo);
            din = new DicomInputStream(archivoDicom);
            objDicom = din.readDicomObject();
            leerRasterDicom();
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        finally {
            try {
                din.close();
            }
            catch (IOException ignore) {
            }
        }
    }
    
    public void listDicomHeader(DicomObject dcmObj) {
        Iterator<DicomElement> iter = dcmObj.datasetIterator();
        while(iter.hasNext()) {
            DicomElement element = iter.next();
            int tag = element.tag();
            try {
                String tagName = dcmObj.nameOf(tag);
                String tagAddr = TagUtils.toString(tag);
                String tagVR = dcmObj.vrOf(tag).toString();
                if (tagVR.equals("SQ")) {
                    if (element.hasItems()) {
                        System.out.println(tagAddr +" ["+  tagVR +"] "+ tagName);
                        listDicomHeader(element.getDicomObject());
                        continue;
                    }
                }
                String tagValue = dcmObj.getString(tag);
                System.out.println(tagAddr +" ["+ tagVR +"] "+ tagName +" ["+ tagValue+"]");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private int getNumBits() {
        Iterator<DicomElement> itera = objDicom.datasetIterator();
        //busca el numero de bits de la imagen dicom
        String tagValue;
        String tagName;
        int numBits = 0;
        while(itera.hasNext()) {
            DicomElement dcmElement = itera.next();
            int tag = dcmElement.tag();
            tagName = objDicom.nameOf(tag);
            tagValue = objDicom.getString(tag);
            if(tagName.equals("Bits Stored")) {
                numBits = Integer.parseInt(tagValue);
                System.out.println(tagName+" ["+tagValue+"]");
            }
        }
        return numBits;
    }
    
    private void leerRasterDicom() {
        try {
            Iterator<ImageReader> iter = ImageIO.getImageReadersByFormatName("DICOM");
            ImageReader reader = (ImageReader)iter.next();
            DicomImageReadParam param = (DicomImageReadParam) reader.getDefaultReadParam();
            ImageInputStream iis = ImageIO.createImageInputStream(archivoDicom);
            reader.setInput(iis, false);
            rasterDicom = reader.readRaster(0, param);
            if(rasterDicom == null) {
                System.out.println("No se pudo crear raster de imagen dicom");
            }
        } catch (IOException ex) {
            System.err.println("Erro al leer archivo dicom");
        }
    }
    
    public Imagen getImagen() {
        int bandas = rasterDicom.getNumBands();
        if(bandas == 1) {
            return getImagenGris();
        }
        if(bandas == 3) {
            return getImagenRGB();
        }
        return objImagen;
    }
    
    private Imagen getImagenGris() {
        int alto = rasterDicom.getHeight();
        int ancho = rasterDicom.getWidth();
        int minX = rasterDicom.getMinX();
        int minY = rasterDicom.getMinY();
        objImagen = new Imagen();
        objImagen.setFormato("P2");
        objImagen.setM(ancho);
        objImagen.setN(alto);
        objImagen.setNivelIntensidad((int)Math.pow(2, getNumBits())-1);
        short matrizGris[][] = new short[alto][ancho];
        for(int i = minY; i < alto; i++) {
            for(int j = minX; j < ancho; j++) {
                short pixel = (short)rasterDicom.getSample(i, j, 0);
                matrizGris[i][j] = pixel;
                /*// Air
                if(pixel == -1000) {
                    matrizGris[i][j] = 0;
                }
                // Lung
                else if(pixel == -300) {
                    matrizGris[i][j] = 100;
                }
                // Fat
                else if(pixel <= -20 && pixel >= -100) {
                    matrizGris[i][j] = (short)((pixel * -1) - 10);
                }
                // Water
                else if(pixel == 0) {
                    matrizGris[i][j] = 128;
                }
                // Cerebrospinal Fluid
                if(pixel >= 0 && pixel <= 22) {
                    matrizGris[i][j] = (short)(pixel + 128);
                }
                // Heart
                if(pixel >= 23 && pixel <= 24) {
                    matrizGris[i][j] = (short)(pixel + 128);
                }
                // White Matter
                if(pixel >= 24 && pixel <= 36) {
                    matrizGris[i][j] = (short)(pixel + 128);
                }
                // Gray Matter
                if(pixel >= 32 && pixel <= 44) {
                    matrizGris[i][j] = (short)(pixel + 128);
                }
                // Blood
                if(pixel >= 42 && pixel <= 58) {
                    matrizGris[i][j] = (short)(pixel + 128);
                }
                // Muscle
                if(pixel >= 44 && pixel <= 59) {
                    matrizGris[i][j] = (short)(pixel + 128);
                }
                // Liver
                if(pixel >= 50 && pixel <= 80) {
                    matrizGris[i][j] = (short)(pixel + 128);
                }
                // Hemorrhage
                if(pixel >= 60 && pixel <= 110) {
                    matrizGris[i][j] = (short)(pixel + 128);
                }
                // Bone
                if(pixel >= 110) {
                    matrizGris[i][j] = 255;
                }*/
            }
        }
        objImagen.setMatrizGris(matrizGris);
        return objImagen;
    }
    
    private Imagen getImagenRGB() {
        return objImagen;
    }
    
    public DicomObject getDicomObject() {
        return objDicom;
    }
    
    public void guardarJPEG() {
        /*OutputStream output = null;
        try {
            File myJpegFile = new File("ImgProcesado/cosa1.jpg");
            output = new BufferedOutputStream(new FileOutputStream(myJpegFile));
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(output);
            encoder.encode(rasterDicom);
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(DicomImg.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ImageFormatException ex) {
            Logger.getLogger(DicomImg.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                output.close();
            } catch (IOException ex) {
                Logger.getLogger(DicomImg.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
        File myJpegFile = new File("ImgProcesado/cosa2.jpg");
        Dcm2Jpg obj = new Dcm2Jpg();
        try {
            obj.convert(archivoDicom, myJpegFile);
        } catch (IOException ex) {
            Logger.getLogger(DicomImg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
