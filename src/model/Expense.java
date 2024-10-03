package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Expense implements Comparable<Expense>{

    int id;

    LocalDate date;

    String description;

    Double amount;


    public Expense(int id, LocalDate dateTime, String description, Double amount) {
        this.id = id;
        this.date = dateTime;
        this.description = description;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate dateTime) {
        this.date = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("# %-3d %-12s %-12s $%.2f", id, date.format(formatter), description, amount);
    }

    @Override
    public int compareTo(Expense o) {
        return Integer.compare(this.id, o.id);
    }
}
