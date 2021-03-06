package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String STARTDATE_FIELD_ID = "#startDate";
    private static final String STARTTIME_FIELD_ID = "#startTime";
    private static final String ENDDATE_FIELD_ID = "#endDate";
    private static final String ENDTIME_FIELD_ID = "#endTime";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String CATEGORIES_FIELD_ID = "#categories";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label startDateLabel;
    private final Label startTimeLabel;
    private final Label endDateLabel;
    private final Label endTimeLabel;
    private final Label descriptionLabel;
    private final Label categoriesLabel;
    private final List<Label> tagLabels;

    public TaskCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        startDateLabel = getChildNode(STARTDATE_FIELD_ID);
        startTimeLabel = getChildNode(STARTTIME_FIELD_ID);
        endDateLabel = getChildNode(ENDDATE_FIELD_ID);
        endTimeLabel = getChildNode(ENDTIME_FIELD_ID);
        descriptionLabel = getChildNode(DESCRIPTION_FIELD_ID);
        categoriesLabel = getChildNode(CATEGORIES_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getStartDate() {
        return startDateLabel.getText();
    }

    public String getStartTime() {
        return startTimeLabel.getText();
    }

    public String getEndDate() {
        return endDateLabel.getText();
    }

    public String getEndTime() {
        return endTimeLabel.getText();
    }

    public String getDescription() {
        return descriptionLabel.getText();
    }

    public String getCategories() {
        return categoriesLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code task}.
     */
    public boolean equals(Task task) {
        return getName().equals(task.getName().fullName)
                && getStartDate().equals(task.getStartDate().value)
                && getStartTime().equals(task.getStartTime().value)
                && getEndDate().equals(task.getEndDate().value)
                && getEndTime().equals(task.getEndTime().value)
                && getDescription().equals(task.getDescription().value)
                && getCategories().equals(task.getCategories().fullName)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(task.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}
