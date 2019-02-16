package postman.decorator;

import org.fluttercode.datafactory.impl.DataFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import postman.*;
import postman.DeliverService;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Thread.sleep;

public class DeiveryTest {
    private DataFactory dataFactory;

    @Before
    public void setUp() throws Exception {
        dataFactory = new DataFactory();
    }

    @Test
    public void simpleDelivery() {
        postman.decorator.DeliverService deliverService = new postman.decorator.DeliverService(4,10);
        HashMap<Address, JButton> existingAddresses = deliverService.drawMap();

        List<postman.PackageInfo> packages = new ArrayList<>();
        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address(City.London, "street1", 3));
        addresses.add(new Address(City.Paris, "street2", 10));
        addresses.add(new Address(City.NY, "street3", 6));
        addresses.add(new Address(City.Paris, "street2", 5));
        addresses.add(new Address(City.London, "street2", 1));
        addresses.add(new Address(City.Rome, "street1", 3));
        addresses.add(new Address(City.London, "street3", 9));
        addresses.add(new Address(City.London, "street1", 2));
        for (Address address : addresses) {
            addAddressToPackage(existingAddresses, packages, address);
        }

        final int actualDeliver = deliverService.deliver(new DeliveryTask() {
            @Override
            public City[] getCities() {
                return packages.stream().map(postman.PackageInfo::getAddress).map(Address::getCity).distinct().toArray(City[]::new);
            }

            @Override
            public List<postman.PackageInfo> getAllPackages() {
                return packages;
            }

            @Override
            public String[] getStreet(City city) {
                return packages.stream()
                        .filter(packageInfo -> packageInfo.getAddress().getCity() == city)
                        .map(PackageInfo::getAddress)
                        .map(Address::getStreet)
                        .distinct()
                        .toArray(String[]::new);
            }
        });
        System.out.println("Total time "  + actualDeliver);
        try {
            sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(320,actualDeliver);
    }
    private void addAddressToPackage(HashMap<Address, JButton> existingAddresses, List<postman.PackageInfo> packages, Address address) {
        packages.add(new postman.PackageInfo(((JButton) existingAddresses.get(address)).getLocationOnScreen(), address, "Mr. "+ dataFactory.getLastName()));
    }

}