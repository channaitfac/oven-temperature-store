package com.astar.ots.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.ZoneId;
import java.util.Date;

import static com.astar.ots.util.Constants.*;

public class Util {

    public static Date getDate(String date) throws ParseException {
        return SIMPLE_DATE_FORMAT.parse(date);
    }

    public static Date removeMilliseconds(Date date) {

        try {
            return getDate(date.toInstant().atZone(ZoneId.of(SG_TIME_ZONE)).format(DATETIME_FORMATTER));
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static URI getUri(String url) throws URISyntaxException {
        return new URI(url);
    }

    public static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(USER_AGENT, "Spring's RestTemplate");
        return headers;
    }

    public static void doWriteIntoJsonFile(String path, Object payload) throws Exception {
        MAPPER.writeValue(new File(path), payload);
    }
}
