/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import model.Phone;
import model.PhoneManager;
import java.util.List;
import util.SelectionSort;
/**
 *
 * @author admin
 */
public class PhoneController {
    
    private final PhoneManager manager;

    public PhoneController() {
        manager = new PhoneManager();
    }

    // Add phone
    public void addPhone(Phone phone) {
        manager.addPhone(phone);
    }

    // Get all phones
    public List<Phone> getAllPhones() {
        return manager.getAllPhones();
    }

    // Remove phone
    public void removePhone(Phone phone) {
        manager.removePhone(phone);
    }

    // Update phone
    public void updatePhone(Phone oldPhone, Phone newPhone) {
        manager.updatePhone(oldPhone, newPhone);
    }

    // Selection Sort by release date
    public List<Phone> sortByReleaseDate() {
        return SelectionSort.sortByReleaseDate(
                manager.getAllPhones()
        );
    }
    
}
