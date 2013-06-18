SeahorseInAMaze
===============

Uses [JAXB][1] and [Jansi][2].

Put the jars in `lib/`

start from Eclipse-Generated `bin/` with `java -cp '.:../lib/*' client.Main KI-Name [host = localhost] [port = 5123] [matches = 1]`

`KI-Name` is a name of a class in the `spieler`-package, that implements the [`ISpieler`-Interface][3].  
A value for `matches` will result in multiple matches being played with the same KI on the same Server.
After each match a Random-Wait time will be choosen between 1 and 5 seconds. 
Also a statistic will show how many games you won, lost or resulted in an error.  
When recompiling the Selected KI the KI will be reloaded before every match.


## Strategie

### StrategieKI
So noch nicht implementiert!

1. Sieg. - Ich hab keine Schätze, will das Heimatfeld erreichen
2. Sieg eines Gegners verhindern
3. Eigenen Schatz erreichen
    1. Danach möglich viele noch nicht vergebene Schätze erreichen können
    2. Damage!
4. Möglichst nahe an eigenen Schatz.
    1. Eigenen Bewegungsradius erhöhen
    2. Gegner Bewegungsradius vermindern

### MatthiasKI
Bewertungsreihenfolge:

1. Kann Schatz erreichen ja/nein
2. `Anzahl Felder in meinem Netzwerk/Anzahl Spieler in diesem Netzwerk` soll maximal werden.
3. Anzahl der Wände, die zwischen mir und meinem Schatz stehen soll minimal werden.
4. `Anzahl Felder die meine Gegner erreichen können/Anzahl gegner` soll minimal werden.

Bei gleichen Bewertungen wird zufällig eine Bewerung ausgesucht. Zu jeder Bewertung gehören eine oder mehrere 
gleichwertige Bewegungs Positionen. 
Diese wird auch zufällig ausgewählt.

[1]: https://jaxb.java.net/
[2]: http://jansi.fusesource.org/
[3]: src/spieler/ISpieler.java

