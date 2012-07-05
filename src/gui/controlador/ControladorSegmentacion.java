/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controlador;

import gui.AxpherPicture;
import gui.SegmentarCerebro;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;

/**
 *
 * @author sebaxtian
 */
public class ControladorSegmentacion implements ActionListener {
    
    private AxpherPicture ventanaPrincipal;
    private SegmentarCerebro ventanaSegmentacion;
    
    public ControladorSegmentacion(SegmentarCerebro ventanaSegmentacion) {
        this.ventanaSegmentacion = ventanaSegmentacion;
        this.ventanaPrincipal = ventanaSegmentacion.ventanaPrincipal;
        // agrega los listener
        this.ventanaSegmentacion.btnGuardar.addActionListener(this);
        this.ventanaSegmentacion.btnMateriaBlanca.addActionListener(this);
        this.ventanaSegmentacion.btnMateriaGris.addActionListener(this);
        this.ventanaSegmentacion.btnVisualizar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Visualizar")) {
            System.out.println("Click en Visualizar");
        }
        if(e.getActionCommand().equals("Materia Blanca")) {
            System.out.println("Click en Materia Blanca");
        }
        if(e.getActionCommand().equals("Materia Gris")) {
            System.out.println("Click en Materia Gris");
        }
        if(e.getActionCommand().equals("Guardar")) {
            System.out.println("Click en Guardar");
        }
    }
}
