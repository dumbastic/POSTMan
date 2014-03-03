package pos.view.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by dumbastic on 02/03/14.
 * this class is used to simulate the payment authorization for card payment
 */
public class PaymentAuthorization extends Dialog {

    String PIN;
    Timer timer;
    JProgressBar progressBar;
    int counter = 5;

    public PaymentAuthorization(Frame f, String PIN){
        super(f, "Payment Authorization", true);
        this.PIN = PIN;
    }

    public void show(){
        this.add("North", new Label("Authorizing Payment...", Label.CENTER));

        progressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, counter);
        progressBar.setPreferredSize(new Dimension(250, 30));
        progressBar.setValue(counter);
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer_actionPerformed(e);
            }
        };
        timer = new Timer(1000, listener);
        timer.start();
        this.add(progressBar);

        this.setSize(new Dimension(300, 100));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        super.show();
    }

    void this_windowClosing(WindowEvent e) {
        this.dispose();
    }

    /**
     * this method will be fired for each timer tick event
     * after the time is 0, then the payment will finish
     * @param e
     */
    void timer_actionPerformed(ActionEvent e) {
        counter--;
        progressBar.setValue(counter);
        if (counter<1) {
            if (!PIN.isEmpty())
                JOptionPane.showMessageDialog(null, "Card is authorized. Thank you for shopping with us...");
            else
                JOptionPane.showMessageDialog(null, "Your PIN is incorrect!!");
            timer.stop();
            this.dispose();
        }
    }
}
