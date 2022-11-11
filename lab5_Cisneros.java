/* The goal for this lab is to solve True or False logic Expressions, the First step would be to ask User to enter the expression, then we will check if the expression is valid
 * It the Expression is Valid then we will move on to the next step which will be evaluating the expression.
 * The way handled the first step is by making methods that will check if the user input is valid, then that input will be turned into an array and the array needs
 * To follow rules of order to be considered valid, if the array returns as true then the array is valid.
 * We will send the values of the array into two stacks, a stack that stores Boolean values and a stack that holds int values which can be translated to logic expression values
 * Such as 2 = and, 3 = or. we will pop out of the logic stack and depending on the value we will do work with the Boolean values, until there is no more logic left
 * The last value in the Boolean stack once there's no more logic will be the answer.*/
import java.util.Scanner;
public class lab5_Cisneros{
    public static void main(String[] args){
    System.out.println("write an expression. Please enter Space efter every input, valid inputs include [ (, ), and, or, not, iff, implies, true, false and Pn values ]:");
    String[] validInputs= {"(",")","and","or","not","iff","implies","true","false"};
    int[] arr= validInput(validInputs);// checks if inputs are valide
    boolean expressionValid = false;

    while(!expressionValid){
        if(isValid(arr)){
            System.out.println("Expression is Valid!");
            expressionValid = true;
        }else{
            System.out.println("Expression is not Valid, please try again");
            arr= validInput(validInputs);
        }
    }
    //printArr(arr);
    definePn(arr);
    PnStack pn = new PnStack(arr.length);
    CompariStack logic = new CompariStack(arr.length);

    for( int i = 0 ; i < arr.length; i++){
        if(arr[i] == 0){
           logic.push(0);
        }else if(arr[i] == 1){
            logic.push(1);
        }else if(arr[i] == 2){
            logic.push(2);
        }else if(arr[i] == 3){
            logic.push(3);
        }else if(arr[i] == 4){
            logic.push(4);
        }else if(arr[i] == 5){
            logic.push(5);
        }else if(arr[i] == 6){
            logic.push(6);
        }else if(arr[i] == 7){
           pn.push(true);
        }else if(arr[i] == 8){
            pn.push(false);
        }
    }

    while(!logic.isEmpty()){
        if(logic.top() == 0){ // " ( "
            logic.pop();

        }else if(logic.top() == 1){// " ) "
            logic.pop();

        }else if(logic.top() == 2){// " and "
            logic.pop();
            boolean tmp = pn.pop();// 1st pop
            if(logic.top() == 4){// NOT
                tmp = !tmp;// 1st pop
                logic.pop();
            }
            boolean tmp2 = pn.pop();// 2nd pop compare
            //System.out.print("Compare: "+ tmp2+" && "+ tmp);
            boolean expressionEquatesTo = ( tmp && tmp2); 
            //System.out.print(" = "+ expressionEquatesTo+"\n");
            pn.push(expressionEquatesTo);

        }else if(logic.top() == 3){// " or "
            logic.pop();
            boolean tmp = pn.pop();// 1st pop
            if(logic.top() == 4){// NOT
                tmp = !tmp;// 1st pop
                logic.pop();
            }
            boolean tmp2 = pn.pop();// 2nd pop compare
            //System.out.print("Compare: "+ tmp2+" || "+ tmp);
            boolean expressionEquatesTo = ( tmp || tmp2); 
            //System.out.print(" = "+ expressionEquatesTo+"\n");
            pn.push(expressionEquatesTo);
            
        }else if(logic.top() == 4){ //" not " makes last boolean expression opposite to what it is 
            boolean tmp = pn.pop();// 1st pop
            pn.push(!tmp);
            logic.pop();

        }else if(logic.top() == 5){ // " iff "
            logic.pop();
            boolean tmp = pn.pop();// 1st pop
            if(logic.top() == 4){// NOT
                tmp = !tmp;// 1st pop
                logic.pop();
            }
            boolean tmp2 = pn.pop();// 2nd pop compare
            //System.out.print("Compare: "+ tmp2+" if and only if "+ tmp);
            boolean expressionEquatesTo = false;
            if( tmp == tmp2){               //if and only if statements are true when TRUE == TRUE or  FALSE == FALSE 
                expressionEquatesTo = true;
            }
            //System.out.print(" = "+ expressionEquatesTo+"\n");
            pn.push(expressionEquatesTo);

        }else if(logic.top() == 6){ // " imply "
            logic.pop();
            boolean tmp = pn.pop();// 1st pop
            if(logic.top() == 4){// NOT
                tmp = !tmp;// 1st pop
                logic.pop();
            }
            boolean tmp2 = pn.pop();// 2nd pop compare
            //System.out.print("Compare: "+ tmp2+" implies "+ tmp);
            boolean expressionEquatesTo = !tmp2 || tmp;
            //System.out.print(" = "+ expressionEquatesTo+"\n");
            pn.push(expressionEquatesTo);
        }
    }
    System.out.println("\nExpression evaluates to "+pn.pop());
    printArr(arr);

    }

    public static int[] definePn(int[] arr){ // lets user define the Pn values as true or false
        Scanner scanner = new Scanner(System.in);
        boolean valid = false;
        int parenthesisCount = 0;
        //printArr(arr);
        for(int i = 0; i < arr.length; i++){
            if(arr[i] == 0){
                parenthesisCount++;
            }
            if(arr[i] > 10){
                int tmp = arr[i]-10;
                while(!valid){ // while input isnt "true" or "false"
                    System.out.print("please give a true or false value for P"+tmp+" \n>");
                    tmp = arr[i];
                    String input = scanner.next();
                    if(input.equalsIgnoreCase("true")){
                        valid = true; //inputs valid
                        arr[i] = 7; //7 = true
                    }else if(input.equalsIgnoreCase("false")){
                        valid = true;
                        arr[i] = 8; // 8 = false
                    }else{
                        System.out.println("Invalid Input");
                    }
                }valid = false;  //resets variable for next Pn value
                for( int j = 0; j < arr.length; j++){ // second loops turns all the values of previous pn into whateve user inputted
                    if(arr[j] == tmp){ //temp is set to previous arr[i] so Pn
                        arr[j] = arr[i];// then we switch arr[j] to new true or false value set to matching Pn
                    }
                }
            }
        }
        // solving parenthesis first
        int start = 0;//indx
        int end = 0;
        int coun = 1;//counts parenthesis
        int[][] parenthesis = new int[parenthesisCount][2];
        for(int i = 0; i < arr.length; i++){
            coun = 1;
            if(arr[i] == 0){
                start = i;//indx
                for(int j = i + 1; coun != 0;j++){
                    if( arr[j] == 0){
                        coun ++;
                    }else if( arr[j]==1){
                        coun --;
                    }
                    end = j;//indx
                    //j++;
                }
                //storing parenthesis indexes into array
                
                parenthesisCount --;
                parenthesis[parenthesisCount][0]= start;
                parenthesis[parenthesisCount][1]= end;
                //System.out.println("Index = "+ start +", "+ end);
            }
        }
        for( int x = 0; x < parenthesis.length; x++){
            System.out.println(parenthesis[x][0]+" "+parenthesis[x][1]);
            getExpression(arr, parenthesis[x][0], parenthesis[x][1]);
            //printArr(arr);
        }


        return arr;

    }
    public static void getExpression(int[] arr, int start, int end){
        PnStack pn = new PnStack(arr.length);
        CompariStack logic = new CompariStack(arr.length);

    for( int i = start ; i < end; i++){
        if(arr[i] == 0){
           logic.push(0);
        }else if(arr[i] == 1){
            logic.push(1);
        }else if(arr[i] == 2){
            logic.push(2);
        }else if(arr[i] == 3){
            logic.push(3);
        }else if(arr[i] == 4){
            logic.push(4);
        }else if(arr[i] == 5){
            logic.push(5);
        }else if(arr[i] == 6){
            logic.push(6);
        }else if(arr[i] == 7){
           pn.push(true);
        }else if(arr[i] == 8){
            pn.push(false);
        }
    }

    while(!logic.isEmpty()){
        if(logic.top() == 0){ // " ( "
            logic.pop();

        }else if(logic.top() == 1){// " ) "
            logic.pop();

        }else if(logic.top() == 2){// " and "
            logic.pop();
            boolean tmp = pn.pop();// 1st pop
            if(logic.top() == 4){// NOT
                tmp = !tmp;// 1st pop
                logic.pop();
            }
            boolean tmp2 = pn.pop();// 2nd pop compare
            //System.out.print("Compare: "+ tmp2+" && "+ tmp);
            boolean expressionEquatesTo = ( tmp && tmp2); 
            //System.out.print(" = "+ expressionEquatesTo+"\n");
            pn.push(expressionEquatesTo);

        }else if(logic.top() == 3){// " or "
            logic.pop();
            boolean tmp = pn.pop();// 1st pop
            if(logic.top() == 4){// NOT
                tmp = !tmp;// 1st pop
                logic.pop();
            }
            boolean tmp2 = pn.pop();// 2nd pop compare
            //System.out.print("Compare: "+ tmp2+" || "+ tmp);
            boolean expressionEquatesTo = ( tmp || tmp2); 
            //System.out.print(" = "+ expressionEquatesTo+"\n");
            pn.push(expressionEquatesTo);
            
        }else if(logic.top() == 4){ //" not " makes last boolean expression opposite to what it is 
            boolean tmp = pn.pop();// 1st pop
            pn.push(!tmp);
            logic.pop();

        }else if(logic.top() == 5){ // " iff "
            logic.pop();
            boolean tmp = pn.pop();// 1st pop
            if(logic.top() == 4){// NOT
                tmp = !tmp;// 1st pop
                logic.pop();
            }
            boolean tmp2 = pn.pop();// 2nd pop compare
            //System.out.print("Compare: "+ tmp2+" if and only if "+ tmp);
            boolean expressionEquatesTo = false;
            if( tmp == tmp2){               //if and only if statements are true when TRUE == TRUE or  FALSE == FALSE 
                expressionEquatesTo = true;
            }
            //System.out.print(" = "+ expressionEquatesTo+"\n");
            pn.push(expressionEquatesTo);

        }else if(logic.top() == 6){ // " imply "
            logic.pop();
            boolean tmp = pn.pop();// 1st pop
            if(logic.top() == 4){// NOT
                tmp = !tmp;// 1st pop
                logic.pop();
            }
            boolean tmp2 = pn.pop();// 2nd pop compare
            //System.out.print("Compare: "+ tmp2+" implies "+ tmp);
            boolean expressionEquatesTo = !tmp2 || tmp;
            //System.out.print(" = "+ expressionEquatesTo+"\n");
            pn.push(expressionEquatesTo);
        }
    }
    boolean tmp = pn.pop();
    if(tmp){
        arr[start] = 7;
    }else{
        arr[start] = 8;
    }
    for(int x = start + 1; x <= end; x++ ){
        arr[x] = -1;
    }
    }

    //checks if a valid expression is entered
    public static boolean isValid(int[] arr){
        int countLP = 0;//Left parenthesis 
        int countRP = 0;
        for(int i = 0 ; i < arr.length ; i++){
            int curr = arr[i];                       // if arr[i] = '(' then arr[i + 1] must have a value after it and it must be true, false or a Pn value
            if(curr == 0){                           //"(",")","and",or","not","iff","implies", "True", "False "};   P0 P1 ...Pn                                         
                if((i + 1) >= arr.length){           // 0   1    2    3    4     5      6       7       8        >=10
                    System.out.println(" can not end with left parenthesis '(' ");
                    return false;
                }else if( arr[i + 1] <= 3 && arr[i + 1] == 5 && arr[i + 1] == 6 && arr [i + 1] > 0){
                    System.out.println("Parenthesis has to be followed with a true or false value");
                    return false;
                }
                countLP++;
            }
            if( curr == 1){
                countRP++;
                if(i - 1 <= 0 ){
                    System.out.println("Can not start statement with right parenthesis ");
                    return false;
                }
                if(countRP > countLP){
                    System.out.println("Can not have more Right Parenthesis than left");
                    return false;
                }
            }
            if(curr >= 7 ){
                if((i + 1) >= arr.length){ // arr[i] is a Pn or true and false,  then the next value should be and AND, OR, ), or Nothing
                }else if(arr[i + 1] >= 1 && arr[i + 1] <= 6 ){  
                }else{
                    System.out.println("Pn, True or False values must be followed by ')','and','or' or nothing ");
                    return false;
                }
            }
            if((curr == 2 || curr == 3)){ // if arr[i] = AND, OR .. then arr[i] needs a value of NOT, TRUE, FALSE or Pn after it
                if(i + 1  == arr.length){
                    System.out.println("can not end on And or Or");
                    return false;
                }
                if(arr[i + 1] == 4 || arr[i + 1] >= 7 || arr[ i + 1] == 0){
                }else{
                    System.out.println("AND and OR statements must be followed by NOT or a TRUE Or FALSE value");
                    return false;
                }
            }

            if(curr == 5 || curr == 6){
                if( (i + 1) == arr.length){
                    System.out.print("Can not end on an IFF or IMPLIES statement");
                    return false;
                }else if(arr[i + 1] == 0 || arr[i +1 ] == 4 || arr[i + 1] >= 7){
                }else{
                    System.out.println("IFF and IMPLIES have to be followed by '(' or NOT or Pn TF value");
                    return false;
                }
                if( ( i - 1 ) >= 0){
                    if(arr[ i - 1] == 1 || arr[i - 1] >= 7 ){

                    }else{
                        System.out.println("IFF and IMPLIES have to be followed after a Pn, T F value or ')'" );
                        return false;
                    }
                }else{
                    System.out.println("Can not start statement with an IFF or IMPLIES");
                    return false;
                }
            }

        }
        if (countLP != countRP){
            System.out.println("Every parenthesis needs to be closed");
            return false;
        }
        return true;
    }
    //checks if user inputs valid expression terms 
    public static int[] validInput(String[] validInputs){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        Scanner scan = new Scanner(input);

        int i; int count = 0;
        for( i = 0; scan.hasNext();i++){
            if(checkNext(scan.next(), validInputs)){
                count++;
            }
        }if(count != i){
            System.out.println("Please enter a valid input");
            validInput(validInputs);
        }
        int[] intSentance = new int[i]; // creates int array for every word in sentance
        //       validInputs= {"(",")","and",or","not","iff","implies"};
        //                      0   1    2    3    4     5      6
        intSentance(intSentance, input, validInputs);
        return intSentance;

    }

    //checks the array valid inputs, and makes sure every input is in valid inputs
    public static boolean checkNext(String next, String[] validInputs){//and, iff, not, or implies
        char char1 = next.charAt(0);
        for(int i = 0; i < validInputs.length; i++){
            if(next.equalsIgnoreCase(validInputs[i])){
                return true;
            }else if( (char1=='P'|| char1 == 'p') && next.length() == 2){
                if(Character.isDigit( next.charAt(1))){
                return true;
                }
            }
        }
        return false;
    }


    public static int[] intSentance(int[] arr, String input, String[] validInputs){
        Scanner scan = new Scanner(input);
        for( int i = 0; i < arr.length; i++){
            arr[i] = checkValues(scan.next(),validInputs);
        }
        return arr;
    }
    //returns the values in the input sentance and numbers into an array, example 7 = true 8 = false
    public static int checkValues(String next, String[] validInput){
        char char1= next.charAt(0);
        for( int i = 0; i < validInput.length; i++){
            if(next.equalsIgnoreCase(validInput[i])){
                return i;
            }else if( (char1=='P'|| char1 == 'p') && next.length() == 2){
                if(Character.isDigit( next.charAt(1))){
                    int a = Character.getNumericValue(next.charAt(1));
                    return a+10;
                }
            }
        }
        return 0;
    }

    public static void printArr(int[] arr){
        System.out.print("\nArray = ");
        for(int i = 0; i < arr.length; i++){
            System.out.print(arr[i]+" ");
        }
    }

}