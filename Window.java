import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Window extends JFrame {

    private DrawArea drawArea = new DrawArea();

    public Window() {
        super();

        setLayout(new BorderLayout());

        add(createMenuBar(), BorderLayout.NORTH);
        add(drawArea, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));

    }

    private JMenuBar createMenuBar() {
        JMenuBar bar = new JMenuBar();

        JButton clear = new JButton("Clear");
        clear.addActionListener(evt -> {
            drawArea.clear();
        });
        bar.add(clear);

        JButton capture = new JButton("Capture");
        capture.addActionListener(evt -> {
            drawArea.getImage();
        });
        bar.add(capture);

        return bar;
    }

    static class Cell extends JLabel implements MouseListener {
        static boolean mousePressed = false;

        int x;
        int y;

        Cell(int index) {
            super();

            x = index % 32;
            y = index / 32;

            setBackground(Color.WHITE);
            setOpaque(true);
            setPreferredSize(new Dimension(48, 48));
            setMaximumSize(new Dimension(48, 48));

            addMouseListener(this);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mousePressed = true;
            setBackground(Color.BLACK);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mousePressed = false;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (mousePressed)
                setBackground(Color.BLACK);
            else if (getBackground() != Color.BLACK)
                setBackground(Color.LIGHT_GRAY);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!mousePressed && getBackground() != Color.BLACK)
                setBackground(Color.WHITE);

        }
    }

    class DrawArea extends JPanel {
        int SIDE_DIM = 32;

        DrawArea() {
            super();

            setLayout(new GridLayout(32, 32));

            setBackground(Color.BLUE);

            for (int i = 0; i < SIDE_DIM * SIDE_DIM; i++) {
                add(new Cell(i));
            }
        }

        public void clear() {
            for (java.awt.Component cell : getComponents()) {
                Cell c = (Window.Cell) cell;
                c.setBackground(Color.WHITE);
            }
        }

        public BufferedImage getImage() {
            BufferedImage img = new BufferedImage(SIDE_DIM, SIDE_DIM, BufferedImage.TYPE_BYTE_GRAY);

            for (java.awt.Component cell : getComponents()) {
                Cell c = (Window.Cell) cell;
                img.setRGB(c.x, c.y, c.getBackground().getRGB());
            }

            File out = new File("C:\\Users\\utente\\Documents\\Java\\DigitIdentifier\\DigitIdentifier\\src\\image.jpg");
            try {
                ImageIO.write(img, "jpg", out);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return img;
        }
    }
}
