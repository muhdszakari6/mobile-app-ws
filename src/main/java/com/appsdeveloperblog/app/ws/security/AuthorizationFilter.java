package com.appsdeveloperblog.app.ws.security;

import com.amazonaws.util.Base64;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.repository.UserRepository;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    private final UserRepository userRepository;
    public AuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

  @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain) throws IOException, ServletException{
        String header = req.getHeader(SecurityConstants.HEADER_STRING);

        if(header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)){
            chain.doFilter(req, res);
            return;
        }

      UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      chain.doFilter(req,res);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req){
        String authorizationHeader = req.getHeader(SecurityConstants.HEADER_STRING);
        if(authorizationHeader==null){
            return null;
        }
        String token = authorizationHeader.replace(SecurityConstants.TOKEN_PREFIX,"");

        byte[] secretKeyBytes = Base64.encode(SecurityConstants.getTokenSecret().getBytes());
        SecretKey secretKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());

      JwtParser jwtParser = Jwts.parser().setSigningKey(secretKey);
      Jwt<Header, Claims> jwt = jwtParser.parse(token);
      String subject = jwt.getBody().getSubject();
      if(subject ==null){
          return null;
      }

      UserEntity userEntity = userRepository.findByEmail(subject);
      if(userEntity == null) return null;
      UserPrincipal userPrincipal = new UserPrincipal(userEntity);
      return new UsernamePasswordAuthenticationToken(userPrincipal, null,userPrincipal.getAuthorities());

//        if(token != null){
//            token = token.replace(SecurityConstants.TOKEN_PREFIX,"");
//            String user = Jwts.parser()
//                    .setSigningKey(SecurityConstants.getTokenSecret())
//                    .parseClaimsJws(token)
//                    .getBody()
//                    .getSubject();
//
//            if(user != null){
//                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
//            }
//            return null;
//        }
//        return null;
  }

}
