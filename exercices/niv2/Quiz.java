/*  Exercice n°2 : 
 * 
 * Me créer en POO une classe Quizz avec les attributs suivants :
 * 
 * Question
 * Reponses (3 réponses dans un tableau)
 * Bonne réponse
 * Terminée (Vrai ou Faux)
 * 
 * Son comportement :
 * 
 * Afficher La Question et les réponses ( Utiliser les index pour afficher la question et les réponses correspondantes)
 * Répondre
 * Verdict (Bonne réponse = 1 point ou mauvaise réponse = 0 point , puis terminé = true)
 * Afficher la Bonne Réponse (Après avoir répondu si on a eu faux)
 * 
 * Diagramme UML et code Java exigés
 * 
 * Dans le main le but sera de créer des objets Quizz dynamiquement en fonction du nombres de questions créer dans un tableau ou une list avec leurs 3 réponses.
 * Vous aurez besoin d'une boucle 
 * 
 * Bonus : Créer la possibilité de mettre un minuteur qui arrête le quizz après la minute de votre choix, il faudra utiliser les localDatetime 
 * Par exemple vous avez défini 10 min de temps limite, si vous commencez le quiz à 10h10 alors à 10h20 le quiz est terminée
 */

package exercices.niv2;

import methods.myfunctions;

public class Quiz {

    // Attributs (états)
    private String query; //  Question attendue en String
    private boolean answer; // Réponse attendue en true ou false

    //construteur
    public Quiz(String query, Boolean answer){
        this.query = query;
        this.answer = answer;
    }

    //méthodes voir pour en ajouter d'autres
    //public void Query(){
    public int Query(int score){
        boolean bool;
        String correct = "Bonne réponse !"; String incorrect = "Mauvaise réponse !";
        bool = myfunctions.rwkQuizerNv2(query, answer, correct, incorrect);
        return score += myfunctions.rwkScore(bool, 1, 0);
    }
}