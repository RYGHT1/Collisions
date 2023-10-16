public class Vector2 {
    public double x;
    public double y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 scale(double factor) {
        return new Vector2(this.x * factor, this.y * factor);
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

}