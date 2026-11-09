/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import model.Phone;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author admin
 */
public class SelectionSort {
    
    public static List<Phone> sortByReleaseDate(List<Phone> phones) {

        ArrayList<Phone> sorted = new ArrayList<>(phones);

        int n = sorted.size();

        for (int i = 0; i < n - 1; i++) {

            int minIndex = i;

            for (int j = i + 1; j < n; j++) {

                if (sorted.get(j).getReleaseDate()
                        .isBefore(sorted.get(minIndex).getReleaseDate())) {

                    minIndex = j;
                }
            }

            // Swap elements
            Phone temp = sorted.get(i);

            sorted.set(i, sorted.get(minIndex));

            sorted.set(minIndex, temp);
        }

        return sorted;
    }
    
}
