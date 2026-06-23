package utils;

public class BlackjackCard {
    private String numberCard;
    private String symbolCard;
    private int value;
    public BlackjackCard(String numberCard, String symbolCard, int value){
        this.numberCard=numberCard;
        this.symbolCard=symbolCard;
        this.value=value;
    }
    public String getnumberCard(){
        return numberCard;
    }
    public String getsymbolCard(){
        return symbolCard;
    }
    public int getvalue(){
        return value;
    }
    public String getDisplay(){
        return numberCard + symbolCard;
    }
    
}
