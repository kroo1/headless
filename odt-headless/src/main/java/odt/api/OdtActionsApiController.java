package odt.api;

import java.io.FileWriter;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import odt.client.ODTClient;
import odt.client.api.ApiUtil;
import odt.client.api.OdtActionsApi;
import odt.client.model.ODTActionSequence;
import odt.client.model.ODTSateResponse;
import odt.context.Context;

@RestController
public class OdtActionsApiController implements OdtActionsApi {

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



            /*new ODTClient();
            int hash = odTActionSequence.getActions().hashCode();

            BufferedReader br = null;
            try {
                StringBuilder res = new StringBuilder();
                FileReader reader = new FileReader("/workspaces/ActionTest/odt-headless/mock/"+hash);
                br = new BufferedReader(reader);
                String line;
                while((line = br.readLine())!=null) {
                    res.append(line).append("\n");
                }
                String state = res.toString();
                getRequest().ifPresent(request -> {
                    ApiUtil.setExampleResponse(request, "application/json", state);
                });
            }catch(Exception ex) {
                ex.printStackTrace();
            }finally {
                if(br != null) br.close();
            }*/
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
}