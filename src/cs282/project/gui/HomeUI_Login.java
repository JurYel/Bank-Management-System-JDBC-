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
import com.sun.glass.events.KeyEvent;
import cs282.project.core.Account;
import cs282.project.core.Employee;
import cs282.project.dao.AccountDAO;
import cs282.project.dao.EmployeeDAO;
import cs282.project.core.UserLoginHistory;
import cs282.project.dao.UserLoginHistoryDAO;
import cs282.project.core.Branch;
import cs282.project.dao.BranchDAO;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class HomeUI_Login extends javax.swing.JFrame {

    /**
     * Creates new form HomeUI_Login
     */
    private AccountDAO accDAO;
    private EmployeeDAO empDAO;
    private UserLoginHistoryDAO userLogDAO;
    private BranchDAO branchDAO;
    public HomeUI_Login() {
        try{
            accDAO = new AccountDAO();
            empDAO = new EmployeeDAO();
            userLogDAO = new UserLoginHistoryDAO();
            branchDAO = new BranchDAO();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        initComponents();
        setLocationRelativeTo(null);
        populateGUI();
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jtxt_AccountID = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jpf_Password = new javax.swing.JPasswordField();
        jCheckBox_ShowPassword = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jbtn_Login = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBox_Branch = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 204));

        jLabel1.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("BANK");
        jLabel1.setToolTipText("");

        jLabel2.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("MANAGEMENT");
        jLabel2.setToolTipText("");

        jLabel3.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("SYSTEM");
        jLabel3.setToolTipText("");

        jPanel2.setBackground(new java.awt.Color(0, 102, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel5.setFont(new java.awt.Font("Alata", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Account ID:");

        jtxt_AccountID.setFont(new java.awt.Font("Alata", 0, 14)); // NOI18N
        jtxt_AccountID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxt_AccountID.setToolTipText("");

        jLabel6.setFont(new java.awt.Font("Alata", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Password:");

        jpf_Password.setFont(new java.awt.Font("Alata", 0, 14)); // NOI18N
        jpf_Password.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jpf_Password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jpf_PasswordKeyPressed(evt);
            }
        });

        jCheckBox_ShowPassword.setFont(new java.awt.Font("Alata", 0, 12)); // NOI18N
        jCheckBox_ShowPassword.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox_ShowPassword.setText("Show Password");
        jCheckBox_ShowPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_ShowPasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jpf_Password, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtxt_AccountID, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCheckBox_ShowPassword)
                .addGap(88, 88, 88))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jtxt_AccountID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jpf_Password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox_ShowPassword)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Alata", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("LOGIN");

        jbtn_Login.setBackground(new java.awt.Color(204, 255, 255));
        jbtn_Login.setFont(new java.awt.Font("Alata", 0, 14)); // NOI18N
        jbtn_Login.setText("Login");
        jbtn_Login.setToolTipText("");
        jbtn_Login.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtn_Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_LoginActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Alata", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Don't have an account yet?");

        jLabel8.setFont(new java.awt.Font("Alata", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 255, 255));
        jLabel8.setText("Click here to register");
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });

        jComboBox_Branch.setFont(new java.awt.Font("Alata", 0, 14)); // NOI18N
        jComboBox_Branch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pick a Branch" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox_Branch, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(227, 227, 227)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addContainerGap(141, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(210, 210, 210)
                .addComponent(jbtn_Login, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addGap(97, 97, 97))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(78, 78, 78))))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox_Branch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(29, 29, 29)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbtn_Login)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void jbtn_LoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_LoginActionPerformed
        try{
            String accountID = jtxt_AccountID.getText().trim();
            String pass = jpf_Password.getText().trim();
            String branch = jComboBox_Branch.getSelectedItem().toString();
            
            if(branch.equals("Pick a Branch")){
                JOptionPane.showMessageDialog(HomeUI_Login.this,"Please pick what branch the bank is.","Login Error",JOptionPane.ERROR_MESSAGE);
            }
            else{
                if(!(accDAO.checkAccountID(accountID)) && !(empDAO.checkEmployeeID(accountID))){
                    JOptionPane.showMessageDialog(HomeUI_Login.this,"Account ID does not exists!","Login Error",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    if(empDAO.checkEmployeeID(accountID)){

                        if(empDAO.checkPassword(accountID, pass)){
                            HomeUI homeUI = new HomeUI();
                            setVisible(false);
                            homeUI.setVisible(true);
                        }
                        else{
                            JOptionPane.showMessageDialog(HomeUI_Login.this,"Password incorrect","Login Error",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    if(accDAO.checkAccountID(accountID)){
                        if(accDAO.checkAccountPin(accountID, pass)){
                            /* Set ATM UI visible to user */
                            UserLoginHistory userLog = new UserLoginHistory(accountID,null);
                            userLogDAO.recordLogin(userLog);

                            HomeUI_ForTransactions fortrans = new HomeUI_ForTransactions(accountID);
                            setVisible(false);
                            fortrans.setVisible(true);
                        }
                        else{
                            JOptionPane.showMessageDialog(HomeUI_Login.this,"Account PIN is incorrect","Login Error",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }

            }
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
    }//GEN-LAST:event_jbtn_LoginActionPerformed

    private void jCheckBox_ShowPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_ShowPasswordActionPerformed
        if(jCheckBox_ShowPassword.isSelected()){
            jpf_Password.setEchoChar((char)0);
        }
        else{
            jpf_Password.setEchoChar('*');
        }
    }//GEN-LAST:event_jCheckBox_ShowPasswordActionPerformed

    private void jpf_PasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jpf_PasswordKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            try{
                String accountID = jtxt_AccountID.getText().trim();
                String pass = jpf_Password.getText().trim();
                String branch = jComboBox_Branch.getSelectedItem().toString();
                
                if(branch.equals("Pick a Branch")){
                    JOptionPane.showMessageDialog(HomeUI_Login.this,"Please pick what branch the bank is.","Login Error",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    if(!(accDAO.checkAccountID(accountID)) && !(empDAO.checkEmployeeID(accountID))){
                        JOptionPane.showMessageDialog(HomeUI_Login.this,"Account ID does not exists!","Login Error",JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        if(empDAO.checkEmployeeID(accountID)){

                            if(empDAO.checkPassword(accountID, pass)){
                                HomeUI homeUI = new HomeUI();
                                setVisible(false);
                                homeUI.setVisible(true);
                            }
                            else{
                                JOptionPane.showMessageDialog(HomeUI_Login.this,"Password incorrect","Login Error",JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        if(accDAO.checkAccountID(accountID)){
                            if(accDAO.checkAccountPin(accountID, pass)){
                                /* Set ATM UI visible to user */
                                UserLoginHistory userLog = new UserLoginHistory(accountID,null);
                                userLogDAO.recordLogin(userLog);

                                HomeUI_ForTransactions fortrans = new HomeUI_ForTransactions(accountID);
                                setVisible(false);
                                fortrans.setVisible(true);
                            }
                            else{
                                JOptionPane.showMessageDialog(HomeUI_Login.this,"Account PIN is incorrect","Login Error",JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }

                }
            }
            catch(Exception exc){
                exc.printStackTrace();
            }
        }
    }//GEN-LAST:event_jpf_PasswordKeyPressed

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        HomeUI_Register register = new HomeUI_Register();
        setVisible(false);
        register.setVisible(true);
    }//GEN-LAST:event_jLabel8MouseClicked

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
            java.util.logging.Logger.getLogger(HomeUI_Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeUI_Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeUI_Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeUI_Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeUI_Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox_ShowPassword;
    private javax.swing.JComboBox<String> jComboBox_Branch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton jbtn_Login;
    private javax.swing.JPasswordField jpf_Password;
    private javax.swing.JTextField jtxt_AccountID;
    // End of variables declaration//GEN-END:variables

    private void populateGUI(){
        try{
            ArrayList<Branch> branches = branchDAO.getAllBranches();
            
            for(int i = 0;i<branches.size();i++){
                jComboBox_Branch.addItem(branches.get(i).getCity());
            }
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
    }
}