package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigatorGUI extends Navigator{
    private final Stage stage;
    private AthleteDashboardGraphicControllerGUI athleteDashboard;
    private LoginGraphicControllerGUI login;
    private RequestPlanGraphicGraphicControllerGUI requestPlan;
    private ViewPlanControllerGUI viewPlan;
    private TrainerDashboardGraphicControllerGUI trainerDashboard;

    public NavigatorGUI(){
        super();
        this.stage=new Stage();
        this.stage.setTitle("-- FitConnect --");
        this.stage.setMinWidth(600);
        this.stage.setMinHeight(400);
    }

    @Override
    public void viewLogin() {
        try {
            if (this.login == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
                Parent root = loader.load();
                this.login = loader.getController();
                this.login.setView(root);
                this.login.setGUINavigator(this);
            }
            this.login.start();
            render(this.login.getView());
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }



    @Override
    public void viewPlan() {    try {
        if (this.viewPlan == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewPlan.fxml"));
            Parent root = loader.load();
            this.viewPlan = loader.getController();
            this.viewPlan.setView(root);
            this.viewPlan.setGUINavigator(this);
        }
        this.viewPlan.start();
        render(this.viewPlan.getView());
    } catch (IOException e) {
        throw new RuntimeException();
    }
    }

    @Override
    public void viewAthleteDashboard() {
        try {
            if (this.athleteDashboard == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AthleteDashboard.fxml"));
                Parent root = loader.load();
                this.athleteDashboard = loader.getController();
                this.athleteDashboard.setView(root);
                this.athleteDashboard.setGUINavigator(this);
            }
            this.athleteDashboard.start();
            render(this.athleteDashboard.getView());
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void viewPlanRequest() {

        try {
            if (this.requestPlan == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/RequestPlan.fxml"));
                Parent root = loader.load();
                this.requestPlan = loader.getController();
                this.requestPlan.setView(root);
                this.requestPlan.setGUINavigator(this);
            }
            this.requestPlan.start();
            render(this.requestPlan.getView());
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }
    @Override
    public void viewTrainerDashboard(){
        try {
            if (this.trainerDashboard == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/TrainerDashboard.fxml"));
                Parent root = loader.load();
                this.trainerDashboard = loader.getController();
                this.trainerDashboard.setView(root);
                this.trainerDashboard.setGUINavigator(this);
            }
            this.trainerDashboard.start();
            render(this.trainerDashboard.getView());
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    @Override
    public void viewCreatePlan(){
        
    }

    @Override
    public void startUp() {
        goToLogin();
    }
    private void render(Parent view) {
        if (view == null) return;

        if (this.stage.getScene() == null) {
            this.stage.setScene(new Scene(view));
        } else {
            this.stage.getScene().setRoot(view);
        }

        this.stage.sizeToScene();

        if (!this.stage.isShowing()) {
            this.stage.centerOnScreen();
            this.stage.show();
        }
    }
}
