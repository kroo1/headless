package odt.api;

import java.io.FileWriter;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import odt.client.ODTClient;
import odt.client.api.ApiUtil;
import odt.client.api.OdtActionsApi;
import odt.client.api.OdtActionsTestApi;
import odt.client.model.ODTActionSequence;
import odt.client.model.ODTActionSequenceTest;
import odt.client.model.ODTSateResponse;
import odt.context.Context;

@RestController
public class OdtActionsApiController implements OdtActionsApi, OdtActionsTestApi {

    @Autowired
    NativeWebRequest request;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.of(request);
    }

    private static ConcurrentHashMap<String, ODTClient> odts = new ConcurrentHashMap<>();
    
    @Override
    public ResponseEntity<ODTSateResponse> postODTActions(ODTActionSequence odTActionSequence, String xSessionIdentifier) {
        if(odTActionSequence == null || odTActionSequence.getActions() == null) {
            return new ResponseEntity(new ODTSateResponse(), HttpStatus.FORBIDDEN);
        }
        try {
            ODTClient odt = odts.get(xSessionIdentifier);
            if(odt == null) {
                odt = new ODTClient();
                odts.put(xSessionIdentifier, odt);
            }
            Context context = odt.init();
            context.runActions(odTActionSequence.getActions());
            String state = context.getState();
            FileWriter writer = null;
            try {
                writer = new FileWriter("/home/vscode/test/"+xSessionIdentifier);
                writer.write(state);
            }catch(Exception ex) {
                ex.printStackTrace();
            }finally {
                if(writer != null) writer.close();
            }
            getRequest().ifPresent(request -> {
                ApiUtil.setExampleResponse(request, "application/json", state);
            });
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ODTSateResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ODTSateResponse> postODTActionsTest(ODTActionSequenceTest odTActionSequence, String xSessionIdentifier) {
        String actionstr = odTActionSequence.getActions().stream().collect(Collectors.joining("\n", "", ""));
        ODTActionSequence actions = new ODTActionSequence().actions(actionstr);
        return postODTActions(actions, xSessionIdentifier);
    }
}