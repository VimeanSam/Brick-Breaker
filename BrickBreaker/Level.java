package BrickBreaker;

import java.util.ArrayList;

class Level {
  public int levelNum = 0;

    public String buildLevel(String extension, String slash) {
      ArrayList<Integer> levels = new ArrayList<Integer>();
      levels.add(1);
      levels.add(2);
      levels.add(3);

      String level = slash+"Assets/level" +levels.get(levelNum)+ "."+ extension;

      return level;
    }

    public String levelBackgroundChange() {
      return buildLevel("png", "/");
    }

    public String levelChange() {
      return buildLevel("txt", "");
    }
    public void newLevel() {
      this.levelNum = levelNum++;
    }
}
