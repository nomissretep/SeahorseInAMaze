package spieler;

import generated.MoveMessageType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import client.types.IllegalTurnException;

import ourGenerated.Board;
import ourGenerated.Card;
import ourGenerated.Position;

public class MatthiasKI extends Spieler {

	private class Bewertung implements Comparable<Bewertung>{
		public final boolean canFindTreasure;
		
		public final int howManyWallsBlockMyWayToTreasure;

		public final int myNetworkSize;
		
		public final double averageEnemyMovability;
		
		
		public Bewertung(boolean canFindTreasure, int howManyWallsBlockMyWay, double averageEnemyMovability, int myNetworkSize) {
			this.canFindTreasure = canFindTreasure;
			this.howManyWallsBlockMyWayToTreasure = howManyWallsBlockMyWay;
			this.averageEnemyMovability = averageEnemyMovability;
			this.myNetworkSize = myNetworkSize;
		}
		
		@Override
		public int compareTo(Bewertung o) {
			if(o == null) return 1;
			if(this.canFindTreasure == o.canFindTreasure) {
				if(this.myNetworkSize == o.myNetworkSize) {
					if(this.howManyWallsBlockMyWayToTreasure == o.howManyWallsBlockMyWayToTreasure) {
						if(Math.abs(this.averageEnemyMovability - o.averageEnemyMovability) <= 0.05) {
							return 0;
						} else {
							return this.averageEnemyMovability > o.averageEnemyMovability ? 1 : -1;
						}
					}  else {
						return this.howManyWallsBlockMyWayToTreasure > o.howManyWallsBlockMyWayToTreasure ? 1 : -1;
					}
				} else {
					return this.myNetworkSize > o.myNetworkSize ? 1 : -1;
					 
				}
			} else {
				return this.canFindTreasure ? 1 : -1;
			}
		}
		public String toString() {
			return String.format("Treasure: %d\nNetworkSize: %d\nWalls: %d\nMovability: %f\n", canFindTreasure?1:0, myNetworkSize, howManyWallsBlockMyWayToTreasure, averageEnemyMovability);
		}
	}
	
	private class Zug {
		public final Position shitPosition;
		public final int cardRotation;
		public final List<Position> movePositions;
		public Zug(Position shiftPosition, int cardRotation, List<Position> movePositions) {
			this.shitPosition= shiftPosition;
			this.cardRotation=cardRotation;
			this.movePositions=movePositions;
		}
	}
	//List<Position> currentMaxMovePositions = new ArrayList<Position>();
	List<Zug> currentMaxZuege = new LinkedList<Zug>();
	//int currentMaxRotationCount, currentMaxX, currentMaxY;
	//Position currentMaxShiftPosition;
	Bewertung currentMaxBewertung;
	
	Random rand = new Random();
	@Override
	public MoveMessageType doTurn(Board bt,
			Map<Integer, Integer> idHasNTreasuresleft) {
		currentMaxBewertung = null;
		Card c = bt.getShiftCard();
		for(int rotationCount = 0; rotationCount<4; ++rotationCount) {
			for(int x = 5; x >= 0; x-=2) {
				versuche(bt, x, 0, c, rotationCount);
				versuche(bt, x, 6, c, rotationCount);
			}
			for(int y = 5; y >= 0; y-=2) {
				versuche(bt, 0, y, c, rotationCount);
				versuche(bt, 6, y, c, rotationCount);
			}
			c.turnCounterClockwise(1);
		}
		
		Zug z = currentMaxZuege.get(currentMaxZuege.size() == 1 ? 0 : rand.nextInt(currentMaxZuege.size()));
		c.turnCounterClockwise(z.cardRotation);
		MoveMessageType move = new MoveMessageType();
		move.setShiftPosition(z.shitPosition.getPositionType());
		move.setShiftCard(c.getCardType());
		move.setNewPinPos(z.movePositions.get(z.movePositions.size() ==1? 0 : rand.nextInt(z.movePositions.size())).getPositionType());
		System.out.println(currentMaxBewertung);
		return move;
	}

	private void versuche(Board bt, int x, int y, Card c, int rotationCount) {
		Position shiftPosition = new Position(x, y);
		Board shiftetBoard;
		try {
			shiftetBoard = bt.shift(shiftPosition, c);
		} catch (IllegalTurnException e) {
			return;
		}
		Position myPos = shiftetBoard.myPosition();
		Position treasurePos = shiftetBoard.getTreasurePosition();
		List<Position> whereCanIGo = shiftetBoard.getPossiblePositionsFromPosition(myPos);
		int[][] walls;
		
		boolean canFindTreasure = treasurePos !=null && whereCanIGo.contains(treasurePos);

		if(!canFindTreasure && treasurePos != null) {
			 walls = shiftetBoard.howManyWallsBlockMyWayTo(treasurePos);
			 for(int i = 5; i >= 0; i -= 2) {
				 walls[0][i] = walls[6][i] = Math.min(walls[0][i], walls[6][i]);
				 walls[i][0] = walls[i][6] = Math.min(walls[i][0], walls[i][6]);
			 }
		} else {
			walls = new int[7][7];
		}
		
		
		List<Position> movePositions = new LinkedList<Position>();
		int howManyWallsBlockMyWayToTreasure = 0;

		if(!canFindTreasure) {
			int minWalls = Integer.MAX_VALUE;
			for(Position pos: whereCanIGo) {
				if(walls[pos.y][pos.x] < minWalls) {
					minWalls = walls[pos.y][pos.x];
					movePositions.clear();
					movePositions.add(pos);
				} else if(walls[pos.y][pos.x] == minWalls) {
					movePositions.add(pos);
				}
			}
			howManyWallsBlockMyWayToTreasure = minWalls;
		} else {
			movePositions.add(treasurePos);
		}
		
		int myNetworkSize = whereCanIGo.size();
		
		int enemysCanMoveTiles = 0;
		for(Position pos: shiftetBoard.getSpielerPositions().values()) {
			enemysCanMoveTiles += shiftetBoard.getPossiblePositionsFromPosition(pos).size();
		}
		double averageEnemyMovability = ((double)enemysCanMoveTiles)/shiftetBoard.getSpielerPositions().size();
		
		Bewertung b = new Bewertung(canFindTreasure, howManyWallsBlockMyWayToTreasure, averageEnemyMovability, myNetworkSize);
		if(b.compareTo(currentMaxBewertung) > 0) {
			System.out.println(b);
			currentMaxZuege.clear();
		}
		if(b.compareTo(currentMaxBewertung) >= 0) {
			System.out.println("+1");
//			shiftetBoard.outputPretty();
//			System.out.println("Walls: ");
//			for(int y_=0; y_ < 7; y_++) {
//				for(int x_ = 0; x_ < 7; x_++) {
//					System.out.format("%4d",walls[y_][x_]);
//				}
//				System.out.println();
//			}
			currentMaxBewertung = b;
			currentMaxZuege.add(new Zug(shiftPosition, rotationCount, movePositions));
		}
	}

	@Override
	public String getName() {
		return "Nasses Seepferdchen";
	}

}
