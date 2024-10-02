package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Expense;
import service.LocalDateAdapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

public class ExpenseRepositoryImpl implements ExpenseRepository{

    private final Path path;

    private final Gson gson;

    public ExpenseRepositoryImpl(Path path) {
        this.path = path;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class,  new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void saveExpenses(List<Expense> expenses) throws IOException {

        if(!Files.exists(path)){
            Files.createFile(path);
        }
        Files.writeString(path, gson.toJson(expenses));
    }

    public List<Expense> loadExpenses() throws IOException {
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        String json = Files.readString(path);
        Expense[] expensesArray = gson.fromJson(json, Expense[].class);

        return expensesArray != null ? new ArrayList<>(Arrays.asList(expensesArray)) : new ArrayList<>();
    }


}
