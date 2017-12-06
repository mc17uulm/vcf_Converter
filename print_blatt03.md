# Blatt 04

### Aufgabe 1

1. Neue Klasse Window
	- `implements MouseListener`
	- Eclipse Fehlerhandling: 
		- import
		- Auto-generated Methods
2. Variablen:

```java
private ImageArray image;
private boolean clicked = false;
private Random random = new Random();
private Long start, end;
private File file;
private FileWriter writer;
private int i = 1, size, distance;
```

3. Konstruktor (Fenster soll 700x700 Pixel groß sein):

```java
public Window() {
		
	this.image = new ImageArray(700, 700);
	this.file = new File("fitts_results.txt");
    
	this.image.fillRect(0, 0, 700, 700, Color.white);
	this.image.addMouseListener(this);
	this.image.show();
    this.start();
		
}
```

4. Start-Funktion (Zeigt Startbutton an und setzt clicked auf false). Startbutton (`40x40px`) unten in der Mitte: 

```java
public void start() {
	this.image.fillRect(329, 659, 40, 40, Color.blue);
	this.clicked = false;
	this.image.displayUpdate();
}
```

5. Erstelle die Funktionen um Abstand D, sowie Höhe S zufällig aus `{200, 400, 600}` und `{20, 40, 80}` auszuwählen:

```java
public void getRandomDistance() {
	this.distance = (this.random.nextInt(3) + 1)*200;
}
	
public void getRandomSize() {
	int tmp = (random.nextInt(4) + 1) * 20;
	if(tmp == 60) {
		this.getRandomSize();
	}
	this.size = tmp;
}
```

6. Implementiere Methode `switchBtn()`:

```java
public void switchBtn() {
	this.image.fillRect(329, 659, 40, 40, Color.gray);
	this.clicked = true;
	this.image.displayUpdate();
}
```

7. Methoden implementieren um Balken anzuzeigen und zu entfernen:

```java
public void showBar() {
	this.getRandomDistance();
	this.getRandomSize();
	this.image.fillRect(0, 680 - (this.distance + (this.size / 2)), 700, this.size, Color.blue);
	this.image.displayUpdate();
}
	
public void hideBar() {
	this.image.fillRect(0,  680 - (this.distance + (this.size / 2)), 700, this.size, Color.white);
	this.image.displayUpdate();
}
```

8. Mouse-Click Event implementieren:

```java
@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
		
	// Prüfen ob ich den Start-/Stopbutton geclicked habe
	if(!this.clicked) {
			
		if((e.getY() >= 659) && (e.getY() <= 700) && (e.getX() >= 330) && (e.getX() <= 370)) {
			this.showBar();
			this.switchBtn();
			this.start = System.currentTimeMillis();
		}			
	} else {
			
		if((e.getY() >= (680 - (this.distance + (this.size / 2)))) && (e.getY() <= (680 - (this.distance - (this.size / 2))))) {
				
			this.hideBar();
			this.start();
			this.end = System.currentTimeMillis();
			this.writeTimeToFile();
		}		
	}		
}
```

9. Implementiere `writeTimeToFile()`:

```java
public void writeTimeToFile() {
		
	long time = this.end - this.start;
	String out = i + " " + this.size + " " + this.distance + " " + time;
	
	try {
		
		this.writer = new FileWriter(this.file, true);
		this.writer.write(out);
		this.writer.write(System.getProperty("line.seperator"));
		this.writer.flush();
		this.writer.close();
			
	} catch (Exception e) {
		e.printStackTrace();
	}
		
	i++;
	System.out.println(out);
		
}
```

10. Initialisiere Klasse in Main-Methode:

```java
public static void main(String[] args) {
		
	Window window = new Window();
		
}
```

### Aufgabe 2:

1. Excel öffnen
2. Leere Arbeitsmappe erstellen
3. `Daten` => `Aus Text/CSV` => Datei auswählen => `Laden`
4. Neue Zeile `Id` anlegen
5. Formel (`Id = log2(D/S + 1)`) eingeben: `=LOG(([@D]/[@S])+1; 2)`
6. `Id` und `MT` auswählen und mit `Einfügen` => `Diagramme/Punktdiagramme` Diagramm erstellen
7. Mit Rechtsklick auf Datenpunkt Trennlinie hinzufügen
8. Steigung
