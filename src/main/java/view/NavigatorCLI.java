package view;
import java.util.Scanner;

    public class NavigatorCLI extends Navigator {

        private final Scanner scanner = new Scanner(System.in);

        private LoginGraphicControllerCLI            login;
        private AthleteDashboardGraphicControllerCLI athleteDashboard;
        private TrainerDashboardGraphicControllerCLI trainerDashboard;
        private ViewPlanGraphicControllerCLI         viewPlan;
        private RequestPlanGraphicControllerCLI      requestPlan;
        private CreatePlanGraphicControllerCLI       createPlan;
        private AddExerciseGraphicControllerCLI      addExercise;

        @Override
        public void viewLogin() {
            if (login == null) {
                login = new LoginGraphicControllerCLI();
                login.setNavigator(this);
            }
            login.start(scanner);
        }

        @Override
        public void viewAthleteDashboard() {
            if (athleteDashboard == null) {
                athleteDashboard = new AthleteDashboardGraphicControllerCLI();
                athleteDashboard.setNavigator(this);
            }
            athleteDashboard.start(scanner);
        }

        @Override
        public void viewTrainerDashboard() {
            if (trainerDashboard == null) {
                trainerDashboard = new TrainerDashboardGraphicControllerCLI();
                trainerDashboard.setNavigator(this);
            }
            trainerDashboard.start(scanner);
        }

        @Override
        public void viewPlan() {
            if (viewPlan == null) {
                viewPlan = new ViewPlanGraphicControllerCLI();
                viewPlan.setNavigator(this);
            }
            viewPlan.start(scanner);
        }

        @Override
        public void viewPlanRequest() {
            if (requestPlan == null) {
                requestPlan = new RequestPlanGraphicControllerCLI();
                requestPlan.setNavigator(this);
            }
            requestPlan.start(scanner);
        }

        @Override
        public void viewCreatePlan() {
            if (createPlan == null) {
                createPlan = new CreatePlanGraphicControllerCLI();
                createPlan.setNavigator(this);
            }
            createPlan.start(scanner);
        }

        @Override
        public void viewAddExercise() {
            if (addExercise == null) {
                addExercise = new AddExerciseGraphicControllerCLI();
                addExercise.setNavigator(this);
            }
            addExercise.start(scanner);
        }

        @Override
        public void startUp() {
            goToLogin();
        }
    }

