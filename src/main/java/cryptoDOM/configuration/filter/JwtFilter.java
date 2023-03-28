package cryptoDOM.configuration.filter;

import cryptoDOM.configuration.jwt.Jwt;
import cryptoDOM.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    public static final String AUTHORIZATION = "Authorization";
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private Jwt jwtProvider;

    private String getTokenFromRequest(HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(AUTHORIZATION))
                .findFirst()
                .map(Cookie::getValue).orElse(null);
    }

    private void clearAuthentication(ServletRequest servletRequest, ServletResponse servletResponse) {
        SecurityContextHolder.clearContext();
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (AUTHORIZATION.equals(cookie.getName())) {
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    httpServletResponse.addCookie(cookie);
                }
            }
        }
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        logger.info("do filter...");
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        if (token != null && jwtProvider.validateToken(token)) {
            String userLogin = jwtProvider.getLoginFromToken(token);
            UserDetails customUserDetails = customUserDetailsService.loadUserByUsername(userLogin);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserDetails,
                    null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            customUserDetails.getAuthorities().forEach(GrantedAuthority::getAuthority);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
