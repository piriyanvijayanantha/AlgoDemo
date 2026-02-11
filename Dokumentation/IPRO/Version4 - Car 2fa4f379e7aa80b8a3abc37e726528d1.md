# Version4 - Car

Ziele

- [BinarySearch - Random Zahlen generieren können](Version4%20-%20Car%202fa4f379e7aa80b8a3abc37e726528d1.md)
- [Inklusiv und Exklusiv ersetzen](Version4%20-%20Car%202fa4f379e7aa80b8a3abc37e726528d1.md)
- [MergeSort Lernen und Verstehen Rekursiv](Version4%20-%20Car%202fa4f379e7aa80b8a3abc37e726528d1.md)
- [MergeSortEngine bauen mit Steps und States](Version4%20-%20Car%202fa4f379e7aa80b8a3abc37e726528d1.md)
- [MergeSort Mockup bauen Java FX](Version4%20-%20Car%202fa4f379e7aa80b8a3abc37e726528d1.md)
- [Rekursionsbaum erstellen mit JavaFX](Version4%20-%20Car%202fa4f379e7aa80b8a3abc37e726528d1.md)
- [Action Box bauen](Version4%20-%20Car%202fa4f379e7aa80b8a3abc37e726528d1.md)
- [Resultat Version4](Version4%20-%20Car%202fa4f379e7aa80b8a3abc37e726528d1.md)
- ArrayInput Integrieren
- **JUnitTests laufen mitschreiben**
    
    

## BinarySearch - Random Zahlen generieren können

![image.png](Version4%20-%20Car/image.png)

Zuerst wollte ich ein kombiniertes InputLabel für Array machen, welches aber während der Umsetzung komisch aussah und auch nicht so benutzerfreundlich. Danach fing ich neu an und dieses mal entschied ich mich ein seperates generate Feld zu machen, dass in das ArrayLabel reinschreiben kann. So kann ich immernoch manuell alles machen und habe zusätzlich die Option per Spinner eine anzahl Array einzugeben. Die grösste Hürde war wie ich es mit dem Layout machen würde. Die umsetzung mit Random Zahlen war ziemlich einfach. Aber dieses Benutzerfreundlich zu machen hat doch mehr Aufwand gekostet. Jetzt müsste man das beim User zum Test geben und schauen ob es intuitiv ist. 

## Inklusiv und Exklusiv ersetzen

Im Gespräch mit Wolfgang kam auf, dass die Begrifflichkeiten Inklusiv und Exklusiv falsch interpretiert werden können. 

![image.png](Version4%20-%20Car/image%201.png)

Es wurde mit dieser einfachen Grafik ersetzt, mit der Klar ersichtlich ist ob i und j vor oder nach der Grenze vorkommen.

## MergeSort Lernen und Verstehen Rekursiv

Zu beginn des MergeSorts wollte ich den Rekursiven MergeSort repetieren, um auch die Visualisierung für den Lernerfolg zu bauen. Da kam es gerade gelegen, dass ich es nicht mehr im Kopf hatte und deshalb auch nochmals lernen musste. Ich habe mir dazu dieses Video angeschaut: [https://youtu.be/bOk35XmHPKs?si=4YW2I0xyL3D1kL1C](https://youtu.be/bOk35XmHPKs?si=4YW2I0xyL3D1kL1C). Mit Hilfe dieses Videos konnte ich dann die Java Version programmieren auf meiner eigenen Art. Um das ganze zu vereinfachen habe ich noch eine Divide Methode eingebaut. Diese half mir fürs Verständnis, aber ist am Ende nur unnötig mehr code. Am Ende sah mein MergeSort Algorithmus so aus:

```java
public static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            divide(array, left, right);
        }
    }

    private static void divide(int[] array, int left, int right) {
        int mid = (left + right) / 2;

        // Rekursiv links sortieren
        mergeSort(array, left, mid);

        // Rekursiv rechts sortieren
        mergeSort(array, mid + 1, right);

        // Beide Hälften zusammenführen
        merge(array, left, mid, right);
    }

    private static void merge(int[] array, int left, int mid, int right){

        int[] leftPart = new int[mid - left + 1];
        int[] rightPart = new int[right - mid];

        for (int i = 0; i < leftPart.length; i++) {
            leftPart[i] = array[left + i];
        }
        for (int i = 0; i < rightPart.length; i++) {
            rightPart[i] = array[mid + 1 + i];
        }
        // Merge-Prozess
        int i = 0;  // Index für leftPart
        int j = 0;  // Index für rightPart
        int k = left;  // Index für array

        while (i < leftPart.length && j < rightPart.length) {
            if (leftPart[i] <= rightPart[j]) {
                array[k] = leftPart[i];
                i++;
            } else {
                array[k] = rightPart[j];
                j++;
            }
            k++;
        }

        // Rest von leftPart kopieren (falls vorhanden)
        while (i < leftPart.length) {
            array[k] = leftPart[i];
            i++;
            k++;
        }

        // Rest von rightPart kopieren (falls vorhanden)
        while (j < rightPart.length) {
            array[k] = rightPart[j];
            j++;
            k++;
        }
    }
```

## MergeSortEngine bauen mit Steps und States

Ich musste mir überlegen, wie ich jeden einzelnen Schritt mache. Bei Binary Search war das einfach aber nun habe ich es mit einer Rekursiven Sortierung zu tun. Da kann ich nicht mehr das prinzip mit dem Stack nutzen, da ich nicht einfach mitten in der Rekursion stoppen kann. Also musste ich hier anders vorgehen und die ganzen Schritte alles generieren am Anfang und per State Klasse die das Aktuelle Array kopiert, die Variabeln speichert und die Details obs Merge oder Divide ist und was wie gemacht wird. Diese States speichere ich dann in einer ArrayList und kann sie per Index Zugriff immer holen. So habe ich immer die Kontrolle bei Step forward und Backward und das implementieren von einer Reset Methode mit der ich zum Anfang komme war auch nicht so schwierig. 

## MergeSort Mockup bauen Java FX

Ich habe mir ein paar Konzept überlegt für die Umsetzung. Am Ende war mir Wichtig, dass ich auch den Lernerfolg in den Vordergrund stelle und nicht nur die Machbarkeit der Visualisierung. Unten sieht man die Einzelnen Konzepte:

![image.png](Version4%20-%20Car/image%202.png)

![image.png](Version4%20-%20Car/image%203.png)

![image.png](Version4%20-%20Car/image%204.png)

Ich habe mich am Ende dazu entschieden einen Baum zu zeichnen als Visualisierung, der am Anfang bei der visualisierung noch keine Zahlen beinhaltet, da dass keine Rolle spielt für die Teilung. Beim Merge werden dann die Zahlen eingeblendet und man s

Ich habe die Konzepte einem Studenten gezeigt, der Prog1 besucht hatte und somit den MergeSort schon kennt. Das Interview dazu ist hier verlinkt: 

[Identifizieren von Endbenutzerbedürfnissen für MergeSort](Version4%20-%20Car/Identifizieren%20von%20Endbenutzerbed%C3%BCrfnissen%20f%C3%BCr%20Mer%202fe4f379e7aa8034841bc8b2e55c1ee9.md)

Nach diesem Interview konnte ich das Feedback noch mit einbauen und ein finales Mockup zeichnen:

![image.png](Version4%20-%20Car/image%205.png)

### Umsetzung mit JavaFX

Ich habe mal grob probiert das Layout umzusetzen. Zum Teil habe ich farben und Styles der Einzelnen Elemente KI Generiert um die CSS arbeit zu ersparen.

![image.png](Version4%20-%20Car/image%206.png)

Ich konnte auch Elemente wie die Buttons aus dem BinarySearch recyceln. Ausserdem habe ich auch den Aufbau genau gleich gemacht mit einer GUI Klasse und einzelnen Sections.

## Rekursionsbaum erstellen mit JavaFX

Bei der Planung dieser Grafik habe ich mich mit der KI zusammengetan und nach möglichen Umsetzungen geschaut. Dabei wollte ich mein MGLI Wissen zu den Graphen anwenden, weill es mich an einen Spannbaum erinnert hat.

### Knoten

Ich wollte jedes Array als Knoten darstellen und die letzten knoten sind dann die Blätter weil sie nur eine Kante haben. Für den Knoten baute ich eine Knoten Klasse wo die Informationen gespeichert waren.

```java
 public TreeNodeInfo(int left, int right, int depth, int[] values) {
        this.left = left;
        this.right = right;
        this.depth = depth;
        this.values = values;
    }
```

Da der Knoten für die Devide Phase keine Zahlen aufweisen soll, habe ich eine linke und rechte Grenze, um die Grösse des Knotens zu bestimmen.

### Breite des Baumes ausrechnen

```java
//Rekursive Methode, die beim Start Root Node bekommt um die Breite des ganzen Baumes zu berechnen
    private double calculateWidths(TreeNodeInfo node, List<TreeNodeInfo> allNodes) {
        double nodeWidth = node.size() * (CELL_WIDTH + CELL_GAP) - CELL_GAP;
        //überspringt Kinder Suche, da ein Blatt
        if (node.isLeaf()) {
            nodeWidths.put(key(node), nodeWidth);
            return nodeWidth;
        }
        TreeNodeInfo leftChild = findLeftChild(node, allNodes);
        TreeNodeInfo rightChild = findRightChild(node, allNodes);

        double totalWidth;
        if (leftChild != null && rightChild != null) {
            double leftWidth = calculateWidths(leftChild, allNodes);
            double rightWidth = calculateWidths(rightChild, allNodes);
            double childrenWidth = leftWidth + GROUP_GAP + rightWidth;
            totalWidth = Math.max(nodeWidth, childrenWidth);
        } else {
            totalWidth = nodeWidth;
        }

        nodeWidths.put(key(node), totalWidth);
        return totalWidth;
    }
```

Bei dieser Methode half gab mir die KI hilfreiche Tipps, wie zb. es Rekursiv umzusetzen oder auch mit einer key() - Methode, um einen KeyWert mit den Grenzen des Knotens und der Tiefe zu machen. 

### Positionierung von Array

Auch hier wurde der gleiche Ansatz wie oben bei der Breiten ausrechnung gebraucht. Es wird Rekursiv die Positionen des Knoten festgelegt.  Als weiteren Parameter wird der Startpunkt benötigt. Da dieser nach den DepthLabels folgen muss und nicht überlappen darf mit dem Label. 

### Zeichnen der Arrays

Da habe ich mich sehr auf KI Hilfe gestützt. Ich habe ihm das MockUp gegeben und weiter Anpassungen mit der er dann die Styles anwenden sollte. 

Zuerst musste ich die Arrays (Knoten) zeichnen. Dazu nutze ich schon die ausgerechneten Positionen aus der vorherigen Methode. Mit dieser hatte ich die X Position eines Knotens. Die Y Position errechnete ich durch die Depth * LEVEL_HEIGHT (eine Konstante die ich für das Level Spacing gesetzt habe). Die X Position eines Knotens musste ich noch für jede Box im Array anpassen. Die Y Position bleibt für jede Box gleich: 

```java
//X - Position
Double posX = nodePositions.get(key(node));
for (int i = 0; i < node.size(); i++) {
     double x = posX + i * (CELL_WIDTH + CELL_GAP);
}
//Y - Position 
double y = MARGIN_TOP + node.depth * LEVEL_HEIGHT;
```

Als nächstes musste ich die Verbindungslinien zeichnen. Die Idee dahinter ist, dass der Parent Node schaut ob er Kinder hat oder ein Blatt ist. Falls er Kinder hat wird eine Linie von der unteren Mitte des Parent zur oberen Mitte des Kindes gezeichnet. 

### Einfärben in einer Box

Für die Farben habe ich mich hier auf die FHNW Farbpallette bezogen:
[https://webteam.pages.fhnw.ch/fhnw-styleguide-v4/colors/](https://webteam.pages.fhnw.ch/fhnw-styleguide-v4/colors/) die ich hier zusammengefasst habe:
**Farben — FHNW Corporate Design**

**FHNW Hauptfarben**
#0065A4  FHNW Blau
#C4071B  FHNW Rot
#fde70e  FHNW Gelb
#EA8700  TrueTangerine (Orange)

**FHNW Grautöne**
#4c4c4c  Dunkelgrau (Text)
#767573  Text und Border Grau
#999999  Mittleres Grau
#bebdb9  Scrollbar Grau
#deded9  Mouseover Grau
#F1F1EE  Helles Grau
#ffffff  Hintergrund

**FHNW Statusfarben**
#df305b  Fehlerrot
#fff7f7  Rot Hintergrund
#e31650  Notfall Border

Ich habe die TreeNodeInfo.java Klasse mit einem Status Attribut vergrössert, um die Farben je nach Status zu verteilen.

![image.png](Version4%20-%20Car/image%207.png)

## Action Box bauen

Die Idee hinter dieser Action Box ist es, ein LiveKommentator darzustellen, der die einzelnen Steps im Rekursionsbaum erklärt. Dieser hat dann 4 Modes dementsprechend. Ein “Start” Mode der als Startbildschirm dient. “Devide” Mode der erklärt wo geteilt wird. “Merge” Mode, komplexeste Methode, die das Sortieren im Merge beinhaltet und eine “Done” Methode für den Endstand. 

## ArrayInput Integrieren

Die Idee dabei ist es ArrayInput aus dem Binary Search, welches auch generierung von Array beinhaltet, zu recyclen. Dazu musste ich es aus der Section in BinarySearch Extrahieren und in einer neuen Komponenten Klasse reintun. So konnte ich das ganze wieder gebrauchen im MergeSort und habe mir dabei auch noch Code im BinarySearch aufgeräumt. Im MergeSort musste ich dann zwei Zeilen machen, da eine nicht mehr ausreichte für alle Komponenten. Also machte ich es wie in Binary Search, wo ich oben das ArrayInput Label mit generierung hatte und unten dran habe ich immernoch die gleiche Zeile wie vorher. 

## JUnit Tests

Ich habe während diesen Tagen und immerwieder paar JUnit Tests geschrieben und habe die MergeSortEngine mit dem getestet. Dabei habe ich die Funktionalitäten:  *Initialisierung, SchrittGenerierung, Navigation, Sortier Korrektheit und Rekursionsbaum* getestet*.* Ich habe dafür ein Setup Methode die vor jedem Test ausgeführt wird und habe für die jeweilgen Bereiche Nested Classes erstellt. 
**

## Resultat Version4

![image.png](Version4%20-%20Car/image%208.png)