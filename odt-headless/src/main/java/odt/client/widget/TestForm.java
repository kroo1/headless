package odt.client.widget;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import odt.client.*;
import odt.client.ODTComponentFactory;
import odt.client.model.ODTSateResponse;

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
        try {
            return mapper.writeValueAsString(resp);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
        /*StringBuilder json = new StringBuilder();
        try {
            json.append("{");
            boolean append = false;
            IComponent[] cmps = formImpl.getFields();
            int cmpc = cmps != null ? cmps.length : 0;
            for (int idx = 0; idx < cmpc; idx++) {
                IComponent cmp = cmps[idx];
                if (append) {
                    json.append(",");
                }
                json.append(cmp.persist());
                append = true;
            }

            json.append("}");

            //System.out.println("ODT_JSON:" + json.toString());
            return json.toString();
        }catch(Exception ex) {
            ex.printStackTrace();
            return "{}";
        }*/
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
