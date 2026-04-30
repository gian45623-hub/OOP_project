package rpgsumting;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class GameFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final int CHOICE_SECONDS = 90;

    private final Player player;
    private final Map<String, StoryNode> story;
    private final ScenePanel scenePanel;
    private final JPanel choicePanel;
    private final JLabel statusLabel;
    private final JLabel timerLabel;
    private final JButton musicButton;
    private final SoundEngine soundEngine;
    private final Random random;
    private Timer choiceTimer;
    private int remainingChoiceSeconds;
    private String pendingStoryNodeId;
    private int choicesSinceRandomScenario;
    private boolean finalActStarted;

    public GameFrame() {
        super("RPGSumting - Choice RPG");

        player = new Player();
        story = StoryGraph.create();
        scenePanel = new ScenePanel();
        choicePanel = new JPanel();
        statusLabel = new JLabel("Choose a job to begin.");
        timerLabel = new JLabel("Timer off");
        musicButton = new JButton("Music: Off");
        soundEngine = new SoundEngine();
        random = new Random();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(900, 680));
        setLayout(new BorderLayout());

        add(createTopBar(), BorderLayout.NORTH);
        add(scenePanel, BorderLayout.CENTER);
        add(choicePanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent event) {
                soundEngine.close();
            }
        });

        showJobSelection();
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout(10, 0));
        topBar.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        topBar.setBackground(new Color(24, 28, 34));

        statusLabel.setForeground(new Color(236, 236, 226));
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        topBar.add(statusLabel, BorderLayout.CENTER);

        timerLabel.setForeground(new Color(245, 207, 116));
        timerLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        topBar.add(timerLabel, BorderLayout.WEST);

        JPanel buttons = new JPanel(new GridLayout(1, 2, 8, 0));
        buttons.setOpaque(false);

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(event -> showJobSelection());

        musicButton.addActionListener(event -> {
            soundEngine.toggle();
            updateMusicButton();
        });

        buttons.add(musicButton);
        buttons.add(restartButton);
        topBar.add(buttons, BorderLayout.EAST);

        return topBar;
    }

    private void showJobSelection() {
        stopChoiceTimer();
        pendingStoryNodeId = null;
        choicesSinceRandomScenario = 0;
        finalActStarted = false;
        statusLabel.setText("Choose a job to begin.");
        scenePanel.showIntro(null);

        choicePanel.removeAll();
        choicePanel.setLayout(new GridLayout(0, 1, 8, 8));
        choicePanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        choicePanel.setBackground(new Color(18, 22, 28));

        for (int i = 0; i < Job.values().length; i++) {
            final Job job = Job.values()[i];
            JButton button = createChoiceButton(job.getDisplayName() + " - " + job.getDescription());
            button.addActionListener(event -> startGame(job));
            choicePanel.add(button);
        }

        choicePanel.revalidate();
        choicePanel.repaint();
    }

    private void startGame(Job job) {
        String name = JOptionPane.showInputDialog(this, "Hero name:", "Traveler");
        if (name == null || name.trim().length() == 0) {
            name = "Traveler";
        }

        player.reset(name.trim(), job);
        pendingStoryNodeId = null;
        choicesSinceRandomScenario = 0;
        finalActStarted = false;
        String[] startNodeIds = StoryGraph.getStartNodeIds(job);
        showNode(startNodeIds[random.nextInt(startNodeIds.length)], null);
    }

    private void showNode(String nodeId, String lastResult) {
        stopChoiceTimer();
        String finaleNodeId = StoryGraph.getFinaleStartNodeId(nodeId);
        if (!finalActStarted && finaleNodeId != null) {
            finalActStarted = true;
            nodeId = finaleNodeId;
        } else {
            String[] endingVariantIds = StoryGraph.getEndingVariantNodeIds(nodeId);
            if (endingVariantIds.length > 0) {
                nodeId = endingVariantIds[random.nextInt(endingVariantIds.length)];
            }
        }

        StoryNode node = story.get(nodeId);
        if (node == null) {
            throw new IllegalArgumentException("Missing story node: " + nodeId);
        }

        statusLabel.setText(player.getStatusLine());
        scenePanel.showNode(node, player, lastResult);

        choicePanel.removeAll();
        choicePanel.setLayout(new GridLayout(0, 1, 8, 8));
        choicePanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        choicePanel.setBackground(new Color(18, 22, 28));

        if (node.isEnding()) {
            JButton restart = createChoiceButton("Play again with another path");
            restart.addActionListener(event -> showJobSelection());
            choicePanel.add(restart);
        } else {
            for (int i = 0; i < node.getChoices().size(); i++) {
                final Choice choice = node.getChoices().get(i);
                JButton button = createChoiceButton(choice.getText());
                button.setToolTipText(choice.getResult() + " (" + choice.getEffectText() + ")");
                button.addActionListener(event -> choose(choice));
                choicePanel.add(button);
            }
            startChoiceTimer(node);
        }

        choicePanel.revalidate();
        choicePanel.repaint();
    }

    private void choose(Choice choice) {
        stopChoiceTimer();
        player.apply(choice);

        String result = choice.getResult() + "  " + choice.getEffectText();
        if (player.getHealth() <= 0) {
            showNode("fallen", result);
            return;
        }

        String nextNodeId = choice.getNextNodeId();
        if (StoryGraph.RESUME_NODE_ID.equals(nextNodeId)) {
            String resumeNodeId = pendingStoryNodeId == null ? "crossroads" : pendingStoryNodeId;
            pendingStoryNodeId = null;
            showNode(resumeNodeId, result);
            return;
        }

        if (shouldTriggerRandomScenario(nextNodeId)) {
            pendingStoryNodeId = nextNodeId;
            choicesSinceRandomScenario = 0;
            String[] randomNodeIds = StoryGraph.getRandomScenarioNodeIds();
            String randomNodeId = randomNodeIds[random.nextInt(randomNodeIds.length)];
            showNode(randomNodeId, result + "  A random scenario interrupts your path.");
            return;
        }

        showNode(nextNodeId, result);
    }

    private JButton createChoiceButton(String text) {
        JButton button = new JButton("<html><body style='width: 760px'>" + escape(text) + "</body></html>");
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("SansSerif", Font.BOLD, 15));
        button.setFocusPainted(false);
        button.setBackground(new Color(238, 232, 214));
        button.setForeground(new Color(30, 34, 40));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(145, 135, 115)),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        return button;
    }

    private String escape(String text) {
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    private void updateMusicButton() {
        musicButton.setText(soundEngine.isEnabled() ? "Music: On" : "Music: Off");
    }

    private void startChoiceTimer(StoryNode node) {
        remainingChoiceSeconds = CHOICE_SECONDS;
        updateTimerLabel();

        choiceTimer = new Timer(1000, event -> {
            remainingChoiceSeconds--;
            updateTimerLabel();

            if (remainingChoiceSeconds <= 0) {
                choiceTimer.stop();
                Choice randomChoice = node.getChoices().get(random.nextInt(node.getChoices().size()));
                choose(randomChoice);
            }
        });
        choiceTimer.start();
    }

    private void stopChoiceTimer() {
        if (choiceTimer != null) {
            choiceTimer.stop();
            choiceTimer = null;
        }
        timerLabel.setText("Timer off");
    }

    private void updateTimerLabel() {
        int minutes = remainingChoiceSeconds / 60;
        int seconds = remainingChoiceSeconds % 60;
        timerLabel.setText(String.format("Choice: %d:%02d", minutes, seconds));
    }

    private boolean shouldTriggerRandomScenario(String nextNodeId) {
        StoryNode nextNode = story.get(nextNodeId);
        if (finalActStarted || pendingStoryNodeId != null || nextNode == null || nextNode.isEnding()) {
            return false;
        }

        choicesSinceRandomScenario++;
        return choicesSinceRandomScenario >= 3 || random.nextInt(100) < 35;
    }
}
