package com.brageast.project.webmessage.config.security;

import com.brageast.project.webmessage.service.UserService;
import com.brageast.project.webmessage.util.JwtUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JWTTokenFilter extends BasicAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(JWTTokenFilter.class);

    /**
     * Token请求头名称
     */
    public static final String TOKEN_HEADER = "Authorization";
    /**
     * Token前缀
     */
    public static final String TOKEN_PREFIX = "Bearer";

    /**
     * WebSocket
     */
    public static final String SOCKET_HEADER = "token";

    private final UserDetailsService userService;

    public JWTTokenFilter(AuthenticationManager authenticationManager, UserDetailsService userService) {
        super(authenticationManager);
        this.userService = userService;
    }


    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        final String token = getToken(request);
        //noinspection ConstantConditions
        do {
            if (token == null || isLogin()) {
                break;
            }
            final String username;
            try {
                username = JwtUtils.getUsername(token);
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug(e.getMessage(), e);
                }
                break;
            }
            Optional.ofNullable(userService.loadUserByUsername(username))
                    .ifPresent(userDetails -> {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        if (log.isDebugEnabled()) {
                            log.debug("用户{}校验成功!", username);
                        }
                    });

        } while (false);
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        boolean isWebSocket = "websocket".equalsIgnoreCase(request.getHeader("Upgrade"));
        return isWebSocket ?
                request.getParameter(SOCKET_HEADER) :
                Optional.ofNullable(request.getHeader(TOKEN_HEADER))
                        .orElse("")
                        .replaceFirst(TOKEN_PREFIX, "")
                        .replace(" ", "");
    }

    private boolean isLogin() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

}
