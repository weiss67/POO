package exercices.niv3;

import methods.Ansi;
import methods.myfunctions;

    /*
     * A l'aide d'un menu d'action, vous allez conduire une voiture, suivre des instructions d'un moniteur pour simuler un test de conduite.
     * Vous roulez sur Paris, vous devez suivre les instructions suivantes, vous devez respecter les limitations de vitesse et le code de la route:
     * 
     * Voir que l'utilisateur puisse selectionner plusieurs touches puis valider pour passer la prochaine étape.
     *
     * Si vous ne respectez pas le code de la route & les limitations de vitesse, le test de conduite a échoué et s'arrête, 
     * le moniteur prendra la main.
     * 
     * Bonus : Rajouter un système de points pour évaluer votre conduite, certaines erreurs ne sont pas éliminatoires mais vous feront perdre des points.
     * par exemple :
     * 
     * la pluie doit tomber aléatoirement pendant le test de conduite, si la pluie tombe, 
     * vous devez activer les essuie-glaces, et les limitations de vitesse sont réduites de 10 km/h.
     * 
     * Le test est sur 40 points, il faut au minimum 30 points pour réussir le test de conduite.
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
            myfunctions.rwkTxtStringV2(Ansi.NRW+"Ne pas mettre son clignotant : -2 point"+Ansi.TVR, false, false);
            myfunctions.rwkTxtStringV2(Ansi.NPW+"Score : "+points+" points"+Ansi.TVR, false, false);
            CheckAll = false;
        }
        if(!CheckAll){
            points -= 2;
            myfunctions.rwkTxtStringV2(Ansi.NRW+"Ne pas contrôler les angles morts lors d'un changement de voie : -3 points"+Ansi.TVR, false, false);
            myfunctions.rwkTxtStringV2(Ansi.NPW+"Score : "+points+" points"+Ansi.TVR, false, false);
            CheckAll = false;
        }
    }

    public static boolean[] CarSteeringWheel(String txt){
        boolean left = false; boolean right = false;
        String option = myfunctions.rwkTxtStringV2(Ansi.NWB+txt+Ansi.NWB+" ([Q] pour gauche / [D] pour droite)"+Ansi.TVR, true, true);
        switch(option){
            case "Q": left = true; right = true; myfunctions.rwkTxtStringV2(Ansi.NBW+"Vous avez selectionné gauche"+Ansi.TVR, false, true); break;   //  * Gauche
            case "D": left = true; right = true; myfunctions.rwkTxtStringV2(Ansi.NBW+"Vous avez selectionné droite"+Ansi.TVR, false, true); break;  //  * Droite
            default: myfunctions.rwkTxtStringV2(Ansi.NYR+"Veuillez répondre que par [Q] ou [D]"+Ansi.TVR, false, true); 
            return CarSteeringWheel(txt); //relancement de sécurité
        }
        return new boolean[] {left, right};
    }

    public static boolean CheckAll(){
        myfunctions.rwkTxtStringV2(Ansi.NBW+"Vous avez contrôlé en visibilité (Intérieur, extérieur, angles morts)"+Ansi.TVR, false, true);
        boolean CheckAll = true;
        return CheckAll;
    }

    public static boolean[] TurnSignals(){
        boolean TurnSignals = true;
        boolean[] wheel = CarSteeringWheel(Ansi.NWB+"Vous voudriez activer quel clignotant ?"+Ansi.TVR);
        return new boolean[] {TurnSignals, wheel[0], wheel[1]};
    }

    public boolean[] Turn(boolean CheckAll, boolean[] TurnSignals){
        CheckSecurity(CheckAll, TurnSignals);
        boolean turn = true;
        boolean[] wheel = CarSteeringWheel(Ansi.NWB+"Vous voudriez tourner quel coté ?"+Ansi.TVR);
        return new boolean[] {turn, wheel[0], wheel[1]};
    }

    public boolean[] ChangeLanes(boolean CheckAll, boolean[] TurnSignals){
        CheckSecurity(CheckAll, TurnSignals);
        boolean ChangeLanes = true;
        boolean[] wheel = CarSteeringWheel(Ansi.NWB+"Vous voudriez vous déporter sur quelle voie ?"+Ansi.TVR);
        return new boolean[] {ChangeLanes, wheel[0], wheel[1]};
    }





    public void Accelerate(int add_speed){
        int actual_spped = speed + add_speed;

        if(actual_spped > 0 && actual_spped <= 20 && gear == 1){
            speed += add_speed;
        }else if(actual_spped > 20 && actual_spped <= 30 && gear == 2){
            speed += add_speed;
        }else if(actual_spped > 30 && actual_spped <= 40 && gear == 3){
            speed += add_speed;
        }else if(actual_spped > 40 && actual_spped <= 50 && gear == 4){
            speed += add_speed;
        }else if(actual_spped > 50 && gear == 5){
            speed += add_speed;
        }else{
            myfunctions.rwkTxtStringV2(Ansi.NBW+"Impossible, vitesse insufissante !"+Ansi.TVR, false, false);
        }
        myfunctions.rwkTxtStringV2(Ansi.NBW+"Vous êtes à "+speed+" KM/H !"+Ansi.TVR, false, false);
    }
    public void Deccelerate(int newSpeed){
        speed -= newSpeed;
        myfunctions.rwkTxtStringV2(Ansi.NBW+"Vous êtes à "+speed+" KM/H !"+Ansi.TVR, false, false);
    }
    public void Brake(int newSpeed){
        speed -= newSpeed;
        myfunctions.rwkTxtStringV2(Ansi.NBW+"Vous êtes à "+speed+" KM/H !"+Ansi.TVR, false, false);
    }

    public void Arrest(){
        speed = 0;
        myfunctions.rwkTxtStringV2(Ansi.NBW+"Vous êtes à l'arrêt !"+Ansi.TVR, false, false);
    }


    public static void ToMoveBack(){}

    public void Gearbox(int newSpeed){
        String status_gear = "";
        myfunctions.rwkTxtStringV2("newSpeed "+newSpeed, false, false);

        //if(newSpeed == 1 && 10 <= speed){
        if(newSpeed == 1){
            gear = 1;
            status_gear = "1er";
        } else if(newSpeed == 2 && speed > 10 && 20 <= speed){
            gear = 2;
            status_gear = "2ème";
        } else if(newSpeed == 3 && speed > 20 && 30 <= speed){
            gear = 3;
            status_gear = "3ème";
        } else {
            myfunctions.rwkTxtStringV2(Ansi.NBW+"Vous ne pouvez pas changer de vitesse"+Ansi.TVR, false, false);
        }

        myfunctions.rwkTxtStringV2(Ansi.NBW+"Vous êtes en "+status_gear+" vitesse"+Ansi.TVR, false, false);

        //gear ++;
        //myfunctions.rwkTxtStringV2(Ansi.NBW+"Vous êtes en "+gear+"(1er)ème vitesse"+Ansi.TVR, false, false);
    }

    // public void Downgrade(){
    //     gear --;
    //     myfunctions.rwkTxtStringV2(Ansi.NBW+"Vous êtes en "+gear+"(1er)ème vitesse"+Ansi.TVR, false, false);

    // }

    public boolean WindshieldWiper(boolean windshieldwiper){
        myfunctions.rwkTxtStringV2(Ansi.NBW+"Vous avez activé les essuies-glaces."+Ansi.TVR, false, false);
        return windshieldwiper = true;
    }

    public static void Headlights(){}
    public static void SignalsDetress(){}
    public static void Horn(){}
}

        // myfunctions.rwkTxtStringV2("TurnSignals "+ TurnSignals[0], false, true);
        // myfunctions.rwkTxtStringV2("wheel[0] "+ TurnSignals[1], false, true);
        // myfunctions.rwkTxtStringV2("wheel[1] "+ TurnSignals[2], false, true);