package ourGenerated;

import generated.BoardType;
import generated.CardType;
import generated.PositionType;
import generated.TreasureType;

import java.util.ArrayList;
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
	protected Position myPosition;// aktuelle Position des Spielers
	protected Position forbidden;// die Position, an die nicht gelegt werden
	// darf
	protected Position treasurePosition;// position des zu findenden

	// schatzes

	public Position myPosition() {
		return myPosition;
	}

	public void setCard(int i, int j, Card c) {
		if (i > 0 && j > 0 && i < cards.length && j < cards[i].length)
			cards[i][j] = c;
	}

	public Card[][] getCards() {
		return cards;
	}

	public Card getShiftCard() {
		return shiftCard;
	}

	protected Map<Integer, Position> spielerPositions = new TreeMap<Integer, Position>();
	public Map<Integer, Position> getSpielerPositions() {
		return spielerPositions;
	}
	public Board(BoardType b, TreasureType t, int id) {
		this.forbidden = b.getForbidden() != null ? new Position(b.getForbidden()) : null;
		this.cards = new Card[7][7];
		this.treasure = t;
		this.id = id;
		int y = 0, x = 0;
		boolean foundMe = false, foundTreasure = false;
		for (BoardType.Row row : b.getRow()) {
			x = 0;
			for (CardType c : row.getCol()) {
				cards[y][x] = new Card(c);
				Position p = new Position(x,y);
				for(int playerID: c.getPin().getPlayerID()) {
					spielerPositions.put(playerID, p);
					if(playerID == id) {
						myPosition = p;
						foundMe = true;						
					}
				}
				if (t.equals(c.getTreasure())) {
					treasurePosition = p;
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

	public Position getMyPosition() {
		return this.myPosition;
	}

	public Position getForbidden() {
		return this.forbidden;
	}

	public Position getTreasurePosition() {
		return this.treasurePosition;
	}
	
	public List<Position> getPossiblePositionsFromPosition(Position position) {
		int canVisit[] = new int[7*7];
		int canVisitSize = 0;
		int haveRevisited = 0;
		int currentIndex;
		canVisit[canVisitSize++] = position.y*7 + position.x;
		int x, y;
		boolean[] currentCardOpenings;
		boolean[] visited = new boolean[7*7];
		
		while(haveRevisited < canVisitSize) {
			currentIndex = canVisit[haveRevisited++];
			x = currentIndex % 7;
			y = currentIndex / 7;
			currentCardOpenings = cards[y][x].openings;
			
			if(y > 0 && !visited[currentIndex - 7] && currentCardOpenings[0] && cards[y - 1][x].openings[2]) { // Oben
				canVisit[canVisitSize++] = (currentIndex - 7);
				visited[currentIndex - 7]=true;
			}
			
			if(x < 7-1 && !visited[currentIndex + 1] &&currentCardOpenings[1] && cards[y][x + 1].openings[3]) { // Rechts
				canVisit[canVisitSize++] = (currentIndex + 1);
				visited[currentIndex + 1]=true;
			}
			
			if(y < 7-1 && !visited[currentIndex + 7] &&currentCardOpenings[2] && cards[y + 1][x].openings[0]) { // Unten
				canVisit[canVisitSize++] = (currentIndex + 7);
				visited[currentIndex + 7]=true;
			}
			
			if(x > 0 && !visited[currentIndex - 1] &&currentCardOpenings[3] && cards[y][x - 1].openings[1]) { // Links
				canVisit[canVisitSize++] = currentIndex - 1;
				visited[currentIndex - 1]=true;
			}
		}
		
		ArrayList<Position> list = new ArrayList<Position>(canVisitSize);
		for(int i=canVisitSize -1; i>=0; --i) {
			list.add(new Position(canVisit[i]%7, canVisit[i]/7));
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
		forbidden = b.forbidden;

		// myPosition kopieren
		myPosition = b.myPosition;

		// treasure-Position kopieren
		treasurePosition = b.treasurePosition;

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
		newBoard.myPosition = shiftPosition(myPosition, start, direction, vertikal);
		newBoard.treasurePosition = shiftPosition(treasurePosition, start, direction, vertikal);
		//TODO: shift my & treasure position

		return newBoard;
	}

	public boolean isValidMove(Position p, Card c) {
		if (!c.isSame(shiftCard)) {
			System.err.println("Shiftcard is not the same.");
			return false;
		}
		if(p.equals(forbidden)) {
			System.err.println("Forbidden Card used.");
			return false;
		}
			
		return ( (p.x%6==0 && p.y%2==1)  || (p.y%6==0 && p.x%2==1) );
	}
	
	private Position shiftPosition(Position p, int start, int direction, boolean vertical) {
		if(vertical) {
			return new Position(p.x, p.y == start ? (7 + p.y + direction)%7 : p.y);
		} else {
			return new Position(p.x == start ? (7 + p.x + direction)%7 : p.x, p.y);
		}
	}

}
