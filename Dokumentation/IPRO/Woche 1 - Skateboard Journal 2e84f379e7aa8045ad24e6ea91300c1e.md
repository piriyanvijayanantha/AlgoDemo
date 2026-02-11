# Woche 1 - Skateboard Journal

Als erstes habe ich am Vormittag vom MockUp eine darstellung nach JavaFX gebaut. Diese hat zum jetztigen Zeitpunkt noch keine Integration mit der Binary Search Engine. Aber das kommt jetzt am Nachmittag

![2026-01-14_13-35-24.png](Woche%201%20-%20Skateboard%20Journal/2026-01-14_13-35-24.png)

Ich habe mich fürs Skateboard dazu entschieden die Array Länge auf einer festen Grösse zu belassen, um für den Prototyp so Zeit zu sparen. NOTIZ: Ich habe es am Ende doch noch richtig gemacht. 

Notiz: OOP in der zweiten Woche noch mehr fokussieren. zb Handler von Events ausbauen. GUI Methode wird langsam zu gross

Notiz: Beim Testen habe ich bemerkt, dass die schrittt besser visualisiert werden müssen. aktuell ist ein sprung von box zu box bei jeder variable aber ich will diesen sprung auch noch visualisieren mit einem gebogenen Pfeil. ich weiss zwar nicht wie man das macht in java fx aber das wäre ein neues Issue.

ich habe das produkt nochmals überarbeitet für das Skateboard und additional habe ich folgende Features eingebaut:

**Pfeile statt Farbige Blöcke**

Da das anfärben von Blöcken nicht ganz sinn ergibt, da i und j grenzen sind und keine index meiner Interpretierung nach, habe ich irgendwie grenzen darstellen wollen.

### Resultat (skateboard)

![image.png](Woche%201%20-%20Skateboard%20Journal/image.png)

### Feedbackmarkt

Die Invarianten besser in den Fokus nehmen, Mehr Interaktiv (zb. Variablen selber nennen können oder nächsten move predicten, indem man die sicht des Computers bekommt. Die Pfeile sind nicht korrekt, Sie sollten auf eine Box zeigen. Inputvalidierung fehlt aktuell noch.

Ein punkt war auch die Variablenetzung von i und j ob das dem User überlassen werden soll oder fix sein sollte (var i = -1 / 0)