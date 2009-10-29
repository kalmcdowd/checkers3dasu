/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * WelcomeScreen.java
 *
 * Created on Oct 15, 2009, 9:41:21 AM
 */

package checkers;

/**
 *
 * @author clarkcito
 */
public class WelcomeScreen extends javax.swing.JFrame
{
    private static final int NEW = 0;
    private static final int ADMIN = 1;
    private static final int STATS = 2;

    /** Creates new form WelcomeScreen */
    public WelcomeScreen()
    {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        WelcomeLabel = new javax.swing.JLabel();
        NewGameButton = new javax.swing.JRadioButton();
        LoadGameButton = new javax.swing.JRadioButton();
        StatsButton = new javax.swing.JRadioButton();
        AdminButton = new javax.swing.JRadioButton();
        NextButton = new javax.swing.JButton();
        ExitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(440, 330));
        setResizable(false);
        getContentPane().setLayout(null);

        WelcomeLabel.setFont(new java.awt.Font("Old English Text MT", 0, 40)); // NOI18N
        WelcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        WelcomeLabel.setText("Welcome to 3D Checkers");
        getContentPane().add(WelcomeLabel);
        WelcomeLabel.setBounds(18, 12, 403, 40);

        buttonGroup.add(NewGameButton);
        NewGameButton.setFont(new java.awt.Font("Old English Text MT", 0, 16)); // NOI18N
        NewGameButton.setText("New Game");
        getContentPane().add(NewGameButton);
        NewGameButton.setBounds(160, 90, 140, 24);

        buttonGroup.add(LoadGameButton);
        LoadGameButton.setFont(new java.awt.Font("Old English Text MT", 0, 16)); // NOI18N
        LoadGameButton.setText("Load Game");
        LoadGameButton.setEnabled(false);
        getContentPane().add(LoadGameButton);
        LoadGameButton.setBounds(160, 120, 140, 24);

        buttonGroup.add(StatsButton);
        StatsButton.setFont(new java.awt.Font("Old English Text MT", 0, 16)); // NOI18N
        StatsButton.setText("Statistics");
        StatsButton.setEnabled(false);
        getContentPane().add(StatsButton);
        StatsButton.setBounds(160, 150, 140, 24);

        buttonGroup.add(AdminButton);
        AdminButton.setFont(new java.awt.Font("Old English Text MT", 0, 16)); // NOI18N
        AdminButton.setText("Administration");
        AdminButton.setEnabled(false);
        getContentPane().add(AdminButton);
        AdminButton.setBounds(160, 180, 140, 24);

        NextButton.setFont(new java.awt.Font("Old English Text MT", 0, 16)); // NOI18N
        NextButton.setText("Next");
        NextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextButtonActionPerformed(evt);
            }
        });
        getContentPane().add(NextButton);
        NextButton.setBounds(240, 242, 68, 28);

        ExitButton.setFont(new java.awt.Font("Old English Text MT", 0, 16)); // NOI18N
        ExitButton.setText("Exit");
        ExitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitButtonActionPerformed(evt);
            }
        });
        getContentPane().add(ExitButton);
        ExitButton.setBounds(130, 242, 68, 28);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ExitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_ExitButtonActionPerformed

    private void NextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextButtonActionPerformed
        if (NewGameButton.isSelected())
        {
            new LoginScreen(NEW).setVisible(true);
            setVisible(false);
        }
    }//GEN-LAST:event_NextButtonActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new WelcomeScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton AdminButton;
    private javax.swing.JButton ExitButton;
    private javax.swing.JRadioButton LoadGameButton;
    private javax.swing.JRadioButton NewGameButton;
    private javax.swing.JButton NextButton;
    private javax.swing.JRadioButton StatsButton;
    private javax.swing.JLabel WelcomeLabel;
    private javax.swing.ButtonGroup buttonGroup;
    // End of variables declaration//GEN-END:variables

}
