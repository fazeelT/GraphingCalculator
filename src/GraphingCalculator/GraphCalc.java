package GraphingCalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GraphCalc extends JFrame {

    class DisplayPanel extends JPanel {

        DisplayPanel() {
            this.setPreferredSize(new Dimension(130, 100));
            this.setBackground(Color.cyan);

        }
    }

    class CalcPanel extends JPanel {

        String ops = "789+456-123*0.±/";

        public CalcPanel() {
            Font fnt = new Font("Segoe", Font.PLAIN, 12);
            JButton jb;
            setLayout(new GridLayout(4, 4, -2, -2));

            for (int i = 0; i < ops.length(); i++) {
                add(jb = new JButton(String.valueOf(ops.charAt(i))));
                // jb.setFont(fnt);
            }

        }
    }

    class GraphPanel extends JPanel {

        Tree tree = new Tree();
        javax.swing.Timer timer;
        float t;

        GraphPanel() {
            timer = new javax.swing.Timer(100, new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    t += 0.100f;
                    GraphPanel.this.repaint();
                }
            });
            tree.parse("x");
        }

        void animate() {
            if (timer.isRunning()) {
                timer.stop();
                t = 0;
                return;
            }
            timer.start();
        }

        public void setEqn(String eqn) {
            tree.parse(eqn);
        }

        float calc(float x) {
            return tree.calc(x + t);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();
            g.setColor(Color.cyan);
            g.drawLine(10, h / 2, w - 10, h / 2);
            g.drawLine(w / 2, 10, w / 2, h - 10);
            BasicStroke bs = new BasicStroke(2.0f);
            g2d.setStroke(bs);

            float bounds = w / 80.0f;
            float x = -bounds;
            float dx = 1 / 40.0f;
            float y = calc(x);
            g.setColor(Color.RED);
            int oldgrx = Math.round(x * 40 + w / 2.0f);
            int oldgry = Math.round(h / 2.0f - y * 40);

            while (x < bounds) {
                x += dx;
                y = calc(x);
                int grx = Math.round(x * 40 + w / 2.0f);
                int gry = Math.round(h / 2.0f - y * 40);
                //g.setcolor(Color.BLACK);
                g.drawLine(oldgrx, oldgry, grx, gry);
                oldgrx = grx;
                oldgry = gry;

            }

        }

    }

    GraphCalc() {

        super("Graphic Calculator");
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        final GraphPanel gp = new GraphPanel();
        final CalcPanel cp = new CalcPanel();
        cp.setPreferredSize(new Dimension(180, 180));
        JPanel buttons = new JPanel();
        JButton jb = new JButton("Graph");
        jb.setToolTipText("Graphs an Equation");
        jb.setPreferredSize(new Dimension(80, 70));
        buttons.add(jb);
        jb.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(GraphCalc.this,
                        "Enter the equation to Graph");
                if (input != null) {
                    gp.setEqn(input);
                    repaint();
                }
            }
        });

        jb = new JButton("Anim");
        jb.setToolTipText("Animates the graph");
        jb.setToolTipText("Graphs an Equation");
        jb.setPreferredSize(new Dimension(80, 70));
        buttons.add(jb);
        jb.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                gp.animate();

            }
        });

        jp.add(new DisplayPanel(), BorderLayout.NORTH);

        jp.add(buttons, BorderLayout.CENTER);

        jp.add(cp, BorderLayout.SOUTH);

        add(jp, BorderLayout.EAST);

        jp.setBorder(BorderFactory.createLineBorder(Color.gray, 2));

        add(gp);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 400);
        setVisible(true);

    }

    public static void main(String[] args) {
        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
        } catch (ClassNotFoundException e) {
            // handle exception
        } catch (InstantiationException e) {
            // handle exception
        } catch (IllegalAccessException e) {
            // handle exception
        }

        new GraphCalc();
    }
}
