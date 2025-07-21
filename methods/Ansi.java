package methods;

public final class Ansi{
    // Reset & Styles
    public static final String TVR = " "+"\u001B[0m";   //ANSI_RESET
    public static final String TVB = "\u001B[1m";       //BOLD
    public static final String TVI = "\u001B[3m";       //ITALIC

    // Couleurs de texte
    public static final String CBT = "\u001B[30m";      //ANSI_BLACK
    public static final String CRT = "\u001B[31m";      //ANSI_RED
    public static final String CWT = "\u001B[37m";      //ANSI_WHITE

    // Arrière-plans
    public static final String CRB = "\u001B[41m";      //ANSI_RED_BACKGROUND
    public static final String CYB = "\u001B[43m";      //ANSI_YELLOW_BACKGROUND
    public static final String CBB = "\u001B[44m";      //ANSI_BLUE_BACKGROUND
    public static final String CWB = "\u001B[47m";      //ANSI_WHITE_BACKGROUND
    public static final String CPB = "\u001B[45m";      //PURPLE_BACKGROUND
    public static final String CGB = "\u001B[42m";      //GREEN_BACKGROUND

    //conbinaisons
    // Instructions, ...
    public static final String NWB = CWB+CBT+" ";

    //notification / actions d'utilisateur
    public static final String NBW = CBB+" ";

    // réponse correct, étape suivante
    public static final String NGW = CGB+" ";

    // Warning
    public static final String NYR = CYB+CRT+TVB+" ";

    // Sanction
    public static final String NRW = CRB+" ";

    // Score 
    public static final String NPW = CPB+" ";

    // Méthode utilitaire pour formater du texte
    public static String coloriser(String texte, String... codesANSI) {
        StringBuilder sb = new StringBuilder();
        for (String code : codesANSI) {
            sb.append(code);
        }
        return sb.toString() + texte + TVR;
    }
}