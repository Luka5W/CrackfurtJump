# CrackfurtJump

_ein pädagogisch vollkommen wertvolles Spiel über Plattformen, Power-Ups,
tödliche Drogen und Monster._


## Konzept

Ziel ist es, mithilfe der Plattformen immer weiter nach oben zu springen und
einen hohen Score zu erzielen.


### Steuerung

Gesteuert wird mit den Pfeiltasten nach rechts und links, um die Spielfigur in
die jeweilige Richtung zu bewegen. Zusätzlich wird die Leertaste verwendet, um
bestimmte gesammelte Items zu aktivieren oder die Runde neu zu starten, wenn sie
zu Ende ist.


## Features

### Plattformen

Am Anfang des Spiels werden 10 Plattformen zufällig Spielbereich verteilt. Diese
werden, wenn sie sich außerhalb des Spielbereichs bewegt haben, recycled. Jede
Plattform hat einen Slot für ein Item oder ein Monster.


### Monster

Mit einer Chance von 25 % spawnt in einem Slot einer gerade recycleten Plattform
ein Monster mit einer zufälligen Farbe; ein Bewohner der fiktiven Stadt
Crackfurt am Main.

Eine Berührung mit einem Monster macht die Spielfigur aufgrund dessen
penetranten Junkie-Geruchs besinnungslos. Das Spiel ist damit beendet.

Junkies können entweder mit speziellen Power-Ups oder durch einen Sprung auf
ihren Kopf und dem anschließenden Genickbruch getötet werden.


### Items

Auch können Items in Plattform-Slots spawnen. Hier beträgt die Chance ebenfalls
25 %. Dabei hat jedes Item die gleiche Spawn chance. Items sind:

| Name    | Funktion          | Beschreibung                                                                                                                                                   |
|:--------|:------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Bazooka | Aktivierbar       | Eine Waffe, mit der Monster abgeschossen werden können, die sich mindestens 100 Pixel (1 Meter) von der Spielfigur weit entfernt und oberhalb dieser befinden. |
| Coin    | Sammeln           | Eine Münze ohne Sinn oder Funktion, die schlicht gesammelt werden kann.                                                                                        |
| Heroin  | Automatisch (10s) | Eine Droge, dessen Wirkung noch nicht implementiert ist.                                                                                                       |
| Jetpack | Automatisch (10s) | Ein Power-Up, mithilfe dessen man für 10 Sekunden nach oben fliegt.                                                                                            |
| LSD     | Automatisch (10s) | Eine Droge, dessen Wirkung noch nicht implementiert ist.                                                                                                       |
| SHIELD  | Automatisch (10s) | Ein Power-Up, mithilfe dessen man für 10 Sekunden nicht durch Monster sterben kann.                                                                            |
| WEED    | Automatisch (10s) | Eine Droge, dessen Wirkung noch nicht implementiert ist.                                                                                                       |

Noch sind die Drogen nicht implementiert. Angedacht sind, dass sie das Sichtfeld
oder die Mobilität der Spielfigur beeinträchtigen und bei einer Überdosis (einer
zu langen Wirkdauer) das Spiel beendet ist.


### High-Scores

Beim Spielstart wird (wenn möglich) im Arbeitsverzeichnis eine Datei
(`highscores.dat`) angelegt. In ihr werden die erreichten High-Scores
zeilensepariert gespeichert. Daraus kann im Game-Over-Screen der High-Score
selbst und ein durchschnittlicher Score errechnet werden. Der aktuelle Score
wird im Spiel oben links in Metern angezeigt.


### Kills

Auch wird gezählt, wenn Monster (Junkies) getötet werden. Der Zähler ist dabei
oben rechts im Spiel.


### Inventar

Unten links ist das Inventar der Spielfigur. Es werden nur Items angezeigt, die
entweder noch genutzt werden oder noch Nutzungen verbleiben. Auch die Anzahl der
Nutzungen bzw. die verbleibende Nutzungsdauer wird angezeigt.

### Scrollgeschwindigkeit der Plattformen

Die Scrollgeschwindigkeit ist abhängig von der relativen (d.h. die Position im
sichtbaren Bereich) und der absoluten Position (d.h. dem erreichten Score/ Höhe)
der Spielfigur.


## Bugs &amp; Probleme

- Ich habe mir sehr schwer damit getan, eine gute Scrollgeschwindigkeit für die
  Plattformen zu finden.
- Auch die Generierung der Plattformen lässt zu wünschen übrig, da sie 
  theoretisch nicht schaffbare Sprünge erfordern kann.
- Wie der Sprung von Herrn Panitz' `FallingImage` funktioniert, verstehe ich
  immer noch nicht.


## Besonderheiten

- Es werden keine Bilddateien verwendet. Alle Grafiken werden mithilfe von
  geometrischen Formen (z.B. `java.awt.Shape`) erstellt.
- Es gibt einen Debug Modus, der die Hit-Boxen der Objekte anzeigt und mit den
  Tasten `E` (Enemy) und `I` (Item) das spawnen von neuen Gegnern bzw. Items
  enforced. Erreichbar ist er, indem dem Programm beim Start das Argument
  `--dev` übergeben wird.
- Ich war zu faul, mich großartig in Swing einzulesen und habe eine recht
  komplexe Klasse (`com.luka5w.swinggame.gui.SwingUtils`) erstellt, mit deren
  Hilfe ich unter anderem Text relativ (rechts-/ linksbündig und zentriert
  &ndash; jeweils vertikal und horizontal) zu Koordinaten malen kann. Im
  Nachhinein habe ich erfahren, dass es so etwas wie Label gibt.
- Die verwendeten Tasten sind in einer separaten Klasse
  (`com.luka5w.crackfurtjump.data.KeyBindings`) definiert. So können Änderungen
  schnell vorgenommen oder eine mögliche Konfigurationsdatei schnell
  implementiert werden.
- Die verwendeten Farben, Schriftarten und -größen sind (aus gleichem Grund) in
  einer separaten Klasse (`com.luka5w.crackfurtjump.data.Style`) definiert.
- Der Code ist ausreichend mit JavaDoc Kommentaren dokumentiert.
- Es wurde `org.jetbrains.annotations.Nullable` verwendet. Ich hoffe, das ist in
  Eclipse kein Problem.

## Quellen

- Es wurden keine Bildquellen direkt verwendet. Inspiration für die Monster war 
  das [Bowser-Gesicht](https://duckduckgo.com/?q=bowser+icon) von Supermario/
  Nintendo.
- Inspiration: Doodle Jump


## Erklärung

Ich habe dieses Spiel selbstständig und nur mit den in [Quellen](#Quellen)
angegebenen Hilfen erstellt.<br/>
<br/>
<br/>


---

05.03.2023, Lukas Wolf