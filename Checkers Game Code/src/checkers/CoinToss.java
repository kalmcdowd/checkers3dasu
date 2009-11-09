/*
 * The CoinToss class uses a random generator to determine whether a coin lands
 * on heads or tails.  The player that wins the coin toss can select whether
 * they want to be the home or visitor player.
 *
 * Author:  Dave Clark
 */

package checkers;

import java.awt.Frame;
import java.util.Random;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class CoinToss extends javax.swing.JDialog
{
    private final int HEADS = 0;
    private final int TAILS = 1;
    private String player1;
    private String player2;
    private String tossWinner;
    private boolean p1Home;
    private int randomNumber;
    Random random;

    //load font
    Font oldEnglish_14 = loadFont(Font.PLAIN, 14);
    Font oldEnglish_16 = loadFont(Font.PLAIN, 16);
    Font oldEnglish_17 = loadFont(Font.PLAIN, 17);
    Font oldEnglish_18 = loadFont(Font.PLAIN, 18);

    /** Creates new form CoinToss */
    public CoinToss(Frame parent, boolean modal, String p1, String p2)
    {
        super(parent, modal);
        random = new Random();

        player1 = p1;
        player2 = p2;

        initComponents();
    }

    public boolean player1Home()
    {
        return p1Home;
    }

    private Font loadFont(int type, float size)
    {
        Font font = null;
        try
        {
            InputStream input = this.getClass().getResourceAsStream("/OLDENGL.TTF");
            font = Font.createFont(Font.PLAIN, input).deriveFont(type, size);
        }
        catch (IOException ioe)
        {
            System.err.println(ioe);
            System.exit(1);
        }
        catch (FontFormatException ffe)
        {
            System.err.println(ffe);
            System.exit(1);
        }
        return font;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headsTailsGroup = new javax.swing.ButtonGroup();
        homeVisitorGroup = new javax.swing.ButtonGroup();
        selectLabel = new javax.swing.JLabel();
        headsRadio = new javax.swing.JRadioButton();
        tailsRadio = new javax.swing.JRadioButton();
        flipButton = new javax.swing.JButton();
        infoLabel = new javax.swing.JLabel();
        homeRadio = new javax.swing.JRadioButton();
        visitorRadio = new javax.swing.JRadioButton();
        confirmButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Coin Toss");
        setMinimumSize(new java.awt.Dimension(400, 270));
        setModal(true);
        setResizable(false);
        getContentPane().setLayout(null);

        selectLabel.setFont(oldEnglish_18);
        selectLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        selectLabel.setText("<html><center>" + player1 + ",<br>please make a selection:</center></html>");
        getContentPane().add(selectLabel);
        selectLabel.setBounds(50, 70, 300, 60);

        headsTailsGroup.add(headsRadio);
        headsRadio.setFont(oldEnglish_14);
        headsRadio.setText("Heads");
        getContentPane().add(headsRadio);
        headsRadio.setBounds(110, 140, 60, 23);

        headsTailsGroup.add(tailsRadio);
        tailsRadio.setFont(oldEnglish_14);
        tailsRadio.setText("Tails");
        getContentPane().add(tailsRadio);
        tailsRadio.setBounds(220, 140, 60, 23);

        flipButton.setFont(oldEnglish_14);
        flipButton.setText("Flip It!");
        flipButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                flipButtonActionPerformed(evt);
            }
        });
        getContentPane().add(flipButton);
        flipButton.setBounds(160, 180, 80, 25);

        infoLabel.setFont(oldEnglish_17);
        infoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoLabel.setText("<html><center><font color=purple><bold>A coin toss will be used to<br>help determine the home and visitor players.</bold></font></center></html>");
        getContentPane().add(infoLabel);
        infoLabel.setBounds(50, 20, 300, 40);

        homeVisitorGroup.add(homeRadio);
        homeRadio.setFont(oldEnglish_14);
        homeRadio.setText("Home");
        getContentPane().add(homeRadio);
        homeRadio.setBounds(110, 140, 60, 23);
        homeRadio.setVisible(false);

        homeVisitorGroup.add(visitorRadio);
        visitorRadio.setFont(oldEnglish_14);
        visitorRadio.setText("Visitor");
        getContentPane().add(visitorRadio);
        visitorRadio.setBounds(220, 140, 60, 23);
        visitorRadio.setVisible(false);

        confirmButton.setFont(oldEnglish_14);
        confirmButton.setText("Confirm");
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmButtonActionPerformed(evt);
            }
        });
        getContentPane().add(confirmButton);
        confirmButton.setBounds(160, 180, 80, 25);
        confirmButton.setVisible(false);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void flipButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flipButtonActionPerformed
        
        randomNumber = random.nextInt(2);

        //coin lands on heads and player 1 selected heads
        if (randomNumber == HEADS && headsRadio.isSelected())
        {
            tossWinner = player1;
            infoLabel.setText("The coin landed on heads!");
        }
        //coin lands on heads and player 1 selected tails
        else if (randomNumber == HEADS && tailsRadio.isSelected())
        {
            tossWinner = player2;
            infoLabel.setText("The coin landed on heads!");
        }
        //coin lands on tails and player 1 selected heads
        else if (randomNumber == TAILS && headsRadio.isSelected())
        {
            tossWinner = player2;
            infoLabel.setText("The coin landed on tails!");
        }
        //coin lands on tails and player 1 selected tails
        else if (randomNumber == TAILS && tailsRadio.isSelected())
        {
            tossWinner = player1;
            infoLabel.setText("The coin landed on tails!");
        }

        //if the user didn't make a selection display an error
        if (!headsRadio.isSelected() && !tailsRadio.isSelected())
        {
            javax.swing.UIManager.put("OptionPane.messageFont", oldEnglish_16);
            JOptionPane.showMessageDialog(this, "Please make a selection", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        //prompt user for selection
        else
        {
            selectLabel.setText("<html>" + tossWinner + " has won the toss." + 
                      "<br>Please make a selection:");

            flipButton.setVisible(false);
            tailsRadio.setVisible(false);
            headsRadio.setVisible(false);
            confirmButton.setVisible(true);
            homeRadio.setVisible(true);
            visitorRadio.setVisible(true);
        }       
    }//GEN-LAST:event_flipButtonActionPerformed

    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmButtonActionPerformed

        //if the user didn't make a selection display an error
        if (!homeRadio.isSelected() && !visitorRadio.isSelected())
        {
            javax.swing.UIManager.put("OptionPane.messageFont", oldEnglish_16);
            JOptionPane.showMessageDialog(this, "Please make a selection",
                  "Error", JOptionPane.ERROR_MESSAGE);
        }
        //prompt user for selection
        else
        {
            //player 1 won the toss and selects home
            if (homeRadio.isSelected() && tossWinner.equals(player1))
                p1Home = true;
            //player 1 won the toss and selects visitor
            else if (visitorRadio.isSelected() && tossWinner.equals(player1))
                p1Home = false;
            //player 2 won the toss and selects visitor
            else if (visitorRadio.isSelected() && tossWinner.equals(player2))
                p1Home = true;
            //player 2 won the toss and selects home
            else if (homeRadio.isSelected() && tossWinner.equals(player2))
                p1Home = false;

            setVisible(false);
        }
    }//GEN-LAST:event_confirmButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton confirmButton;
    private javax.swing.JButton flipButton;
    private javax.swing.JRadioButton headsRadio;
    private javax.swing.ButtonGroup headsTailsGroup;
    private javax.swing.JRadioButton homeRadio;
    private javax.swing.ButtonGroup homeVisitorGroup;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JLabel selectLabel;
    private javax.swing.JRadioButton tailsRadio;
    private javax.swing.JRadioButton visitorRadio;
    // End of variables declaration//GEN-END:variables

}
