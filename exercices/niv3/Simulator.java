package exercices.niv3;

import methods.myfunctions;

/*
     * Me créer en POO une classe Voiture :
     * 
     * 
     * Voiture :
     * 
     * Attribut :
     * Marque
     * Modele
     * Puissance (cv, la puissance aura une influence sur l'embrayage, plus la puissance est élevée, plus l'embrayage est sensible)
     * vitesse
     * 
     * Comportement :
     * 
     * Accelerer
     * Decelerer
     * Freiner
     * Marche arrière
     * Arreter
     * Embrayer
     * Débrayer
     * Virer à gauche
     * Virer à droite
     * Changer de voie à gauche
     * Changer de voie à droite
     * Contrôle visibilité (Intérieur, extérieur, angles morts)
     * Clignotant à gauche
     * Clignotant à droite
     * Feu de croisement
     * Essuie-glace
     * Feu warning
     * Klaxonner
     * Marche arrière (On ira du principe que le conducteur manipule le volant pendant la marche arrière)
     * 
     * 
     * A l'aide d'un menu d'action, vous allez conduire une voiture, suivre des instructions d'un moniteur pour simuler un test de conduite.
     * Vous roulez sur Paris, vous devez suivre les instructions suivantes, vous devez respecter les limitations de vitesse et le code de la route:
     * 
     * Voir que l'utilisateur puisse selectionner plusieurs touches puis valider pour passer la prochaine étape.
     * 
     * Notification : Vous êtes sur une route limité à 30 km/h
     * 1. Démarrer la voiture
     * 2. Continuer tout droit
     * 3. Ralentir puis tourner à gauche (vous êtes prioritaire)
     * 4. Continuer tout droit (Il y a une voiture sur l'intersection à droite, vous n'êtes pas prioritaire)
     * 5. Attention, il y a un dos d'âne !
     * 6. Continuer tout droit
     * 7. Il y a un feu rouge, quand il passera au vert, tourner à droite sur l'avenue des Champs-Élysées
     * Notification : Vous êtes sur une route limité à 50 km/h
     * 8. Continuer tout droit
     * 9. Au rond point tourner à gauche
     * 10. Continuer tout droit 
     * 11. Attention, des piétons traversent
     * 12. Continuer tout droit
     * 13. Il y a un feu orange, ralentir, attendre que le feu soit rouge et tourner à gauche sur la voie d'insertion du Périphérique
     * Notification : Vous êtes sur le Périphérique, limité à 70 km/h
     * 14. Continuer tout droit
     * 15. Vous êtes sur la voie de droite, une voiture devant vous est trop lente, dépasser la voiture
     * 16. Attention, il y a un bouchon, veuillez ralentir , prévenir les autres conducteurs à l'arrière, puis s'arrêter
     * 17. Insérer vous dans la voie de droite, pour prendre la sortie A6
     * 18. Continuer tout droit, vous rendrez dans un tunnel
     * Notification : Vous êtes sur une route limité à 110 km/h.
     * 19. Continuer tout droit sur 10 km
     * 20. Vous êtes sur la voie de droite, un véhicule de police cherche à vous dépasser, laisser passer le véhicule en vous débordant sur la gauche
     * 21. Continuer tout droit
     * 22. Prenez la sortie en direction de Orly
     * Notification : Vous êtes sur une route limité à 50 km/h
     * 23. Continuer tout droit 
     * 24. Tournez à droite en direction de l'aéroport d'Orly 
     * Notification : Vous êtes sur une route limité à 30 km/h
     * 25. Continuer tout droit après le rond-point
     * 26. Virer à droite pour entrer dans le parking
     * 27. Garez la voiture en marche arrière
     * 28. Couper le moteur
     * Vous êtes arrivé(e) !!!
     * 
     * Si vous ne respectez pas le code de la route & les limitations de vitesse, le test de conduite a échoué et s'arrête, 
     * le moniteur prendra la main.
     * 
     * Bonus : Rajouter un système de points pour évaluer votre conduite, certaines erreurs ne sont pas éliminatoires mais vous feront perdre des points.
     * par exemple :
     * 
     * // speed
     * - Faire un excès de vitesse +5 km/h : -2 points
     * 
     * - Ne pas céder le passage : -3 points
     * - Brûler un feu orange : -5 points
     * 
     * - Ne pas mettre de feu de croisement dans un tunnel : -2 points
     * 
     * - Ne pas activer les essuie-glaces en cas de pluie : -5 points
     * - Ne pas ralentir avant un dos d'âne : -2 points
     * 
     * la pluie doit tomber aléatoirement pendant le test de conduite, si la pluie tombe, 
     * vous devez activer les essuie-glaces, et les limitations de vitesse sont réduites de 10 km/h.
     * 
     * Le test est sur 40 points, il faut au minimum 30 points pour réussir le test de conduite.
     * 
     */

public class Simulator {

    // Attributs (états)
    private String mark;    //
    private String model;   //  
    private int power;  //
    private int gear;      //
    private int speed;      //
    private int points;      //

    //construteur
    public Simulator(String mark, String model, int power, int gear, int speed, int points){
        this.mark = mark;
        this.model = model;
        this.power = power;
        this.gear = gear;
        this.speed = speed;
        this.points = points;
    }

    public Object[] GetSimulator(){
        return new Object[]{mark, model, power, gear, speed, points};
    };

    public void SetSimulator(String set_mark, String set_model, int set_power, int set_gear, int set_speed, int set_points){
        mark = set_mark;
        model = set_model;
        power = set_power;
        gear = set_gear;
        speed = set_speed;
        points = set_points;
    };

    public void CheckSecurity(boolean CheckAll, boolean[] TurnSignals){
        if(!TurnSignals[0]){
            points -= 2;
            myfunctions.rwkTxtStringV2("Ne pas mettre son clignotant : -2 point, score : "+ points, false, false);
            CheckAll = false;
        }
        if(!CheckAll){
            points -= 2;
            myfunctions.rwkTxtStringV2("Ne pas contrôler les angles morts lors d'un changement de voie : -3 points, score : "+ points, false, false);
            CheckAll = false;
        }
    }

    public static boolean[] CarSteeringWheel(String txt){
        boolean left = false; boolean right = false;
        String option = myfunctions.rwkTxtStringV2(txt+"\n(Q) pour gauche\n(D) pour droite", true, true);
        switch(option){
            case "Q": left = true; right = true; myfunctions.rwkTxtStringV2("Vous avez selectionné gauche", false, true); break;   //  * Gauche
            case "D": left = true; right = true; myfunctions.rwkTxtStringV2("Vous avez selectionné droite", false, true); break;  //  * Droite
            default: myfunctions.rwkTxtStringV2("Veuillez répondre que par ???", false, true); 
            return CarSteeringWheel(txt); //relancement de sécurité
        }
        return new boolean[] {left, right};
    }

    public static boolean CheckAll(){
        myfunctions.rwkTxtStringV2("Vous avez contrôlé en visibilité (Intérieur, extérieur, angles morts)", false, true);
        boolean CheckAll = true;
        return CheckAll;
    }

    public static boolean[] TurnSignals(){
        boolean TurnSignals = true;
        boolean[] wheel = CarSteeringWheel("Vous voudriez activer quel clignotant ?");
        return new boolean[] {TurnSignals, wheel[0], wheel[1]};
    }

    public boolean[] Turn(boolean CheckAll, boolean[] TurnSignals){
        CheckSecurity(CheckAll, TurnSignals);
        boolean turn = true;
        boolean[] wheel = CarSteeringWheel("Vous voudriez tourner quel coté ?");
        return new boolean[] {turn, wheel[0], wheel[1]};
    }

    public boolean[] ChangeLanes(boolean CheckAll, boolean[] TurnSignals){
        CheckSecurity(CheckAll, TurnSignals);
        boolean ChangeLanes = true;
        boolean[] wheel = CarSteeringWheel("Vous voudriez vous déporter sur quelle voie ?");
        return new boolean[] {ChangeLanes, wheel[0], wheel[1]};
    }

    public void Accelerate(int newSpeed){
        speed += newSpeed;
        myfunctions.rwkTxtStringV2("Vous êtes à "+speed+" KM/H !", false, false);
    }
    public void Deccelerate(int newSpeed){
        speed -= newSpeed;
        myfunctions.rwkTxtStringV2("Vous êtes à "+speed+" KM/H !", false, false);
    }
    public void Brake(int newSpeed){
        speed -= newSpeed;
        myfunctions.rwkTxtStringV2("Vous êtes à "+speed+" KM/H !", false, false);
    }

    public static void ToMoveBack(){}

    public void Upgrade(){
        gear ++;
        myfunctions.rwkTxtStringV2("Vous êtes en "+gear+" vitesse", false, false);
    }

    public void Downgrade(){
        gear --;
        myfunctions.rwkTxtStringV2("Vous êtes en "+gear+" vitesse", false, false);

    }

    public boolean WindshieldWiper(){
        boolean windshieldwiper;
        return windshieldwiper = true;
    }

    public static void ClutchEnabled(){}
    public static void ClutchDisabled(){}
    public static void Headlights(){}
    public static void SignalsDetress(){}
    public static void Horn(){}
    public static void Arrest(){}
}

        // myfunctions.rwkTxtStringV2("TurnSignals "+ TurnSignals[0], false, true);
        // myfunctions.rwkTxtStringV2("wheel[0] "+ TurnSignals[1], false, true);
        // myfunctions.rwkTxtStringV2("wheel[1] "+ TurnSignals[2], false, true);