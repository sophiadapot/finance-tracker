package com.tracker.model;
import java.util.*; 
import com.google.gson.Gson; 
import com.google.gson.GsonBuilder; 
import java.io.*; 
import java.lang.reflect.Type;        
import com.google.gson.reflect.TypeToken;

public class FinanceManager {
    private ArrayList<Transaction> transactions;
    private String username; 
    private static String FILE_PATH = "";
    private Gson gson; 
    public FinanceManager(String user){
        username = user; 
        FILE_PATH = "data_" + username + ".json";
        gson = new GsonBuilder().setPrettyPrinting().create(); 
        transactions = new ArrayList<>();
        loadData(); 
    }

    public void addTransaction(Transaction t){
        transactions.add(t); 
        saveData(); 
    }
    public List<Transaction> getTransactions(){
        return transactions; 
    }
    public void saveData(){
        try (FileWriter writer = new FileWriter(FILE_PATH)){
            gson.toJson(transactions, writer);
        } catch (IOException e){
            System.out.println(e.toString());
        }
    }
    public void loadData(){
        try (FileReader reader = new FileReader(FILE_PATH)){
            Type listType = new TypeToken<ArrayList<Transaction>>(){}.getType(); 
            transactions = gson.fromJson(reader, listType);
            if (transactions == null) {
                transactions = new ArrayList<>();
                }
        } catch(IOException e){
            System.out.println(e.toString());
        }
    }
    public Map<String, Double> getCategoryTotals(){
        Map<String, Double> totals = new HashMap<>(); 

        for (Transaction t : transactions){
            String category = t.getCatagory();
            double amount = t.getAmount(); 

            totals.put(category, totals.getOrDefault(category, 0.0) + amount);
        }
        return totals; 
    }
    
}
