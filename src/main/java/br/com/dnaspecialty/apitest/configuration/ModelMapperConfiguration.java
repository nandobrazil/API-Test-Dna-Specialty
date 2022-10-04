package br.com.dnaspecialty.apitest.configuration;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@Configuration
public class ModelMapperConfiguration {

    private final DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Bean
    public ModelMapper modelMapper() {
        final var modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.addConverter(new StringLocalDateAbstractConverter());
        modelMapper.addConverter(new LocalDateStringAbstractConverter());
        modelMapper.addConverter(new DateStringAbstractConverter());
        return modelMapper;
    }

    private static class DateStringAbstractConverter extends AbstractConverter<Date, String> {

        @Override
        @Nullable
        protected final String convert(final Date source) {
            final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            return Optional.ofNullable(source).map(dateFormat::format).orElse(null);
        }

    }

    private class StringLocalDateAbstractConverter extends AbstractConverter<String, LocalDate> {

        @Override
        @Nullable
        protected final LocalDate convert(final String source) {
            return Optional.ofNullable(source).map(this::getLocalDate).orElse(null);
        }

        private LocalDate getLocalDate(final CharSequence s) {
            return LocalDate.parse(s, ModelMapperConfiguration.this.localDateFormatter);
        }

    }

    private class LocalDateStringAbstractConverter extends AbstractConverter<LocalDate, String> {

        @Override
        @Nullable
        protected final String convert(final LocalDate source) {
            return Optional.ofNullable(source).map(this::getFormat).orElse(null);
        }

        private String getFormat(final TemporalAccessor temporal) {
            return ModelMapperConfiguration.this.localDateFormatter.format(temporal);
        }

    }

}
