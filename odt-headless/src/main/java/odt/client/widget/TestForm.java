package odt.client.widget;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.openapitools.jackson.nullable.JsonNullableModule;

import odt.client.*;
import odt.client.model.ODTSateResponse;
import odt.context.ODTComponentFactory;

public class TestForm  extends AComponent implements IForm {

    private IForm formImpl;

    public TestForm() {
        formImpl = ODTComponentFactory.createForm(this);
        impl = formImpl;
    }

    @Override
    public String getODTState() {
        ODTSateResponse resp = new ODTSateResponse();

        IComponent[] cmps = formImpl.getFields();
        int cmpc = cmps != null ? cmps.length : 0;
        for (int idx = 0; idx < cmpc; idx++) {
            IComponent cmp = cmps[idx];
            resp.addOdtStateItem(cmp.persist());
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.registerModule(new JsonNullableModule());
        try {
            return mapper.writeValueAsString(resp);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    @Override
    public void setSate(Map state) {
        IComponent[] cmps = formImpl.getFields();
        int cmpc = cmps != null ? cmps.length : 0;
        for (int idx = 0; idx < cmpc; idx++) {
            Object item = cmps[idx];
            IComponent cmp = cmps[idx];
            Map iState = (Map)state.get(cmp.getName());
            if(iState != null) {
                cmp.setSate(iState);
            }
        }
    }

    @Override
    public void add(IComponent b) {
        formImpl.add(b);
        b.setFormId(this.getName());
    }

}
