package com.lira.automobileLira.menu;

import com.lira.automobileLira.model.AutomobileData;
import com.lira.automobileLira.model.AutomobileInfo;
import com.lira.automobileLira.model.AutomobileModel;
import com.lira.automobileLira.service.AutomobileAPI;
import com.lira.automobileLira.service.ConvertData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu {

    private final String URL = "https://parallelum.com.br/fipe/api/v1";
    private final Scanner read = new Scanner(System.in);
    private final AutomobileAPI automobileAPI = new AutomobileAPI();
    private final ConvertData convertData = new ConvertData();

    public void showMenu() {
        String link;
        String json;

        System.out.println("What type of automobile are you looking for? (car, truck, motorcycle)");
        var automobileType = read.nextLine();

        if (automobileType.equals("car")) {
            link = URL + "/carros/marcas";
        } else if (automobileType.equals("truck")) {
            link = URL + "/motos/marcas";
        } else {
            link = URL + "/caminhoes/marcas";
        }

        json = automobileAPI.getData(link);
        List<AutomobileData> automobileData = convertData.getDataList(json, AutomobileData.class);
        automobileData.stream()
                        .sorted(Comparator.comparing(AutomobileData::code))
                                .forEach(System.out::println);


        System.out.println("Which branch do you want to check?");
        var automobileBrandCode = read.nextLine();

        if(automobileType.equals("car")){
            automobileType = "carros";
        } else if (automobileType.equals("truck")){
            automobileType = "caminhoes";
        } else {
            automobileType = "motos";
        }

        link = URL + "/" + automobileType + "/marcas/" + automobileBrandCode + "/modelos";
        json = automobileAPI.getData(link);

        System.out.println(link);

        var automobileListModel = convertData.getData(json, AutomobileModel.class);
        System.out.println("\nModels by Brand: ");
        automobileListModel.models().stream()
                .sorted(Comparator.comparing(AutomobileData::code))
                .forEach(System.out::println);

        System.out.println("Type the name of the car you want to check?");
        var automobileName = read.nextLine();

        List<AutomobileData> filteredModels = automobileListModel.models().stream()
                .filter(m -> m.name().toLowerCase().contains(automobileName.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nFiltered models.");
        filteredModels.forEach(System.out::println);

        System.out.println("Please type the code of the models you want to check.");
        var automobileModelCode = read.nextLine();

        link = link + "/" + automobileModelCode + "/anos";
        System.out.println(link);
        json = automobileAPI.getData(link);

        List<AutomobileData> years = convertData.getDataList(json, AutomobileData.class);
        List<AutomobileInfo> vehicles = new ArrayList<>();

        for (int i = 0; i < years.size(); i++) {
            var urlYears = link + "/" + years.get(i).code();
            json = automobileAPI.getData(urlYears);
            AutomobileInfo vehicle = convertData.getData(json, AutomobileInfo.class);
            vehicles.add(vehicle);
        }

        System.out.println("\nAll vehicles filtered: ");
        vehicles.forEach(System.out::println);
    }
}
