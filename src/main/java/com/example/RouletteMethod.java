package com.example;

public class RouletteMethod {
    public static int roulette(String choice, int number, int randomnum){
        int winMultiplier = 0;
        int randomNumber=randomnum;
        if(choice.equals("black")){
            if(isBlack(randomNumber)){
                winMultiplier=2;
            }
        }
        if(choice.equals("red")){
            if(isRed(randomNumber)){
                winMultiplier=2;
            }
        }
        if(randomNumber != 0 && choice.equals("even")){
            if(randomNumber%2==0){
                winMultiplier=2;
            }

        }
        if(randomNumber != 0 && choice.equals("odd")){
            if(randomNumber%2==1){
                winMultiplier=2;
            }
        }
        if(choice.equals("number")){
            if(randomNumber==number){
                winMultiplier=35;
            }
        }
        
        return winMultiplier;
    }
    private static boolean isRed(int number){
        int[] red={1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36};
        for(int n : red){
            if(number == n){
                return true;
            }
        }
        return false;
    }
    private static boolean isBlack(int number){
        return number != 0 && !isRed(number);
    }
}
