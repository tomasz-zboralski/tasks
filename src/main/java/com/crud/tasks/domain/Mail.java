package com.crud.tasks.domain;

//import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

//import javax.validation.constraints.Null;
import java.util.Optional;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class Mail {
    private final String mailTo;
    private final String subject;
    private final String message;
    private final Optional<String> toCc;

}