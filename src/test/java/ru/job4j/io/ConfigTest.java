package ru.job4j.io;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

class ConfigTest {

    @Test
    void whenPairWithoutComment() {
        String path = "./data/pair_without_comment.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name")).isEqualTo("Kirill Smirnov");
    }

    @Test
    void whenOnlyComment() {
        String path = "./data/only_comment.properties";
        Config config = new Config(path);
        config.load();
        assertThatThrownBy(() -> config.value("name"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void whenNoKeyAndValue() {
        String path = "./data/wrong_format.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenNoKey() {
        String path = "./data/pair_without_key.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenNoValue() {
        String path = "./data/pair_without_value.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenMultipleEqualSign() {
        String path = "./data/multiple_equal_sign.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name")).isEqualTo("Kirill Smirnov=");
        assertThat(config.value("port")).isEqualTo("6666=1");
    }
}