import java.util.ArrayList;
import java.util.List;

public class Memory {

    private List<StoredCell> memCells;
    private List<StoredCell> plasCells;

    public List<StoredCell> getMemCells() {
        return memCells;
    }

    public List<StoredCell> getPlasCells() {
        return plasCells;
    }

    public int plasmaSize() {
        return plasCells.size();
    }

    public int memorySize() {
        return memCells.size();
    }

    public Memory() {
        this.memCells = new ArrayList<>();
        this.plasCells = new ArrayList<>();
    }

    public void addPlasma(Bcell cell, int iteration) {
        plasCells.add(new StoredCell(cell, iteration, "Plasma"));
    }

    public void addMemory(Bcell cell, int iteration) {
        memCells.add(new StoredCell(cell, iteration, "Memory"));
    }

    public static double avg(List<StoredCell> cells) {

        if (cells.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (int i = 0; i < cells.size(); i++) {
            sum += cells.get(i).getAffinity();
        }
        return sum / cells.size();
    }

    public void printMetaData() {
        for (int i = 0; i < plasCells.size(); i++) {
            System.out.println("Type: Plasma" + " | Affinity: " + plasCells.get(i).getAffinity() + " | Iteration = " + plasCells.get(i).getIteration());
        }
        System.out.println("------");
        for (int i = 0; i < memCells.size(); i++) {
            System.out.println("Type: Memory" + " | Affinity: " + memCells.get(i).getAffinity() + " | Iteration = " + memCells.get(i).getIteration());
        }
        
    }

}