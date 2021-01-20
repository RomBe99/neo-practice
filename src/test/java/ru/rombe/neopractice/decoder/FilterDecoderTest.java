package ru.rombe.neopractice.decoder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.rombe.neopractice.decoder.exception.DecoderException;
import ru.rombe.neopractice.manager.property.PropertiesManager;
import ru.rombe.neopractice.manager.property.PropertiesManagerImpl;
import ru.rombe.neopractice.source.Source;
import ru.rombe.neopractice.util.JsonUtils;
import ru.rombe.neopractice.util.lex.exception.AnalyzerException;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FilterDecoderTest {

    @ParameterizedTest
    @MethodSource("decodeTestValues")
    public void decodeTest(FilterDecoder filterDecoder,
                           String jsonRules, Map<String, String> animal, boolean expectedResultForAllRules)
            throws DecoderException, AnalyzerException {
        Map<String, Predicate<Map<String, String>>> rules = filterDecoder.decode(jsonRules);

        for (String s : rules.keySet()) {
            boolean actualResult = rules.get(s).test(animal);

            Assertions.assertEquals(expectedResultForAllRules, actualResult);
        }
    }

    private static Stream<Arguments> decodeTestValues() throws Exception {
        Map<String, Set<String>> properties = new HashMap<>();
        properties.put("WEIGHT", Set.of("THIN", "AVERAGE", "FAT"));
        properties.put("HEIGHT", Set.of("LOW", "MEDIUM", "HIGH"));
        properties.put("SEX", Set.of("MALE", "FEMALE"));

        Source<String> source = Mockito.mock(Source.class);
        Decoder<String, Map<String, Set<String>>> decoder = Mockito.mock(Decoder.class);
        Mockito.when(source.extract()).thenReturn("");
        Mockito.when(decoder.decode("")).thenReturn(properties);

        PropertiesManager<String, String> propertiesManager = new PropertiesManagerImpl<>(source, decoder);
        propertiesManager.update();
        FilterDecoder filterDecoder = new FilterDecoder(propertiesManager);

        List<Arguments> args = new LinkedList<>();

        {
            Map<String, String> rules = new HashMap<>();
            rules.put("11234", "NOT WEIGHT.THIN");
            rules.put("24532", "HEIGHT.HIGH AND SEX.FEMALE");
            rules.put("32363", "NOT SEX.MALE AND (NOT HEIGHT.MEDIUM OR HEIGHT.HIGH)");

            Map<String, String> a1 = new HashMap<>();
            a1.put("WEIGHT", "FAT");
            a1.put("HEIGHT", "HIGH");
            a1.put("SEX", "FEMALE");

            args.add(Arguments.of(filterDecoder, JsonUtils.toJson(rules), a1, true));
        }

        return args.stream();
    }
}