package DZ_5.Task5;

public class ServiceProxy implements Service {
    private RealService realService;
    private boolean isAdmin;

    public ServiceProxy(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public void doSomething() {
        if(isAdmin) {
            if(realService == null) {
                realService = new RealService();
            }
            realService.doSomething();
        } else {
            System.out.println("Need admin root");
        }
    }
}
