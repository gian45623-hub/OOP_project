package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.Component;
import java.awt.Dimension;

import util.InputHandler;

public class GameWindow extends JFrame {

    private static GameWindow instance;

    private JTextArea textArea;
    private JPanel buttonPanel;
    private GameCanvas canvas;

    private final Color fgColor = new Color(220, 220, 220); // Off-white text
    private final Color buttonBg = new Color(240, 240, 235); // Off-white button
    private final Color buttonFg = new Color(20, 20, 20); // Dark text

    public GameWindow() {
        setTitle("Aethermoor");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Custom Canvas for Background and Sprites
        canvas = new GameCanvas();
        canvas.setLayout(null); // Absolute layout for floating text box

        // Floating Text Area
        textArea = new JTextArea() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(15, 20, 20, 200)); // Semi-transparent dark overlay
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        textArea.setEditable(false);
        textArea.setForeground(fgColor);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false); 

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 2));
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.setBounds(30, 30, 600, 350); // Floating top-left
        
        canvas.add(scrollPane);
        add(canvas, BorderLayout.CENTER);

        // Stacked Button Panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(15, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(buttonPanel, BorderLayout.SOUTH);

        instance = this;

        // Redirect System.out
        redirectSystemOut();
    }

    private class GameCanvas extends JPanel {
        private BufferedImage bgImage;
        private BufferedImage mageImage;
        private BufferedImage knightImage;
        private BufferedImage priestImage;

        public GameCanvas() {
            bgImage = loadImage("pixel_bg.png", "pixel_bg.jpg", "pixel_bg.jpeg");
            mageImage = loadSprite("pixel_mage_test.png", "pixel_mage.png");
            knightImage = loadSprite("pixel_knight.png", "pixel_knight_test.png");
            priestImage = loadSprite("pixel_priest.png", "pixel_priest_test.png");
        }

        private BufferedImage loadImage(String... names) {
            for (String name : names) {
                try {
                    URL resource = GameWindow.class.getResource("/assets/" + name);
                    if (resource != null) {
                        return ImageIO.read(resource);
                    }

                    File srcFile = new File("src/assets/" + name);
                    if (srcFile.exists()) {
                        return ImageIO.read(srcFile);
                    }

                    File assetFile = new File("assets/" + name);
                    if (assetFile.exists()) {
                        return ImageIO.read(assetFile);
                    }
                } catch (IOException e) {
                    System.err.println("Failed to load " + name + ": " + e.getMessage());
                }
            }

            System.err.println("Could not find asset: " + String.join(", ", names));
            return null;
        }

        private BufferedImage loadSprite(String transparentName, String fallbackName) {
            BufferedImage image = loadImage(transparentName, fallbackName);
            if (image == null) {
                return null;
            }

            BufferedImage sprite = hasTransparency(image) ? toArgb(image) : removeEdgeBackground(image);
            return trimTransparent(sprite);
        }

        private boolean hasTransparency(BufferedImage image) {
            if (!image.getColorModel().hasAlpha()) {
                return false;
            }

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    if (((image.getRGB(x, y) >> 24) & 0xFF) < 255) {
                        return true;
                    }
                }
            }
            return false;
        }

        private BufferedImage toArgb(BufferedImage image) {
            BufferedImage converted = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = converted.createGraphics();
            g2.drawImage(image, 0, 0, null);
            g2.dispose();
            return converted;
        }

        private BufferedImage removeEdgeBackground(BufferedImage im) {
            BufferedImage dest = new BufferedImage(im.getWidth(), im.getHeight(), BufferedImage.TYPE_INT_ARGB);
            int[][] corners = {{0,0}, {im.getWidth()-1, 0}, {0, im.getHeight()-1}, {im.getWidth()-1, im.getHeight()-1}};
            int bgR = 0;
            int bgG = 0;
            int bgB = 0;

            for (int[] corner : corners) {
                int rgb = im.getRGB(corner[0], corner[1]);
                bgR += (rgb >> 16) & 0xFF;
                bgG += (rgb >> 8) & 0xFF;
                bgB += rgb & 0xFF;
            }
            bgR /= corners.length;
            bgG /= corners.length;
            bgB /= corners.length;

            boolean[][] foreground = new boolean[im.getWidth()][im.getHeight()];
            for (int y = 0; y < im.getHeight(); y++) {
                for (int x = 0; x < im.getWidth(); x++) {
                    int rgb = im.getRGB(x, y);
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int b = rgb & 0xFF;
                    if (colorDistance(r, g, b, bgR, bgG, bgB) > 70) {
                        foreground[x][y] = true;
                    }
                }
            }

            for (int i = 0; i < 10; i++) {
                boolean[][] expanded = new boolean[im.getWidth()][im.getHeight()];
                for (int y = 0; y < im.getHeight(); y++) {
                    for (int x = 0; x < im.getWidth(); x++) {
                        if (foreground[x][y]) {
                            for (int ny = Math.max(0, y - 1); ny <= Math.min(im.getHeight() - 1, y + 1); ny++) {
                                for (int nx = Math.max(0, x - 1); nx <= Math.min(im.getWidth() - 1, x + 1); nx++) {
                                    expanded[nx][ny] = true;
                                }
                            }
                        }
                    }
                }
                foreground = expanded;
            }

            boolean[][] outsideBackground = new boolean[im.getWidth()][im.getHeight()];
            java.util.Queue<int[]> queue = new java.util.LinkedList<>();
            for (int x = 0; x < im.getWidth(); x++) {
                addOutsidePixel(queue, outsideBackground, foreground, x, 0);
                addOutsidePixel(queue, outsideBackground, foreground, x, im.getHeight() - 1);
            }
            for (int y = 0; y < im.getHeight(); y++) {
                addOutsidePixel(queue, outsideBackground, foreground, 0, y);
                addOutsidePixel(queue, outsideBackground, foreground, im.getWidth() - 1, y);
            }

            while (!queue.isEmpty()) {
                int[] pixel = queue.poll();
                int x = pixel[0];
                int y = pixel[1];
                addOutsidePixel(queue, outsideBackground, foreground, x - 1, y);
                addOutsidePixel(queue, outsideBackground, foreground, x + 1, y);
                addOutsidePixel(queue, outsideBackground, foreground, x, y - 1);
                addOutsidePixel(queue, outsideBackground, foreground, x, y + 1);
            }

            for (int y = 0; y < im.getHeight(); y++) {
                for (int x = 0; x < im.getWidth(); x++) {
                    dest.setRGB(x, y, outsideBackground[x][y] ? 0x00000000 : im.getRGB(x, y));
                }
            }
            return dest;
        }

        private void addOutsidePixel(java.util.Queue<int[]> queue, boolean[][] outsideBackground, boolean[][] foreground, int x, int y) {
            if (x < 0 || y < 0 || x >= outsideBackground.length || y >= outsideBackground[0].length) {
                return;
            }
            if (outsideBackground[x][y] || foreground[x][y]) {
                return;
            }

            outsideBackground[x][y] = true;
            queue.add(new int[]{x, y});
        }

        private int colorDistance(int r1, int g1, int b1, int r2, int g2, int b2) {
            int dr = r1 - r2;
            int dg = g1 - g2;
            int db = b1 - b2;
            return (int) Math.sqrt(dr * dr + dg * dg + db * db);
        }

        private BufferedImage trimTransparent(BufferedImage image) {
            int minX = image.getWidth();
            int minY = image.getHeight();
            int maxX = -1;
            int maxY = -1;

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int alpha = (image.getRGB(x, y) >> 24) & 0xFF;
                    if (alpha > 8) {
                        if (x < minX) minX = x;
                        if (x > maxX) maxX = x;
                        if (y < minY) minY = y;
                        if (y > maxY) maxY = y;
                    }
                }
            }

            if (maxX < minX || maxY < minY) {
                return image;
            }

            BufferedImage trimmed = new BufferedImage(maxX - minX + 1, maxY - minY + 1, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = trimmed.createGraphics();
            g2.drawImage(image, 0, 0, trimmed.getWidth(), trimmed.getHeight(), minX, minY, maxX + 1, maxY + 1, null);
            g2.dispose();
            return trimmed;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            g2.setColor(new Color(15, 20, 20));
            g2.fillRect(0, 0, getWidth(), getHeight());

            int panelWidth = getWidth();
            int panelHeight = getHeight();
            if (bgImage != null) {
                double scale = Math.max((double) panelWidth / bgImage.getWidth(), (double) panelHeight / bgImage.getHeight());
                int drawWidth = (int) Math.ceil(bgImage.getWidth() * scale);
                int drawHeight = (int) Math.ceil(bgImage.getHeight() * scale);
                int drawX = (panelWidth - drawWidth) / 2;
                int drawY = (panelHeight - drawHeight) / 2;
                g2.drawImage(bgImage, drawX, drawY, drawWidth, drawHeight, this);
            }

            int groundY = (int) (panelHeight * 0.88);
            int maxCharacterHeight = Math.max(150, Math.min(280, (int) (panelHeight * 0.36)));
            String selectedCharacter = System.getProperty("aethermoor.selectedCharacter");

            if (selectedCharacter == null) {
                drawCharacter(g2, mageImage, (int) (panelWidth * 0.28), groundY, maxCharacterHeight);
                drawCharacter(g2, knightImage, (int) (panelWidth * 0.50), groundY, maxCharacterHeight);
                drawCharacter(g2, priestImage, (int) (panelWidth * 0.72), groundY, maxCharacterHeight);
            } else {
                drawCharacter(g2, getCharacterImage(selectedCharacter), (int) (panelWidth * 0.50), groundY, maxCharacterHeight);
            }
            g2.dispose();
        }

        private BufferedImage getCharacterImage(String characterClass) {
            return switch (characterClass) {
                case "Mage" -> mageImage;
                case "Knight" -> knightImage;
                case "Priest" -> priestImage;
                default -> null;
            };
        }

        private void drawCharacter(Graphics2D g2, BufferedImage image, int centerX, int groundY, int maxHeight) {
            if (image == null) {
                return;
            }

            double scale = (double) maxHeight / image.getHeight();
            int width = (int) Math.round(image.getWidth() * scale);
            int height = (int) Math.round(image.getHeight() * scale);
            g2.drawImage(image, centerX - width / 2, groundY - height, width, height, this);
        }
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

    public void appendOutput(String text) {
        SwingUtilities.invokeLater(() -> {
            textArea.append(text);
            textArea.setCaretPosition(textArea.getDocument().getLength());
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
            for (int i = min; i <= max; i++) {
                JButton btn = createStyledButton("Choice " + i);
                final int choice = i;
                btn.addActionListener(e -> {
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
        btn.setFont(new Font("Consolas", Font.BOLD, 18));
        btn.setBackground(buttonBg);
        btn.setForeground(buttonFg);
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Short.MAX_VALUE, 50));
        btn.setPreferredSize(new Dimension(800, 50));
        return btn;
    }
}
