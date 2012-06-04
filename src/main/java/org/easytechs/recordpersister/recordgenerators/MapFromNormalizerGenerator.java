package org.easytechs.recordpersister.recordgenerators;

import java.util.Map;

import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.RecordGenerator;


public class MapFromNormalizerGenerator implements RecordGenerator<Map<String, String>> {

        @Override
        public Map<String, String> generate(NormalizedMessage message) {
                return message.getValues();
        }

}