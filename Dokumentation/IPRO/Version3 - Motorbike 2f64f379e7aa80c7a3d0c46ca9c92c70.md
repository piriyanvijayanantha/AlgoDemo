# Version3 - Motorbike

## Ziele:

- [Objektdiagramm](Version3%20-%20Motorbike%202f64f379e7aa80c7a3d0c46ca9c92c70.md)
- [Abahmetestkonzept](Version3%20-%20Motorbike%202f64f379e7aa80c7a3d0c46ca9c92c70.md), ein paar Test die man mit dem Stakeholder bespricht. (Sammeln und validieren von funktionalen Anforderungen für ein Softwaresystem mit einem Stakeholder gemäß einer Standardmethodik und Erstellen von Akzeptanzkriterien)
- Tests für Interview Partner, Interaction Testing
- PullRequest anschauen
- KI Hilfe für Refactorn -> OOP umsetzung
- [Invariantenanzeige](Version3%20-%20Motorbike%202f64f379e7aa80c7a3d0c46ca9c92c70.md)
- [Vorschlag für weiteren Algorithmus umsetzung](Version3%20-%20Motorbike%202f64f379e7aa80c7a3d0c46ca9c92c70.md)

## Objektdiagramm

![image.png](Version3%20-%20Motorbike/image.png)

Dieser wurde mit ClaudeAI generiert und ist ein überblick über das ganze Programm.

### Legende

| Blaue Pfeile | Vererbungen (extends) |
| --- | --- |
| Schwarze Pfeile | Komposition (Besitz über…) |
| Roter Pfeil | Callbacks / Dependencies |

## Testkonzept

Als nächstes habe ich ein Testkonzept geschrieben mit 6 Funktionalen Anforderungen und 4 nicht-Funktionale Anforderungen. Bei den Funktionalen Anforderungen habe ich mich vorallen auf die Hauptfunktionen konzentriert und auch Tests geschrieben die ich weiss, dass die normalerweise funktionieren sollten, aber als Sicherheit habe ich sie trotzdem aufgeschrieben, da man so wirklich 100% sicher sein kann vor dem Release.

### Code Review

Danach habe ich die Verbesserungsvorschläge von Wolfgang angeschaut und den Code nochmal überarbeitet. Ein Beispiel wäre diese Step Funktion in der Engine

![image.png](Version3%20-%20Motorbike/image%201.png)

Rechts sieht man die verbesserte Version, wo kein early Return gegeben wird. sondern nun ist die Abbruchbedingung die Main Bedingung und der Rest ist in einer Schlaufe ohne early Returns. 

Weiteres Refactoring machte ich mit der Buttonklasse. Da der Button überall einheitlich sein soll, habe ich eine StyledButton Klasse erstellt, mit der ich zusätlich Farbe und ein Runnable als Aktion des Buttons mitgebe. 

## Invariante vom User auswählen lassen

Ich habe ein Enum gemacht mit den Auswahl möglichkeiten:

```java
public enum InvariantType {
    BOTH_INCLUSIVE("[i..j] (beide inklusiv)", 0, -1),
    LEFT_INCLUSIVE("[i..j) (i inkl, j exkl)", 0, 0),
    BOTH_EXCLUSIVE("(i..j) (beide exklusiv)", -1, 0),
    RIGHT_INCLUSIVE("(i..j] (i exkl, j inkl)", -1, -1);
    //rest des Codes
}
```

Das nächste Problem hatte ich dann mit den Pfeilen, da sie an eine Array Position festliegen, musste ich das Array um 2 Positionen erhöhen, um exklusiv darzustellen.
Das grösste Hindernis war hier die einheitliche Umsetzung. Ich wollte dass es nur in der Engine und im Enum selber die Logik braucht. Z.B die Abbruchbedinung war mehrfach im Code was zu Reduanzen führt und der Code deshalb an mehreren Orten geändert werden müsste. Ich beschloss fast alles, was die Entscheidung von der Invariante war in das Enum rein zu tun. Das machte ich zum ersten mal. Auch lernte ich das BiPrädikat kennen, welches mir für die Abbruchbedingun diente.

Am Ende habe ich die ganzen Entscheide ins Enum getan so dass dann die Klasse so aussah:

![image.png](Version3%20-%20Motorbike/image%202.png)

Ich habe die ganzen Berechnungen mitgegeben. Also m Berechnung und i/j positionierung je nach case. Ich wollte die Berechnung nicht direkt machen sondern weitergeben, da ich die Idee hatte, dass der User die Formel selber wählen kann um zu prüfen, ob er richtig oder falsch liegt. Z.B der User gibt eine andere Berechnung der neuen position von I an, dann soll es weiter gehen und der User sieht dann den Fehler. Diese Idee bleibt aktuell noch eine Idee.

## MergeSort

![image.png](Version3%20-%20Motorbike/image%203.png)

Mit diesem Ansatz probiere ich nun für den Merge Sort eine saubere Lösung zu finden. 

![image.png](Version3%20-%20Motorbike/image%204.png)

Das ist mal ein Provisorisches Mockup. Die KI hat dafür noch ein richtiges Mockup erstellt.

![image.png](Version3%20-%20Motorbike/image%205.png)