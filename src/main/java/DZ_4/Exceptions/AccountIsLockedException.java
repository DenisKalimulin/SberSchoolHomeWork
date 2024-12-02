package DZ_4.Exceptions;

import java.util.Timer;

public class AccountIsLockedException extends Exception {
    Timer timer = new Timer();
    public AccountIsLockedException(String message) {
        super(message);
    }
}
