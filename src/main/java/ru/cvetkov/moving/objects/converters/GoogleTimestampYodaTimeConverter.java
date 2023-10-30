package ru.cvetkov.moving.objects.converters;

import com.google.protobuf.Timestamp;
import org.joda.time.DateTime;

public class GoogleTimestampYodaTimeConverter {
    public GoogleTimestampYodaTimeConverter() {
    }

    public static DateTime convertToJodaTime(Timestamp timestamp) {
        long seconds = timestamp.getSeconds();
        int nanos = timestamp.getNanos();

        // Конвертируем секунды и наносекунды в миллисекунды
        long milliseconds = seconds * 1000 + nanos / 1_000_000;

        // Создаем объект DateTime с использованием полученного значения и UTC
        return new DateTime(milliseconds);
    }

    public static Timestamp convertToProtoTimestamp(DateTime jodaDateTime) {
        // Получаем количество миллисекунд с 1970-01-01T00:00:00Z
        long milliseconds = jodaDateTime.getMillis();

        // Конвертируем миллисекунды в секунды и наносекунды
        long seconds = milliseconds / 1000;
        int nanos = (int) ((milliseconds % 1000) * 1_000_000);

        // Создаем объект com.google.protobuf.Timestamp
        return Timestamp.newBuilder()
                .setSeconds(seconds)
                .setNanos(nanos)
                .build();
    }
}
