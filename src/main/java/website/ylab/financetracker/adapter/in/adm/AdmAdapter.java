package website.ylab.financetracker.adapter.in.adm;

import website.ylab.financetracker.application.domain.model.auth.Role;
import website.ylab.financetracker.application.port.in.adm.AdmUseCase;

import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class AdmAdapter {
    private final AdmUseCase admUseCase;
    private final Scanner scanner;

    public AdmAdapter(AdmUseCase admUseCase) {
        this.admUseCase = admUseCase;
        this.scanner = new Scanner(System.in);
    }

    public String getUsers() {
        if (!admUseCase.isAdmin()) {
            return "You do not have permission to perform such an operation.";
        }
        return admUseCase.getUsers().toString();
    }

    public String getUserTransactions() {
        if (!admUseCase.isAdmin()) {
            return "You do not have permission to perform such an operation.";
        }
        System.out.println("Enter user ID to view transactions");
        return admUseCase.getUserTransactions(getUserId()).toString();
    }

    public String blockUser() {
        if (!admUseCase.isAdmin()) {
            return "You do not have permission to perform such an operation.";
        };
        System.out.println("Enter the user ID to block");
        return admUseCase.blockUser(getUserId()).toString();
    }

    public String unblockUser() {
        if (!admUseCase.isAdmin()) {
            return "You do not have permission to perform such an operation.";
        };
        System.out.println("Enter the user ID to unblock");
        return admUseCase.unblockUser(getUserId()).toString();
    }

    public String deleteUser() {
        if (!admUseCase.isAdmin()) {
            return "You do not have permission to perform such an operation.";
        };
        System.out.println("Enter the user ID to delete");
        return admUseCase.deleteUser(getUserId()).toString();
    }

    public String changeUserRole() {
        if (!admUseCase.isAdmin()) {
            return "You do not have permission to perform such an operation.";
        };
        System.out.println("Enter the user ID to change role");
        long id = getUserId();
        Role role = getRole();
        return admUseCase.changeUserRole(role, id).toString();
    }

    private long getUserId() {
        long id=0;
        do {
            try {
                id = Long.parseLong(getData("Enter a user id"));
            } catch (NumberFormatException e) {
                System.out.println("Invalid id format");
            }
        } while (id <= 0);
        return id;
    }

    private Role getRole() {
        Role role=null;
        do {
            Optional<Role> optional = Role.fromString(getData("Enter new role (USER, ADMIN)"));
            if (optional.isEmpty()) {
                System.out.println("Invalid role input");
            } else {
                role = optional.get();
            }
        } while (Objects.isNull(role));
        return role;
    }

    private String getData(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
}