package com.tracker.model;
import java.time.LocalDate;

public class Transaction
{
private LocalDate date;
private double amount;
private String category; 
private String description;

public Transaction(double amt, String category, String des)
{
    date = LocalDate.now();
    amount = amt;
    this.category = category;
    description = des;
}

public LocalDate getDate()
{
    return date;
}

public double getAmount()
{
    return amount;
}

public String getCategory()
{
    return category;
}

public String getDescription()
{
    return description;
}

public void setAmount(double amount)
{
    this.amount = amount;
}

public void setCategory(String category)
{
    this.category = category;
}

public void setDescription(String description)
{
    this.description = description;
}

}

