
package methods;

public class Exceptioner {

    public static void TxtException(Exception e, int total, String txt){
        String errorType = e.getClass().getSimpleName(); // Récupère le nom simple de l'exception
        String errorMessage = e.getMessage(); // Récupère le message d'erreur
        String err = "Erreur : ";
        String messageErr= "";
 
        switch (errorType) {
            case "InputMismatchException":
                messageErr = err+"Entrée invalide | Format attendu (nombre)";
                break;
            case "ArrayIndexOutOfBoundsException":

   
            if(total > 0) {
                messageErr = err+"Index hors limites | Veuillez choisir un nombre entre 1 et "+total;
            } else {
                messageErr = err+"Liste vide | Veuillez ajouter des éléments à la liste avant de choisir un index.";
            }

            
            break;
            case "IndexOutOfBoundsException":
                messageErr = err+"Index hors limites";
                break;
            case "NullPointerException":
                messageErr = err+"Objet non initialisé (Null)";
                break;
            case "NumberFormatException":
                messageErr = err+"Conversion numérique impossible";
                break;
            case "DateTimeParseException":
                messageErr = err+"Date invalide | Format attendu ("+txt+")";
                break;
            default:
                messageErr = err+"inconnue : " + errorMessage;
                break;
        }

        System.out.println(messageErr);
    }
    
}