package exercices.niv3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import methods.myfunctions;

public class MainSimulator {
    private static Simulator DMW;
    
    public static void main(String[] args){
        DMW = new Simulator("BUGATTI", "Divo", 10, 0, 0, 40);

        //Object  String ???
        Object[][] list_simulator = {
            // instructions, expected_actions

            // instructions, speed, gear, expected_actions
            {"Notification : Vous êtes sur une route limité à 30 km/h",
            0,   0, ""},
            {"1. Démarrer la voiture",     
            30,    3,    ""},
            {"2. Continuer tout droit",     
            30,    3,    ""},
            {"3. Ralentir puis tourner à gauche (vous êtes prioritaire)",     
            30,    2,   ""},
            {"4. Continuer tout droit (Il y a une voiture sur l'intersection à droite, vous n'êtes pas prioritaire)",    
            30,    2,   ""},
            {"5. Attention, il y a un dos d'âne !",     
            20,    2,   ""},
            {"6. Continuer tout droit",     
            30,    3,   ""},
            {"7. Il y a un feu rouge, quand il passera au vert, tourner à droite sur l'avenue des Champs-Élysées",     
            30,    2,   ""},
            {"Notification : Vous êtes sur une route limité à 50 km/h",     
            50,    4,   ""},
            {"8. Continuer tout droit",     
            50,    4,   ""},
            {"9. Au rond point tourner à gauche",     
            50,    3,   ""},
            {"10. Continuer tout droit ",     
            50,    4,   ""},
            {"11. Attention, des piétons traversent",     
            50,    3,   ""},
            {"12. Continuer tout droit",     
            50,    4,   ""},
            {"13. Il y a un feu orange, ralentir, attendre que le feu soit rouge et tourner à gauche sur la voie d'insertion du Périphérique",     
            50,    2,   ""},
            {"Notification : Vous êtes sur le Périphérique, limité à 70 km/h",     
            70,    5,   ""},
            {"14. Continuer tout droit",     
            70,    5,   ""},
            {"15. Vous êtes sur la voie de droite, une voiture devant vous est trop lente, dépasser la voiture",     
            70,    5,   ""},
            {"16. Attention, il y a un bouchon, veuillez ralentir , prévenir les autres conducteurs à l'arrière, puis s'arrêter",     
            70,    0,   ""},
            {"17. Insérer vous dans la voie de droite, pour prendre la sortie A6",     
            70,    4,   ""},
            {"18. Continuer tout droit, vous rendrez dans un tunnel",     
            70,    5,   ""},
            {"Notification : Vous êtes sur une route limité à 110 km/h.",    
            110,   5,   ""},
            {"19. Continuer tout droit sur 10 km",     
            110,   5,   ""},
            {"20. Vous êtes sur la voie de droite, un véhicule de police cherche à vous dépasser, laisser passer le véhicule en vous débordant sur la gauche",     
            110,   5,   ""},
            {"21. Continuer tout droit",    
            110,   5,   ""},
            {"22. Prenez la sortie en direction de Orly",     
            110,   4,   ""},
            {"Notification : Vous êtes sur une route limité à 50 km/h",     
            50,    4,   ""},
            {"23. Continuer tout droit",     
            50,    4,   ""},
            {"24. Tournez à droite en direction de l'aéroport d'Orly",     
            50,    2,   ""},
            {"Notification : Vous êtes sur une route limité à 30 km/h",     
            30,    3,   ""},
            {"25. Continuer tout droit après le rond-point",     
            30,    3,   ""},
            {"26. Virer à droite pour entrer dans le parking",     
            30,    2,   ""},
            {"27. Garez la voiture en marche arrière",     
            30,    2,   ""},
            {"28. Couper le moteur",     
            0,     0,   ""}
        };

        for (int i = 0; i < list_simulator.length; i++) {
            //Instruction donnée
            System.out.println(list_simulator[i][0]);

            // new = création d'un nouveau tableau
            rwkSwitchCase(false, new boolean[]{false, false, false});

            Object[] get_simulator = DMW.GetSimulator();
            //0.mark, 1.model, 2.power, 3.gear, 4.speed, 5.points
            System.out.println("Vitesse actuelle : "+get_simulator[5]+" KM/H");
            
            System.out.println("Vitesse limitée : "+list_simulator[i][1]+" KM/H");
            System.out.println("Boîte à vitesse : "+list_simulator[i][2]+" G");

            // prévoir -- si instruction non suivie afin de relancer
        }
    }

    public static String rwkSwitchCase(boolean CheckAll, boolean[] TurnSignals){
        
        String option = myfunctions.rwkTxtStringV2("\n(W) pour avoir toutes les touches en liste", true, true);

        int newSpeed = 0; int letters = 1; int Numbers = 2;
        // Pattern = Entre A-a et Z-z en premier caractère puis deux chiffres
        Pattern pattern = Pattern.compile("^([A-Za-z]){"+letters+"}(\\d{"+Numbers+"})$");
        Matcher matcher = pattern.matcher(option);

        if(matcher.matches()){
            // intègre à l'option pour la première lettre du permier group match
            option = matcher.group(1);
            
            // Prends les chiffres matchés en 2ème group
            String numberStr = matcher.group(2);
            newSpeed = Integer.parseInt(numberStr);
        }

        switch(option){
            case "Z": if(newSpeed > 0){
            DMW.Accelerate(newSpeed);} // Accelerer
            return rwkSwitchCase(CheckAll, TurnSignals); //relance le tableau de proposition avec index ajouté

            case "D": if(newSpeed > 0){
            DMW.Deccelerate(newSpeed);} // Decelerer
            return rwkSwitchCase(CheckAll, TurnSignals);

            case "F": if(newSpeed > 0){
            DMW.Brake(newSpeed);} // Freiner 
            return rwkSwitchCase(CheckAll, TurnSignals);

            case "S": DMW.ToMoveBack(); // Marche arrière
            return rwkSwitchCase(CheckAll, TurnSignals);

            case "CE": DMW.ClutchEnabled(); // Embrayer Clutch Enabled
            return rwkSwitchCase(CheckAll, TurnSignals);

            case "CD": DMW.ClutchDisabled(); // Débrayer Clutch Disabled
            return rwkSwitchCase(CheckAll, TurnSignals);

            case "UG": DMW.Upgrade(); // upgrader
            return rwkSwitchCase(CheckAll, TurnSignals);

            case "DG": DMW.Downgrade(); // retrograder
            return rwkSwitchCase(CheckAll, TurnSignals);

            case "T": DMW.Turn(CheckAll, TurnSignals); // Virer gauche/droite
            CheckAll = false; TurnSignals = new boolean[]{false, false, false}; // reset
            return rwkSwitchCase(CheckAll, TurnSignals); // OK

            case "CL": DMW.ChangeLanes(CheckAll, TurnSignals); // Changer de voie à gauche/droite
            CheckAll = false; TurnSignals = new boolean[]{false, false, false}; // reset
            return rwkSwitchCase(CheckAll, TurnSignals); // OK

            case "CA": CheckAll = DMW.CheckAll(); // Contrôle visibilité (Intérieur, extérieur, angles morts)
            return rwkSwitchCase(CheckAll, TurnSignals); // OK

            case "TS": TurnSignals = DMW.TurnSignals(); // Clignotant à gauche/droite
            return rwkSwitchCase(CheckAll, TurnSignals);  // OK

            case "HL": DMW.Headlights(); // Feu de croisement
            return rwkSwitchCase(CheckAll, TurnSignals);

            case "WW": DMW.WindshieldWiper(); // Essuie-glace
            return rwkSwitchCase(CheckAll, TurnSignals);

            case "SD": DMW.SignalsDetress(); // Feu warning
            return rwkSwitchCase(CheckAll, TurnSignals);

            case "H": DMW.Horn(); // Klaxon
            return rwkSwitchCase(CheckAll, TurnSignals);

            // case "W": DMW.AllInputs(); // voir pour mettre une listing de touche
            // return rwkSwitchCase(CheckAll, TurnSignals);
            case "X": DMW.Arrest(); // Arreter
            return rwkSwitchCase(CheckAll, TurnSignals);
            case "OK": myfunctions.rwkTxtStringV2("Nous allons passer à l'étape suivante ", false, false);
            return "Fin";
            default: myfunctions.rwkTxtStringV2("Veuillez répondre que par ???", false, true); 
            return rwkSwitchCase(CheckAll, TurnSignals); //relancement de sécurité
        }
    }
}


// voir pour faire attendre "CD" = clignotant droite
// "CG" clignotant