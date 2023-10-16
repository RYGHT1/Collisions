import javax.swing.*;
import java.awt.*;

public class BallPanel extends JPanel implements Runnable {
    Thread simulationThread;
    public static final int PARTICLE_COUNT = 100;
    Particle[] particles;
    Graphics2D g2d;

    BallPanel() {
        particles = new Particle[PARTICLE_COUNT];
        spawnBalls();

        simulationThread = new Thread(this);
        simulationThread.start();
    }

    private void spawnBalls() {
        int asd = (int) ((Math.sqrt(PARTICLE_COUNT)+1));
        int count = 0;



        for (int i = 0; i < PARTICLE_COUNT; i++) {
            int x = (int) (((count%asd)*CollisionWindow.SIZE/asd)+Particle.MAX_SIZE/2);
            int y = (int) (((count/asd)*CollisionWindow.SIZE/asd)+Particle.MAX_SIZE/2);
            particles[i] = new Particle(x,y);
            count++;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(0x212121));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        for (Particle p : particles) {
            drawParticle(p);
        }
    }

    private void drawParticle(Particle p) {
        g2d.setPaint(p.color);
        g2d.fillOval((int) p.getTopLeftX(), (int) p.getTopLeftY(), (int) p.size, (int) p.size);
        g2d.setPaint(p.color.darker());
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval((int) p.getTopLeftX(), (int) p.getTopLeftY(), (int) p.size, (int) p.size);
        g2d.setStroke(new BasicStroke(1));
    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
            System.out.println("sleeping");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long timeOld = System.nanoTime();
        double amountOfTicks = 144.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while (true) {
            long now = System.nanoTime();
            delta += (now - timeOld) / ns;
            timeOld = now;
            if (delta >= 1) {
                delta--;
                updateParticles();
                repaint();

            }
        }
    }

    private void updateParticles() {
        for (int i = 0; i < PARTICLE_COUNT; i++) {
            particles[i].moved = false;
            keepBounded(particles[i]);
            this.particles[i].x += this.particles[i].velX;
            this.particles[i].y += this.particles[i].velY;
        }

        for (int i = 0; i < PARTICLE_COUNT - 1; i++) {
            for (int j = i + 1; j < PARTICLE_COUNT; j++) {
                checkCollisions(particles[i], particles[j]);
            }
        }
    }

    private void checkCollisions(Particle p1, Particle p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance < p1.size / 2 + p2.size / 2) {
            Vector2 dir = new Vector2(dx, dy).scale(1 / distance);
            double relativeVelocityX = p1.velX - p2.velX;
            double relativeVelocityY = p1.velY - p2.velY;
            double dotProduct = relativeVelocityX * dir.x + relativeVelocityY * dir.y;

            double impulse = (2.0 * dotProduct) / (2);

            p1.velX -= (float) (impulse * dir.x);
            p1.velY -= (float) (impulse * dir.y);
            p2.velX += (float) (impulse * dir.x);
            p2.velY += (float) (impulse * dir.y);
        }

    }


    private void keepBounded(Particle p) {
        if (p.x - p.size / 2 <= 0 || p.x + p.size / 2 >= getWidth()) {
            p.velX = -p.velX;
        }
        if (p.y - p.size / 2 <= 0 || p.y + p.size / 2 >= getHeight()) {
            p.velY = -p.velY;
        }

    }
}
