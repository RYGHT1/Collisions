import java.awt.*;
import java.util.Random;

public class Particle {
    public Color color;
    public float size;
    private final static float MIN_SIZE = 10f;
    public final static float MAX_SIZE = 30f;
    public float x, y;
    public float velX, velY;
    Random random = new Random();
    public float radius;
    public boolean moved;


    Particle(int x, int y) {
        this.x = x;
        this.y = y;
        setColor();
        setStartingVelocity();
    }

    private void setStartingVelocity() {
        this.velX = (this.size - MIN_SIZE - (MAX_SIZE - MIN_SIZE) / 2) % 0.42f;
        this.velY = ((((this.size + MAX_SIZE - MIN_SIZE) / 2) % MAX_SIZE - MIN_SIZE) - MIN_SIZE - (MAX_SIZE - MIN_SIZE) / 2) % 0.42f;
    }
    private void setColor() {
        setSize();

        float relativeRange = (MAX_SIZE - MIN_SIZE);
        float relativeVal = this.size - MIN_SIZE;
        HSLColor hsl = new HSLColor(360 * (relativeRange / relativeVal), 100, 50);

        this.color = (hsl.getRGB());
    }

    private void setSize() {
        this.size = random.nextFloat(MIN_SIZE, MAX_SIZE);
        this.radius = this.size/2;
    }

    public float getTopLeftX() {
        return x - size / 2;
    }

    public float getTopLeftY() {
        return y - size / 2;
    }
}
