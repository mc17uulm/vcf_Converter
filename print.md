## GIS Blatt 02:

1. Neues Projekt "Blatt 2" ->Package "de.uulm.gis"
2. Class `main.java` erstellen. Methode `main` erstellen
3. Ordner "lib" erstellen. `.jar` hinein verschieben.
4. Rechtsklick -> Properties -> Java Build Path -> Libraries -> Add JAR -> Apply & Close
5. Testen:

```java
try {
	ImageArray image = new ImageArray("");
} catch(IOException e) {
	System.err.println(e.getMessage());
}
```


6. Werten einlesen:

```java

System.out.println("Bitte geben Sie die Anzahl an Farbwerten, Sättigung und Helligkeit ein");
		
BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
try {
	System.out.print("Anzahl an Farbwerten: ");
	int hue = Integer.parseInt(in.readLine());
	System.out.print("Anzahl an Sättigungen: ");
	int saturation = Integer.parseInt(in.readLine());
	System.out.println("Anzahl an Helligkeitswerten: ");
	int value = Integer.parseInt(in.readLine());
			
			
} catch(IOException e) {
	e.printStackTrace();
} catch(NumberFormatException e) {
	e.printStackTrace();
}
```

7. `HSVtoRGB()` implementieren (auf Wikipedia schauen):
	Da RGB e [0,1] * 255 um RGB Wert zu bekommen.

```java
private static Color HSVtoRGB(float hue, float saturation, float value) {
		
	double H = Math.floor(hue / 60.0f);
	double F = hue/60.0f - H;
	double p = value * (1- saturation);
	double q = value * (1- saturation * F);
	double t = value * (1- saturation * (1 - F));
	float r = 0;
	float g = 0;
	float b = 0;
		
	if (H == 0 || H == 6) {
		r = value;
		g = (float) t;
		b = (float) p;
	} else if (H == 1) {
		r = (float) q;
		g = value;
		b = (float) p;
	} else if (H == 2) {
		r = (float) p;
		g = value;
		b = (float) t;
	} else if (H == 3) {
		r = (float) p;
		g = (float) q;
		b = value;
	} else if (H == 4) {
		r = (float) t;
		g = (float) p;
		b = value;
	} else if (H == 5) {
		r = value;
		g = (float) p;
		b = (float) q;
	} else {
		
	}
	return new Color(Math.round((r) * 255.0f), Math.round((g) * 255.0f), Math.round((b) * 255.0f));
}
```

8. `getColors()` Funktion implementieren:

```java
private static void getColors(int h, int s, int v) {
		
	Color[] colors = new Color[h*s*v];
    
    int index = 0;
		
	for(int k = 0; k < v; k++) {
		for(int j = 0; j < s; j++) {
			for(int i = 0; i < v; i++) {
					
				float hue = 360.0f / (h) * i;
				float sat = 1.0f / (s) * (j + 1);
				float val = 1.0f / (v) * (k + 1);
					
                // Funktion implementieren
				colors[index] = HSV2RGB(hue, sat, val);
					
				System.out.println(
                	"Farbe " + c + 
					": r=" + colors[index].getRed() + 
					", g=" + colors[index].getGreen() + 
					", b=" + colors[index].getBlue()
                 );
                 index++;
			}
		}
	}
    displayColors(colors);
}
```
 
9. `displayColors(Color[] colors)` implementieren:

```java
private static void displayColors(Color[] colors) {
		
	int height = (int) Math.ceil(colors.length/10.0) * 100 + 10;
		
	ImageArray image = new ImageArray(1010, height);
	for(int i = 0; i < colors.length; i++) {
		int x = i % 10 * 100 + 10;
		int y = i / 10 * 100 + 10;
		
		image.fillRect(x, y, 90, 90, colors[i]);
	}
	
	image.show();
}
```

10. Testen

