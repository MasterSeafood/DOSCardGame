

public class Test2 {
    public static void main(String[] args){

        String test = " ⎽⎽     ⎽⎽   ⎽⎽⎽⎽    ⎽    ⎽   ⎽⎽⎽⎽⎽      ⎽⎽⎽⎽⎽⎽⎽   ⎽    ⎽   ⎽⎽⎽⎽⎽    ⎽   ⎽  \n" + 
        " \\ \\   / /  / ⎽⎽ \\  | |  | | |  ⎽⎽ \\    |⎽⎽   ⎽⎽| | |  | | |  ⎽⎽ \\  | \\ | | \n" + 
        "  \\ \\⎽/ /  | |  | | | |  | | | |⎽⎽) |      | |    | |  | | | |⎽⎽) | |  \\| | \n" +
        "   \\   /   | |  | | | |  | | |  ⎽  /       | |    | |  | | |  ⎽  /  | . ` | \n " + 
        "   | |    | |⎽⎽| | | |⎽⎽| | | | \\ \\       | |    | |⎽⎽| | | | \\ \\  | |\\  | \n" + 
        "    |⎽|     \\⎽⎽⎽⎽/   \\⎽⎽⎽⎽/  |⎽|  \\⎽\\      |⎽|     \\⎽⎽⎽⎽/  |⎽|  \\⎽\\ |⎽| \\⎽| ";


        String test2 = "   ⎽⎽⎽⎽⎽    ⎽⎽⎽⎽    ⎽⎽  ⎽⎽   ⎽⎽⎽⎽⎽    ⎽    ⎽   ⎽⎽⎽⎽⎽⎽⎽   ⎽⎽⎽⎽⎽⎽   ⎽⎽⎽⎽⎽    ⎽    ⎽⎽⎽⎽⎽     ⎽⎽⎽⎽⎽⎽⎽   ⎽    ⎽   ⎽⎽⎽⎽⎽    ⎽   ⎽  \n" + 
        "  / ⎽⎽⎽⎽|  / ⎽⎽ \\  |  \\/  | |  ⎽⎽ \\  | |  | | |⎽⎽   ⎽⎽| |  ⎽⎽⎽⎽| |  ⎽⎽ \\  ( )  / ⎽⎽⎽⎽|   |⎽⎽   ⎽⎽| | |  | | |  ⎽⎽ \\  | \\ | | \n" + 
        " | |      | |  | | | \\  / | | |⎽⎽) | | |  | |    | |    | |⎽⎽    | |⎽⎽) | |/  | (⎽⎽⎽        | |    | |  | | | |⎽⎽) | |  \\| |  \n" + 
        " | |      | |  | | | |\\/| | |  ⎽⎽⎽/  | |  | |    | |    |  ⎽⎽|   |  ⎽  /       \\⎽⎽⎽ \\       | |    | |  | | |  ⎽  /  | . ` | \n" + 
        " | |⎽⎽⎽⎽  | |⎽⎽| | | |  | | | |      | |⎽⎽| |    | |    | |⎽⎽⎽⎽  | | \\ \\       ⎽⎽⎽⎽) |      | |    | |⎽⎽| | | | \\ \\  | |\\  | \n" +
        "  \\⎽⎽⎽⎽⎽|  \\⎽⎽⎽⎽/  |⎽|  |⎽| |⎽|       \\⎽⎽⎽⎽/     |⎽|    |⎽⎽⎽⎽⎽⎽| |⎽|  \\⎽\\     |⎽⎽⎽⎽⎽/       |⎽|     \\⎽⎽⎽⎽/  |⎽|  \\⎽\\ |⎽| \\⎽|";
        // System.out.println(test);

        String test3 = "  _____    ____    __  __   _____    _    _   _______   ______   _____    _    _____     _______   _    _   _____    _   _ ";
        System.out.println("   _____    ____    __  __   _____    _    _   _______   ______   _____    _    _____     _______   _    _   _____    _   _ ");
        
    }

    public static boolean isCompletelyUsed(String[] array, boolean[] cross){
        boolean output = true;
        for (int i = 0; i < arrayLength(array); i++) {
            if(!cross[i]){
                output = false;
            }
        }

        return output;
    }

    public static int arrayLength(String[] input){
        for (int i = 0; i < input.length; i++) {
            if(input[i] == "EEE"){
                return i;
            }
        }
        return input.length;
    }

    public static int[] StringToNumbers(String input){
        String[] preOutput = new String[(input.length()/2) + 1];

        for (int i = 0; i < preOutput.length; i++) {
            preOutput[i] = "";
        }

        boolean insideNumber = false;

        int currIndex = 0;
        
        for (int i = 0; i < input.length(); i++) {
            if(input.charAt(i) < '0' || input.charAt(i) > '9'){
                if(insideNumber == true){
                    currIndex++;
                }
                insideNumber = false;
                
            }else{
                preOutput[currIndex] = preOutput[currIndex] + input.charAt(i);
                insideNumber = true;
            }
        }

        int j = 0;
        while(!preOutput[j++].equals(""));

        int[] output = new int[j-1];

        for (int i = 0; i < output.length; i++) {
            output[i] = Integer.valueOf(preOutput[i]);
        }

        return output;
        }
}
