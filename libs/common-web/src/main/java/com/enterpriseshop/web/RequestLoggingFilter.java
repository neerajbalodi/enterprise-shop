package com.enterpriseshop.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Logs one tidy line per incoming HTTP request: method, path, status, duration.
 * Goes to stdout as JSON (via logback) -> Promtail -> Loki, so each request is
 * visible in Grafana. Actuator probe/scrape traffic is skipped to keep it clean.
 */
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger("http.access");

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        long start = System.currentTimeMillis();
        try {
            chain.doFilter(req, res);
        } finally {
            long ms = System.currentTimeMillis() - start;
            String query = req.getQueryString();
            log.info("{} {}{} -> {} ({} ms)",
                    req.getMethod(),
                    req.getRequestURI(),
                    query != null ? "?" + query : "",
                    res.getStatus(),
                    ms);
        }
    }

    /** Don't log health probes / metric scrapes — they run constantly. */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest req) {
        return req.getRequestURI().startsWith("/actuator");
    }
}
