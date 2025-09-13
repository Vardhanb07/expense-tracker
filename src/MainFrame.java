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
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        addHeaderButtons();
        addExpensesPanel();
    }

    void addHeaderButtons() {
        JPanel headerButtonPenal = new JPanel(new FlowLayout());
        createButton = new JButton("Create");
        expensesButton = new JButton("Expenses");
        headerButtonPenal.add(createButton);
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
        JPanel centerExpensePanel = new JPanel();
        centerExpensePanel.setLayout(new BorderLayout());
        JPanel expenseInnerPanel = new JPanel();
        expenseInnerPanel.setLayout(new BoxLayout(expenseInnerPanel, BoxLayout.PAGE_AXIS));
        JLabel titleLabel = new JLabel(data.get(1));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setVerticalAlignment(JLabel.CENTER);
        expenseInnerPanel.add(titleLabel);
        JLabel amountLabel = new JLabel(data.get(2));
        amountLabel.setHorizontalAlignment(JLabel.CENTER);
        amountLabel.setVerticalAlignment(JLabel.CENTER);
        expenseInnerPanel.add(amountLabel);
        JLabel descriptionLabel = new JLabel(data.get(3));
        descriptionLabel.setHorizontalAlignment(JLabel.CENTER);
        descriptionLabel.setVerticalAlignment(JLabel.CENTER);
        expenseInnerPanel.add(descriptionLabel);
        centerExpensePanel.add(expenseInnerPanel, BorderLayout.CENTER);
        expensePanel.add(centerExpensePanel);
    }
}
