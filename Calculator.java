package Lab3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
    private JTextField inputField;
    private JButton[] numberButtons;
    private JButton[] operatorButtons;
    private JButton equalsButton;
    private JButton clearButton;
    private String currentExpression;

    public Calculator() {
        currentExpression = "";

        // Create and configure the JFrame
        setTitle("Lab3.Calculator");
        setSize(400,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Create the input field
        inputField = new JTextField();
        inputField.setBounds(0, 0, 400, 55);
        inputField.setEditable(false);
        add(inputField);

        // Create the number buttons
        JPanel panel = new JPanel(new GridLayout(4, 4));
        panel.setBounds(0,55,400,300);
        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);

        }
        operatorButtons = new JButton[5];
        String[] operatorSymbols = {"+", "-", "*", "/"};
        for (int i = 0; i < operatorSymbols.length; i++) {
            operatorButtons[i] = new JButton(operatorSymbols[i]);
            operatorButtons[i].addActionListener(this);
        }

        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        // Create the clear button
        clearButton = new JButton("C");
        clearButton.addActionListener(this);
        panel.add(clearButton);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);

        panel.add(operatorButtons[0]);

        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);

        panel.add(operatorButtons[1]);
        panel.add(operatorButtons[2]);

        panel.add(numberButtons[0]);
        panel.add(operatorButtons[3]);

        // Create the equals button
        equalsButton = new JButton("=");
        equalsButton.addActionListener(this);
        panel.add(equalsButton);
        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == equalsButton) {
            calculateExpression();
        } else if (source == clearButton) {
            clearExpression();
        } else {
            // Append the selected number or operator to the current expression
            for (int i = 0; i < 10; i++) {
                if (source == numberButtons[i]) {
                    currentExpression += String.valueOf(i);
                    break;
                }
            }
            for (int i = 0; i < 5; i++) {
                if (source == operatorButtons[i]) {
                    currentExpression += operatorButtons[i].getText();
                    break;
                }
            }
            updateInputField();
        }
    }

    private void calculateExpression() {
        try {
            // Split the expression into operands and operator
            String[] parts = currentExpression.split("[+\\-*/]");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid expression");
            }

            int operand1 = Integer.parseInt(parts[0]);
            int operand2 = Integer.parseInt(parts[1]);
            int result = 0;

            // Perform the appropriate operation based on the operator
            if (currentExpression.contains("+")) {
                result = operand1 + operand2;
            } else if (currentExpression.contains("-")) {
                result = operand1 - operand2;
            } else if (currentExpression.contains("*")) {
                result = operand1 * operand2;
            } else if (currentExpression.contains("/")) {
                if (operand2 == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                result = operand1 / operand2;
            }

            // Display the result
            inputField.setText(String.valueOf(result));
            currentExpression = String.valueOf(result);
        } catch (NumberFormatException e) {
            inputField.setText("Error");
            currentExpression = "";
        } catch (IllegalArgumentException e) {
            inputField.setText("Error");
            currentExpression = "";
        } catch (ArithmeticException e) {
            inputField.setText("Error: " + e.getMessage());
            currentExpression = "";
        }
    }


    private void clearExpression() {
        currentExpression = "";
        updateInputField();
    }

    private void updateInputField() {
        inputField.setText(currentExpression);
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
