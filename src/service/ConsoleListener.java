package service;

import model.City;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static service.Service.*;

/**
 * Класс, реализующий управляющую логику
 */
public class ConsoleListener {

    /** Константы расположения файла */
    public static final String FILE = "/Users/rataevroman/IdeaProjects/First/src/TestFile";
    public static final Path PATH = Paths.get(FILE);

    public static void listen() {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Choose option: \n" +
                "1 - get list of cities \n" +
                "2 - get list of cities sorted by name \n" +
                "3 - get list of cities sorted district and name \n" +
                "4 - get the most populated city in list \n" +
                "5 - get amount of cities in each district");

        int number = 0;
        try {
            number = Integer.parseInt(console.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> rawData = scanFile(PATH);
        ArrayList<City> citiesRaw = getCitiesList(rawData);

        if (number == 1) {
            for (City city : citiesRaw) {
                System.out.println(city);
            }
        }
        if (number == 2) {
            ArrayList<City> citiesSortedByName = sortCitiesByName(citiesRaw);
            for (City city : citiesSortedByName) {
                System.out.println(city);
            }
        }
        if (number == 3) {
            ArrayList<City> citiesSortedByDistrict = sortCitiesByDistrictAndName(citiesRaw);
            for (City city : citiesSortedByDistrict) {
                System.out.println(city);
            }
        }
        if (number == 4) {
            System.out.println(mostPopulatedCity(citiesRaw));
        }
        if (number == 5) {
            System.out.println(citiesInRegions(citiesRaw));
        }
    }
}
