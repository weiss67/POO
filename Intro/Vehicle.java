//package Intro;

public class Vehicle {

    // Attributs (états)
    private String mark;
    private String model;
    private String type;
    private int weight;
    private Double price;
    private int editionLimited;

    //construteur
    public Vehicle(String mark, String model, String type, int weight, Double price, int editionLimited){
        this.mark = mark;
        this.model = model;
        this.type = type;
        this.weight = weight;
        this.price = price;
        this.editionLimited = editionLimited;
    }

    //méthodes voir pour en ajouter d'autres
    public void EngineOn(){
        System.out.println("Le modèle "+model+" de la marque "+mark+" a le moteur démarré désormais !");

    }




}
