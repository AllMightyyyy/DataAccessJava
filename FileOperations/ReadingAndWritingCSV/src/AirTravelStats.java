public class AirTravelStats {
    private String monthAbv;
    private int year1;
    private int year2;
    private int year3;

    public AirTravelStats(String monthAbv, int year1, int year2, int year3) {
        this.monthAbv = monthAbv;
        this.year1 = year1;
        this.year2 = year2;
        this.year3 = year3;
    }

    public int getYear1() {
        return year1;
    }

    public void setYear1(int year1) {
        this.year1 = year1;
    }

    public String getMonthAbv() {
        return monthAbv;
    }

    public void setMonthAbv(String monthAbv) {
        this.monthAbv = monthAbv;
    }

    public int getYear2() {
        return year2;
    }

    public void setYear2(int year2) {
        this.year2 = year2;
    }

    public int getYear3() {
        return year3;
    }

    public void setYear3(int year3) {
        this.year3 = year3;
    }
}
