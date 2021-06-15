package service;

import model.City;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

/**
 * Класс, реализующий основную логику приложения
 */
public class Service {
    /**
     * Метод, считывающий данные из файла построчно
     * @param path путь к файлу
     * @return Лист строк из файла
     */
    public static ArrayList<String> scanFile(Path path) {
        ArrayList<String> cities = new ArrayList<>();
        Scanner scanner = null;

        try {
            scanner = new Scanner(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (scanner != null) {
            scanner.useDelimiter(System.getProperty("line.separator"));
            while (scanner.hasNext()) {
                cities.add(scanner.next());
            }
            scanner.close();
        }

        return cities;
    }

    /**
     * Метод, преобразовывающий лист строк в лист сущностей городов
     * @param rawInfoList лист строк с данными о городах
     * @return Лист сущностей городов
     */
    public static ArrayList<City> getCitiesList(ArrayList<String> rawInfoList) {
        ArrayList<City> cities = new ArrayList<>();

        for (String s : rawInfoList) {
            try {
                String[] data = s.split(";");
                int population = Integer.parseInt(data[4]);
                int foundation = Integer.parseInt(data[5]);
                City city = new City(data[1], data[2], data[3], population, foundation);
                cities.add(city);
            }
            catch (Exception e) {
                System.out.println("Ошибка в данных.");
            }
        }

        return cities;
    }

    /**
     * Метод, сортирующий города по их названиям
     * @param cities лист сущностей городов
     * @return Лист городов, отсортированный по названиям
     */
    public static ArrayList<City> sortCitiesByName(ArrayList<City> cities) {
        ArrayList<City> citiesSorted = new ArrayList<>(cities);
        citiesSorted.sort(new CityComparatorByName());
        return citiesSorted;
    }

    /**
     * Метод, сортирующий города по округам и названиям
     * @param cities - лист сущностей городов
     * @return Лист городов, отсортированный по округам и по названиям
     */
    public static ArrayList<City> sortCitiesByDistrictAndName(ArrayList<City> cities) {
        ArrayList<City> citiesSorted = new ArrayList<>(cities);
        citiesSorted.sort(new CityComparatorByDistrict());
        return citiesSorted;
    }

    /**
     * Метод, определяющий город с наибольшим количеством жителей
     * @param cities лист сущностей городов
     * @return Строка с индексом и населением города с наибольшим количеством жителей
     */
    public static String mostPopulatedCity(ArrayList<City> cities) {
        City[] citiesArray = cities.toArray(new City[0]);
        int maxPopulation = 0;
        int cityNumber = 0;

        for (int i = 0; i < citiesArray.length; i++) {
            if (citiesArray[i].getPopulation() > maxPopulation) {
                maxPopulation = citiesArray[i].getPopulation();
                cityNumber = i;
            }
        }

        return String.format("[%d] = %d", cityNumber, maxPopulation);
    }

    /**
     * Метод, определяющийколичество городов в каждой области
     * @param cities лист сущностей городов
     * @return Информация о количестве городов в каждой области
     */
    public static String citiesInRegions(ArrayList<City> cities) {
        HashMap<String, Integer> countersMap = new HashMap<>();

        for (City city : cities) {
            if (countersMap.containsKey(city.getRegion())) {
                int newAmount = countersMap.get(city.getRegion()) + 1;
                countersMap.put(city.getRegion(), newAmount);
            }
            else {
                countersMap.put(city.getRegion(), 1);
            }
        }

        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Integer> entry : countersMap.entrySet()) {
            result.append(String.format("%s - %d\n", entry.getKey(), entry.getValue()));
        }
        result.deleteCharAt(result.length() - 1);

        return result.toString();
    }

    /**
     * Класс компаратора для поиска городов по названию
     */
    public static class CityComparatorByName implements Comparator<City> {

        @Override
        public int compare(City o1, City o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    }

    /**
     * Класс компаратора для поиска городов по области и названию
     */
    public static class CityComparatorByDistrict implements Comparator<City> {

        @Override
        public int compare(City o1, City o2) {
            if (o1.getDistrict().compareTo(o2.getDistrict()) == 0) {
                return o1.getName().compareTo(o2.getName());
            }
            return o1.getDistrict().compareTo(o2.getDistrict());
        }
    }
}
