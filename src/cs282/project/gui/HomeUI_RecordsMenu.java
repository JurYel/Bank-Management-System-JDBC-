/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs282.project.gui;

/**
 *
 * @author Jur Yel
 */

public class HomeUI_RecordsMenu extends javax.swing.JDialog {

    /**
     * Creates new form HomeUI_RecordsMenu
     */
    
    private HomeUI homeUI;
    public HomeUI_RecordsMenu(HomeUI ui){
        initComponents();
        setLocationRelativeTo(ui);
        homeUI = ui;
    }
    
    public HomeUI_RecordsMenu(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox_Records = new javax.swing.JComboBox<>();
        jbtn_Proceed = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 204));

        jLabel1.setFont(new java.awt.Font("Alata", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Bank Records ");

        jComboBox_Records.setFont(new java.awt.Font("Alata", 0, 14)); // NOI18N
        jComboBox_Records.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Registered Banks", "Registered Branches", "Bank Accounts", "Employees", "Customers" }));

        jbtn_Proceed.setBackground(new java.awt.Color(0, 0, 0));
        jbtn_Proceed.setFont(new java.awt.Font("Alata", 0, 14)); // NOI18N
        jbtn_Proceed.setForeground(new java.awt.Color(255, 255, 255));
        jbtn_Proceed.setText("Proceed");
        jbtn_Proceed.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtn_Proceed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_ProceedActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox_Records, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(jbtn_Proceed)))))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(jComboBox_Records, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtn_Proceed)
                .addGap(36, 36, 36))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtn_ProceedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_ProceedActionPerformed
        try{
            String records = jComboBox_Records.getSelectedItem().toString();
            
            if(records.equals("Registered Banks")){
                BankUI bankUI = new BankUI();
                setVisible(false);
                homeUI.setVisible(false);
                bankUI.setVisible(true);
            }
            else if(records.equals("Registered Branches")){
                BranchUI branchUI = new BranchUI();
                setVisible(false);
                homeUI.setVisible(false);
                branchUI.setVisible(true);
            }
            else if(records.equals("Bank Accounts")){
                AccountUI accUI = new AccountUI();
                setVisible(false);
                homeUI.setVisible(false);
                accUI.setVisible(true);
            }
            else if(records.equals("Employees")){
                EmployeeUI empUI = new EmployeeUI();
                setVisible(false);
                homeUI.setVisible(false);
                empUI.setVisible(true);
            }
            else if(records.equals("Customers")){
                CustomerUI custUI = new CustomerUI();
                setVisible(false);
                homeUI.setVisible(false);
                custUI.setVisible(true);
            }
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
    }//GEN-LAST:event_jbtn_ProceedActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomeUI_RecordsMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeUI_RecordsMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeUI_RecordsMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeUI_RecordsMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                HomeUI_RecordsMenu dialog = new HomeUI_RecordsMenu(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox_Records;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbtn_Proceed;
    // End of variables declaration//GEN-END:variables
}
