package yanghgri.whileyouplay.enums;

public enum Review implements BaseEnum<Integer> {
    OverwhelminglyPositive(1), VeryPositive(2), Positive(3), MostlyPositive(4), Mixed(5), MostlyNegative(6), Negative(7), VeryNegative(8), OverwhelminglyNegative(9);

    private final Integer code;


    Review(int code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    public static Review getInstance(Integer code) {
        Review[] reviews = Review.values();
        for (Review e : reviews) {
            Integer c = e.getCode();
            if (c.equals(code)) {
                return e;
            }
        }
        return null;
    }
}