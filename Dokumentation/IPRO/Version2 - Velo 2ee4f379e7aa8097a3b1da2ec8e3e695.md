# Version2 - Velo

## Ziele (Velo) - bis 27.01

### [Undo Step hinzufügen](Version2%20-%20Velo%202ee4f379e7aa8097a3b1da2ec8e3e695.md)

Mit einem Button klick sollte man einen Schritt im Algorithmus zurück gehen können.

### [Inputvalidierung](Version2%20-%20Velo%202ee4f379e7aa8097a3b1da2ec8e3e695.md)

Binary Search kann nur sortierte Arrays aufnehmen und muss deshalb überprüft werden, ob das der Fall ist.

### Code Refactorn in OOP Design

Alles ist aktuell in einer GUI Klasse. Da muss mehr Struktur her. Idee: für jede Section eine components Klasse machen.

### InfoTab Hinzufügen

Ein InfoTab der als Lernhilfe dienen sollte.

### Inputvalidierung

Ich habe zuerst mit der Inputvalidierung gestartet. Dort konnte ich gut mit try catch arbeiten, da ich den Input nehme und diese zu einem int parsen will. und falls das nicht der Fall ist gibt es zwei verschiedene Exceptionen. Bei 
`NumberFormatException` wird es wohl etwas mit einer Zahl zu tun haben, muss aber eine Ganze Zahl sein. Bei
`IllegalArgumentException` handelt es sich um etwas komplett anderes als eine Zahl. Somit habe ich einen kleinen Exkurs in Exception Handeling gemacht. Ich habe den schon bereits exisitierenden StatusLabel gebraucht um diese Exceptions dann anzuzeigen. 

### Undo Step hinzufügen

Danach habe ich mir eine Lösung zur State Speicherung überlegt. Da ich den Algorithmus gegen vorwärts und rückwärts brauchen will. Also die Step back function. Dazu brauche ich das gleiche Prinzip, das auch Java braucht für ihr Runtime, Stacks. Mit diesem kann ich den Stand der Variabeln und ob es schon gefunden wurde mitgeben. und mit Pop würde man dann den letzten Stapel bekommen. Da ich es die Stack Klasse zum ersten mal gebraucht habe, hat es einbisschen gedauert bis ich es verstanden habe. 

https://www.geeksforgeeks.org/java/stack-class-in-java/

![image.png](Version2%20-%20Velo/image.png)

Danach ging es zur verdeutlichung von der Invariante. Dazu habe ich ein Info Tab Hinzugefügt.

![image.png](Version2%20-%20Velo/image%201.png)

Dieser kann dem User zum verständnis dienen. Ausserdem habe ich den i-Pfeil über dem Array getan, so dass sich i und j nicht mehr im Weg sind. 

![image.png](Version2%20-%20Velo/image%202.png)

Als nächstes musste ich mich für entweder UX verbessern oder die Code Architektur verbessern entscheiden. Ich entschied mich für die Code Architektur, da es nachhaltiger zum programmieren ist, das was später kommt. Ich habe mir Inspiration aus Trick17 geholt. Aktuell habe ich das ganze GUI in einer Klasse. Ich wollte ein Pattern folgen, mit dem die einzeölnen Panels in components reintun, so könnte man sie auch wiederverwenden für zb einen weiter Algorithmus wie der Merge Sort. 
//UPDATE
Das Refactoring beanspruchte doch mehr Zeit als angenommen, da ich mich auch in kleinere Sachen einlesen musste. Die grösste Hürde waren die Handler Methoden. da diese über das GUI laufen aber die Button in der Komponentenklasse sind. Durch KI Hilfe kam ich zwar auf die Lösung es brauchte aber auch Zeit um das ganze richtig zu Verstehen.
//UPDATE
Ein weiteres Ziel kam dazu, das ich die Methoden alle Kommentieren sollte, da ich nun ziemlich viele Methoden habe

Eine Frage die sich rausstellte war, ob man das Anfang array hardcodieren sollte. Oder ob der User das Array bestimmen kann. Durch meine zwei interviews konnte ich diese Frage weiterstellen und es ergab sich, dass der User manchmal die Entscheidung nicht treffen will sondern nur die Funktion des Binary Search testen will. Dadurch macht ein Hard Codiertes Array in diesem Fall sinn am Anfang und man kann es über das Input Feld ändern. 

### Farbpallette

[https://www.color-hex.com/color-palette/2280](https://www.color-hex.com/color-palette/2280)

[https://webteam.pages.fhnw.ch/fhnw-styleguide-v4/colors/](https://webteam.pages.fhnw.ch/fhnw-styleguide-v4/colors/)

## Resultat

![image.png](Version2%20-%20Velo/image%203.png)

### Feedbackmarkt

Ich habe den Studierenden nicht erklärt wie es geht, so dass sie selbst es probieren können. Dabei ist mir aufgefallen, dass der Start Button nicht wirklich intuitiv ist. Weiterhin ist immer noch nicht ganz klar ob i inklusive oder exklusive ist.