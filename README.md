# Algorithmen-Demonstrator

Interaktives JavaFX-Tool zur schrittweisen Visualisierung von Algorithmen — entwickelt als iPro-Projekt an der **FHNW Hochschule für Informatik** für Studierende des Moduls Prog1.

---

## Übersicht

Der Algorithmen-Demonstrator ermöglicht es, zwei klassische Algorithmen der Prog1-Vorlesung **Schritt für Schritt** nachzuvollziehen. Jeder Schritt kann vorwärts und rückwärts navigiert werden. Eigene Eingaben sind jederzeit möglich.

---

## Algorithmen

### Binary Search

Visualisiert die binäre Suche auf einem sortierten Array mit Zeigern für `i`, `m` und `j`.

**Besonderheit: 4 wählbare Invarianten**

| Invariante | Bereich | Bedingung |
|---|---|---|
| `[i..j]` | beide inklusiv | `i <= j` |
| `[i..j)` | links inklusiv | `i < j` |
| `(i..j)` | beide exklusiv | `i+1 < j` |
| `(i..j]` | rechts inklusiv | `i < j` |

Die Engine berechnet je nach Invariante die korrekten Startwerte, die Mittelpunkt-Berechnung (auf- oder abrunden) und die Grenzen-Updates automatisch.

**Features:**
- Farbige Array-Boxen zeigen den aktuellen Suchbereich
- Pfeile markieren `i`, `m`, `j` mit Indexbeschriftung
- Info-Panel erklärt die gewählte Invariante
- Manuelle Array-Eingabe oder Zufalls-Generierung
- Suchwert und Invariante frei wählbar
- Step / Undo Navigation via Stack-basierter History

---

### MergeSort

Visualisiert den rekursiven MergeSort-Algorithmus mit wachsendem Rekursionsbaum.

**Features:**
- **Rekursionsbaum** wächst Schritt für Schritt (DIVIDE = blau, MERGE = grün)
- **Variablen-Panel** zeigt `left`, `mid`, `right`, `depth` und Phase des aktuellen Schritts
- **CallStack-Anzeige** zeigt den echten Methodenaufruf-Stack zur Laufzeit
- **Aktions-Panel** erklärt in Klartext was gerade passiert (z.B. "Teile [0..9] bei Index 4")
- **Pfeile** zeigen `left`, `mid`, `right` im aktiven Knoten (kollisionsgeschützt)
- Manuelle Array-Eingabe
- Step / Undo / Reset Navigation

---

## Architektur

Das Projekt folgt einer klaren Trennung von **Engine** (Algorithmus-Logik) und **GUI** (JavaFX-Sections).

```
MainMenu
├── BinarySearchGUI
│   ├── BinarySearchEngine      ← Algorithmus-Logik mit Stack-History
│   │   ├── State               ← Immutable Record (i, j, m, isFound)
│   │   └── InvariantType       ← Enum mit 4 Varianten + Lambda-Logik
│   └── Sections
│       ├── ArrayVisualizationSection
│       ├── InputSection
│       ├── ControlSection
│       └── InfoSection
│
└── MergeSortGUI
    ├── MergeSortEngine         ← Generiert alle Steps vorab als List<MergeState>
    │   ├── MergeState          ← Snapshot pro Schritt (Phase, Array, left/mid/right)
    │   └── TreeNodeInfo        ← Zustand eines Knotens im Rekursionsbaum
    └── Sections
        ├── RecursionTreeSection
        ├── VariablesSection
        ├── ActionSection
        └── ControlSection

Shared
├── components/ArrayInputComponent
├── components/StyledButton
└── utils/ArrayGenerator
```

---

## Starten

### Voraussetzungen

- **Java 24** (oder höher)
- **Maven** (oder der enthaltene `mvnw` Wrapper)

### Build & Run

```bash
cd BinarySearch
./mvnw clean package
java -jar target/AlgoDemo.jar
```

Die JAR enthält alle Plattform-Natives (Windows, macOS Intel/Apple Silicon, Linux) — kein separates JavaFX nötig.

---

## Tests ausführen

JUnit 5 Tests für die `MergeSortEngine`:

```bash
cd BinarySearch
mvn test
```

**Testabdeckung der MergeSortEngine:**

| Testklasse | Was wird getestet |
|---|---|
| `Initialisierung` | Standard-Array, defensive Kopien, Startzustand |
| `SchrittGenerierung` | Erster/letzter Schritt, gleiche Anzahl DIVIDE/MERGE |
| `Navigation` | Step, Undo, Reset, Grenzbedingungen |
| `SortierKorrektheit` | Ergebnis korrekt sortiert, alle Elemente erhalten |
| `Rekursionsbaum` | Tiefe, Grösse, Status der Knoten, Blatt-Erkennung |

---

## Technologien

| Technologie | Version | Zweck |
|---|---|---|
| Java | 24 | Sprache |
| JavaFX | 21.0.6 | GUI-Framework |
| Maven | 3.x | Build & Dependency Management |
| Maven Shade Plugin | — | Fat-JAR Erstellung |
| JUnit Jupiter | 5.12.1 | Unit Tests |

---

## Projektstruktur

```
AlgoDemo/
├── BinarySearch/               ← Maven-Modul (enthält beide Algorithmen)
│   ├── src/main/java/ch/fhnw/
│   │   ├── Launcher.java
│   │   ├── MainMenu.java
│   │   ├── binarysearch/       ← Binary Search GUI + Engine + Sections
│   │   ├── mergeSort/          ← MergeSort GUI + Engine + Sections
│   │   ├── components/         ← Wiederverwendbare UI-Komponenten
│   │   └── utils/              ← ArrayGenerator
│   ├── src/test/java/
│   │   └── MergeSortEngineTests.java
│   └── pom.xml
└── Dokumentation/
    └── IPRO/                   ← Entwicklungsdokumentation (Version 1–5)
```

---

## Autor

**Piriyan Vijayanantha**
iPro HS25, FHNW Hochschule für Informatik

Betreuer: Wolfgang Weck