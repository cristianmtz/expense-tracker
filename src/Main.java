import cli.Manager;
import org.apache.commons.cli.*;
import repository.ExpenseRepository;
import repository.ExpenseRepositoryImpl;
import service.ExpenseService;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;


public class Main {

    static final String PATH = "Expenses.json";
    private static final int NO_ARGS = 0;


    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        ExpenseRepository expenseRepository = new ExpenseRepositoryImpl(Path.of(PATH));
        ExpenseService expenseService = new ExpenseService(expenseRepository, scanner);


        if (args.length == NO_ARGS) {
            System.out.println("No command line arguments provided");
        }

        Options options = Manager.createOptions();
        CommandLineParser parser = new DefaultParser();
        HelpFormatter helper = new HelpFormatter();


        try {
            CommandLine cmd = parser.parse(options, args);
            String description = null;
            Double amount = null;
            int id;

            for (Option option : cmd.getOptions()) {
                switch (option.getOpt()) {
                    case "h":
                        helper.printHelp("Usage", options);
                        System.exit(0);
                        break;

                    case "d":
                        description = cmd.getOptionValue("d");
                        System.out.println("Description: " + description);
                        break;

                    case "a":
                        amount = Double.parseDouble(cmd.getOptionValue("a"));
                        System.out.println("Amount: " + amount);
                        break;
                    case "l":
                        printMenu();
                        expenseService.expenseList();
                        break;
                    case "del":
                        id = Integer.parseInt(cmd.getOptionValue("del"));
                        expenseService.deleteExpense(id);
                        break;
                    case "s":
                        String month = cmd.getOptionValue("s");
                        expenseService.expensesSummary(month);
                        break;
                    case "v":
                        System.out.println("Verbose output enabled");
                        break;
                    case "u":
                        id = Integer.parseInt(cmd.getOptionValue("u"));
                        expenseService.updateExpense(id);
                        break;
                    default:
                        System.out.println("Unknown option: " + option.getOpt());
                        break;
                }
            }

            if (description != null || amount != null) {
                expenseService.addExpense(description, amount);
                System.out.println("Expense added: " + description + " - " + amount);
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            helper.printHelp("Usage:", options);
            System.exit(1);
        }

        expenseService.saveExpense();
    }

    private static void printMenu(){
        System.out.printf("%-4s %-12s %-15s %-10s%n", "ID", "Date", "Description", "Amount");
        System.out.println("-----------------------------------------------");
    }
}
