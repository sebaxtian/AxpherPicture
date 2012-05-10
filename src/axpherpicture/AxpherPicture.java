/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package axpherpicture;

import imagen.*;
import org.dcm4che2.data.DicomObject;

/**
 * Clase Main del proyecto AxpherPicture
 * 
 * @author Juan Sebastian Rios Sabogal
 * @Fecha sab mar 31 21:01:59 COT 2012
 * @version 0.1
 */


public class AxpherPicture {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        /**
         * Imagen en formato Dicom
         */
        
        DcmImg objDcmImg = new DcmImg("ImgFuente/ankle.dcm");
        //objDcmImg.guardaY("ImgProcesado/ankle.dcm");
        DicomObject dcmObj;
        dcmObj = objDcmImg.getDicomObject();
        objDcmImg.printHeaders(dcmObj);
        String estudio = objDcmImg.getEstudio();
        if(!estudio.equals("CT") || !estudio.equals("RM")) {
            Imagen imgObj;
            imgObj = objDcmImg.getImagen();
            imgObj.guardarImagen("ImgProcesado/ankle.pgm");
            //-->construye una imagen binaria
            Imagen imgBinariaPGM = new Imagen();
            imgBinariaPGM.setFormato("P2");
            imgBinariaPGM.setNivelIntensidad(1);
            imgBinariaPGM.setN(imgObj.getN());
            imgBinariaPGM.setM(imgObj.getM());
            short matrizGris[][] = new short[imgBinariaPGM.getN()][imgBinariaPGM.getM()];
            for(int i = 0; i < imgBinariaPGM.getN(); i++) {
                for(int j = 0; j < imgBinariaPGM.getM(); j++) {
                    if(imgObj.getMatrizGris()[i][j] < 104) {
                        matrizGris[i][j] = 0;
                    } else {
                        matrizGris[i][j] = 1;
                    }
                }
            }
            imgBinariaPGM.setMatrizGris(matrizGris);
            objDcmImg.setImagen(imgBinariaPGM);
            imgObj = objDcmImg.getImagen();
            imgObj.guardarImagen("ImgProcesado/binariaAnkle.pgm");
            objDcmImg.guardarDcmImg("ImgProcesado/binarioAnkle.dcm");
            objDcmImg.guardaX("ImgProcesado/bi2Ankle.dcm");
            //------
            objDcmImg.guardaY("ImgProcesado/binaria3ankle.dcm");
        }
        
        
        /**
         * Operaciones
         */
        String rutaImgPGM = "ImgFuente/noisy.pgm";
        Imagen imgPGM = new Imagen(rutaImgPGM);

        // --->> Filtros
        FiltroNoise fl = new FiltroNoise(imgPGM);
        fl.filtroSigma((short)100);
        fl.getImagen().guardarImagen("ImgProcesado/sigmaNoisy.pgm");

        fl.filtroMediana(3);
        fl.getImagen().guardarImagen("ImgProcesado/medianaNoisy.pgm");

        fl.nagaoMatsuyama();
        fl.getImagen().guardarImagen("ImgProcesado/nagaoMatsuyamaNoisy.pgm");


        String rutaImgPGM2 = "ImgFuente/madera.pgm";
        Imagen imgPGM2 = new Imagen(rutaImgPGM2);
        String rutaImgPGM1 = "ImgFuente/velas.pgm";
        Imagen imgPGM1 = new Imagen(rutaImgPGM1);

        // --->> Cuantizar
        Cuantizar rs = new Cuantizar(imgPGM1);
        rs.asignarBitsPixel(4);
        rs.getObjImagen().guardarImagen("ImgProcesado/Resolucion7Lena.pgm");

        // -->> Operaciones
        rutaImgPGM2 = "ImgFuente/madera.pgm";
        imgPGM2 = new Imagen(rutaImgPGM2);
        rutaImgPGM1 = "ImgFuente/velas.pgm";
        imgPGM1 = new Imagen(rutaImgPGM1);
        
        Operaciones op = new Operaciones();
        op.OrAndXor(imgPGM1, imgPGM2, "and").guardarImagen("ImgProcesado/maderaAndVelas.pgm");
        op.OrAndXor(imgPGM1, imgPGM2, "xor").guardarImagen("ImgProcesado/maderaXorVelas.pgm");
        op.OrAndXor(imgPGM1, imgPGM2, "or").guardarImagen("ImgProcesado/maderaOrVelas.pgm");
        op.suma(imgPGM1, imgPGM2).guardarImagen("ImgProcesado/maderaSumaVelas.pgm");
        op.suma(imgPGM1, 128).guardarImagen("ImgProcesado/velasSuma128.pgm");
        
        rutaImgPGM2 = "ImgFuente/pills.pgm";
        imgPGM2 = new Imagen(rutaImgPGM2);
        rutaImgPGM1 = "ImgFuente/birs.pgm";
        imgPGM1 = new Imagen(rutaImgPGM1);
        
        op.OrAndXor(imgPGM1, imgPGM2, "and").guardarImagen("ImgProcesado/pillsAndBirs.pgm");
        op.OrAndXor(imgPGM1, imgPGM2, "xor").guardarImagen("ImgProcesado/pillsXorBirs.pgm");
        op.OrAndXor(imgPGM1, imgPGM2, "or").guardarImagen("ImgProcesado/pillsOrBirs.pgm");
        op.suma(imgPGM1, imgPGM2).guardarImagen("ImgProcesado/pillsSumaBirs.pgm");
        op.suma(imgPGM1, 128).guardarImagen("ImgProcesado/birsSuma128.pgm");
        
        
        /*
        DicomImg objDicomImg = new DicomImg("ImgFuente/ankle.dcm");
        System.out.println("Imprime Headers De Imagen Dicom");
        objDicomImg.listDicomHeader(objDicomImg.getDicomObject());
        Imagen objImagen = objDicomImg.getImagen();
        objImagen.guardarImagen("ImgProcesado/ankle.pgm");
        objDicomImg.guardarJPEG();
        */
        
        /**
         * Imagen en formato PGM
         */
        /*String rutaImgPGM = "ImgFuente/lena.pgm";
        Imagen imgPGM = new Imagen(rutaImgPGM);
        Ecualizacion ecualizador = new Ecualizacion(imgPGM);
        imgPGM = ecualizador.ecualizar();
        imgPGM.guardarImagen("ImgProcesado/ecualizadaLena.pgm");
        
        String rutaImgPPM = "ImgFuente/lena.ppm";
        Imagen imgPPM = new Imagen(rutaImgPPM);
        ecualizador = new Ecualizacion(imgPPM);
        imgPPM = ecualizador.ecualizar();
        imgPPM.guardarImagen("ImgProcesado/ecualizadaLena.ppm");*/
        
        /*
        String rutaImgPGM = "ImgFuente/lena.pgm";
        Imagen imgPGM = new Imagen(rutaImgPGM);
        imgPGM.guardarImagen("ImgProcesado/lenaCopia.pgm");
        //-->calcula el histograma
        Histograma histogramaImgPGM = new Histograma(imgPGM);
        Imagen imgHistogramaPGM = histogramaImgPGM.getImagenHistograma();
        imgHistogramaPGM.guardarImagen("ImgProcesado/histogramaLena.pgm");
        //-->calcula el umbral
        Umbralizacion umbralizacionPGM = new Umbralizacion(histogramaImgPGM, 0);
        int umbralGris = umbralizacionPGM.getUmbralGris();
        System.out.println("UmbralGris: "+umbralGris);
        //-->construye una imagen binaria
        Imagen imgBinariaPGM = new Imagen();
        imgBinariaPGM.setFormato("P2");
        imgBinariaPGM.setNivelIntensidad(1);
        imgBinariaPGM.setN(imgPGM.getN());
        imgBinariaPGM.setM(imgPGM.getM());
        short matrizGris[][] = new short[imgBinariaPGM.getN()][imgBinariaPGM.getM()];
        for(int i = 0; i < imgBinariaPGM.getN(); i++) {
            for(int j = 0; j < imgBinariaPGM.getM(); j++) {
                if(imgPGM.getMatrizGris()[i][j] < umbralGris) {
                    matrizGris[i][j] = 0;
                } else {
                    matrizGris[i][j] = 1;
                }
            }
        }
        imgBinariaPGM.setMatrizGris(matrizGris);
        imgBinariaPGM.guardarImagen("ImgProcesado/binariaLena.pgm");
        //aplica escalacion a la imagen
        double factorScalarPGM = 2.25;
        Scalar scalarPGM = new Scalar(imgPGM, factorScalarPGM);
        scalarPGM.escalacionBicubica();
        imgPGM.setMatrizGris(scalarPGM.getImagenEscalada());
        imgPGM.setN(imgPGM.getMatrizGris().length);
        imgPGM.setM(imgPGM.getMatrizGris()[0].length);
        imgPGM.guardarImagen("ImgProcesado/scalarLena2.25X.pgm");
        */
        /**
         * Imagen en formato PPM
         */
        /*
        String rutaImgPPM = "ImgFuente/lena.ppm";
        Imagen imgPPM = new Imagen(rutaImgPPM);
        imgPPM.guardarImagen("ImgProcesado/lenaCopia.ppm");
        //calcula escala de grises
        Imagen imgGrises = imgPPM.getEscalaGrises();
        imgGrises.guardarImagen("ImgProcesado/lenaGrises.pgm");
        //calcula el histograma
        Histograma histogramaImgPPM = new Histograma(imgPPM);
        Imagen imgHistogramaPPM = histogramaImgPPM.getImagenHistograma();
        imgHistogramaPPM.guardarImagen("ImgProcesado/histogramaLena.ppm");
        //calcula el umbral
        //Umbralizacion umbralizacionPPM = new Umbralizacion(histogramaImgPPM, 0);
        Umbralizacion umbralizacionPPM = new Umbralizacion(histogramaImgPPM, 1);
        int umbralR = umbralizacionPPM.getUmbralR();
        int umbralG = umbralizacionPPM.getUmbralG();
        int umbralB = umbralizacionPPM.getUmbralB();
        System.out.println("UmbralR: "+umbralR);
        System.out.println("UmbralG: "+umbralG);
        System.out.println("UmbralB: "+umbralB);
        //construye una imagen binaria
        Imagen imgBinariaPPM = new Imagen();
        imgBinariaPPM.setFormato("P3");
        imgBinariaPPM.setNivelIntensidad(1);
        imgBinariaPPM.setN(imgPPM.getN());
        imgBinariaPPM.setM(imgPPM.getM());
        short matrizR[][] = new short[imgBinariaPPM.getN()][imgBinariaPPM.getM()];
        short matrizG[][] = new short[imgBinariaPPM.getN()][imgBinariaPPM.getM()];
        short matrizB[][] = new short[imgBinariaPPM.getN()][imgBinariaPPM.getM()];
        for(int i = 0; i < imgBinariaPPM.getN(); i++) {
            for(int j = 0; j < imgBinariaPPM.getM(); j++) {
                //canal R
                if(imgPPM.getMatrizR()[i][j] < umbralR) {
                    matrizR[i][j] = 0;
                } else {
                    matrizR[i][j] = 1;
                }
                //canal G
                if(imgPPM.getMatrizG()[i][j] < umbralG) {
                    matrizG[i][j] = 0;
                } else {
                    matrizG[i][j] = 1;
                }
                //canal B
                if(imgPPM.getMatrizB()[i][j] < umbralB) {
                    matrizB[i][j] = 0;
                } else {
                    matrizB[i][j] = 1;
                }
            }
        }
        imgBinariaPPM.setMatrizR(matrizR);
        imgBinariaPPM.setMatrizG(matrizG);
        imgBinariaPPM.setMatrizB(matrizB);
        imgBinariaPPM.guardarImagen("ImgProcesado/binariaLena.ppm");
        //aplica escalacion a la imagen
        double factorScalarPPM = 2.25;
        Scalar scalarPPM = new Scalar(imgPPM, factorScalarPPM);
        scalarPPM.escalacionBicubica();
        imgPPM.setMatrizR(scalarPPM.getMatrizR());
        imgPPM.setMatrizG(scalarPPM.getMatrizG());
        imgPPM.setMatrizB(scalarPPM.getMatrizB());
        imgPPM.setN(imgPPM.getMatrizR().length);
        imgPPM.setM(imgPPM.getMatrizR()[0].length);
        imgPPM.guardarImagen("ImgProcesado/scalarLena2.25X.ppm");
        */
        /*
        String rutaImgPGM = "ImgFuente/noisy.pgm";
        Imagen imgPGM = new Imagen(rutaImgPGM);
        FiltroNoise fl = new FiltroNoise(imgPGM);
        fl.filtroSigma((short)100);
        fl.getImagen().guardarImagen("ImgProcesado/sigmaNoisy.pgm");
        
//        String rutaImgPGM = "ImgFuente/noisy.pgm";
//        Imagen imgPGM = new Imagen(rutaImgPGM);
//       
//        FiltroNoise fl = new FiltroNoise(imgPGM);
//        fl.filtroSigma((short)100);
//        fl.getImagen().guardarImagen("ImgProcesado/sigmaNoisy.pgm");
//        
//        fl.filtroMediana(3);
//        fl.getImagen().guardarImagen("ImgProcesado/medianaNoisy.pgm");
//        
//        fl.nagaoMatsuyama();
//        fl.getImagen().guardarImagen("ImgProcesado/nagaoMatsuyamaNoisy.pgm");
        
        fl.nagaoMatsuyama();
        fl.getImagen().guardarImagen("ImgProcesado/nagaoMatsuyamaNoisy.pgm");
        */
        
    }
}
