package client;

import java.util.HashMap;
import java.util.Map;

import generated.WinMessageType;
import generated.WinMessageType.Winner;

public class WinStatistics {
	private static HashMap<String, Integer> playerNameHasWon= new HashMap<String, Integer>();
	private static HashMap<Integer, Integer> playerNumberhasWon= new HashMap<Integer, Integer>();
	public static void addStatistic(WinMessageType wmt) {
		Winner w = wmt.getWinner();
		addOneTo(playerNameHasWon, w.getValue());
		addOneTo(playerNumberhasWon, w.getId());
	}
	private static <KeyType>   void addOneTo(Map<KeyType, Integer> m, KeyType k) { 
		if(m.containsKey(k)) {
			m.put(k, m.get(k) + 1);
		} else {
			m.put(k, 1);
		}
	}
	public static void printStatistic() {
		System.out.println("-------");
		for(String s: playerNameHasWon.keySet()) {
			System.out.format("%5d %s\n", playerNameHasWon.get(s), s);
		}
		for(int i: playerNumberhasWon.keySet()) {
			System.out.format("%5d wins on PlayerSlot %d\n", playerNumberhasWon.get(i), i);
		}
		System.out.println();
	}
}
