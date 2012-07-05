/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controlador;

import gui.*;
import imagen.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.StringTokenizer;
import javax.activation.MimetypesFileTypeMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.dcm4che2.data.DicomObject;

/**
 * Clase controlador para una imagen.
 * 
 * @author sebaxtian
 */


public class ControladorImagen implements ActionListener, ChangeListener {
    
    private AxpherPicture objVentanaAxpherPicture;
    private VentanaHistograma objVentanaHistograma;
    private VentanaSignal objVentanaSignal;
    private PanelUmbralizacion objPanelUmbral;
    private PanelMR objPanelMR;
    private File archivoImagen;
    private Imagen objImagenFuente;
    private Imagen objImagenProcesado;
    private Imagen objImagenAtras;
    private Histograma objHistograma;
    private Umbralizacion objUmbral;
    private FiltroNoise objFiltro;
    private DcmImg objDcmImg;
    public static Segmentacion segmentacionKmeans;
    public static Imagen imgCerebro;
    
    public ControladorImagen(AxpherPicture objVentana) {
        this.objVentanaAxpherPicture = objVentana;
        this.objVentanaHistograma = new VentanaHistograma();
        this.objVentanaSignal = new VentanaSignal();
        this.objVentanaAxpherPicture.menuItemAbrirPGMPPM.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemAbrirDICOM.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemGuardar.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemSalir.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemVerHistograma.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemSignal.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemVerImagen.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemAtras.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemUmbral.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemEcualizar.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemCuantizar.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemSigma.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemMediana.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemMatsuyama.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemGausiano.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemOpAnd.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemOpOr.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemOpXor.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemOpSuma.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemOpResta.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemOpProducto.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemOpTraslacion.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemOpReflexH.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemOpReflexV.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemOpMedia.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemOpMaximo.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemOpMinimo.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemLineH.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemLineV.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemLineHV.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemSalPiper.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemSobel.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemCany.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemAutomatico.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemManual.addActionListener(this);
        this.objVentanaHistograma.btnGuardarHistograma.addActionListener(this);
        this.objVentanaSignal.sliderSignal.addChangeListener(this);
        this.objVentanaAxpherPicture.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Cierra ventana");
                System.exit(0);
            }
        });
    }
    
    private void setAtras() {
        objImagenAtras = new Imagen();
        objImagenAtras.setFormato(objImagenProcesado.getFormato());
        objImagenAtras.setM(objImagenProcesado.getM());
        objImagenAtras.setN(objImagenProcesado.getN());
        objImagenAtras.setNivelIntensidad(objImagenProcesado.getNivelIntensidad());
        short matrizGris[][] = new short[objImagenProcesado.getN()][objImagenProcesado.getM()];
        for(int i = 0; i < matrizGris.length; i++) {
            for(int j = 0; j < matrizGris[0].length; j++) {
                matrizGris[i][j] = objImagenProcesado.getMatrizGris()[i][j];
            }
        }
        objImagenAtras.setMatrizGris(matrizGris);
    }
    
    private void pasoAtras() {
        objImagenProcesado.setFormato(objImagenAtras.getFormato());
        objImagenProcesado.setM(objImagenAtras.getM());
        objImagenProcesado.setN(objImagenAtras.getN());
        objImagenProcesado.setNivelIntensidad(objImagenAtras.getNivelIntensidad());
        short matrizGris[][] = new short[objImagenAtras.getN()][objImagenAtras.getM()];
        for(int i = 0; i < matrizGris.length; i++) {
            for(int j = 0; j < matrizGris[0].length; j++) {
                matrizGris[i][j] = objImagenAtras.getMatrizGris()[i][j];
            }
        }
        objImagenProcesado.setMatrizGris(matrizGris);
        objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenProcesado);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("PGM - PPM")) {
            System.out.println("Abre archivo de imagen PGM - PPM");
            abrirArchivoImagenPGMPPM();
        }
        if(e.getActionCommand().equals("DICOM")) {
            System.out.println("Abre archivo de imagen DICOM");
            abrirArchivoImagenDICOM();
        }
        if(e.getActionCommand().equals("Guardar ...")) {
            System.out.println("Guarda archivo de imagen");
            guardarArchivoImagen();
        }
        if(e.getActionCommand().equals("Salir")) {
            System.out.println("Cierra ventana");
            System.exit(0);
        }
        if(e.getActionCommand().equals("Histograma")) {
            System.out.println("Ver histograma");
            HiloVerHistogramaImagen hiloVerHistogramaImagen = new HiloVerHistogramaImagen();
            hiloVerHistogramaImagen.start();
        }
        if(e.getActionCommand().equals("Signal")) {
            System.out.println("Ver Signal");
            HiloVerSignalImagen hiloVerSignalImagen = new HiloVerSignalImagen();
            hiloVerSignalImagen.start();
        }
        if(e.getActionCommand().equals("Imagen")) {
            System.out.println("Ver imagen");
            verImagenFuente();
        }
        if(e.getActionCommand().equals("Atras")) {
            System.out.println("Atras");
            pasoAtras();
        }
        if(e.getSource().equals(objVentanaHistograma.btnGuardarHistograma)) {
            System.out.println("Guardar histograma");
            guardarArchivoHistograma();
        }
        if(e.getActionCommand().equals("Umbralizar")) {
            System.out.println("Umbralizacion");
            objPanelUmbral = new PanelUmbralizacion();
            objPanelUmbral.btnCalcularDosPicos.addActionListener(this);
            objPanelUmbral.btnCalcularImgBinaria.addActionListener(this);
            objPanelUmbral.btnCalcularIsodata.addActionListener(this);
            objPanelUmbral.btnCalcularOtsu.addActionListener(this);
            objVentanaAxpherPicture.panelOperaciones.removeAll();
            objVentanaAxpherPicture.panelOperaciones.add(objPanelUmbral);
            objVentanaAxpherPicture.pack();
        }
        if(objPanelUmbral != null) {
            if(e.getSource().equals(objPanelUmbral.btnCalcularDosPicos)) {
                HiloUmbralizacion hiloUmbral = new HiloUmbralizacion(0);
                hiloUmbral.start();
                System.out.println("Umbral Dos Picos");
            }
            if(e.getSource().equals(objPanelUmbral.btnCalcularIsodata)) {
                HiloUmbralizacion hiloUmbral = new HiloUmbralizacion(1);
                hiloUmbral.start();
                System.out.println("Umbral Isodata");
            }
            if(e.getSource().equals(objPanelUmbral.btnCalcularOtsu)) {
                HiloUmbralizacion hiloUmbral = new HiloUmbralizacion(2);
                hiloUmbral.start();
                System.out.println("Umbral Otsu");
            }
            if(e.getSource().equals(objPanelUmbral.btnCalcularImgBinaria)) {
                String umbral = objPanelUmbral.textFieldValorUmbral.getText();
                HiloImagenBinaria hiloImgBinaria = new HiloImagenBinaria(umbral);
                hiloImgBinaria.start();
                System.out.println("Imagen Binaria");
            }
        }
        if(e.getActionCommand().equals("Ecualizar")) {
            HiloEcualizacion hiloEcualizacion = new HiloEcualizacion();
            hiloEcualizacion.start();
            System.out.println("Ecualizar");
        }
        if(e.getActionCommand().equals("Cuantizar")) {
            String valor = JOptionPane.showInputDialog(objVentanaAxpherPicture, "Ingrese numero de bits por pixel", "Cuantizar", JOptionPane.INFORMATION_MESSAGE);
            int bitsPixel = Integer.parseInt(valor);
            HiloCuantizar hiloCuantizar = new HiloCuantizar(bitsPixel);
            hiloCuantizar.start();
            System.out.println("Cuantizar");
        }
        if(e.getActionCommand().equals("Sigma")) {
            HiloFiltroNoise hiloFiltroNoise = new HiloFiltroNoise(0);
            hiloFiltroNoise.start();
            System.out.println("Filtro Sigma");
        }
        if(e.getActionCommand().equals("Mediana")) {
            HiloFiltroNoise hiloFiltroNoise = new HiloFiltroNoise(1);
            hiloFiltroNoise.start();
            System.out.println("Filtro Mediana");
        }
        if(e.getActionCommand().equals("Matsuyama")) {
            HiloFiltroNoise hiloFiltroNoise = new HiloFiltroNoise(2);
            hiloFiltroNoise.start();
            System.out.println("Filtro Matsuyama");
        }
        if(e.getActionCommand().equals("Gausiano")) {
            HiloFiltroNoise hiloFiltroNoise = new HiloFiltroNoise(7);
            hiloFiltroNoise.start();
            System.out.println("Filtro Gausiano");
        }
        if(e.getActionCommand().equals("And")) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PGM & PPM", "pgm", "ppm");
            fileChooser.setFileFilter(filter);
            fileChooser.setAcceptAllFileFilterUsed(false);
            int respuesta = fileChooser.showOpenDialog(objVentanaAxpherPicture);
            if(respuesta == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().exists()) {
                File fileImage = fileChooser.getSelectedFile();
                //crea el objeto Imagen
                Imagen objImg = new Imagen(fileImage.getAbsolutePath());
                HiloOperaciones hiloOperaciones = new HiloOperaciones("And");
                hiloOperaciones.setOpImg(objImg);
                hiloOperaciones.start();
                System.out.println("Archivo imagen seleccionado");
            } else {
                System.out.println("No selecciona archivo imagen");
            }
            System.out.println("Operador And");
        }
        if(e.getActionCommand().equals("Or")) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PGM & PPM", "pgm", "ppm");
            fileChooser.setFileFilter(filter);
            fileChooser.setAcceptAllFileFilterUsed(false);
            int respuesta = fileChooser.showOpenDialog(objVentanaAxpherPicture);
            if(respuesta == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().exists()) {
                File fileImage = fileChooser.getSelectedFile();
                //crea el objeto Imagen
                Imagen objImg = new Imagen(fileImage.getAbsolutePath());
                HiloOperaciones hiloOperaciones = new HiloOperaciones("Or");
                hiloOperaciones.setOpImg(objImg);
                hiloOperaciones.start();
                System.out.println("Archivo imagen seleccionado");
            } else {
                System.out.println("No selecciona archivo imagen");
            }
            System.out.println("Operador Or");
        }
        if(e.getActionCommand().equals("Xor")) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PGM & PPM", "pgm", "ppm");
            fileChooser.setFileFilter(filter);
            fileChooser.setAcceptAllFileFilterUsed(false);
            int respuesta = fileChooser.showOpenDialog(objVentanaAxpherPicture);
            if(respuesta == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().exists()) {
                File fileImage = fileChooser.getSelectedFile();
                //crea el objeto Imagen
                Imagen objImg = new Imagen(fileImage.getAbsolutePath());
                HiloOperaciones hiloOperaciones = new HiloOperaciones("Xor");
                hiloOperaciones.setOpImg(objImg);
                hiloOperaciones.start();
                System.out.println("Archivo imagen seleccionado");
            } else {
                System.out.println("No selecciona archivo imagen");
            }
            System.out.println("Operador Xor");
        }
        if(e.getActionCommand().equals("Suma")) {
            int respuesta = JOptionPane.showConfirmDialog(objVentanaAxpherPicture, "Sumar Una Constante?", "Sumar Constante O Imagen",JOptionPane.YES_NO_CANCEL_OPTION);
            // si respuesta es si, suma constante
            if(respuesta == 0) {
                String valor = JOptionPane.showInputDialog(objVentanaAxpherPicture, "Valor", "Sumar Valor A Imagen", JOptionPane.INFORMATION_MESSAGE);
                int constante = Integer.parseInt(valor);
                HiloOperaciones hiloOperaciones = new HiloOperaciones("Suma");
                hiloOperaciones.setOpConst(constante);
                hiloOperaciones.start();
                System.out.println("Si Suma Constante");
            }
            // si respuesta es no, suma imagen
            if(respuesta == 1) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PGM & PPM", "pgm", "ppm");
                fileChooser.setFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);
                int respu = fileChooser.showOpenDialog(objVentanaAxpherPicture);
                if(respu == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().exists()) {
                    File fileImage = fileChooser.getSelectedFile();
                    //crea el objeto Imagen
                    Imagen objImg = new Imagen(fileImage.getAbsolutePath());
                    HiloOperaciones hiloOperaciones = new HiloOperaciones("Suma");
                    hiloOperaciones.setOpImg(objImg);
                    hiloOperaciones.start();
                    System.out.println("Archivo imagen seleccionado");
                } else {
                    System.out.println("No selecciona archivo imagen");
                }
                System.out.println("No Suma Constante");
            }
            System.out.println("Suma "+respuesta);
        }
        if(e.getActionCommand().equals("Resta")) {
            int respuesta = JOptionPane.showConfirmDialog(objVentanaAxpherPicture, "Restar Una Constante?", "Restar Constante O Imagen",JOptionPane.YES_NO_CANCEL_OPTION);
            // si respuesta es si, resta constante
            if(respuesta == 0) {
                String valor = JOptionPane.showInputDialog(objVentanaAxpherPicture, "Valor", "Restar Valor A Imagen", JOptionPane.INFORMATION_MESSAGE);
                int constante = Integer.parseInt(valor);
                // ---------------------------------->
                HiloOperaciones hiloOperaciones = new HiloOperaciones("Resta");
                hiloOperaciones.setOpConst(constante);
                hiloOperaciones.start();
                // ---------------------------------->
                System.out.println("Si Resta Constante");
            }
            // si respuesta es no, resta imagen
            if(respuesta == 1) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PGM & PPM", "pgm", "ppm");
                fileChooser.setFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);
                int respu = fileChooser.showOpenDialog(objVentanaAxpherPicture);
                if(respu == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().exists()) {
                    File fileImage = fileChooser.getSelectedFile();
                    //crea el objeto Imagen
                    Imagen objImg = new Imagen(fileImage.getAbsolutePath());
                    // ------------------------------------>
                    HiloOperaciones hiloOperaciones = new HiloOperaciones("Resta");
                    hiloOperaciones.setOpImg(objImg);
                    hiloOperaciones.start();
                    // ------------------------------------>
                    System.out.println("Archivo imagen seleccionado");
                } else {
                    System.out.println("No selecciona archivo imagen");
                }
                System.out.println("No Resta Constante");
            }
            System.out.println("Resta "+respuesta);
        }
        if(e.getActionCommand().equals("Producto")) {
            int respuesta = JOptionPane.showConfirmDialog(objVentanaAxpherPicture, "Producto Por Una Constante?", "Producto Constante O Imagen",JOptionPane.YES_NO_CANCEL_OPTION);
            // si respuesta es si, producto por constante
            if(respuesta == 0) {
                String valor = JOptionPane.showInputDialog(objVentanaAxpherPicture, "Valor", "Producto Por Valor A Imagen", JOptionPane.INFORMATION_MESSAGE);
                int constante = Integer.parseInt(valor);
                // ---------------------------------->
                HiloOperaciones hiloOperaciones = new HiloOperaciones("Producto");
                hiloOperaciones.setOpConst(constante);
                hiloOperaciones.start();
                // ---------------------------------->
                System.out.println("Si Producto Por Constante");
            }
            // si respuesta es no, producto por imagen
            if(respuesta == 1) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PGM & PPM", "pgm", "ppm");
                fileChooser.setFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);
                int respu = fileChooser.showOpenDialog(objVentanaAxpherPicture);
                if(respu == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().exists()) {
                    File fileImage = fileChooser.getSelectedFile();
                    //crea el objeto Imagen
                    Imagen objImg = new Imagen(fileImage.getAbsolutePath());
                    // ------------------------------------>
                    HiloOperaciones hiloOperaciones = new HiloOperaciones("Producto");
                    hiloOperaciones.setOpImg(objImg);
                    hiloOperaciones.start();
                    // ------------------------------------>
                    System.out.println("Archivo imagen seleccionado");
                } else {
                    System.out.println("No selecciona archivo imagen");
                }
                System.out.println("No Producto Por Constante");
            }
            System.out.println("Producto "+respuesta);
        }
        if(e.getActionCommand().equals("Traslacion")) {
            String valor = JOptionPane.showInputDialog(objVentanaAxpherPicture, "Valor", "Valor En X", JOptionPane.INFORMATION_MESSAGE);
            int valorX = Integer.parseInt(valor);
            valor = JOptionPane.showInputDialog(objVentanaAxpherPicture, "Valor", "Valor En Y", JOptionPane.INFORMATION_MESSAGE);
            int valorY = Integer.parseInt(valor);
            // ---------------------------------->
            HiloOperaciones hiloOperaciones = new HiloOperaciones("Traslacion");
            hiloOperaciones.setValorX(valorX);
            hiloOperaciones.setValorY(valorY);
            hiloOperaciones.start();
            // ---------------------------------->
            System.out.println("Traslacion !!");
        }
        if(e.getSource() == this.objVentanaAxpherPicture.menuItemOpReflexV) {
            System.out.println("Reflex Vertical !!");
            // ---------------------------------->
            HiloOperaciones hiloOperaciones = new HiloOperaciones("ReflexV");
            hiloOperaciones.start();
            // ---------------------------------->
        }
        if(e.getSource() == this.objVentanaAxpherPicture.menuItemOpReflexH) {
            System.out.println("Reflex Horizontal !!");
            // ---------------------------------->
            HiloOperaciones hiloOperaciones = new HiloOperaciones("ReflexH");
            hiloOperaciones.start();
            // ---------------------------------->
        }
        if(e.getActionCommand().equals("Media")) {
            int respuesta = JOptionPane.showConfirmDialog(objVentanaAxpherPicture, "Media Por Una Constante?", "Producto Constante O Imagen",JOptionPane.YES_NO_CANCEL_OPTION);
            // si respuesta es si, media por constante
            if(respuesta == 0) {
                String valor = JOptionPane.showInputDialog(objVentanaAxpherPicture, "Valor", "Media Por Una Imagen", JOptionPane.INFORMATION_MESSAGE);
                int constante = Integer.parseInt(valor);
                // ---------------------------------->
                HiloOperaciones hiloOperaciones = new HiloOperaciones("Media");
                hiloOperaciones.setOpConst(constante);
                hiloOperaciones.start();
                // ---------------------------------->
                System.out.println("Si Media Por Constante");
            }
            // si respuesta es no, media por imagen
            if(respuesta == 1) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PGM & PPM", "pgm", "ppm");
                fileChooser.setFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);
                int respu = fileChooser.showOpenDialog(objVentanaAxpherPicture);
                if(respu == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().exists()) {
                    File fileImage = fileChooser.getSelectedFile();
                    //crea el objeto Imagen
                    Imagen objImg = new Imagen(fileImage.getAbsolutePath());
                    // ------------------------------------>
                    HiloOperaciones hiloOperaciones = new HiloOperaciones("Media");
                    hiloOperaciones.setOpImg(objImg);
                    hiloOperaciones.start();
                    // ------------------------------------>
                    System.out.println("Archivo imagen seleccionado");
                } else {
                    System.out.println("No selecciona archivo imagen");
                }
                System.out.println("No Producto Por Constante");
            }
            System.out.println("Media "+respuesta);
        }
        if(e.getActionCommand().equals("Maximo")) {
            System.out.println("Maximo !!");
            int respuesta = JOptionPane.showConfirmDialog(objVentanaAxpherPicture, "Maximo Por Una Constante?", "Maximo Constante O Imagen",JOptionPane.YES_NO_CANCEL_OPTION);
            // si respuesta es si, maximo por constante
            if(respuesta == 0) {
                String valor = JOptionPane.showInputDialog(objVentanaAxpherPicture, "Valor", "Maximo Por Una Imagen", JOptionPane.INFORMATION_MESSAGE);
                int constante = Integer.parseInt(valor);
                // ---------------------------------->
                HiloOperaciones hiloOperaciones = new HiloOperaciones("Maximo");
                hiloOperaciones.setOpConst(constante);
                hiloOperaciones.start();
                // ---------------------------------->
                System.out.println("Si Maximo Por Constante");
            }
            // si respuesta es no, maximo por imagen
            if(respuesta == 1) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PGM & PPM", "pgm", "ppm");
                fileChooser.setFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);
                int respu = fileChooser.showOpenDialog(objVentanaAxpherPicture);
                if(respu == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().exists()) {
                    File fileImage = fileChooser.getSelectedFile();
                    //crea el objeto Imagen
                    Imagen objImg = new Imagen(fileImage.getAbsolutePath());
                    // ------------------------------------>
                    HiloOperaciones hiloOperaciones = new HiloOperaciones("Maximo");
                    hiloOperaciones.setOpImg(objImg);
                    hiloOperaciones.start();
                    // ------------------------------------>
                    System.out.println("Archivo imagen seleccionado");
                } else {
                    System.out.println("No selecciona archivo imagen");
                }
                System.out.println("No Producto Por Constante");
            }
            System.out.println("Maximo "+respuesta);
        }
        if(e.getActionCommand().equals("Minimo")) {
            System.out.println("Minimo !!");
            int respuesta = JOptionPane.showConfirmDialog(objVentanaAxpherPicture, "Minimo Por Una Constante?", "Maximo Constante O Imagen",JOptionPane.YES_NO_CANCEL_OPTION);
            // si respuesta es si, minimo por constante
            if(respuesta == 0) {
                String valor = JOptionPane.showInputDialog(objVentanaAxpherPicture, "Valor", "Minimo Por Una Imagen", JOptionPane.INFORMATION_MESSAGE);
                int constante = Integer.parseInt(valor);
                // ---------------------------------->
                HiloOperaciones hiloOperaciones = new HiloOperaciones("Minimo");
                hiloOperaciones.setOpConst(constante);
                hiloOperaciones.start();
                // ---------------------------------->
                System.out.println("Si Minimo Por Constante");
            }
            // si respuesta es no, minimo por imagen
            if(respuesta == 1) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PGM & PPM", "pgm", "ppm");
                fileChooser.setFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);
                int respu = fileChooser.showOpenDialog(objVentanaAxpherPicture);
                if(respu == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().exists()) {
                    File fileImage = fileChooser.getSelectedFile();
                    //crea el objeto Imagen
                    Imagen objImg = new Imagen(fileImage.getAbsolutePath());
                    // ------------------------------------>
                    HiloOperaciones hiloOperaciones = new HiloOperaciones("Minimo");
                    hiloOperaciones.setOpImg(objImg);
                    hiloOperaciones.start();
                    // ------------------------------------>
                    System.out.println("Archivo imagen seleccionado");
                } else {
                    System.out.println("No selecciona archivo imagen");
                }
                System.out.println("No Producto Por Constante");
            }
            System.out.println("Minimo "+respuesta);
        }
        if(e.getSource() == this.objVentanaAxpherPicture.menuItemLineH) {
            System.out.println("Filtro Lineas Horizontal");
            String valor = JOptionPane.showInputDialog(objVentanaAxpherPicture, "Valor", "Filtro Lineas Horizontal", JOptionPane.INFORMATION_MESSAGE);
            int constante = Integer.parseInt(valor);
            // ---------------------------------->
            HiloFiltroNoise hiloFiltroNoise = new HiloFiltroNoise(3);
            hiloFiltroNoise.setConst(constante);
            hiloFiltroNoise.start();
            // ---------------------------------->
        }
        if(e.getSource() == this.objVentanaAxpherPicture.menuItemLineV) {
            System.out.println("Filtro Lineas Vertical");
            String valor = JOptionPane.showInputDialog(objVentanaAxpherPicture, "Valor", "Filtro Lineas Vertical", JOptionPane.INFORMATION_MESSAGE);
            int constante = Integer.parseInt(valor);
            // ---------------------------------->
            HiloFiltroNoise hiloFiltroNoise = new HiloFiltroNoise(4);
            hiloFiltroNoise.setConst(constante);
            hiloFiltroNoise.start();
            // ---------------------------------->
        }
        if(e.getSource() == this.objVentanaAxpherPicture.menuItemLineHV) {
            System.out.println("Filtro Lineas HV");
            String valor = JOptionPane.showInputDialog(objVentanaAxpherPicture, "Valor", "Filtro Lineas", JOptionPane.INFORMATION_MESSAGE);
            int constante = Integer.parseInt(valor);
            // ---------------------------------->
            HiloFiltroNoise hiloFiltroNoise = new HiloFiltroNoise(5);
            hiloFiltroNoise.setConst(constante);
            hiloFiltroNoise.start();
            // ---------------------------------->
        }
        if(e.getActionCommand().equals("Sal-Pimienta")) {
            System.out.println("Filtro Sal-Piper");
            String valor = JOptionPane.showInputDialog(objVentanaAxpherPicture, "Valor", "Filtro Sal-Pimienta", JOptionPane.INFORMATION_MESSAGE);
            int constante = Integer.parseInt(valor);
            // ---------------------------------->
            HiloFiltroNoise hiloFiltroNoise = new HiloFiltroNoise(6);
            hiloFiltroNoise.setConst(constante);
            hiloFiltroNoise.start();
            // ---------------------------------->
        }
        if(objPanelMR != null) {
            if(e.getSource().equals(objPanelMR.btnVisualizar)){
                int windowCenter = Integer.parseInt(objPanelMR.textFieldWC.getText());
                int windowWidth = Integer.parseInt(objPanelMR.textFieldWW.getText());
                if(objDcmImg.getEstudio().equals("MR")) {
                    objImagenFuente = objDcmImg.getImagenMR(windowCenter,windowWidth);
                }
                if(objDcmImg.getEstudio().equals("CR")) {
                    objImagenFuente = objDcmImg.getImagenCR(windowCenter,windowWidth);
                }
                //copia de objeto imagen fuente
                objImagenProcesado = objImagenFuente.clone();
                verAtributosImagen(objImagenFuente);
                objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenFuente);
                System.out.println("Boton Visualizar");
            }
        }
        if(e.getActionCommand().equals("Sobel")) {
            HiloBordeSobel hiloBordeSobel = new HiloBordeSobel();
            hiloBordeSobel.start();
            System.out.println("Bordes Sobel");
        }
        if(e.getActionCommand().equals("Cany")) {
            System.out.println("Bordes Cany");
            int respuesta = JOptionPane.showConfirmDialog(objVentanaAxpherPicture, "Definir Umbrales ?", "Bordes Canny",JOptionPane.YES_NO_CANCEL_OPTION);
            // si respuesta es si, ingresa umbrales
            if(respuesta == 0) {
                String valor = JOptionPane.showInputDialog(objVentanaAxpherPicture, "Valor", "Umbral1", JOptionPane.INFORMATION_MESSAGE);
                int umbral1 = Integer.parseInt(valor);
                valor = JOptionPane.showInputDialog(objVentanaAxpherPicture, "Valor", "Umbral2", JOptionPane.INFORMATION_MESSAGE);
                int umbral2 = Integer.parseInt(valor);
                valor = JOptionPane.showInputDialog(objVentanaAxpherPicture, "Valor", "Kernel", JOptionPane.INFORMATION_MESSAGE);
                int kernel = Integer.parseInt(valor);
                // ---------------------------------->
                HiloBordeCany hiloBordeCany = new HiloBordeCany();
                hiloBordeCany.setUmbralizado(true);
                hiloBordeCany.setUmbral1(umbral1);
                hiloBordeCany.setUmbral2(umbral2);
                hiloBordeCany.setKernel(kernel);
                hiloBordeCany.start();
                // ---------------------------------->
                System.out.println("Si umbrales");
            }
            // si respuesta es no, ingresa solo kernel
            if(respuesta == 1) {
                String valor = JOptionPane.showInputDialog(objVentanaAxpherPicture, "Valor", "Kernel", JOptionPane.INFORMATION_MESSAGE);
                int kernel = Integer.parseInt(valor);
                // ---------------------------------->
                HiloBordeCany hiloBordeCany = new HiloBordeCany();
                hiloBordeCany.setUmbralizado(false);
                hiloBordeCany.setKernel(kernel);
                hiloBordeCany.start();
                // ---------------------------------->
                System.out.println("Solo kernel");
            }
            System.out.println("Canny "+respuesta);
        }
        if(e.getActionCommand().equals("Automatico")) {
            System.out.println("Menu Segmentar Automatico");
            HiloSegmentarKmeans hiloSegmentarKmeans = new HiloSegmentarKmeans(true);
            hiloSegmentarKmeans.start();
        }
        if(e.getActionCommand().equals("Manual")) {
            System.out.println("Menu Segmentar Manual");
            HiloSegmentarKmeans hiloSegmentarKmeans = new HiloSegmentarKmeans(false);
            hiloSegmentarKmeans.start();
        }
    }
    
    private void verAtributosImagen(Imagen objImagen) {
        objVentanaAxpherPicture.labelArchivo.setText(""+objImagen.getNombreArchivo());
        objVentanaAxpherPicture.labelFormato.setText(""+objImagen.getFormato());
        objVentanaAxpherPicture.labelIntensidad.setText(""+objImagen.getNivelIntensidad());
        objVentanaAxpherPicture.labelAncho.setText(""+objImagen.getM());
        objVentanaAxpherPicture.labelAlto.setText(""+objImagen.getN());
    }
    
    private void abrirArchivoImagenPGMPPM() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PGM, PPM", "pgm", "ppm");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        int respuesta = fileChooser.showOpenDialog(objVentanaAxpherPicture);
        if(respuesta == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().exists()) {
            archivoImagen = fileChooser.getSelectedFile();
            //crea el objeto Imagen
            HiloAbreArchivoImagenPGMPPM hiloAbreArchivoImagen = new HiloAbreArchivoImagenPGMPPM();
            hiloAbreArchivoImagen.start();
            System.out.println("Archivo imagen seleccionado");
        } else {
            System.out.println("No selecciona archivo imagen");
        }
    }
    
    private void abrirArchivoImagenDICOM() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("DICOM", "dcm");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        int respuesta = fileChooser.showOpenDialog(objVentanaAxpherPicture);
        if(respuesta == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().exists()) {
            archivoImagen = fileChooser.getSelectedFile();
            //crea el objeto Imagen
            HiloAbreArchivoImagenDICOM hiloArchivoImagenDicom = new HiloAbreArchivoImagenDICOM();
            hiloArchivoImagenDicom.start();
            System.out.println("Archivo imagen seleccionado");
        } else {
            System.out.println("No selecciona archivo imagen");
        }
    }
    
    private void guardarArchivoImagen() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PGM & PPM", "pgm", "ppm");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        int respuesta = fileChooser.showSaveDialog(objVentanaAxpherPicture);
        if(respuesta == JFileChooser.APPROVE_OPTION) {
            archivoImagen = fileChooser.getSelectedFile();
            //crea el objeto Imagen
            HiloGuardaArchivoImagen hiloGuardaArchivoImagen = new HiloGuardaArchivoImagen();
            hiloGuardaArchivoImagen.start();
            System.out.println("Archivo imagen seleccionado");
        } else {
            System.out.println("No selecciona archivo imagen");
        }
    }
    
    private void guardarArchivoHistograma() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PGM & PPM", "pgm", "ppm");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        int respuesta = fileChooser.showSaveDialog(objVentanaAxpherPicture);
        if(respuesta == JFileChooser.APPROVE_OPTION) {
            archivoImagen = fileChooser.getSelectedFile();
            //crea el objeto Imagen
            HiloGuardarArchivoHistograma hiloGuardaArchivoHistograma = new HiloGuardarArchivoHistograma();
            hiloGuardaArchivoHistograma.start();
            System.out.println("Archivo imagen seleccionado");
        } else {
            System.out.println("No selecciona archivo imagen");
        }
    }
    
    private void verImagenFuente() {
        objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenFuente);
        objImagenProcesado = objImagenFuente.clone();
        verAtributosImagen(objImagenFuente);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(objPanelMR != null) {
            if(e.getSource().equals(objPanelMR.sliderWC)){
                objPanelMR.textFieldWC.setText(objPanelMR.sliderWC.getValue()+"");
            }
            if(e.getSource().equals(objPanelMR.sliderWW)){
                objPanelMR.textFieldWW.setText(objPanelMR.sliderWW.getValue()+"");
            }
            //--------->
            /*int windowCenter = Integer.parseInt(objPanelMR.textFieldWC.getText());
            int windowWidth = Integer.parseInt(objPanelMR.textFieldWW.getText());
            if(objDcmImg.getEstudio().equals("MR")) {
                objImagenFuente = objDcmImg.getImagenMR(windowCenter,windowWidth);
            }
            if(objDcmImg.getEstudio().equals("CR")) {
                objImagenFuente = objDcmImg.getImagenCR(windowCenter,windowWidth);
            }
            //copia de objeto imagen fuente
            objImagenProcesado = objImagenFuente.clone();
            verAtributosImagen(objImagenFuente);
            objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenFuente);*/
            //----------------->
        }
        if(objVentanaSignal != null) {
            if(e.getSource().equals(objVentanaSignal.sliderSignal)) {
                System.out.println("Fila: "+objVentanaSignal.sliderSignal.getValue());
                objVentanaSignal.canvasSignal.setFila(objVentanaSignal.sliderSignal.getValue());
                objVentanaSignal.canvasSignal.repaint();
                System.out.println("H "+objVentanaSignal.getHeight()+" W "+objVentanaSignal.getWidth());
            }
        }
        System.out.println("Cambia Slider");
    }
    
    class HiloAbreArchivoImagenPGMPPM extends Thread {
        @Override
        public void run() {
            AxpherPicture.barraProgreso.setValue(25);
            objImagenFuente = new Imagen(archivoImagen.getAbsolutePath());
            //copia de objeto imagen fuente
            objImagenProcesado = objImagenFuente.clone();
            verAtributosImagen(objImagenFuente);
            AxpherPicture.barraProgreso.setValue(80);
            objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenFuente);
            AxpherPicture.barraProgreso.setValue(100);
            try {
                sleep(512);
            } catch (InterruptedException ex) {
                System.err.println("Error al dormir Hilo abrir archivo de imagen");
            }
            AxpherPicture.barraProgreso.setValue(0);
        }
    }
    
    class HiloAbreArchivoImagenDICOM extends Thread {
        
        public HiloAbreArchivoImagenDICOM() {
            
        }
        
        @Override
        public void run() {
            objDcmImg = new DcmImg(archivoImagen.getAbsolutePath());
            DicomObject dcmObj;
            dcmObj = objDcmImg.getDicomObject();
            objDcmImg.printHeaders(dcmObj);
            DcmImg.pixelSpacing = objDcmImg.getPixelSpacing();
            String estudio = objDcmImg.getEstudio();
            System.out.println("Estudio "+estudio);
            if(estudio.equals("OT")) {
                objImagenFuente = objDcmImg.getImagenOT();
                //copia de objeto imagen fuente
                objImagenProcesado = objImagenFuente.clone();
                verAtributosImagen(objImagenFuente);
                objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenFuente);
            }
            if(estudio.equals("MR")) {
                int windowCenter = objDcmImg.getWindowCenter();
                int windowWidth = objDcmImg.getWindowWidth();
                objImagenFuente = objDcmImg.getImagenMR(windowCenter,windowWidth);
                //copia de objeto imagen fuente
                objImagenProcesado = objImagenFuente.clone();
                verAtributosImagen(objImagenFuente);
                objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenFuente);
                // agrega el panel MR
                objVentanaAxpherPicture.panelOperaciones.removeAll();
                objPanelMR = new PanelMR();
                objVentanaAxpherPicture.panelOperaciones.add(objPanelMR);
                objPanelMR.textFieldWC.setText(windowCenter+"");
                objPanelMR.textFieldWW.setText(windowWidth+"");
                objPanelMR.sliderWC.setMaximum(windowCenter);
                objPanelMR.sliderWW.setMaximum(windowWidth);
                objPanelMR.sliderWC.setValue(windowCenter);
                objPanelMR.sliderWW.setValue(windowWidth);
                objPanelMR.sliderWC.addChangeListener(ControladorImagen.this);
                objPanelMR.sliderWW.addChangeListener(ControladorImagen.this);
                objPanelMR.btnVisualizar.addActionListener(ControladorImagen.this);
                objVentanaAxpherPicture.pack();
            }
            if(estudio.equals("CR")) {
                int windowCenter = objDcmImg.getWindowCenter();
                int windowWidth = objDcmImg.getWindowWidth();
                objImagenFuente = objDcmImg.getImagenCR(windowCenter,windowWidth);
                //copia de objeto imagen fuente
                objImagenProcesado = objImagenFuente.clone();
                verAtributosImagen(objImagenFuente);
                objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenFuente);
                // agrega el panel CR
                objVentanaAxpherPicture.panelOperaciones.removeAll();
                objPanelMR = new PanelMR();
                objVentanaAxpherPicture.panelOperaciones.add(objPanelMR);
                objPanelMR.textFieldWC.setText(windowCenter+"");
                objPanelMR.textFieldWW.setText(windowWidth+"");
                objPanelMR.sliderWC.setMaximum(windowCenter);
                objPanelMR.sliderWW.setMaximum(windowWidth);
                objPanelMR.sliderWC.setValue(windowCenter);
                objPanelMR.sliderWW.setValue(windowWidth);
                objPanelMR.sliderWC.addChangeListener(ControladorImagen.this);
                objPanelMR.sliderWW.addChangeListener(ControladorImagen.this);
                objPanelMR.btnVisualizar.addActionListener(ControladorImagen.this);
                objVentanaAxpherPicture.pack();
            }
        }
    }
    
    class HiloGuardaArchivoImagen extends Thread {
        @Override
        public void run() {
            AxpherPicture.barraProgreso.setValue(25);
            objImagenProcesado.guardarImagen(archivoImagen.getAbsolutePath());
            AxpherPicture.barraProgreso.setValue(100);
            try {
                sleep(512);
            } catch (InterruptedException ex) {
                System.err.println("Error al dormir Hilo guardar archivo de imagen");
            }
            AxpherPicture.barraProgreso.setValue(0);
        }
    }
    
    class HiloVerHistogramaImagen extends Thread {
        @Override
        public void run() {
            AxpherPicture.barraProgreso.setValue(25);
            objHistograma = new Histograma(objImagenProcesado);
            objVentanaHistograma.canvasHistograma.pintarHistograma(objHistograma.getImagenHistograma());
            AxpherPicture.barraProgreso.setValue(50);
            objVentanaHistograma.pack();
            objVentanaHistograma.setLocationRelativeTo(objVentanaAxpherPicture);
            objVentanaHistograma.setVisible(true);
            AxpherPicture.barraProgreso.setValue(100);
            try {
                sleep(512);
            } catch (InterruptedException ex) {
                System.err.println("Error al dormir Hilo ver histograma de imagen");
            }
            AxpherPicture.barraProgreso.setValue(0);
        }
    }
    
    class HiloVerSignalImagen extends Thread {
        @Override
        public void run() {
            // --->
            objVentanaSignal.canvasSignal.pintarSignal(objImagenProcesado, objVentanaSignal.sliderSignal.getValue());
            objVentanaSignal.pack();
            objVentanaSignal.setLocationRelativeTo(objVentanaAxpherPicture);
            objVentanaSignal.setVisible(true);
        }
    }
    
    class HiloGuardarArchivoHistograma extends Thread {
        @Override
        public void run() {
            AxpherPicture.barraProgreso.setValue(25);
            Imagen imgHistograma = objHistograma.getImagenHistograma();
            AxpherPicture.barraProgreso.setValue(50);
            imgHistograma.guardarImagen(archivoImagen.getAbsolutePath());
            AxpherPicture.barraProgreso.setValue(100);
            try {
                sleep(512);
            } catch (InterruptedException ex) {
                System.err.println("Error al dormir Hilo guardar archivo de imagen");
            }
            AxpherPicture.barraProgreso.setValue(0);
        }
    }
    
    class HiloUmbralizacion extends Thread {
        
        private int idMetodo;
        
        public HiloUmbralizacion(int idMetodo) {
            this.idMetodo = idMetodo;
        }
        
        @Override
        public void run() {
            AxpherPicture.barraProgreso.setValue(25);
            Histograma objHistograma = new Histograma(objImagenProcesado);
            AxpherPicture.barraProgreso.setValue(50);
            objUmbral = new Umbralizacion(objHistograma, idMetodo);
            if(objImagenFuente.getFormato().equals("P2")) {
                int umbral = objUmbral.getUmbralGris();
                if(idMetodo == 0) {
                    objPanelUmbral.labelValorDosPicos.setText(""+umbral);
                }
                if(idMetodo == 1) {
                    objPanelUmbral.labelValorIsodata.setText(""+umbral);
                }
                if(idMetodo == 2) {
                    objPanelUmbral.labelValorOtsu.setText(""+umbral);
                }
            } else {
                int umbralR = objUmbral.getUmbralR();
                int umbralG = objUmbral.getUmbralG();
                int umbralB = objUmbral.getUmbralB();
                if(idMetodo == 0) {
                    objPanelUmbral.labelValorDosPicos.setText(umbralR+","+umbralG+","+umbralB);
                }
                if(idMetodo == 1) {
                    objPanelUmbral.labelValorIsodata.setText(umbralR+","+umbralG+","+umbralB);
                }
                if(idMetodo == 2) {
                    objPanelUmbral.labelValorOtsu.setText(umbralR+","+umbralG+","+umbralB);
                }
            }
            AxpherPicture.barraProgreso.setValue(100);
            try {
                sleep(512);
            } catch (InterruptedException ex) {
                System.err.println("Error al dormir Hilo umbralizacion");
            }
            AxpherPicture.barraProgreso.setValue(0);
        }
    }
    
    class HiloImagenBinaria extends Thread {
        
        private String umbral;
        
        public HiloImagenBinaria(String umbral) {
            this.umbral = umbral;
        }
        
        @Override
        public void run() {
            setAtras();
            AxpherPicture.barraProgreso.setValue(25);
            objImagenProcesado.setFormato(objImagenFuente.getFormato());
            objImagenProcesado.setM(objImagenFuente.getM());
            objImagenProcesado.setN(objImagenFuente.getN());
            objImagenProcesado.setNivelIntensidad(1);
            if(objImagenFuente.getFormato().equals("P2")) {
                short matrizGris[][] = new short[objImagenProcesado.getN()][objImagenProcesado.getM()];
                objImagenProcesado.setMatrizGris(matrizGris);
                int valorUmbral = Integer.parseInt(umbral);
                AxpherPicture.barraProgreso.setValue(45);
                for(int i = 0; i < objImagenProcesado.getN(); i++) {
                    for(int j = 0; j < objImagenProcesado.getM(); j++) {
                        if(objImagenFuente.getMatrizGris()[i][j] < valorUmbral) {
                            objImagenProcesado.getMatrizGris()[i][j] = 0;
                        } else {
                            objImagenProcesado.getMatrizGris()[i][j] = 1;
                        }
                    }
                }
                AxpherPicture.barraProgreso.setValue(80);
            } else {
                short matrizR[][] = new short[objImagenProcesado.getN()][objImagenProcesado.getM()];
                short matrizG[][] = new short[objImagenProcesado.getN()][objImagenProcesado.getM()];
                short matrizB[][] = new short[objImagenProcesado.getN()][objImagenProcesado.getM()];
                objImagenProcesado.setMatrizR(matrizR);
                objImagenProcesado.setMatrizG(matrizG);
                objImagenProcesado.setMatrizB(matrizB);
                StringTokenizer stringToken = new StringTokenizer(umbral, ",");
                int valorUmbralR = Integer.parseInt(stringToken.nextToken());
                int valorUmbralG = Integer.parseInt(stringToken.nextToken());
                int valorUmbralB = Integer.parseInt(stringToken.nextToken());
                AxpherPicture.barraProgreso.setValue(45);
                for(int i = 0; i < objImagenProcesado.getN(); i++) {
                    for(int j = 0; j < objImagenProcesado.getM(); j++) {
                        //canal R
                        if(objImagenFuente.getMatrizR()[i][j] < valorUmbralR) {
                            objImagenProcesado.getMatrizR()[i][j] = 0;
                        } else {
                            objImagenProcesado.getMatrizR()[i][j] = 1;
                        }
                        //canal G
                        if(objImagenFuente.getMatrizG()[i][j] < valorUmbralG) {
                            objImagenProcesado.getMatrizG()[i][j] = 0;
                        } else {
                            objImagenProcesado.getMatrizG()[i][j] = 1;
                        }
                        //canal B
                        if(objImagenFuente.getMatrizB()[i][j] < valorUmbralB) {
                            objImagenProcesado.getMatrizB()[i][j] = 0;
                        } else {
                            objImagenProcesado.getMatrizB()[i][j] = 1;
                        }
                    }
                }
                AxpherPicture.barraProgreso.setValue(80);
            }
            objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenProcesado);
            AxpherPicture.barraProgreso.setValue(90);
            verAtributosImagen(objImagenFuente);
            AxpherPicture.barraProgreso.setValue(100);
            try {
                sleep(512);
            } catch (InterruptedException ex) {
                System.err.println("Error al dormir Hilo umbralizacion");
            }
            AxpherPicture.barraProgreso.setValue(0);
        }
    }
    
    class HiloEcualizacion extends Thread {
        @Override
        public void run() {
            setAtras();
            AxpherPicture.barraProgreso.setValue(45);
            Ecualizacion ecualizador = new Ecualizacion(objImagenProcesado);
            objImagenProcesado = ecualizador.ecualizar();
            AxpherPicture.barraProgreso.setValue(80);
            objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenProcesado);
            AxpherPicture.barraProgreso.setValue(100);
            try {
                sleep(512);
            } catch (InterruptedException ex) {
                System.err.println("Error al dormir Hilo umbralizacion");
            }
            AxpherPicture.barraProgreso.setValue(0);
        }
    }
    
    class HiloCuantizar extends Thread {
        private int bitsPixel;
        
        public HiloCuantizar(int bitsPixel) {
            this.bitsPixel = bitsPixel;
        }
        
        @Override
        public void run() {
            setAtras();
            AxpherPicture.barraProgreso.setValue(45);
            objImagenProcesado.setFormato(objImagenProcesado.getFormato());
            objImagenProcesado.setM(objImagenProcesado.getM());
            objImagenProcesado.setN(objImagenProcesado.getN());
            objImagenProcesado.setNivelIntensidad(objImagenProcesado.getNivelIntensidad());
            short matrizGris[][] = new short[objImagenProcesado.getN()][objImagenProcesado.getM()];
            for(int i = 0; i < matrizGris.length; i++) {
                for(int j = 0; j < matrizGris[0].length; j++) {
                    matrizGris[i][j] = objImagenProcesado.getMatrizGris()[i][j];
                }
            }
            objImagenProcesado.setMatrizGris(matrizGris);
            Cuantizar rs = new Cuantizar(objImagenProcesado);
            rs.asignarBitsPixel(this.bitsPixel);
            AxpherPicture.barraProgreso.setValue(80);
            objImagenProcesado = rs.getObjImagen();
            objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenProcesado);
            AxpherPicture.barraProgreso.setValue(100);
            try {
                sleep(512);
            } catch (InterruptedException ex) {
                System.err.println("Error al dormir Hilo umbralizacion");
            }
            AxpherPicture.barraProgreso.setValue(0);
        }
    }
    
    class HiloFiltroNoise extends Thread {
        private int filtro;
        private int constante;
        
        public HiloFiltroNoise(int filtro) {
            this.filtro = filtro;
        }
        
        public void setConst(int constante) {
            this.constante = constante;
        }
        
        @Override
        public void run() {
            setAtras();
            AxpherPicture.barraProgreso.setValue(45);
            objImagenProcesado.setFormato(objImagenProcesado.getFormato());
            objImagenProcesado.setM(objImagenProcesado.getM());
            objImagenProcesado.setN(objImagenProcesado.getN());
            objImagenProcesado.setNivelIntensidad(objImagenProcesado.getNivelIntensidad());
            short matrizGris[][] = new short[objImagenProcesado.getN()][objImagenProcesado.getM()];
            for(int i = 0; i < matrizGris.length; i++) {
                for(int j = 0; j < matrizGris[0].length; j++) {
                    matrizGris[i][j] = objImagenProcesado.getMatrizGris()[i][j];
                }
            }
            objImagenProcesado.setMatrizGris(matrizGris);
            // -->>
            objFiltro = new FiltroNoise(objImagenProcesado);
            if(filtro == 0) {
                String valor = JOptionPane.showInputDialog(objVentanaAxpherPicture, "Ingrese un valor para el Sigma", "Filtro Sigma", JOptionPane.INFORMATION_MESSAGE);
                short sigma = Short.parseShort(valor);
                objFiltro.filtroSigma(sigma);
            }
            if(filtro == 1) {
                String valor = JOptionPane.showInputDialog(objVentanaAxpherPicture, "Ingrese un tamaño de ventana", "Filtro Mediana", JOptionPane.INFORMATION_MESSAGE);
                int sizeVentana = Integer.parseInt(valor);
                objFiltro.filtroMediana(sizeVentana);
            }
            if(filtro == 2) {
                objFiltro.nagaoMatsuyama();
            }
            if(filtro == 3) {
                objFiltro.filtroRuidoLineasHorizontal(constante);
                objImagenProcesado = objFiltro.getImagen();
            }
            if(filtro == 4) {
                objFiltro.filtroRuidoLineasVertical(constante);
                objImagenProcesado = objFiltro.getImagen();
            }
            if(filtro == 5) {
                objFiltro.filtroRuidoLineas(constante);
                objImagenProcesado = objFiltro.getImagen();
            }
            if(filtro == 6) {
                objFiltro.filtroSalPimienta(constante);
                objImagenProcesado = objFiltro.getImagen();
            }
            if(filtro == 7) {
                String valor = JOptionPane.showInputDialog(objVentanaAxpherPicture, "Ingrese un tamaño de ventana", "Filtro Gausiano", JOptionPane.INFORMATION_MESSAGE);
                int sizeVentana = Integer.parseInt(valor);
                objFiltro.filtroGausiano(sizeVentana);
            }
            AxpherPicture.barraProgreso.setValue(80);
            objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenProcesado);
            AxpherPicture.barraProgreso.setValue(100);
            try {
                sleep(512);
            } catch (InterruptedException ex) {
                System.err.println("Error al dormir Hilo umbralizacion");
            }
            AxpherPicture.barraProgreso.setValue(0);
        }
    }
    
    class HiloOperaciones extends Thread {
        private String operacion;
        private Imagen opImg;
        private int constante;
        private int valorX;
        private int valorY;
        
        public HiloOperaciones(String operacion) {
            this.operacion = operacion;
        }
        
        public void setOpImg(Imagen objImagen) {
            this.opImg = objImagen;
        }
        
        public void setOpConst(int constante) {
            this.constante = constante;
        }
        
        public void setValorX(int valorX) {
            this.valorX = valorX;
        }
        
        public void setValorY(int valorY) {
            this.valorY = valorY;
        }
        
        @Override
        public void run() {
            setAtras();
            if(operacion.equals("And")) {
                Operaciones operaciones = new Operaciones();
                objImagenProcesado = operaciones.OrAndXor(objImagenProcesado, opImg, "and");
                objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenProcesado);
            }
            if(operacion.equals("Or")) {
                Operaciones operaciones = new Operaciones();
                objImagenProcesado = operaciones.OrAndXor(objImagenProcesado, opImg, "or");
                objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenProcesado);
            }
            if(operacion.equals("Xor")) {
                Operaciones operaciones = new Operaciones();
                objImagenProcesado = operaciones.OrAndXor(objImagenProcesado, opImg, "xor");
                objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenProcesado);
            }
            if(operacion.equals("Suma")) {
                // si no hay operador imagen, suma constante
                if(opImg == null) {
                    Operaciones operaciones = new Operaciones();
                    objImagenProcesado = operaciones.suma(objImagenProcesado, constante);
                    objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenProcesado);
                } else { // si hay operador imagen, suma imagen
                    Operaciones operaciones = new Operaciones();
                    objImagenProcesado = operaciones.suma(objImagenProcesado, opImg);
                    objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenProcesado);
                }
            }
            if(operacion.equals("Resta")) {
                // si no hay operador imagen, resta constante
                if(opImg == null) {
                    Operaciones operaciones = new Operaciones();
                    objImagenProcesado = operaciones.resta(objImagenProcesado, constante);
                    objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenProcesado);
                } else { // si hay operador imagen, resta imagen
                    Operaciones operaciones = new Operaciones();
                    objImagenProcesado = operaciones.resta(objImagenProcesado, opImg);
                    objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenProcesado);
                }
            }
            if(operacion.equals("Producto")) {
                // si no hay operador imagen, producto constante
                if(opImg == null) {
                    Operaciones operaciones = new Operaciones();
                    objImagenProcesado = operaciones.producto(objImagenProcesado, constante);
                    objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenProcesado);
                } else { // si hay operador imagen, producto imagen
                    Operaciones operaciones = new Operaciones();
                    objImagenProcesado = operaciones.producto(objImagenProcesado, opImg);
                    objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenProcesado);
                }
            }
            if(operacion.equals("Traslacion")) {
                Operaciones operaciones = new Operaciones();
                objImagenProcesado = operaciones.traslacion(objImagenProcesado, valorY, valorX);
                objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenProcesado);
            }
            if(operacion.equals("ReflexV")) {
                Operaciones operaciones = new Operaciones();
                objImagenProcesado = operaciones.reflexionY(objImagenProcesado);
                objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenProcesado);
            }
            if(operacion.equals("ReflexH")) {
                Operaciones operaciones = new Operaciones();
                objImagenProcesado = operaciones.reflexionX(objImagenProcesado);
                objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenProcesado);
            }
            if(operacion.equals("Media")) {
                // si no hay operador imagen, media constante
                if(opImg == null) {
                    Operaciones operaciones = new Operaciones();
                    objImagenProcesado = operaciones.media(objImagenProcesado, constante);
                    objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenProcesado);
                } else { // si hay operador imagen, media imagen
                    Operaciones operaciones = new Operaciones();
                    objImagenProcesado = operaciones.media(objImagenProcesado, opImg);
                    objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenProcesado);
                }
            }
            if(operacion.equals("Maximo")) {
                // si no hay operador imagen, maximo constante
                if(opImg == null) {
                    Operaciones operaciones = new Operaciones();
                    objImagenProcesado = operaciones.maximo(objImagenProcesado, constante);
                    objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenProcesado);
                } else { // si hay operador imagen, maximo imagen
                    Operaciones operaciones = new Operaciones();
                    objImagenProcesado = operaciones.maximo(objImagenProcesado, opImg);
                    objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenProcesado);
                }
            }
            if(operacion.equals("Minimo")) {
                // si no hay operador imagen, minimo constante
                if(opImg == null) {
                    Operaciones operaciones = new Operaciones();
                    objImagenProcesado = operaciones.minimo(objImagenProcesado, constante);
                    objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenProcesado);
                } else { // si hay operador imagen, minimo imagen
                    Operaciones operaciones = new Operaciones();
                    objImagenProcesado = operaciones.minimo(objImagenProcesado, opImg);
                    objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenProcesado);
                }
            }
        }
    }
    
    class HiloBordeSobel extends Thread {
        @Override
        public void run() {
            setAtras();
            AxpherPicture.barraProgreso.setValue(45);
            objImagenProcesado.setFormato(objImagenProcesado.getFormato());
            objImagenProcesado.setM(objImagenProcesado.getM());
            objImagenProcesado.setN(objImagenProcesado.getN());
            objImagenProcesado.setNivelIntensidad(objImagenProcesado.getNivelIntensidad());
            short matrizGris[][] = new short[objImagenProcesado.getN()][objImagenProcesado.getM()];
            for(int i = 0; i < matrizGris.length; i++) {
                for(int j = 0; j < matrizGris[0].length; j++) {
                    matrizGris[i][j] = objImagenProcesado.getMatrizGris()[i][j];
                }
            }
            objImagenProcesado.setMatrizGris(matrizGris);
            // objeto de filtrado
            objFiltro = new FiltroNoise(objImagenProcesado);
            // aplica el filtro de sobel
            objFiltro.filtroSobel(56);
            AxpherPicture.barraProgreso.setValue(80);
            objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenProcesado);
            AxpherPicture.barraProgreso.setValue(100);
            try {
                sleep(512);
            } catch (InterruptedException ex) {
                System.err.println("Error al dormir Hilo umbralizacion");
            }
            AxpherPicture.barraProgreso.setValue(0);
        }
    }
    
    class HiloBordeCany extends Thread {
        
        private int kernel;
        private int umbral1, umbral2;
        private boolean umbralizado;
        
        public void setKernel(int kernel) {
            this.kernel = kernel;
        }
        
        public void setUmbral1(int umbral1) {
            this.umbral1 = umbral1;
        }
        
        public void setUmbral2(int umbral2) {
            this.umbral2 = umbral2;
        }
        
        public void setUmbralizado(boolean umbralizado) {
            this.umbralizado = umbralizado;
        }
        
        @Override
        public void run() {
            setAtras();
            AxpherPicture.barraProgreso.setValue(45);
            objImagenProcesado.setFormato(objImagenProcesado.getFormato());
            objImagenProcesado.setM(objImagenProcesado.getM());
            objImagenProcesado.setN(objImagenProcesado.getN());
            objImagenProcesado.setNivelIntensidad(objImagenProcesado.getNivelIntensidad());
            short matrizGris[][] = new short[objImagenProcesado.getN()][objImagenProcesado.getM()];
            for(int i = 0; i < matrizGris.length; i++) {
                for(int j = 0; j < matrizGris[0].length; j++) {
                    matrizGris[i][j] = objImagenProcesado.getMatrizGris()[i][j];
                }
            }
            objImagenProcesado.setMatrizGris(matrizGris);
            // canny
            // ----------------------------->
            Canny objCanny = new Canny();
            if(!umbralizado) {
                objCanny.calculoCanny(objImagenProcesado, kernel);
                objImagenProcesado = objCanny.getImagen();
            } else {
                objCanny.calculoCanny(objImagenProcesado, kernel, umbral1, umbral2);
                objImagenProcesado = objCanny.getImagen();
            }
            // ----------------------------->
            AxpherPicture.barraProgreso.setValue(80);
            objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagenProcesado);
            AxpherPicture.barraProgreso.setValue(100);
            try {
                sleep(512);
            } catch (InterruptedException ex) {
                System.err.println("Error al dormir Hilo canny");
            }
            AxpherPicture.barraProgreso.setValue(0);
        }
    }
    
    class HiloSegmentarKmeans extends Thread {
        
        private boolean automatico;
        
        public HiloSegmentarKmeans(boolean automatico) {
            this.automatico = automatico;
        }
        
        @Override
        public void run() {
            if(automatico) {
                segmentacionKmeans = new Segmentacion(objImagenProcesado);
                segmentacionKmeans.k_means();
            } else {
                String valor = JOptionPane.showInputDialog(objVentanaAxpherPicture, "Ingrese el numero de centroides", "Segmentacion Kmeans", JOptionPane.INFORMATION_MESSAGE);
                int k = Integer.parseInt(valor);
                segmentacionKmeans = new Segmentacion(objImagenProcesado);
                segmentacionKmeans.k_means(k);
            }
            AxpherPicture.barraProgreso.setValue(100);
            try {
                sleep(512);
            } catch (InterruptedException ex) {
                System.err.println("Error al dormir Hilo canny");
            }
            AxpherPicture.barraProgreso.setValue(0);
            imgCerebro = objImagenProcesado;
            new SegmentarCerebro(objVentanaAxpherPicture).setVisible(true);
        }
    }
}
