# Interaktiver Algorithmen-Demonstrator

Ein JavaFX-Tool das Algorithmen Schritt für Schritt visualisiert — entwickelt für Prog1-Studierende an der FHNW.

## Algorithmen

### Binary Search
- Sortiertes Array mit visueller Darstellung
- Pfeile zeigen `i`, `m`, `j` Positionen
- Invarianten wählbar (inklusiv/exklusiv)
- Eigenes Array und Suchwert eingeben

### MergeSort
- Rekursionsbaum wächst Schritt für Schritt
- Divide- und Merge-Phasen farblich unterschieden
- Pfeile zeigen `left`, `mid`, `right` Grenzen
- CallStack- und Variablen-Anzeige

## Features
- **Step / Undo / Reset** — Navigation durch alle Schritte
- **Eigene Arrays** — manuell eingeben oder zufällig generieren
- **FHNW Corporate Design** — Farben und Layout nach CI

## Starten

```bash
mvn clean package
java -jar target/BinarySearch-1.0-SNAPSHOT.jar
```

**Voraussetzung:** Java 24

## Technologien
- Java 24
- JavaFX 21
- Maven (Shade Plugin)
- JUnit 5

## Autor
Piriyan Vijayanantha — iPro HS25, FHNW Hochschule für Informatik

Betreuer: Wolfgang Weck