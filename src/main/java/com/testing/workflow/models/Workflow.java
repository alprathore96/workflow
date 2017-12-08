package com.testing.workflow.models;

import com.testing.workflow.models.operation.Operation;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Workflow {

    private static final Logger LOGGER = Logger.getLogger(Workflow.class);

    private String id;
    private String name;
    List<Constant> constants;
    private List<Operation> operations;

    @PostConstruct
    public void enrichConstants() {
        for ( Operation operation : operations ) {
            Map<String, Object> inputs = operation.getInputs();
            enrichMap(inputs);
        }
    }

    private void enrichMap(Map<String, Object> map ) {
        for (Map.Entry entry : map.entrySet()) {
            Object value = entry.getValue();
            if ( value instanceof Map ) {
                enrichMap((Map)value);
            } else if ( value instanceof String) {
                String strValue = (String) value;
                Pattern pattern = Pattern.compile("(.*)\\$\\{(.*)}(.*)");
                Matcher matcher = pattern.matcher(strValue);
                if ( matcher.find() ) {
                    try {
                        String constantExpression = matcher.group(2);
                        Optional<Constant> constantOptional = constants.stream().filter(constant -> constant.getKey() != null
                                && constant.getKey()
                                .equalsIgnoreCase(constantExpression)).findAny();
                        constantOptional.ifPresent(constant -> entry.setValue((matcher.group(1) + constant.value + matcher.group(3))));
                    } catch (Exception e) {
                        LOGGER.warn(String.format("Could not match string %s for constant pattern.", strValue));
                    }
                }
            }
        }
    }

    public Optional<Operation> getOperation(double id) {
        return this.operations.stream().filter(o -> o.getId() == id).findAny();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Constant> getConstants() {
        return constants;
    }

    public void setConstants(List<Constant> constants) {
        this.constants = constants;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Workflow workflow = (Workflow) o;

        return id.equals(workflow.id);
    }

    @Override
    public int hashCode() {
        return (id == null ? 0 : id.hashCode());
    }

    public static class Constant {
        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
