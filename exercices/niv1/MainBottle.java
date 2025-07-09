package exercices.niv1;

import methods.myfunctions;

public class MainBottle {
    public static void main(String[] args){
        Bottle bCola = new Bottle("Coca-Cola", 100, false, false);
        bCola.Drink(25);

        //rwkSwitchCase(Bottle bCola);
        //public static ArrayList<String> rwkSwitchCase(String[] tableau){

        String option = myfunctions.rwkTxtString("A pour ouvrir, B pour fermer, Q, Y, W, X", true, true);
        switch(option){// voir pour faire des nouvelles functions assez indépendants pour utiliser en tout
            case "A": bCola.Open(); break;
            //return rwkSwitchCase(tableau); //relance le tableau de proposition avec index ajouté
            case "B": bCola.Close(); break;
            //return rwkSwitchCase();
            case "Y": bCola.Drink(25);
            //return rwkSwitchCase();
            case "W": bCola.show();
            //return rwkSwitchCase();
            case "X": myfunctions.rwkTxtString("Merci au revoir ! ", false, false); break;
            default: myfunctions.rwkTxtString("Veuillez répondre que par (A), (B), (Y) ou (X)", false, true); 
            //return rwkSwitchCase(); //relancement de sécurité
        }

        //}



    }
}
