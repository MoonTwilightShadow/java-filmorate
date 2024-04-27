package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Data
@AllArgsConstructor
public class FilmGenre {
    private Integer filmId;
    private Integer genreId;
}
