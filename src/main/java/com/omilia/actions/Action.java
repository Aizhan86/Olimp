package com.omilia.actions;

import com.omilia.diamant.dialog.components.fields.ApiField;

import java.io.IOException;
import java.util.Map;

public interface Action {
    void process() throws IOException;

    void setInput(Map<String, ApiField> var1);

    Map<String, ApiField> getOutput();

    void setOutput(Map<String, ApiField> var1);
}
