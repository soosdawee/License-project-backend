package com.license.backend.domain.model;

import com.license.backend.util.ListToJsonConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "table_datas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TableData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "table_datas_table_data_id_seq")
    @SequenceGenerator(name = "table_datas_table_data_id_seq", allocationSize = 1)
    @Column
    private Integer tableDataId;

    @Convert(converter = ListToJsonConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<List<String>> data;

}
