package repository;

import model.Expense;

import java.io.IOException;
import java.util.List;


public interface ExpenseRepository {

    void saveExpenses(List<Expense> expenses) throws IOException;


    List<Expense> loadExpenses() throws  IOException;
}
