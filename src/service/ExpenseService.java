package service;

import model.Expense;
import repository.ExpenseRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ExpenseService {

    private final List<Expense> expenses;

    private final ExpenseRepository expenseRepository;

    private final Scanner scanner;

    public ExpenseService(ExpenseRepository expenseRepository, Scanner scanner) throws IOException {
        this.expenseRepository = expenseRepository;
        this.expenses = expenseRepository.loadExpenses();
        this.scanner = scanner;
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


    public void updateExpense(int id){

        System.out.println("Please enter a description:");
        String description = scanner.nextLine();
        System.out.println("Please enter an amount:");
        double amount = Double.parseDouble(scanner.nextLine());

        Expense expenseToUpdate = expenses.stream()
                .filter(expense -> expense.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        // Actualizar los campos del gasto existente
        expenseToUpdate.setDescription(description); // Asegúrate de tener este método en tu clase Expense
        expenseToUpdate.setAmount(amount);

        System.out.println("Expense updated correctly");

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
