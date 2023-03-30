import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentGUI extends JFrame {
    private JButton confirmBtn, cancelBtn;
    private JLabel nameLabel, priceLabel, iconLabel, quantityLabel;
    private ImageIcon orderedImg;
    private Font primaryFont = new Font("Courier New", Font.PLAIN, 16);
    private Font priceFont = new Font("Courier New", Font.BOLD, 20);
    private int quantity = 1;
    public  PaymentGUI(CoffeeMachine coffeeMachine, DrinksTypes type, DefaultTableModel tableModel) {

        super("Payment");
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));

        JPanel namePanel = new JPanel();
        orderedImg = new ImageIcon("C:\\Users\\kitti\\IdeaProjects\\CoffeeMachineGUIFX\\src\\main\\resources\\com\\coffeemachine\\coffeemachineguifx\\order-icon.png ");

        nameLabel = new JLabel(type.name());
        nameLabel.setFont(primaryFont);
        nameLabel.setForeground(Color.DARK_GRAY);
        namePanel.add(nameLabel);

        JPanel imgPanel = new JPanel();
        iconLabel = new JLabel();
        iconLabel.setIcon(orderedImg);
        imgPanel.add(iconLabel);

        JPanel quantityPanel = new JPanel();
        JButton decreaseQuantity = new JButton("-");
        decreaseQuantity.setEnabled(false);
        JButton increaseQuantity = new JButton("+");
        quantityLabel = new JLabel(String.valueOf(quantity));

        QuantityController quantityController = new QuantityController(type, increaseQuantity, decreaseQuantity);
        decreaseQuantity.addActionListener( quantityController );
        increaseQuantity.addActionListener( quantityController );

        quantityPanel.add(decreaseQuantity);
        quantityPanel.add(quantityLabel);
        quantityPanel.add(increaseQuantity);

        JPanel pricePanel = new JPanel();
        priceLabel = new JLabel(String.valueOf(type.getPricePerCup() * quantity));
        priceLabel.setFont(priceFont);
        priceLabel.setForeground(Color.orange);
        pricePanel.add(priceLabel);

        JPanel btnPanel = new JPanel();
        confirmBtn = new JButton("Confirm");
        cancelBtn = new JButton("Cancel");

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(coffeeMachine.canMakeCoffee(type.getWaterMilliLitrePerCup() * quantity, type.getMilkMilliLitrePerCup() * quantity, type.getBeansGramPerCup() * quantity)){
                    String coffeeMsg = coffeeMachine.makeCoffee(type, quantity);
                    System.out.println(coffeeMsg);
                    System.out.println("ORDERED: " + type.name());
                    tableModel.addRow(new Object[]{type.name(), quantity, type.getPricePerCup()*quantity});
                    dispose();
                }else{
                    String coffeeMsg = coffeeMachine.makeCoffee(type, quantity);
                    System.out.println(coffeeMsg);
                }
            }
        });

        btnPanel.add(confirmBtn);
        btnPanel.add(cancelBtn);

        verticalPanel.add(namePanel);
        verticalPanel.add(imgPanel);
        verticalPanel.add(quantityPanel);
        verticalPanel.add(pricePanel);
        verticalPanel.add(btnPanel);

        // Create a panel with FlowLayout to center the components horizontally
        JPanel horizontalPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        horizontalPanel.add(verticalPanel);


        container.add(horizontalPanel, BorderLayout.CENTER);

        setSize(480, 800);
        setVisible(true);
    }

    class QuantityController implements ActionListener{
        private DrinksTypes type;
        private JButton increaseQuantity, decreaseQuantity;

        QuantityController(DrinksTypes type, JButton increaseQuantity, JButton decreaseQuantity) {
            this.type = type;
            this.increaseQuantity = increaseQuantity;
            this.decreaseQuantity = decreaseQuantity;
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            int newQuantity = Integer.parseInt(quantityLabel.getText()) - 1;
            if (newQuantity == 1) {
                decreaseQuantity.setEnabled(false);
            }else{
                decreaseQuantity.setEnabled(true);
            }

            if (e.getSource() == increaseQuantity){
                quantity += 1;
                quantityLabel.setText(String.valueOf(quantity));
                priceLabel.setText(String.valueOf(type.getPricePerCup() * quantity));
                if (quantity > 1) {
                    decreaseQuantity.setEnabled(true);
                }
            }
            if (e.getSource() == decreaseQuantity){
                quantity -= 1;
                quantityLabel.setText(String.valueOf(quantity));
                priceLabel.setText(String.valueOf(type.getPricePerCup() * quantity));
                if (quantity == 1) {
                    decreaseQuantity.setEnabled(false);
                }
            }
        }
    }
}
