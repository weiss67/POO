package exercices.niv1;

import java.util.Random;
import methods.myfunctions;

public class Bottle {

    // Attributs (états)
    private String container; //  * Liquide (Eau,Coca, Jus d'orange)
    private int centiliter; //  * quantité (en cl)
    private boolean open; //  * Est ouvert ( Oui ou non)
    private boolean empty; //  * Est vide ( Oui ou non)

    //construteur
    public Bottle(String container, int centiliter, Boolean open, Boolean empty){
        this.container = container;
        this.centiliter = centiliter;
        this.open = open;
        this.empty = empty;
    }

    //méthodes voir pour en ajouter d'autres
    public void Open(){
        open = true;
        System.out.println("La bouteille contenant du "+container+" au volume de "+centiliter+" est ouverte.");
    }

    public void Drink(int drinked){
        if(open){
        //System.out.println("Vous êtes en train de boire la bouteille contenant du "+container+" au volume de "+centiliter+".");
        drinked = myfunctions.rwkTxtInt("Vous buvez combien de centilitres ?");
        // voir pour mettre une function qu'on puisse choisir pour boir e la quantité qu'on veut boire.
        if(drinked <= centiliter){
            centiliter = centiliter - drinked;
            System.out.println("La bouteille "+container+" est mtnt au volume de "+centiliter+" cl.");
        }else{
            System.out.println("La bouteille "+container+" ne contient que "+centiliter+" cl.");
        }
        }else{
            System.out.println("La bouteille contenant du "+container+" au volume de "+centiliter+" cl n'est pas ouverte.");
        }
    }

    public void Close(){
        open = false;
        System.out.println("La bouteille contenant du "+container+" au volume de "+centiliter+" cl est fermée.");
    }

    public void Show(){
        System.out.println("La bouteille contenant du "+container+" est actuellement au volume de "+centiliter+" cl.");
    }

    public void Break(){
        Random random = new Random();
        if(random.nextBoolean()){
            myfunctions.rwkTxtStringV2("Oups! la bouteille s'est vidé accidentellement", false, false);
            centiliter = 0;
        }
    }
}