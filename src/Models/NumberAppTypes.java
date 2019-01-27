package Models;

public class NumberAppTypes {
    private String month;
    private Integer counselingCount;
    private Integer tutoringCount;
    private Integer mentoringCount;
    
    public NumberAppTypes(String month, Integer counselingCount, Integer tutoringCount, Integer mentoringCount) {
        this.month = month;
        this.counselingCount = counselingCount;
        this.tutoringCount = tutoringCount;
        this.mentoringCount = mentoringCount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getCounselingCount() {
        return counselingCount;
    }

    public void setCounselingCount(Integer counselingCount) {
        this.counselingCount = counselingCount;
    }

    public Integer getTutoringCount() {
        return tutoringCount;
    }

    public void setTutoringCount(Integer tutoringCount) {
        this.tutoringCount = tutoringCount;
    }

    public Integer getMentoringCount() {
        return mentoringCount;
    }

    public void setMentoringCount(Integer mentoringCount) {
        this.mentoringCount = mentoringCount;
    }

    
}
