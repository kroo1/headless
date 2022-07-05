package odt.client.api;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import io.swagger.annotations.ApiParam;
import odt.client.Context;
import odt.client.ODTClient;
import odt.client.ODTComponentFactory;
import odt.client.api.OdtActionsApi;
import odt.client.model.ODTActionSequence;
import odt.client.model.ODTSateResponse;

@RestController
public class OdtActionsApiController implements OdtActionsApi {

    @Autowired
    NativeWebRequest request;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.of(request);
    }
    
    @Override
    public ResponseEntity<ODTSateResponse> postODTActions(@ApiParam(value = "Sequence of actions to perform on the ODT server." ,required=true )  @Valid @RequestBody ODTActionSequence odTActionSequence,@ApiParam(value = "Parameter to be identify the current middle layer session " ) @RequestHeader(value="X-Session-Identifier", required=false) String xSessionIdentifier) {
        if(odTActionSequence == null || odTActionSequence.getActions() == null) {
            return new ResponseEntity(new ODTSateResponse(), HttpStatus.FORBIDDEN);
        }
        try {
            new ODTClient();
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
            }

            /*
            Context context = ODTComponentFactory.getContext();
            context.runActions(odTActionSequence.getActions());
            String state = context.getState();
            FileWriter writer = null;
            try {
                writer = new FileWriter("/workspaces/ActionTest/odt-headless/mock/"+hash);
                writer.write(state);
            }catch(Exception ex) {
                ex.printStackTrace();
            }finally {
                if(writer != null) writer.close();
            }
            getRequest().ifPresent(request -> {
                ApiUtil.setExampleResponse(request, "application/json", state);
            });*/
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ODTSateResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}