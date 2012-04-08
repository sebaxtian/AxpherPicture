/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controlador;

import gui.AxpherPicture;
import imagen.Histograma;
import imagen.Imagen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
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
    private File archivoImagen;
    private Imagen objImagen;
    private Histograma objHistograma;
    
    public ControladorImagen(AxpherPicture objVentana) {
        this.objVentanaAxpherPicture = objVentana;
        this.objVentanaHistograma = new gui.Histograma();
        this.objVentanaAxpherPicture.menuItemAbrir.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemGuardar.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemSalir.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemVerHistograma.addActionListener(this);
        this.objVentanaAxpherPicture.menuItemVerImagen.addActionListener(this);
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
}
