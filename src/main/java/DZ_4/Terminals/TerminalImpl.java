package DZ_4.Terminals;

import DZ_4.Exceptions.AccountIsLockedException;
import DZ_4.Models.User;
import DZ_4.Utils.PinValidator;
import DZ_4.Utils.TerminalServer;

import java.math.BigDecimal;

public class TerminalImpl implements Terminal {
    private final TerminalServer terminalServer;
    private final PinValidator pinValidator;

    public TerminalImpl(TerminalServer terminal, PinValidator pinValidator) {
        this.terminalServer = terminal;
        this.pinValidator = pinValidator;
    }

    /**
     * Для проверки баланса с помощью метода checkBalance нужно ввести правильный пин-код, затем показывается баланс.
     *
     * @param user пользователь у которого проверяем баланс
     * @return баланс
     */
    @Override
    public BigDecimal checkBalance(User user) {
        if(pinValidator.isValidPin(user.getPinCode())) {
            return user.getBalance();
        } else {
            return BigDecimal.ZERO;
        }
    }

    /**
     * Метод для снятия денег.
     * Использует методы классов PinValidator для проверки правильности введенного пин-кода, затем
     * с помощью метода balanceDown класса TerminalServer снимает сумму кратную 100
     * @param user пользователь у которого снимаем деньги со счета
     * @param amount сумма
     */
    @Override
    public void takeMoney(User user, BigDecimal amount) {
        pinValidator.isValidPin(user.getPinCode());
        terminalServer.balanceDown(user, amount);
    }

    /**
     * Метод для пополнения баланса.
     * Использует методы классов PinValidator для проверки введенного пин-кода, затем
     * с помощью метода balanceUp класса TerminalServer снимает сумму кратную 100
     * @param user пользователь, которому пополняем баланс
     * @param amount сумма
     */
    @Override
    public void depositMoney(User user, BigDecimal amount) {
        pinValidator.isValidPin(user.getPinCode());
        terminalServer.balanceUp(user, amount);
    }
}
