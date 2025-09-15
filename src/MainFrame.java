import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import db.Query;

public class MainFrame extends JFrame {
    String url, name, password;
    JButton createButton, expensesButton;
    JPanel expensePanel;

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
        headerButtonPenal.add(createButton);
        expensesButton = new JButton("Expenses");
        expensesButton.setFont(new Font("Arial", Font.PLAIN, 24));
        expensesButton.setBackground(new Color(0xCA231919, true));
        expensesButton.setForeground(new Color(0xFFFFFF));
        expensesButton.setFocusPainted(false);
        expensesButton.setBorder(BorderFactory.createEmptyBorder(6, 20, 6, 20));
        expensesButton.setToolTipText("Click me to show all the expenses");
        expensesButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        innerExpensePanel.add(titleLabel);
        JLabel amountLabel = new JLabel("Amount: " + data.get(2));
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        amountLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        innerExpensePanel.add(amountLabel);
        JLabel descriptionLabel = new JLabel("Description: " + data.get(3));
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        innerExpensePanel.add(descriptionLabel);
        JButton removeButton = new JButton("Remove");
        removeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        removeButton.setToolTipText("Click me to remove the above expense");
        removeButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        removeButton.setBackground(new Color(0xCA231919, true));
        removeButton.setForeground(new Color(0xFFFFFF));
        removeButton.setFont(new Font("Arial", Font.PLAIN, 16));
        removeButton.addActionListener(e -> {
            Query q = new Query(this.url, this.name, this.password);
            q.removeData(Integer.parseInt(data.get(0)));
            innerExpensePanel.setVisible(false);
        });
        innerExpensePanel.add(removeButton);
        innerExpensePanel.setBorder(new RoundedBorder(10));
        innerExpensePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        expensePanel.add(innerExpensePanel);
        expensePanel.add(Box.createRigidArea(new Dimension(10, 10)));
    }
}
