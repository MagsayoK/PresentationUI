/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author admin
 */
public class PhoneManager {
    
    private final ArrayList<Phone> phones;

    public PhoneManager() {
        phones = new ArrayList<>();
    }

    // Add phone
    public void addPhone(Phone phone) {
        phones.add(phone);
    }

    // Get all phones
    public List<Phone> getAllPhones() {
        return new ArrayList<>(phones);
    }

    // Remove phone
    public void removePhone(Phone phone) {
        phones.remove(phone);
    }

    // Update phone
    public void updatePhone(Phone oldPhone, Phone newPhone) {

        int index = phones.indexOf(oldPhone);

        if (index != -1) {
            phones.set(index, newPhone);
        }
    }
    
}
