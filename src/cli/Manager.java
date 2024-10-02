package cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Manager {

    public static Options createOptions() {
        Options options = new Options();

        options.addOption(createOption("h", "help", false, "show help"));
        options.addOption(createOption("d", "description", true, "description of the item"));
        options.addOption(createOption("a", "amount", true, "amount of the item"));
        options.addOption(createOption("l", "list", false, "List of expenses"));
        options.addOption(createOption("del", "delete", true, "delete expense by id"));
        options.addOption(createOption("s", "summary", true, "expenses summary"));
        options.addOption(createOption("v", "verbose", false, "verbose output"));

        return options;
    }

    private static Option createOption(String shortOpt, String longOpt, boolean hasArg, String description) {
        return Option.builder(shortOpt)
                .longOpt(longOpt)
                .hasArg(hasArg)
                .desc(description)
                .build();
    }
}
