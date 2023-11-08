package ru.cvetkov.moving.objects.converters;

public class TimestampSqlTimeConverter {
    public TimestampSqlTimeConverter() {
    }

    public static java.sql.Timestamp convertToSqlTimestamp(com.google.protobuf.Timestamp timestamp) {
        long seconds = timestamp.getSeconds();
        int nanos = timestamp.getNanos();

        long milliseconds = seconds * 1000 + nanos / 1_000_000;

        return new java.sql.Timestamp(milliseconds);
    }

}
