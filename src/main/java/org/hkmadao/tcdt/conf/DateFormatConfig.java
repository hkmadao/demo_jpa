//package org.hkmadao.tcdt.conf;
//
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
//import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
//import org.springframework.boot.jackson.JsonComponent;
//import org.springframework.context.annotation.Bean;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//@JsonComponent
//public class DateFormatConfig {
//
//    private String dateTimePattern = "yyyy-MM-dd'T'HH:mm:ss.SSS Z";
//    private String datePattern = "yyyy-MM-dd'T00:00:00.000Z'";
//
////    @Bean
////    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilder() {
////        return builder -> {
////            TimeZone tz = TimeZone.getTimeZone("UTC");
////            DateFormat df = new SimpleDateFormat(pattern);
////            df.setTimeZone(tz);
////            builder.failOnEmptyBeans(false)
////                    .failOnUnknownProperties(false)
////                    .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
////                    .dateFormat(df);
////        };
////    }
//
//    @Bean
//    public LocalDateTimeSerializer localDateTimeDeserializer() {
//        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimePattern));
//    }
//
//    @Bean
//    public LocalDateSerializer localDateDeserializer() {
//        return new LocalDateSerializer(DateTimeFormatter.ofPattern(datePattern));
//    }
//
//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
//        return builder -> builder.serializerByType(LocalDateTime.class, localDateTimeDeserializer())
//                .serializerByType(LocalDate.class, localDateDeserializer());
//    }
//}