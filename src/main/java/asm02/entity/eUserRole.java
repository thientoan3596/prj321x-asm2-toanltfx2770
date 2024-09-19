package asm02.entity;

import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum eUserRole {
    JOB_SEEKER,
    RECRUITER;
    public static Map<String, String> getLocalizedValues(MessageSource messageSource, Locale locale) {
        return Stream.of(eUserRole.values())
                .collect(Collectors.toMap(
                        role -> role.name(),
                        role -> messageSource.getMessage( role.name(), null, locale)
                ));

    }
}
