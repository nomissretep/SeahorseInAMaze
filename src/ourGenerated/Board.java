package ourGenerated;

import generated.BoardType;
import generated.CardType;
import generated.TreasureType;

import java.util.List;

public class Board {
	
	private Card cards[][];
	protected Card shiftCard;
	protected TreasureType treasure;
	
	public void setCard(int i, int j, Card c){
		cards[i][j]=c;
	}
	
	public Card[][] getCards(){
		return cards;
	}
	
	public void setShiftCard(Card c){
		shiftCard=c;
	}
	
	public Card getShiftCard(){
		return shiftCard;
	}
	
	public void setTreasure(TreasureType t){
		treasure = t;
	}
	
	public TreasureType getTreasureType(){
		return treasure;
	}
	
	
	public Board(BoardType b){
		treasure = b.getTreasure();
		int i=0,j=0;
		for(BoardType.Row row:b.getRow()){
			for(CardType c:row.getCol()){
				cards[i][j]=new Card(c);
				j++;
			}
			i++;
		}
		shiftCard = new Card(b.getShiftCard());
		
	}
	
	public BoardType getBoardType(){
		BoardType b = new BoardType();
		b.setShiftCard(shiftCard.getCardType());
		b.setTreasure(treasure);
		List<BoardType.Row> rows=b.getRow();//erzeugt das Row-Objekt...
		for (int i=0;i<cards.length;i++){
			for (int j=0; j<cards[i].length;j++){
				rows.get(i).getCol().add(cards[i][j].getCardType());
			}
		}
		
		return b;
	}

}
