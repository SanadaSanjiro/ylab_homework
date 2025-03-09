package website.ylab.financetracker.adm;

import website.ylab.financetracker.ServiceProvider;
import website.ylab.financetracker.auth.Role;
import website.ylab.financetracker.auth.TrackerUser;
import website.ylab.financetracker.auth.UserAuthService;
;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

/**
 * Prompts the user for information required for administrator operations
 */
public class AdmDataInput {
    private final AdmService admService = ServiceProvider.getAdmService();
    private final Scanner scanner = new Scanner(System.in);

    public String getUsers() {
        if (!isAdmin()) {
            return "You do not have permission to perform such an operation.";
        }
        return admService.getUsers().toString();
    }

    public String getUserTransactions() {
        if (!isAdmin()) {
            return "You do not have permission to perform such an operation.";
        }
        System.out.println("Enter user ID to view transactions");
        return admService.getUserTransactions(getUserId()).toString();
    }

    public String blockUser() {
        if (!isAdmin()) {
            return "You do not have permission to perform such an operation.";
        };
        System.out.println("Enter the user ID to block");
        return admService.blockUser(getUserId());
    }

    public String deleteUser() {
        if (!isAdmin()) {
            return "You do not have permission to perform such an operation.";
        };
        System.out.println("Enter the user ID to delete");
        return admService.deleteUser(getUserId());
    }

    public String changeUserRole() {
        if (!isAdmin()) {
            return "You do not have permission to perform such an operation.";
        };
        System.out.println("Enter the user ID to change role");
        long id = getUserId();
        Role role = getRole();
        return admService.changeUserRole(role, id);
    }

    private Optional<TrackerUser> getUserById(long id) {
        return ServiceProvider.getUserService().getAllUsers().stream()
                .filter(u->u.getId()==id)
                .findFirst();
    }

    private boolean isAdmin() {
        TrackerUser currentUser = UserAuthService.getCurrentUser();
        return currentUser.getRole().equals(Role.ADMIN);
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

    private String getData(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
}
