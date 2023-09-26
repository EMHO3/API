package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Security;

@Component
public class SecurityFilter extends  OncePerRequestFilter {
    @Autowired
    private  TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //obtener el token de los header

         var authHeader = request.getHeader("Authorization");//.replace("Bearer","");

        if (authHeader !=null){

            var token = authHeader.replace("Bearer","");
             var subject=tokenService.getSubject(token);
             if (subject !=null){
                 //Token valido
                 var usuario =usuarioRepository.findByLogin(subject);
                 var authentication= new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());//forzamos el inicio de sesion
                 SecurityContextHolder.getContext().setAuthentication(authentication);
             }
        }
        filterChain.doFilter(request,response);

    }
}
