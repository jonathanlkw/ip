import java.util.Scanner;

public class Duke {

    public static final int END_OF_DONE_INDEX = 4;
    public static final int END_OF_EVENT_INDEX = 5;
    public static final int END_OF_DEADLINE_INDEX = 8;
    public static final int END_OF_TODO_INDEX = 4;
    public static final int AT_LENGTH = 3;
    public static final int BY_LENGTH = 3;
    public static final String LOGO = " _____         _____\n"
            + "|     \\ _____ |     \\ _____\n"
            + "|  o  /|     ||  o  /|     |\n"
            + "|  o  \\|  o  ||  o  \\|  o  |\n"
            + "|_____/|_____||_____/|_____|\n";
    public static final String HORIZONTAL_LINE = "____________________________________________________________";
    public static boolean isRunning = true;
    public static Task[] tasks = new Task[100];
    public static int numberOfTasks = 0;

    public static void main(String[] args) {

        String line;
        Scanner in = new Scanner(System.in);

        sayHello();
        while (isRunning) {
            line = in.nextLine();
            try {
                if (line.trim().equals("bye")) {
                    exitProgram();
                } else if(line.trim().equals("list")) {
                    printList();
                } else if (line.startsWith("done")) {
                    handleDone(line);
                } else if (line.startsWith("todo")) {
                    addTodoTask(line);
                } else if (line.startsWith("deadline")) {
                    addDeadlineTask(line);
                } else if (line.startsWith("event")) {
                    addEventTask(line);
                } else {
                    throw new InvalidCommandException();
                }
            } catch (InvalidCommandException e) {
                sayInvalid();
            }
        }
        sayBye();
    }

    private static void sayHello() {
        System.out.println(LOGO);
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Hello! I'm Bobo!");
        System.out.println("I'm a little blur, but I'd love to help!");
        System.out.println(HORIZONTAL_LINE);
    }

    private static void sayBye() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Ok. Bye bye!");
        System.out.println(HORIZONTAL_LINE);
    }

    private static void sayInvalid() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Sorry I don't understand that! Can you rephrase?");
        System.out.println(HORIZONTAL_LINE);
    }

    private static void printList() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < numberOfTasks; i++) {
            System.out.println((i + 1) + "." + tasks[i]);
        }
        System.out.println(HORIZONTAL_LINE);
    }

    private static void printDone(int taskNumber) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Okie! Marked this as done:");
        System.out.println((taskNumber + 1) + "." + tasks[taskNumber]);
        System.out.println(HORIZONTAL_LINE);
    }

    private static void handleDone(String line) {
        try {
            int taskNumber = Integer.parseInt(line.substring(END_OF_DONE_INDEX).trim()) - 1;
            if (tasks[taskNumber].isDone) {
                System.out.println("This task is already done!");
            } else {
                tasks[taskNumber].markAsDone();
                printDone(taskNumber);
            }
        } catch (NumberFormatException e) {
            System.out.println(HORIZONTAL_LINE);
            System.out.println("Oh no! An integer must come after the done command!");
            System.out.println("Please try again!");
            System.out.println(HORIZONTAL_LINE);
        } catch (NullPointerException e) {
            System.out.println(HORIZONTAL_LINE);
            System.out.println("Oh no! There isn't a task with that index");
            System.out.println("Please try again!");
            System.out.println(HORIZONTAL_LINE);
        }
    }

    private static void printTaskConfirmation() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Umm ok added:");
        System.out.println("  " + tasks[numberOfTasks - 1]);
        System.out.println("Now you have " + numberOfTasks + " tasks in the list.");
        System.out.println(HORIZONTAL_LINE);
    }

    private static void addEventTask(String line) {
        try {
            int endOfDescriptionIndex = line.indexOf("/at");
            int startOfAtIndex = line.indexOf("/at") + AT_LENGTH;
            String description = line.substring(END_OF_EVENT_INDEX, endOfDescriptionIndex).trim();
            String at = line.substring(startOfAtIndex).trim();

            tasks[numberOfTasks] = new Event(description, at);
            numberOfTasks++;

            printTaskConfirmation();
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(HORIZONTAL_LINE);
            System.out.println("Oh no! Event tasks must be followed by /at keyword!");
            System.out.println("Please try again!");
            System.out.println(HORIZONTAL_LINE);
        }

    }

    private static void addDeadlineTask(String line) {
        try {
            int endOfDescriptionIndex = line.indexOf("/by");
            int startOfByIndex = line.indexOf("/by") + BY_LENGTH;
            String description = line.substring(END_OF_DEADLINE_INDEX, endOfDescriptionIndex).trim();
            String by = line.substring(startOfByIndex).trim();

            tasks[numberOfTasks] = new Deadline(description, by);
            numberOfTasks++;

            printTaskConfirmation();
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(HORIZONTAL_LINE);
            System.out.println("Oh no! Deadline tasks must be followed by /by keyword!");
            System.out.println("Please try again!");
            System.out.println(HORIZONTAL_LINE);
        }
    }

    private static void addTodoTask(String line) {
        String description = line.substring(END_OF_TODO_INDEX).trim();
        tasks[numberOfTasks] = new Todo(description);
        numberOfTasks++;
        printTaskConfirmation();
    }

    private static void exitProgram() {
        isRunning = false;
    }
}
