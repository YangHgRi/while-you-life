package yanghgri.whileyouplay.enums;

public enum GameTag implements BaseEnum<Integer> {
    Action(1), Strategy(2), Simulation(3), Adventure(4);

    private final Integer code;

    GameTag(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    public static GameTag getInstance(Integer code) {
        GameTag[] gameTags = GameTag.values();
        for (GameTag g : gameTags) {
            Integer c = g.getCode();
            if (c.equals(code)) {
                return g;
            }
        }
        return null;
    }
}