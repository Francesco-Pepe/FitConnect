package bean;

public class SessionBean {


    private AthleteBean athlete;
    private PersonalTrainerBean pt;
    private final int id;

    public SessionBean(AthleteBean athlete,int id){
        this.athlete=athlete;
        this.id=id;
    }

    public SessionBean(PersonalTrainerBean pt,int id){
        this.pt=pt;
        this.id=id;
    }
    public AthleteBean getAthlete() {
        return athlete;
    }

    public void setAthlete(AthleteBean athlete) {
        this.athlete = athlete;
    }

    public PersonalTrainerBean getPt() {
        return pt;
    }

    public void setPt(PersonalTrainerBean pt) {
        this.pt = pt;
    }

    public int getId() {
        return id;
    }
}
