package com.anfe0690.tu_mejor_compra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebFilter("*.xhtml")
public class DesktopMobileFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(DesktopMobileFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.trace("init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        String accept = req.getHeader("Accept");

        if (accept != null && accept.contains("text/html")) {

            String userAgent = req.getHeader("user-agent");

            if (userAgent != null) {
                UAgentInfo uai = new UAgentInfo(userAgent, accept);

                if (uai.isMobileDevice()) {
                    String queryString = ((req.getQueryString() != null) ? ("?" + req.getQueryString()) : "");
                    logger.info("Mobile device - {}" , req.getRequestURI() + queryString);
//                    logger.debug("contextPath=\"{}\"", req.getContextPath());
//                    logger.debug("requestURI=\"{}\"", req.getRequestURI());

                    String requestWithoutContext = req.getRequestURI();
                    if (!req.getContextPath().isEmpty()) {
                        requestWithoutContext = requestWithoutContext.replace(req.getContextPath(), "");
                    }
//                    logger.debug("requestWithoutContext=\"{}\"", requestWithoutContext);

                    Path path = Paths.get(requestWithoutContext);
//                    logger.debug("path.getRoot()=\"{}\"", path.getRoot());
//                    logger.debug("path.getName(0)=\"{}\"", path.getName(0));

                    if (!path.getName(0).toString().equals("m")) {
                        logger.info("Redireccion a version Mobile...");
                        HttpServletResponse res = (HttpServletResponse) response;

                        String redireccion = req.getContextPath() + "/m/" + path.subpath(0, path.getNameCount()) + queryString;
//                        logger.debug("redireccion=\"{}\"", redireccion);
                        res.sendRedirect(redireccion);
                    } else {
                        chain.doFilter(req, response);
                    }
                    return;
                } else {
                    String queryString = ((req.getQueryString() != null) ? ("?" + req.getQueryString()) : "");
                    logger.info("Desktop device - {}", req.getRequestURI() + queryString);
//                    logger.debug("contextPath=\"{}\"", req.getContextPath());
//                    logger.debug("requestURI=\"{}\"", req.getRequestURI());

                    String requestWithoutContext = req.getRequestURI();
                    if (!req.getContextPath().isEmpty()) {
                        requestWithoutContext = requestWithoutContext.replace(req.getContextPath(), "");
                    }
//                    logger.debug("requestWithoutContext=\"{}\"", requestWithoutContext);

                    Path path = Paths.get(requestWithoutContext);
//                    logger.debug("path.getRoot()=\"{}\"", path.getRoot());
//                    logger.debug("path.getName(0)=\"{}\"", path.getName(0));

                    if (path.getName(0).toString().equals("m")) {
                        logger.info("Redireccion a version Desktop...");
                        HttpServletResponse res = (HttpServletResponse) response;

                        String redireccion = req.getContextPath() + "/" + path.subpath(1, path.getNameCount()) + queryString;
//                        logger.debug("redireccion=\"{}\"", redireccion);
                        res.sendRedirect(redireccion);
                    } else {
                        chain.doFilter(req, response);
                    }
                    return;
                }
            } else {
                logger.warn("user-agent nulo");
            }
        }
        chain.doFilter(req, response);
    }

    @Override
    public void destroy() {
        logger.trace("destroy");
    }
}
