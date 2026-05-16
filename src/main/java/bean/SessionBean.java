package bean;

public class SessionBean {

    private Role role;
    private AthleteBean athlete;
    private PersonalTrainerBean pt;
    private int id;

    public SessionBean(AthleteBean athlete,int id){
        this.athlete=athlete;
        this.id=id;
        this.role=Role.ATHLETE;
    }

    public SessionBean(PersonalTrainerBean pt,int id){
        this.pt=pt;
        this.id=id;
        this.role=Role.PERSONAL_TRAINER;
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
