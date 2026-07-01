public class StoredCell {

    private Bcell cell;
    private int iteration;
    private String type;

    public StoredCell(Bcell cell, int iteration, String type) {
        this.cell = cell.cloner();
        this.iteration = iteration;
        this.type = type;
    }

    public Bcell getCell() {
        return cell;
    }

    public double getAffinity() {
        return cell.getAffinity();
    }

    public int getIteration() {
        return iteration;
    }

    public String getType() {
        return type;
    }
}