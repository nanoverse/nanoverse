package nanoverse.runtime.io.deserialize.agent;

import nanoverse.runtime.io.deserialize.TextInputHandle;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by dbborens on 10/26/2015.
 */
public class NameIndexReader {

    public Map<Integer, String> readReverseIndex(TextInputHandle indexFile) {
        return indexFile.lines()
                .map(String::trim)
                .map(trimmed -> trimmed.split("\t"))
                .collect(Collectors.toMap(this::getKey, this::getValue));
    }

    private Integer getKey(String[] fields) {
        String keyElem = fields[0];
        return Integer.valueOf(keyElem);
    }

    private String getValue(String[] fields) {
        return fields[1];
    }
}
