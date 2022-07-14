package yanghgri.whileyouplay.enums;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;

public class Integer2ReviewConverter implements Converter<Integer, Review> {
    @Override
    public Review convert(@NotNull Integer source) {
        Review review = Review.getInstance(source);
        if (review == null) {
            throw new IllegalArgumentException("无法匹配到Review的Code!");
        }
        return review;
    }
}
