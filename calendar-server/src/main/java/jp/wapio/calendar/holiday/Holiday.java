package jp.wapio.calendar.holiday;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Table("holidays")
public class Holiday implements Serializable {

    @Id
    private LocalDate date;

    private String nameJa;

    private String nameEn;
}
