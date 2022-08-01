package br.com.bookstore.catalog.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_BOOK")
@SequenceGenerator(name = "SQ_BOOK", sequenceName = "SQ_BOOK",initialValue = 1, allocationSize = 1)
public class Book implements Serializable {

    @Id
    @GeneratedValue(generator = "SQ_BOOK", strategy = SEQUENCE)
    private long id;
    private String authorId;
    private String publisher;
    private String title;
    private LocalDate releasedAt;
    private int numberOfPages;
    private boolean active;

}
