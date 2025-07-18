package exercices.niv3;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import methods.Ansi;
import methods.myfunctions;

public class MainSimulator {
    private static Simulator DMW;

    public static void main(String[] args){
        String mark = "BUGATTI"; String model = "Divo"; int power = 10;
        int gear = 0; int speed = 0; int points = 40;
        DMW = new Simulator(mark, model, power, gear, speed, points);

        //Object  String ???
        Object[][] list_simulator = {
            // instructions, expected_actions

            // instructions, speed, gear, expected_actions
            //{"Notification : Vous êtes sur une route limité à 30 km/h",
            //0,   0, ""},
            {"Notification : Vous êtes sur une route limité à 30 km/h \n 1. Démarrer la voiture",     
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

            myfunctions.rwkTxtStringV2("\n"+Ansi.NPW+"Score : "+(points)+" points."+Ansi.TVR, false, false);
            //Instruction donnée
            //0.instructions, 1.speed, 2.gear, 3.expected_actions
            System.out.println("\n"+Ansi.NWB+list_simulator[i][0]+Ansi.TVR);
            int speed_limited = (int) list_simulator[i][1];
            int gear_expected = (int) list_simulator[i][2];

            // random rainning (pluie aléatoire pour chaque action)
            boolean rainning = false; Random random = new Random();
            if(random.nextBoolean()){
                rainning = true;
                myfunctions.rwkTxtStringV2(Ansi.NYR+"(!) Attention il pleut !"+Ansi.TVR, false, false);
            }

            // new = création d'un nouveau tableau
            boolean windshieldwiper = rwkSwitchCase(false, new boolean[]{false, false, false}, false);

            // récupère les données des attributs
            //0.mark, 1.model, 2.power, 3.gear, 4.speed, 5.points
            Object[] get_simulator = DMW.GetSimulator();
            int get_gear = (int) get_simulator[3];
            int get_speed = (int) get_simulator[4];
            int get_points = (int) get_simulator[5];

            int suivi_penality = 0;
            myfunctions.rwkTxtStringV2("CHECK POINTS "+get_points, false, false);

            //System.out.println("Vitesse actuelle : "+speed+" KM/H");
            
            if(rainning){ // baisse de 10 kilomètre la limitation en cas de pluie
                if(speed_limited >= 10){ // vérifie si bien minimum 10KM/H
                    speed_limited -= 10;
                }
                if(!windshieldwiper){
                    suivi_penality = 5;
                    myfunctions.rwkTxtStringV2(Ansi.NRW+"Ne pas activer les essuie-glaces en cas de pluie : -5 points"+Ansi.TVR, false, false);
                    myfunctions.rwkTxtStringV2(Ansi.NPW+"Score "+(get_points - suivi_penality)+" points."+Ansi.TVR, false, false);

                }   
            }

            if(get_speed > speed_limited){
                suivi_penality = 2;
                System.out.println(Ansi.NRW+"Limitation à "+speed_limited+" KM/H non respectée car vous êtes à "+get_speed+" KM/H !"+Ansi.TVR);
                System.out.println(Ansi.NRW+"Excès de vitesse +5 km/h : -2 points."+Ansi.TVR);
                System.out.println(Ansi.NPW+"Score "+(get_points - suivi_penality)+" points."+Ansi.TVR);
            }

            if(get_gear > gear_expected || get_gear < gear_expected){
                System.out.println(Ansi.NRW+"Moteur cassé car vous étiez en "+get_gear+" alors que c'était "+gear_expected+" attendue !"+Ansi.TVR);
            }

            DMW.SetSimulator(mark, model, power, get_gear, get_speed, (get_points - suivi_penality));

            // prévoir -- si instruction non suivie afin de relancer

            Object[] check_simulator = DMW.GetSimulator();
            int check_points = (int) check_simulator[5];
            myfunctions.rwkTxtStringV2("CHECK POINTS "+check_points, false, false);
        
            myfunctions.rwkTxtStringV2("Nous allons passer à l'instruction suivante ", false, false);
        }
    }

    public static boolean rwkSwitchCase(boolean CheckAll, boolean[] TurnSignals, boolean windshieldwiper){
        
        //CBB+TVI+TVR
        String option = myfunctions.rwkTxtStringV2(" ", true, true);
        
        myfunctions.rwkTxtStringV2("", false, false);

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
            return rwkSwitchCase(CheckAll, TurnSignals, windshieldwiper); //relance le tableau de proposition avec index ajouté

            case "D": if(newSpeed > 0){
            DMW.Deccelerate(newSpeed);} // Decelerer
            return rwkSwitchCase(CheckAll, TurnSignals, windshieldwiper);

            case "F": if(newSpeed > 0){
            DMW.Brake(newSpeed);} // Freiner 
            return rwkSwitchCase(CheckAll, TurnSignals, windshieldwiper);

            case "S": DMW.ToMoveBack(); // Marche arrière
            return rwkSwitchCase(CheckAll, TurnSignals, windshieldwiper);

            case "CE": DMW.ClutchEnabled(); // Embrayer Clutch Enabled
            return rwkSwitchCase(CheckAll, TurnSignals, windshieldwiper);

            case "CD": DMW.ClutchDisabled(); // Débrayer Clutch Disabled
            return rwkSwitchCase(CheckAll, TurnSignals, windshieldwiper);

            case "UG": DMW.Upgrade(); // upgrader
            return rwkSwitchCase(CheckAll, TurnSignals, windshieldwiper);

            case "DG": DMW.Downgrade(); // retrograder
            return rwkSwitchCase(CheckAll, TurnSignals, windshieldwiper);

            case "T": DMW.Turn(CheckAll, TurnSignals); // Virer gauche/droite
            CheckAll = false; TurnSignals = new boolean[]{false, false, false}; // reset
            return rwkSwitchCase(CheckAll, TurnSignals, windshieldwiper); // OK

            case "CL": DMW.ChangeLanes(CheckAll, TurnSignals); // Changer de voie à gauche/droite
            CheckAll = false; TurnSignals = new boolean[]{false, false, false}; // reset
            return rwkSwitchCase(CheckAll, TurnSignals, windshieldwiper); // OK

            case "CA": CheckAll = DMW.CheckAll(); // Contrôle visibilité (Intérieur, extérieur, angles morts)
            return rwkSwitchCase(CheckAll, TurnSignals, windshieldwiper); // OK

            case "TS": TurnSignals = DMW.TurnSignals(); // Clignotant à gauche/droite
            return rwkSwitchCase(CheckAll, TurnSignals, windshieldwiper);  // OK

            case "HL": DMW.Headlights(); // Feu de croisement
            return rwkSwitchCase(CheckAll, TurnSignals, windshieldwiper);

            case "WW": windshieldwiper = DMW.WindshieldWiper(); // Essuie-glace
            return rwkSwitchCase(CheckAll, TurnSignals, windshieldwiper);

            case "SD": DMW.SignalsDetress(); // Feu warning
            return rwkSwitchCase(CheckAll, TurnSignals, windshieldwiper);

            case "H": DMW.Horn(); // Klaxon
            return rwkSwitchCase(CheckAll, TurnSignals, windshieldwiper);

            // case "W": DMW.AllInputs(); // voir pour mettre une listing de touche
            // return rwkSwitchCase(CheckAll, TurnSignals);
            case "X": DMW.Arrest(); // Arreter
            return rwkSwitchCase(CheckAll, TurnSignals, windshieldwiper);
            case "OK":;
            //return "Fin";
            return windshieldwiper;
            default: myfunctions.rwkTxtStringV2("Veuillez répondre que par ???", false, true); 
            return rwkSwitchCase(CheckAll, TurnSignals, windshieldwiper); //relancement de sécurité
        }
    }
}


// voir pour faire attendre "CD" = clignotant droite
// "CG" clignotant