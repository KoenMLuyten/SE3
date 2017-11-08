package Domain.Infrastructure;

public class Section {

    private  int sectionID;
    private int numberOfBlocks;
    private int blockLenght;
    private int[] crossings;
    private boolean singleDirection;
    private int[] highBlocknumberSectionIds;

    public Section(int sectionID, int numberOfBlocks, int blockLenght, int[] crossings, boolean singleDirection, int[] highBlocknumberSectionIds){
        this.sectionID = sectionID;
        this.numberOfBlocks = numberOfBlocks;
        this.blockLenght = blockLenght;
        this.crossings = crossings;
        this.singleDirection = singleDirection;
        this.highBlocknumberSectionIds = highBlocknumberSectionIds;
    }

    public int getSectionID() {
        return sectionID;
    }

    public int getNumberOfBlocks() {
        return numberOfBlocks;
    }

    public int getBlockLenght() {
        return blockLenght;
    }

    public int[] getCrossings() {
        return crossings;
    }

    public boolean isSingleDirection() {
        return singleDirection;
    }

    public int[] getHighBlocknumberSectionIds() {
        return highBlocknumberSectionIds;
    }
}
