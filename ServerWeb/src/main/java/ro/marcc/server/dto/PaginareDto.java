package ro.marcc.server.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaginareDto {
    private int numarPagina;
    private int numarElemente;

    private LocalDate dataStart;
    private LocalDate dataSfarsit;


}
