package DZ_4.Terminals;

import DZ_4.Models.User;

import java.math.BigDecimal;

public interface Terminal {
    BigDecimal checkBalance(User user);
    void takeMoney(User user, BigDecimal amount);
    void depositMoney(User user, BigDecimal amount);
}
