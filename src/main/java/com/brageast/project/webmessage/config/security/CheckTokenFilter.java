package com.brageast.project.webmessage.config.security;

import com.brageast.project.webmessage.pojo.table.UserTable;
import com.brageast.project.webmessage.service.UserService;
import com.brageast.project.webmessage.util.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CheckTokenFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(CheckTokenFilter.class);

    /**
     * Token请求头名称
     */
    private static final String TOKEN_HEADER = "Authorization";
    /**
     * Token前缀
     */
    private static final String TOKEN_PREFIX = "Bearer";

    @Autowired
    private UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(TOKEN_HEADER);
        if (token != null && token.startsWith(TOKEN_PREFIX) && isNotLogin()) {
            token = token.replaceFirst(TOKEN_PREFIX, "").replace(" ", "");
            try {
                final String username = JwtUtils.getUsername(token);
                final UserTable user = userService.findUser(username);
                if (user != null) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    if (log.isDebugEnabled()) {
                        log.debug("用户{}校验成功!", username);
                    }
                }

            } /*catch (ExpiredJwtException e) {
                response.sendError(HttpStatus.Au);
                return;
            } */catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug(e.getMessage(), e);
                }
            }
        }
//        response.sendRedirect("/login");
        filterChain.doFilter(request, response);
    }

    private boolean isNotLogin() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null || !authentication.isAuthenticated();
    }

}
