package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HorarioDeAnticipacion implements ValidadorDeConsultas{
    public void validar(DatosAgendarConsulta datos){
        var ahora = LocalDateTime.now();
        var horadeconsulta=datos.fecha();
        var diferencia30min= Duration.between(ahora,horadeconsulta).toMinutes()<30;
        if(diferencia30min){
            throw new ValidationException("Las consultas deben progrmarse con al menos 30 min de anticipacion");
        }
    }
}
