package sierpinski;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;

class Triangle extends JFrame {

    int counter;
    static List<ColorPoint> points;
    static Color pointColor;
    static JSlider pointSize;
    static JSlider speedThread;
    static DrawingPane pane;

    List<Integer> massiveX = new CopyOnWriteArrayList<>();
    List<Integer> massiveY = new CopyOnWriteArrayList<>();

    JLabel jcounter = new JLabel(String.valueOf(0));

    void count() {
        String str;
        counter += 1;
        str = Integer.toString(counter);
        jcounter.setText(str);
    }

    StartGame startGame;

    Triangle() {
        setTitle("Sierpinski Triangle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        points = new CopyOnWriteArrayList<>();
        pointColor = Color.BLACK;
        pane = new DrawingPane();

        JPanel topPanel = new JPanel();
        JButton colorButton = new JButton();
        colorButton.setBackground(pointColor);
        colorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pointColor = JColorChooser.showDialog(Triangle.this,
                        "Choose a color:", pointColor);
                colorButton.setBackground(pointColor);
            }
        });

        colorButton.setPreferredSize(new Dimension(40, 40));
        pointSize = new JSlider(3, 20, 3);
        speedThread = new JSlider(1, 20, 1);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (massiveX.size() < 4) {
                    JOptionPane.showMessageDialog(null,
                            "Set the required number of points!",
                            "Error!",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    startGame = new StartGame();
                    Thread thr = new Thread(startGame);
                    thr.start();
                }
            }
        });

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame.stop();
                points.clear();
                pane.repaint();
                massiveX.clear();
                massiveY.clear();
            }
        });

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame.suspended();
            }
        });

        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame.resume();
            }
        });

        topPanel.add(new JLabel("Color:"));
        topPanel.add(colorButton);
        topPanel.add(new JLabel("Point size:"));
        topPanel.add(pointSize);
        topPanel.add(new JLabel("Speed:"));
        topPanel.add(speedThread);
        topPanel.add(startButton);
        topPanel.add(stopButton);
        topPanel.add(pauseButton);
        topPanel.add(continueButton);
        topPanel.add(new JLabel("Number of points"));
        topPanel.add(jcounter);
        add(pane);
        add(topPanel, BorderLayout.NORTH);
        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Triangle().setVisible(true);
            }
        });
    }

    class DrawingPane extends JComponent {
        public DrawingPane() {
            setPreferredSize(new Dimension(1200, 400));
            setBorder(new LineBorder(Color.GRAY));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (points.size() < 4) {
                        ColorPoint point = new ColorPoint(e.getX(), e.getY(), pointSize.getValue(), pointColor);
                        points.add(point);
                        massiveX.add(e.getX());
                        massiveY.add(e.getY());
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "You cannot create more than 4 points!",
                                "Error!",
                                JOptionPane.WARNING_MESSAGE);
                    }
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for (ColorPoint p : points) {
                g2.setColor(p.getColor());
                int x = p.getX();
                int y = p.getY();
                int size = p.getSize();
                g2.fillOval(x - size / 2, y - size / 2, size, size);
            }
        }
    }

    static class ColorPoint {
        private final int x;
        private final int y;
        private final int size;
        private final Color color;

        public ColorPoint(int x, int y, int size, Color color) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.color = color;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Color getColor() {
            return color;
        }

        public int getSize() {
            return size;
        }
    }

    static class RandomPoint {
        int min;
        int max;

        static int rnd(int min, int max) {
            max -= min;
            return (int) (Math.random() * ++max) + min;
        }
    }

    class StartGame extends JComponent implements Runnable {

        volatile boolean suspended;
        volatile boolean stopped;

        synchronized void suspended() {
            suspended = true;
        }

        synchronized void resume() {
            suspended = false;
            notify();
        }

        synchronized void stop() {
            stopped = true;
            counter = -1;
        }

        StartGame() {
            suspended = false;
            stopped = false;
        }

        @Override
        public void run() {

            int n_point_x;
            int n_point_y;

            try {
                for (int i = 0; i < 50000; i++) {
                    count();
                    synchronized (this) {
                        while (suspended) {
                            JOptionPane.showMessageDialog(null,
                                    "The stream is paused!",
                                    "Message!",
                                    JOptionPane.WARNING_MESSAGE);
                            wait();
                        }
                        if (stopped) break;
                    }
                    int next_point = RandomPoint.rnd(0, 2);
                    n_point_x = (massiveX.get(3) + massiveX.get(next_point)) / 2;
                    n_point_y = (massiveY.get(3) + massiveY.get(next_point)) / 2;
                    massiveX.remove(3);
                    massiveY.remove(3);
                    massiveX.add(n_point_x);
                    massiveY.add(n_point_y);
                    ColorPoint point = new Triangle.ColorPoint(n_point_x, n_point_y, Triangle.pointSize.getValue(), Triangle.pointColor);
                    points.add(point);
                    pane.repaint();
                    Thread.sleep(Triangle.speedThread.getValue() * 5);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null,
                    "The program has finished working!",
                    "Message!",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}

