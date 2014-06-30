import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ian on 29-06-2014.
 */
public class MainTestWindow {
    List<GeoRef> points;
    List<GeoRef> path;
    GeoRef selected;
    Algorithm algorithm = new HeuristicConvexHull();
    JFrame jframe;

    public MainTestWindow() {
        points = new ArrayList<GeoRef>();
    }

    public void start() {
        jframe = new MyJFrame();
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(500, 500);
        jframe.setVisible(true);
    }

    public static void main(String[] args) {
        new MainTestWindow().start();
    }

    class MyJFrame extends JFrame {
        BufferedImage bi;

        public MyJFrame() {
            bi = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
            //setBackground(Color.white);
            MyMouseAdapter mAdapter = new MyMouseAdapter();
            addMouseListener(mAdapter);
            addMouseMotionListener(mAdapter);
        }

        @Override
        public void paintComponents(Graphics g) {
            super.paintComponents(g);
        }

        @Override
        public void paint(Graphics jframeg) {
            //super.paint(g);
            Graphics g = bi.getGraphics();
            g.setColor(Color.white);
            g.fillRect(0, 0, 500, 500);

            for (int i = 0; i < points.size(); i++) {

                GeoRef p = points.get(i);

                if (p == selected) {
                    g.setColor(Color.red);
                } else {
                    g.setColor(Color.black);
                }

                if (i==0){
                    g.setColor(Color.blue);
                }

                int radius = 10;
                g.fillArc((int) p.getX() - radius / 2, (int) p.getY() - radius / 2, radius, radius, 0, 360);
            }
            g.setColor(Color.green);
            if (points.size() >= 3) {
                algorithm.loadData(points);
                algorithm.run();
                for (int i = 0; i < algorithm.conjunct.size(); i++) {
                    GeoRef p = algorithm.conjunct.get(i);
                    GeoRef q = algorithm.conjunct.get((i + 1) % algorithm.conjunct.size());

                    if (i == 0) {
                        g.setColor(Color.blue);
                        g.drawLine((int) p.getX(), (int) p.getY(), (int) q.getX(), (int) q.getY());
                        g.setColor(Color.green);
                    } else {
                        g.drawLine((int) p.getX(), (int) p.getY(), (int) q.getX(), (int) q.getY());
                    }
                    char[] ar = ("L:"+p.distance(q)).toCharArray();
                    g.drawChars(ar,0,ar.length,(int) p.getX(), (int) p.getY());
                }
            }
            jframeg.drawImage(bi, 0, 0, null);
        }
    }

    class MyMouseAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            System.out.println("pressed");
            GeoRef mp = new GeoRef(0, e.getX(), e.getY());
            double distance = 10;
            for (GeoRef p : points) {
                if ((p.distance(mp) < distance)) {
                    selected = p;
                    distance = selected.distance(mp);
                }
            }
            if (selected != null && e.getButton() == 3) {
                points.remove(selected);
                jframe.repaint();
                return;
            }
            if (selected == null) {
                System.out.println("new point");
                selected = new GeoRef(e.getX(), e.getY());
                points.add(selected);
                jframe.repaint();
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (selected != null) {
                selected.setX(e.getX());
                selected.setY(e.getY());
                jframe.repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            System.out.println("released");
            selected = null;
            jframe.repaint();
        }
    }
}
