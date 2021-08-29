package nextstep.jwp.controller;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import nextstep.jwp.http.HttpHeader;
import nextstep.jwp.http.HttpMethod;
import nextstep.jwp.http.HttpRequest;
import nextstep.jwp.http.HttpResponse;
import nextstep.jwp.http.ResponseStatus;
import nextstep.jwp.util.FileUtil;

public class IndexPageController extends AbstractController {

    private static final HttpMethod HTTP_METHOD = HttpMethod.GET;
    private static final String URI_PATH = "/index.html";

    @Override
    HttpMethod httpMethod() {
        return HTTP_METHOD;
    }

    @Override
    String uriPath() {
        return URI_PATH;
    }

    @Override
    public HttpResponse doService(HttpRequest httpRequest) {
        try {
            String responseBody = FileUtil.readFileByUriPath(uriPath());
            return HttpResponse.status(ResponseStatus.OK,
                HttpHeader.getHTMLResponseHeader(responseBody),
                responseBody);
        } catch (IllegalArgumentException e) {
            // TODO: 에러 파일 출력
            return null;
        }
    }
}
