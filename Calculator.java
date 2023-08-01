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
        setTitle("Prashant Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the input field
        inputField = new JTextField();
        inputField.setEditable(false);
        inputField.setFont(new Font("Arial", Font.PLAIN, 36));
        inputField.setHorizontalAlignment(JTextField.RIGHT);
        add(inputField, BorderLayout.NORTH);

        // Create the button panel
        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create the number buttons
        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = createButton(String.valueOf(i), Color.BLACK, Color.WHITE);
            numberButtons[i].addActionListener(this);

            buttonPanel.add(numberButtons[i]);
        }

        // Create the operator buttons
        operatorButtons = new JButton[5];
        String[] operatorSymbols = {"+", "-", "*", "/"};
        for (int i = 0; i < operatorSymbols.length; i++) {
            operatorButtons[i] = createButton(operatorSymbols[i], Color.BLACK, Color.GRAY);
            operatorButtons[i].addActionListener(this);
            buttonPanel.add(operatorButtons[i]);
        }

        // Create the clear and equals buttons
        clearButton = createButton("C", Color.WHITE, Color.GRAY);
        clearButton.addActionListener(this);
        buttonPanel.add(clearButton);

        equalsButton = createButton("=", Color.WHITE, Color.BLUE);
        equalsButton.addActionListener(this);
        buttonPanel.add(equalsButton);

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JButton createButton(String label, Color foreground, Color background) {
        JButton button = new JButton(label);
        button.setForeground(foreground);
        button.setBackground(background);
        button.setFont(new Font("Arial", Font.BOLD, 32));
        return button;
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
