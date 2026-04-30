package rpgsumting;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class ScenePanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private StoryNode node;
    private Player player;
    private String lastResult;

    public ScenePanel() {
        setPreferredSize(new Dimension(900, 520));
        setMinimumSize(new Dimension(720, 420));
    }

    public void showIntro(Player player) {
        this.node = null;
        this.player = player;
        this.lastResult = null;
        repaint();
    }

    public void showNode(StoryNode node, Player player, String lastResult) {
        this.node = node;
        this.player = player;
        this.lastResult = lastResult;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        SceneType type = node == null ? SceneType.INTRO : node.getSceneType();
        paintBackground(g, type);
        paintAtmosphere(g, type);

        if (player != null && player.getJob() != null) {
            paintCharacter(g, player.getJob(), getWidth() - 265, getHeight() - 330, 1.25);
        } else {
            paintIntroParty(g);
        }

        paintTextPanel(g);
        g.dispose();
    }

    private void paintBackground(Graphics2D g, SceneType type) {
        int width = getWidth();
        int height = getHeight();

        if (type == SceneType.FOREST || type == SceneType.INTRO) {
            g.setPaint(new GradientPaint(0, 0, new Color(24, 43, 52), 0, height, new Color(27, 82, 61)));
            g.fillRect(0, 0, width, height);
            g.setColor(new Color(20, 48, 38));
            for (int x = -40; x < width + 80; x += 80) {
                int base = height - 70;
                g.fillPolygon(new int[] { x, x + 38, x + 76 }, new int[] { base, 90, base }, 3);
                g.setColor(new Color(16, 36, 34));
                g.fillRect(x + 30, base - 20, 15, 80);
                g.setColor(new Color(20, 48, 38));
            }
            g.setColor(new Color(115, 38, 48, 190));
            g.fillOval(width - 170, 55, 82, 82);
        } else if (type == SceneType.CASTLE) {
            g.setPaint(new GradientPaint(0, 0, new Color(54, 54, 65), 0, height, new Color(122, 65, 48)));
            g.fillRect(0, 0, width, height);
            g.setColor(new Color(86, 88, 96));
            g.fillRect(0, height - 190, width, 140);
            for (int x = 20; x < width; x += 90) {
                g.fillRect(x, height - 250, 55, 90);
            }
            g.setColor(new Color(38, 42, 48));
            g.fillRect(0, height - 75, width, 75);
            g.setColor(new Color(230, 130, 58, 160));
            for (int x = 80; x < width; x += 190) {
                g.fillOval(x, height - 135, 42, 75);
            }
        } else if (type == SceneType.CAVE) {
            g.setPaint(new GradientPaint(0, 0, new Color(18, 24, 32), 0, height, new Color(48, 43, 60)));
            g.fillRect(0, 0, width, height);
            g.setColor(new Color(63, 63, 78));
            for (int x = 0; x < width; x += 90) {
                g.fillPolygon(new int[] { x, x + 55, x + 25 }, new int[] { 0, 0, 125 }, 3);
                g.fillPolygon(new int[] { x + 20, x + 80, x + 50 }, new int[] { height, height, height - 130 }, 3);
            }
            g.setColor(new Color(90, 150, 170, 115));
            g.fillOval(width / 2 - 170, height / 2 - 90, 340, 180);
        } else if (type == SceneType.TOWER) {
            g.setPaint(new GradientPaint(0, 0, new Color(30, 35, 72), 0, height, new Color(76, 65, 95)));
            g.fillRect(0, 0, width, height);
            g.setColor(new Color(92, 88, 112));
            g.fillRect(width / 2 - 120, 100, 240, height - 145);
            g.setColor(new Color(50, 50, 70));
            for (int y = 130; y < height - 80; y += 58) {
                g.drawLine(width / 2 - 120, y, width / 2 + 120, y);
            }
            g.setColor(new Color(245, 220, 130, 125));
            g.fillOval(width / 2 - 70, 155, 140, 180);
        } else if (type == SceneType.VILLAGE) {
            g.setPaint(new GradientPaint(0, 0, new Color(43, 76, 90), 0, height, new Color(66, 104, 79)));
            g.fillRect(0, 0, width, height);
            g.setColor(new Color(104, 75, 58));
            for (int x = 80; x < width; x += 210) {
                g.fillRect(x, height - 185, 120, 95);
                g.setColor(new Color(126, 50, 45));
                g.fillPolygon(new int[] { x - 12, x + 60, x + 132 }, new int[] { height - 185, height - 245, height - 185 }, 3);
                g.setColor(new Color(104, 75, 58));
            }
            g.setColor(new Color(45, 58, 49));
            g.fillRect(0, height - 90, width, 90);
        } else if (type == SceneType.ROAD) {
            g.setPaint(new GradientPaint(0, 0, new Color(70, 95, 108), 0, height, new Color(82, 116, 78)));
            g.fillRect(0, 0, width, height);
            g.setColor(new Color(72, 88, 64));
            g.fillRect(0, height - 185, width, 185);
            g.setColor(new Color(122, 96, 68));
            g.fillPolygon(new int[] { width / 2 - 48, width / 2 + 48, width + 160, -160 },
                    new int[] { height - 185, height - 185, height, height }, 4);
            g.setColor(new Color(92, 72, 52));
            for (int y = height - 155; y < height; y += 38) {
                int spread = (height - y) * 2;
                g.drawLine(width / 2 - 26 - spread, y, width / 2 + 26 + spread, y);
            }
            g.setColor(new Color(42, 75, 52));
            for (int x = -20; x < width + 80; x += 115) {
                g.fillRect(x + 35, height - 230, 13, 120);
                g.fillOval(x, height - 285, 85, 85);
            }
        } else if (type == SceneType.INN) {
            g.setPaint(new GradientPaint(0, 0, new Color(88, 56, 45), 0, height, new Color(48, 34, 32)));
            g.fillRect(0, 0, width, height);
            g.setColor(new Color(102, 67, 45));
            for (int y = 60; y < height; y += 44) {
                g.fillRect(0, y, width, 12);
            }
            g.setColor(new Color(180, 92, 48));
            g.fillOval(width - 190, height - 215, 105, 105);
            g.setColor(new Color(255, 180, 78, 170));
            g.fillOval(width - 170, height - 195, 64, 64);
            g.setColor(new Color(70, 44, 34));
            g.fillRect(60, height - 150, 245, 34);
            g.fillRect(82, height - 116, 20, 75);
            g.fillRect(260, height - 116, 20, 75);
            g.setColor(new Color(218, 188, 112, 150));
            for (int x = 90; x < width - 220; x += 140) {
                g.fillOval(x, 92, 20, 20);
            }
        } else if (type == SceneType.GRAVEYARD) {
            g.setPaint(new GradientPaint(0, 0, new Color(42, 48, 60), 0, height, new Color(45, 70, 62)));
            g.fillRect(0, 0, width, height);
            g.setColor(new Color(28, 40, 38));
            g.fillRect(0, height - 120, width, 120);
            g.setColor(new Color(138, 142, 136));
            for (int x = 45; x < width; x += 95) {
                int top = height - 185 + (x % 3) * 14;
                g.fillRoundRect(x, top, 42, 86, 18, 18);
                g.setColor(new Color(80, 86, 84));
                g.drawLine(x + 10, top + 28, x + 32, top + 28);
                g.drawLine(x + 21, top + 16, x + 21, top + 48);
                g.setColor(new Color(138, 142, 136));
            }
            g.setColor(new Color(225, 218, 174, 160));
            g.fillOval(width - 160, 55, 86, 86);
        } else if (type == SceneType.RIVER) {
            g.setPaint(new GradientPaint(0, 0, new Color(55, 91, 108), 0, height, new Color(55, 116, 86)));
            g.fillRect(0, 0, width, height);
            g.setColor(new Color(46, 92, 112));
            g.fillPolygon(new int[] { -80, width / 2 - 80, width / 2 + 85, width + 120 },
                    new int[] { height, height - 190, height - 190, height }, 4);
            g.setColor(new Color(120, 175, 190, 95));
            for (int y = height - 170; y < height - 20; y += 32) {
                g.drawArc(width / 2 - 155, y, 310, 24, 0, 180);
            }
            g.setColor(new Color(75, 84, 68));
            for (int x = 35; x < width; x += 135) {
                g.fillOval(x, height - 230 + (x % 2) * 20, 78, 32);
            }
            g.setColor(new Color(58, 42, 30));
            g.fillRect(width / 2 - 150, height - 212, 300, 14);
            for (int x = width / 2 - 135; x <= width / 2 + 135; x += 45) {
                g.fillRect(x, height - 230, 9, 48);
            }
        } else if (type == SceneType.RUINS) {
            g.setPaint(new GradientPaint(0, 0, new Color(47, 56, 54), 0, height, new Color(79, 91, 69)));
            g.fillRect(0, 0, width, height);
            g.setColor(new Color(130, 128, 110));
            for (int x = 55; x < width; x += 160) {
                g.fillRect(x, height - 220, 45, 145);
                g.fillRect(x - 20, height - 230, 85, 20);
            }
            g.setColor(new Color(230, 190, 85, 160));
            g.fillOval(width / 2 - 32, height - 180, 64, 38);
        } else if (type == SceneType.ACADEMY) {
            g.setPaint(new GradientPaint(0, 0, new Color(36, 43, 78), 0, height, new Color(92, 78, 110)));
            g.fillRect(0, 0, width, height);
            g.setColor(new Color(80, 70, 104));
            g.fillRect(0, height - 145, width, 145);
            g.setColor(new Color(46, 48, 78));
            for (int x = 30; x < width; x += 150) {
                g.fillRoundRect(x, height - 245, 82, 135, 8, 8);
                g.setColor(new Color(238, 210, 120, 95));
                g.fillOval(x + 15, height - 223, 52, 76);
                g.setColor(new Color(46, 48, 78));
            }
            g.setColor(new Color(235, 220, 145, 150));
            for (int i = 0; i < 16; i++) {
                int x = (i * 97 + 65) % Math.max(1, width);
                int y = 45 + ((i * 53) % Math.max(1, height - 210));
                g.fillOval(x, y, 5, 5);
            }
        } else if (type == SceneType.MARKET) {
            g.setPaint(new GradientPaint(0, 0, new Color(39, 63, 75), 0, height, new Color(112, 86, 65)));
            g.fillRect(0, 0, width, height);
            g.setColor(new Color(50, 46, 48));
            g.fillRect(0, height - 78, width, 78);
            for (int x = 38; x < width; x += 170) {
                g.setColor(new Color(126, 48, 56));
                g.fillPolygon(new int[] { x, x + 62, x + 124 }, new int[] { height - 180, height - 225, height - 180 }, 3);
                g.setColor(new Color(156, 108, 62));
                g.fillRect(x + 8, height - 180, 108, 92);
                g.setColor(new Color(245, 204, 104, 165));
                g.fillOval(x + 48, height - 168, 24, 24);
            }
            g.setColor(new Color(248, 220, 140, 135));
            for (int x = 20; x < width; x += 64) {
                g.fillOval(x, 82 + (x % 3) * 10, 10, 10);
            }
        } else if (type == SceneType.BATTLEFIELD) {
            g.setPaint(new GradientPaint(0, 0, new Color(69, 68, 72), 0, height, new Color(92, 73, 54)));
            g.fillRect(0, 0, width, height);
            g.setColor(new Color(54, 58, 50));
            g.fillRect(0, height - 120, width, 120);
            for (int x = 50; x < width; x += 135) {
                g.setColor(new Color(88, 30, 38));
                g.fillPolygon(new int[] { x, x + 52, x }, new int[] { height - 270, height - 247, height - 224 }, 3);
                g.setColor(new Color(42, 38, 34));
                g.fillRect(x - 4, height - 272, 8, 165);
            }
            g.setColor(new Color(35, 35, 35, 75));
            for (int x = -50; x < width; x += 160) {
                g.fillOval(x, height - 185, 220, 85);
            }
        } else if (type == SceneType.TEMPLE) {
            g.setPaint(new GradientPaint(0, 0, new Color(38, 50, 78), 0, height, new Color(82, 88, 83)));
            g.fillRect(0, 0, width, height);
            g.setColor(new Color(185, 178, 150));
            for (int x = 70; x < width; x += 150) {
                g.fillRect(x, height - 280, 44, 210);
                g.fillRect(x - 18, height - 292, 80, 16);
                g.fillRect(x - 10, height - 76, 64, 18);
            }
            g.setColor(new Color(222, 203, 130, 145));
            g.fillOval(width / 2 - 70, height - 230, 140, 58);
            g.setColor(new Color(238, 232, 190, 175));
            g.fillOval(width - 150, 55, 78, 78);
        } else if (type == SceneType.SWAMP) {
            g.setPaint(new GradientPaint(0, 0, new Color(30, 52, 55), 0, height, new Color(39, 67, 49)));
            g.fillRect(0, 0, width, height);
            g.setColor(new Color(22, 46, 45));
            g.fillRect(0, height - 135, width, 135);
            g.setColor(new Color(79, 110, 71));
            for (int x = 20; x < width; x += 48) {
                g.drawLine(x, height - 70, x + 12, height - 165);
                g.drawLine(x + 14, height - 75, x + 2, height - 150);
            }
            g.setColor(new Color(192, 222, 160, 85));
            for (int x = 80; x < width; x += 170) {
                g.fillOval(x, height - 190, 36, 36);
            }
        } else if (type == SceneType.DOCKS) {
            g.setPaint(new GradientPaint(0, 0, new Color(35, 64, 82), 0, height, new Color(27, 42, 55)));
            g.fillRect(0, 0, width, height);
            g.setColor(new Color(31, 58, 75));
            g.fillRect(0, height - 150, width, 150);
            g.setColor(new Color(95, 67, 45));
            for (int x = -20; x < width; x += 95) {
                g.fillRect(x, height - 125, 65, 14);
                g.fillRect(x + 24, height - 160, 12, 95);
            }
            g.setColor(new Color(72, 48, 36));
            for (int x = 90; x < width; x += 250) {
                g.fillArc(x, height - 170, 125, 62, 180, 180);
                g.fillRect(x + 55, height - 245, 7, 76);
                g.setColor(new Color(214, 197, 145, 150));
                g.fillPolygon(new int[] { x + 62, x + 62, x + 112 }, new int[] { height - 242, height - 178, height - 190 }, 3);
                g.setColor(new Color(72, 48, 36));
            }
        } else if (type == SceneType.MOUNTAIN) {
            g.setPaint(new GradientPaint(0, 0, new Color(52, 73, 94), 0, height, new Color(101, 112, 112)));
            g.fillRect(0, 0, width, height);
            g.setColor(new Color(82, 86, 86));
            g.fillPolygon(new int[] { -80, 170, 420 }, new int[] { height, 100, height }, 3);
            g.setColor(new Color(108, 112, 109));
            g.fillPolygon(new int[] { 250, 560, 920 }, new int[] { height, 55, height }, 3);
            g.setColor(new Color(218, 224, 218, 170));
            g.fillPolygon(new int[] { 515, 560, 610 }, new int[] { 130, 55, 130 }, 3);
            g.setColor(new Color(94, 62, 42));
            g.fillRect(0, height - 96, width, 18);
            for (int x = 25; x < width; x += 70) {
                g.fillRect(x, height - 125, 10, 78);
            }
        } else {
            g.setPaint(new GradientPaint(0, 0, new Color(28, 29, 38), 0, height, new Color(58, 67, 75)));
            g.fillRect(0, 0, width, height);
            g.setColor(new Color(230, 220, 165, 150));
            for (int i = 0; i < 22; i++) {
                int x = (i * 89 + 40) % Math.max(1, width);
                int y = 45 + ((i * 47) % Math.max(1, height - 140));
                g.fillOval(x, y, 4, 4);
            }
        }
    }

    private void paintAtmosphere(Graphics2D g, SceneType type) {
        int width = getWidth();
        int height = getHeight();
        g.setPaint(new GradientPaint(0, 0, new Color(0, 0, 0, 35), 0, height, new Color(0, 0, 0, 140)));
        g.fillRect(0, 0, width, height);

        if (type == SceneType.ENDING) {
            g.setColor(new Color(250, 232, 160, 70));
            g.fillOval(width / 2 - 220, height / 2 - 180, 440, 320);
        }
    }

    private void paintIntroParty(Graphics2D g) {
        int startX = Math.max(70, getWidth() / 2 - 310);
        int y = getHeight() - 290;
        int gap = 122;
        Job[] jobs = Job.values();
        for (int i = 0; i < jobs.length; i++) {
            paintCharacter(g, jobs[i], startX + i * gap, y + (i % 2) * 18, 0.64);
        }
    }

    private void paintTextPanel(Graphics2D g) {
        int panelX = 36;
        int panelY = 36;
        int panelWidth = Math.min(560, getWidth() - 72);
        int panelHeight = lastResult == null ? 210 : 270;

        g.setColor(new Color(18, 22, 28, 210));
        g.fillRoundRect(panelX, panelY, panelWidth, panelHeight, 8, 8);
        g.setColor(new Color(230, 220, 190, 130));
        g.setStroke(new BasicStroke(2f));
        g.drawRoundRect(panelX, panelY, panelWidth, panelHeight, 8, 8);

        String title = node == null ? "Choose Your Fate" : node.getTitle();
        String body = node == null
                ? "Pick a job, then survive a branching RPG story where each decision changes your stats, allies, enemies, and final ending."
                : node.getBody();

        g.setColor(new Color(246, 231, 184));
        g.setFont(new Font("Serif", Font.BOLD, 30));
        g.drawString(title, panelX + 24, panelY + 45);

        g.setFont(new Font("SansSerif", Font.PLAIN, 16));
        g.setColor(new Color(238, 239, 232));
        drawWrappedText(g, body, panelX + 24, panelY + 78, panelWidth - 48, 23);

        if (lastResult != null) {
            g.setColor(new Color(125, 210, 190));
            g.setFont(new Font("SansSerif", Font.BOLD, 14));
            g.drawString("Last choice:", panelX + 24, panelY + 180);
            g.setColor(new Color(225, 238, 232));
            g.setFont(new Font("SansSerif", Font.PLAIN, 14));
            drawWrappedText(g, lastResult, panelX + 24, panelY + 202, panelWidth - 48, 20);
        }
    }

    private void paintCharacter(Graphics2D g, Job job, int x, int y, double scale) {
        AffineTransform oldTransform = g.getTransform();
        g.translate(x, y);
        g.scale(scale, scale);

        Color primary = job.getPrimaryColor();
        Color accent = job.getAccentColor();

        g.setColor(new Color(0, 0, 0, 95));
        g.fillOval(14, 218, 110, 24);

        g.setColor(primary.darker());
        g.fillRoundRect(42, 78, 54, 122, 24, 24);
        g.setColor(primary);
        g.fillRoundRect(34, 92, 70, 118, 18, 18);
        g.setColor(new Color(226, 181, 132));
        g.fillOval(43, 35, 50, 52);
        g.setColor(new Color(38, 30, 32));
        g.fillArc(39, 28, 58, 42, 0, 180);
        g.setColor(Color.BLACK);
        g.fillOval(56, 57, 4, 4);
        g.fillOval(76, 57, 4, 4);
        g.drawArc(58, 67, 21, 8, 190, 160);

        g.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(new Color(226, 181, 132));
        g.drawLine(38, 112, 13, 157);
        g.drawLine(100, 112, 124, 157);
        g.drawLine(55, 198, 47, 232);
        g.drawLine(82, 198, 92, 232);

        g.setColor(accent);
        g.setStroke(new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        if (job == Job.MAGE) {
            g.fillPolygon(new int[] { 43, 69, 95 }, new int[] { 42, -8, 42 }, 3);
            g.drawLine(123, 78, 123, 206);
            g.fillOval(113, 58, 20, 20);
            g.setColor(new Color(180, 225, 255, 180));
            g.fillOval(107, 52, 32, 32);
        } else if (job == Job.KNIGHT) {
            g.fillRoundRect(102, 118, 36, 52, 12, 12);
            g.setColor(new Color(250, 250, 255, 170));
            g.drawLine(13, 78, 13, 188);
            g.drawLine(4, 98, 22, 98);
            g.setColor(new Color(210, 220, 230));
            g.drawArc(43, 32, 50, 36, 0, 180);
        } else if (job == Job.ROGUE) {
            g.drawLine(18, 135, 2, 177);
            g.drawLine(116, 135, 134, 177);
            g.setColor(new Color(25, 30, 35, 190));
            g.fillArc(37, 29, 63, 65, 0, 180);
        } else if (job == Job.THIEF) {
            g.fillOval(107, 154, 36, 44);
            g.drawLine(42, 105, 95, 105);
            g.setColor(new Color(40, 32, 26, 210));
            g.fillArc(37, 29, 63, 65, 0, 180);
            g.setColor(accent);
            g.fillOval(113, 160, 7, 7);
            g.fillOval(127, 160, 7, 7);
        } else if (job == Job.ARCHER) {
            g.drawArc(105, 75, 42, 112, 270, 180);
            g.drawLine(126, 80, 126, 190);
            g.drawLine(11, 150, 92, 118);
            g.fillPolygon(new int[] { 11, 25, 23 }, new int[] { 150, 145, 156 }, 3);
        }

        g.setTransform(oldTransform);
    }

    private void drawWrappedText(Graphics2D g, String text, int x, int y, int maxWidth, int lineHeight) {
        FontMetrics metrics = g.getFontMetrics();
        List<String> lines = wrap(text, metrics, maxWidth);
        for (int i = 0; i < lines.size(); i++) {
            g.drawString(lines.get(i), x, y + i * lineHeight);
        }
    }

    private List<String> wrap(String text, FontMetrics metrics, int maxWidth) {
        List<String> lines = new ArrayList<String>();
        String[] words = text.split(" ");
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String next = current.length() == 0 ? words[i] : current.toString() + " " + words[i];
            if (metrics.stringWidth(next) <= maxWidth) {
                current = new StringBuilder(next);
            } else {
                if (current.length() > 0) {
                    lines.add(current.toString());
                }
                current = new StringBuilder(words[i]);
            }
        }

        if (current.length() > 0) {
            lines.add(current.toString());
        }

        return lines;
    }
}
