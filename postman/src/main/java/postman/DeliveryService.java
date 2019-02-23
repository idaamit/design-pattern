package postman;

import java.util.function.BiConsumer;

public class DeliveryService implements Observable{

    protected final PackagesStorage packagesStorage;
    private PostmanImpl postman;
    protected DeliveryTask deliveryTask;

    public DeliveryService(PackagesStorage packagesStorage, DeliveryTask deliveryTask) {
        this.packagesStorage = packagesStorage;
        postman = new PostmanImpl(this.packagesStorage);
        this.deliveryTask = deliveryTask;
    }

    public void startWorkDay(){
        packagesStorage.addPackages(deliveryTask.getAllPackages());
        postman.deliver(deliveryTask);
    }

    @Override
    public void addObserver(Observer observer, BiConsumer<String, Address> consumer) {
        postman.addObserver(observer, consumer);
    }

    @Override
    public void removeObserver(Observer observer) {
        postman.removeObserver(observer);
    }

    @Override
    public void notifyObserver(String fromAddress, Address toAddress) {
        postman.notifyObserver(fromAddress, toAddress);
    }
}
