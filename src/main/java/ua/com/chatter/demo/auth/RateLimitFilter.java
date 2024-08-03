package ua.com.chatter.demo.auth;

import java.io.IOException;
import java.time.Duration;

import org.springframework.stereotype.Component;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

@Component
public class RateLimitFilter implements Filter {

    private final Bucket bucket;

    public RateLimitFilter() {
        Bandwidth limit = Bandwidth.classic(10, Refill.greedy(10, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, jakarta.servlet.ServletException {
        if (bucket.tryConsume(1)) {
            chain.doFilter(request, response);
        } else {
            // final int SC_TOO_MANY_REQUESTS = 429;
            // response.setContentType(null);(SC_TOO_MANY_REQUESTS);
            response.getWriter().write("Too many requests");
        }
    }
}
