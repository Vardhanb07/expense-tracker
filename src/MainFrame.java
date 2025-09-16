import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

import db.Query;

public class MainFrame extends JFrame {
    String url, name, password;
    JButton createButton, expensesButton;
    JPanel expensePanel, formPanel, innerFormPanel;
    JTextField titleInputField, amountInputField;
    JTextArea descriptionTextArea;

    MainFrame(String url, String name, String password) {
        this.url = url;
        this.name = name;
        this.password = password;
        init();
    }

    void init() {
        this.setTitle("Expense Tracker");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(30, 30));
        this.setVisible(true);
        addHeaderButtons();
        addExpensesPanel();
    }

    void addHeaderButtons() {
        JPanel headerButtonPenal = new JPanel();
        headerButtonPenal.setLayout(new FlowLayout());
        createButton = new JButton("Create");
        createButton.setFont(new Font("Arial", Font.PLAIN, 24));
        createButton.setBackground(new Color(0xCA231919, true));
        createButton.setForeground(new Color(0xFFFFFF));
        createButton.setFocusPainted(false);
        createButton.setBorder(BorderFactory.createEmptyBorder(6, 20, 6, 20));
        createButton.setToolTipText("Click me to create a new expense");
        createButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createButton.addActionListener(e -> {
            expensePanel.setVisible(false);
            addForm();
        });
        headerButtonPenal.add(createButton);
        expensesButton = new JButton("Expenses");
        expensesButton.setFont(new Font("Arial", Font.PLAIN, 24));
        expensesButton.setBackground(new Color(0xCA231919, true));
        expensesButton.setForeground(new Color(0xFFFFFF));
        expensesButton.setFocusPainted(false);
        expensesButton.setBorder(BorderFactory.createEmptyBorder(6, 20, 6, 20));
        expensesButton.setToolTipText("Click me to show all the expenses");
        expensesButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        expensesButton.addActionListener(e -> {
            formPanel.setVisible(false);
            addExpensesPanel();
        });
        headerButtonPenal.add(expensesButton);
        this.add(headerButtonPenal, BorderLayout.PAGE_START);
    }

    void addExpensesPanel() {
        expensePanel = new JPanel();
        expensePanel.setLayout(new BoxLayout(expensePanel, BoxLayout.Y_AXIS));
        Query q = new Query(this.url, this.name, this.password);
        ArrayList<ArrayList<String>> data = q.getData();
        for (ArrayList<String> i : data) {
            addExpensePanel(i);
        }
        this.add(expensePanel, BorderLayout.CENTER);
    }

    void addExpensePanel(ArrayList<String> data) {
        JPanel innerExpensePanel = new JPanel();
        innerExpensePanel.setLayout(new BoxLayout(innerExpensePanel, BoxLayout.Y_AXIS));
        JLabel titleLabel = new JLabel("Title: " + data.get(1));
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 10));
        innerExpensePanel.add(titleLabel);
        JLabel amountLabel = new JLabel("Amount: " + data.get(2));
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        amountLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10));
        innerExpensePanel.add(amountLabel);
        JLabel descriptionLabel = new JLabel("Description: " + data.get(3));
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10));
        innerExpensePanel.add(descriptionLabel);
        JButton removeButton = new JButton("Remove");
        removeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        removeButton.setToolTipText("Click me to remove the above expense");
        removeButton.setBorder(BorderFactory.createEmptyBorder(10, 6, 10, 10));
        removeButton.setBackground(new Color(0xCA231919, true));
        removeButton.setForeground(new Color(0xFFFFFF));
        removeButton.setFont(new Font("Arial", Font.PLAIN, 16));
        removeButton.addActionListener(e -> {
            Query q = new Query(this.url, this.name, this.password);
            q.removeData(Integer.parseInt(data.getFirst()));
            innerExpensePanel.setVisible(false);
        });
        innerExpensePanel.add(removeButton);
        innerExpensePanel.setBorder(new RoundedBorder(10));
        innerExpensePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        expensePanel.add(innerExpensePanel);
        expensePanel.add(Box.createRigidArea(new Dimension(10, 10)));
    }

    void addForm() {
        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        innerFormPanel = new JPanel();
        innerFormPanel.setLayout(new BoxLayout(innerFormPanel, BoxLayout.Y_AXIS));
        innerFormPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerFormPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        addFormInputFields("Title", false);
        addFormInputFields("Amount", false);
        addFormInputFields("Description", true);
        JButton addButton = new JButton("Add");
        innerFormPanel.add(addButton);
        addButton.setFont(new Font("Arial", Font.PLAIN, 24));
        addButton.setBackground(new Color(0xCA231919, true));
        addButton.setForeground(new Color(0xFFFFFF));
        addButton.setFocusPainted(false);
        addButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addButton.setToolTipText("Click me add the above as a expense");
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.addActionListener(e -> {
            Query q = new Query(this.url, this.name, this.password);
            q.insertData(titleInputField.getText(), amountInputField.getText(), descriptionTextArea.getText());
            formPanel.setVisible(false);
            addExpensesPanel();
        });
        formPanel.add(innerFormPanel);
        this.add(formPanel);
    }

    void addFormInputFields(String name, Boolean isDescription) {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        JPanel inputFieldPanel = new JPanel();
        inputFieldPanel.setLayout(new BoxLayout(inputFieldPanel, BoxLayout.X_AXIS));
        inputFieldPanel.setBorder(BorderFactory.createTitledBorder(null, name, TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.PLAIN, 24), Color.BLACK));
        JTextField inputField = new JTextField(60);
        inputField.setFont(new Font("Arial", Font.PLAIN, 20));
        inputField.setBorder(BorderFactory.createLineBorder(new Color(0x67231919, true)));
        inputField.setBorder(BorderFactory.createCompoundBorder(inputField.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
        JTextArea inputTextArea = new JTextArea(18, 70);
        inputTextArea.setBorder(BorderFactory.createLineBorder(new Color(0x67231919, true)));
        inputTextArea.setBorder(BorderFactory.createCompoundBorder(inputField.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
        inputTextArea.setFont(new Font("Arial", Font.PLAIN, 20));
        inputPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        if (!isDescription) {
            inputFieldPanel.add(inputField);
        } else {
            inputFieldPanel.add(inputTextArea);
        }
        if (name.equals("Title")) {
            titleInputField = inputField;
        } else if (name.equals("Amount")) {
            amountInputField = inputField;
        } else if (name.equals("Description")) {
            descriptionTextArea = inputTextArea;
        }
        inputPanel.add(inputFieldPanel);
        innerFormPanel.add(inputPanel);
    }
}
