package com.xxq;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class List2MapTest {
    @Test
    public void convert_list_to_map_with_guava() {
        List<Movie> movies = Lists.newArrayList();
        movies.add(new Movie(1, "The Shawshank Redemption"));
        movies.add(new Movie(2, "The Godfather"));
        movies.add(new Movie(3, null));
        ImmutableMap<Integer, Movie> mappedMovies = Maps.uniqueIndex(movies, movie -> movie.getRank());
        System.out.println(JSON.toJSONString(mappedMovies));
    }

    @Test
    public void convert_list_to_map_with_java8_lambda() {

        List<Movie> movies = new ArrayList<Movie>();
        movies.add(new Movie(1, "The Shawshank Redemption"));
        movies.add(new Movie(2, "The Godfather"));
        movies.add(new Movie(2, "The Godfather2"));
//        movies.add(new Movie(3, null));
        movies.add(new Movie(3, "The Godfather"));
//        Map<Integer, Movie> mappedMovies = movies.stream().collect(Collectors.toMap(Movie::getRank, p -> p, (o, n) -> n));
        Map<Integer, String> mappedMovies = movies.stream().collect(Collectors.toMap(Movie::getRank, Movie::getDescription, (o, n) -> n));
        System.out.println(JSON.toJSONString(mappedMovies));
    }

    @Test
    public void convert_list_to_map_with_java8_lambda2() {

        List<Movie> movies = new ArrayList<Movie>();
        movies.add(new Movie(1, "The Shawshank Redemption"));
        movies.add(new Movie(2, "The Godfather"));

        Map<Integer, Movie> mappedMovies = movies.stream()
                .collect(
                        Collector.of(HashMap::new,
                                (m, per) -> m.put(per.getRank(), per), (k, v) -> v, Collector.Characteristics.IDENTITY_FINISH));
        System.out.println(JSON.toJSONString(mappedMovies));
    }

    @Test
    public void convert_list_to_map_with_java() {

        List<Movie> movies = new ArrayList<Movie>();
        movies.add(new Movie(1, "The Shawshank Redemption"));
        movies.add(new Movie(2, "The Godfather"));

        Map<Integer, Movie> mappedMovies = new HashMap<Integer, Movie>();
        for (Movie movie : movies) {
            mappedMovies.put(movie.getRank(), movie);
        }

        assertEquals("The Shawshank Redemption", mappedMovies.get(1).getDescription());
    }

}
