package com.tracker.controller;

import com.tracker.model.FinanceManager;
import com.tracker.model.Transaction;
import com.tracker.model.User;
import com.tracker.service.OpenAIService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class MainController {
    // UI Elements from FXML
    @FXML private TextField amountField;
    @FXML private ComboBox<String> categoryBox;
    @FXML private PieChart expenseChart;
    @FXML private TextArea aiAdviceArea;
    @FXML private ListView<String> transactionListView;

    private FinanceManager manager;
    private OpenAIService aiService;

    public void setLoggedInUser(User user)
    {
        this.manager=new FinanceManager(user);
        this.aiService=new OpenAIService(user.getPersona());
    }
    @FXML
    public void initialize() {
        // Setup categories for the dropdown
        categoryBox.getItems().addAll("Food", "Rent", "Entertainment", "Utilities", "Other");

    }

    @FXML
    public void handleAddExpense() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String category = categoryBox.getValue();

            if (category == null) {
                aiAdviceArea.setText("Please select a category first.");
                return;
            }
            
            Transaction t = new Transaction(amount, category, "");
            manager.addTransaction(t);
            
            updateUI(); //refresh
            amountField.clear();
        } catch (NumberFormatException e) {
            aiAdviceArea.setText("Error: Please enter a numeric value for the amount.");
        }
    }

    private void updateUI() {
        //update stuff
        expenseChart.getData().clear();
        LocalDate now= LocalDate.now();
        expenseChart.setTitle("Spending for "+now.getMonth().toString());
        Map<String, Double> totals = manager.getCategoryTotalsByMonth();
        for (Map.Entry<String, Double> entry : totals.entrySet()) {
            PieChart.Data data = new PieChart.Data(entry.getKey(), entry.getValue());
            expenseChart.getData().add(data);
            //interactive stuff
            Tooltip tooltip = new Tooltip(String.format("%s: $%.2f", entry.getKey(), entry.getValue()));
            Tooltip.install(data.getNode(), tooltip);
        }
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Transaction t : manager.getTransactions()) {
            // Formatting
            String displayString = String.format("[%s]\nAmt: $%.2f | at %s", t.getCategory(), t.getAmount(), t.getDate());
            items.add(displayString);
        }
        transactionListView.setItems(items);
    }

    @FXML
    public void handleAIRequest() {
        aiAdviceArea.setText("Asking AI for advice based on your spending...");
        
        try {
            Map<String, Double> totals = manager.getCategoryTotalsByMonth();
            if (totals.isEmpty()) {
                aiAdviceArea.setText("Add some expenses first so the AI has data to analyze!");
                return;
            }

            // Using the service to get advice
            String advice = aiService.getFinancialAdvice(totals);
            aiAdviceArea.setText(advice);
            
        } catch (Exception e) {
            aiAdviceArea.setText("Error connecting to AI service. Check your API key.");
        }
    }

    public void initializeUserSession(User user) {
        this.aiService = new OpenAIService(user.getPersona()); 
    }
}