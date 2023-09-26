package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.usuarios.DatosAuntenticacionUsuario;
import med.voll.api.domain.usuarios.Usuario;
import med.voll.api.infra.security.DatosJWTToken;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {
    @Autowired
    private AuthenticationManager authenticationManager;


    private TokenService tokenService;

    @PutMapping
    public ResponseEntity autenticarUsuario (@RequestBody @Valid DatosAuntenticacionUsuario datosAuntenticacionUsuario){

        Authentication authtoken = new UsernamePasswordAuthenticationToken(datosAuntenticacionUsuario.login(),
               datosAuntenticacionUsuario.clave());
        var usuarioAutenticado = authenticationManager.authenticate(authtoken);
        var JWTtoken =tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
      return ResponseEntity.ok(new DatosJWTToken(JWTtoken) );
    }
}
