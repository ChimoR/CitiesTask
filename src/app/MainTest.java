package app;

import model.City;
import org.junit.Assert;
import org.junit.Test;
import service.ConsoleListener;
import service.Service;

import java.nio.file.Path;
import java.util.ArrayList;


/**
 * Класс тестов
 */
public class MainTest {

    /** Эталонные переменные */
    String s1 = "1;Самара;Самарский;Поволжский;1200000;1736;";
    String s2 = "2;Москва;Московский;Центральный;7200000;1100;";
    String s3 = "3;Санкт-Петербург;Ленинградский;Северо-Западный;5000000;1703;";
    String s4 = "4;Тольятти;Самарский;Поволжский;707000;1737;";
    String s5 = "5;Гатчина;Ленинградский;Северо-Западный;90000;1796;";

    City city1 = new City("Самара", "Самарский", "Поволжский", 1200000, 1736);
    City city2 = new City("Москва", "Московский", "Центральный", 7200000, 1100);
    City city3 = new City("Санкт-Петербург", "Ленинградский", "Северо-Западный", 5000000, 1703);
    City city4 = new City("Тольятти", "Самарский", "Поволжский", 707000, 1737);
    City city5 = new City("Гатчина", "Ленинградский", "Северо-Западный", 90000, 1796);

    @Test
    public void scanFile() {
        ArrayList<String> expected = Service.scanFile(ConsoleListener.PATH);

        ArrayList<String> actual = new ArrayList<>();
        actual.add(s1);
        actual.add(s2);
        actual.add(s3);
        actual.add(s4);
        actual.add(s5);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getCitiesList() {
        ArrayList<City> expected = Service.getCitiesList(Service.scanFile(ConsoleListener.PATH));

        ArrayList<City> actual = new ArrayList<>();
        actual.add(city1);
        actual.add(city2);
        actual.add(city3);
        actual.add(city4);
        actual.add(city5);

        Assert.assertEquals(expected, actual);

    }

    @Test
    public void sortCitiesByName() {
        ArrayList<City> expected = Service.sortCitiesByName(Service.getCitiesList(Service.scanFile(ConsoleListener.PATH)));

        ArrayList<City> actual = new ArrayList<>();
        actual.add(city5);
        actual.add(city2);
        actual.add(city1);
        actual.add(city3);
        actual.add(city4);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void sortCitiesByDistrictAndName() {
        ArrayList<City> expected = Service.sortCitiesByDistrictAndName(Service.getCitiesList(Service.scanFile(ConsoleListener.PATH)));

        ArrayList<City> actual = new ArrayList<>();
        actual.add(city1);
        actual.add(city4);
        actual.add(city5);
        actual.add(city3);
        actual.add(city2);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void mostPopulatedCity() {
        String expected = Service.mostPopulatedCity(Service.getCitiesList(Service.scanFile(ConsoleListener.PATH)));
        String actual = "[1] = 7200000";

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void citiesInRegions() {
        String expected = Service.citiesInRegions(Service.getCitiesList(Service.scanFile(ConsoleListener.PATH)));

        String actual = "Ленинградский - 2\n"+
                "Московский - 1\n" +
                "Самарский - 2";

        Assert.assertEquals(expected, actual);
    }

}
