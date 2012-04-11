/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controlador;

import gui.AxpherPicture;
import gui.PanelUmbralizacion;
import imagen.Histograma;
import imagen.Imagen;
import imagen.Umbralizacion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Clase controlador para una imagen.
 * 
 * @author sebaxtian
 */


public class ControladorImagen implements ActionListener {
    
    private AxpherPicture objVentanaAxpherPicture;
    private gui.Histograma objVentanaHistograma;
    private PanelUmbralizacion objPanelUmbral;
    private File archivoImagen;
    private Imagen objImagen;
    private Histograma objHistograma;
    private Umbralizacion objUmbral;
    
    public ControladorImagen(AxpherPicture objVentana) {
        this.objVentanaAxpherPicture = objVentana;
        this.objVentanaHistograma = new gui.Histograma();
        this.objVentanaAxpherPicture.menuItemAbrir.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemGuardar.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemSalir.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemVerHistograma.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemVerImagen.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemUmbral.addActionListener(this);
        this.objVentanaHistograma.btnGuardarHistograma.addActionListener(this);
        this.objVentanaAxpherPicture.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Cierra ventana");
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Abrir ...")) {
            System.out.println("Abre archivo de imagen");
            abrirArchivoImagen();
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
        if(e.getActionCommand().equals("Imagen")) {
            System.out.println("Ver imagen");
        }
        if(e.getSource().equals(objVentanaHistograma.btnGuardarHistograma)) {
            System.out.println("Guardar histograma");
            guardarArchivoHistograma();
        }
        if(e.getActionCommand().equals("Umbralizacion")) {
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
    }
    
    private void abrirArchivoImagen() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PGM & PPM", "pgm", "ppm");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        int respuesta = fileChooser.showOpenDialog(objVentanaAxpherPicture);
        if(respuesta == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().exists()) {
            archivoImagen = fileChooser.getSelectedFile();
            //crea el objeto Imagen
            HiloAbreArchivoImagen hiloAbreArchivoImagen = new HiloAbreArchivoImagen();
            hiloAbreArchivoImagen.start();
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
    
    class HiloAbreArchivoImagen extends Thread {
        @Override
        public void run() {
            AxpherPicture.barraProgreso.setValue(25);
            objImagen = new Imagen(archivoImagen.getAbsolutePath());
            objVentanaAxpherPicture.labelArchivo.setText(""+archivoImagen.getName());
            objVentanaAxpherPicture.labelFormato.setText(""+objImagen.getFormato());
            objVentanaAxpherPicture.labelIntensidad.setText(""+objImagen.getNivelIntensidad());
            objVentanaAxpherPicture.labelAncho.setText(""+objImagen.getM());
            objVentanaAxpherPicture.labelAlto.setText(""+objImagen.getN());
            AxpherPicture.barraProgreso.setValue(80);
            objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagen);
            AxpherPicture.barraProgreso.setValue(100);
            try {
                sleep(512);
            } catch (InterruptedException ex) {
                System.err.println("Error al dormir Hilo abrir archivo de imagen");
            }
            AxpherPicture.barraProgreso.setValue(0);
        }
    }
    
    class HiloGuardaArchivoImagen extends Thread {
        @Override
        public void run() {
            AxpherPicture.barraProgreso.setValue(25);
            objImagen.guardarImagen(archivoImagen.getAbsolutePath());
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
            objHistograma = new Histograma(objImagen);
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
            objHistograma = new Histograma(objImagen);
            AxpherPicture.barraProgreso.setValue(50);
            objUmbral = new Umbralizacion(objHistograma, idMetodo);
            if(objImagen.getFormato().equals("P2")) {
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
            if(objImagen.getFormato().equals("P2")) {
                objImagen.setNivelIntensidad(1);
                short matrizGris[][] = objImagen.getMatrizGris();
                int valorUmbral = Integer.parseInt(umbral);
                for(int i = 0; i < objImagen.getN(); i++) {
                    for(int j = 0; j < objImagen.getM(); j++) {
                        if(matrizGris[i][j] < valorUmbral) {
                            matrizGris[i][j] = 0;
                        } else {
                            matrizGris[i][j] = 1;
                        }
                    }
                }
                objImagen.setMatrizGris(matrizGris);
                objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagen);
            } else {
                objImagen.setNivelIntensidad(1);
                short matrizR[][] = objImagen.getMatrizR();
                short matrizG[][] = objImagen.getMatrizG();
                short matrizB[][] = objImagen.getMatrizB();
                StringTokenizer stringToken = new StringTokenizer(umbral, ",");
                int valorUmbralR = Integer.parseInt(stringToken.nextToken());
                int valorUmbralG = Integer.parseInt(stringToken.nextToken());
                int valorUmbralB = Integer.parseInt(stringToken.nextToken());
                for(int i = 0; i < objImagen.getN(); i++) {
                    for(int j = 0; j < objImagen.getM(); j++) {
                        //canal R
                        if(matrizR[i][j] < valorUmbralR) {
                            matrizR[i][j] = 0;
                        } else {
                            matrizR[i][j] = 1;
                        }
                        //canal G
                        if(matrizG[i][j] < valorUmbralG) {
                            matrizG[i][j] = 0;
                        } else {
                            matrizG[i][j] = 1;
                        }
                        //canal B
                        if(matrizB[i][j] < valorUmbralB) {
                            matrizB[i][j] = 0;
                        } else {
                            matrizB[i][j] = 1;
                        }
                    }
                }
                objImagen.setMatrizR(matrizR);
                objImagen.setMatrizG(matrizG);
                objImagen.setMatrizB(matrizB);
                objVentanaAxpherPicture.canvasImagen.pintarImagen(objImagen);
            }
        }
    }
}
