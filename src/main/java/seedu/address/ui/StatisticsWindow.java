package seedu.address.ui;

import java.util.List;
import java.util.Map;

import javafx.scene.Scene;
import javafx.stage.Stage;
import seedu.address.model.person.JobRole;

/**
 * A new window that displays job statistics in a bar chart.
 */
public class StatisticsWindow {

    private static final String TITLE = "Job Statistics";
    private static StatisticsWindow instance = null;
    private Stage stage;

    /**
     * Constructs a new StatisticsWindow.
     * This creates a new Stage and sets the title to "Job Statistics".
     */
    public StatisticsWindow() {
        stage = new Stage();
        stage.setTitle(TITLE);
        stage.setResizable(false);
        stage.setOnHidden(e -> instance = null);
    }

    public static StatisticsWindow getInstance() {
        if (instance == null) {
            instance = new StatisticsWindow();
        }
        return instance;
    }

    /**
     * Sets the statistics to be displayed in the window.
     * @param stats A map of job roles to applicant counts.
     */
    public void setStatistics(Map<JobRole, Long> stats, List<JobRole> dynamicJobRoles) {
        StatisticsPanel statsPanel = new StatisticsPanel();
        statsPanel.setStatistics(stats, dynamicJobRoles);
        stage.setScene(new Scene(statsPanel.getRoot()));
    }

    /**
     * Displays the statistics window.
     */
    public void show() {
        stage.show();
    }

    /**
     * Hides the statistics window.
     */
    public void hide() {
        stage.hide();
    }

    /**
     * Returns true if the statistics window is currently showing.
     */
    public boolean isShowing() {
        return stage.isShowing();
    }
}
