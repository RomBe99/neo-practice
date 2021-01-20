package ru.rombe.neopractice.manager;

import ru.rombe.neopractice.decoder.Decoder;
import ru.rombe.neopractice.source.Source;

import java.util.Objects;

/**
 * This abstract class contains
 *
 * @param <SR> source returned value type
 * @param <DR> decoder result
 * @see Source
 * @see Decoder
 */
public abstract class AbstractManager<SR, DR> {
    protected Source<SR> source;
    protected Decoder<SR, DR> decoder;

    /**
     * @param source  manager source
     * @param decoder decoder for encoded data extracted from source
     * @see Source
     * @see Decoder
     */
    public AbstractManager(Source<SR> source, Decoder<SR, DR> decoder) {
        this.source = source;
        this.decoder = decoder;
    }

    public void setSource(Source<SR> source) {
        this.source = source;
    }

    public void setDecoder(Decoder<SR, DR> decoder) {
        this.decoder = decoder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractManager<?, ?> that = (AbstractManager<?, ?>) o;
        return Objects.equals(source, that.source) && Objects.equals(decoder, that.decoder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, decoder);
    }
}