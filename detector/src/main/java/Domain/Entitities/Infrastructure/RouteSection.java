package Domain.Entitities.Infrastructure;


/*
* This class represents a certain section on a Route as returned by the RouteService
* */
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

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof RouteSection){
            return ((RouteSection) obj).getSectionID() == sectionID;
        }
        else {
            return false;
        }
    }
}
