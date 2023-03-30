import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class MainGUI extends JFrame{
    private JButton orderBtn, takeBtn, prevBtn;
    private JLabel label, heroLabelIcon, totalValue;;
    private Font headerFont = new Font("TimesRoman", Font.BOLD, 24);
    private Font btnFont = new Font("TimesRoman", Font.BOLD, 20);
    private JTable table;
    private DefaultTableModel tableModel;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JLabel totalPriceLabel;
    private double totalPrice = 0.0;
    private Map<DrinksTypes, Integer> drinkQuantities = new HashMap<>();
    private double totalCash = StockHandler.coffeeMachine.getCash();

    public MainGUI(){
        super("KRATAIBIN");
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);


        // create the different "cards"
        JPanel mainPanel = createMainPanel();
        JPanel orderPanel = createOrderPanel(drinkQuantities);
        JPanel fillPanel = createFillPanel();
        JPanel takePanel = createTakePanel();
        JPanel paymentPanel = createPaymentPanel(drinkQuantities);

        // add the different "cards" to the card panel
        cardPanel.add(mainPanel, "Main");
        cardPanel.add(orderPanel, "Order");
        cardPanel.add(paymentPanel, "Payment");
        cardPanel.add(fillPanel, "Fill");
        cardPanel.add(takePanel, "Take");

        // display the main panel by default
        cardLayout.show(cardPanel, "Main");

        container.add(cardPanel, BorderLayout.CENTER);

        setSize(480, 800);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private  JPanel controlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.decode("#4d1a19"));

        // create a control panel to add back button
        JButton prevSign = new JButton("<");
        prevSign.setBackground(Color.decode("#4d1a19"));
        prevSign.setForeground(Color.WHITE);
        prevSign.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(cardPanel, "Main");
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        controlPanel.add(prevSign);
        return controlPanel;
    }

    private JPanel createTakePanel() {
        JPanel takePanel = new JPanel(new BorderLayout());
        JPanel componentPanel = new JPanel();
        takePanel.add(controlPanel(), BorderLayout.NORTH);

        // Add label on the center
        JLabel totalLabel = new JLabel("Total income: ");
        totalValue = new JLabel(String.valueOf(totalCash));
        JPanel totalPanel = new JPanel(new GridLayout(2, 1));
        totalPanel.add(totalLabel);
        totalPanel.add(totalValue);
        componentPanel.add(totalPanel);

        // Add text field and button
        JTextField amountField = new JTextField(15);
        JButton confirmButton = new JButton("Confirm");
        JPanel inputPanel = new JPanel();
        inputPanel.add(amountField);
        inputPanel.add(confirmButton);
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        componentPanel.add(inputPanel);

        // Add password confirmation dialog
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String strAmount = amountField.getText();
                if(strAmount != null && !strAmount.isEmpty()){
                    int amount = Integer.parseInt(String.valueOf(amountField.getText()));
                    if (amount > 0 && amount <= totalCash){
                        totalCash -= amount;
                        updateTotalCash(totalValue);
                        cardLayout.show(cardPanel, "Main");
                    }
                    else if (amount < 0) {
                        JOptionPane.showMessageDialog(null, "Please enter a positive amount.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                    else if (amount > totalCash) {
                        JOptionPane.showMessageDialog(null, "Not enough cash in out system.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Please enter an amount.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        takePanel.add(componentPanel, BorderLayout.CENTER);
        return takePanel;
    }

    private JPanel createFillPanel() {
        JPanel fillPanel = new JPanel(new BorderLayout());
        JPanel componentPanel = new JPanel();
        componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.Y_AXIS));
        fillPanel.add(controlPanel(), BorderLayout.NORTH);

        // create a pair panel for the water components
        JPanel waterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        StockHandler.waterField = new JTextField("", 15);
        StockHandler.waterField.setEnabled(false);
        StockHandler.waterCheck = new JCheckBox("Fill Water");
        waterPanel.add(StockHandler.waterField);
        waterPanel.add(StockHandler.waterCheck);
        componentPanel.add(waterPanel);

        // create a pair panel for the milk components
        JPanel milkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        StockHandler.milkField = new JTextField("", 15);
        StockHandler.milkField.setEnabled(false);
        StockHandler.milkCheck = new JCheckBox("Fill Milk");
        milkPanel.add(StockHandler.milkField);
        milkPanel.add(StockHandler.milkCheck);
        componentPanel.add(milkPanel);

        // create a pair panel for the beans components
        JPanel beansPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        StockHandler.beansField = new JTextField("", 15);
        StockHandler.beansField.setEnabled(false);
        StockHandler.beansCheck = new JCheckBox("Fill Beans");
        beansPanel.add(StockHandler.beansField);
        beansPanel.add(StockHandler.beansCheck);
        componentPanel.add(beansPanel);

        JPanel cupsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        StockHandler.cupsField = new JTextField("", 15);
        StockHandler.cupsField.setEnabled(false);
        StockHandler.cupsCheck = new JCheckBox("Fill Cups");
        cupsPanel.add(StockHandler.cupsField);
        cupsPanel.add(StockHandler.cupsCheck);
        componentPanel.add(cupsPanel);

        componentPanel.setBorder(new EmptyBorder(new Insets(20, 0, 0, 0)));

        fillPanel.add(componentPanel, BorderLayout.CENTER);

        StockHandler.CheckboxHandler checkboxHandler = new StockHandler.CheckboxHandler();
        StockHandler.waterCheck.addItemListener( checkboxHandler );
        StockHandler.milkCheck.addItemListener( checkboxHandler );
        StockHandler.beansCheck.addItemListener( checkboxHandler );
        StockHandler.cupsCheck.addItemListener( checkboxHandler );

        // create table model with two columns: "Name" and "Value"
        StockHandler.tableModel = new DefaultTableModel(new String[]{"Type", "Value"}, 0);
        // add data to table model from the enum
        StockHandler.tableModel.addRow(new Object[]{"Water", StockHandler.coffeeMachine.getWater()});
        StockHandler.tableModel.addRow(new Object[]{"Milk", StockHandler.coffeeMachine.getMilk()});
        StockHandler.tableModel.addRow(new Object[]{"Beans", StockHandler.coffeeMachine.getBeans()});
        StockHandler.tableModel.addRow(new Object[]{"Cups", StockHandler.coffeeMachine.getCups()});

        // create table with the table model
        StockHandler.table = new JTable();
        StockHandler.table.setModel(StockHandler.tableModel);

        // add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(StockHandler.table);

        StockHandler.FillButtonHandler fillButtonHandler = new StockHandler.FillButtonHandler();
        StockHandler.ClearButtonHandler clearButtonHandler = new StockHandler.ClearButtonHandler();
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        StockHandler.fillBtn = new JButton("Confirm");
        StockHandler.fillBtn.addActionListener( fillButtonHandler );
        StockHandler.clearBtn = new JButton("Clear");
        StockHandler.clearBtn.addActionListener( clearButtonHandler );
        btnPanel.add(StockHandler.fillBtn);
        btnPanel.add(StockHandler.clearBtn);

        componentPanel.add(btnPanel, BorderLayout.WEST);
        fillPanel.add(componentPanel, BorderLayout.CENTER);

        // add the scroll pane to the panel
        fillPanel.add(scrollPane, BorderLayout.SOUTH);
        return fillPanel;
    }

    private void updateTotalCash (JLabel totalValue){
        double currentValue =0.0;
        for (Map.Entry<DrinksTypes, Integer> entry : drinkQuantities.entrySet()) {
            totalPrice += entry.getKey().getPricePerCup() * entry.getValue();
        }
        currentValue += totalPrice;
        totalCash += currentValue;
        totalValue.setText(String.valueOf(totalCash));
    }
    private JPanel createPaymentPanel(Map<DrinksTypes, Integer> drinkQuantities) {
        JPanel paymentPanel = new JPanel(new BorderLayout());
        JPanel componentPanel = new JPanel();
        paymentPanel.add(controlPanel(), BorderLayout.NORTH);
        ImageIcon img1 = new ImageIcon("resources/krataipay1.jpg");
        Image imgSlider1 = img1.getImage();
        Image imgSliderScaled = imgSlider1.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
        ImageIcon imgSliderIcon = new ImageIcon(imgSliderScaled);
        JLabel imgSliderLabel = new JLabel("");
        imgSliderLabel.setIcon(imgSliderIcon);
        componentPanel.add(imgSliderLabel);

        totalPriceLabel = new JLabel("Total Price: ฿" + totalPrice);
        componentPanel.add(totalPriceLabel);

        for (Map.Entry<DrinksTypes, Integer> entry : drinkQuantities.entrySet()) {
            totalPrice += entry.getKey().getPricePerCup() * entry.getValue();
        }
        // Create a label to display the total price
        JLabel totalPriceLabel = new JLabel();
        componentPanel.add(totalPriceLabel);

        // Loop through the updated drinkQuantities to display the current list of ordered drinks
        for (Map.Entry<DrinksTypes, Integer> entry : drinkQuantities.entrySet()) {
            JPanel drinkPanel = new JPanel(new BorderLayout());
            JLabel drinkLabel = new JLabel(entry.getValue() + " x " + entry.getKey().name() + " " + entry.getKey().getPricePerCup() + " * " + entry.getValue());
            drinkPanel.add(drinkLabel, BorderLayout.WEST);
            componentPanel.add(drinkPanel);
        }

        paymentPanel.add(componentPanel);

        JButton confirmBtn = new JButton("Confirm");
        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Map.Entry<DrinksTypes, Integer> entry : drinkQuantities.entrySet()) {
                    DrinksTypes type = entry.getKey();
                    int quantity = entry.getValue();
                    if(StockHandler.coffeeMachine.canMakeCoffee(type.getWaterMilliLitrePerCup() * quantity, type.getMilkMilliLitrePerCup() * quantity, type.getBeansGramPerCup() * quantity)){
                        String coffeeMsg = StockHandler.coffeeMachine.makeCoffee(type, quantity);
                        updateTotalCash(totalValue);
                        System.out.println(coffeeMsg);
                        System.out.println("ORDERED: " + type.name()+ "X" + quantity);
                        tableModel.addRow(new Object[]{type.name(), quantity, type.getPricePerCup() * quantity});
                        cardLayout.show(cardPanel, "Main");
                    }else{
                        String coffeeMsg = StockHandler.coffeeMachine.makeCoffee(type, quantity);
                        System.out.println(coffeeMsg);
                    }
                }
            }
        });
        paymentPanel.add(confirmBtn, BorderLayout.SOUTH);
        return paymentPanel;
    }
    private void updateTotalPrice(Map<DrinksTypes, Integer> drinkQuantities, double totalPrice) {
        this.totalPrice = totalPrice;
        for (Map.Entry<DrinksTypes, Integer> entry : drinkQuantities.entrySet()) {
            totalPrice += entry.getKey().getPricePerCup() * entry.getValue();
        }
        totalPriceLabel.setText(String.format("Total Price: ฿%.2f", totalPrice));
        System.out.println(totalPrice);
    }


    private void createDrinkPanel(DrinksTypes type, JPanel parentPanel, Map<DrinksTypes, Integer> drinkQuantities) {
        // Loop through all the panels in the parentPanel
        boolean panelExists = false;
        Component[] components = parentPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel drinkPanel = (JPanel) component;
                if (drinkPanel.getComponentCount() >= 4) {
                    JLabel drinkLabel = (JLabel) drinkPanel.getComponent(0);
                    // Get the drink type
                    DrinksTypes drinkType = DrinksTypes.valueOf(drinkLabel.getText());
                    // Check if panel already exists for the drink type
                    if (drinkType == type) {
                        panelExists = true;
                        // Get the quantity value
                        JLabel quantityValue = (JLabel) drinkPanel.getComponent(2);
                        int quantity = Integer.parseInt(quantityValue.getText());
                        // Update the quantity
                        quantity++;
                        quantityValue.setText(Integer.toString(quantity));
                        // Update the drink quantities map
                        drinkQuantities.put(type, quantity);
                        updateTotalPrice(drinkQuantities, totalPrice);
                        break;
                    }
                }
            }
        }

        // Create a new panel if one does not already exist for the drink type
        if (!panelExists) {
            JPanel drinkPanel = new JPanel();
            drinkPanel.setPreferredSize(new Dimension(300, 50));

            // Create the image and label components for the drink
            JLabel drinkLabel = new JLabel(type.name());
            //ImageIcon drinkIcon = new ImageIcon("../resources/com/coffeemachine/coffeemachineguifx/" + type.toString().toLowerCase() + "-icon.png");
            //JLabel drinkImage = new JLabel(drinkIcon);

            // Create the quantity components for the drink
            JButton increaseButton = new JButton("+");
            JButton decreaseButton = new JButton("-");
            JLabel quantityValue = new JLabel("1");

            //drinkPanel.add(drinkImage, gbc);
            drinkPanel.add(drinkLabel);
            drinkPanel.add(decreaseButton);
            drinkPanel.add(quantityValue);
            drinkPanel.add(increaseButton);

            // Add action listeners to the buttons
            increaseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int quantity = Integer.parseInt(quantityValue.getText());
                    quantity++;
                    quantityValue.setText(Integer.toString(quantity));
                    drinkQuantities.put(type, quantity); // Update the drink quantities map
                    updateTotalPrice(drinkQuantities, totalPrice);
                }
            });

            decreaseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int quantity = Integer.parseInt(quantityValue.getText());
                    if (quantity > 0) {
                        quantity--;
                        quantityValue.setText(Integer.toString(quantity));
                        drinkQuantities.put(type, quantity); // Update the drink quantities map
                        updateTotalPrice(drinkQuantities, totalPrice);
                    }
                }
            });
            // Add the drink panel to the order panel
            parentPanel.add(drinkPanel);
            parentPanel.revalidate();
            parentPanel.repaint();

            // Update the drink quantities map and total price
            drinkQuantities.put(type, 1); // Add the new drink type with quantity 1
            updateTotalPrice(drinkQuantities, totalPrice);
        }

    }

    private JPanel createOrderPanel(Map<DrinksTypes, Integer> drinkQuantities) {
        JPanel orderPanel = new JPanel();
        Icon icon1 = new ImageIcon("../resources/com/coffeemachine/coffeemachineguifx/order-icon.png");
        orderPanel.add(controlPanel());
        JPanel componentPanel = new JPanel(new FlowLayout());


        JButton btnLatte = new JButton("Latte",icon1);
        btnLatte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDrinkPanel(DrinksTypes.LATTE, orderPanel, drinkQuantities);
            }
        });
        btnLatte.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnLatte.setHorizontalTextPosition(SwingConstants.CENTER);
        btnLatte.setPreferredSize(new Dimension(120,120));
        componentPanel.add(btnLatte);

        JButton btnCapu = new JButton("Capu",icon1);
        btnCapu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDrinkPanel(DrinksTypes.CAPPUCCINO, orderPanel, drinkQuantities);
            }
        });
        btnCapu.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnCapu.setHorizontalTextPosition(SwingConstants.CENTER);
        btnCapu.setPreferredSize(new Dimension(120,120));
        componentPanel.add(btnCapu);

        JButton btnEs = new JButton("Espresso",icon1);
        btnEs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDrinkPanel(DrinksTypes.ESPRESSO, orderPanel, drinkQuantities);
            }
        });
        btnEs.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnEs.setHorizontalTextPosition(SwingConstants.CENTER);
        btnEs.setPreferredSize(new Dimension(120,120));
        componentPanel.add(btnEs);

        JButton confirmBtn = new JButton("Confirm");
        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Payment");
            }
        });
        orderPanel.add(confirmBtn);

        componentPanel.setBorder(new EmptyBorder(50,250,1,250));
        orderPanel.add(componentPanel);
        return orderPanel;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.decode("#FFFDEB"));
        ImageIcon heroIcon = new ImageIcon("resources/krataibin-icon.PNG");
        Image heroImg = heroIcon.getImage();
        Image heroScaledImg = heroImg.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
        ImageIcon heroScaledIcon = new ImageIcon(heroScaledImg);

        ImageIcon icon = new ImageIcon("resources/order-icon.png");
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(128, 128, java.awt.Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);

        heroLabelIcon = new JLabel("");
        heroLabelIcon.setIcon(heroScaledIcon);

        JPanel heroIconPanel = new JPanel();
        heroIconPanel.setBackground(Color.decode("#FFFDEB"));
        heroIconPanel.add(heroLabelIcon);

        label = new JLabel("KRATAIBIN");
        label.setFont(headerFont);

        JPanel headerLabelPanel = new JPanel();
        headerLabelPanel.setBackground(Color.decode("#FFFDEB"));
        headerLabelPanel.add(label);

        orderBtn = new JButton("Drinks and coffee");
        orderBtn.setFocusable(false); // Remove the focusability of
        orderBtn.setIcon(scaledIcon);
        orderBtn.setFont(btnFont);
        orderBtn.setForeground(Color.WHITE);
        orderBtn.setVerticalTextPosition(SwingConstants.CENTER);
        orderBtn.setHorizontalTextPosition(SwingConstants.LEFT);
        orderBtn.setPreferredSize(new Dimension(400,150));
        orderBtn.setBackground(Color.decode("#4d1a19"));
        orderBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Order");
            }
        });

        StockHandler.fillBtn = new JButton("Fill and check stock");
        StockHandler.fillBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        StockHandler.fillBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        StockHandler.fillBtn.setPreferredSize(new Dimension(200,150));
        StockHandler.fillBtn.setBackground(Color.decode("#4d1a19"));
        StockHandler.fillBtn.setForeground(Color.WHITE);
        StockHandler.fillBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Fill");
            }
        });

        takeBtn = new JButton("Take out cash");
        takeBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        takeBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        takeBtn.setForeground(Color.WHITE);
        takeBtn.setBackground(Color.decode("#4d1a19"));
        takeBtn.setPreferredSize(new Dimension(200,150));
        takeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Take");
            }
        });

        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
        verticalPanel.setBackground(Color.decode("#FFFDEB"));

        JPanel orderBtnPanel = new JPanel();
        orderBtnPanel.setBackground(Color.decode("#FFFDEB"));
        orderBtnPanel.add(orderBtn);

        JPanel horizontalBtnPanel = new JPanel();
        horizontalBtnPanel.setLayout(new FlowLayout());
        horizontalBtnPanel.setBackground(Color.decode("#FFFDEB"));
        horizontalBtnPanel.add(StockHandler.fillBtn);
        horizontalBtnPanel.add(takeBtn);

        // create table model with three columns: "Ordered name" and "Quantity" and "Price"
        tableModel = new DefaultTableModel(new String[]{"Ordered name", "Quantity", "Price"}, 0);

        // create table with the table model
        table = new JTable();
        table.setModel(tableModel);

        // add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        verticalPanel.add(heroIconPanel);
        verticalPanel.add(headerLabelPanel);
        verticalPanel.add(orderBtnPanel);
        verticalPanel.add(horizontalBtnPanel);
        verticalPanel.add(scrollPane);

        // Create a panel with FlowLayout to center the components horizontally
        JPanel horizontalPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        horizontalPanel.setBackground(Color.decode("#FFFDEB"));
        horizontalPanel.add(verticalPanel);

        mainPanel.add(horizontalPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    public static void main(String[] args) {
        new MainGUI();
    }
}
