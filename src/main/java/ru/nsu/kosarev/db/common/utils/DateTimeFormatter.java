package ru.nsu.kosarev.db.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeFormatter {

    public static String getFormattedDateFromTimestamp(Date birthDate) {
        return birthDate == null ? null : new SimpleDateFormat("dd/MM/yyyy").format(birthDate);
    }

}
