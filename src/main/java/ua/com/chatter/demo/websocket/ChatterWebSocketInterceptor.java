package ua.com.chatter.demo.websocket;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatterWebSocketInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {

        try {
            String key = splitQuery(request.getURI()).get("key").get(0);

            log.info("ChatterWebSocketInterceptor chat key -- " + key);
            if (key != null) {
                attributes.put("key", key);
            }
        } catch(UnsupportedEncodingException exp) {
            log.info("ChatterWebSocketInterceptor exp -- " + exp.getMessage());
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    private Map<String, List<String>> splitQuery(URI url) throws UnsupportedEncodingException {
        final Map<String, List<String>> query_pairs = new LinkedHashMap<String, List<String>>();
        final String[] pairs = url.getQuery().split("&");
        for (String pair : pairs) {
            final int idx = pair.indexOf("=");
            final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
            if (!query_pairs.containsKey(key)) {
                query_pairs.put(key, new LinkedList<String>());
            }
            final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
            query_pairs.get(key).add(value);
        }
        return query_pairs;
    }
}
