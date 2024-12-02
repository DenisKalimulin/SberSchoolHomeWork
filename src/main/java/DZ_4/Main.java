package DZ_4;

import DZ_4.Models.User;
import DZ_4.Services.UserService;
import DZ_4.Terminals.TerminalImpl;
import DZ_4.Utils.PinValidator;
import DZ_4.Utils.TerminalServer;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        User user = userService.createUser("Denis", "1234");
        PinValidator pinValidator = new PinValidator();
        TerminalServer terminalServer = new TerminalServer();

        TerminalImpl terminal = new TerminalImpl(terminalServer, pinValidator);

        System.out.println(terminal.checkBalance(user));

        // terminal.depositMoney(user, BigDecimal.valueOf(100));


    }
}
