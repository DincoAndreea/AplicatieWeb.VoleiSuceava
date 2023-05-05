package ro.marcc.server.dto.Meci;

import lombok.*;
import ro.marcc.server.dto.EchipaDto;
import ro.marcc.server.model.Meciuri.Meci;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MeciPrevizualizareDto {
    private Integer id;
    private EchipaDto echipa1;
    private EchipaDto echipa2;
    private String scor;
    private LocalDate dataMeci;
    private LocalTime oraMeci;
    private EchipaDto castigator;

    public MeciPrevizualizareDto(Meci meci){
        this.id = meci.getId();
        this.echipa1 = new EchipaDto(meci.getEchipe()[0]);
        this.echipa2 = new EchipaDto(meci.getEchipe()[1]);
        if(meci.getScor()[0]!=-1 && meci.getScor()[1]!=-1) {
            this.scor = meci.getScor()[0] + "-" + meci.getScor()[1];
            this.castigator = meci.getScor()[0]>meci.getScor()[1]?echipa1:meci.getScor()[0]<meci.getScor()[1]?echipa2:null;
        }else{
            this.scor = null;
        }
        this.dataMeci = meci.getDataMeci().toLocalDate();
        this.oraMeci = meci.getDataMeci().toLocalTime();
    }
}
