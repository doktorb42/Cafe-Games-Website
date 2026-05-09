package com.example;

public class RouletteMethod {
    public static int roulette(String choice, int number){
        int winMultiplier=-1;
        int randomNumber=(int)(Math.random()*37);
        number=0;
        if(choice.equals("black")){
            if(isBlank(randomNumber)){
                winMultiplier=2;
            }
        }
        if(choice.equals("red")){
            if(isRed(randomNumber)){
                winMultiplier=2;
            }
        }
        if(choice.equals("even")){
            if(randomNumber%2==0){
                winMultiplier=2;
            }

        }
        if(choice.equals("odd")){
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
    private static boolean isBlank(int number){
        return !isRed(number);
    }
}
