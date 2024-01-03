package com.sametb.cinequiltapp.metadata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Samet Bayat.
 * Date: 14.12.2023 7:09 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

/**
 * @deprecated more simplistic approach followed.
 */
@Deprecated
@Getter
@AllArgsConstructor
public enum GenreType {
    ACTION(28, "Action"),
    ADVENTURE(12, "Adventure"),
    ANIMATION(16, "Animation"),
    COMEDY(35, "Comedy"),
    CRIME(80, "Crime"),
    DOCUMENTARY(99, "Documentary"),
    DRAMA(18, "Drama"),
    FAMILY(10751, "Family"),
    FANTASY(14, "Fantasy"),
    HISTORY(36, "History"),
    HORROR(27, "Horror"),
    MUSIC(10402, "Music"),
    MYSTERY(9648, "Mystery"),
    ROMANCE(10749, "Romance"),
    SCIENCE_FICTION(878, "Science Fiction"),
    TV_MOVIE(10770, "TV Movie"),
    THRILLER(53, "Thriller"),
    WAR(10752, "War"),
    WESTERN(37, "Western"),
    ACTION_ADVENTURE(10759, "Action & Adventure"),
    KIDS(10762, "Kids"),
    SCIFI_FANTASY(10765, "Sci-Fi & Fantasy"),
    SOAP(10766, "Soap"),
    NEWS(10763, "News"),
    REALITY(10764, "Reality"),
    TALK(10767, "Talk"),
    WAR_POLITICS(10768, "War & Politics");

    private final int id;
    private final String name;

}

