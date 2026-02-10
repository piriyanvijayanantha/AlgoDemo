package ch.fhnw.mergeSort.section;

import ch.fhnw.mergeSort.Engine.TreeNodeInfo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecursionTreeSection extends VBox {
    //Konstante
    private static final double MARGIN_TOP = 10;
    private static final double DEPTH_LABEL_WIDTH = 50; //Spacing für Depth Label
    private static final double CELL_WIDTH = 32;
    private static final double CELL_HEIGHT = 32;
    private static final double CELL_GAP = 5; //Lücke zwischen den Zellen
    private static final double GROUP_GAP = 32; //TODO: Könnte man evtl. wegnehmen und cellWidth brauchen
    private static final double LEVEL_HEIGHT = 100; //Vertikaler Space zwischen Depths

    private final Pane treePane;
    private final Label titleLabel;

    // Berechnete Positionen und Breiten pro Knoten
    private final Map<String, Double> nodePositions = new HashMap<>();
    private final Map<String, Double> nodeWidths = new HashMap<>();


    public RecursionTreeSection() {
        setPadding(new Insets(10));
        setSpacing(8);
        setStyle("-fx-background-color: white;");
        setAlignment(Pos.TOP_CENTER);

        titleLabel = new Label("Rekursionsbaum");

        treePane = new Pane();
        treePane.setMinHeight(350);
        treePane.setStyle("-fx-background-color: white;");

        //Horizontale Scroll Fläche
        ScrollPane scrollPane = new ScrollPane(treePane);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // die Bar erscheint nur wenn es nötig ist
        scrollPane.setStyle("-fx-background: white; -fx-border-color: transparent;");
        VBox.setVgrow(scrollPane, javafx.scene.layout.Priority.ALWAYS); // um den ganzen Vertikalen Platz zu brauchen

        getChildren().addAll(titleLabel, scrollPane);
    }

    public void update(List<TreeNodeInfo> treeNodes) {
        treePane.getChildren().clear();
        nodePositions.clear();
        nodeWidths.clear();
        //sicherheitscheck
        if (treeNodes == null || treeNodes.isEmpty()) {
            return;
        }

        TreeNodeInfo root = treeNodes.getFirst();
        //Breiten berechnen
        calculateWidths(root, treeNodes);

        // Phase 2: Positionen zuweisen
        double rootWidth = getWidth(root);
        double paneWidth = Math.max(treePane.getWidth(), 700);
        double rootStartX = DEPTH_LABEL_WIDTH + (paneWidth - DEPTH_LABEL_WIDTH - rootWidth) / 2;

        assignPositions(root, rootStartX, treeNodes);

        // Phase 3: Zeichnen
        drawConnections(treeNodes);
        drawDepthLabels(treeNodes);
        for (TreeNodeInfo node : treeNodes) {
            drawNode(node);
        }
    }

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
            totalWidth = Math.max(nodeWidth, childrenWidth); //nimmt den grösseren Wert der beiden Paramter
        } else {
            totalWidth = nodeWidth;
        }

        nodeWidths.put(key(node), totalWidth);
        return totalWidth;
    }

    //Rekursive Methode, die beim Start Root Node bekommt und so die Positionen der Knoten Festlegt für den ganzen Baum
    private void assignPositions(TreeNodeInfo node, double startX, List<TreeNodeInfo> allNodes) {
        double totalWidth = getWidth(node);
        double ownWidth = node.size() * (CELL_WIDTH + CELL_GAP) - CELL_GAP;

        double nodeX = startX + (totalWidth - ownWidth) / 2;
        nodePositions.put(key(node), nodeX);

        if (node.isLeaf()) {
            nodePositions.put(key(node), startX);
            return;
        }

        TreeNodeInfo leftChild = findLeftChild(node, allNodes);
        TreeNodeInfo rightChild = findRightChild(node, allNodes);

        if (leftChild != null && rightChild != null) {
            double leftWidth = getWidth(leftChild);
            double rightWidth = getWidth(rightChild);
            double childrenWidth = leftWidth + GROUP_GAP + rightWidth;
            double childrenStartX = startX + (totalWidth - childrenWidth) / 2;

            assignPositions(leftChild, childrenStartX, allNodes);
            assignPositions(rightChild, childrenStartX + leftWidth + GROUP_GAP, allNodes);
        }
    }

    private void drawNode(TreeNodeInfo node) {
        Double posX = nodePositions.get(key(node)); //x Position des Knotens
        if (posX == null) {
            return;
        }

        double y = MARGIN_TOP + node.depth * LEVEL_HEIGHT; //y Position jeder Box

        for (int i = 0; i < node.size(); i++) {
            double x = posX + i * (CELL_WIDTH + CELL_GAP); //x Position einer Box

            Rectangle cell = new Rectangle(x, y, CELL_WIDTH, CELL_HEIGHT);
            cell.setArcWidth(5); //Ecken Abrunden
            cell.setArcHeight(5);//Ecken Abrunden
            cell.setStroke(getStrokeColor(node.status));
            cell.setStrokeWidth(getStrokeWidth(node.status));
            cell.setFill(getFillColor(node.status));
            treePane.getChildren().add(cell);

            if (node.values != null && i < node.values.length) {
                Text valueText = new Text(String.valueOf(node.values[i]));
                valueText.setFill(getTextColor(node.status));

                double textWidth = valueText.getLayoutBounds().getWidth();
                double textHeight = valueText.getLayoutBounds().getHeight();
                valueText.setX(x + (CELL_WIDTH - textWidth) / 2);
                valueText.setY(y + (CELL_HEIGHT + textHeight) / 2 - 2);

                treePane.getChildren().add(valueText);
            }
        }

        // Gruppenrahmen (gestrichelt)
        if (node.size() > 1) {
            double gw = node.size() * (CELL_WIDTH + CELL_GAP) - CELL_GAP;
            Rectangle border = new Rectangle(posX - 2, y - 2, gw + 4, CELL_HEIGHT + 4);
            border.setFill(Color.TRANSPARENT);
            border.setStroke(getGroupBorderColor(node.status));
            border.setStrokeWidth(1.2);
            border.setArcWidth(7);
            border.setArcHeight(7);
            border.getStrokeDashArray().addAll(4.0, 3.0);
            treePane.getChildren().add(border);
        }
    }

    private void drawConnections(List<TreeNodeInfo> treeNodes) {
        for (TreeNodeInfo parent : treeNodes) {
            TreeNodeInfo leftChild = findLeftChild(parent, treeNodes);
            TreeNodeInfo rightChild = findRightChild(parent, treeNodes);
            if (leftChild != null) drawLine(parent, leftChild);
            if (rightChild != null) drawLine(parent, rightChild);
        }
    }

    private void drawLine(TreeNodeInfo parent, TreeNodeInfo child) {
        double px = getNodeCenterX(parent);
        double py = MARGIN_TOP + parent.depth * LEVEL_HEIGHT + CELL_HEIGHT;
        double cx = getNodeCenterX(child);
        double cy = MARGIN_TOP + child.depth * LEVEL_HEIGHT;

        Line line = new Line(px, py + 2, cx, cy - 2);
        line.setStroke(Color.web("#b8b8b8"));
        line.setStrokeWidth(1.2);
        treePane.getChildren().add(line);
    }

    private void drawDepthLabels(List<TreeNodeInfo> treeNodes) {
        int maxDepth = 0;
        for (TreeNodeInfo n : treeNodes) maxDepth = Math.max(maxDepth, n.depth);

        for (int d = 0; d <= maxDepth; d++) {
            double y = MARGIN_TOP + d * LEVEL_HEIGHT + CELL_HEIGHT / 2 + 4;
            Text label = new Text(3, y, "Tiefe " + d);
            label.setFill(Color.web("#aaaaaa"));
            treePane.getChildren().add(label);
        }
    }

    private TreeNodeInfo findLeftChild(TreeNodeInfo parent, List<TreeNodeInfo> all) {
        for (TreeNodeInfo n : all) {
            if (n.depth == parent.depth + 1 && n.left == parent.left && n.right < parent.right)
                return n;
        }
        return null;
    }

    private TreeNodeInfo findRightChild(TreeNodeInfo parent, List<TreeNodeInfo> all) {
        for (TreeNodeInfo n : all) {
            if (n.depth == parent.depth + 1 && n.right == parent.right && n.left > parent.left)
                return n;
        }
        return null;
    }

    private double getNodeCenterX(TreeNodeInfo node) {
        Double posX = nodePositions.get(key(node));
        if (posX == null) return 0;
        double width = node.size() * (CELL_WIDTH + CELL_GAP) - CELL_GAP;
        return posX + width / 2;
    }

    private double getWidth(TreeNodeInfo node) {
        return nodeWidths.get(key(node));
    }
    private String key(TreeNodeInfo node) {
        return node.left + "-" + node.right + "-" + node.depth;
    }

    //Farben------------------------------- mit KI generiert

    private Color getFillColor(String status) {
        switch (status) {
            case "INITIAL":       return Color.web("#F1F1EE");       // Helles Grau — Startknoten
            case "EMPTY":         return Color.WHITE;                // Hintergrund — leere Boxen
            case "LEAF":          return Color.web("#fde70e", 0.2); // FHNW Gelb — Blatt
            case "DIVIDED":       return Color.web("#F1F1EE");       // Helles Grau — geteilt
            case "ACTIVE_DIVIDE": return Color.web("#fff7f7");       // Rot Hintergrund — aktiv
            case "MERGED":        return Color.web("#0065A4", 0.1); // FHNW Blau — gemerged
            default:              return Color.WHITE;
        }
    }

    private Color getStrokeColor(String status) {
        switch (status) {
            case "ACTIVE_DIVIDE": return Color.web("#df305b");       // Fehlerrot
            case "MERGED":        return Color.web("#0065A4");       // FHNW Blau
            case "LEAF":          return Color.web("#EA8700");       // TrueTangerine
            default:              return Color.web("#bebdb9");       // Scrollbar Grau
        }
    }

    private double getStrokeWidth(String status) {
        switch (status) {
            case "ACTIVE_DIVIDE": return 2.0;
            case "MERGED":        return 1.8;
            default:              return 1.2;
        }
    }

    private Color getTextColor(String status) {
        switch (status) {
            case "ACTIVE_DIVIDE": return Color.web("#df305b");       // Fehlerrot
            case "MERGED":        return Color.web("#0065A4");       // FHNW Blau
            case "LEAF":          return Color.web("#EA8700");       // TrueTangerine
            default:              return Color.web("#4c4c4c");       // Dunkelgrau
        }
    }

    private Color getGroupBorderColor(String status) {
        switch (status) {
            case "ACTIVE_DIVIDE": return Color.web("#df305b", 0.4); // Fehlerrot transparent
            case "MERGED":        return Color.web("#0065A4", 0.4); // FHNW Blau transparent
            default:              return Color.web("#bebdb9", 0.3); // Scrollbar Grau transparent
        }
    }
}