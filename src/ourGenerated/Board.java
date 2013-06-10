package ourGenerated;

import generated.BoardType;
import generated.CardType;
import generated.PositionType;
import generated.TreasureType;

import java.util.List;

public class Board {

	private Card cards[][];
	protected Card shiftCard;// die einzufuegende Karte
	protected TreasureType treasure;// der als naechstes zu erreichende Schatz
	protected int id;
	// (enthaelt auch die PlayerId)
	protected PositionType myPosition;// aktuelle Position des Spielers
	protected PositionType forbidden;// die Position, an die nicht gelegt werden
	// darf
	protected PositionType treasurePosition;// position des zu findenden
											// schatzes

	public PositionType myPosition() {
		return myPosition;
	}

	public void setCard(int i, int j, Card c) {
		if (i > 0 && j > 0 && i < cards.length && j < cards[i].length)
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

	public Board(BoardType b, TreasureType t, int id) {
		myPosition = new PositionType();
		this.treasure = t;
		this.id = id;
		int i = 0, j = 0;
		boolean foundMe = false, foundTreasure = false;
		for (BoardType.Row row : b.getRow()) {
			for (CardType c : row.getCol()) {
				cards[i][j] = new Card(c);
				if (c.getPin().getPlayerID().contains(id)) {
					myPosition.setRow(i);
					myPosition.setCol(j);
					foundMe = true;
				}
				if (c.getTreasure().equals(id)) {
					treasurePosition.setRow(i);
					treasurePosition.setCol(j);
					foundTreasure = true;
				}
				j++;
			}
			i++;
		}

		shiftCard = new Card(b.getShiftCard());
		if (!(foundMe || foundTreasure)) {
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

	public TreasureType getTreasure() {
		return this.treasure;
	}

	public void setTreasure(TreasureType t) {
		this.treasure = t;
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

	public PositionType getTreasurePosition() {
		return this.treasurePosition;
	}

	public void setTreasurePosition(PositionType treasure) {
		this.treasurePosition = treasure;
	}

	public void setCards(Card[][] cards) {
		this.cards = cards;
	}

	public Board(Board b) {
		// cards kopieren
		cards = new Card[b.cards.length][b.cards[0].length];
		for (int i = 0; i < b.cards.length; i++)
			for (int j = 0; j < b.cards[i].length; j++)
				cards[i][j] = new Card(b.cards[i][j]);
		// shiftCard kopieren
		shiftCard = new Card(b.shiftCard);

		// forbidden-Position kopieren
		forbidden = new PositionType();
		forbidden.setCol(b.forbidden.getCol());
		forbidden.setRow(b.forbidden.getRow());

		// myPosition kopieren
		myPosition = new PositionType();
		myPosition.setCol(b.myPosition.getCol());
		myPosition.setRow(b.myPosition.getRow());

		// treasure-Position kopieren
		treasurePosition = new PositionType();
		treasurePosition.setCol(b.treasurePosition.getCol());
		treasurePosition.setRow(b.treasurePosition.getRow());

		// treasure kopieren
		treasure = b.getTreasure();
		
		//ID
		id=b.id;
	}

}
