package DZ_5.Task6;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Класс PerfomanceProxy реализует интерфейс InvocationHandle, который
 * определяет метод invoke() - этот метод будет вызываться каждый раз,
 * когда вызывается метод на прокси-объекте.
 */
public class PerfomanceProxy implements InvocationHandler {
    private final Object proxy;

    public PerfomanceProxy(Object proxy) {
        this.proxy = proxy;
    }

    /**
     * Статический метод создает новый прокси-объект для указанного целевого объекта.
     * @param newProxy целевой объект, который будет проксироваться
     * @return прокси-объект. реализующий интерфейсы целевого объекта
     * @param <T> тип целевого объекта
     */
    public static <T> T createProxy(T newProxy) {
        return (T) Proxy.newProxyInstance(newProxy.getClass().getClassLoader(), newProxy.getClass().getInterfaces(), new PerfomanceProxy(newProxy));
    }

    /**
     * Перехватывает вызовы методов на прокси-объекте.
     * Если метод помечен аннотацией {@link Metric}, то измеряется время его выполнения.
     * @param proxy the proxy instance that the method was invoked on
     *
     * @param method the {@code Method} instance corresponding to
     * the interface method invoked on the proxy instance.  The declaring
     * class of the {@code Method} object will be the interface that
     * the method was declared in, which may be a superinterface of the
     * proxy interface that the proxy class inherits the method through.
     *
     * @param args an array of objects containing the values of the
     * arguments passed in the method invocation on the proxy instance,
     * or {@code null} if interface method takes no arguments.
     * Arguments of primitive types are wrapped in instances of the
     * appropriate primitive wrapper class, such as
     * {@code java.lang.Integer} or {@code java.lang.Boolean}.
     *
     * @return результат выполнения метода
     * @throws Throwable если возникает исключение при вызове метода
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.isAnnotationPresent(Metric.class)) {
            long startTime = System.nanoTime();
            Object result = method.invoke(this.proxy, args);
            long endTime = System.nanoTime();
            System.out.println("Время работы метода: " + (endTime - startTime) + "(в наносек)");
            return result;
        }
        return method.invoke(proxy, args);
    }
}
