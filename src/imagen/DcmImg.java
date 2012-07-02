/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagen;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.dcm4che2.data.*;
import org.dcm4che2.imageio.plugins.dcm.DicomImageReadParam;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.io.DicomOutputStream;
import org.dcm4che2.tool.jpg2dcm.Jpg2Dcm;
import org.dcm4che2.util.TagUtils;
import org.dcm4che2.util.UIDUtils;

/**
 *
 * @author juansrs
 */
public class DcmImg {

    // Atributos de clase
    private File archivoDcm;
    private DicomObject dcmObj;
    private Raster rasterDicom;
    private int tagBits;
    private short Ymin = 0;
    private short Ymax = 255;
    public static double pixelSpacing;

    /**
     * Metodo contructor que recibe como argumento la ruta hacia el archivo
     * DICOM, Construye un objeto Dicom apartir del archivo.
     *
     * @param rutaArchivo
     */
    public DcmImg(String rutaArchivo) {
        DicomInputStream din = null;
        try {
            archivoDcm = new File(rutaArchivo);
            din = new DicomInputStream(archivoDcm);
            //din = new DicomInputStream(new BufferedInputStream(new FileInputStream(rutaArchivo)), TransferSyntax.ImplicitVRLittleEndian);
            dcmObj = din.readDicomObject();
            // obtiene un objeto raster de la imagen de un archivo Dicom
            rasterizarDicom(archivoDcm);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            try {
                din.close();
            } catch (IOException ignore) {
            }
        }
    }

    /**
     * Este metodo permite obtener un objeto raster de la imagen de un archivo
     * Dicom.
     *
     * @param archivoDcm
     */
    private void rasterizarDicom(File archivoDcm) {
        try {
            Iterator<ImageReader> iter = ImageIO.getImageReadersByFormatName("DICOM");
            ImageReader reader = (ImageReader) iter.next();
            DicomImageReadParam param = (DicomImageReadParam) reader.getDefaultReadParam();
            ImageInputStream iis = ImageIO.createImageInputStream(archivoDcm);
            reader.setInput(iis, false);
            rasterDicom = reader.readRaster(0, param);
            if (rasterDicom == null) {
                System.err.println("No se pudo crear raster de imagen dicom");
            }
        } catch (IOException ex) {
            System.err.println("Erro al leer archivo dicom");
        }
    }

    /**
     * Metodo que permite imprimir los headers de algun objeto Dicom.
     *
     * @param dcmObj
     */
    public void printHeaders(DicomObject dcmObj) {
        Iterator<DicomElement> iter = dcmObj.datasetIterator();
        while (iter.hasNext()) {
            DicomElement element = iter.next();
            int tag = element.tag();
            try {
                String tagName = dcmObj.nameOf(tag);
                String tagAddr = TagUtils.toString(tag);
                String tagVR = dcmObj.vrOf(tag).toString();
                if (tagVR.equals("SQ")) {
                    if (element.hasItems()) {
                        System.out.println(tagAddr + " [" + tagVR + "] " + tagName);
                        printHeaders(element.getDicomObject());
                        continue;
                    }
                }
                String tagValue = dcmObj.getString(tag);
                System.out.println(tagAddr + " [" + tagVR + "] " + tagName + " [" + tagValue + "]");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Este metodo permite obtener el tipo de estudio que representa el objeto
     * dicom.
     *
     * @return tipoEstudio
     */
    public String getEstudio() {
        Iterator<DicomElement> itera = dcmObj.datasetIterator();
        //busca tipo de estudio Rx, TAC, mamografía, ecografía, ultrasonido, etc.
        String tagValue;
        String tagName;
        String tagAddr;
        String estudio = "";
        while (itera.hasNext()) {
            DicomElement dcmElement = itera.next();
            int tag = dcmElement.tag();
            tagName = dcmObj.nameOf(tag);
            tagAddr = TagUtils.toString(tag);
            if (tagAddr.equals("(0008,0060)")) {
                tagValue = dcmObj.getString(tag);
                estudio = tagValue;
                System.out.println(tagName + " [" + tagValue + "]");
            }
        }
        return estudio;
    }

    /**
     * Este metodo permite obtener el numero de Bits que son utilizados para
     * construir la imagen Dicom.
     *
     * @return numBits
     */
    public int getNumBits() {
        Iterator<DicomElement> itera = dcmObj.datasetIterator();
        //busca el numero de bits de la imagen dicom
        String tagValue;
        String tagName;
        String tagAddr;
        int numBits = 0;
        while (itera.hasNext()) {
            DicomElement dcmElement = itera.next();
            int tag = dcmElement.tag();
            tagName = dcmObj.nameOf(tag);
            tagAddr = TagUtils.toString(tag);
            if (tagAddr.equals("(0028,0100)")) {
                tagValue = dcmObj.getString(tag);
                tagBits = tag;
                numBits = Integer.parseInt(tagValue);
                System.out.println(tagName + " [" + tagValue + "]");
            }
        }
        return numBits;
    }
    
    public double getPixelSpacing() {
        Iterator<DicomElement> itera = dcmObj.datasetIterator();
        //busca el numero de bits de la imagen dicom
        String tagValue;
        String tagName;
        String tagAddr;
        double pixelSpacing = 0;
        while (itera.hasNext()) {
            DicomElement dcmElement = itera.next();
            int tag = dcmElement.tag();
            tagName = dcmObj.nameOf(tag);
            tagAddr = TagUtils.toString(tag);
            if (tagAddr.equals("(0028,0030)")) {
                tagValue = dcmObj.getString(tag);
                tagBits = tag;
                pixelSpacing = Double.parseDouble(tagValue);
                System.out.println(tagName + " [" + tagValue + "]");
            }
        }
        return pixelSpacing;
    }
    
    public int getWindowCenter() {
        Iterator<DicomElement> itera = dcmObj.datasetIterator();
        //busca el window center de la imagen dicom
        String tagValue;
        String tagName;
        String tagAddr;
        int windowCenter = 0;
        while (itera.hasNext()) {
            DicomElement dcmElement = itera.next();
            int tag = dcmElement.tag();
            tagName = dcmObj.nameOf(tag);
            tagAddr = TagUtils.toString(tag);
            if (tagAddr.equals("(0028,1050)")) {
                tagValue = dcmObj.getString(tag);
                tagBits = tag;
                windowCenter = Integer.parseInt(tagValue);
                System.out.println(tagName + " [" + tagValue + "]");
            }
        }
        return windowCenter;
    }
    
    public int getWindowWidth() {
        Iterator<DicomElement> itera = dcmObj.datasetIterator();
        //busca el window width de la imagen dicom
        String tagValue;
        String tagName;
        String tagAddr;
        int windowWidth = 0;
        while (itera.hasNext()) {
            DicomElement dcmElement = itera.next();
            int tag = dcmElement.tag();
            tagName = dcmObj.nameOf(tag);
            tagAddr = TagUtils.toString(tag);
            if (tagAddr.equals("(0028,1051)")) {
                tagValue = dcmObj.getString(tag);
                tagBits = tag;
                windowWidth = Integer.parseInt(tagValue);
                System.out.println(tagName + " [" + tagValue + "]");
            }
        }
        return windowWidth;
    }

    public Imagen getImagen() {
        int bandas = rasterDicom.getNumBands();
        if (bandas == 1) {
            return getImagenGris();
        }
        if (bandas == 3) {
            return getImagenRGB();
        }
        return null;
    }

    private Imagen getImagenGris() {
        Imagen imgObj = new Imagen();
        int alto = rasterDicom.getHeight();
        int ancho = rasterDicom.getWidth();
        int minX = rasterDicom.getMinX();
        int minY = rasterDicom.getMinY();
        imgObj.setFormato("P2");
        imgObj.setM(ancho);
        imgObj.setN(alto);
        imgObj.setNivelIntensidad((int) Math.pow(2, getNumBits()) - 1);//32,767
        imgObj.setNivelIntensidad(32767);
        imgObj.setArchivoImagen(archivoDcm);
        short matrizGris[][] = new short[alto][ancho];
        for (int i = minY; i < alto; i++) {
            for (int j = minX; j < ancho; j++) {
                short pixel = (short) rasterDicom.getSample(j, i, 0);
                matrizGris[i][j] = pixel;
            }
        }
        imgObj.setMatrizGris(matrizGris);

        return imgObj;
    }
    
    public Imagen getImagenOT() {
        Imagen imgObj = new Imagen();
        int alto = rasterDicom.getHeight();
        int ancho = rasterDicom.getWidth();
        int minX = rasterDicom.getMinX();
        int minY = rasterDicom.getMinY();
        imgObj.setFormato("P2");
        imgObj.setM(ancho);
        imgObj.setN(alto);
        imgObj.setNivelIntensidad((int) Math.pow(2, getNumBits()) - 1);
        imgObj.setArchivoImagen(archivoDcm);
        short matrizGris[][] = new short[alto][ancho];
        for (int i = minY; i < alto; i++) {
            for (int j = minX; j < ancho; j++) {
                short pixel = (short) rasterDicom.getSample(j, i, 0);
                matrizGris[i][j] = pixel;
            }
        }
        imgObj.setMatrizGris(matrizGris);

        return imgObj;
    }
    
    public Imagen getImagenMR(int windowCenter, int windowWidth) {
        Imagen imgObj = new Imagen();
        int alto = rasterDicom.getHeight();
        int ancho = rasterDicom.getWidth();
        int minX = rasterDicom.getMinX();
        int minY = rasterDicom.getMinY();
        imgObj.setFormato("P2");
        imgObj.setM(ancho);
        imgObj.setN(alto);
        //imgObj.setNivelIntensidad((int) Math.pow(2, getNumBits()) - 1);
        imgObj.setNivelIntensidad(255);
        imgObj.setArchivoImagen(archivoDcm);
        short matrizGris[][] = new short[alto][ancho];
        if(windowCenter == 0){
            windowCenter = getWindowCenter();
        }
        if(windowWidth == 0){
            windowWidth = getWindowWidth();
        }
        short y;
        for (int i = minY; i < alto; i++) {
            for (int j = minX; j < ancho; j++) {
                short x = (short) rasterDicom.getSample(j, i, 0);
                //System.out.println(""+x);
                if(x <= windowCenter - 0.5 - (windowWidth - 1) / 2 ){
                    y = Ymin;
                }
                else if(x > windowCenter - 0.5 + (windowWidth - 1) / 2 ) {
                    y = Ymax;
                }
                else {
                    y = (short) ( ( (x - (windowCenter - 0.5) ) / (windowWidth - 1) + 0.5 ) * ( Ymax - Ymin ) + Ymin );
                }
                
                matrizGris[i][j] = y;
            }
        }
        imgObj.setMatrizGris(matrizGris);
        
        return imgObj;
    }
    
    public Imagen getImagenCR(int windowCenter, int windowWidth) {
        Imagen imgObj = new Imagen();
        int alto = rasterDicom.getHeight();
        int ancho = rasterDicom.getWidth();
        int minX = rasterDicom.getMinX();
        int minY = rasterDicom.getMinY();
        imgObj.setFormato("P2");
        imgObj.setM(ancho);
        imgObj.setN(alto);
        //imgObj.setNivelIntensidad((int) Math.pow(2, getNumBits()) - 1);
        imgObj.setNivelIntensidad(255);
        imgObj.setArchivoImagen(archivoDcm);
        short matrizGris[][] = new short[alto][ancho];
        if(windowCenter == 0){
            windowCenter = getWindowCenter();
        }
        if(windowWidth == 0){
            windowWidth = getWindowWidth();
        }
        short y;
        for (int i = minY; i < alto; i++) {
            for (int j = minX; j < ancho; j++) {
                short x = (short) rasterDicom.getSample(j, i, 0);
                //System.out.println(""+x);
                if(x <= windowCenter - 0.5 - (windowWidth - 1) / 2 ){
                    y = Ymin;
                }
                else if(x > windowCenter - 0.5 + (windowWidth - 1) / 2 ) {
                    y = Ymax;
                }
                else {
                    y = (short) ( ( (x - (windowCenter - 0.5) ) / (windowWidth - 1) + 0.5 ) * ( Ymax - Ymin ) + Ymin );
                }
                
                matrizGris[i][j] = (short)(255 - y);
            }
        }
        imgObj.setMatrizGris(matrizGris);
        
        return imgObj;
    }

    private Imagen getImagenRGB() {
        Imagen imgObj = new Imagen();
        int alto = rasterDicom.getHeight();
        int ancho = rasterDicom.getWidth();
        int minX = rasterDicom.getMinX();
        int minY = rasterDicom.getMinY();
        imgObj.setFormato("P3");
        imgObj.setM(ancho);
        imgObj.setN(alto);
        imgObj.setNivelIntensidad((int) Math.pow(2, getNumBits()) - 1);
        short matrizR[][] = new short[alto][ancho];
        short matrizG[][] = new short[alto][ancho];
        short matrizB[][] = new short[alto][ancho];
        for (int i = minY; i < alto; i++) {
            for (int j = minX; j < ancho; j++) {
                short pixelR = (short) rasterDicom.getSample(j, i, 0);
                short pixelG = (short) rasterDicom.getSample(j, i, 1);
                short pixelB = (short) rasterDicom.getSample(j, i, 2);
                matrizR[i][j] = pixelR;
                matrizG[i][j] = pixelG;
                matrizB[i][j] = pixelB;
            }
        }
        imgObj.setMatrizR(matrizR);
        imgObj.setMatrizG(matrizG);
        imgObj.setMatrizB(matrizB);

        return imgObj;
    }

    public Imagen getImgVentana(int ventana, int nivel) {
        Imagen imgObj = new Imagen();

        return imgObj;
    }

    /**
     * Obtiene el objeto tipo Dicom.
     *
     * @return dcmObj
     */
    public DicomObject getDicomObject() {
        return dcmObj;
    }

    public void setImagen(Imagen imgObj) {
        int bandas = rasterDicom.getNumBands();
        String formato = imgObj.getFormato();
        if (bandas == 1 && formato.equals("P2")) {
            // Imagenes Grises
            setImagenGris(imgObj);
        } else if (bandas == 3 && formato.equals("P3")) {
            // Imagenes RGB
            setImagenRGB(imgObj);
        } else {
            System.err.println("El formato de la imagen no es compatible con el objeto DcmImg");
        }
    }

    /**
     * Metodo que modifica la imagen de un objeto dicom por los valores del
     * objeto imagen que recibe como argumento.
     *
     * @param imgObj
     */
    private void setImagenGris(Imagen imgObj) {
        WritableRaster wrtRaster = rasterDicom.createCompatibleWritableRaster();
        int alto = rasterDicom.getHeight();
        int ancho = rasterDicom.getWidth();
        int minX = rasterDicom.getMinX();
        int minY = rasterDicom.getMinY();
        int nivelIntensidad = imgObj.getNivelIntensidad();
        for (int i = minY; i < alto; i++) {
            for (int j = minX; j < ancho; j++) {
                short pixel = imgObj.getMatrizGris()[i][j];
                wrtRaster.setSample(j, i, 0, pixel);
            }
        }
        int numBits = (int) (Math.log10(nivelIntensidad + 1) / Math.log10(2));
        System.out.println("Numero de Bits: " + numBits);
        dcmObj.putInt(tagBits, VR.US, numBits);
        rasterDicom = wrtRaster.createChild(0, 0, ancho, alto, 0, 0, new int[]{0});
    }

    /**
     * Metodo que modifica la imagen de un objeto dicom por los valores del
     * objeto imagen que recibe como argumento.
     *
     * @param imgObj
     */
    private void setImagenRGB(Imagen imgObj) {
        WritableRaster wrtRaster = rasterDicom.createCompatibleWritableRaster();
        int alto = rasterDicom.getHeight();
        int ancho = rasterDicom.getWidth();
        int minX = rasterDicom.getMinX();
        int minY = rasterDicom.getMinY();
        int nivelIntensidad = imgObj.getNivelIntensidad();
        for (int i = minY; i < alto; i++) {
            for (int j = minX; j < ancho; j++) {
                short pixelR = imgObj.getMatrizR()[i][j];
                short pixelG = imgObj.getMatrizG()[i][j];
                short pixelB = imgObj.getMatrizB()[i][j];
                wrtRaster.setSample(j, i, 0, pixelR);
                wrtRaster.setSample(j, i, 1, pixelG);
                wrtRaster.setSample(j, i, 2, pixelB);
            }
        }
        int numBits = (int) (Math.log10(nivelIntensidad + 1) / Math.log10(2));
        System.out.println("Numero de Bits: " + numBits);
        dcmObj.putInt(tagBits, VR.US, numBits);
        rasterDicom = wrtRaster.createChild(0, 0, ancho, alto, 0, 0, new int[]{0});
    }

    public void guardaX(String rutaArchivo) {
        File f = new File(rutaArchivo);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        DicomOutputStream dos = new DicomOutputStream(bos);
        try {
            dcmObj.putString(Tag.TransferSyntaxUID, VR.UI, UID.ImplicitVRLittleEndian);
            dos.writeDicomFile(dcmObj);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            try {
                dos.close();
            } catch (IOException ignore) {
            }
        }
    }

    public void guardarDcmImg(String rutaArchivo) {
        File archivoDicom = new File(rutaArchivo);
        File jpeg = getJPEG();
        Jpg2Dcm jpg2dcm = new Jpg2Dcm();
        try {
            jpg2dcm.convert(jpeg, archivoDicom);
        } catch (IOException ex) {
            System.err.println("Error al construir archivo DICOM");
        }
    }
    
    public void guardaY(String rutaArchivo) {
        File archivoJPEG = getJPEG();
        File archivoDICOM = new File(rutaArchivo);
        jpeg2dicom(archivoJPEG, archivoDICOM);
    }

    /**
     * Este metodo obtiene un archivo JPEG de la imagen
     * del objeto Dicom.
     * 
     * @return archivoJPEG
     */
    private File getJPEG() {
        OutputStream output = null;
        File auxDcm = null;
        try {
            auxDcm = new File("/tmp/auxDcm.jpg");
            output = new BufferedOutputStream(new FileOutputStream(auxDcm));
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(output);
            encoder.encode(rasterDicom);
            output.close();
        } catch (IOException ex) {
            System.err.println("Error al crear archivo auxiliar JPEG");
        } catch (ImageFormatException ex) {
            System.err.println("Error al codificar raster dicom a JPEG");
        } finally {
            try {
                output.close();
            } catch (IOException ex) {
                System.err.println("Error al cerrar el archivo auxiliar JPEG");
            }
        }
        return auxDcm;
    }
    
    
    private void jpeg2dicom(File archivoJPEG, File archivoDICOM) {
        try {
            BufferedImage jpegImage = ImageIO.read(archivoJPEG);
            if(jpegImage == null) {
                System.err.println("No se pudo crear imagen de archivo JPEG");
            }
            //atributos de imagen
            int colorComponents = jpegImage.getColorModel().getNumColorComponents();
            int bitsPerPixel = jpegImage.getColorModel().getPixelSize();
            int bitsAllocated = (bitsPerPixel / colorComponents);
            int samplesPerPixel = colorComponents;
            //crea un nuevo objeto dicom
            DicomObject dicom = new BasicDicomObject();
            dicom.putString(Tag.SpecificCharacterSet, VR.CS, "ISO_IR 100");
            dicom.putString(Tag.PhotometricInterpretation, VR.CS, samplesPerPixel == 3 ? "YBR_FULL_422" : "MONOCHROME2");
            dicom.putInt(Tag.SamplesPerPixel, VR.US, samplesPerPixel);       
            //valores del header obligatorios
            dicom.putInt(Tag.Rows, VR.US, jpegImage.getHeight());
            dicom.putInt(Tag.Columns, VR.US, jpegImage.getWidth());
            dicom.putInt(Tag.BitsAllocated, VR.US, bitsAllocated);
            dicom.putInt(Tag.BitsStored, VR.US, bitsAllocated);
            dicom.putInt(Tag.HighBit, VR.US, bitsAllocated-1);
            dicom.putInt(Tag.PixelRepresentation, VR.US, 0);
            dicom.putDate(Tag.InstanceCreationDate, VR.DA, new Date());
            dicom.putDate(Tag.InstanceCreationTime, VR.TM, new Date());
            //cada dicom debe tener un UID
            dicom.putString(Tag.StudyInstanceUID, VR.UI, UIDUtils.createUID());
            dicom.putString(Tag.SeriesInstanceUID, VR.UI, UIDUtils.createUID());
            dicom.putString(Tag.SOPInstanceUID, VR.UI, UIDUtils.createUID());
            //metafile information jpeg encapsulado en el dicom
            dicom.initFileMetaInformation(UID.JPEGBaseline1);
            //terminada la definicion de headers abre un outputstream al archivo dicom
            FileOutputStream fos = new FileOutputStream(archivoDICOM);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            DicomOutputStream dos = new DicomOutputStream(bos);
            dos.writeDicomFile(dicom);
            //configura los headers para la escritura de la imagen JPEG en el dicom
            dos.writeHeader(Tag.PixelData, VR.OB, -1);
            dos.writeHeader(Tag.Item, null, 0);
            /*
                According to Gunter from dcm4che team we have to take care that 
                the pixel data fragment length containing the JPEG stream has 
                an even length.
            */
            int jpgLen = (int) archivoJPEG.length(); 
            dos.writeHeader(Tag.Item, null, (jpgLen+1)&~1);
            FileInputStream fis = new FileInputStream(archivoJPEG);
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);
            //escribe los datos del JPEG en el DICOM
            byte[] buffer = new byte[65536];       
            int b;
            while ((b = dis.read(buffer)) > 0) {
                dos.write(buffer, 0, b);
            }
            /*
                According to Gunter from dcm4che team we have to take care that 
                the pixel data fragment length containing the JPEG stream has 
                an even length. So if needed the line below pads JPEG stream with 
                odd length with 0 byte.
            */
            if ((jpgLen&1) != 0) dos.write(0); 
            dos.writeHeader(Tag.SequenceDelimitationItem, null, 0);
            dos.close();
        } catch (IOException ex) {
            System.err.println("Error al crar buffer en archivo JPEG");
        }
    }
    
    public Raster getRasterDicom() {
        return rasterDicom;
    }
    
    public void setRasterDicom(Raster objRaster) {
        this.rasterDicom = objRaster;
    }
    
    public void guardarImgRaster() {
        Imagen imgObj = new Imagen();
        int alto = rasterDicom.getHeight();
        int ancho = rasterDicom.getWidth();
        int minX = rasterDicom.getMinX();
        int minY = rasterDicom.getMinY();
        imgObj.setFormato("P2");
        imgObj.setM(ancho);
        imgObj.setN(alto);
        imgObj.setNivelIntensidad(255);
        imgObj.setArchivoImagen(archivoDcm);
        short matrizGris[][] = new short[alto][ancho];
        for (int i = minY; i < alto; i++) {
            for (int j = minX; j < ancho; j++) {
                short x = (short) rasterDicom.getSample(j, i, 0);
                matrizGris[i][j] = x;
            }
        }
        imgObj.setMatrizGris(matrizGris);
        //---
        imgObj.guardarImagen("ImgProcesado/rasterImg.pgm");
    }
    
}
