package ourGenerated;

import generated.BoardType;
import generated.CardType;
import generated.PositionType;
import generated.TreasuresToGoType;

import java.util.LinkedList;
import java.util.List;

public class Board {

	private Card cards[][];
	protected Card shiftCard;// die einzufuegende Karte
	protected TreasuresToGoType ttg;// der als naechstes zu erreichende Schatz
									// (enthaelt auch die PlayerId)
	protected PositionType myPosition;//aktuelle Position des Spielers
	protected PositionType forbidden;//die Position, an die nicht gelegt werden darf
	protected PositionType treasure;//position des zu findenden schatzes

	public PositionType myPosition() {
		return myPosition;
	}

	public void setCard(int i, int j, Card c) {
		if(i>0 && j>0 && i<cards.length && j<cards[i].length)
			cards[i][j] = c;
	}

	public Card[][] getCards() {
		return cards;
	}

	public void setShiftCard(Card c) {
		shiftCard = c;
	}

	public Card getShiftCard() {
		return shiftCard;
	}

	public Board(BoardType b, TreasuresToGoType ttg) {
		myPosition = new PositionType();
		this.ttg = ttg;
		int i = 0, j = 0;
		boolean foundMe=false, foundTreasure=false;
		for (BoardType.Row row : b.getRow()) {
			for (CardType c : row.getCol()) {
				cards[i][j] = new Card(c);
				if (c.getPin().getPlayerID().contains(ttg.getPlayer())) {
					myPosition.setRow(i);
					myPosition.setCol(j);
					foundMe=true;
				}
				if (c.getTreasure().equals(ttg.getTreasures())) {
					treasure.setRow(i);
					treasure.setCol(j);
					foundTreasure=true;
				}
				j++;
			}
			i++;
		}
		
		shiftCard = new Card(b.getShiftCard());
		if(!(foundMe||foundTreasure)){
			System.out.println("Ungueltiges Brett");
		}

	}

	public BoardType getBoardType() {
		BoardType b = new BoardType();
		b.setShiftCard(shiftCard.getCardType());
		List<BoardType.Row> rows = b.getRow();// erzeugt das Row-Objekt...
		for (int i = 0; i < cards.length; i++) {
			for (int j = 0; j < cards[i].length; j++) {
				rows.get(i).getCol().add(cards[i][j].getCardType());
			}
		}

		return b;
	}

	public TreasuresToGoType getTtg() {
		return this.ttg;
	}

	public void setTtg(TreasuresToGoType ttg) {
		this.ttg = ttg;
	}

	public PositionType getMyPosition() {
		return this.myPosition;
	}

	public void setMyPosition(PositionType myPosition) {
		this.myPosition = myPosition;
	}

	public PositionType getForbidden() {
		return this.forbidden;
	}

	public void setForbidden(PositionType forbidden) {
		this.forbidden = forbidden;
	}

	public PositionType getTreasure() {
		return this.treasure;
	}

	public void setTreasure(PositionType treasure) {
		this.treasure = treasure;
	}

	public void setCards(Card[][] cards) {
		this.cards = cards;
	}
	
	public class Pathfinding {
		public List<PositionType> getPossiblePositionsFromPosition(PositionType pos) {
			int canVisit[] = new int[7*7];
			int canVisitSize = 0;
			int haveRevisited = 0;
			int currentIndex;
			canVisit[canVisitSize++] = pos.getRow()*7 + pos.getCol();
			int x, y;
			boolean[] currentCardOpenings;
			
			while(haveRevisited < canVisitSize) {
				currentIndex = canVisit[haveRevisited++];
				x = currentIndex % 7;
				y = currentIndex / 7;
				currentCardOpenings = cards[y][x].openings;
				
				if(y > 0 && currentCardOpenings[0] && cards[y - 1][x].openings[2]) { // Oben
					canVisit[canVisitSize++] = (currentIndex - 7);
				}
				
				if(x < 7-1 && currentCardOpenings[1] && cards[y][x + 1].openings[3]) { // Rechts
					canVisit[canVisitSize++] = (currentIndex + 1);
				}
				
				if(y < 7-1 && currentCardOpenings[2] && cards[y + 1][x].openings[0]) { // Unten
					canVisit[canVisitSize++] = (currentIndex + 7);
				}
				
				if(x > 0 && currentCardOpenings[3] && cards[y][x - 1].openings[1]) { // Links
					canVisit[canVisitSize++] = currentIndex - 1;
				}
			}
			
			
		}
	}

}
