package com.brageast.project.webmessage.config.security;

import com.brageast.project.webmessage.pojo.table.UserTable;
import com.brageast.project.webmessage.service.UserService;
import com.brageast.project.webmessage.util.JwtUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public static final String TOKEN_HEADER = "Authorization";
    /**
     * Token前缀
     */
    public static final String TOKEN_PREFIX = "Bearer";

    /**
     * WebSocket
     */
    public static final String SOCKET_HEADER = "token";

    @Autowired
    private UserService userService;


    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        final String token = getToken(request);
        if (token != null && isNotLogin()) {
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
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug(e.getMessage(), e);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        boolean isWebSocket = "websocket".equalsIgnoreCase(request.getHeader("Upgrade"));
        String token = null;
        if (!isWebSocket) {
            String _token = request.getHeader(TOKEN_HEADER);
            if (_token != null) {
                token = _token
                        .replaceFirst(TOKEN_PREFIX, "")
                        .replace(" ", "");
            }
        } else {
            // 浏览器原生WebSocket不支持添加请求头(可还行)
            token = request.getParameter(SOCKET_HEADER);
        }
        return token;
    }

    private boolean isNotLogin() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null || !authentication.isAuthenticated();
    }

}
