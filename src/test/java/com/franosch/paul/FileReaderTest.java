package com.franosch.paul;

import com.franosch.paul.io.FileReader;
import com.franosch.paul.model.Graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileReaderTest {


    @Test
    public void simpleFileReaderTest() {
        FileReader fileReader = new FileReader(true);
        Graph graph = fileReader.read("huepfburg0");
        Assertions.assertNotNull(graph);
    }

}
