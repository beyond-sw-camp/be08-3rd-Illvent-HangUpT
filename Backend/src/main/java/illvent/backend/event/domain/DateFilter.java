package illvent.backend.event.domain;

import org.springframework.cglib.core.Local;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public enum DateFilter {
    ALL,TODAY,THIS_WEEK,THIS_MONTH;

    public LocalDateRange getDateRange() {
        LocalDate today = LocalDate.now(); // 오늘 날짜
        return switch (this) {
            case TODAY -> new LocalDateRange(today, today);
            case THIS_WEEK -> new LocalDateRange(today.with(DayOfWeek.MONDAY), today.with(DayOfWeek.SUNDAY));
            case THIS_MONTH ->
                    new LocalDateRange(today.with(TemporalAdjusters.firstDayOfMonth()), today.with(TemporalAdjusters.lastDayOfMonth()));
            default -> null;
        };

    }
}
