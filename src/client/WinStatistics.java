package client;

import java.io.PrintStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import generated.WinMessageType;
import generated.WinMessageType.Winner;

public class WinStatistics {
	private static int totalGames = 0;
	private static int iWonCount = 0;
	private static int iLostCount = 0;
	private static HashMap<Integer, Integer> iLostOn= new HashMap<Integer, Integer>();
	private static HashMap<Integer, Integer> iWonOn= new HashMap<Integer, Integer>();
	private static HashMap<String, Integer> playerNameHasWon= new HashMap<String, Integer>();
	private static HashMap<Integer, Integer> playerNumberhasWon= new HashMap<Integer, Integer>();
	public static void addStatistic(WinMessageType wmt, int myId) {
		totalGames += 1;
		Winner w = wmt.getWinner();
		addOneTo(playerNameHasWon, w.getValue());
		addOneTo(playerNumberhasWon, w.getId());
		if(myId == w.getId()) {
			//I won
			iWonCount+=1;
			addOneTo(iWonOn, myId);
			get(iLostOn, myId);
		} else {
			iLostCount+=1;
			addOneTo(iLostOn, myId);
			get(iWonOn, myId);
			get(playerNumberhasWon, myId);
		}
	}
	private static <KeyType>   void addOneTo(Map<KeyType, Integer> m, KeyType k) { 
		if(m.containsKey(k)) {
			m.put(k, m.get(k) + 1);
		} else {
			m.put(k, 1);
		}
	}
	private static <KeyType> int get(Map<KeyType, Integer> m, KeyType k) {
		if(m.containsKey(k)) {
			return m.get(k);
		} else {
			m.put(k, 0);
			return 0;
		}
	}
	public static void printStatistic() {
		printStatistic(System.err);
	}

	public static void printStatistic(PrintStream out) {
		int maxWins = Collections.max(playerNameHasWon.values());
		int maxWinLength = Math.max((int)Math.log10(maxWins) + 1, 4);
		out.format("%"+maxWinLength+"s | %s\n", "wins", "Name");
		for(String s: playerNameHasWon.keySet()) {
			out.format("%"+maxWinLength+"d | %s\n", get(playerNameHasWon,s), s);
		}
		out.println();
		
		int totalGamesSlotLength = Math.max((int)Math.log10(totalGames) + 1, 4);
		int totalMyWinsLength = Math.max((int)Math.log10(iWonCount) + 1, 7);
		int totalMyLossLength = Math.max((int)Math.log10(iLostCount) + 1, 8);

		out.format("%6s | %"+totalGamesSlotLength+"s | %"+totalMyWinsLength + "s | %"+totalMyLossLength +"s\n",
				"slot", "wins", "my wins", "my losts");
		for(int i: playerNumberhasWon.keySet()) {
			out.format("%6d | %"+totalGamesSlotLength+"d | %"+totalMyWinsLength + "d | %"+totalMyLossLength +"d\n", i, get(playerNumberhasWon,i), get(iWonOn,i),get(iLostOn,i));
		}
		out.format("%6s | %"+totalGamesSlotLength+"s | %"+totalMyWinsLength + "s | %"+totalMyLossLength +"s\n", "", "", "", "");
		out.format("%6s | %"+totalGamesSlotLength+"d | %"+totalMyWinsLength + "d | %"+totalMyLossLength +"d\n", "Total:", totalGames, iWonCount, iLostCount);
		
	}
}
