package exercices.niv1;

import methods.myfunctions;

public class MainBottle {
    private static Bottle bCola;
    
    public static void main(String[] args){
        bCola = new Bottle("Coca-Cola", 100, false, false);
        rwkSwitchCase("abc");
    }

    public static String rwkSwitchCase(String test){
        String option = myfunctions.rwkTxtString("A pour ouvrir\nB pour fermer\nY pour boire\nW Voir la quantité actuelle\nX pour arreter d'intéragir", true, true);
        switch(option){// voir pour faire des nouvelles functions assez indépendants pour utiliser en tout
            case "A": bCola.Open();
            return rwkSwitchCase(test); //relance le tableau de proposition avec index ajouté
            case "B": bCola.Close();
            return rwkSwitchCase(test); 
            case "Y": bCola.Drink(25); bCola.Break();
            return rwkSwitchCase(test);
            case "W": bCola.Show();
            return rwkSwitchCase(test);
            case "X": myfunctions.rwkTxtString("Merci au revoir ! ", false, false);
            return "Fin";
            default: myfunctions.rwkTxtString("Veuillez répondre que par (A), (B), (Y) ou (X)", false, true); 
            return rwkSwitchCase(test); //relancement de sécurité
        }
    }
}
