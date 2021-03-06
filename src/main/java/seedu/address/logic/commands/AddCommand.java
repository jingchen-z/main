package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DATE_CONSTRAINTS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_TIME_CONSTRAINTS;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.checks.CheckValidDate;
import seedu.address.logic.commands.checks.CheckValidTime;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Add a task to Tasketch
 */
public class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_ALIAS = "a";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to Tasketch. "
            + "Parameters: "
            + PREFIX_NAME + "TASK NAME "
            + PREFIX_STARTDATE + "START_DATE "
            + PREFIX_STARTTIME + "START_TIME(24-hr format) "
            + PREFIX_ENDDATE + "END_DATE "
            + PREFIX_ENDTIME + "END_TIME(24-hr format) "
            + PREFIX_DESCRIPTION + "CONTENT "
            + PREFIX_CATEGORY + "CATEGORY "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Revise CS2113T "
            + PREFIX_STARTDATE + "15-03-19 "
            + PREFIX_STARTTIME + "14.00 "
            + PREFIX_ENDDATE + "15-03-19 "
            + PREFIX_ENDTIME + "17.00 "
            + PREFIX_DESCRIPTION + "Class diagram "
            + PREFIX_CATEGORY + "a "
            + PREFIX_TAG + "urgent "
            + PREFIX_TAG + "duesoon\n";

    public static final String COMMAND_PARAMETERS = "Parameters: "
            + PREFIX_NAME + "TASK NAME "
            + PREFIX_STARTDATE + "START_DATE "
            + PREFIX_STARTTIME + "START_TIME(24-hr format) "
            + PREFIX_ENDDATE + "END_DATE "
            + PREFIX_ENDTIME + "END_TIME(24-hr format) "
            + PREFIX_DESCRIPTION + "CONTENT "
            + PREFIX_CATEGORY + "CATEGORY "
            + "[" + PREFIX_TAG + "TAG]...\n";

    public static final String MESSAGE_SUCCESS = "New Task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This Task already exists in Tasketch";
    public static final String MESSAGE_DAILYTIME_CONSTRAINTS = "Adding daily task, Start Time must be before End Time!";
    public static final String MESSAGE_LOGIN = "Please login first";

    private Task toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Task}
     */
    public AddCommand(Task task) {
        requireNonNull(task);

        boolean isValidDate;
        boolean isValidTime;
        CheckValidDate checkValidDate = new CheckValidDate(task);
        CheckValidTime checkValidTime = new CheckValidTime(task);

        isValidDate = checkValidDate.getCheck();
        isValidTime = checkValidTime.getCheck();

        String startD = task.getStartDate().value;
        String endD = task.getEndDate().value;

        if (startD.equalsIgnoreCase(endD)) {
            checkArgument(isValidTime, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_DAILYTIME_CONSTRAINTS));
        } else {
            checkArgument(isValidDate, String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_DATE_CONSTRAINTS));
            checkArgument(isValidTime, String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_TIME_CONSTRAINTS));
        }
        toAdd = task;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.getLoginStatus()) {
            throw new CommandException(MESSAGE_LOGIN);
        }

        if (model.hasTask(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.addTask(toAdd);
        model.commitTaskBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
