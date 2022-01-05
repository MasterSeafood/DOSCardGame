public class Test {
    public static void main(String[] args){
        String[] myLists = new String[3];

        myLists[0] = "0,1,2";
        myLists[1] = "0";
        myLists[2] = "0,1";

        String[] combos = comboMaker(myLists);

        for (String string : combos) {
            System.out.println(string);
        }
        

    }

    public static String[] comboMaker(String[] input){
        int product = 1;
        for (int i = 0; i < input.length; i++) { //count number of possibilities
            product *= symbolCounter(input[i], ',') + 1;
        }

        String[] output = new String[product];
        String currString = "";
        

        for (int n = 0; n < product; n++) { //creating array (length A x B x C ...)
            currString = "";
            for (int i = 0; i < input.length; i++) { //creating string (length input)
                currString = currString + Integer.toString(generateTerm(input, n, i)) + ((i==input.length-1) ? "" : "|");
            }
            output[n] = currString;
        }

        return output;
    }

    public static int generateTerm(String[] input, int n, int termIndex){
        int denomProd = 1;
        for (int i = termIndex+1; i < input.length; i++) {
            denomProd *= symbolCounter(input[i], ',') + 1;
        }

        return (n/denomProd) % (symbolCounter(input[termIndex], ',')+1);

    }
    
    public static int symbolCounter(String input, char symbol){
        int count = 0;

        for (int i = 0; i < input.length(); i++) {
            if(input.charAt(i) == symbol){
                count++;
            }
        }

        return count;
    }

    

}
