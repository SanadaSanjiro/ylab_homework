package website.ylab.financetracker;

import website.ylab.financetracker.auth.TrackerUser;
import website.ylab.financetracker.auth.UserAuthService;
import website.ylab.financetracker.commands.Command;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Enter your commands here");
        Scanner scanner = new Scanner(System.in);
        String currentUserName="";
        while (true) {
            TrackerUser currentUser = UserAuthService.getCurrentUser();
            if (Objects.nonNull(currentUser)) {
                currentUserName = currentUser.getUsername();
            } else {
                currentUserName = "";
            }
            System.out.print(currentUserName + "> ");
            String s = scanner.nextLine();
            if (s.trim().equalsIgnoreCase("EXIT"))
                break;
            System.out.println(Command.runCommand(StringSplitter.SplitString(s)));
        }
    }
}
