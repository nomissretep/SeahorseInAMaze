package ourGenerated;

import generated.BoardType;
import generated.CardType;
import generated.PositionType;
import generated.TreasureType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import client.types.IllegalTurnException;

public class Board {

	private Card cards[][];
	protected Card shiftCard;// die einzufuegende Karte
	protected TreasureType treasure;// der als naechstes zu erreichende Schatz
	protected int id;
	// (enthaelt auch die PlayerId)
	protected PositionType myPosition;// aktuelle Position des Spielers
	protected Position forbidden;// die Position, an die nicht gelegt werden
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

	protected Map<Integer, Position> spielerPositions = new TreeMap<Integer, Position>();
	public Map<Integer, Position> getSpielerPositions() {
		return spielerPositions;
	}
	public Board(BoardType b, TreasureType t, int id) {
		myPosition = new PositionType();
		this.treasure = t;
		this.id = id;
		int y = 0, x = 0;
		boolean foundMe = false, foundTreasure = false;
		for (BoardType.Row row : b.getRow()) {
			for (CardType c : row.getCol()) {
				cards[y][x] = new Card(c);
				Position p = new Position(x,y);
				for(int playerID: c.getPin().getPlayerID()) {
					spielerPositions.put(playerID, p);
					if(playerID == id) {
						myPosition.setRow(y);
						myPosition.setCol(x);
						foundMe = true;						
					}
				}
				if (c.getTreasure().equals(t)) {
					treasurePosition.setRow(y);
					treasurePosition.setCol(x);
					foundTreasure = true;
				}
				// die shiftcard ueberpruefen TODO
				x++;
			}
			y++;
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

	public Position getForbidden() {
		return this.forbidden;
	}

	public void setForbidden(Position forbidden) {
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
	
	public List<Position> getPossiblePositionsFromPosition(PositionType pos) {
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
		
		ArrayList<Position> list = new ArrayList<Position>(canVisitSize);
		for(int i=canVisitSize - 1; i>=0; i--) {
			list.set(i, new Position(canVisit[i]%7, canVisit[i]/7));
		}
		return list;
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
		forbidden = new Position(b.forbidden.x, b.forbidden.y);

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

		// ID
		id = b.id;
	}

	/**
	 * 
	 * @param p
	 *            Die Position, an der eingefuegt wird
	 * @param c
	 *            die Karte, die eingefuegt wird
	 * @return
	 */
	public Board shift(Position p, Card c) throws IllegalTurnException {
		if (!isValidMove(p, c))
			throw new IllegalTurnException(
					"Es wurde kein gueltiger Zuge gefunden");
		Board newBoard = new Board(this);
		Card tmp=null;
		int start = 0, direction = 0;
		boolean vertikal = false;
		if (p.x == 0) {// Karte wird oben eingefuegt
			tmp = newBoard.cards[6][p.y];// die unterste Karte der Spalte
			start = 0;
			direction = 1;
			vertikal = true;
		} else if (p.x == 6) {// Karte wird unten eingefuegt
			tmp = newBoard.cards[0][p.y];// die oberste Karte der Spalte
			start = 6;
			direction = -1;
			vertikal = true;
		} else if (p.y == 0) {// karte wird links eingefuegt
			tmp = newBoard.cards[p.x][6];// die letzte Karte der Spalte
			start = 0;
			direction = 1;
			vertikal = false;
		} else if (p.y == 6) {
			tmp = newBoard.cards[p.x][0];
			start = 6;
			direction = -1;
			vertikal = false;
		}
		
		for (int i = start; i <= 6 && i>=0 && i+direction<=6 && i+direction>=0; i += direction) {
			if (vertikal) {
				newBoard.cards[i][p.y]=newBoard.cards[i+direction][p.y];
			} else {
				newBoard.cards[p.x][i]=newBoard.cards[p.x][i+direction];
			}
		}
		newBoard.cards[p.x][p.y]=new Card(shiftCard);
		newBoard.shiftCard=tmp;
		
		for(int spieler: spielerPositions.keySet()) {
			newBoard.spielerPositions.put(spieler, shiftPosition(spielerPositions.get(spieler), start, direction, vertikal));
		}
		//TODO: shift my & treasure position

		return newBoard;
	}

	public boolean isValidMove(Position p, Card c) {
		if (!c.isSame(shiftCard) || p.equals(forbidden))
			return false;
		boolean valid = false;
		for (int i = 0; i < getCards().length; i += 6) {
			for (int j = 1; j < getCards()[i].length; j += 2) {
				if (p.equals(new Position(i, j))
						|| p.equals(new Position(j, i))) {
					valid = true;
				}
			}
		}
		return valid;
	}
	
	private Position shiftPosition(Position p, int start, int direction, boolean vertical) {
		if(vertical) {
			return new Position(p.x, p.y == start ? (7 + p.y + direction)%7 : p.y);
		} else {
			return new Position(p.x == start ? (7 + p.x + direction)%7 : p.x, p.y);
		}
	}

}
