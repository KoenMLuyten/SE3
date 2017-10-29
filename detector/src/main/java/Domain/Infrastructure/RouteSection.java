package Domain;

public class RouteSection {
    private int sectionID;
    private int speed;

    public RouteSection(int sectionID, int speed){
        this.sectionID = sectionID;
        this.speed = speed;
    }

    public int getSectionID() {
        return sectionID;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
