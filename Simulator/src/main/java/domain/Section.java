package domain;

/**
 * Created by Zeger Nuitten on 22/10/2017.
 */
public class Section {
  private int numberOfBlocks;
  private int sectionId;
  private int speed;
  private int blockLength;

  public Section(int numberOfBlocks, int sectionId, int speed, int blockLength) {
    this.numberOfBlocks = numberOfBlocks;
    this.sectionId = sectionId;
    this.speed = speed;
    this.blockLength = blockLength;
  }

  public int getBlockLength() {
    return blockLength;
  }
  public int getNumberOfBlocks() {
    return numberOfBlocks;
  }

  public void setNumberOfBlocks(int numberOfBlocks) {
    this.numberOfBlocks = numberOfBlocks;
  }

  public int getSectionId() {
    return sectionId;
  }

  public void setSectionId(int sectionId) {
    this.sectionId = sectionId;
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }
}
