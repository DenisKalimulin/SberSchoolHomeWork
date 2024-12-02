package DZ_4.Utils;

import DZ_4.Models.User;

import java.math.BigDecimal;

public class TerminalServer {

    public void balanceUp(User user, BigDecimal amount) {
        if(amount.remainder(BigDecimal.valueOf(100)).compareTo(BigDecimal.ZERO) == 0) {
            user.setBalance(user.getBalance().add(amount));
            System.out.println( "Баланс: " + user.getBalance());
        } else {
            System.out.println("Невозможно выполнить операцию. Пополнить можно только сумму кратную 100");
        }
    }

    public void balanceDown(User user, BigDecimal amount) {
        if(amount.remainder(BigDecimal.valueOf(100)).compareTo(BigDecimal.ZERO) == 0) {
            user.setBalance(user.getBalance().subtract(amount));
            System.out.println( "Баланс: " + user.getBalance());
        } else {
            System.out.println("Невозможно выполнить операцию. Снять можно только сумму кратную 100");
        }
    }
}
