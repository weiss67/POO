package exercices.niv2;

// pour l'instant garder ici mais à mettre dans fichiers functions
import java.time.Duration;
import java.time.LocalDateTime;
import methods.myfunctions;

public class MainQuiz {
    
    public static void main(String[] args){
        Quiz[] querys = { // Création d'un tableau
            new Quiz("Est-ce que le soleil se lève à l'est ?", true),
            new Quiz("Est-ce que 2 + 2 = 5 ?", false),
            new Quiz("Est-ce que l'eau bout à 100 degrés ?", true)
        };
        // Voir pour double afin de mettre 0.5 mais transformer en parse
        runTimeQuiz(querys, 1);
    }

    // à voir pour retravailler dans le fichier functions et faire appel ici
    // @param querys    = List de questions attendues 
    // @param timeLimit = Durée limitée
    public static void runTimeQuiz(Quiz[] querys, int timeLimit){
        LocalDateTime endTime = LocalDateTime.now().plusMinutes(timeLimit);
        int score = 0;

        myfunctions.rwkTxtStringV2("Vous avez "+timeLimit+" minute(s) pour répondre", false, false);
        
        for (Quiz query : querys){ // Liste toutes les questions créees dans le tableau
            
            if(LocalDateTime.now().isAfter(endTime)){ // Vérifie si le temps est passé après le temps déterminé
                myfunctions.rwkTxtStringV2("Temps écoulé ! Fin du quiz", false, false);
                break; // stop tout la function
            }
            // remaining = restant donc temps restant
            long remainingTime = Duration.between(LocalDateTime.now(), endTime).getSeconds();
            myfunctions.rwkTxtStringV2("Temps restant : "+remainingTime+" seconde(s)", false, false);

            score = query.Query(score); // Appel à la function Question et réponse
            myfunctions.rwkTxtStringV2("Voici le score actuel : "+score, false, false);
        }
        myfunctions.rwkTxtStringV2("Voici le score final : "+score, false, false);
    }
}