/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controlador;

import gui.AxpherPicture;
import imagen.Imagen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Clase controlador para una imagen.
 * 
 * @author sebaxtian
 */


public class ControladorImagen implements ActionListener {
    
    private AxpherPicture objVentana;
    private File archivoImagen;
    private Imagen objImagen;
    
    public ControladorImagen(AxpherPicture objVentana) {
        this.objVentana = objVentana;
        this.objVentana.menuItemAbrir.addActionListener(this);
        this.objVentana.menuItemGuardar.addActionListener(this);
        this.objVentana.menuItemSalir.addActionListener(this);
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
            objVentana.dispose();
        }
    }
    
    private void abrirArchivoImagen() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PGM & PPM", "pgm", "ppm");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        int respuesta = fileChooser.showOpenDialog(objVentana);
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
        int respuesta = fileChooser.showSaveDialog(objVentana);
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
    
    class HiloAbreArchivoImagen extends Thread {
        @Override
        public void run() {
            AxpherPicture.barraProgreso.setValue(25);
            objImagen = new Imagen(archivoImagen.getAbsolutePath());
            objVentana.labelArchivo.setText(""+archivoImagen.getName());
            objVentana.labelFormato.setText(""+objImagen.getFormato());
            objVentana.labelIntensidad.setText(""+objImagen.getNivelIntensidad());
            objVentana.labelAncho.setText(""+objImagen.getM());
            objVentana.labelAlto.setText(""+objImagen.getN());
            AxpherPicture.barraProgreso.setValue(80);
            objVentana.canvasImagen.pintarImagen(objImagen);
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
                System.err.println("Error al dormir Hilo abrir archivo de imagen");
            }
            AxpherPicture.barraProgreso.setValue(0);
        }
    }
}
