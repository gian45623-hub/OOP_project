package ui;

import java.awt.BorderLayout;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import util.InputHandler;

public class GameWindow extends JFrame {
    private enum SceneType {
        TITLE,
        DUSKWALL_MARKET,
        ROAD,
        FOREST,
        WAYSTATION,
        VILLAGE,
        TAVERN,
        VALDENMERE,
        SPIRE,
        FORTRESS,
        REFUGEE_CAMP,
        SACRED_FLAME,
        VAULT,
        COMBAT
    }

    private static GameWindow instance;

    private JTextArea textArea;
    private JScrollPane textScrollPane;
    private JPanel buttonPanel;
    private GameCanvas canvas;
    private JLabel timerLabel;
    private JLabel sceneLabel;
    private JButton musicToggleButton;
    private Timer countdownTimer;
    private Sequencer musicSequencer;
    private int secondsRemaining = 90;
    private int timerMinChoice;
    private int timerMaxChoice;
    private boolean musicPlaying;
    private SceneType currentScene = SceneType.TITLE;
    private String currentSceneTitle = "Aethermoor";
    private String storyContext = "";

    private final Color fgColor = new Color(233, 228, 212);
    private final Color panelGlass = new Color(10, 14, 18, 214);
    private final Color panelBorder = new Color(166, 135, 86);
    private final Color buttonBg = new Color(193, 160, 103);
    private final Color buttonBgHover = new Color(219, 187, 122);
    private final Color buttonFg = new Color(31, 22, 12);
    private final Font uiFont = new Font("Dialog", Font.BOLD, 16);
    private final Font bodyFont = new Font("Monospaced", Font.PLAIN, 17);
    private final Font titleFont = new Font("Serif", Font.BOLD, 18);

    public GameWindow() {
        setTitle("Aethermoor");
        setMinimumSize(new Dimension(960, 640));
        setSize(1280, 820);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        canvas = new GameCanvas();
        canvas.setLayout(null);

        textArea = new JTextArea() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(12, 17, 22, 0));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        textArea.setEditable(false);
        textArea.setForeground(fgColor);
        textArea.setFont(bodyFont);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setMargin(new Insets(18, 18, 18, 18));

        textScrollPane = new JScrollPane(textArea);
        textScrollPane.setBorder(BorderFactory.createLineBorder(panelBorder, 2));
        textScrollPane.getViewport().setOpaque(false);
        textScrollPane.setOpaque(false);
        textScrollPane.getVerticalScrollBar().setUI(createScrollBarUI());
        textScrollPane.getHorizontalScrollBar().setUI(createScrollBarUI());
        canvas.add(textScrollPane);

        sceneLabel = new JLabel(currentSceneTitle);
        sceneLabel.setFont(titleFont);
        sceneLabel.setForeground(new Color(246, 233, 187));
        sceneLabel.setOpaque(true);
        sceneLabel.setBackground(panelGlass);
        sceneLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(panelBorder, 2),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        canvas.add(sceneLabel);

        timerLabel = new JLabel();
        timerLabel.setFont(uiFont.deriveFont(18f));
        timerLabel.setForeground(new Color(235, 240, 235));
        timerLabel.setBackground(panelGlass);
        timerLabel.setOpaque(true);
        timerLabel.setHorizontalAlignment(JLabel.CENTER);
        timerLabel.setBorder(BorderFactory.createLineBorder(panelBorder, 2));
        timerLabel.setVisible(false);
        canvas.add(timerLabel);

        musicToggleButton = createStyledButton("Music: Off");
        musicToggleButton.setFont(uiFont.deriveFont(15f));
        musicToggleButton.addActionListener(e -> toggleMusic());
        canvas.add(musicToggleButton);

        add(canvas, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(true);
        buttonPanel.setBackground(panelGlass);
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(panelBorder, 2),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)));
        canvas.add(buttonPanel);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                layoutResponsiveComponents();
                canvas.repaint();
            }
        });

        instance = this;
        redirectSystemOut();
        layoutResponsiveComponents();
    }

    private class GameCanvas extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            paintSceneBackground(g2, panelWidth, panelHeight);

            int groundY = (int) (panelHeight * 0.9);
            int maxCharacterHeight = Math.max(170, Math.min(360, (int) (panelHeight * 0.42)));
            String selectedCharacter = System.getProperty("aethermoor.selectedCharacter");

            if (selectedCharacter == null) {
                drawCharacter(g2, "Mage", (int) (panelWidth * 0.25), groundY, maxCharacterHeight - 30);
                drawCharacter(g2, "Knight", (int) (panelWidth * 0.50), groundY, maxCharacterHeight);
                drawCharacter(g2, "Priest", (int) (panelWidth * 0.75), groundY, maxCharacterHeight - 20);
            } else {
                drawCharacter(g2, selectedCharacter, (int) (panelWidth * 0.50), groundY, maxCharacterHeight + 10);
            }
            g2.dispose();
        }
    }

    private void layoutResponsiveComponents() {
        int width = Math.max(getContentPane().getWidth(), 960);
        int height = Math.max(getContentPane().getHeight(), 640);
        int margin = Math.max(18, width / 48);
        int textWidth = Math.min((int) (width * 0.46), 700);
        int textHeight = Math.min((int) (height * 0.48), 410);
        int hudWidth = Math.min(230, width / 4);
        int bottomHeight = Math.max(120, (int) (height * 0.2));

        textScrollPane.setBounds(margin, margin + 54, textWidth, textHeight);
        sceneLabel.setBounds(margin, margin, Math.min(340, textWidth), 44);
        timerLabel.setBounds(width - margin - hudWidth, margin, 110, 44);
        musicToggleButton.setBounds(width - margin - hudWidth + 120, margin, hudWidth - 120, 44);
        buttonPanel.setBounds(margin, height - bottomHeight - margin, width - (margin * 2), bottomHeight);
    }

    private BasicScrollBarUI createScrollBarUI() {
        return new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbColor = new Color(169, 138, 84);
                trackColor = new Color(29, 34, 39, 180);
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createScrollButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createScrollButton();
            }

            private JButton createScrollButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorder(BorderFactory.createEmptyBorder());
                return button;
            }
        };
    }

    private void paintSceneBackground(Graphics2D g2, int width, int height) {
        switch (currentScene) {
            case DUSKWALL_MARKET -> paintDuskwall(g2, width, height);
            case ROAD -> paintRoad(g2, width, height);
            case FOREST -> paintForest(g2, width, height);
            case WAYSTATION -> paintWaystation(g2, width, height);
            case VILLAGE -> paintVillage(g2, width, height);
            case TAVERN -> paintTavern(g2, width, height);
            case VALDENMERE -> paintValdenmere(g2, width, height);
            case SPIRE -> paintSpire(g2, width, height);
            case FORTRESS -> paintFortress(g2, width, height);
            case REFUGEE_CAMP -> paintRefugeeCamp(g2, width, height);
            case SACRED_FLAME -> paintSacredFlame(g2, width, height);
            case VAULT -> paintVault(g2, width, height);
            case COMBAT -> paintCombat(g2, width, height);
            default -> paintTitleScene(g2, width, height);
        }

        g2.setColor(new Color(8, 10, 14, 64));
        for (int y = 0; y < height; y += 4) {
            g2.drawLine(0, y, width, y);
        }
    }

    private void paintTitleScene(Graphics2D g2, int width, int height) {
        g2.setColor(new Color(15, 30, 53));
        g2.fillRect(0, 0, width, height);
        paintSkyGradient(g2, width, height, new Color(28, 76, 123), new Color(218, 180, 105));
        paintMountains(g2, width, height, new Color(54, 75, 93), new Color(36, 48, 62));
        paintCastle(g2, width, height, 0.72, 0.28, 0.22);
        paintForeground(g2, width, height, new Color(34, 69, 44), new Color(19, 40, 26));
    }

    private void paintDuskwall(Graphics2D g2, int width, int height) {
        paintSkyGradient(g2, width, height, new Color(83, 67, 94), new Color(221, 145, 90));
        paintBuildings(g2, width, height, new Color(92, 67, 54), new Color(60, 42, 36), true);
        paintStreet(g2, width, height, new Color(99, 88, 74), new Color(71, 58, 49));
    }

    private void paintRoad(Graphics2D g2, int width, int height) {
        paintSkyGradient(g2, width, height, new Color(78, 126, 158), new Color(231, 195, 126));
        paintMountains(g2, width, height, new Color(63, 96, 112), new Color(42, 61, 75));
        paintWindingRoad(g2, width, height, new Color(161, 136, 91), new Color(112, 86, 54));
        paintTreeLine(g2, width, height, new Color(36, 82, 53), new Color(20, 48, 31));
    }

    private void paintForest(Graphics2D g2, int width, int height) {
        paintSkyGradient(g2, width, height, new Color(36, 62, 70), new Color(94, 118, 88));
        paintTreeLine(g2, width, height, new Color(28, 59, 39), new Color(15, 32, 21));
        paintForestDepth(g2, width, height);
    }

    private void paintWaystation(Graphics2D g2, int width, int height) {
        paintSkyGradient(g2, width, height, new Color(51, 59, 86), new Color(196, 141, 87));
        paintMountains(g2, width, height, new Color(49, 64, 81), new Color(30, 39, 52));
        paintWindingRoad(g2, width, height, new Color(128, 107, 74), new Color(85, 63, 40));
        paintWaystationShelter(g2, width, height);
    }

    private void paintVillage(Graphics2D g2, int width, int height) {
        paintSkyGradient(g2, width, height, new Color(110, 150, 176), new Color(242, 201, 124));
        paintBuildings(g2, width, height, new Color(135, 103, 72), new Color(82, 57, 39), false);
        paintStreet(g2, width, height, new Color(142, 123, 89), new Color(89, 71, 51));
    }

    private void paintTavern(Graphics2D g2, int width, int height) {
        g2.setColor(new Color(41, 28, 18));
        g2.fillRect(0, 0, width, height);
        g2.setColor(new Color(85, 58, 32));
        for (int x = 0; x < width; x += 70) {
            g2.fillRect(x, 0, 20, height);
        }
        g2.setColor(new Color(217, 169, 84));
        g2.fillOval((int) (width * 0.15), (int) (height * 0.12), 110, 110);
        g2.fillOval((int) (width * 0.7), (int) (height * 0.2), 90, 90);
        g2.setColor(new Color(99, 69, 37));
        g2.fillRect(0, (int) (height * 0.75), width, (int) (height * 0.25));
    }

    private void paintValdenmere(Graphics2D g2, int width, int height) {
        paintSkyGradient(g2, width, height, new Color(88, 116, 146), new Color(240, 196, 132));
        paintCastle(g2, width, height, 0.58, 0.23, 0.28);
        paintCityWalls(g2, width, height);
        paintStreet(g2, width, height, new Color(126, 118, 108), new Color(79, 76, 70));
    }

    private void paintSpire(Graphics2D g2, int width, int height) {
        paintSkyGradient(g2, width, height, new Color(53, 70, 96), new Color(190, 139, 105));
        paintCityWalls(g2, width, height);
        g2.setColor(new Color(66, 62, 78));
        g2.fillRect((int) (width * 0.62), (int) (height * 0.1), (int) (width * 0.12), (int) (height * 0.6));
        g2.setColor(new Color(102, 97, 120));
        g2.fillRect((int) (width * 0.655), (int) (height * 0.03), (int) (width * 0.05), (int) (height * 0.18));
        g2.setColor(new Color(138, 68, 84));
        g2.fillOval((int) (width * 0.64), (int) (height * 0.18), 28, 28);
    }

    private void paintFortress(Graphics2D g2, int width, int height) {
        paintSkyGradient(g2, width, height, new Color(44, 54, 72), new Color(148, 116, 85));
        paintMountains(g2, width, height, new Color(52, 56, 66), new Color(28, 32, 39));
        paintCastle(g2, width, height, 0.66, 0.2, 0.26);
        paintStreet(g2, width, height, new Color(112, 101, 91), new Color(74, 63, 55));
    }

    private void paintRefugeeCamp(Graphics2D g2, int width, int height) {
        paintSkyGradient(g2, width, height, new Color(98, 124, 136), new Color(208, 173, 116));
        paintCityWalls(g2, width, height);
        for (int i = 0; i < 6; i++) {
            int x = (int) (width * (0.12 + (i * 0.12)));
            int y = (int) (height * (0.58 + ((i % 2) * 0.05)));
            paintTent(g2, x, y, 86, 60);
        }
        g2.setColor(new Color(87, 102, 70));
        g2.fillRect(0, (int) (height * 0.72), width, (int) (height * 0.28));
    }

    private void paintSacredFlame(Graphics2D g2, int width, int height) {
        paintSkyGradient(g2, width, height, new Color(123, 116, 89), new Color(247, 212, 143));
        paintCathedral(g2, width, height, new Color(198, 190, 170), new Color(132, 118, 92));
        g2.setColor(new Color(219, 171, 73));
        g2.fillOval((int) (width * 0.72), (int) (height * 0.08), 96, 96);
    }

    private void paintVault(Graphics2D g2, int width, int height) {
        g2.setColor(new Color(19, 18, 24));
        g2.fillRect(0, 0, width, height);
        g2.setColor(new Color(61, 45, 33));
        for (int x = 0; x < width; x += 80) {
            g2.fillRect(x, 0, 18, height);
        }
        g2.setColor(new Color(154, 103, 67));
        g2.fillOval((int) (width * 0.48), (int) (height * 0.18), 100, 100);
        g2.setColor(new Color(82, 113, 122));
        g2.drawOval((int) (width * 0.41), (int) (height * 0.12), 240, 200);
        g2.drawOval((int) (width * 0.43), (int) (height * 0.16), 200, 150);
    }

    private void paintCombat(Graphics2D g2, int width, int height) {
        paintSkyGradient(g2, width, height, new Color(74, 36, 36), new Color(222, 119, 84));
        paintMountains(g2, width, height, new Color(60, 46, 56), new Color(34, 24, 31));
        g2.setColor(new Color(71, 32, 35));
        g2.fillRect(0, (int) (height * 0.73), width, (int) (height * 0.27));
    }

    private void paintSkyGradient(Graphics2D g2, int width, int height, Color top, Color bottom) {
        for (int y = 0; y < height; y++) {
            float blend = (float) y / Math.max(1, height - 1);
            int r = (int) (top.getRed() * (1 - blend) + bottom.getRed() * blend);
            int g = (int) (top.getGreen() * (1 - blend) + bottom.getGreen() * blend);
            int b = (int) (top.getBlue() * (1 - blend) + bottom.getBlue() * blend);
            g2.setColor(new Color(r, g, b));
            g2.drawLine(0, y, width, y);
        }
    }

    private void paintMountains(Graphics2D g2, int width, int height, Color far, Color near) {
        Polygon ridge = new Polygon();
        ridge.addPoint(0, (int) (height * 0.56));
        ridge.addPoint((int) (width * 0.18), (int) (height * 0.35));
        ridge.addPoint((int) (width * 0.32), (int) (height * 0.5));
        ridge.addPoint((int) (width * 0.51), (int) (height * 0.26));
        ridge.addPoint((int) (width * 0.7), (int) (height * 0.48));
        ridge.addPoint((int) (width * 0.88), (int) (height * 0.31));
        ridge.addPoint(width, (int) (height * 0.55));
        ridge.addPoint(width, height);
        ridge.addPoint(0, height);
        g2.setColor(far);
        g2.fillPolygon(ridge);

        Polygon front = new Polygon();
        front.addPoint(0, (int) (height * 0.72));
        front.addPoint((int) (width * 0.15), (int) (height * 0.55));
        front.addPoint((int) (width * 0.4), (int) (height * 0.68));
        front.addPoint((int) (width * 0.58), (int) (height * 0.5));
        front.addPoint((int) (width * 0.8), (int) (height * 0.7));
        front.addPoint(width, (int) (height * 0.58));
        front.addPoint(width, height);
        front.addPoint(0, height);
        g2.setColor(near);
        g2.fillPolygon(front);
    }

    private void paintTreeLine(Graphics2D g2, int width, int height, Color trunk, Color deep) {
        for (int i = 0; i < 14; i++) {
            int x = (int) (i * (width / 13.0));
            int base = (int) (height * (0.78 + ((i % 3) * 0.03)));
            int size = 90 + (i % 4) * 18;
            g2.setColor(deep);
            Polygon canopy = new Polygon(
                    new int[]{x, x + size / 2, x + size},
                    new int[]{base, base - size, base},
                    3);
            g2.fillPolygon(canopy);
            g2.setColor(trunk);
            g2.fillRect(x + size / 2 - 6, base, 12, size / 3);
        }
    }

    private void paintForestDepth(Graphics2D g2, int width, int height) {
        for (int i = 0; i < 30; i++) {
            int x = (i * 97) % width;
            int y = (int) (height * 0.36) + ((i * 41) % (height / 2));
            int size = 18 + (i % 5) * 12;
            g2.setColor(new Color(37, 78, 52, 165));
            g2.fillOval(x, y, size, size / 2);
        }
    }

    private void paintStreet(Graphics2D g2, int width, int height, Color top, Color bottom) {
        Polygon street = new Polygon();
        street.addPoint((int) (width * 0.35), (int) (height * 0.56));
        street.addPoint((int) (width * 0.65), (int) (height * 0.56));
        street.addPoint(width, height);
        street.addPoint(0, height);
        g2.setColor(top);
        g2.fillPolygon(street);
        g2.setColor(bottom);
        g2.drawPolygon(street);
    }

    private void paintWindingRoad(Graphics2D g2, int width, int height, Color path, Color edge) {
        Polygon road = new Polygon();
        road.addPoint((int) (width * 0.53), (int) (height * 0.55));
        road.addPoint((int) (width * 0.61), (int) (height * 0.55));
        road.addPoint((int) (width * 0.76), height);
        road.addPoint((int) (width * 0.25), height);
        road.addPoint((int) (width * 0.41), (int) (height * 0.78));
        road.addPoint((int) (width * 0.32), (int) (height * 0.66));
        g2.setColor(path);
        g2.fillPolygon(road);
        g2.setColor(edge);
        g2.drawPolygon(road);
    }

    private void paintBuildings(Graphics2D g2, int width, int height, Color wall, Color roof, boolean crooked) {
        g2.setColor(new Color(49, 63, 72));
        g2.fillRect(0, (int) (height * 0.62), width, (int) (height * 0.38));
        for (int i = 0; i < 7; i++) {
            int buildingX = i * (width / 7);
            int buildingW = (width / 8) + ((i % 3) * 18);
            int buildingH = (int) (height * (0.17 + ((i % 4) * 0.05)));
            int topY = (int) (height * 0.62) - buildingH;
            g2.setColor(wall);
            g2.fillRect(buildingX, topY, buildingW, buildingH);
            g2.setColor(roof);
            if (crooked) {
                Polygon roofPoly = new Polygon(
                        new int[]{buildingX - 6, buildingX + buildingW / 2, buildingX + buildingW + 6},
                        new int[]{topY, topY - 24 - (i % 2) * 8, topY},
                        3);
                g2.fillPolygon(roofPoly);
            } else {
                g2.fillRect(buildingX, topY - 18, buildingW, 18);
            }
        }
    }

    private void paintCastle(Graphics2D g2, int width, int height, double centerXRatio, double topRatio, double widthRatio) {
        int castleW = (int) (width * widthRatio);
        int castleX = (int) (width * centerXRatio) - castleW / 2;
        int castleY = (int) (height * topRatio);
        g2.setColor(new Color(144, 146, 145));
        g2.fillRect(castleX, castleY + 90, castleW, 180);
        g2.fillRect(castleX + 40, castleY, 70, 220);
        g2.fillRect(castleX + castleW - 110, castleY + 25, 70, 195);
        g2.fillRect(castleX + castleW / 2 - 55, castleY - 35, 110, 250);
        g2.setColor(new Color(112, 69, 56));
        g2.fillPolygon(new Polygon(new int[]{castleX + 36, castleX + 76, castleX + 116}, new int[]{castleY, castleY - 55, castleY}, 3));
        g2.fillPolygon(new Polygon(new int[]{castleX + castleW - 116, castleX + castleW - 76, castleX + castleW - 36}, new int[]{castleY + 25, castleY - 28, castleY + 25}, 3));
        g2.fillPolygon(new Polygon(new int[]{castleX + castleW / 2 - 62, castleX + castleW / 2, castleX + castleW / 2 + 62}, new int[]{castleY - 35, castleY - 100, castleY - 35}, 3));
    }

    private void paintCityWalls(Graphics2D g2, int width, int height) {
        g2.setColor(new Color(116, 120, 124));
        g2.fillRect((int) (width * 0.1), (int) (height * 0.52), (int) (width * 0.8), (int) (height * 0.16));
        for (int i = 0; i < 6; i++) {
            g2.fillRect((int) (width * 0.14) + i * (width / 9), (int) (height * 0.44), 44, (int) (height * 0.24));
        }
    }

    private void paintWaystationShelter(Graphics2D g2, int width, int height) {
        int x = (int) (width * 0.56);
        int y = (int) (height * 0.42);
        g2.setColor(new Color(96, 76, 49));
        g2.fillRect(x, y, 180, 20);
        g2.fillRect(x + 12, y + 20, 14, 120);
        g2.fillRect(x + 154, y + 20, 14, 120);
        g2.fillPolygon(new Polygon(new int[]{x - 10, x + 90, x + 190}, new int[]{y, y - 70, y}, 3));
        g2.setColor(new Color(234, 178, 92, 180));
        g2.fillOval(x + 78, y + 54, 24, 24);
    }

    private void paintTent(Graphics2D g2, int x, int y, int width, int height) {
        g2.setColor(new Color(145, 116, 82));
        g2.fillPolygon(new Polygon(new int[]{x, x + width / 2, x + width}, new int[]{y + height, y, y + height}, 3));
        g2.setColor(new Color(78, 57, 42));
        g2.drawLine(x + width / 2, y, x + width / 2, y + height);
    }

    private void paintCathedral(Graphics2D g2, int width, int height, Color wall, Color trim) {
        int baseX = (int) (width * 0.56);
        int baseY = (int) (height * 0.24);
        g2.setColor(wall);
        g2.fillRect(baseX, baseY + 90, 280, 220);
        g2.fillRect(baseX + 30, baseY, 52, 190);
        g2.fillRect(baseX + 198, baseY, 52, 190);
        g2.setColor(trim);
        g2.fillPolygon(new Polygon(new int[]{baseX + 22, baseX + 56, baseX + 90}, new int[]{baseY, baseY - 72, baseY}, 3));
        g2.fillPolygon(new Polygon(new int[]{baseX + 190, baseX + 224, baseX + 258}, new int[]{baseY, baseY - 72, baseY}, 3));
    }

    private void paintForeground(Graphics2D g2, int width, int height, Color grass, Color shadow) {
        g2.setColor(grass);
        g2.fillRect(0, (int) (height * 0.76), width, (int) (height * 0.24));
        g2.setColor(shadow);
        g2.fillOval((int) (width * 0.58), (int) (height * 0.72), 180, 40);
    }

    private void drawCharacter(Graphics2D g2, String characterClass, int centerX, int groundY, int maxHeight) {
        int scale = Math.max(2, maxHeight / 72);
        int bodyHeight = 70 * scale;
        int bodyWidth = 30 * scale;
        int x = centerX - bodyWidth / 2;
        int y = groundY - bodyHeight;

        g2.setColor(new Color(9, 12, 16, 88));
        g2.fillOval(centerX - bodyWidth / 2, groundY - 8, bodyWidth, 18);

        switch (characterClass) {
            case "Mage" -> drawMage(g2, x, y, scale);
            case "Knight" -> drawKnight(g2, x, y, scale);
            case "Priest" -> drawPriest(g2, x, y, scale);
            default -> drawKnight(g2, x, y, scale);
        }
    }

    private void drawMage(Graphics2D g2, int x, int y, int s) {
        g2.setColor(new Color(72, 27, 104));
        g2.fillRect(x + 8 * s, y + 24 * s, 16 * s, 26 * s);
        g2.fillRect(x + 6 * s, y + 48 * s, 20 * s, 18 * s);
        g2.setColor(new Color(108, 57, 161));
        g2.fillRect(x + 5 * s, y + 22 * s, 22 * s, 22 * s);
        g2.setColor(new Color(238, 199, 164));
        g2.fillRect(x + 11 * s, y + 10 * s, 10 * s, 12 * s);
        g2.setColor(Color.BLACK);
        g2.fillRect(x + 9 * s, y + 8 * s, 14 * s, 4 * s);
        g2.fillRect(x + 6 * s, y + 12 * s, 20 * s, 3 * s);
        g2.fillPolygon(new Polygon(new int[]{x + 16 * s, x + 4 * s, x + 28 * s}, new int[]{y - 6 * s, y + 12 * s, y + 12 * s}, 3));
        g2.setColor(new Color(232, 193, 78));
        g2.fillRect(x + 13 * s, y + 30 * s, 6 * s, 3 * s);
        g2.setColor(new Color(90, 58, 31));
        g2.fillRect(x - 2 * s, y + 18 * s, 4 * s, 50 * s);
        g2.setColor(new Color(47, 84, 174));
        g2.fillOval(x - 7 * s, y + 8 * s, 12 * s, 12 * s);
        g2.setColor(Color.BLACK);
        g2.drawOval(x - 7 * s, y + 8 * s, 12 * s, 12 * s);
    }

    private void drawKnight(Graphics2D g2, int x, int y, int s) {
        g2.setColor(new Color(173, 178, 182));
        g2.fillRect(x + 9 * s, y + 18 * s, 14 * s, 30 * s);
        g2.fillRect(x + 7 * s, y + 44 * s, 18 * s, 18 * s);
        g2.setColor(new Color(203, 207, 210));
        g2.fillRect(x + 10 * s, y + 8 * s, 12 * s, 12 * s);
        g2.setColor(new Color(69, 76, 86));
        g2.fillRect(x + 12 * s, y + 12 * s, 8 * s, 4 * s);
        g2.setColor(new Color(123, 129, 135));
        g2.fillRect(x + 2 * s, y + 26 * s, 8 * s, 18 * s);
        g2.fillRect(x + 22 * s, y + 24 * s, 7 * s, 22 * s);
        g2.setColor(new Color(55, 63, 97));
        g2.fillRect(x + 11 * s, y + 62 * s, 5 * s, 10 * s);
        g2.fillRect(x + 17 * s, y + 62 * s, 5 * s, 10 * s);
        g2.setColor(new Color(206, 210, 214));
        g2.fillRect(x + 28 * s, y + 15 * s, 4 * s, 42 * s);
        g2.setColor(new Color(38, 36, 40));
        g2.fillRect(x + 27 * s, y + 56 * s, 6 * s, 3 * s);
        Polygon shield = new Polygon(
                new int[]{x - 1 * s, x + 8 * s, x + 12 * s, x + 9 * s, x + 1 * s},
                new int[]{y + 28 * s, y + 24 * s, y + 35 * s, y + 56 * s, y + 56 * s},
                5);
        g2.setColor(new Color(191, 196, 200));
        g2.fillPolygon(shield);
        g2.setColor(new Color(120, 129, 134));
        g2.drawPolygon(shield);
        g2.drawLine(x + 5 * s, y + 27 * s, x + 5 * s, y + 54 * s);
        g2.drawLine(x + 1 * s, y + 40 * s, x + 9 * s, y + 40 * s);
    }

    private void drawPriest(Graphics2D g2, int x, int y, int s) {
        g2.setColor(new Color(117, 75, 49));
        g2.fillRect(x + 8 * s, y + 22 * s, 16 * s, 42 * s);
        g2.fillRect(x + 6 * s, y + 54 * s, 20 * s, 16 * s);
        g2.setColor(new Color(226, 198, 170));
        g2.fillRect(x + 11 * s, y + 10 * s, 10 * s, 12 * s);
        g2.setColor(new Color(255, 252, 247));
        g2.fillRect(x + 10 * s, y + 21 * s, 12 * s, 4 * s);
        g2.fillRect(x + 13 * s, y + 24 * s, 6 * s, 7 * s);
        g2.setColor(new Color(93, 60, 42));
        g2.fillPolygon(new Polygon(new int[]{x + 6 * s, x + 16 * s, x + 26 * s, x + 22 * s, x + 10 * s},
                new int[]{y + 13 * s, y - 1 * s, y + 13 * s, y + 28 * s, y + 28 * s}, 5));
        g2.setColor(new Color(255, 245, 230));
        g2.fillRect(x + 28 * s, y + 30 * s, 11 * s, 20 * s);
        g2.setColor(new Color(156, 102, 61));
        g2.fillOval(x + 24 * s, y + 27 * s, 17 * s, 26 * s);
        g2.setColor(new Color(255, 248, 238));
        g2.fillRect(x + 31 * s, y + 31 * s, 3 * s, 16 * s);
        g2.fillRect(x + 27 * s, y + 37 * s, 11 * s, 3 * s);
    }

    private void updateSceneFromText(String text) {
        storyContext = (storyContext + " " + text).toLowerCase();
        if (storyContext.length() > 4000) {
            storyContext = storyContext.substring(storyContext.length() - 4000);
        }

        SceneType nextScene = currentScene;
        String nextTitle = currentSceneTitle;

        if (containsAny(text, "ACT I", "ACT II", "ACT III")) {
            nextTitle = text.trim();
        }

        if (containsAny(storyContext, "combat:", "attacks!", "confronts you", "guard", "battle")) {
            nextScene = SceneType.COMBAT;
            nextTitle = "Combat";
        } else if (containsAny(storyContext, "vault of the first flame", "pyre conduit", "the vault")) {
            nextScene = SceneType.VAULT;
            nextTitle = "Vault Of The First Flame";
        } else if (containsAny(storyContext, "sacred flame", "ember quarter", "chapter house")) {
            nextScene = SceneType.SACRED_FLAME;
            nextTitle = "Sacred Flame";
        } else if (containsAny(storyContext, "refugee camp", "camp outside valdenmere")) {
            nextScene = SceneType.REFUGEE_CAMP;
            nextTitle = "Refugee Camp";
        } else if (containsAny(storyContext, "spire", "rogue arcanist", "harvesting chambers")) {
            nextScene = SceneType.SPIRE;
            nextTitle = "The Spire";
        } else if (containsAny(storyContext, "fort greyveil", "cult fortress", "gate. inside")) {
            nextScene = SceneType.FORTRESS;
            nextTitle = "Fort Greyveil";
        } else if (containsAny(storyContext, "valdenmere", "iron hall", "summit")) {
            nextScene = SceneType.VALDENMERE;
            nextTitle = "Valdenmere";
        } else if (containsAny(storyContext, "tavern", "ashen tap")) {
            nextScene = SceneType.TAVERN;
            nextTitle = "Tavern";
        } else if (containsAny(storyContext, "waystation", "campfire", "roadside")) {
            nextScene = SceneType.WAYSTATION;
            nextTitle = "Waystation";
        } else if (containsAny(storyContext, "forest", "trees", "treeline", "ashen territory")) {
            nextScene = SceneType.FOREST;
            nextTitle = "Forest Path";
        } else if (containsAny(storyContext, "road", "highway", "crossroads", "crestfall")) {
            nextScene = SceneType.ROAD;
            nextTitle = "The Road";
        } else if (containsAny(storyContext, "village of millhaven", "market town", "village")) {
            nextScene = SceneType.VILLAGE;
            nextTitle = "Village";
        } else if (containsAny(storyContext, "duskwall", "market stalls", "old fish")) {
            nextScene = SceneType.DUSKWALL_MARKET;
            nextTitle = "Duskwall";
        }

        currentScene = nextScene;
        currentSceneTitle = nextTitle;
        sceneLabel.setText(currentSceneTitle);
    }

    private boolean containsAny(String source, String... needles) {
        String haystack = source.toLowerCase();
        for (String needle : needles) {
            if (haystack.contains(needle.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private void redirectSystemOut() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) {
                appendOutput(String.valueOf((char) b));
            }
            @Override
            public void write(byte[] b, int off, int len) {
                appendOutput(new String(b, off, len));
            }
        };
        System.setOut(new PrintStream(out, true));
    }

    public static GameWindow getInstance() {
        return instance;
    }

    public void startChoiceTimer(int min, int max) {
        timerMinChoice = min;
        timerMaxChoice = max;
        secondsRemaining = 90;
        timerLabel.setVisible(true);
        updateTimerLabel();
        if (countdownTimer != null) {
            countdownTimer.stop();
        }
        countdownTimer = new Timer(1000, e -> {
            if (secondsRemaining > 0) {
                secondsRemaining--;
                updateTimerLabel();
            } else {
                countdownTimer.stop();
                int randomChoice = ThreadLocalRandom.current().nextInt(timerMinChoice, timerMaxChoice + 1);
                appendOutput("\n[Time ran out. Random choice selected: " + randomChoice + "]\n");
                InputHandler.submitInput(String.valueOf(randomChoice));
            }
        });
        countdownTimer.start();
    }

    public void stopChoiceTimer() {
        if (countdownTimer != null) {
            countdownTimer.stop();
        }
        timerLabel.setVisible(false);
    }

    private void updateTimerLabel() {
        int minutes = secondsRemaining / 60;
        int seconds = secondsRemaining % 60;
        timerLabel.setText(String.format("Time %d:%02d", minutes, seconds));
        timerLabel.setForeground(secondsRemaining <= 10 ? new Color(255, 120, 120) : new Color(235, 240, 235));
    }

    private void toggleMusic() {
        if (musicPlaying) {
            stopMusic();
        } else {
            startMusic();
        }
    }

    private void startMusic() {
        try {
            if (musicSequencer == null) {
                musicSequencer = MidiSystem.getSequencer();
                musicSequencer.open();
                musicSequencer.setSequence(createBackgroundMusic());
                musicSequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            }
            musicSequencer.start();
            musicPlaying = true;
            musicToggleButton.setText("Music: On");
        } catch (Exception e) {
            appendOutput("\n[Background music is unavailable on this system.]\n");
        }
    }

    private void stopMusic() {
        if (musicSequencer != null && musicSequencer.isRunning()) {
            musicSequencer.stop();
        }
        musicPlaying = false;
        musicToggleButton.setText("Music: Off");
    }

    private Sequence createBackgroundMusic() throws InvalidMidiDataException {
        Sequence sequence = new Sequence(Sequence.PPQ, 24);
        Track track = sequence.createTrack();
        addMidiEvent(track, ShortMessage.PROGRAM_CHANGE, 0, 48, 0, 0);

        int[] melody = {57, 60, 64, 60, 55, 59, 62, 59, 53, 57, 60, 57, 55, 59, 62, 64};
        int[] bass = {45, 45, 43, 43, 41, 41, 43, 43};
        long tick = 0;

        for (int i = 0; i < melody.length; i++) {
            addNote(track, 0, melody[i], 52, tick, 24);
            tick += 24;
        }

        tick = 0;
        for (int note : bass) {
            addNote(track, 0, note, 38, tick, 48);
            tick += 48;
        }

        return sequence;
    }

    private void addNote(Track track, int channel, int note, int velocity, long startTick, long duration) throws InvalidMidiDataException {
        addMidiEvent(track, ShortMessage.NOTE_ON, channel, note, velocity, startTick);
        addMidiEvent(track, ShortMessage.NOTE_OFF, channel, note, 0, startTick + duration);
    }

    private void addMidiEvent(Track track, int command, int channel, int data1, int data2, long tick) throws InvalidMidiDataException {
        ShortMessage message = new ShortMessage();
        message.setMessage(command, channel, data1, data2);
        track.add(new MidiEvent(message, tick));
    }

    @Override
    public void dispose() {
        if (countdownTimer != null) {
            countdownTimer.stop();
        }
        if (musicSequencer != null) {
            musicSequencer.stop();
            musicSequencer.close();
        }
        super.dispose();
    }

    public void appendOutput(String text) {
        SwingUtilities.invokeLater(() -> {
            updateSceneFromText(text);
            textArea.append(text);
            textArea.setCaretPosition(textArea.getDocument().getLength());
            sceneLabel.setText(currentSceneTitle);
            canvas.repaint();
        });
    }

    public void showSelectedCharacter(String characterClass) {
        SwingUtilities.invokeLater(() -> {
            System.setProperty("aethermoor.selectedCharacter", characterClass);
            canvas.repaint();
        });
    }

    public void clearButtons() {
        SwingUtilities.invokeLater(() -> {
            buttonPanel.removeAll();
            buttonPanel.revalidate();
            buttonPanel.repaint();
        });
    }

    public void showNumberChoices(int min, int max) {
        SwingUtilities.invokeLater(() -> {
            buttonPanel.removeAll();
            if (max - min + 1 >= 3) {
                startChoiceTimer(min, max);
            } else {
                stopChoiceTimer();
            }
            for (int i = min; i <= max; i++) {
                JButton btn = createStyledButton(i + "");
                final int choice = i;
                btn.addActionListener(e -> {
                    stopChoiceTimer();
                    clearButtons();
                    InputHandler.submitInput(String.valueOf(choice));
                });
                buttonPanel.add(btn);
                buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
            buttonPanel.revalidate();
            buttonPanel.repaint();
        });
    }

    public void showContinueButton() {
        SwingUtilities.invokeLater(() -> {
            buttonPanel.removeAll();
            stopChoiceTimer();
            JButton btn = createStyledButton("Continue");
            btn.addActionListener(e -> {
                clearButtons();
                InputHandler.submitInput(""); // Just trigger continue
            });
            buttonPanel.add(btn);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            buttonPanel.revalidate();
            buttonPanel.repaint();
        });
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(uiFont);
        btn.setBackground(buttonBg);
        btn.setForeground(buttonFg);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(104, 74, 38), 2));
        btn.setContentAreaFilled(true);
        btn.setOpaque(true);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Short.MAX_VALUE, 56));
        btn.setPreferredSize(new Dimension(800, 56));
        btn.addChangeListener(e -> btn.setBackground(btn.getModel().isRollover() ? buttonBgHover : buttonBg));
        return btn;
    }
}
