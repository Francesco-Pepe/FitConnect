package view;


import bean.*;
import model.TrainingPlan;

import java.util.List;

public abstract class Navigator {
    private boolean started;
    private Context context;
    private Screen currentScreen;

    protected Navigator(){
        this.started=true;
        this.context=new Context();
    }
    protected AthleteBean getAthlete(){
        return this.context.getAthlete();
    }

    protected PersonalTrainerBean getPt(){
        return this.context.getPt();
    }

    protected void setPlanRequest(PlanRequestBean request){
        this.context.setRequest(request);
    }

    protected PlanRequestBean getPlanRequest(){
        return  this.context.getRequest();
    }

    protected  void setTrainerName(String trainer){
        context.setTrainer(trainer);
    }

    protected String getTrainerName(){
        return context.getTrainer();
    }

    protected void setAthlete(AthleteBean a){
        this.context=new Context(a);
    }

    protected void setPt(PersonalTrainerBean pt){
        this.context=new Context(pt);
    }

    protected TrainingPlanBean getPlan(){
        return this.context.getPlan();
    }
    protected void setPlan(TrainingPlanBean plan){
        this.context.setPlan(plan);
    }

    protected void addClients(List<AthleteBean> clients){
        this.context.setAthletes(clients);
    }

    protected List<AthleteBean> getClients(){
        return this.context.getAthletes();
    }

    protected SessionBean getSession(){
        return this.context.getSession();
    }

    protected void setSession(SessionBean session){
        this.context.setSession(session);
    }

    protected void setCurrentScreen(Screen screen){
        this.currentScreen=screen;
    }

    protected void nextScreen(){
        if (this.currentScreen==null) return;
        switch(currentScreen){
            case LOGIN -> viewLogin();
            case VIEW_PLAN -> viewPlan();
            case ATHLETE_DASHBOARD -> viewAthleteDashboard();
            case REQUEST_PLAN -> viewPlanRequest();
            case TRAINER_DASHBOARD -> viewTrainerDashboard();
            case CREATE_PLAN ->viewCreatePlan();
        }

    }

    protected void goToLogin(){setCurrentScreen(Screen.LOGIN); nextScreen();}
    protected void goToViewPLan(){setCurrentScreen(Screen.VIEW_PLAN); nextScreen();}
    protected void goToAthleteDashboard(){setCurrentScreen(Screen.ATHLETE_DASHBOARD); nextScreen();}
    protected void goToPlanRequest(){setCurrentScreen(Screen.REQUEST_PLAN); nextScreen();}
    protected void goToTrainerDashboard(){setCurrentScreen(Screen.TRAINER_DASHBOARD); nextScreen();}
    protected void goToCreatePlan(){setCurrentScreen(Screen.CREATE_PLAN);nextScreen();}

    public abstract void viewCreatePlan();
    public abstract void viewLogin();
    public abstract void viewPlan();
    public abstract void viewAthleteDashboard();
    public abstract void viewPlanRequest();
    public abstract  void viewTrainerDashboard();

    public abstract void startUp();

}
