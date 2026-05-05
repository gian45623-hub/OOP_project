package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
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
    private String currentSceneTitle = "A E T H E R M O O R";
    private String storyContext = "";

    private final Color fgColor = new Color(245, 240, 225);
    private final Color panelGlass = new Color(0, 0, 0, 210);
    private final Color panelBorder = new Color(212, 175, 55);
    private final Color buttonBg = new Color(193, 160, 103);
    private final Color buttonBgHover = new Color(219, 187, 122);
    private final Color buttonFg = new Color(31, 22, 12);
    private final Font uiFont = new Font("Monospaced", Font.BOLD, 16);
    private final Font bodyFont = new Font("Monospaced", Font.PLAIN, 18);
    private final Font titleFont = new Font("Serif", Font.BOLD, 22);

    public GameWindow() {
        setTitle("A E T H E R M O O R");
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
        int margin = Math.max(18, width / 40);
        int textWidth = (int) (width * 0.45);
        int textHeight = (int) (height * 0.55);
        int hudWidth = Math.min(260, width / 4);
        
        int numButtons = 0;
        if (buttonPanel != null) {
            numButtons = buttonPanel.getComponentCount() / 2;
        }
        int bottomHeight = Math.max(120, (numButtons * 50) + 32);

        textScrollPane.setBounds(margin, margin + 54, textWidth, textHeight);
        sceneLabel.setBounds(margin, margin, Math.min(400, textWidth), 44);
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

    private java.util.Map<String, java.awt.Image> imageCache = new java.util.HashMap<>();

    private java.awt.Image loadImage(String path) {
        if (!imageCache.containsKey(path)) {
            try {
                imageCache.put(path, javax.imageio.ImageIO.read(new java.io.File(path)));
            } catch (Exception e) {
                System.err.println("Could not load image: " + path);
                imageCache.put(path, null);
            }
        }
        return imageCache.get(path);
    }

    private void paintSceneBackground(Graphics2D g2, int width, int height) {
        String sceneName = currentScene.name().toLowerCase();
        java.awt.Image bgImage = loadImage("src/assets/images/scene_" + sceneName + ".png");
        if (bgImage != null) {
            double imgAspect = (double) bgImage.getWidth(null) / bgImage.getHeight(null);
            double canvasAspect = (double) width / height;
            int drawWidth, drawHeight;
            if (canvasAspect > imgAspect) {
                drawWidth = width;
                drawHeight = (int) (width / imgAspect);
            } else {
                drawHeight = height;
                drawWidth = (int) (height * imgAspect);
            }
            int x = (width - drawWidth) / 2;
            int y = (height - drawHeight) / 2;
            g2.drawImage(bgImage, x, y, drawWidth, drawHeight, null);
        } else {
            g2.setColor(new Color(15, 30, 53));
            g2.fillRect(0, 0, width, height);
        }
        
        g2.setColor(new Color(8, 10, 14, 64));
        for (int y = 0; y < height; y += 4) {
            g2.drawLine(0, y, width, y);
        }
    }


    private void drawCharacter(Graphics2D g2, String characterClass, int centerX, int groundY, int maxHeight) {
        String charName = characterClass.toLowerCase();
        java.awt.Image charImage = loadImage("src/assets/images/char_" + charName + ".png");
        
        int shadowWidth = 80;
        g2.setColor(new Color(9, 12, 16, 128));
        g2.fillOval(centerX - shadowWidth / 2, groundY - 8, shadowWidth, 18);

        if (charImage != null) {
            double charAspect = (double) charImage.getWidth(null) / charImage.getHeight(null);
            int drawHeight = maxHeight;
            int drawWidth = (int) (drawHeight * charAspect);
            int x = centerX - drawWidth / 2;
            int y = groundY - drawHeight;
            g2.drawImage(charImage, x, y, drawWidth, drawHeight, null);
        } else {
            g2.setColor(Color.WHITE);
            g2.fillRect(centerX - 20, groundY - maxHeight, 40, maxHeight);
        }
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
            layoutResponsiveComponents();
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
            layoutResponsiveComponents();
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
            layoutResponsiveComponents();
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
