package service;

import model.Expense;
import repository.ExpenseRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class ExpenseService {

    private final List<Expense> expenses;

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) throws IOException {
        this.expenseRepository = expenseRepository;
        this.expenses = expenseRepository.loadExpenses();
    }

    public void addExpense(String description, Double amount){

        Expense expense = new Expense(
                expenses.size() + 1,
                LocalDate.now(),
                description,
                amount
        );

        expenses.add(expense);
    }

    public void deleteExpense(int id){
        boolean isPresent = expenses.removeIf(expense -> expense.getId() == id);

        if(!isPresent){
            System.out.println("Expense does not exist");
            return;
        }

        System.out.println("Expense deleted successfully");
    }

    public void expenseList(){
        expenses.forEach(System.out::println);
    }

    public void expensesSummary(String ...month){

        double summary;

        if (month.length > 0 && month[0] != null && !month[0].isEmpty()) {

            String monthArg = month[0].toLowerCase();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");

            summary = expenses.stream()
                    .filter(expense -> {
                        LocalDate date = expense.getDate();
                        String formattedDate = date.format(formatter);
                        return formattedDate.equals(monthArg);
                    })
                    .mapToDouble(Expense::getAmount)
                    .sum();
            System.out.println( "Total expenses for " + Arrays.toString(month) + " month: $" + summary);
            return;

        }

        summary = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        System.out.println("Total expenses: $" + summary);

    }

    public void saveExpense() throws IOException {
        expenseRepository.saveExpenses(expenses);
    }
}
