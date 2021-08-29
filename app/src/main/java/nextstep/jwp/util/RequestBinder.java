package nextstep.jwp.util;

import com.google.common.base.Splitter;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nextstep.jwp.http.HttpMethod;
import nextstep.jwp.http.HttpHeader;
import nextstep.jwp.http.HttpRequest;
import nextstep.jwp.http.Protocol;
import nextstep.jwp.http.QueryStrings;
import nextstep.jwp.http.URI;

public class RequestBinder {

    private static final int HTTP_METHOD_INDEX = 0;
    private static final int URI_INDEX = 1;
    private static final int PROTOCOL_INDEX = 2;
    private static final String URI_AND_QUERY_STRING_DELIMITER = "\\?";
    private static final String HEADER_DELIMITER = ":";
    private static final String START_LINE_DELIMITER = " ";
    private static final String QUERY_STRING_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    public static HttpRequest createRequestByMessage(BufferedReader bufferedReader) throws IOException {
        List<String> startLine = Arrays.asList(bufferedReader.readLine().split(START_LINE_DELIMITER));
        HttpMethod httpMethod = HttpMethod.of(startLine.get(HTTP_METHOD_INDEX));
        URI uri = initializeUri(startLine.get(URI_INDEX));
        Protocol protocol = new Protocol(startLine.get(PROTOCOL_INDEX));
        HttpHeader httpHeader = initializeHeaders(bufferedReader);

        return new HttpRequest(httpMethod, uri, protocol, httpHeader);
    }

    private static HttpHeader initializeHeaders(BufferedReader bufferedReader) throws IOException {
        Map<String, String> httpHeaders = new HashMap<>();

        String header = bufferedReader.readLine();
        while (header != null && !header.isEmpty()) {
            String[] keyValue = header.split(HEADER_DELIMITER, 2);
            httpHeaders.put(keyValue[0].trim(), keyValue[1].trim());
            header = bufferedReader.readLine();
        }

        return new HttpHeader(httpHeaders);
    }

    private static URI initializeUri(String uri) {
        String[] elements = uri.split(URI_AND_QUERY_STRING_DELIMITER, 2);

        QueryStrings queryStrings = null;
        if (elements.length == 2) {
            queryStrings = new QueryStrings(Splitter.on(QUERY_STRING_DELIMITER)
                .withKeyValueSeparator(KEY_VALUE_DELIMITER)
                .split(elements[1]));
        }

        return new URI(elements[0], queryStrings);
    }

}
