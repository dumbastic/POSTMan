package pos.view.gui;

import pos.model.memento.ShoppingCartCaretaker;
import pos.model.memento.ShoppingCartOriginator;
import pos.model.payment.*;
import pos.model.sales.CartItem;
import pos.model.command.*;
import pos.model.product.ProductCatalog;
import pos.model.sales.StoreTransactionVisitor;
import pos.model.sales.SalesOrderVisitor;
import pos.model.sales.ShoppingCart;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by dumbastic on 01/03/14.
 * this is the main UI class
 */
public class POSTerminalGUI extends JFrame {

    private JPanel productPanel;
    private JLabel productLabel;
    private JList productList;
    private JScrollPane productScrollPane;

    private JPanel cartPanel;
    private JLabel cartLabel;
    private JList cartList;
    private JScrollPane cartScrollPane;

    private JPanel paymentPanel;
    private JLabel paymentLabel, paymentAmountLabel;
    private JLabel paymentMethodLabel;
    private JComboBox paymentMethodComboBox;
    private JLabel totalAmountLabel;
    private JTextField totalAmountTextField;
    private JLabel returnAmountLabel;
    private JTextField returnAmountTextField;
    private JLabel codeOrPinLabel;
    private JPasswordField codeOrPinPasswordField;

    private ICommandHolder addItemButton, removeItemButton;
    private ICommand addCommand, removeCommand;

    private JButton undoButton, redoButton, checkOutButton, payButton;

    ShoppingCartCaretaker shoppingCartCaretaker;
    ShoppingCartOriginator shoppingCartOriginator;
    int currentCart;

    private ShoppingCart shoppingCart;

    public POSTerminalGUI() {
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * this method will initialize all the UI components
     */
    private void initComponents () {

        shoppingCartCaretaker = new ShoppingCartCaretaker();
        shoppingCartOriginator = new ShoppingCartOriginator();
        currentCart = 0;
        shoppingCart = new ShoppingCart();

        productPanel = new JPanel ();
        productLabel = new JLabel ();
        productList = new JList ();

        cartPanel = new JPanel ();
        cartLabel = new JLabel ();
        cartList = new JList ();

        paymentPanel = new JPanel ();
        paymentLabel = new JLabel ();
        paymentAmountLabel = new JLabel();
        paymentMethodLabel = new JLabel();
        totalAmountLabel = new JLabel();
        totalAmountTextField = new JTextField();
        returnAmountLabel = new JLabel();
        returnAmountTextField = new JTextField();
        codeOrPinLabel = new JLabel();
        codeOrPinPasswordField = new JPasswordField();
        
        getContentPane().setLayout(new FlowLayout());
        setTitle("Point Of Sales Terminal Manager (POSTMan)");
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                exitForm(evt);
            }
        }
        );

        productPanel.setPreferredSize(new Dimension(200, 330));
        productLabel.setPreferredSize(new Dimension(190, 14));
        productLabel.setText("Product Catalog");
        productPanel.add(productLabel);

        ListCellRenderer productListRenderer = new ProductListRenderer();
        productList.setCellRenderer(productListRenderer);
        productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                productList_valueChanged(e);
            }
        });

        //get product catalog from csv
        Object[] catalog = ProductCatalog.getInstance().getCatalog().keySet().toArray();
        Arrays.sort(catalog);
        productList.setListData(catalog);
        productScrollPane = new JScrollPane(productList);
        productScrollPane.setPreferredSize(new Dimension(190, 295));
        productPanel.add(productScrollPane);

        getContentPane().add(productPanel);

        cartPanel.setPreferredSize(new Dimension(230, 330));
        cartLabel.setPreferredSize(new Dimension(220, 14));
        cartLabel.setText("Shopping Cart");
        cartPanel.add(cartLabel);

        ListCellRenderer cartListRenderer = new CartListRenderer();
        cartList.setCellRenderer(cartListRenderer);
        cartList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cartList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                cartList_valueChanged(e);
            }
        });
        cartScrollPane = new JScrollPane(cartList);
        cartScrollPane.setPreferredSize(new Dimension(225, 190));
        cartPanel.add(cartScrollPane);

        addItemButton = new CommandButton("Add Item");
        ((CommandButton)addItemButton).setPreferredSize(new Dimension(110, 30));
        addCommand = new AddCommand(shoppingCart);
        addItemButton.setCommand(addCommand);
        ((CommandButton)addItemButton).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button_actionPerformed(e);
            }
        });
        ((CommandButton) addItemButton).setEnabled(false);
        cartPanel.add(((CommandButton)addItemButton));

        removeItemButton = new CommandButton("Remove Item");
        ((CommandButton)removeItemButton).setPreferredSize(new Dimension(110, 30));
        removeCommand = new RemoveCommand(shoppingCart);
        removeItemButton.setCommand(removeCommand);
        ((CommandButton)removeItemButton).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button_actionPerformed(e);
            }
        });
        ((CommandButton) removeItemButton).setEnabled(false);
        cartPanel.add(((CommandButton)removeItemButton));

        undoButton = new JButton("Undo Action");
        undoButton.setPreferredSize(new Dimension(110, 30));
        undoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                undoButton_actionPerformed(e);
            }
        });
        undoButton.setEnabled(false);
        cartPanel.add(undoButton);

        redoButton = new JButton("Redo Action");
        redoButton.setPreferredSize(new Dimension(110, 30));
        redoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                redoButton_actionPerformed(e);
            }
        });
        redoButton.setEnabled(false);
        cartPanel.add(redoButton);

        checkOutButton = new JButton("Check Out & Pay");
        checkOutButton.setPreferredSize(new Dimension(225, 30));
        checkOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkOutButton_actionPerformed(e);
            }
        });
        checkOutButton.setEnabled(false);
        cartPanel.add(checkOutButton);

        getContentPane().add(cartPanel);

        paymentPanel.setPreferredSize(new Dimension(200, 330));
        paymentPanel.setEnabled(false);

        paymentLabel.setPreferredSize(new Dimension(100, 60));
        paymentLabel.setText("<html><font color='red'>Total Price:<br>After Discount:<br>After VAT:</font></html>");
        paymentLabel.setVerticalAlignment(JLabel.TOP);
        paymentPanel.add(paymentLabel);

        paymentAmountLabel.setPreferredSize(new Dimension(90, 60));
        paymentAmountLabel.setText("<html><font color='red'>£0.00<br>£0.00<br>£0.00</font></html>");
        paymentAmountLabel.setHorizontalAlignment(JLabel.RIGHT);
        paymentAmountLabel.setVerticalAlignment(JLabel.TOP);
        paymentPanel.add(paymentAmountLabel);

        paymentMethodLabel.setPreferredSize(new Dimension(190, 14));
        paymentMethodLabel.setText("Choose Payment Method:");
        paymentPanel.add(paymentMethodLabel);

        String[] paymentStr = {"Cash Payment", "Card Payment", "Coupon Payment"};
        paymentMethodComboBox = new JComboBox(paymentStr);
        paymentMethodComboBox.setPreferredSize(new Dimension(190, 30));
        paymentMethodComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    paymentMethodComboBox_ValueChanged(e);
                }
            }
        });
        paymentPanel.add(paymentMethodComboBox);

        totalAmountLabel.setPreferredSize(new Dimension(190, 14));
        totalAmountLabel.setText("Total Amount Received:");
        paymentPanel.add(totalAmountLabel);

        totalAmountTextField.setPreferredSize(new Dimension(190, 30));
        totalAmountTextField.setHorizontalAlignment(JTextField.RIGHT);
        paymentPanel.add(totalAmountTextField);

        returnAmountLabel.setPreferredSize(new Dimension(190, 14));
        returnAmountLabel.setText("Return Amount:");
        paymentPanel.add(returnAmountLabel);

        returnAmountTextField.setPreferredSize(new Dimension(190, 30));
        returnAmountTextField.setHorizontalAlignment(JTextField.RIGHT);
        returnAmountTextField.setEnabled(false);
        paymentPanel.add(returnAmountTextField);

        codeOrPinLabel.setPreferredSize(new Dimension(190, 14));
        codeOrPinLabel.setText("Voucher Code or Card PIN:");
        codeOrPinPasswordField.setHorizontalAlignment(JTextField.CENTER);
        paymentPanel.add(codeOrPinLabel);

        codeOrPinPasswordField.setPreferredSize(new Dimension(190, 30));
        paymentPanel.add(codeOrPinPasswordField);

        payButton = new JButton ("Finish Payment");
        payButton.setPreferredSize(new Dimension(180, 30));
        payButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                payButton_actionPerformed(e);
            }
        });
        paymentPanel.add(payButton);

        setPaymentPanelEnabled(false);

        getContentPane().add(paymentPanel);
    }

    /**
     * this method will be fired when the application exit
     * @param evt
     */
    private void exitForm(WindowEvent evt) {
        JOptionPane.showMessageDialog(this, "Thank you for using POSTMan!!");
        this.dispose();
    }

    /**
     * this method will handle product list value changed event
     * @param e
     */
    private void productList_valueChanged(ListSelectionEvent e) {
        if (!productList.isSelectionEmpty()) {
            ((AddCommand)addCommand).setProductID(productList.getSelectedValue().toString());
            cartList.clearSelection();
            ((CommandButton)addItemButton).setEnabled(true);
            ((CommandButton)removeItemButton).setEnabled(false);
        }
    }

    /**
     * this method will handle cart list value changed event
     * @param e
     */
    private void cartList_valueChanged(ListSelectionEvent e) {
        if (!cartList.isSelectionEmpty()) {
            ((RemoveCommand)removeCommand).setItemID(((CartItem)cartList.getSelectedValue()).getProduct().getID());
            productList.clearSelection();
            ((CommandButton)addItemButton).setEnabled(false);
            ((CommandButton)removeItemButton).setEnabled(true);
        }
    }

    /**
     * this method will handle the add and remove button click event
     * @param e
     */
    private void button_actionPerformed(ActionEvent e) {
        //save previous state
        shoppingCartOriginator.set(shoppingCart);
        shoppingCartCaretaker.addMemento(shoppingCartOriginator.saveToMemento());
        currentCart++;

        ICommandHolder obj = (ICommandHolder)e.getSource();
        ICommand cmd = obj.getCommand();

        try {
            //execute the command
            cmd.execute();
            updateCartList();
        }
        catch(Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }

        setUndoRedoButtonEnabled();
        setCheckOutButtonEnabled();
    }

    /**
     * this method will handle the undo button click event
     * @param e
     */
    private void undoButton_actionPerformed(ActionEvent e) {
        //save current state
        if (currentCart == shoppingCartCaretaker.getSize()) {
            shoppingCartOriginator.set(shoppingCart);
            shoppingCartCaretaker.addMemento(shoppingCartOriginator.saveToMemento());
        }

        //set current cart to previous index
        populateCart(--currentCart);
        updateCartList();

        resetListSelection();
        setUndoRedoButtonEnabled();
        setCheckOutButtonEnabled();
    }

    /**
     * this method will handle redo button click event
     * @param e
     */
    private void redoButton_actionPerformed(ActionEvent e) {
        //set current cart to next index
        if (currentCart < shoppingCartCaretaker.getSize()-1) {
            populateCart(++currentCart);
            updateCartList();
        }

        resetListSelection();
        setUndoRedoButtonEnabled();
        setCheckOutButtonEnabled();
    }

    /**
     * this method will update total price, after discount and after VAT display in payment panel
     * @param totalAmount
     * @param afterDiscount
     * @param afterVAT
     */
    private void updatePaymentAmount(BigDecimal totalAmount, BigDecimal afterDiscount, BigDecimal afterVAT) {
        paymentAmountLabel.setText(String.format("<html><font color='red'>£%s<br>£%s<br>£%s</font></html>",
                totalAmount.setScale(2, BigDecimal.ROUND_DOWN).toString(),
                afterDiscount.setScale(2, BigDecimal.ROUND_DOWN).toString(),
                afterVAT.setScale(2, BigDecimal.ROUND_DOWN).toString()));
    }

    /**
     * this method will handle checkout button click event
     * @param e
     */
    private void checkOutButton_actionPerformed(ActionEvent e) {
        shoppingCart.giveDiscount();
        shoppingCart.addVAT();

        //calculate total amount, discount, and VAT
        shoppingCart.accept(new SalesOrderVisitor());
        updatePaymentAmount(shoppingCart.getTotalBill(ShoppingCart.BillState.TOTAL_AMOUNT),
                shoppingCart.getTotalBill(ShoppingCart.BillState.AFTER_DISCOUNT),
                shoppingCart.getTotalBill(ShoppingCart.BillState.AFTER_VAT));

        //reset payment panel and disable cart panel
        setCartPanelEnabled(false);
        setPaymentPanelEnabled(true);
        totalAmountTextField.setText("0");
        returnAmountTextField.setText("0");
    }

    /**
     * this method set all components inside cart panel enable or disable
     * @param isEnabled
     */
    private void setCartPanelEnabled(boolean isEnabled) {
        Component[] comps = cartPanel.getComponents();
        for (Component comp : comps){
            comp.setEnabled(isEnabled);
        }
    }

    /**
     * this method will reset product list and cart list
     */
    private void resetListSelection() {
        productList.clearSelection();
        cartList.clearSelection();
        ((CommandButton)addItemButton).setEnabled(false);
        ((CommandButton)removeItemButton).setEnabled(false);
    }

    /**
     * this method will set whether the undo button enable or disable
     */
    private void setUndoRedoButtonEnabled(){
        if (currentCart > 0)
            undoButton.setEnabled(true);
        else
            undoButton.setEnabled(false);

        if (currentCart < shoppingCartCaretaker.getSize()-1)
            redoButton.setEnabled(true);
        else
            redoButton.setEnabled(false);
    }

    /**
     * this method will set whether the checkout button enable or disable
     */
    private void setCheckOutButtonEnabled() {
        if (cartList.getModel().getSize() > 0)
            checkOutButton.setEnabled(true);
        else
            checkOutButton.setEnabled(false);
    }

    /**
     * payment method change event
     * this method will update the UI based on the payment method chosen
     * @param e
     */
    private void paymentMethodComboBox_ValueChanged(ItemEvent e) {
        totalAmountTextField.setEnabled(true);
        totalAmountTextField.setText("0");
        returnAmountTextField.setText("0");
        codeOrPinPasswordField.setEnabled(true);
        codeOrPinPasswordField.setText("");

        if (e.getItem().toString() == "Cash Payment")
            codeOrPinPasswordField.setEnabled(false);
        else if (e.getItem().toString() == "Card Payment") {
            totalAmountTextField.setEnabled(false);
            totalAmountTextField.setText(shoppingCart.getTotalBill(ShoppingCart.BillState.AFTER_VAT).toString());
            codeOrPinPasswordField.setEchoChar('*');
        }
        else if (e.getItem().toString() == "Coupon Payment")
            codeOrPinPasswordField.setEchoChar((char)0);
    }

    /**
     * this method will handle the payment button event
     * @param e
     */
    private void payButton_actionPerformed(ActionEvent e) {
        if (totalAmountTextField.getText() != "" &&
            returnAmountTextField.getText() != "" &&
            codeOrPinPasswordField.getText() != "") {

            //pay transaction
            PaymentMethod paymentMethod = PaymentMethod.values()[paymentMethodComboBox.getSelectedIndex()];
            PaymentFactory paymentFactory = PaymentFactory.getInstance();
            PaymentStrategy paymentStrategy =
                    paymentFactory.createPayment(
                            paymentMethod,
                            shoppingCart.getTotalBill(ShoppingCart.BillState.AFTER_VAT)
                    );
            //implement payment strategy
            shoppingCart.setPaymentStrategy(paymentStrategy);
            shoppingCart.makePayment();

            //save transaction to csv
            shoppingCart.accept(new StoreTransactionVisitor());

            if (paymentMethodComboBox.getSelectedIndex() == 1) { //card payment
                //payment authorization
                ((CardPayment)paymentStrategy).setPin(codeOrPinPasswordField.getPassword().toString());
                PaymentAuthorization authorization = new PaymentAuthorization(this,
                        codeOrPinPasswordField.getPassword().toString());
                authorization.show();
            }
            else {
                if (paymentMethodComboBox.getSelectedIndex() == 0){ //cash payment
                    //set the return amount (total paid amount - total price)
                    returnAmountTextField.setText(
                            ((CashPayment) paymentStrategy).getChangeAmount(
                                    new BigDecimal(totalAmountTextField.getText())
                            ).setScale(2, BigDecimal.ROUND_DOWN).toString()
                    );
                }
                else if (paymentMethodComboBox.getSelectedIndex() == 2) { //coupon payment
                    //calculate cash amount need to be paid after subtracted with coupon value
                    BigDecimal cashAmount = ((CouponPayment) paymentStrategy).getCashAmount(
                            CouponPayment.CouponType.valueOf(codeOrPinPasswordField.getText())
                    );
                    BigDecimal returnAmount = new BigDecimal(totalAmountTextField.getText()).subtract(cashAmount);
                    returnAmountTextField.setText(returnAmount.setScale(2, BigDecimal.ROUND_DOWN).toString());
                }

                JOptionPane.showMessageDialog(this, "Payment success!! Thank you for shopping with us...");
            }

            //reset POS terminal
            getContentPane().removeAll();
            initComponents();
            pack();
        }
    }

    /**
     * this method set all components inside payment panel enable or disable
     * @param isEnabled
     */
    private void setPaymentPanelEnabled(boolean isEnabled) {
        Component[] comps = paymentPanel.getComponents();
        for (Component comp : comps){
            comp.setEnabled(isEnabled);
        }
        returnAmountTextField.setEnabled(false);
        codeOrPinPasswordField.setEnabled(false);

        //reset payment panel
        if (!isEnabled) {
            paymentAmountLabel.setText("<html><font color='red'><html>£0.00<br>£0.00<br>£0.00<br></font></html>");
            paymentMethodComboBox.setSelectedIndex(0);
            totalAmountTextField.setText("");
            returnAmountTextField.setText("");
            codeOrPinPasswordField.setText("");
        }
    }

    /**
     * this method will populate shopping cart based on the index from undo or redo operation
     * @param currentIndex
     */
    private void populateCart(int currentIndex) {
        shoppingCart.clearCartItem();
        ShoppingCart sc = shoppingCartOriginator.restoreFromMemento(shoppingCartCaretaker.getMemento(currentIndex));
        Iterator iterator = sc.getCartItems().values().iterator();
        while (iterator.hasNext()) {
            CartItem cartItem = (CartItem)iterator.next();
            shoppingCart.addCartItem(cartItem);
        }
    }

    /**
     * this method will update the shopping cart list
     */
    private void updateCartList() {
        Iterator iterator = shoppingCart.getCartItems().values().iterator();
        Vector dataList = new Vector();
        while(iterator.hasNext()) {
            dataList.addElement((CartItem)iterator.next());
        }
        cartList.setListData(dataList);

        if (dataList.size() == 0)
            ((CommandButton)removeItemButton).setEnabled(false);
    }

    /**
     * main method
     * @param args
     */
    public static void main(String[] args) {
        POSTerminalGUI posTerminalGUI = new POSTerminalGUI();
        posTerminalGUI.show();
    }
}
