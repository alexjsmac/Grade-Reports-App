package ca.uwo.csd.cs2212.team10;

public class JavaBean {
    
    /* Attributes */
    private Double deliverableAvg;
    private Double deliverableGrade;
    private String deliverableName;
    
    public JavaBean(String deliverableName, Double grade, Double deliverableAvg) {
        this.deliverableName = deliverableName;
        this.deliverableGrade = grade;
        this.deliverableAvg = deliverableAvg;
    }

    public String getDeliverableName() {
        return deliverableName;
    }

    public Double getDeliverableGrade() {
        return deliverableGrade;
    }

    public Double getDeliverableAvg() {
        return deliverableAvg;
    }
}
