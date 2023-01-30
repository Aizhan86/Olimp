package com.omilia.actions;

import com.omilia.diamant.dialog.components.fields.ApiField;
import com.omilia.diamant.loggers.DialogLogger;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractAction implements Action{
    protected Map<String, ApiField> input;
    protected Map<String, ApiField> output;
    protected DialogLogger logger;

    public AbstractAction(Map<String, ApiField> input, DialogLogger logger, String actionName) {
        this.setInput(input);
        this.output = new HashMap();
        this.logger = logger;
    }

    public void setInput(Map<String, ApiField> input) {
        this.input = input;
    }

    public Map<String, ApiField> getOutput() {
        return this.output;
    }

    public void setOutput(Map<String, ApiField> output) {
        this.output = output;
    }
}
