/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controlador;

import gui.AxpherPicture;
import gui.CanvasImagen;
import gui.SegmentarCerebro;
import imagen.Histograma;
import imagen.Imagen;
import imagen.Operaciones;
import imagen.Umbralizacion;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author sebaxtian
 */
public class ControladorSegmentacion implements ActionListener {
    
    private AxpherPicture ventanaPrincipal;
    private SegmentarCerebro ventanaSegmentacion;
    private Imagen imgKmeans;
    private Imagen materiaBlanca;
    private Imagen materiaGris;
    private Imagen imgSalida;
    private File archivoImagen;
    
    public ControladorSegmentacion(SegmentarCerebro ventanaSegmentacion) {
        this.ventanaSegmentacion = ventanaSegmentacion;
        this.ventanaPrincipal = ventanaSegmentacion.ventanaPrincipal;
        // agrega los listener
        this.ventanaSegmentacion.btnGuardar.addActionListener(this);
        this.ventanaSegmentacion.btnMateriaBlanca.addActionListener(this);
        this.ventanaSegmentacion.btnMateriaGris.addActionListener(this);
        this.ventanaSegmentacion.btnVisualizar.addActionListener(this);
        this.ventanaSegmentacion.canvasKmeans.repaint();
        this.ventanaSegmentacion.canvasMateria.repaint();
        // actualiza el combo kmeans
        actualizaComboKmeans();
    }
    
    private void actualizaComboKmeans() {
        int numKmeans = ControladorImagen.segmentacionKmeans.getImagenKmeans().length;
        String[] elemKmeans = new String[numKmeans];
        System.out.println("Kmeans " + numKmeans);
        this.ventanaSegmentacion.comboKmeans.removeAllItems();
        for(int i = 0; i < numKmeans; i++) {
            elemKmeans[i] = ""+i;
            this.ventanaSegmentacion.comboKmeans.addItem(""+i);
        }
        this.ventanaSegmentacion.pack();
    }
    
    private void visualizaKmeans(int indiceImagenKmeans) {
        Imagen imgKmeansI = ControladorImagen.segmentacionKmeans.getImagenKmeans()[indiceImagenKmeans];
        System.out.println("Alto: "+imgKmeansI.getN());
        System.out.println("Ancho: "+imgKmeansI.getM());
        System.out.println("Intensidad: "+imgKmeansI.getNivelIntensidad());
        imgKmeansI.setFormato("P2");
        this.ventanaSegmentacion.canvasKmeans.pintarImagen(imgKmeansI);
        imgKmeans = imgKmeansI;
    }
    
    private Imagen segmentarMateriaBlanca(Imagen imgCerebro, Imagen imgKmeans) {
        Imagen materiaBlanca = new Imagen();
        // Imagen de la resta entre la imagen y el k-means del cerebro
        Operaciones operador = new Operaciones();
        Imagen imgResta = operador.resta(imgCerebro, imgKmeans);
        
        Histograma histograma = new Histograma(imgResta);
        Umbralizacion umbralizar = new Umbralizacion(histograma, 2); // usa metodo Otsu
        int umbral = umbralizar.getUmbralGris();
        
        // Imagen de materia blanca
        materiaBlanca.setN(imgCerebro.getN());
        materiaBlanca.setM(imgCerebro.getM());
        materiaBlanca.setNivelIntensidad(imgCerebro.getNivelIntensidad());
        materiaBlanca.setFormato(imgCerebro.getFormato());
        short matrizBlanca[][] = new short[imgCerebro.getN()][imgCerebro.getM()];
        
        // Extrae la materia blanca con el umbral
        for(int fila = 0; fila < imgResta.getN(); fila++) {
            for(int columna = 0; columna < imgResta.getM(); columna++) {
                short pixel = imgResta.getMatrizGris()[fila][columna];
                if(pixel > umbral) {
                    //pixel = (short)imgResta.getNivelIntensidad();
                }
                if(pixel < umbral) {
                    pixel = 0;
                }
                matrizBlanca[fila][columna] = pixel;
            }
        }
        
        // Asigna la matriz de materia blanca a la imagen
        materiaBlanca.setMatrizGris(matrizBlanca);
        
        return materiaBlanca;
    }
    
    private Imagen segmentarMateriaGris(Imagen imgCerebro, Imagen materiaBlanca) {
        Imagen materiaGris = new Imagen();
        // Realiza la resta entre la imagen del cerebro y la materia blanca
        Operaciones operador = new Operaciones();
        Imagen imgResta = operador.resta(imgCerebro, materiaBlanca);
        
        // Imagen de materia gris
        materiaGris.setN(imgCerebro.getN());
        materiaGris.setM(imgCerebro.getM());
        materiaGris.setNivelIntensidad(imgCerebro.getNivelIntensidad());
        materiaGris.setFormato(imgCerebro.getFormato());
        short matrizGris[][] = new short[imgCerebro.getN()][imgCerebro.getM()];
        
        // Extrae la matriz de materia gris
        for(int fila = 0; fila < materiaGris.getN(); fila++) {
            for(int columna = 0; columna < materiaGris.getM(); columna++) {
                short pixel = imgResta.getMatrizGris()[fila][columna];
                matrizGris[fila][columna] = pixel;
            }
        }
        
        // Asigna la matriz de materia gris a la imagen
        materiaGris.setMatrizGris(matrizGris);
        
        return materiaGris;
    }
    
    private void guardarArchivoImagen() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PGM & PPM", "pgm", "ppm");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        int respuesta = fileChooser.showSaveDialog(this.ventanaSegmentacion);
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
    
    class HiloGuardaArchivoImagen extends Thread {
        @Override
        public void run() {
            imgSalida.guardarImagen(archivoImagen.getAbsolutePath());
            try {
                sleep(512);
            } catch (InterruptedException ex) {
                System.err.println("Error al dormir Hilo guardar archivo de imagen");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Visualizar")) {
            System.out.println("Click en Visualizar");
            visualizaKmeans(this.ventanaSegmentacion.comboKmeans.getSelectedIndex());
        }
        if(e.getActionCommand().equals("Materia Blanca")) {
            System.out.println("Click en Materia Blanca");
            materiaBlanca = segmentarMateriaBlanca(ControladorImagen.imgCerebro, imgKmeans);
            this.ventanaSegmentacion.canvasMateria.pintarImagen(materiaBlanca);
            imgSalida = materiaBlanca;
        }
        if(e.getActionCommand().equals("Materia Gris")) {
            System.out.println("Click en Materia Gris");
            materiaGris = segmentarMateriaGris(ControladorImagen.imgCerebro, materiaBlanca);
            this.ventanaSegmentacion.canvasMateria.pintarImagen(materiaGris);
            imgSalida = materiaGris;
        }
        if(e.getActionCommand().equals("Guardar")) {
            System.out.println("Click en Guardar");
            guardarArchivoImagen();
        }
    }
}
