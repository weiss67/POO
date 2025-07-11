package methods;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit; //import d'un outil pour comparer entre dates
import java.util.ArrayList; 
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class myfunctions {
    public static Object[] rwkSignUp(boolean identity, boolean age, boolean account){
        String firstname = ""; String lastname = ""; int years = 0; String mail = ""; String createmdp = ""; String checkmdp = "";
        if(identity){
            firstname = rwkTxtStringV2("Votre prénom ?", true, false);
            lastname = rwkTxtStringV2("Votre nom ?", true, false);
        }
        if (age){
            years = rwkTxtInt("Quel est votre âge ?");
        }
        // voir aussi pour ajouter son anniversaire
        if(account){
            mail = rwkTxtStringV2("Votre adresse mail ?", true, false);
            createmdp = rwkTxtStringV2("Votre mot de passe ?", true, false);
            checkmdp = rwkTxtStringV2("Confirmer votre mot de passe ?", true, false);

            if (checkmdp.equals(createmdp)) {
                rwkTxtStringV2("succes_txt", false, false);
            } else { 
                rwkTxtStringV2("refused_txt", false, false);
                return rwkSignUp(identity, age, account);
            }
        }
        return new Object[]{firstname, lastname, years, mail, createmdp, checkmdp};
    }

    //Lister une arrays de A à Z grâce à length pour arrêter, peut être utile pour ajouter à tout moment
    public static String rwkLoopArrays(String[] arrays){
        String result = "";
        for (int i = 0; i < arrays.length; i++){
            result += rwkTxtStringV2("N°"+i+" | "+arrays[i]+"", false, false);
        }
        return result;
    }

    public static String rwkCheckdate(String date_string, int duration, ChronoUnit unit, String sector){
        //ChronoUnit.DAYS or ChronoUnit.MONTHS or ChronoUnit.YEARS

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate local_date = LocalDate.parse(date_string, formatter);

        LocalDate now = LocalDate.now();
        long difference;

        if(sector == "ALIMENTARY"){
            difference = unit.between(now, local_date);
        }else{
            difference = unit.between(local_date, now);
        }

        String result = "";
        //rwkTxtStringV2("Il y a "+difference+" "+unit+" de différence entre aujourd'hui et péremption (Période de 20 % si proche des "+duration+" jours restants)", false, false);
        
        //rwkTxtStringV2(difference+" >= "+duration, false, false);

        if(sector == "ALIMENTARY" && (difference < 0)){ // si au dessous du 0, évite des -1 ou moins
            //rwkTxtStringV2("TEST 1 | Différence de "+ difference, false, false);
            //result = "Périmée"; //fonctionnel
            result = "EXPIRED";
        }else if( (sector == "ALIMENTARY" && (difference <= duration)) || (sector == "ECOMMERCE" && (difference >= duration))){
            //rwkTxtStringV2("TEST 2 LIMITED", false, false);
            //result = "Consommable (Périme bientôt !!!)";
            result = "LIMITED";
        }else{
            //rwkTxtStringV2("TEST 3 Différence de "+ difference, false, false);
            //result = "Consommable";
            result = "VALIDATED";
        }

        return result;
    }

    public static double rwkSwitchCasePrice(String option, double price, boolean isOnSale, int onsale, int reduce){
        switch(option){// voir pour faire des nouvelles functions assez indépendants pour utiliser en tout
            //case "Périmée": 
            case "EXPIRED": 
            price = 0.0; 
            break;
            //case "Consommable (Périme bientôt !!!)": 
            case "LIMITED":
            //rwkTxtStringV2("CHECK rwkSwitchCasePrice 1 "+price, false, false);
            double discount;
            if (isOnSale) {
                //discount = 40;
                discount = onsale;
                rwkTxtStringV2("(Solde de "+onsale+"% appliquée)", false, false); 
            } else {
                //discount = 10;
                discount = reduce;
                rwkTxtStringV2("(Réduction de "+reduce+"% appliquée)", false, false);
            }
            price = myfunctions.rwkOperatorV2("", false, discount, "-%", "", price); 
            break;
            //case "Consommable":
            case "VALIDATED":
            break;
            default: rwkTxtStringV2("default rwkSwitchCasePrice", false, true); 
            return rwkSwitchCasePrice(option, price, isOnSale, onsale, reduce); //relancement de sécurité
        }
        return price;
    }

    public static String addProductType(String []type, String[]adpt_txt) {
        // Implémentez la logique pour ajouter un produit avec son type
        // Par exemple, vous pouvez créer une nouvelle entrée dans la liste avec le type du produit
        String typeName = "";
        try {
        //Déroule la liste de couleur
        rwkTxtStringV2(adpt_txt[0], false, false);
        for (int i = 0; i < type.length; i++) {
            System.out.println("(" + (i + 1) + "). " + type[i]);
        }
        //Demande à l'utilisateur quelle option choisir
        int typeChoice = rwkTxtInt(adpt_txt[1]);

            typeName = type[typeChoice - 1];

                    //Notifie quelle couleur et son prix après avoir selectionné
                    //rwkTxtStringV2(String.format(adpt_txt[2], typeName, price), false, false);
        } catch (Exception e) {
            Exceptioner.TxtException(e,type.length, "");
            addProductType(type, adpt_txt);
        }
        return typeName;
    }

    public static String rwkReference(String reference, int reduct) {
        try {// function qui permet de réduire le mot souhaité au nombre de caractère demandé
            if (reference.length() <= reduct){
                return reference;}// si déjà en 2 caractères, retourn directement sans modif
            return reference.toUpperCase().substring(0, reduct);
        } catch (Exception e) {
            Exceptioner.TxtException(e, 0, "");
            rwkReference(reference, reduct);
        }
        return reference;
    }

    public static String rwkChoiceCategory(String[][] all_categorys) {
        String result = ""; 
        
        // Étape 1: Récupérer toutes les marques (sans doublons)
        // Convertir le Set en List pour accéder par index
        Set<String> marks = new HashSet<>();
        for (String[] mark : all_categorys) {
            marks.add(mark[0]); // Index 0 = marque (prends que les marques)
        }
        List<String> marksList = new ArrayList<>(marks);

        try{
            // déroule une liste de marques
            System.out.println("Marques disponibles:");
            int index_marks = 1;
            for (String mark : marks) {
                System.out.println(index_marks + ". " + mark);
                index_marks++;
            }
            int choiceMark = rwkTxtInt("Choisissez une marque (numéro): ");
            String markChoiced = marksList.get(choiceMark - 1);
            System.out.println("Vous avez choisi : " + markChoiced);
            result = markChoiced;

        } catch (Exception e) {
            Exceptioner.TxtException(e, marksList.size(), "");
            rwkChoiceCategory(all_categorys);
        }        
        return result;
    }

    public static String[] rwkChoiceElement(String[][] all_categorys, String markChoiced) {
        String[] result = new String[3]; // 3 valeurs attendues voir pour mettre plus si il faut

        System.out.println("\nModèles disponibles pour " + markChoiced + ":");
        int modelIndex = 1;
        for (String[] vehicule : all_categorys) {
            if (vehicule[0].equals(markChoiced)) {
                System.out.println(modelIndex + ". " + vehicule[1] + " (" + vehicule[2] + " euros)");
                modelIndex++;
            }
        }
        // créer une liste pour pouvoir indexer tout en prenant que la catégorie selectionnée
        List<String[]> models = new ArrayList<>();
        for (String[] model : all_categorys)
        if (model[0].equals(markChoiced)) models.add(model);

        try{
            int choiceModel = rwkTxtInt("Choisissez un modèle: ");
            String[] selection = models.get(choiceModel - 1);
            System.out.println("Selection: " + selection[1] + " (" + selection[2] + " euros)");

            result[0] = markChoiced; result[1] = selection[1]; result[2] = selection[2];

        } catch (Exception e) {
            Exceptioner.TxtException(e, models.size(), "");
            rwkChoiceElement(all_categorys, markChoiced);
        }        
        return result;
    }

    public static ArrayList<String> rwkAddItem(ArrayList<String> tableau, int index, String[] types, String sector, int duration, ChronoUnit unit, int onsale, int reduce, String[][][] all_categorys){
        
        String name = ""; String typeName = ""; String date_manufacturing = ""; String[] choix; double price = 0.0; String add_item = "";
        if ("MEDICAL_OFFICE".equals(sector)){

            String[] rwktypestxt = {
            /*01*/   "Service %d : %s | Code %s | Prix : %.2f", 
            /*02*/   "Veuillez choisir votre type de produit :", 
            /*03*/   "Type de produit choisi : %s | Code : %s | Prix : %.2f", 
            };

            // for (int i = 0; i < MainJalonGreenCda.TYPES.size(); i++) {
            //     myfunctions.TypeSelection  service = MainJalonGreenCda.TYPES.get(i);
            //     rwkTxtStringV2("Service " + (i+1) + ": " +service.type_txt+" | Code : "+service.type_code+" | Prix : "+ service.type_price, false, false);
            // }

            Object[] userData = rwkSignUp(true, true, false);
            // Extraction des valeurs
            String firstname = (String) userData[0];
            String lastname = (String) userData[1];
            int years = (int) userData[2];

            //typeName = addProductType(types);
            String set_date = rwkDateTime("Quelle date voulez vous prendre pour un RDV ? ", "1", "", "HH:mm", "dd/MM/yyyy", true);
            String set_time = rwkDateTime("A quelle heure souhaitiez vous ? ", "2", "", "HH:mm", "dd/MM/yyyy", true);
            // appels pour convertir en références 
            String ref_d = rwkDateTime(set_date, "4", "", "", "yyyyMMdd", false);
            String ref_t = rwkDateTime(set_time, "5", "", "HHmm", "", false);
            String ref_fn = rwkReference(firstname, 1); String reference_ln = rwkReference(lastname, 1);

            add_item = ref_fn+reference_ln+ref_d+ref_t+" | Prénom : "+firstname+" | Nom : "+lastname+" | Age : "+years+" | Type : mark | RDV le : "+set_date+" à "+set_time;
        }

        if ("DEALERSHIP".equals(sector)){

            rwkTxtStringV2("Vous voulez ajouter une voiture, très bien.", false, false);
            String[] selectionned = rwkChoiceElement(all_categorys[0], rwkChoiceCategory(all_categorys[0]));

            String marque = selectionned[0];// récupère la marque
            String modele = selectionned[1];// récupère le modèle
            price = Double.parseDouble(selectionned[2]); // converti en double car string de base

            // coupure temporaire mettre 1 pour passer
            //rwkTxtStringV2("En attende de suite", true, false);

            String reference_marque = rwkReference(marque, 2); String reference_model = rwkReference(modele, 2);
            String reference_date = rwkDateTime("", "3", "", "HH:mm", "dd-MM-yyyy", false);

            boolean condition = rwkTxtBoolean("Est-il neuf ?", true);
            int kilometrage = rwkTxtInt("Quel est son kilométrage (en km) ?");

            if(kilometrage > 200000){ // au dessous des 200 000 km
                price = rwkOperatorV2("", false, reduce, "-%", "", price);
                rwkTxtStringV2("Réduction de 50% pour kilométrage au dessus des 200 000 KM", false, false);
            }else if(kilometrage > 100000){ // entre 100 000 et 200 000 km
                price = rwkOperatorV2("", false, onsale, "-%", "", price);
                rwkTxtStringV2("Réduction de 25% pour kilométrage entre 100 000 et 200 000 KM", false, false);
            }else if(!condition){ // occasion si condition = false
                price = rwkOperatorV2("", false, 10, "-%", "", price);
                rwkTxtStringV2("Réduction de 10% appliqué pour occasion", false, false);
            }

            String[] adpt_txt = {
            /*01*/   "Liste des couleurs :", 
            /*02*/   "Quel est sa couleur : ", 
            /*03*/   "Vous avez choisi le couleur %s, +%.2f euros", 
            };

            String[] colorChoiced = rwkChoiceElement(all_categorys[1], "couleur métalisée");
            String type_paint = selectionned[0];// récupère le type de peinture
            String color_choiced = selectionned[1];// récupère la couleur de peinture
            price += Double.parseDouble(selectionned[2]); // converti en double car string de base

            add_item = "[code : "+reference_marque+reference_model+"-"+reference_date+"] | ID("+index+")| Marque : "+marque+" | Modele : "+modele+" | Neuf : "+condition+" | Kilométrage : "+kilometrage+" | Couleur : "+color_choiced+" | Prix total : "+String.format("%.2f", price);
        }

        if ("ALIMENTARY".equals(sector) || "ECOMMERCE".equals(sector)){
            name                 = rwkTxtStringV2("Veuillez mettre le nom de l'article", true, false);

            String[] adpt_txt = {
            /*01*/   "Type de produit :", 
            /*02*/   "Veuillez choisir votre type de produit : ", 
            /*03*/   "Type de produit choisi : ", 
            };
            typeName             = addProductType(types, adpt_txt);

            //String test_date            = rwkDateTime("Veuillez mettre la date de fabrication ", "1", "");
            //String test_time            = rwkDateTime("Veuillez mettre l'heure et minute de fabrication ", "2", "");
            //String date_manufacturing   = test_date+" "+test_time; //fonctionnel
            date_manufacturing   = rwkDateTime("Veuillez mettre la date de fabrication ", "1", "", "HH:mm", "dd/MM/yyyy", true);
        }
        
        String date_expiration = ""; String date_expiration_txt = ""; String date_check = date_manufacturing;
        if ("ALIMENTARY".equals(sector)){//Demande la date de péremption si alimentaire
            date_expiration      = rwkDateTime("Veuillez mettre la date de péremption ", "1", "", "HH:mm", "dd/MM/yyyy", true);
            date_expiration_txt = "Date de péremption : "+date_expiration+" | ";
            date_check = date_expiration;
        }
        
        String consumable = ""; boolean isOnSale = false; 
        if ("ALIMENTARY".equals(sector) || "ECOMMERCE".equals(sector)){
            price               = rwkOperatorV2("Prix de base : ", true, 0, "=", "", 0);
            consumable  = rwkCheckdate(date_check, duration, unit, sector); // checking de la limite de date
            if ("ECOMMERCE".equals(sector)){ // solde pour le moment cocerné par le menu commerce
                isOnSale            = rwkTxtBoolean("En solde ? (OUI/NON)", true);}
            price               = rwkSwitchCasePrice(consumable, price, isOnSale, onsale, reduce); // modification du prix en fonction de la date
            add_item = "("+index+") Nom : "+name+"\nType de produit : "+typeName+" | Date de fabrication : "+date_manufacturing+" | "+date_expiration_txt+"Prix : "+String.format("%.2f", price)+" | Etat : "+consumable;
        }

        tableau.add(index, add_item);
        rwkTxtStringV2("Produit ajouté avec succès ! "+add_item, false, false);
        return tableau;
    }

    public static ArrayList<String> rwkRmvItemViaIndex(ArrayList<String> tableau, int index){
        int index_delete = rwkTxtInt("Veuillez mettre l'ID que vous voulez delete");
        if(index_delete >= 0 && index_delete < tableau.size()) {
            tableau.remove(index_delete);
            rwkTxtStringV2(index_delete+" a été deleté !", false, false);
            return tableau; // mets à jour le tableau en retournant
        }
        if (index_delete > tableau.size()) {
            rwkTxtStringV2("Index invalide !", false, false);
            rwkRmvItemViaIndex(tableau, index);
        }
        return tableau;
    }

    public static ArrayList<String> rwkSrhItem(ArrayList<String> tableau, int index){
        String search = rwkTxtStringV2("Veuillez mettre le nom du produit que vous recherchez :", true, false);
        //System.out.print("TEST "+search);

        // if(tableau.contains(search)){
        //     rwkTxtStringV2(tableau.get(tableau.indexOf(search))+"", false, false);
        // }else{
        //     rwkTxtStringV2("Produit introuvable !", false, false);
        //     return rwkSwitchCase(tableau, index);
        // }
        boolean found = false;
        for (String item : tableau) {
            if (item.contains(search)) { // Cherche "Nom : [recherche]" dans la chaîne
                rwkTxtStringV2("Produit trouvé :" + item, false, false);
                found = true;
                break;
            }
        }
        if(!found) {
            rwkTxtStringV2("Aucun produit trouvé pour : " + search, false, false);
        }
        return tableau;
    }

    public static ArrayList<String> rwkSwitchCase(
        ArrayList<String> tableau, 
        int index,      String[] types, 
        String sector,  int duration, 
        ChronoUnit unit, int onsale, int reduce,
        String[][][] all_categorys, String[] details_txt
        ){
        //String option = rwkTxtStringV2("Voulez-vous ? (A) Ajouter un nouvel article | (B) Supprimer un article | (Y) Chercher un article | (W) Afficher la liste d'articles | (X) Quitter", true, true);
        String option = rwkTxtStringV2(details_txt[0], true, true);
        switch(option){// voir pour faire des nouvelles functions assez indépendants pour utiliser en tout
            case "A": 
            if("ALIMENTARY".equals(sector) || "ECOMMERCE".equals(sector) || "DEALERSHIP".equals(sector)){
                tableau = rwkAddItem(tableau, index, types, sector, duration, unit, onsale, reduce, all_categorys);
            }else if("MEDICAL_OFFICE".equals(sector)){
                tableau = rwkAddItem(tableau, index, types, sector, duration, unit, onsale, reduce, all_categorys);
            }
            return rwkSwitchCase(tableau, index + 1, types, sector, duration, unit, onsale, reduce, all_categorys, details_txt); //relance le tableau de proposition avec index ajouté
            case "B": tableau = rwkRmvItemViaIndex(tableau, index);
            return rwkSwitchCase(tableau, index, types, sector, duration, unit, onsale, reduce, all_categorys, details_txt);
            case "Q": rwkSrhItem(tableau, index);
            return rwkSwitchCase(tableau, index, types, sector, duration, unit, onsale, reduce, all_categorys, details_txt);
            case "Y": rwkSrhItem(tableau, index);
            return rwkSwitchCase(tableau, index, types, sector, duration, unit, onsale, reduce, all_categorys, details_txt);
            case "W": rwkLoopArraysList(tableau);
            return rwkSwitchCase(tableau, index, types, sector, duration, unit, onsale, reduce, all_categorys, details_txt);
            case "X": rwkTxtStringV2("Merci au revoir ! ", false, false); break;
            default: rwkTxtStringV2("Veuillez répondre que par (A), (B), (Y) ou (X)", false, true); 
            return rwkSwitchCase(tableau, index, types, sector, duration, unit, onsale, reduce, all_categorys, details_txt); //relancement de sécurité
        }
        return tableau;
    }

    public static String rwkDateTimeFormatter(String prompt){
        //variable formatter qui permet de convertir la date en europe
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        //question pour avoir la date
        String date_string = rwkTxtStringV2(prompt +" (format JJ/MM/AAAA HH:MM)", true, false);

        //Transform le string en variable date mais en US pour travailler à la suite
        LocalDateTime date_time = LocalDateTime.parse(date_string, formatter);

        String formattedDate = date_time.format(formatter);
        System.out.println("Date d'aujourd'hui format Francophone: " + formattedDate);
        return formattedDate;
    }

    //public static String rwkDateTime(String prompt, String operator, String result, String time_txt, String date_txt){
    public static String rwkDateTime(String prompt, String operator, String result, String time_txt, String date_txt, boolean keyboard){ 
        try{
            //String time_txt = "HH"+fh+"mm"; String date_txt = "dd"+fd+"MM"+fd+"yyyy";
            DateTimeFormatter formatter;
            switch(operator){
                case "1": //add date and not time
                formatter = DateTimeFormatter.ofPattern(date_txt);
                String date_string = rwkTxtStringV2(prompt +" (format "+date_txt+")", true, false);
                //checking format date
                LocalDate.parse(date_string, formatter); // check si bien format date
                result = date_string;
                break;

                case "2":  //add time and not date
                formatter = DateTimeFormatter.ofPattern(time_txt);
                String time_string = rwkTxtStringV2(prompt +" (format "+time_txt+")", true, false);
                LocalTime.parse(time_string, formatter); // check si bien format time
                result = time_string;
                break;

                case "3": //get date reel now
                formatter = DateTimeFormatter.ofPattern(date_txt);
                LocalDateTime now = LocalDateTime.now();
                result = now.format(formatter); 
                break;

                case "4":
                // On parse d'abord selon son format d'origine
                LocalDate intermediateDate = LocalDate.parse(prompt, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                // Puis on reformate selon le format demandé (date_txt)
                result = intermediateDate.format(DateTimeFormatter.ofPattern(date_txt));
                break;

                case "5":
               // On parse d'abord selon son format d'origine
                LocalTime intermediateTime = LocalTime.parse(prompt, DateTimeFormatter.ofPattern("HH:mm"));
                // Puis on reformate selon le format demandé (date_txt)
                result = intermediateTime.format(DateTimeFormatter.ofPattern(time_txt));
                break;

                case "6": //releases date in EU
                formatter = DateTimeFormatter.ofPattern(time_txt+" "+date_txt);
                LocalDateTime date_time = LocalDateTime.parse(result, formatter);
                String formattedDate = date_time.format(formatter);
                System.out.println("Date d'aujourd'hui format Francophone: " + formattedDate);
                result = formattedDate; 
                break;

                // voir d'autres options comme prendre le temps et l'afficher en string
                // voir pour ajouter d'autres options comme ajouter soit un an, un jour ect
                // Essyere de voir si possible d'utiliser un variable à la place de parseYear ???

                default: System.out.println("(op_error)"); break;
            }
            return result;
        }catch (Exception e){// revoir le textuel
            Exceptioner.TxtException(e, 0, (date_txt+time_txt));
            return rwkDateTime(prompt, operator, result, time_txt, date_txt, keyboard);
        }
    }

    public static void AfficherTouteValeurTableau(int[] tableau) {
        for (int i = 0; i < tableau.length; i++) {
            System.out.println("Valeur à l'index " + i + " : " + tableau[i]); // Affiche chaque valeur du tableau
        }
    }

    public static void rwkLoopArraysList(ArrayList<String> tableau){
        if (tableau.isEmpty()) {
            rwkTxtStringV2("La liste est vide", false, false);
        }else{
            rwkTxtStringV2("Voici la liste d'articles", false, false);
            for(String nom:tableau){
                rwkTxtStringV2(nom, false, false);
            }
        }
    }

    //public static String TrouverUnNoms(ArrayList<String> stagiaires, String rechercheNom){
    public static String TrouverUnNoms(ArrayList<String> stagiaires){
        String rechercheNom = rwkTxtStringV2("Rechercher :", true, false);

        if(stagiaires.contains(rechercheNom)){
            rwkTxtStringV2(rechercheNom+" Existe dans la liste, à la position "+stagiaires.indexOf(rechercheNom), false, false);
            //return stagiaires;
            return rechercheNom+" Existe dans la liste, à la position "+stagiaires.indexOf(rechercheNom);
        }else{
            //return stagiaires;
            return "Nom introuvable";
        }
    }
    // à regarder plus tard
    public static Object rwkTxt(String prompt, boolean keyboard, boolean keybool){ 
        Scanner sc = new Scanner(System.in);
        System.out.print(prompt+"\n");
        if(keyboard){
            if(keybool){
                return sc.nextBoolean();
            } else  {
                return sc.next();
            }
        } else {
            if (keybool) {
                return Boolean.parseBoolean(prompt);
            } else {
                return prompt;
            }
        }
    }

    public static String rwkTxtStringV2(String prompt, boolean keyboard, boolean touppercase){ 
        Scanner sc = new Scanner(System.in);
        System.out.print(prompt+"\n");
        // Si clavier innatendu
        if(!keyboard){
            // retourne en MAJ si touppercase true ou en normal
            return touppercase ? prompt.toUpperCase() : prompt;
        }
        String imput = sc.nextLine();
        return touppercase ? imput.toUpperCase() : imput;
    }

    public static boolean rwkTxtBoolean(String prompt, boolean convert){ 
        try{
            Scanner sc = new Scanner(System.in); 
            rwkTxtStringV2(prompt, false, true);
            boolean result;
            
            if(convert){
                String bool = sc.nextLine().toUpperCase();
                switch(bool){
                    case "YES": case "TRUE": case "OUI": return true;
                    case "NO": case "FALSE": case "NON": return false;
                    default: rwkTxtStringV2("Veuillez répondre par YES/NO, TRUE/FALSE ou OUI/NON", false, true); 
                    return rwkTxtBoolean(prompt, convert);
                }
            //}else if(!convert){
                //return sc.nextBoolean();
            }else{
                return rwkTxtBoolean(prompt, convert);
            }
        }catch (Exception e){
            Exceptioner.TxtException(e, 0, "");
            return rwkTxtBoolean(prompt, convert);
        }
    }

    public static int rwkTxtInt(String prompt){ 
        try{
            Scanner sc = new Scanner(System.in); 
            rwkTxtStringV2(prompt, false, false);
            return sc.nextInt();
        }catch (Exception e){
            Exceptioner.TxtException(e, 0, "");
            return rwkTxtInt(prompt);
        }
    }
    public static double rwkCalculator(String prompt, double result, boolean add, boolean pct){ 
        try{
            Scanner sc = new Scanner(System.in);
            rwkTxtStringV2(prompt, false, false);
            double clavier = sc.nextInt();
            if(add){
                result += clavier;
            }
            if(pct){
                result = result * (1-clavier/100.0);
            }
            return result;
        }catch (Exception e){
            Exceptioner.TxtException(e, 0, "");
            return rwkCalculator(prompt, result, add, pct);
        }
    }

    public static double rwkOperatorV2(String prompt, boolean scanner, double dft, String operator, String notif, double result){ 
        try{
            Scanner sc = new Scanner(System.in);
            if(scanner){
                rwkTxtStringV2(prompt, false, false);
                dft = sc.nextInt();
            }
            switch(operator){
                case "+":  result += dft; break;
                case "-":  result -= dft; break;
                case "*":  result = dft * result; break;
                case "/":  result = dft / result; break;
                case "+%": result = result * (1+dft/100.0); break; // augmentation
                case "-%": result = result * (1-dft/100.0); break; // réduction
                case "=":  result = dft; break; //laisse comme tel afin d'éviter de créer une function juste pour cela
                default: System.out.println("(op_error) revoir l'opérator"); break;
            }
            if(!notif.equals("")){
                rwkTxtStringV2(notif, false, false);
            }
            //rwkTxtStringV2("CHECK RESULT RWKOPERATOR "+result, false, false);
            return result;
        }catch (Exception e){
            rwkTxtStringV2("Veuillez mettre que des chiffres, virgule autorisé !", false, false);
            return rwkOperatorV2(prompt, scanner, dft, operator, notif, result);
        }
    }

    public static double reconversion(String getmoney, double getcash, String setmoney, double result){
        double eur = 1.0;
        double usd = 1.1410;
        double gbp = 0.8576;
        switch(getmoney){
            case "EUR": result = getcash / eur; break;
            case "USD": result = getcash / usd; break;
            case "GBP": result = getcash / gbp; break;
            default: System.out.println("Monnaie source inconnue."); return 0.0;
        }

        switch(setmoney){
            case "EUR": return result * eur;
            case "USD": return result * usd;
            case "GBP": return result * gbp;
            default: System.out.println("Monnaie cible inconnue."); return 0.0;
        }
    }  

    public static String rwkDeleteName(ArrayList<String> stagiaires, String rechercheNom){
        if(stagiaires.contains(rechercheNom)){
            stagiaires.remove(stagiaires.indexOf(rechercheNom));
            return rechercheNom+" a été delete ";
        }else{
            return "Nom introuvable";
        }
    }

    // Lance un quiz avec question et réponse attendue
    // @param query     = Text attendu en question
    // @param bool      = Réponse false or true attendue
    // @param correct   = Notif en string pour bonne réponse
    // @param incorrect = Notif en string pour mauvaise réponse
    public static String rwkQuizer(String query, boolean bool, String correct, String incorrect){
        try{
        String result = "";
        Boolean answer = rwkTxtBoolean(query + " (true/false)", false);
            if (answer == bool) {
                result = correct;
            } else {
                result = incorrect;
            }
        return result;
        }catch (Exception e){
            Exceptioner.TxtException(e, 0, "");
            return rwkQuizer(query, bool, correct, incorrect);
        }
    }
    public static Boolean rwkQuizerNv2(String query, boolean bool, String correct, String incorrect){
        Boolean result;
        Boolean answer = rwkTxtBoolean(query+" (true/false)", true);
            if (answer == bool) {
                rwkTxtStringV2(correct, false, false);
                result = true;
            } else {
                rwkTxtStringV2(incorrect, false, false);
                result = false;
            }
        return result;
    }
    public static int rwkScore(boolean bool, int win, int los){ // voir plus tard pour ajouter d'autres paramètres
        if (bool) {
            return win;
        } else {
            return los;
        }
    }
    public static int rwkSolde(boolean bool, int solde){ // voir plus tard pour ajouter d'autres paramètres
        int credit; int debit;
        if (bool) {
            credit = rwkTxtInt("Combien voulez-vous mettre ?");
            solde += credit;
        } else {
            debit = rwkTxtInt("Combien voulez-vous retirer ?");
            if (solde >= debit) {
                solde -= debit;
                rwkTxtStringV2("Votre nouveau solde est à "+ solde, false, false);
            } else {
                rwkTxtStringV2("Opération refusée, fond insuffissant !", false, false);
                rwkSolde(bool, solde);
            }
        }
        return solde;
    }
}