package med.voll.api.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // actua como un proxy, para interceptar los metodos del controlador y capturar las excepciones
public class TratadorDeErrores {

    // cuando se lanza la excepcion  EntityNotFoundException  se ejecutará este metodo
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratadorDeErrores(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratadorError400(MethodArgumentNotValidException e){ // el parametro obtiene las excepciones
        var errores = e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }

    private record DatosErrorValidacion(String campo, String error){

        public DatosErrorValidacion(FieldError fieldError){
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}
