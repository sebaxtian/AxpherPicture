/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.controlador.ControladorImagen;
import java.awt.BorderLayout;

/**
 *
 * @author sebaxtian
 */
public class AxpherPicture extends javax.swing.JFrame {
    
    public CanvasImagen canvasImagen;

    /**
     * Creates new form AxpherPicture
     */
    public AxpherPicture() {
        initComponents();
        canvasImagen = new CanvasImagen();
        panelCanvas.add(canvasImagen, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        new ControladorImagen(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelEast = new javax.swing.JPanel();
        panelCanvas = new javax.swing.JPanel();
        panelCenter = new javax.swing.JPanel();
        panelOperaciones = new javax.swing.JPanel();
        panelPropiedades = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        labelArchivo = new javax.swing.JLabel();
        labelFormato = new javax.swing.JLabel();
        labelIntensidad = new javax.swing.JLabel();
        labelAncho = new javax.swing.JLabel();
        labelAlto = new javax.swing.JLabel();
        barraProgreso = new javax.swing.JProgressBar();
        barraMenus = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        menuItemAbrir = new javax.swing.JMenuItem();
        menuItemGuardar = new javax.swing.JMenuItem();
        menuSeparador = new javax.swing.JPopupMenu.Separator();
        menuItemSalir = new javax.swing.JMenuItem();
        menuImagen = new javax.swing.JMenu();
        menuVer = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("AxpherPicture");
        setResizable(false);

        panelEast.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Previsualizar", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        panelEast.setMaximumSize(new java.awt.Dimension(512, 512));
        panelEast.setMinimumSize(new java.awt.Dimension(512, 512));
        panelEast.setPreferredSize(new java.awt.Dimension(512, 512));

        panelCanvas.setBackground(new java.awt.Color(254, 254, 254));
        panelCanvas.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout panelEastLayout = new javax.swing.GroupLayout(panelEast);
        panelEast.setLayout(panelEastLayout);
        panelEastLayout.setHorizontalGroup(
            panelEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEastLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCanvas, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelEastLayout.setVerticalGroup(
            panelEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEastLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCanvas, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(panelEast, java.awt.BorderLayout.EAST);

        panelCenter.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Imagen", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        panelOperaciones.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panelOperaciones.setPreferredSize(new java.awt.Dimension(512, 512));

        javax.swing.GroupLayout panelOperacionesLayout = new javax.swing.GroupLayout(panelOperaciones);
        panelOperaciones.setLayout(panelOperacionesLayout);
        panelOperacionesLayout.setHorizontalGroup(
            panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelOperacionesLayout.setVerticalGroup(
            panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 348, Short.MAX_VALUE)
        );

        panelPropiedades.setBorder(javax.swing.BorderFactory.createTitledBorder("Propiedades"));

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel1.setText("Archivo:");

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel2.setText("Formato:");

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel3.setText("Intensidad:");

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel4.setText("Ancho:");

        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel5.setText("Alto:");

        javax.swing.GroupLayout panelPropiedadesLayout = new javax.swing.GroupLayout(panelPropiedades);
        panelPropiedades.setLayout(panelPropiedadesLayout);
        panelPropiedadesLayout.setHorizontalGroup(
            panelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPropiedadesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPropiedadesLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelArchivo)
                        .addContainerGap(415, Short.MAX_VALUE))
                    .addGroup(panelPropiedadesLayout.createSequentialGroup()
                        .addGroup(panelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPropiedadesLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelFormato))
                            .addGroup(panelPropiedadesLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelIntensidad)))
                        .addGap(194, 194, 194)
                        .addGroup(panelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelAncho)
                            .addComponent(labelAlto))
                        .addContainerGap())))
        );
        panelPropiedadesLayout.setVerticalGroup(
            panelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPropiedadesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(labelArchivo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(labelFormato)
                    .addComponent(labelAncho))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(panelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(labelIntensidad)
                    .addComponent(labelAlto))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelCenterLayout = new javax.swing.GroupLayout(panelCenter);
        panelCenter.setLayout(panelCenterLayout);
        panelCenterLayout.setHorizontalGroup(
            panelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCenterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelPropiedades, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelOperaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelCenterLayout.setVerticalGroup(
            panelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCenterLayout.createSequentialGroup()
                .addComponent(panelPropiedades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelOperaciones, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(panelCenter, java.awt.BorderLayout.CENTER);
        getContentPane().add(barraProgreso, java.awt.BorderLayout.SOUTH);

        menuArchivo.setText("Archivo");

        menuItemAbrir.setText("Abrir ...");
        menuArchivo.add(menuItemAbrir);

        menuItemGuardar.setText("Guardar ...");
        menuArchivo.add(menuItemGuardar);
        menuArchivo.add(menuSeparador);

        menuItemSalir.setText("Salir");
        menuArchivo.add(menuItemSalir);

        barraMenus.add(menuArchivo);

        menuImagen.setText("Imagen");
        barraMenus.add(menuImagen);

        menuVer.setText("Ver");
        barraMenus.add(menuVer);

        setJMenuBar(barraMenus);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AxpherPicture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AxpherPicture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AxpherPicture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AxpherPicture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new AxpherPicture().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar barraMenus;
    public static javax.swing.JProgressBar barraProgreso;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    public javax.swing.JLabel labelAlto;
    public javax.swing.JLabel labelAncho;
    public javax.swing.JLabel labelArchivo;
    public javax.swing.JLabel labelFormato;
    public javax.swing.JLabel labelIntensidad;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenu menuImagen;
    public javax.swing.JMenuItem menuItemAbrir;
    public javax.swing.JMenuItem menuItemGuardar;
    public javax.swing.JMenuItem menuItemSalir;
    private javax.swing.JPopupMenu.Separator menuSeparador;
    private javax.swing.JMenu menuVer;
    private javax.swing.JPanel panelCanvas;
    private javax.swing.JPanel panelCenter;
    private javax.swing.JPanel panelEast;
    private javax.swing.JPanel panelOperaciones;
    private javax.swing.JPanel panelPropiedades;
    // End of variables declaration//GEN-END:variables
}
