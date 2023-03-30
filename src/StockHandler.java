import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class StockHandler {
    static CoffeeMachine coffeeMachine = new CoffeeMachine(5000, 5400, 1200, 90, 550);
    static JButton fillBtn, clearBtn;
    static JCheckBox waterCheck, milkCheck, beansCheck, cupsCheck;
    static JTextField waterField, milkField, beansField, cupsField;
    static boolean isWaterChecked, isMilkChecked, isBeansChecked, isCupsChecked;
    static JTable table;
    static DefaultTableModel tableModel;

    static class FillButtonHandler implements ActionListener {
        private CoffeeMachine mainStock;
        private JTextField[] inputField =  {waterField, milkField, beansField, cupsField};
        private JCheckBox[] checkBoxes = {waterCheck, milkCheck, beansCheck, cupsCheck};
        private String types[] = {"Water", "Milk", "Bean", "Cup"};
        public FillButtonHandler(){
            this.mainStock = coffeeMachine;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < inputField.length; i++){
                if (inputField[i].isEnabled() && !inputField[i].getText().equals("")){
                    int newValue = Integer.parseInt(inputField[i].getText());
                    int current = switch (types[i]) {
                        case "Water" -> mainStock.getWater();
                        case "Milk" -> mainStock.getMilk();
                        case "Bean" -> mainStock.getBeans();
                        case "Cup" -> mainStock.getCups();
                        default -> 0;
                    };

                    int totalValue = current + newValue;
                    System.out.printf("Updating %s: " + current + " + " + newValue + " = " + totalValue + "\n", types[i]);
                    switch (types[i]) {
                        case "Water" : {
                            coffeeMachine.setWater(totalValue);
                            tableModel.setValueAt(totalValue, 0, 1);
                            break;
                        }
                        case "Milk" : {
                            coffeeMachine.setMilk(totalValue);
                            tableModel.setValueAt(totalValue, 1, 1);
                            break;
                        }
                        case "Bean" : {
                            coffeeMachine.setBeans(totalValue);
                            tableModel.setValueAt(totalValue, 2, 1);
                            break;
                        }
                        case "Cup" : {
                            coffeeMachine.setCups(totalValue);
                            tableModel.setValueAt(totalValue, 2, 1);
                            break;
                        }
                    };
                }
                inputField[i].setText("");
                inputField[i].setEnabled(false);
                checkBoxes[i].setSelected(false);
            }
            tableModel.fireTableDataChanged();
        }
    }

    static class ClearButtonHandler implements ActionListener{
        private JCheckBox[] checkBoxes = {waterCheck, milkCheck, beansCheck, cupsCheck};
        private JTextField[] textFields = {waterField, milkField, beansField, cupsField};

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < checkBoxes.length; i++) {
                textFields[i].setText("");
                textFields[i].setEnabled(false);
                checkBoxes[i].setSelected(false);
            }
        }
    }

    static class CheckboxHandler implements ItemListener {
        private boolean[] isChecked = {isWaterChecked, isMilkChecked, isBeansChecked, isCupsChecked};
        private JCheckBox[] checkBoxes = {waterCheck, milkCheck, beansCheck, cupsCheck};
        private JTextField[] textFields = {waterField, milkField, beansField, cupsField};

        @Override
        public void itemStateChanged(ItemEvent e) {
            for (int i = 0; i < checkBoxes.length; i++) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (e.getSource() == checkBoxes[i]) {
                        isChecked[i] = true;
                        System.out.printf("status %s : " + isChecked[i] + "\n", checkBoxes[i].getText());
                        textFields[i].setEnabled(true);
                    }
                }
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    if (e.getSource() == checkBoxes[i]) {
                        isChecked[i] = false;
                        System.out.printf("status %s : " + isChecked[i] + "\n", checkBoxes[i].getText());
                        textFields[i].setEnabled(false);
                    }
                }
                textFields[i].repaint();
            }
        }
    }

}

