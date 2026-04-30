package rpgsumting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StoryNode {
    private final String id;
    private final String title;
    private final String body;
    private final SceneType sceneType;
    private final boolean ending;
    private final List<Choice> choices;

    public StoryNode(String id, String title, String body, SceneType sceneType, boolean ending, List<Choice> choices) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.sceneType = sceneType;
        this.ending = ending;
        this.choices = Collections.unmodifiableList(new ArrayList<Choice>(choices));
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public SceneType getSceneType() {
        return sceneType;
    }

    public boolean isEnding() {
        return ending;
    }

    public List<Choice> getChoices() {
        return choices;
    }
}
